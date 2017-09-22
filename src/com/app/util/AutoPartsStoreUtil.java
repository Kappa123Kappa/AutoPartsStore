package com.app.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;

public interface AutoPartsStoreUtil {
	
	void setProperties(String fileName) throws InvalidPropertiesFormatException, IOException;
	void createDataBase(Connection connection) throws SQLException;
	Connection getConnection() throws ClassNotFoundException, SQLException;
	
	void dropTables(Connection connection) throws SQLException;
	void closeConnection(Connection connection) throws SQLException;
	
	void createTable(Connection connection, String mysqlFileName) 
			throws FileNotFoundException, SQLException;
	
}
