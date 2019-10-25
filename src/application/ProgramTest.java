package application;

import java.util.Date;

import model.entities.Bmi;
import model.entities.Person;
import model.services.BmiService;

public class ProgramTest {

	public static void main(String[] args) {
		
		Person ps = new Person(null, "Isaias", 139.00, 1.69, new Date());
		
		BmiService.bmiGenerator(ps);
		
		
		for (Bmi bmi : ps.getBmis()) {
			System.out.println(" IMC = " + bmi.getBmiValue() + " - " + bmi.getResult());
		}

	}

}
