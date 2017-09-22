package com.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;

import com.app.model.AutoPart;
import com.app.model.Salary;
import com.app.model.SoldAutoPart;
import com.app.model.Worker;

import javafx.collections.ObservableList;

public interface AutoPartsDao {
	
	ObservableList<Worker> getAllWorkers(Connection connection);
	void addDefaultWorkers(Connection connection);
	void addWorker(Connection connection, int id, String firstName, String secondName,
			String position, String interestStable, String password);
	void updateWorker(Connection connection, int id, String firstName, String secondName,
			String position, String interestStable, String password);
	void deleteWorkerFromWorkers(Connection connection, int id);
	
	
	
	
	ObservableList<AutoPart> getAllAutoParts(Connection connection);
	void addDefaultAvailableAutoParts(Connection connection);
	
	void addAutoPart(Connection connection, int id, String name, String manufacturer, 
			String price, String priceincreaseCoefficient, String description, int quantity, 
			String date);
	
	void updateAvailableAutoPart(Connection connection, int id, String name, 
			String manufacturer, String price, double priceincreaseCoefficient, 
			String description, int quantity);
	
	void updateAvailableAutoPartQuantity(Connection connection, int id, int quantity);
	
	void deleteAutoPartFromAvailableAutoParts(Connection connection, int id, String worker, 
			String soldDecommission, String name, String manufacturer,
			String price, String profit, String description, int quantity);
	
	
	
	ObservableList<SoldAutoPart> getAllSoldAutoParts(Connection connection);
	ObservableList<SoldAutoPart> getAllSoldAutoPartsByDay(Connection connection, int day);
	ObservableList<SoldAutoPart> getAllSoldAutoPartsByMonth(Connection connection, int month);
	ObservableList<SoldAutoPart> getAllSoldAutoPartsByYear(Connection connection, int year);
	ObservableList<SoldAutoPart> getAllSoldAutoPartsOfOneWorker(Connection connection, String workersName);
	void addSoldAutoPart(Connection connection, int id, String worker, String soldDecommission,
			String name, String manufacturer, String price, String profit, String description,
			int quantity, Date date);

	
	
	ObservableList<Salary> getAllSallaries(Connection connection);
	void addDefaultSallaries(Connection connection);
	
	void addSallary(Connection connection, int id, String worker, String position, 
			String interestStable, String sallaryCoefficient, String sallary);
	
	void updateOnlySallary(Connection connection, int id, String interestStable, 
			String salaryCoefficien,  String salaryTextField);
	
	void updateSallaryInformation(Connection connection, int id, String worker, String position, 
			String interestStable, String sallaryCoefficient, String sallary);
	
	void deleteWorkerFromSallaries(Connection connection, int id);
}
