package model.dao;

import java.util.List;

import model.entities.Person;

public interface PersonDao {
	
	void insert(Person obj);
	void update(Person obj);
	void delete(Person obj);
	List<Person> findAll();
	
}
