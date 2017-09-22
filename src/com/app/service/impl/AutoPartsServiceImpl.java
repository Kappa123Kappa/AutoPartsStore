package com.app.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

import com.app.action.Main;
import com.app.action.SignIn;
import com.app.control.MainController;
import com.app.control.WindowForAddController;
import com.app.control.WindowForAddWorkerController;
import com.app.control.WindowForDecommissionController;
import com.app.control.WindowForEditController;
import com.app.control.WindowForEditSalaryController;
import com.app.control.WindowForEditWorkerController;
import com.app.control.WindowForSellController;
import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.model.AutoPart;
import com.app.model.Position;
import com.app.model.Salary;
import com.app.model.SoldAutoPart;
import com.app.model.Worker;
import com.app.service.AutoPartsService;
import com.app.util.Language;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AutoPartsServiceImpl implements AutoPartsService {
	
	// signIn
	@Override
	public String signIn(Connection connection, String user, String password)  {
		StringBuilder userBuilder = new StringBuilder(user);
		String firtsName = null;
		String secondName = null;
		int passwordInt = Integer.valueOf(password);
		
		for(int i = 0; i < userBuilder.length(); i++) {
			if(userBuilder.charAt(i) == ' '){
				firtsName = userBuilder.substring(0, i);
				secondName = userBuilder.substring(i + 1, userBuilder.length());
				break;
			}
		}
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM Workers "
					+ "WHERE firstName = '" + firtsName + "' and secondName = '" + secondName
					+ "' and password = '" + passwordInt + "' LIMIT 1;";
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				if(resultSet.getString("firstName").equals(firtsName)
						&& resultSet.getString("secondName").equals(secondName)
						&& resultSet.getString("password").equals(password)) {
					return resultSet.getString("position");
				}
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	// Access
	public void provideAccess(String position, MenuItem workers, 
			MenuItem accounting, Button decommissionButton) {
		switch(position) {
			case "CEO":
				break;
			case "Seller":
				workers.setVisible(false);
				accounting.setVisible(false);
				decommissionButton.setVisible(false);
				break;
			case "Accountant":
				workers.setVisible(false);
				accounting.setVisible(true);
				decommissionButton.setVisible(true);
				break;
			default:
				break;
		}
	}
	
	
	// Change language
	public void setMainWindowLanguage(Language language) {
		new MainController().mainControllerLanguage(language);
	}
	
	
	// Show
	public void showWindowForAddingWorker(Main main, TableView<Worker> tableViewWorkers, 
			Language language, String addWorkerFXMLPath){
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainController.class.getResource(addWorkerFXMLPath));
		try {
			root = (Parent)loader.load();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		Stage stage = new Stage();
		stage.setTitle("Add worker");
		stage.getIcons().add(new Image(new SignIn().getIconPath()));
		stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
		
		WindowForAddWorkerController controller = loader.getController();
		
		controller.setConnection(main.getConnection());
		controller.setStage(stage);
		controller.windowControllerLanguage(language);
	
		controller.fillPositionComboBox();

		
		
		stage.show();
		
		stage.setOnHidden(eh -> {
			populateWorkers(tableViewWorkers, main.getConnection());
		});
	}
	
	public void showWindowForEditingWorker(Main main, TableView<Worker> tableViewWorkers, 
			Language language, String editWorkerFXMLPath) {
		if(tableViewWorkers.getSelectionModel().getSelectedItem() != null) {
			Parent root = null;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainController.class.getResource(editWorkerFXMLPath));
			try {
				root = (Parent)loader.load();
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
			
			Stage stage = new Stage();
			stage.setTitle("Add worker");
			stage.getIcons().add(new Image(new SignIn().getIconPath()));
			stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
			
			WindowForEditWorkerController controller = loader.getController();
			
			controller.setConnection(main.getConnection());
			controller.setStage(stage);
			controller.windowControllerLanguage(language);
			
			
			controller.setWorkerID(tableViewWorkers.getSelectionModel()
												   .getSelectedItem()
												   .getID()
												   .getValue());
			
			controller.setFirstNameTextField(tableViewWorkers.getSelectionModel()
												   .getSelectedItem()
												   .getFirstName()
												   .getValue());
			controller.setSecondNameTextField(tableViewWorkers.getSelectionModel()
												   .getSelectedItem()
												   .getSecondName()
												   .getValue());
			
			controller.fillPositionComboBox();
			
			controller.setPositionComboBox(tableViewWorkers.getSelectionModel()
												   .getSelectedItem()
												   .getPosition()
												   .getValue());
			controller.setInterestStableComboBox(tableViewWorkers.getSelectionModel()
												   .getSelectedItem()
												   .getInterestStable()
												   .getValue());
			controller.setPasswordTextField(tableViewWorkers.getSelectionModel()
												   .getSelectedItem()
												   .getPassword()
												   .getValue());
			
			stage.show();
			
			stage.setOnHidden(eh -> {
				populateWorkers(tableViewWorkers, main.getConnection());
			});
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}
	}
	
	public void showWindowForDismissingWorker(Main main, AutoPartsDaoImpl autoPartsDaoImpl, 
			TableView<Worker> tableViewWorkers, Language language) {
		if(tableViewWorkers.getSelectionModel().getSelectedItem() != null) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			switch(language.getId()) {
				case 1:
					alert.setTitle("Dismiss worker");
					alert.setHeaderText("Confirmation");
					alert.setContentText("Are you sure, that you want to dismiss worker?");
					break;
				case 2:
					alert.setTitle("Звільнення робітника");
					alert.setHeaderText("Підтвердження");
					alert.setContentText("Ви впевненні, що хочете звільнити робітника?");
					break;
				case 3:
					alert.setTitle("Увольнение работника");
					alert.setHeaderText("Подтверждение");
					alert.setContentText("Вы уверены, что хотите уволить работника?");
					break;
				default :
					alert.setTitle("Dismiss worker");
					alert.setHeaderText("Confirmation");
					alert.setContentText("Are you sure, that you want to dismiss worker?");
					break;
					
			}
			

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				autoPartsDaoImpl.deleteWorkerFromWorkers(main.getConnection(), 
						tableViewWorkers.getSelectionModel()
										.getSelectedItem()
										.getID()
										.getValue());
				autoPartsDaoImpl.deleteWorkerFromSallaries(main.getConnection(),
						tableViewWorkers.getSelectionModel()
										.getSelectedItem()
										.getID()
										.getValue());
				
				
				populateWorkers(tableViewWorkers, main.getConnection());
				
			} else {
				alert.close();
			}
			
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}
	}
	
	public void showWindowForSellingAutoPart(Main main, AutoPartsDaoImpl autoPartsDaoImpl, 
			TableView<AutoPart> tableViewAutoParts, Language language, String sellFXMLPath){
		if(tableViewAutoParts.getSelectionModel().getSelectedItem() != null)
		{
			ObservableList<AutoPart> autoPartsList = 
					autoPartsDaoImpl.getAllAutoParts(main.getConnection());
			 
			
			Parent root = null;
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(MainController.class.getResource(sellFXMLPath));
			try {
				 root = (Parent)loader.load();
			} catch (Exception e) {
				 System.out.println("Error: " + e);
			}
			
			Stage stage = new Stage();
			stage.setTitle("Auto-parts Store Accounting");
			stage.getIcons().add(new Image(new SignIn().getIconPath()));
			stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
			
			WindowForSellController controller = loader.getController();
			
			controller.setConnection(main.getConnection());
			
			controller.windowControllerLanguage(language);
			
			controller.setStage(stage);
			
			controller.setWorker(main.getWorkersName());
			
			for(int i = 0; i < autoPartsList.size(); i++) {
				if(autoPartsList.get(i).getID().getValue().intValue() 
						== tableViewAutoParts.getSelectionModel()
								.getSelectedItem().getID().intValue() ) {
					controller.setPrice(autoPartsList.get(i)
													 .getPrice()
													 .getValue()
													 .toString());
				}
			}
			
			
			controller.setAutoPartID(tableViewAutoParts.getSelectionModel()
															  .getSelectedItem()
															  .getID()
															  .getValue());
			
		    controller.setAutoPartNameLabel(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getName()
		    												  .getValue());
		    
		    controller.setAutoPartManufacturerLabel(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getManufacturer()
		    												  .getValue());
		   
		    for(int i = 0; i < autoPartsList.size(); i++) {
		    	 if(autoPartsList.get(i).getName().getValue()
		    			 .equals(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getName()
		    												  .getValue())
		    		&& autoPartsList.get(i).getDescription().getValue()
	    			 	.equals(tableViewAutoParts.getSelectionModel()
							  .getSelectedItem()
							  .getDescription()
							  .getValue())) {
		    		 
				    controller.setAutoPartPriceLabel(tableViewAutoParts.getSelectionModel()
				    										  .getSelectedItem()
				    										  .getPrice()
				    										  .getValue().toString());
		    	 }
		    }
		   
		    
		    controller.setAutoPartDescriptionLabel(tableViewAutoParts.getSelectionModel()
					  										  .getSelectedItem()
					  										  .getDescription()
					  										  .getValue().toString());
		    
		    controller.setAutoPartQuantityComboBox(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getQuantity()
		    												  .getValue());
		    
		    controller.setAutoPartDateLabel(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getDate()
		    												  .getValue());
			
			
			stage.show();
			
			stage.setOnHidden(eh -> {
				populateAutoParts(tableViewAutoParts, main.getConnection());
				//showAvailableAutoPartsActions();
			});
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}
	}

	public void showWindowForAddingAutoPart(Main main, 
			TableView<AutoPart> tableViewAutoParts, Language language, String addFXMLPath) {
		
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainController.class.getResource(addFXMLPath));
		try {
			 root = (Parent)loader.load();
		} catch (Exception e) {
			 System.out.println("Error: " + e);
		}
		
		Stage stage = new Stage();
		stage.setTitle("Auto-parts Store Accounting");
		stage.getIcons().add(new Image(new SignIn().getIconPath()));
		stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
		
		WindowForAddController controller = loader.getController();
		
		controller.setConnection(main.getConnection());
		
		controller.windowControllerLanguage(language);
		
		controller.setPriceTextFieldPromptText("100.00");
		
		controller.setPriceincreaseCoefficientTextFieldPromptText("1.02 = 0.2%");
		
		controller.setStage(stage);
		
		stage.show();
		
		stage.setOnHidden(eh -> {
			populateAutoParts(tableViewAutoParts, main.getConnection());
		});
	}
	
	public void showWindowForEditingAutoPart(Main main, AutoPartsDaoImpl autoPartsDaoImpl,
			TableView<AutoPart> tableViewAutoParts, Language language, String editAutoPartFXMLPath) {
		if(tableViewAutoParts.getSelectionModel().getSelectedItem() != null)
		{
			autoPartsDaoImpl = new AutoPartsDaoImpl();
			ObservableList<AutoPart> autoPartsList = autoPartsDaoImpl.getAllAutoParts(main.getConnection());
			 
			
			Parent root = null;
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(MainController.class.getResource(editAutoPartFXMLPath));
			try {
				 root = (Parent)loader.load();
			} catch (Exception e) {
				 System.out.println("Error: " + e);
			}
			
			Stage stage = new Stage();
			stage.setTitle("Auto-parts Store Accounting");
			stage.getIcons().add(new Image(new SignIn().getIconPath()));
			stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
			
			WindowForEditController controller = loader.getController();
			
			controller.windowControllerLanguage(language);
			
			controller.setConnection(main.getConnection());
			
			controller.setStage(stage);
			
			controller.setAutoPartID(tableViewAutoParts.getSelectionModel()
															  .getSelectedItem()
															  .getID()
															  .getValue());
			
		    controller.setAutoPartNameTextField(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getName()
		    												  .getValue());
		    
		    controller.setAutoPartManufacturerTextField(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getManufacturer()
		    												  .getValue());
		   
		    for(int i = 0; i < autoPartsList.size(); i++) {
		    	 if(autoPartsList.get(i).getName().getValue()
		    			 .equals(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getName()
		    												  .getValue())
		    		&& autoPartsList.get(i).getDescription().getValue()
	    			 	.equals(tableViewAutoParts.getSelectionModel()
							  .getSelectedItem()
							  .getDescription()
							  .getValue())) {
		    		 
				    controller.setAutoPartPriceTextField(autoPartsList.get(i)
				    										  .getPrice()
				    										  .getValue().toString());
		    	 	controller.setAutoPartPriceIncreaseCoefficientTextField(autoPartsList.get(i)
		    	 			.getPriceIncreaseCoefficient().getValue().toString());
		    	 }
		    }
		   
		    
		    controller.setAutoPartDescriptionTextField(tableViewAutoParts.getSelectionModel()
					  										  .getSelectedItem()
					  										  .getDescription()
					  										  .getValue().toString());
		    
		    controller.setAutoPartQuantityTextField(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getQuantity()
		    												  .getValue().toString());
		    
			stage.show();
			
			stage.setOnHidden(eh -> {
				populateAutoParts(tableViewAutoParts, main.getConnection());
			});
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}
	}
	
	public void showWindowForDismissingAutoPart(Main main, AutoPartsDaoImpl autoPartsDaoImpl,
			TableView<AutoPart> tableViewAutoParts, Language language, 
			String decommissionAutoPartFXMLPath) {
		
		if(tableViewAutoParts.getSelectionModel().getSelectedItem() != null)
		{
			ObservableList<AutoPart> autoPartsList 
					= autoPartsDaoImpl.getAllAutoParts(main.getConnection());
			 
			
			Parent root = null;
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(MainController.class
		    		.getResource(decommissionAutoPartFXMLPath));
			try {
				 root = (Parent)loader.load();
			} catch (Exception e) {
				 System.out.println("Error: " + e);
			}
			
			Stage stage = new Stage();
			stage.setTitle("Auto-parts Store Accounting");
			stage.getIcons().add(new Image(new SignIn().getIconPath()));
			stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
			
			WindowForDecommissionController controller = loader.getController();
			
			controller.setConnection(main.getConnection());
			
			controller.windowControllerLanguage(language);
			
			controller.setStage(stage);
			
			for(int i = 0; i < autoPartsList.size(); i++) {
				if(autoPartsList.get(i).getID().getValue().intValue() == tableViewAutoParts.getSelectionModel()
						.getSelectedItem().getID().intValue() ) {
					controller.setPrice(autoPartsList.get(i).getPrice().getValue().toString());
				}
			}
			
			controller.setWorker(main.getWorkersName());
			
			controller.setAutoPartID(tableViewAutoParts.getSelectionModel()
															  .getSelectedItem()
															  .getID()
															  .getValue());
			
		    controller.setAutoPartNameLabel(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getName()
		    												  .getValue());
		    
		    controller.setAutoPartManufacturerLabel(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getManufacturer()
		    												  .getValue());
		   
		    for(int i = 0; i < autoPartsList.size(); i++) {
		    	 if(autoPartsList.get(i).getName().getValue()
		    			 .equals(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getName()
		    												  .getValue())
		    		&& autoPartsList.get(i).getDescription().getValue()
	    			 	.equals(tableViewAutoParts.getSelectionModel()
							  .getSelectedItem()
							  .getDescription()
							  .getValue())) {
		    		 
				    controller.setAutoPartPriceLabel(tableViewAutoParts.getSelectionModel()
				    										  .getSelectedItem()
				    										  .getPrice()
				    										  .getValue().toString());
		    	 }
		    }
		   
		    
		    controller.setAutoPartDescriptionLabel(tableViewAutoParts.getSelectionModel()
					  										  .getSelectedItem()
					  										  .getDescription()
					  										  .getValue().toString());
		    
		    controller.setAutoPartQuantityComboBox(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getQuantity()
		    												  .getValue());
		    
		    controller.setAutoPartDateLabel(tableViewAutoParts.getSelectionModel()
		    												  .getSelectedItem()
		    												  .getDate()
		    												  .getValue());
		    
			stage.show();
			
			stage.setOnHidden(eh -> {
				populateAutoParts(tableViewAutoParts, main.getConnection());
			});
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}
	}
	
	public void showSallary(Main main, AutoPartsDaoImpl autoPartsDaoImpl, 
			TableView<Salary> salaryTableView, DatePicker salaryDatePicker, 
			TextField totalProfitTextField, TextField totalSalaryTextField,
			TextField remainderTextField) {
		if(salaryDatePicker.getValue() != null) {
			
			//sallaryDatePicker.setValue(profitDatePicker.getValue());
			
			ObservableList<SoldAutoPart> autoSoldPartsList = 
					autoPartsDaoImpl.getAllSoldAutoPartsByMonth(main.getConnection(), 
							salaryDatePicker.getValue().getMonthValue());
			
			ObservableList<Salary> allSallaries = 
					autoPartsDaoImpl.getAllSallaries(main.getConnection());
			
			if(autoSoldPartsList.size() > 0) {
				populateSalaries(salaryTableView, autoSoldPartsList, 
						allSallaries, totalProfitTextField, totalSalaryTextField,
						remainderTextField);
				
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Nothing has been sold in this month.");
				alert.setContentText("Choose another month.");

				alert.showAndWait();
			}
			
			
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a date.");
			alert.setContentText("Select a date!");

			alert.showAndWait();
		}
	}
	
	public void showWindowForEditSalary(Main main, AutoPartsDaoImpl autoPartsDaoImpl,
			TableView<Salary> salaryForEditTableView, Language language, 
			String editSalaryFXMLPath) {
		
		if(salaryForEditTableView.getSelectionModel().getSelectedItem() != null)
		{
			Parent root = null;
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(MainController.class
		    		.getResource(editSalaryFXMLPath));
			try {
				 root = (Parent)loader.load();
			} catch (Exception e) {
				 System.out.println("Error: " + e);
			}
			
			Stage stage = new Stage();
			stage.setTitle("Auto-parts Store Accounting");
			stage.getIcons().add(new Image(new SignIn().getIconPath()));
			stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
			
			WindowForEditSalaryController controller = loader.getController();
			
			controller.windowControllerLanguage(language);
			
			controller.setConnection(main.getConnection());
			
			controller.setStage(stage);
			
			controller.setSalaryID(
					salaryForEditTableView.getSelectionModel()
					   			   .getSelectedItem().getID()
					   			   .getValue());
			
			controller.setInterestStableComboBox(
					salaryForEditTableView.getSelectionModel()
								   .getSelectedItem()
								   .getInterestStable()
								   .getValue());
			
			controller.setSalaryCoefficien(
					salaryForEditTableView.getSelectionModel()
		   			   			   .getSelectedItem()
		   			   			   .getSallaryCoefficient()
		   			   			   .getValue());
			
			controller.setSalary(
					salaryForEditTableView.getSelectionModel()
					   			   .getSelectedItem()
					   			   .getSallary()
					   			   .getValue());
			
			stage.show();
			
			stage.setOnHiding(eh -> {
				populateSalariesForEdit(
						salaryForEditTableView, main.getConnection());
			});
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}
		
	}
	
	public void populateWorkers(TableView<Worker> tableViewWorkers, 
			Connection connection) {
		
		tableViewWorkers.getColumns().clear();
		
		TableColumn<Worker, String> firstName = new TableColumn("First Name");
	    TableColumn<Worker, String> secondName  = new TableColumn("Second Name");
	    TableColumn<Worker, String> position = new TableColumn("Position");
	    TableColumn<Worker, String> interestStable = new TableColumn("Interest/Stable");
	    TableColumn<Worker, String> password = new TableColumn("Password");
	    
	    tableViewWorkers.getColumns().addAll(firstName, secondName, position, interestStable,
	    							password);
	    
	    firstName.setPrefWidth(tableViewWorkers.getWidth() / tableViewWorkers.getColumns().size());
	    secondName.setPrefWidth(tableViewWorkers.getWidth() / tableViewWorkers.getColumns().size());
	    position.setPrefWidth(tableViewWorkers.getWidth() / tableViewWorkers.getColumns().size());
	    interestStable.setPrefWidth(tableViewWorkers.getWidth() / tableViewWorkers.getColumns().size());
	    password.setPrefWidth(tableViewWorkers.getWidth() / tableViewWorkers.getColumns().size());
	    
	    firstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
	    secondName.setCellValueFactory(cellData -> cellData.getValue().getSecondName());
	    position.setCellValueFactory(cellData -> cellData.getValue().getPosition());
	    interestStable.setCellValueFactory(cellData -> cellData.getValue().getInterestStable());
	    password.setCellValueFactory(cellData -> cellData.getValue().getPassword());
	
	    
	    tableViewWorkers.setItems(new AutoPartsDaoImpl().getAllWorkers(connection));
	}
	
	@Override
	public void populateAutoParts(TableView<AutoPart> tableViewAutoParts, 
			Connection connection) {
		
		tableViewAutoParts.getItems().clear();
		
		AutoPartsDaoImpl autoPartsDaoImpl = new AutoPartsDaoImpl();
		
		TableColumn<AutoPart, String> name = new TableColumn("Name");
	    TableColumn<AutoPart, String> manufacturer  = new TableColumn("Manufacturer");
	    TableColumn<AutoPart, String> price = new TableColumn("Price in $");
	    TableColumn<AutoPart, String> description = new TableColumn("Description");
	    TableColumn<AutoPart, Integer> quantity = new TableColumn("Quantity");
	    TableColumn<AutoPart, String> date = new TableColumn("Date");

	    tableViewAutoParts.getColumns().addAll(name, manufacturer, price,description, quantity, date);
	    name.setPrefWidth(tableViewAutoParts.getWidth() / tableViewAutoParts.getColumns().size());
	    manufacturer.setPrefWidth(tableViewAutoParts.getWidth() / tableViewAutoParts.getColumns().size());
	    price.setPrefWidth(tableViewAutoParts.getWidth() / tableViewAutoParts.getColumns().size());
	    description.setPrefWidth(tableViewAutoParts.getWidth() / tableViewAutoParts.getColumns().size());
	    quantity.setPrefWidth(tableViewAutoParts.getWidth() / tableViewAutoParts.getColumns().size());
	    date.setPrefWidth(tableViewAutoParts.getWidth() / tableViewAutoParts.getColumns().size());
	     
	     
	     
	    //autoPartsList = getAllAutoParts();
	    name.setCellValueFactory(cellData -> cellData.getValue().getName());
	    manufacturer.setCellValueFactory(cellData -> cellData.getValue().getManufacturer());
	    price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
	    description.setCellValueFactory(cellData -> cellData.getValue().getDescription());
	    quantity.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
	    date.setCellValueFactory(cellData -> cellData.getValue().getDate());
	    
	    tableViewAutoParts.setItems(autoPartsDaoImpl.getAllAutoParts(connection));
	     
	    for(int i = 0; i < tableViewAutoParts.getItems().size(); i++) {
	    	BigDecimal money = new BigDecimal(tableViewAutoParts.getColumns()
	    														.get(2)
	    														.getCellData(i)
	    														.toString()); 
	    	money = multiplication(
	    			 tableViewAutoParts.getItems()
	    			 .get(i)
	    			 .getPriceIncreaseCoefficient()
	    			 .getValue()
	    			 .doubleValue(), money);
	    	tableViewAutoParts.getItems().get(i).setPrice(money.setScale(2, BigDecimal.ROUND_DOWN).toString());
	    	 
	    }
	}
	
	
	@Override
	public void populateSoldAutoParts(TableView<SoldAutoPart> tableViewSoldAutoParts, 
				Connection connection, String workersName) {
		
		 TableColumn<SoldAutoPart, String> worker = new TableColumn("Worker");
		 TableColumn<SoldAutoPart, String> soldDecommission = new TableColumn("Sold/Decommission");
		 TableColumn<SoldAutoPart, String> name = new TableColumn("Name");
	     TableColumn<SoldAutoPart, String> manufacturer  = new TableColumn("Manufacturer");
	     TableColumn<SoldAutoPart, String> price = new TableColumn("Price in $");
	     TableColumn<SoldAutoPart, String> profit = new TableColumn("Profit in $");
	     TableColumn<SoldAutoPart, String> description = new TableColumn("Description");
	     TableColumn<SoldAutoPart, Integer> quantity = new TableColumn("Quantity");
	     TableColumn<SoldAutoPart, String> date = new TableColumn("Date");
	     
	     
	     tableViewSoldAutoParts.getColumns().addAll(worker, soldDecommission, name, manufacturer,
	    		 price, profit, description, quantity, date);
	     
	     
	     AutoPartsDaoImpl autoPartsDaoImpl = new AutoPartsDaoImpl();
	     tableViewSoldAutoParts.setItems(
	    		 autoPartsDaoImpl.getAllSoldAutoPartsOfOneWorker(connection, workersName));
	     
	  
	     worker.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     soldDecommission.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     name.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     manufacturer.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     price.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     profit.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     description.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     quantity.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	     date.setPrefWidth(tableViewSoldAutoParts.getWidth() / tableViewSoldAutoParts.getColumns().size());
	
	     worker.setCellValueFactory(cellData -> cellData.getValue().getWorker());
	     soldDecommission.setCellValueFactory(cellData -> cellData.getValue().getSoldDecommission());
	     name.setCellValueFactory(cellData -> cellData.getValue().getName());
	     manufacturer.setCellValueFactory(cellData -> cellData.getValue().getManufacturer());
	     price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
	     profit.setCellValueFactory(cellData -> cellData.getValue().getProfit());
	     description.setCellValueFactory(cellData -> cellData.getValue().getDescription());
	     quantity.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
	     date.setCellValueFactory(cellData -> cellData.getValue().getDate());
	}
	
	public void populateProfit(TableView<SoldAutoPart> profitTableView, 
			ObservableList<SoldAutoPart> allSoldAutoPats, TextField profitTextField) {
		
	    if(allSoldAutoPats.size() >= 1) {
			BigDecimal priceDecimal = new BigDecimal("0.00");
			
			
			TableColumn<SoldAutoPart, String> worker = new TableColumn("Worker");
			TableColumn<SoldAutoPart, String> soldDecommission = new TableColumn("Sold/Decommission");
			TableColumn<SoldAutoPart, String> name = new TableColumn("Name");
		    TableColumn<SoldAutoPart, String> manufacturer  = new TableColumn("Manufacturer");
		    TableColumn<SoldAutoPart, String> price = new TableColumn("Price in $");
		    TableColumn<SoldAutoPart, String> profit = new TableColumn("Profit in $");
		    TableColumn<SoldAutoPart, String> description = new TableColumn("Description");
		    TableColumn<SoldAutoPart, Integer> quantity = new TableColumn("Quantity");
		    TableColumn<SoldAutoPart, String> date = new TableColumn("Date");
		    
		    
		    profitTableView.getColumns().addAll(worker, soldDecommission, name, manufacturer,
		    		 price, profit, description, quantity, date);
		    
		    profitTableView.setItems(allSoldAutoPats);
		    
		    worker.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    soldDecommission.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    name.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    manufacturer.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    price.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    profit.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    description.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    quantity.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
		    date.setPrefWidth(profitTableView.getWidth() / profitTableView.getColumns().size());
			
		    
		    worker.setCellValueFactory(cellData -> cellData.getValue().getWorker());
		    soldDecommission.setCellValueFactory(cellData -> cellData.getValue().getSoldDecommission());
		    name.setCellValueFactory(cellData -> cellData.getValue().getName());
		    manufacturer.setCellValueFactory(cellData -> cellData.getValue().getManufacturer());
		    price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		    profit.setCellValueFactory(cellData -> cellData.getValue().getProfit());
		    description.setCellValueFactory(cellData -> cellData.getValue().getDescription());
		    quantity.setCellValueFactory(cellData -> cellData.getValue().getQuantity().asObject());
		    date.setCellValueFactory(cellData -> cellData.getValue().getDate());
		    
		    for(int i = 0; i < profitTableView.getItems().size(); i++) {
				priceDecimal = priceDecimal.add(new BigDecimal(profitTableView.getItems().get(i).getProfit().getValue().toString()));
			}
			profitTextField.setText(priceDecimal.toString());
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("Nothing has been sold in this day.");
			alert.setContentText("Choose another day.");

			alert.showAndWait();
		}
	    
	    
	    
	   
	   
	}
	
	public void getSelectedRowForSelling(TableView<AutoPart> tableViewAutoParts, 
			ObservableList<AutoPart> autoPartsList, WindowForSellController controller){
		
		
		for(int i = 0; i < autoPartsList.size(); i++) {
			if(autoPartsList.get(i).getID().getValue().intValue() == tableViewAutoParts.getSelectionModel()
					.getSelectedItem().getID().intValue() ) {
				controller.setPrice(autoPartsList.get(i).getPrice().getValue().toString());
			}
		}
		
		
		controller.setAutoPartID(tableViewAutoParts.getSelectionModel()
														  .getSelectedItem()
														  .getID()
														  .getValue());
		
	    controller.setAutoPartNameLabel(tableViewAutoParts.getSelectionModel()
	    												  .getSelectedItem()
	    												  .getName()
	    												  .getValue());
	    
	    controller.setAutoPartManufacturerLabel(tableViewAutoParts.getSelectionModel()
	    												  .getSelectedItem()
	    												  .getManufacturer()
	    												  .getValue());
	   
	    for(int i = 0; i < autoPartsList.size(); i++) {
	    	 if(autoPartsList.get(i).getName().getValue()
	    			 .equals(tableViewAutoParts.getSelectionModel()
	    												  .getSelectedItem()
	    												  .getName()
	    												  .getValue())
	    		&& autoPartsList.get(i).getDescription().getValue()
    			 	.equals(tableViewAutoParts.getSelectionModel()
						  .getSelectedItem()
						  .getDescription()
						  .getValue())) {
	    		 
			    controller.setAutoPartPriceLabel(tableViewAutoParts.getSelectionModel()
			    										  .getSelectedItem()
			    										  .getPrice()
			    										  .getValue().toString());
	    	 }
	    }
	   
	    
	    controller.setAutoPartDescriptionLabel(tableViewAutoParts.getSelectionModel()
				  										  .getSelectedItem()
				  										  .getDescription()
				  										  .getValue().toString());
	    
	    controller.setAutoPartQuantityComboBox(tableViewAutoParts.getSelectionModel()
	    												  .getSelectedItem()
	    												  .getQuantity()
	    												  .getValue());
	    
	    controller.setAutoPartDateLabel(tableViewAutoParts.getSelectionModel()
	    												  .getSelectedItem()
	    												  .getDate()
	    												  .getValue());
	    
		
	}
	
	
	public void getSelectedRowForEditing(TableView<AutoPart> tableViewAutoParts, 
			ObservableList<AutoPart> autoPartsList, WindowForSellController controller) {
		
	}

	private BigDecimal multiplication(double itemQuantity, BigDecimal itemPrice)
    {
		itemPrice  = itemPrice.multiply(new BigDecimal(itemQuantity));
        return itemPrice;
    }
	
	public String getProfit(Label autoPartPriceLabel, 
				ComboBox<Integer> autoPartQuantityComboBox, String price){

		BigDecimal priceWithCoeficient = new BigDecimal(autoPartPriceLabel.getText())
				.multiply(new BigDecimal(autoPartQuantityComboBox
						.getSelectionModel()
						.getSelectedItem()
						.intValue()));
		
		BigDecimal priceWithoutCoeficient = new BigDecimal(price)
				.multiply(new BigDecimal(autoPartQuantityComboBox
						.getSelectionModel()
						.getSelectedItem()
						.intValue()));
		return (priceWithCoeficient.subtract(priceWithoutCoeficient)).toString();
	}

	
	public void populateSalaries(TableView<Salary> salaryTableView,
			ObservableList<SoldAutoPart> autoSoldPartsList,
			ObservableList<Salary> allSalaries, TextField totalProfitTextField, 
			TextField totalSalaryTextField, TextField remainderTextField) {
		
		
		BigDecimal allProfit = 
				new BigDecimal(autoSoldPartsList.get(0).getProfit().getValue());
		
		for(int i = 1; i < autoSoldPartsList.size(); i++) {
			allProfit = allProfit.add(
					new BigDecimal(autoSoldPartsList.get(i)
													.getProfit()
													.getValue()))
					.setScale(2, BigDecimal.ROUND_DOWN);
		
		}
		

		BigDecimal allSalariesDecimal = new BigDecimal("0.00");
		for(int i = 0; i < allSalaries.size(); i++) {
			
			if(allSalaries.get(i).getPosition().getValue()
					.equals(Position.CEO.toString())
					&& allSalaries.get(i).getInterestStable().getValue()
					.equals("Interest")) {

				allSalaries.get(i).setSallary(
						allProfit.multiply(
								new BigDecimal(allSalaries.get(i)
														   .getSallaryCoefficient()
														   .getValue()))
						.setScale(2, BigDecimal.ROUND_DOWN).toString());
				
				allSalariesDecimal = allSalariesDecimal.add(
						new BigDecimal(allSalaries.get(i).getSallary().getValue()));
				
				allSalaries.get(i).setAllprofit(
						allProfit.setScale(2, BigDecimal.ROUND_DOWN).toString());
			}
			else if(allSalaries.get(i).getPosition().getValue()
					.equals(Position.CEO.toString()) 
					&& allSalaries.get(i).getInterestStable().getValue()
					.equals("Stable")) {
				
				allSalariesDecimal = allSalariesDecimal.add(
						new BigDecimal(allSalaries.get(i).getSallary().getValue()));
				
				BigDecimal allProfitOfOneWorker = new BigDecimal("0.00");
				
				for(int j = 0; j < autoSoldPartsList.size(); j++) {
					if(allSalaries.get(i).getWorker().getValue()
							.equals(autoSoldPartsList.get(j).getWorker().getValue())) {
						
						allProfitOfOneWorker = allProfitOfOneWorker.add(
								new BigDecimal(autoSoldPartsList.get(j)
																.getProfit()
																.getValue()));
					}
				}
				
				allSalaries.get(i).setAllprofit(
						allProfitOfOneWorker.setScale(2, BigDecimal.ROUND_DOWN).toString());
				
			}
			else if(!allSalaries.get(i).getPosition().getValue()
					.equals(Position.CEO.toString()) 
					&& allSalaries.get(i).getInterestStable().getValue()
					.equals("Interest")) {
				
				BigDecimal allProfitOfOneWorker = new BigDecimal("0.00");
				
				for(int j = 0; j < autoSoldPartsList.size(); j++) {
					if(allSalaries.get(i).getWorker().getValue()
							.equals(autoSoldPartsList.get(j).getWorker().getValue())) {
						
						allProfitOfOneWorker = allProfitOfOneWorker.add(
								new BigDecimal(autoSoldPartsList.get(j)
																.getProfit()
																.getValue()));
					}
				}
				
				allSalaries.get(i).setAllprofit(
						allProfitOfOneWorker.setScale(2, BigDecimal.ROUND_DOWN).toString());
				
				allSalaries.get(i).setSallary(allProfitOfOneWorker.multiply(
						new BigDecimal(allSalaries.get(i)
												   .getSallaryCoefficient()
												   .getValue()))
						.setScale(2, BigDecimal.ROUND_DOWN).toString().toString());
				
				allSalariesDecimal = allSalariesDecimal.add(
						new BigDecimal(allSalaries.get(i).getSallary().getValue()));
			}
			else if(!allSalaries.get(i).getPosition().getValue()
					.equals(Position.CEO.toString()) 
					&& allSalaries.get(i).getInterestStable().getValue()
					.equals("Stable")) {
				
				allSalariesDecimal = allSalariesDecimal.add(
						new BigDecimal(allSalaries.get(i).getSallary().getValue()));
				
				BigDecimal allProfitOfOneWorker = new BigDecimal("0.00");
				
				for(int j = 0; j < autoSoldPartsList.size(); j++) {
					if(allSalaries.get(i).getWorker().getValue()
							.equals(autoSoldPartsList.get(j).getWorker().getValue())) {
						
						allProfitOfOneWorker = allProfitOfOneWorker.add(
								new BigDecimal(autoSoldPartsList.get(j)
																.getProfit()
																.getValue()));
					}
				}
				
				allSalaries.get(i).setAllprofit(
						allProfitOfOneWorker.setScale(2, BigDecimal.ROUND_DOWN).toString());
				
			}
		}
		
		System.out.println(allSalariesDecimal.toString());
		
		totalProfitTextField.setText(allProfit.setScale(2, BigDecimal.ROUND_DOWN).toString());
		totalSalaryTextField.setText(allSalariesDecimal.setScale(2, BigDecimal.ROUND_DOWN).toString());
		remainderTextField.setText((
				allProfit.subtract(allSalariesDecimal)).setScale(2, BigDecimal.ROUND_DOWN)
				.toString());

		TableColumn<Salary, String> worker = new TableColumn("Worker");
	    TableColumn<Salary, String> position = new TableColumn("Position");
	    TableColumn<Salary, String> interestStable = new TableColumn("Interest/Stable");
	    TableColumn<Salary, String> sallaryCoefficient = new TableColumn("Sallary coefficient");
	    TableColumn<Salary, String> sallary = new TableColumn("Sallary in $");
	    TableColumn<Salary, String> profit = new TableColumn("Profit in $");
	    
	    salaryTableView.getColumns().addAll(worker, position, interestStable, 
	    		sallaryCoefficient, sallary, profit);
	    
	    worker.setPrefWidth(salaryTableView.getWidth() / salaryTableView.getColumns().size());
	    position.setPrefWidth(salaryTableView.getWidth() / salaryTableView.getColumns().size());
	    interestStable.setPrefWidth(salaryTableView.getWidth() / salaryTableView.getColumns().size());
	    sallaryCoefficient.setPrefWidth(salaryTableView.getWidth() / salaryTableView.getColumns().size());
	    sallary.setPrefWidth(salaryTableView.getWidth() / salaryTableView.getColumns().size());
	    profit.setPrefWidth(salaryTableView.getWidth() / salaryTableView.getColumns().size());
	    
	    worker.setCellValueFactory(cellData -> cellData.getValue().getWorker());
	    position.setCellValueFactory(cellData -> cellData.getValue().getPosition());
	    interestStable.setCellValueFactory(cellData -> cellData.getValue().getInterestStable());
	    sallaryCoefficient.setCellValueFactory(cellData -> cellData.getValue().getSallaryCoefficient());
	    sallary.setCellValueFactory(cellData -> cellData.getValue().getSallary());
	    profit.setCellValueFactory(cellData -> cellData.getValue().getAllprofit());
	    
	    salaryTableView.setItems(allSalaries);  
	}

	public void populateSalariesForEdit(TableView<Salary> salaryForEditTableView,
			Connection connection) {
		
		salaryForEditTableView.getItems().clear();
		
		ObservableList<Salary> allSalaries = 
				new AutoPartsDaoImpl().getAllSallaries(connection);
		
		TableColumn<Salary, String> worker = new TableColumn("Worker");
	    TableColumn<Salary, String> position = new TableColumn("Position");
	    TableColumn<Salary, String> interestStable = new TableColumn("Interest/Stable");
	    TableColumn<Salary, String> sallaryCoefficient = new TableColumn("Sallary coefficient");
	    TableColumn<Salary, String> sallary = new TableColumn("Sallary in $");
	    
	    salaryForEditTableView.getColumns().addAll(worker, position, interestStable, 
	    		sallaryCoefficient, sallary);
	    
	    worker.setPrefWidth(salaryForEditTableView.getWidth() / salaryForEditTableView.getColumns().size());
	    position.setPrefWidth(salaryForEditTableView.getWidth() / salaryForEditTableView.getColumns().size());
	    interestStable.setPrefWidth(salaryForEditTableView.getWidth() / salaryForEditTableView.getColumns().size());
	    sallaryCoefficient.setPrefWidth(salaryForEditTableView.getWidth() / salaryForEditTableView.getColumns().size());
	    sallary.setPrefWidth(salaryForEditTableView.getWidth() / salaryForEditTableView.getColumns().size());
	    
	    worker.setCellValueFactory(cellData -> cellData.getValue().getWorker());
	    position.setCellValueFactory(cellData -> cellData.getValue().getPosition());
	    interestStable.setCellValueFactory(cellData -> cellData.getValue().getInterestStable());
	    sallaryCoefficient.setCellValueFactory(cellData -> cellData.getValue().getSallaryCoefficient());
	    sallary.setCellValueFactory(cellData -> cellData.getValue().getSallary());
	    
	    salaryForEditTableView.setItems(allSalaries);
	}




}
