package com.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SoldAutoPart {
	private IntegerProperty id;
	private StringProperty  worker;
	private StringProperty  soldDecommission;
	private StringProperty  name;
	private StringProperty  manufacturer;
	private StringProperty  price;
	private StringProperty  profit;
	private StringProperty  description;
	private IntegerProperty quantity;
	private StringProperty  date;
	
	/*public SoldAutoParts(int id, String worker, String soldDecommission, 
					String name, String manufacturer, String price, 
					String profit, String description, int quantity, 
					String date) {
		
		this.id = new SimpleIntegerProperty(id);
		this.worker = new SimpleStringProperty(worker);
		this.soldDecommission = new SimpleStringProperty(soldDecommission);
		this.name = new SimpleStringProperty(name);
		this.manufacturer = new SimpleStringProperty(manufacturer);
		this.price = new SimpleStringProperty(price);
		this.profit = new SimpleStringProperty(profit);
		this.description = new SimpleStringProperty(description);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.date = new SimpleStringProperty(date);
	}*/
	
	public void setID(int id){
		this.id = new SimpleIntegerProperty(id);
	}
	
	public IntegerProperty  getID(){
		return id;
	}
	
	
	public void setWorker(String worker) {
		this.worker = new SimpleStringProperty(worker);
	}
	
	public StringProperty getWorker(){
		return worker;
	}
	
	
	public void setSoldDecommission(String soldDecommission) {
		this.soldDecommission = new SimpleStringProperty(soldDecommission);
	}
	
	public StringProperty getSoldDecommission(){
		return soldDecommission;
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
	
	
	public void setProfit(String profit) {
		this.profit = new SimpleStringProperty(profit);
	}
	
	public StringProperty getProfit(){
		return profit;
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
