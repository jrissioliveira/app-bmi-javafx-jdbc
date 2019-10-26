package model.dao;

import db.DB;
import model.dao.imp.BmiDaoJDBC;
import model.dao.imp.PersonDaoJDBC;

public class DaoFactory {
	
	public static PersonDao createPersonDao() {
		return new PersonDaoJDBC(DB.getConnection());
	}
	
	public static BmiDao createBmiDao() {
		return new BmiDaoJDBC(DB.getConnection());
	}
	
}
