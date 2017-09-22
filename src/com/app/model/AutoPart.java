package com.app.model;

import javafx.beans.property.*;

public class AutoPart {
	
	private IntegerProperty id;
	private StringProperty  name;
	private StringProperty  manufacturer;
	private StringProperty  price;
	private DoubleProperty  priceIncreaseCoefficient;
	private StringProperty  description;
	private IntegerProperty quantity;
	private StringProperty  date;
	
	public void setID(int id){
		this.id = new SimpleIntegerProperty(id);
	}
	
	public IntegerProperty  getID(){
		return id;
	}
	
	
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public StringProperty getName(){
		return name;
	}
	
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = new SimpleStringProperty(manufacturer);
	}
	
	public StringProperty getManufacturer(){
		return manufacturer;
	}
	
	
	public void setPriceIncreaseCoefficient(double priceIncreaseCoefficient) {
		this.priceIncreaseCoefficient = new SimpleDoubleProperty(priceIncreaseCoefficient);
	}
	
	public DoubleProperty getPriceIncreaseCoefficient(){
		return priceIncreaseCoefficient;
	}
	
	
	public void setPrice(String price) {
		this.price = new SimpleStringProperty(price);
	}
	
	public StringProperty getPrice(){
		return price;
	}
	
	
	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}
	
	public StringProperty getDescription(){
		return description;
	}
	
	
	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}
	
	public IntegerProperty getQuantity(){
		return quantity;
	}
	
	
	public void setDate(String date) {
		this.date = new SimpleStringProperty(date);
	}
	
	public StringProperty getDate(){
		return date;
	}
}
