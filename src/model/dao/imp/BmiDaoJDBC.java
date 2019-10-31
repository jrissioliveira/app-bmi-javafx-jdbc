package model.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.BmiDao;
import model.entities.Bmi;
import model.entities.Person;

public class BmiDaoJDBC implements BmiDao {
	
	private Connection conn;
	
	public BmiDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Bmi obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO bmi "
					+ "(date, bmiValue, result, person_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?,)", Statement.RETURN_GENERATED_KEYS);
			
			st.setDate(1, new java.sql.Date(obj.getDate().getTime()));
			st.setDouble(2, obj.getBmiValue());
			st.setString(3, obj.getResult());
			st.setInt(4, obj.getPerson().getId());
			
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
	public void delete(Bmi obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM bmi WHERE id = ?");
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
	public List<Bmi> findAll() {
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(
					"SELECT p.*, b.* FROM person AS p "
					+ "INNER JOIN BMI AS b "
					+ "ON p.id = b.person_id "
					+ "ORDER BY p.name");
			
			List<Bmi> list = new ArrayList<>();
			Map<Integer, Person> map = new HashMap<>();
			
			while (rs.next()) {
				Person person = map.get(rs.getInt("p.id"));
				
				if (person == null) {
					person = instantiatePerson(rs);
					map.put(person.getId(), person);
				}
				
				Bmi obj = instantiateBmi(rs, person);
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

	@Override
	public List<Bmi> findByPerson(Person ps) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT p.*, b.* FROM person AS p "
					+ "INNER JOIN BMI AS b "
					+ "ON p.id = b.person_id "
					+ "WHERE b.person_id = ? "
					+ "ORDER BY b.date");
			st.setInt(1, ps.getId());
			
			rs = st.executeQuery();
			
			List<Bmi> list = new ArrayList<>();
			Map<Integer, Person> map = new HashMap<>();
			
			while (rs.next()) {
				Person person = map.get(rs.getInt("p.id"));
				
				if (person == null) {
					person = instantiatePerson(rs);
					map.put(person.getId(), person);
				}
				
				Bmi obj = instantiateBmi(rs, person);
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
		Person person = new Person();
		person.setId(rs.getInt("p.id"));
		person.setName(rs.getString("p.name"));
		person.setWeight(rs.getDouble("p.weight"));
		person.setHeight(rs.getDouble("p.height"));
		person.setBirthDate(new java.util.Date(rs.getDate("p.birthDate").getTime()));
		return person;
	}
	
	private Bmi instantiateBmi(ResultSet rs, Person person) throws SQLException {
		Bmi obj = new Bmi();
		obj.setId(rs.getInt("b.id"));
		obj.setDate(new java.util.Date(rs.getDate("b.Date").getTime()));
		obj.setBmiValue(rs.getDouble("b.bmiValue"));
		obj.setResult(rs.getString("b.resul"));
		obj.setPerson(person);
		return obj;
	}
}
