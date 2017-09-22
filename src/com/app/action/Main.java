package com.app.action;

import java.awt.MenuBar;
import java.io.IOException;
import java.sql.*;

import javax.swing.text.Position;

import com.app.control.MainController;
import com.app.control.SignInController;
import com.app.control.WindowForDecommissionController;
import com.app.service.impl.AutoPartsServiceImpl;
import com.app.util.Language;
import com.app.util.impl.AutoPartsStoreUtilImpl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main {

	private static Connection connection;
	private static String name;
	private static Language language;
	private static String position;
	

	public Main() {
	};

	public Main(Connection connection, AutoPartsStoreUtilImpl autoPartsStoreUtilImpl, 
			String name, String position, Language language)
			throws IOException {

		this.connection = connection;
		this.name = name;
		this.language = language;
		this.position = position;
		
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("mainFXML.fxml"));
		try {
			root = (Parent) loader.load();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		Stage stage = new Stage();
		stage.setTitle("Auto-parts Store Accounting");
		stage.getIcons().add(new Image(new SignIn().getIconPath()));
		stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));

		MainController controller = loader.getController();
		controller.setStage(stage);
		controller.setWorkersLabel();
		controller.mainControllerLanguage(language);
		controller.setPosition(this.position);
		
		stage.show();

		stage.setOnHiding(event -> {
			if (connection != null) {
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
			stage.close();
			this.position = null;

			try {

				Parent rootSigIn = null;
				FXMLLoader loaderSigIn = new FXMLLoader();
				loaderSigIn.setLocation(getClass().getResource("signInFXML.fxml"));
				try {
					rootSigIn = (Parent) loaderSigIn.load();
				} catch (Exception e) {
					System.out.println("Error: " + e);
				}

				Stage stageSignIn = new Stage();
				stageSignIn.setTitle("Auto-parts Store Accounting");
				stageSignIn.setScene(new Scene(rootSigIn, 400, 400));
				stageSignIn.getIcons().add(new Image(new SignIn().getIconPath()));

				SignInController signInController = loaderSigIn.getController();

				signInController.changeLanguage(language);

				stageSignIn.show();
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
	}

	public Connection getConnection() {
		return connection;
	}

	public String getWorkersName() {
		return name;
	}

	public Language getLanguage() {
		return language;
	}
	
	public String getPosition() {
		return position;
	}
	
	
	public Parent changeLanguage(String language) {
		Parent root = null;
		try {
			switch (language) {
			case "English":
				root = FXMLLoader.load(getClass().getResource("mainENFXML.fxml"));
				break;
			case "Українська":
				root = FXMLLoader.load(getClass().getResource("mainENFXML.fxml"));
				break;

			case "Русский":
				root = FXMLLoader.load(getClass().getResource("mainENFXML.fxml"));
				break;
			default:
				root = FXMLLoader.load(getClass().getResource("mainENFXML.fxml"));
				break;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return root;
	}
}
