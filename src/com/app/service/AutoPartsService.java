package com.app.service;

import java.math.BigDecimal;
import java.sql.Connection;

import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.model.AutoPart;
import com.app.model.SoldAutoPart;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public interface AutoPartsService {
	String signIn(Connection connection, String user, String password) throws Exception;

	void provideAccess(String position, MenuItem workers, MenuItem accounting, 
			Button decommissionButton);
	
	void populateAutoParts(TableView<AutoPart> tableViewAutoParts, Connection connection);
	
	void populateSoldAutoParts(TableView<SoldAutoPart> tableViewSoldAutoParts, 
											Connection connection, String workersName);
}
