package com.app.action;
	
import java.sql.Connection;
import java.sql.SQLException;


import com.app.util.impl.AutoPartsStoreUtilImpl;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;


public class SignIn extends Application {
	
	//
	//AutoPartsDaoImpl autoPartsDaoImpl = null;
	
	private Connection connection = null;
	private Stage primaryStage;
	private final String icon = "file:resources/photos/icon.png";
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			this.primaryStage = primaryStage;
			Parent root = FXMLLoader.load(getClass()
					.getResource("signInFXML.fxml"));
			primaryStage.setTitle("Auto-parts Store Accounting");
			primaryStage.setScene(new Scene(root, 400, 400));
			primaryStage.getIcons().add(new Image(icon));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void stop() {
		if(connection !=null) {
			AutoPartsStoreUtilImpl autoPartsStoreUtilImpl = new AutoPartsStoreUtilImpl();
			try {
				autoPartsStoreUtilImpl.dropTables(connection);
				autoPartsStoreUtilImpl.closeConnection(connection);
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("SQL Exception");
				alert.setContentText(e.toString());

				alert.showAndWait();
			}
		}
	}
	
	public String getIconPath() {
		return icon;
	}

	public static void main(String[] args) {
		Application.launch(SignIn.class, args);
		//launch(args);		
	}
	
	
}
