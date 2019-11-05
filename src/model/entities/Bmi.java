package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Bmi implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date date;
	private Double bmiValue;
	private String result;
	private Double weight;
	private Double height;
	
	private Person person;
	
	public Bmi() {
	}

	public Bmi(Integer id, Date date, Double bmiValue, String result, Double weight, Double height, Person person) {
		super();
		this.id = id;
		this.date = date;
		this.bmiValue = bmiValue;
		this.result = result;
		this.weight = weight;
		this.height = height;
		this.person = person;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getBmiValue() {
		return bmiValue;
	}

	public void setBmiValue(Double bmiValue) {
		this.bmiValue = bmiValue;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Bmi other = (Bmi) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
