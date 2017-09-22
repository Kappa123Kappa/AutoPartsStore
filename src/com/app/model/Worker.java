package com.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Worker {
	
	private IntegerProperty id;
	private StringProperty  firstName;
	private StringProperty  secondName;
	private StringProperty  position;
	private StringProperty  interestStable;
	private StringProperty password;
	

	
	public void setID(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	
	public IntegerProperty getID() {
		return id;
	}
	
	
	
	public void setFirstName(String firstName) {
		this.firstName = new SimpleStringProperty(firstName);
	}
	
	public StringProperty getFirstName() {
		return firstName;
	}
	
	
	
	public void setSecondName(String secondName) {
		this.secondName = new SimpleStringProperty(secondName);
	}
	
	public StringProperty getSecondName() {
		return secondName;
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
	
	
	
	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}
	
	public StringProperty getPassword() {
		return password;
	}
}
