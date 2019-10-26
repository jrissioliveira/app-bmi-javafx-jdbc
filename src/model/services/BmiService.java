package model.services;

import java.util.Date;

import model.entities.Bmi;
import model.entities.Person;

public class BmiService {
	
	public static void bmiGenerator(Person person) {
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
		
		person.addBmi(new Bmi(null, new Date(), bmiValue, result, person));
	}
	
}
