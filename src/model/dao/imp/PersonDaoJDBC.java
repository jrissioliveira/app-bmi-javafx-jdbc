package model.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PersonDao;
import model.entities.Person;

public class PersonDaoJDBC implements PersonDao {
	
	private Connection conn;
	
	public PersonDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Person obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO person "
					+ "(name = ?, weight = ? height =?, birthDate = ?) "
					+ "VALUES "
					+ "(?, ?, ?, ?", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setDouble(2, obj.getWeight());
			st.setDouble(3, obj.getHeight());
			st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
				
			}
			else {
				throw new DbException("Unexpected Error! No Rows Affected!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Person obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE person "
					+ "SET name = ?, weight = ?, height = ?, birthDate = ? "
					+ "WHERE id = ?");
			
			st.setString(1, obj.getName());
			st.setDouble(2, obj.getWeight());
			st.setDouble(3, obj.getHeight());
			st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setInt(5, obj.getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DbException("Unexpected Error! No Rows Affected!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Person obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM person WHERE id = ?");			
			st.setInt(1, obj.getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DbException("Unexpected Error! No Rows Affected!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Person> findAll() {
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM person ORDER BY name");
			List<Person> list = new ArrayList<>();	
			
			while (rs.next()) {
				Person obj = instantiatePerson(rs);
				list.add(obj);
			}
			return list;
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	private Person instantiatePerson(ResultSet rs) throws SQLException {
		Person obj = new Person();
		obj.setId(rs.getInt("id"));
		obj.setName(rs.getString("name"));
		obj.setWeight(rs.getDouble("weight"));
		obj.setHeight(rs.getDouble("height"));
		obj.setBirthDate(new java.util.Date(rs.getTimestamp("birthDate").getTime()));
		return obj;
	}

}
