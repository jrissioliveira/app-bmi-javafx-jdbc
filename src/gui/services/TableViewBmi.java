package gui.services;

import java.util.Date;

import model.entities.Bmi;
import model.entities.Person;

public class TableViewBmi {
	
	private Integer id;
	private String name;
	private Integer yearsOld;
	private Double height;
	private Double weight;
	private Integer bmiId;
	private Double bmiValue;
	private Date date;
	private String Result;
	
	public TableViewBmi(Bmi bmi) {
		this.id = bmi.getPerson().getId();
		this.name = bmi.getPerson().getName();
		this.yearsOld = calcYearOld(bmi.getPerson());
		this.height = bmi.getPerson().getHeight();
		this.weight = bmi.getPerson().getWeight();
		this.bmiId = bmi.getId();
		this.bmiValue = bmi.getBmiValue();
		this.date = bmi.getDate();
		this.Result = bmi.getResult();
	}
	
	private Integer calcYearOld(Person person) {
		double birthDate = person.getBirthDate().getTime();
		double now = new Date().getTime();
		int dif = (int) ((now - birthDate) /1000.0 /60.0 /60.0 /24.0/ 365);
		return dif;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYearsOld() {
		return yearsOld;
	}

	public void setYearsOld(Integer yearsOld) {
		this.yearsOld = yearsOld;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getBmiValue() {
		return bmiValue;
	}

	public void setBmiValue(Double bmiValue) {
		this.bmiValue = bmiValue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public Integer getBmiId() {
		return bmiId;
	}

	public void setBmiId(Integer bmiId) {
		this.bmiId = bmiId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bmiId == null) ? 0 : bmiId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableViewBmi other = (TableViewBmi) obj;
		if (bmiId == null) {
			if (other.bmiId != null)
				return false;
		} else if (!bmiId.equals(other.bmiId))
			return false;
		return true;
	}
	
}
