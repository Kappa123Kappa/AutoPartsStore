package com.app.dao.impl;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.app.dao.AutoPartsDao;
import com.app.model.AutoPart;
import com.app.model.Salary;
import com.app.model.SoldAutoPart;
import com.app.model.Worker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AutoPartsDaoImpl implements AutoPartsDao {
	
	
	// get
	@Override
	public ObservableList<AutoPart> getAllAutoParts(Connection connection){
		ObservableList<AutoPart> autoPartsList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM AutoParts");
			while(resultSet.next()){
				AutoPart autoPart = new AutoPart();
				autoPart.setID(resultSet.getInt("id"));
				autoPart.setName(resultSet.getString("name"));
				autoPart.setManufacturer(resultSet.getString("manufacturer"));
				autoPart.setPrice(resultSet.getString("price"));
				autoPart.setPriceIncreaseCoefficient(
						resultSet.getDouble("priceIncreaseCoefficient"));
				autoPart.setDescription(resultSet.getString("description"));
				autoPart.setQuantity(resultSet.getInt("quantity"));
				autoPart.setDate(resultSet.getString("date"));
				autoPartsList.add(autoPart);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return autoPartsList;
		
	}
	
	
	@Override
	public ObservableList<SoldAutoPart> getAllSoldAutoParts(Connection connection){
		ObservableList<SoldAutoPart> autoSoldPartsList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM SoldAutoParts");
			while(resultSet.next()){
				SoldAutoPart soldAutoPart = new SoldAutoPart();
				soldAutoPart.setID(resultSet.getInt("id"));
				soldAutoPart.setWorker(resultSet.getString("worker"));
				soldAutoPart.setSoldDecommission(resultSet.getString("soldDecommission"));
				soldAutoPart.setName(resultSet.getString("name"));
				soldAutoPart.setManufacturer(resultSet.getString("manufacturer"));
				soldAutoPart.setPrice(resultSet.getString("price"));
				soldAutoPart.setProfit(resultSet.getString("profit"));
				soldAutoPart.setDescription(resultSet.getString("description"));
				soldAutoPart.setQuantity(resultSet.getInt("quantity"));
				soldAutoPart.setDate(resultSet.getString("date"));
				autoSoldPartsList.add(soldAutoPart);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return autoSoldPartsList;
	}
	
	@Override
	public ObservableList<SoldAutoPart> getAllSoldAutoPartsByDay(Connection connection, 
				int day){
		ObservableList<SoldAutoPart> autoSoldPartsList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM SoldAutoParts "
					+ "WHERE DAYOFMONTH(date) = " + day);
			while(resultSet.next()){
				SoldAutoPart soldAutoPart = new SoldAutoPart();
				soldAutoPart.setID(resultSet.getInt("id"));
				soldAutoPart.setWorker(resultSet.getString("worker"));
				soldAutoPart.setSoldDecommission(resultSet.getString("soldDecommission"));
				soldAutoPart.setName(resultSet.getString("name"));
				soldAutoPart.setManufacturer(resultSet.getString("manufacturer"));
				soldAutoPart.setPrice(resultSet.getString("price"));
				soldAutoPart.setProfit(resultSet.getString("profit"));
				soldAutoPart.setDescription(resultSet.getString("description"));
				soldAutoPart.setQuantity(resultSet.getInt("quantity"));
				soldAutoPart.setDate(resultSet.getString("date"));
				autoSoldPartsList.add(soldAutoPart);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return autoSoldPartsList;
	}
	
	@Override
	public ObservableList<SoldAutoPart> getAllSoldAutoPartsByMonth(Connection connection, 
				int month){
		ObservableList<SoldAutoPart> autoSoldPartsList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM SoldAutoParts "
					+ "WHERE MONTH(date) = " + month);
			while(resultSet.next()){
				SoldAutoPart soldAutoPart = new SoldAutoPart();
				soldAutoPart.setID(resultSet.getInt("id"));
				soldAutoPart.setWorker(resultSet.getString("worker"));
				soldAutoPart.setSoldDecommission(resultSet.getString("soldDecommission"));
				soldAutoPart.setName(resultSet.getString("name"));
				soldAutoPart.setManufacturer(resultSet.getString("manufacturer"));
				soldAutoPart.setPrice(resultSet.getString("price"));
				soldAutoPart.setProfit(resultSet.getString("profit"));
				soldAutoPart.setDescription(resultSet.getString("description"));
				soldAutoPart.setQuantity(resultSet.getInt("quantity"));
				soldAutoPart.setDate(resultSet.getString("date"));
				autoSoldPartsList.add(soldAutoPart);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return autoSoldPartsList;
	}
	
	@Override
	public ObservableList<SoldAutoPart> getAllSoldAutoPartsByYear(Connection connection, 
				int year){
		ObservableList<SoldAutoPart> autoSoldPartsList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM SoldAutoParts "
					+ "WHERE YEAR(date) = " + year);
			while(resultSet.next()){
				SoldAutoPart soldAutoPart = new SoldAutoPart();
				soldAutoPart.setID(resultSet.getInt("id"));
				soldAutoPart.setWorker(resultSet.getString("worker"));
				soldAutoPart.setSoldDecommission(resultSet.getString("soldDecommission"));
				soldAutoPart.setName(resultSet.getString("name"));
				soldAutoPart.setManufacturer(resultSet.getString("manufacturer"));
				soldAutoPart.setPrice(resultSet.getString("price"));
				soldAutoPart.setProfit(resultSet.getString("profit"));
				soldAutoPart.setDescription(resultSet.getString("description"));
				soldAutoPart.setQuantity(resultSet.getInt("quantity"));
				soldAutoPart.setDate(resultSet.getString("date"));
				autoSoldPartsList.add(soldAutoPart);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return autoSoldPartsList;
	}
	
	@Override
	public ObservableList<SoldAutoPart> getAllSoldAutoPartsOfOneWorker(Connection connection, 
				String workersName){
		ObservableList<SoldAutoPart> autoSoldPartsList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM SoldAutoParts WHERE "
					+ "worker = '" + workersName + "'");
			while(resultSet.next()){
				SoldAutoPart soldAutoPart = new SoldAutoPart();
				soldAutoPart.setID(resultSet.getInt("id"));
				soldAutoPart.setWorker(resultSet.getString("worker"));
				soldAutoPart.setSoldDecommission(resultSet.getString("soldDecommission"));
				soldAutoPart.setName(resultSet.getString("name"));
				soldAutoPart.setManufacturer(resultSet.getString("manufacturer"));
				soldAutoPart.setPrice(resultSet.getString("price"));
				soldAutoPart.setProfit(resultSet.getString("profit"));
				soldAutoPart.setDescription(resultSet.getString("description"));
				soldAutoPart.setQuantity(resultSet.getInt("quantity"));
				soldAutoPart.setDate(resultSet.getString("date"));
				autoSoldPartsList.add(soldAutoPart);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return autoSoldPartsList;
	}

	@Override
	public ObservableList<Worker> getAllWorkers(Connection connection) {
		ObservableList<Worker> workersList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Workers");
			while(resultSet.next()) {
				Worker workers = new Worker();
				workers.setID(resultSet.getInt("id"));
				workers.setFirstName(resultSet.getString("firstName"));
				workers.setSecondName(resultSet.getString("secondName"));
				workers.setPosition(resultSet.getString("position"));
				workers.setInterestStable(resultSet.getString("interestStable"));
				workers.setPassword(resultSet.getString("password"));
				workersList.add(workers);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return workersList;
	}

	@Override
	public ObservableList<Salary> getAllSallaries(Connection connection) {
		ObservableList<Salary> sallariesList = FXCollections.observableArrayList();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Sallaries");
			while(resultSet.next()) {
				Salary sallaries = new Salary();
				sallaries.setID(resultSet.getInt("id"));
				sallaries.setWorker(resultSet.getString("worker"));
				sallaries.setPosition(resultSet.getString("position"));
				sallaries.setInterestStable(resultSet.getString("interestStable"));
				sallaries.setSallaryCoefficient(resultSet.getString("sallaryCoefficient"));
				sallaries.setSallary(resultSet.getString("sallary"));
				sallariesList.add(sallaries);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return sallariesList;
	}
	
	
	
	// add
	@Override
	public void addDefaultWorkers(Connection connection) {
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO Workers "
						+ " VALUES(1, 'John', 'Doe', 'CEO', 'Interest', '111');");
			
			statement.executeUpdate("INSERT INTO Workers "
						+ " VALUES(2, 'Peter', 'Kappa', 'Seller', 'Interest', '222');");
			
			statement.executeUpdate("INSERT INTO Workers "
						+ " VALUES(3, 'Artour', 'Babaev', 'Seller', 'Stable', '333');");
			
			statement.executeUpdate("INSERT INTO Workers "
						+ " VALUES(4, 'Vitaliy', 'Vilat', 'Accountant', 'Stable', '444');");
			
		} catch(SQLException e) {
			
		}
	}
	
	
	@Override
	public void addWorker(Connection connection, int id, String firstName, 
					String secondName, String position, String interestStable, String password) {
		
		try {
			PreparedStatement preparedStatement = 
    				connection.prepareStatement("INSERT INTO Workers VALUES(?,  ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, firstName);
    		preparedStatement.setString(3, secondName);
    		preparedStatement.setString(4, position);
    		preparedStatement.setString(5, interestStable);
    		preparedStatement.setString(6, password);
    		preparedStatement.executeUpdate();
			
		} catch(SQLException e) {
			
		}
		
	}

	
	@Override
	public void addDefaultAvailableAutoParts(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now).toString());
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(1, 'Cylinder heads', 'HKS', '1000.00', 1.1, 'Race', 15, '" 
						+ dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(2, 'Ignition system', 'Nology', '570.00', 1.5, 'Race', 6, '" 
						+ dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(3, 'Fuel system', 'Moroso', '900.00', 1.3, 'Race', 9, '" 
						+ dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(4, 'Frotn axle', 'SLP Perfomanse parts', '1500.00', 1.1, "
						+ "'Race', 20, '" + dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(5, 'Rear axle', 'SLP Perfomanse parts', '1500.00', 1.3, "
						+ "'Race', 20, '" + dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(6, 'Transmission', 'BEM', '2500.00', 1.09, 'Race', 5, '" 
						+ dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(7, 'Transmission', 'BEM', '2000.00', 1.09, 'Sport', 15, '" 
						+ dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(8, 'Nitrous oxide', 'Nitrous Oxide Systems', '500.00', 1.5, "
						+ "'Dinitrogen monoxide', 35, '" + dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(9, 'Tires', 'TOYO', '5000.00', 1.4, 'Complect(4) Drift', 15, '" 
						+ dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(10, 'Tires', 'Pirelli', '5000.00', 1.4, 'Complect(4) Race', "
						+ "15, '" + dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(11, 'Brakes', 'Brembo', '1200.00', 1.3, 'Complect(4) Race',"
						+ " 20, '" + dtf.format(now).toString() + "');");
			
			statement.executeUpdate("INSERT INTO AutoParts "
						+ "VALUES(12, 'Turbocharger', 'Turbonetics', '1100.00', 1.2, 'Race', "
						+ "20, '" + dtf.format(now).toString() + "');");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addAutoPart(Connection connection, int id, String name, String manufacturer,
								String price, String priceincreaseCoefficient, String description, int quantity, String date){
		try {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("INSERT INTO AutoParts VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, manufacturer);
			preparedStatement.setString(4, price);
			preparedStatement.setString(5, priceincreaseCoefficient);
			preparedStatement.setString(6, description);
			preparedStatement.setInt(7, quantity);
			preparedStatement.setString(8, date);
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void addSoldAutoPart(Connection connection, int id, String worker, 
			String soldDecommission, String name, String manufacturer,
			String price, String profit, String description, int quantity, Date date){
		try {
			PreparedStatement preparedStatement = 
			connection.prepareStatement("INSERT INTO SoldAutoParts VALUES(?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, worker);
			preparedStatement.setString(3, soldDecommission);
			preparedStatement.setString(4, name);
			preparedStatement.setString(5, manufacturer);
			preparedStatement.setString(6, price);
			preparedStatement.setString(7, profit);
			preparedStatement.setString(8, description);
			preparedStatement.setInt(9, quantity);
			preparedStatement.setDate(10, date);
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void addDefaultSallaries(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO Sallaries "
						+ " VALUES(1, 'John Doe', 'CEO', 'Interest', '0.20', '0.00');");
			
			statement.executeUpdate("INSERT INTO Sallaries "
						+ " VALUES(2, 'Peter Kappa', 'Seller', 'Interest', '0.02', '0.00');");
			
			statement.executeUpdate("INSERT INTO Sallaries "
						+ " VALUES(3, 'Artour Babaev', 'Seller', 'Stable', '0.00', '1000.00');");
			
			statement.executeUpdate("INSERT INTO Sallaries "
						+ " VALUES(4, 'Vitaliy Vilat', 'Accountant', 'Stable', '0.00', '2000.00');");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addSallary(Connection connection, int id, String worker, String position, 
			String interestStable, String sallaryCoefficient, String sallary) {
		
		try {
			PreparedStatement preparedStatement = 
    				connection.prepareStatement("INSERT INTO Sallaries VALUES(?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, worker);
    		preparedStatement.setString(3, position);
    		preparedStatement.setString(4, interestStable);
    		preparedStatement.setString(5, sallaryCoefficient);
    		preparedStatement.setString(6, sallary);
    		preparedStatement.executeUpdate();
			
		} catch(SQLException e) {
			
		}
		
	}
	
	
	
	
	// update
	
	@Override
	public void updateWorker(Connection connection, int id, String firstName,
					String secondName, String position, String interestStable, String password) {
		try {
    		PreparedStatement preparedStatement = 
    				connection.prepareStatement("UPDATE Workers SET firstName = ?, "
							+ "secondName = ?, position = ?, interestStable = ?, "
							+ "password = ? WHERE id = ?");
    		preparedStatement.setString(1, firstName);
    		preparedStatement.setString(2, secondName);
    		preparedStatement.setString(3, position);
    		preparedStatement.setString(4, interestStable);
    		preparedStatement.setString(5, password);
    		preparedStatement.setInt(6, id);
    		preparedStatement.executeUpdate();
    		
			
    	}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateOnlySallary(Connection connection, int id, String interestStable, 
			String salaryCoefficien,  String salary) {
			try {
	    		PreparedStatement preparedStatement = 
	    				connection.prepareStatement("UPDATE Sallaries SET interestStable = ?, "
	    						+ "sallaryCoefficient = ?, sallary = ? "
	    						+ "WHERE id = ?");
	    		preparedStatement.setString(1, interestStable);
	    		preparedStatement.setString(2, salaryCoefficien);
	    		preparedStatement.setString(3, salary);
	    		preparedStatement.setInt(4, id);
	    		preparedStatement.executeUpdate();
	    		
				
	    	}catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public void updateSallaryInformation(Connection connection, int id, String worker, String position, 
			String interestStable, String sallaryCoefficient, String sallary) {
		try {
    		PreparedStatement preparedStatement = 
    				connection.prepareStatement("UPDATE Sallaries SET worker = ?, "
    						+ "position = ?, interestStable = ?, "
    						+ "sallaryCoefficient = ?, sallary = ? WHERE id = ?");
    		preparedStatement.setString(1, worker);
    		preparedStatement.setString(2, position);
    		preparedStatement.setString(3, interestStable);
    		preparedStatement.setString(4, sallaryCoefficient);
    		preparedStatement.setString(5, sallary);
    		preparedStatement.setInt(6, id);
    		preparedStatement.executeUpdate();
    		
			
    	}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateAvailableAutoPart(Connection connection, int id, String name,
					String manufacturer, String price, double priceincreaseCoefficient, String description, int quantity) {
		try {
			
    		PreparedStatement preparedStatement = 
    				connection.prepareStatement("UPDATE AutoParts SET name = ?, "
							+ "manufacturer = ?, price = ?, priceincreaseCoefficient = ?, "
							+ "description = ?, quantity = ? "  
							+ "WHERE id = ?");
    		preparedStatement.setString(1, name);
    		preparedStatement.setString(2, manufacturer);
    		preparedStatement.setString(3, price);
    		preparedStatement.setDouble(4, priceincreaseCoefficient);
    		preparedStatement.setString(5, description);
    		preparedStatement.setInt(6, quantity);
    		preparedStatement.setInt(7, id);
    		preparedStatement.executeUpdate();
    		
			
    	}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAvailableAutoPartQuantity(Connection connection, int id, int quantity) {
		try {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("UPDATE AutoParts SET quantity = ?"  
							+ " WHERE id = ?");
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}


	
	// delete
	@Override
	public void deleteWorkerFromWorkers(Connection connection, int id) {
		try {
    		PreparedStatement preparedStatement = 
    				connection.prepareStatement("DELETE FROM Workers WHERE id = ? LIMIT 1");
    		preparedStatement.setInt(1, id);
    		preparedStatement.execute();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteWorkerFromSallaries(Connection connection, int id) {
		try {
    		PreparedStatement preparedStatement = 
    				connection.prepareStatement("DELETE FROM Sallaries WHERE id = ? LIMIT 1");
    		preparedStatement.setInt(1, id);
    		preparedStatement.execute();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteAutoPartFromAvailableAutoParts(Connection connection, int id, String worker, 
			String soldDecommission, String name, String manufacturer,
			String price, String profit, String description, int quantity) {
		
		
		ObservableList<AutoPart> autoPartsList = getAllAutoParts(connection);
	
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
		DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
	
		
		String nowDate = dateFormater.format(now);
		
		int quantityInDB = 0;
		for(int i = 0; i < autoPartsList.size(); i++) {
			if(autoPartsList.get(i).getID().intValue() == id) {
				quantityInDB = autoPartsList.get(i).getQuantity().intValue();
			}
		}
		
		if(quantityInDB == quantity) {
			
			addSoldAutoPart(connection, 
					getAllSoldAutoPartsOfOneWorker(connection, worker).size() + 1, worker, 
					soldDecommission,  name,  manufacturer, price,  profit,  description,  
					quantity,  Date.valueOf(dateFormater.format(now)));
			try{
				PreparedStatement preparedStatement = 
						connection.prepareStatement("DELETE FROM AutoParts WHERE id = ? LIMIT 1");
				preparedStatement.setInt(1, id);
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else {
			updateAvailableAutoPartQuantity(connection, Integer.valueOf(id), quantityInDB - quantity);
			addSoldAutoPart(connection, getAllSoldAutoPartsOfOneWorker(connection, worker).size() + 1, worker, soldDecommission,  name,  manufacturer,
					 price,  profit,  description,  quantity,  Date.valueOf(dateFormater.format(now).toString()));
		}
		
	}

}
