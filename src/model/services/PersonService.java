package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PersonDao;
import model.entities.Person;

public class PersonService {
	
	PersonDao dao = DaoFactory.createPersonDao();
	
	public List<Person> findAll() {
		List<Person> list = dao.findAll();
		return list;
	}
	
}
