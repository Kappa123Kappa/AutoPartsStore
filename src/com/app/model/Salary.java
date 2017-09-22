package com.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Salary {
	
	private IntegerProperty id;
	private StringProperty worker;
	private StringProperty position;
	private StringProperty interestStable;
	private StringProperty salaryCoefficient;
	private StringProperty salary;
	private StringProperty profit;
	

	
	public void setID(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	
	public IntegerProperty getID() {
		return id;
	}
	
	
	
	public void setWorker(String worker) {
		this.worker = new SimpleStringProperty(worker);
	}
	
	public StringProperty getWorker() {
		return worker;
	}
	
	
	
	public void setPosition(String position) {
		this.position = new SimpleStringProperty(position);
	}
	
	public StringProperty getPosition() {
		return position;
	}
	
	
	
	public void setInterestStable(String interestStable) {
		this.interestStable = new SimpleStringProperty(interestStable);
	}
	
	public StringProperty getInterestStable() {
		return interestStable;
	}
	
	
	
	public void setSallaryCoefficient(String sallaryCoefficient) {
		this.salaryCoefficient = new SimpleStringProperty(sallaryCoefficient);
	}
	
	public StringProperty getSallaryCoefficient() {
		return salaryCoefficient;
	}
	
	
	public void setSallary(String sallary) {
		this.salary = new SimpleStringProperty(sallary);
	}
	
	public StringProperty getSallary() {
		return salary;
	}
	
	
	
	public void setAllprofit(String profit) {
		this.profit = new SimpleStringProperty(profit);
	}
	
	public StringProperty getAllprofit() {
		return profit;
	}
}
