package com.app.util.impl;

import java.io.*;
import java.sql.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Scanner;

import com.app.util.AutoPartsStoreUtil;

public class AutoPartsStoreUtilImpl implements AutoPartsStoreUtil{
	
	Properties properties;
	public String urlString;
	
	public String typeDB;
    private String driver;
    public String dbName; 
    public String userName;
    public String password;
    private String serverName;
    private int portNumber;

	@Override
	public void setProperties(String fileName) 
			throws InvalidPropertiesFormatException, IOException {
		
		properties = new Properties();
		FileInputStream fileImputeStream = null;
		
		
		fileImputeStream = new FileInputStream(fileName);
		properties.loadFromXML(fileImputeStream);
		
		/*try {
			fileImputeStream = new FileInputStream(fileName);
		} catch(IOException e) {
			// вікно з повідомленням про помилку
		}
		
		try {
			properties.loadFromXML(fileImputeStream);
		} catch (IOException e) {
			// вікно з повідомленням про помилку
		}*/
		
		this.typeDB = this.properties.getProperty("dbms");
    	this.driver = this.properties.getProperty("driver");
    	this.dbName = this.properties.getProperty("database_name");
    	this.userName = this.properties.getProperty("user_name");
    	this.password = this.properties.getProperty("password");
    	this.serverName = this.properties.getProperty("server_name");
    	this.portNumber = Integer.parseInt(this.properties.getProperty("port_number"));
	}

	@Override
	public void createDataBase(Connection connection) throws SQLException {
		Statement statement = null;
		if (this.typeDB.equals("mysql")) {
			statement = connection.createStatement();
			String query = "CREATE DATABASE IF NOT EXISTS " + this.dbName;
			statement.execute(query);
			System.out.println("Created database " + dbName);
		}
	}

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Connection connection = null;
		
		Properties connectionProperties = new Properties();
    	connectionProperties.put("user", this.userName);
    	connectionProperties.put("password", this.password);
		
    	
    	System.out.println("Loading driver...");
    	
    	if (this.typeDB.equals("mysql")) {
			Class.forName(driver);
			System.out.println("Driver was load.");
			
			String currentUrlString = "jdbc:" + this.typeDB + "://" + this.serverName
									+ ":" + this.portNumber + "/";
			System.out.println("Connecting to DataBase...");
			connection = DriverManager.getConnection(currentUrlString, connectionProperties);
			connection.setCatalog(dbName);
			System.out.println("Connected to DataBase");
			this.urlString = currentUrlString + this.dbName;
		}

		return connection;
	}

	@Override
	public void dropTables(Connection connection) throws SQLException {
		Statement statement = null;
		
		statement = connection.createStatement();
    	if (this.typeDB.equals("mysql")) {
        	String query = "DROP TABLE IF EXISTS Workers";
        	statement.executeUpdate(query);
        	System.out.println("Table 'workers' is deleted");
        	
        	query = "DROP TABLE IF EXISTS CashRegisters";
        	statement.executeUpdate(query);
        	System.out.println("Table 'CashRegisters' is deleted");
        	
        	query = "DROP TABLE IF EXISTS AutoParts";
        	statement.executeUpdate(query);
        	System.out.println("Table 'AutoParts' is deleted");
        	
        	query = "DROP TABLE IF EXISTS SoldAutoParts";
        	statement.executeUpdate(query);
        	System.out.println("Table 'SoldAutoParts' is deleted");
        	
        	query = "DROP TABLE IF EXISTS Sallaries";
        	statement.executeUpdate(query);
        	System.out.println("Table 'Sallaries' is deleted");
    	}
	}

	@Override
	public void closeConnection(Connection connection) throws SQLException {
		connection.close();
		System.out.println("Connection is closed");
	}

	@Override
	public void createTable(Connection connection, String mysqlFileName) 
			throws FileNotFoundException, SQLException {
		Statement statement = null;
		StringBuilder query = new StringBuilder();
    	Scanner in = null;
    	
    	in = new Scanner(new File(mysqlFileName));
    	
    	String str;
    	while(in.hasNext()) {
    		str = in.nextLine() + "\r\n";
    		query.append(str); 
    	}
    	in.close();
    	
    	statement = connection.createStatement();
    	statement.executeUpdate(query.toString());
    	System.out.println("Table is created");
	}

}
