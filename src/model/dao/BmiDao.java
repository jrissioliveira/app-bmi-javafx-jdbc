package model.dao;

import java.util.List;

import model.entities.Bmi;
import model.entities.Person;

public interface BmiDao {
	
	void insert(Bmi obj);
	void delete(Integer id);
	List<Bmi> findAll();
	List<Bmi> findByPerson(Person ps);
	
}
