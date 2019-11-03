package model.services;

import java.util.Date;
import java.util.List;

import model.dao.BmiDao;
import model.dao.DaoFactory;
import model.entities.Bmi;
import model.entities.Person;

public class BmiService {
	
	BmiDao dao = DaoFactory.createBmiDao();
	
	public List<Bmi> findAll() {
		List<Bmi> list = dao.findAll();
		return list;
	}
	
	public List<Bmi> findByBmi(Person ps) {
		return dao.findByPerson(ps);
	}
	
	public void save(Bmi obj) {
		dao.insert(obj);
	}
	
	public void remove(Integer id) {
		dao.delete(id);
	}
	
	public Bmi bmiGenerator(Person person) {
		double height = person.getHeight();
		double weight = person.getWeight();
		
		double bmiValue = weight / (height * height);
		String result;
		
		if (bmiValue < 18.5) {
			result = "Abaixo do Peso";
		}
		else if (bmiValue >= 18.5 && bmiValue < 25) {
			result = "Peso Normal";
		}
		else if (bmiValue >= 25 && bmiValue < 30.0) {
			result = "Sobrepeso";
		}
		else if (bmiValue >= 30.0 && bmiValue < 35.0) {
			result = "Obesidade 1";
		}
		else if (bmiValue >= 35.0 && bmiValue < 40.0) {
			result = "Obesidade 2";
		}
		else {
			result = "Obesidade 3";
		}
	
		return new Bmi(null, new Date(), bmiValue, result, person);
	}

}
