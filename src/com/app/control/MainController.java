package com.app.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import com.app.action.Main;
import com.app.action.SignIn;
import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.model.AutoPart;
import com.app.model.Position;
import com.app.model.Salary;
import com.app.model.SoldAutoPart;
import com.app.model.Worker;
import com.app.service.impl.AutoPartsServiceImpl;
import com.app.util.Language;
import com.app.model.Position;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MainController {
	
	private Main main = new Main();
	private AutoPartsDaoImpl autoPartsDaoImpl = new AutoPartsDaoImpl();
	private AutoPartsServiceImpl autoPartsServiceImpl = new AutoPartsServiceImpl();
	private Stage stage;
	private Language language = main.getLanguage();
	private final String addWorkerFXMLPath= "../action/addWorkerFXML.fxml";
	private final String editWorkerFXMLPath = "../action/editWorkerFXML.fxml";
	private final String sellFXMLPath = "../action/sellFXML.fxml";
	private final String addAutoPartFXMLPath = "../action/addFXML.fxml";
	private final String editAutoPartFXMLPath = "../action/editFXML.fxml";
	private final String decommissionAutoPartFXMLPath = "../action/decommissionFXML.fxml";
	private final String editSalaryFXMLPath = "../action/editSalaryFXML.fxml";
	
	// Top
	@FXML private Menu dataBaseMenu;
	@FXML private MenuItem workers;
	@FXML private MenuItem availableAutoPartsMenuItem;
	@FXML private MenuItem soldAutoPartsMenuItem;
	@FXML private MenuItem accounting;
	
	
	@FXML private Menu windowMenu;
	@FXML private Menu chooseLanguageMenu;
	@FXML private MenuItem enMenuItem;
	@FXML private MenuItem uaMenuItem;
	@FXML private MenuItem ruMenuItem;
	
	@FXML private Menu helpMenu;
	@FXML private MenuItem aboutMenuItem;
	
	
	// Left
	@FXML private AnchorPane anchorPaneLeft;
	@FXML private Button sellButton;
	@FXML private Button addButton;
	@FXML private Button editButton;
	@FXML private Button decommissionButton;
	
	@FXML private Label labelTotalPrice;
	@FXML private TextField totalCostTextField;
	
	
	
	// Center
	@FXML private AnchorPane anchorPaneCenter;
	@FXML private TableView<Worker> tableViewWorkers;
	@FXML private TableView<AutoPart> tableViewAutoParts;
	@FXML private TableView<SoldAutoPart> tableViewSoldAutoParts;
	@FXML private ScrollPane scrollPaneCenter;
	
	@FXML private Label nameLabel;
	@FXML private Label manufacturerLabel;
	@FXML private Label priceLabel;
	@FXML private Label descriptionLabel;
	@FXML private Label quantityLabel;
	@FXML private Label dateLabel;
	
	@FXML private GridPane centerGridPane;
	@FXML private ComboBox<String> comboBoxName;
	@FXML private ComboBox<String> comboBoxOfManufacturer;
	@FXML private ComboBox<String> comboBoxOfPrice;
	@FXML private ComboBox<String> comboBoxOfDescription;
	@FXML private ComboBox<String> comboBoxOfQuantity;
	@FXML private ComboBox<String> comboBoxOfDate;
	
	
	// Accounting
	@FXML private TabPane tabPane;
	@FXML private Rectangle profitRectangle;
	
	@FXML private Tab profitTab;
	@FXML private Label choseDateProfitLabel;
	@FXML private Label selectProfitLabel;
	@FXML private Label totalProfitLabel;
	@FXML private DatePicker profitDatePicker;
	@FXML private Button byDayButton;
	@FXML private Button byMonthButton;
	@FXML private Button byYearButton;
	@FXML private TableView<SoldAutoPart> profitTableView;
	@FXML private TextField profitTextField;
	
	
	
	@FXML private Tab salaryTab;
	@FXML private Rectangle salaryRectangle;
	@FXML private Label choseDateSalaryLabel;
	@FXML private DatePicker salaryDatePicker;
	@FXML private TableView<Salary> salaryTableView;
	
	@FXML private Button showSalaryBotton;
	@FXML private TextField totalProfitTextField;
	@FXML private TextField totalSalaryTextField;
	@FXML private TextField remainderTextField;
	@FXML private Label profitLabel;
	@FXML private Label sallaryLabel;
	@FXML private Label remainderLabel;
	
	
	
	@FXML private Tab editSalaryTab;
	@FXML private TableView<Salary> salaryForEditTableView;
	@FXML private Button editSalaryBotton;
	
	
	// Right
	@FXML private AnchorPane anchorPaneRight;
	@FXML private Label workerLabel;
	@FXML private Button quitButton;
	
	@FXML private Button addWorker;
	@FXML private Button editWorker;
	@FXML private Button dismissWorker;
	
	
		
	public void mainControllerLanguage(Language language) {
		
		this.language = language;
		switch(language.getId()){
    		case 1 :
	        	dataBaseMenu.setText("Data Base");
	        	workers.setText("Workers");
	        	availableAutoPartsMenuItem.setText("Available Auto Parts");
	        	soldAutoPartsMenuItem.setText("Sold Auto Parts");
	        	accounting.setText("Accounting");
	        	
	        	windowMenu.setText("Window");
	        	chooseLanguageMenu.setText("Choose Language");
	        	enMenuItem.setText("English");
	        	uaMenuItem.setText("Українська");
	        	ruMenuItem.setText("Русский");
	        	
	        	helpMenu.setText("Help");
	        	aboutMenuItem.setText("About");

	        	sellButton.setText("Sell Auto Part");
	        	addButton.setText("Add Auto Part");
	        	editButton.setText("Edit Auto Part");
	        	decommissionButton.setText("Decommission Auto Part");
	        	
	        	labelTotalPrice.setText("Total price:");
	        	
	        	nameLabel.setText("Name");
	        	manufacturerLabel.setText("Manufacturer");
	        	priceLabel.setText("Price in $");
	        	descriptionLabel.setText("Description");
	        	quantityLabel.setText("Quantity");
	        	dateLabel.setText("Date");
	        	
	        	
	        	
	        	profitTab.setText("Profit");
	        	choseDateProfitLabel.setText("Chose date:");
	        	selectProfitLabel.setText("Select profit by:");
	        	totalProfitLabel.setText("Total profit in $:");
	        	byDayButton.setText("Day");
	        	byMonthButton.setText("Month");
	        	byYearButton.setText("Year");
	        	
	        	salaryTab.setText("Salary");
	        	choseDateSalaryLabel.setText("Chose date:");
	        	showSalaryBotton.setText("Show month salaries");
	        	
	        	profitLabel.setText("Total profit in $:");
	        	sallaryLabel.setText("Sum of salaries in $:");
	        	remainderLabel.setText("Remainder in $:");
	        	
	        	editSalaryTab.setText("Edit salaries");
	        	editSalaryBotton.setText("Edit salary");
	        	
	        	
	        	addWorker.setText("Add worker");
	        	editWorker.setText("Edit worker");
	        	dismissWorker.setText("Dismiss worker");
	        	quitButton.setText("Quit");
	        	
	        	break;
	        	
	        case 2 :
	        	dataBaseMenu.setText("База даних");
	        	workers.setText("Робітники");
	        	availableAutoPartsMenuItem.setText("Доступні автозапчастини");
	        	soldAutoPartsMenuItem.setText("Продані автозапчастини");
	        	accounting.setText("Бухгалтерія");
	        	
	        	windowMenu.setText("Вікно");
	        	chooseLanguageMenu.setText("Обрати мову");
	        	enMenuItem.setText("English");
	        	uaMenuItem.setText("Українська");
	        	ruMenuItem.setText("Русский");
	        	
	        	helpMenu.setText("Допомога");
	        	aboutMenuItem.setText("Про програму");

	        	sellButton.setText("Продати автозапчастини");
	        	addButton.setText("Додати автозапчастини");
	        	editButton.setText("Змінити автозапчастини");
	        	decommissionButton.setText("Списати автозапчастини");
	        	
	        	labelTotalPrice.setText("Загальна ціна:");
	        	
	        	nameLabel.setText("Назва");
	        	manufacturerLabel.setText("Виробник");
	        	priceLabel.setText("Ціна у $");
	        	descriptionLabel.setText("Опис");
	        	quantityLabel.setText("Кількість");
	        	dateLabel.setText("Дата");
	        	
	        	
	        	
	        	profitTab.setText("Прибуток");
	        	choseDateProfitLabel.setText("Оберіть дату:");
	        	selectProfitLabel.setText("Оберить прибуток за:");
	        	totalProfitLabel.setText("Заг. прибуток у $:");
	        	byDayButton.setText("День");
	        	byMonthButton.setText("Місяць");
	        	byYearButton.setText("Рік");
	        	
	        	salaryTab.setText("Зарплата");
	        	choseDateSalaryLabel.setText("Оберіть дату:");
	        	showSalaryBotton.setText("Показати місячну зарплату");
	        	
	        	profitLabel.setText("Загальний прибуток у $:");
	        	sallaryLabel.setText("Сума зарплат у $:");
	        	remainderLabel.setText("Залишок у $:");
	        	
	        	editSalaryTab.setText("Змінити зарплати");
	        	editSalaryBotton.setText("Змінити зарплату");
	        	
	        	
	        	addWorker.setText("Додати робітника");
	        	editWorker.setText("Змінти робітника");
	        	dismissWorker.setText("Звільнити робітника");
	        	quitButton.setText("Вийти");
	        	
	        	break;
	        	
	        case 3 :
	        	dataBaseMenu.setText("База данных");
	        	workers.setText("Работники");
	        	availableAutoPartsMenuItem.setText("Доступные автозапчасти");
	        	soldAutoPartsMenuItem.setText("Проданные автозапчасти");
	        	accounting.setText("Бухгалтерия");
	        	
	        	windowMenu.setText("Окно");
	        	chooseLanguageMenu.setText("Выбрать язык");
	        	enMenuItem.setText("English");
	        	uaMenuItem.setText("Українська");
	        	ruMenuItem.setText("Русский");
	        	
	        	helpMenu.setText("Помощь");
	        	aboutMenuItem.setText("Об програме");

	        	sellButton.setText("Продать автозапчасти");
	        	addButton.setText("Добавить автозапчасти");
	        	editButton.setText("Изменить автозапчасти");
	        	decommissionButton.setText("Списать автозапчасти");
	        	
	        	labelTotalPrice.setText("Общая цена:");
	        	
	        	nameLabel.setText("Название");
	        	manufacturerLabel.setText("Производитель");
	        	priceLabel.setText("Цена в $");
	        	descriptionLabel.setText("Описание");
	        	quantityLabel.setText("Количество");
	        	dateLabel.setText("Дата");
	        	
	        	
	        	
	        	profitTab.setText("Прибыль");
	        	choseDateProfitLabel.setText("Выберите дату:");
	        	selectProfitLabel.setText("Выберите прибыль за:");
	        	totalProfitLabel.setText("Общая прибыль в $:");
	        	byDayButton.setText("День");
	        	byMonthButton.setText("Месяц");
	        	byYearButton.setText("Год");
	        	
	        	salaryTab.setText("Зарплата");
	        	choseDateSalaryLabel.setText("Выберите дату:");
	        	showSalaryBotton.setText("Показать месячную зарплату");
	        	
	        	profitLabel.setText("Общая прибыль в $:");
	        	sallaryLabel.setText("Сумма зарплат в $:");
	        	remainderLabel.setText("Остаток в $:");
	        	
	        	editSalaryTab.setText("Сменить зарплаты");
	        	editSalaryBotton.setText("Сменить зарплату");
	        	
	        	addWorker.setText("Добавить работника");
	        	editWorker.setText("Изменить работника");
	        	dismissWorker.setText("Уволить работника");
	        	quitButton.setText("Выйти");
	        	
	        	break;
	        	
	        default:
	        	dataBaseMenu.setText("Data Base");
	        	workers.setText("Workers");
	        	availableAutoPartsMenuItem.setText("Available Auto Parts");
	        	soldAutoPartsMenuItem.setText("Sold Auto Parts");
	        	accounting.setText("Accounting");
	        	
	        	windowMenu.setText("Window");
	        	chooseLanguageMenu.setText("Choose Language");
	        	enMenuItem.setText("English");
	        	uaMenuItem.setText("Українська");
	        	ruMenuItem.setText("Русский");
	        	
	        	helpMenu.setText("Help");
	        	aboutMenuItem.setText("About");

	        	sellButton.setText("Sell Auto Part");
	        	addButton.setText("Add Auto Part");
	        	editButton.setText("Edit Auto Part");
	        	decommissionButton.setText("Decommission Auto Part");
	        	
	        	labelTotalPrice.setText("Total price:");
	        	
	        	nameLabel.setText("Name");
	        	manufacturerLabel.setText("Manufacturer");
	        	priceLabel.setText("Price in $");
	        	descriptionLabel.setText("Description");
	        	quantityLabel.setText("Quantity");
	        	dateLabel.setText("Date");
	        	
	        	
	        	
	        	profitTab.setText("Profit");
	        	choseDateProfitLabel.setText("Chose date:");
	        	selectProfitLabel.setText("Select profit by:");
	        	totalProfitLabel.setText("Total profit in $:");
	        	byDayButton.setText("Day");
	        	byMonthButton.setText("Month");
	        	byYearButton.setText("Year");
	        	
	        	salaryTab.setText("Salary");
	        	showSalaryBotton.setText("Show month salary");
	        	
	        	profitLabel.setText("Total profit in $:");
	        	sallaryLabel.setText("Sum of salaries in $:");
	        	remainderLabel.setText("Remainder in $:");
	        	
	        	editSalaryTab.setText("Edit salary");
	        	editSalaryBotton.setText("Edit salary");
	        	
	        	
	        	addWorker.setText("Add worker");
	        	editWorker.setText("Edit worker");
	        	dismissWorker.setText("Dismiss worker");
	        	quitButton.setText("Quit");
	        	quitButton.setText("Quit");
	        	
	        	break;
		}
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setWorkersLabel() {
		workerLabel.setText(main.getWorkersName());
	}
	
	public void setPosition(String position) {
		autoPartsServiceImpl.provideAccess(position, workers, accounting, 
				decommissionButton);
	}
	
	@FXML private void showAllWorkers() {
		
		// Visible
		// Center
		tableViewWorkers.setVisible(true);
		anchorPaneCenter.setVisible(true);
		
		// Right
		addWorker.setVisible(true);
		editWorker.setVisible(true);
		dismissWorker.setVisible(true);
		
				
		// Not visible
		// Center
		tableViewSoldAutoParts.setVisible(false);
		tableViewAutoParts.setVisible(false);
		scrollPaneCenter.setVisible(false);
		tabPane.setVisible(false);
				 
		// Left
		anchorPaneLeft.setVisible(false);
		
		
		tableViewWorkers.getColumns().clear();
		autoPartsServiceImpl.populateWorkers(tableViewWorkers,main.getConnection());
	}
	
	@FXML private void addWorker() {
		autoPartsServiceImpl.showWindowForAddingWorker(main, tableViewWorkers, 
					language, addWorkerFXMLPath);
		/*Parent root = null;
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
			showAllWorkers();
		});*/
	}
	
	@FXML private void editWorker() {
		autoPartsServiceImpl.showWindowForEditingWorker(main, tableViewWorkers, language, 
				editWorkerFXMLPath);
		/*if(tableViewWorkers.getSelectionModel().getSelectedItem() != null) {
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
				showAllWorkers();
			});
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}
		*/
	}
	
	@FXML private void dismissWorker() {
		
		autoPartsServiceImpl.showWindowForDismissingWorker(main, autoPartsDaoImpl, 
				tableViewWorkers, language);
		/*if(tableViewWorkers.getSelectionModel().getSelectedItem() != null) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Dismiss worker");
			alert.setHeaderText("Confirmation");
			alert.setContentText("Are you sure, that you want to dismiss worker?");

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
				showAllWorkers();
			} else {
				alert.close();
			}
			
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}*/
	}
	
	
	
	@FXML private void showAvailableAutoPartsActions(){
		
		 /*
		  *  Visible
		  */
		
		 // Center
	     anchorPaneCenter.setVisible(true);
		 tableViewAutoParts.setVisible(true);
		 
		 
		 // Left
		 anchorPaneLeft.setVisible(true);
		
		 
		 /*
		  *  Not visible
		  */
		 
		 // Center
		 tableViewWorkers.setVisible(false);
		 tableViewSoldAutoParts.setVisible(false);
		 scrollPaneCenter.setVisible(false);
		 tabPane.setVisible(false);
		 
		 // Left
		 labelTotalPrice.setVisible(false);
		 totalCostTextField.setVisible(false);
		 

		 // Right
		 addWorker.setVisible(false);
		 editWorker.setVisible(false);
		 dismissWorker.setVisible(false);
		 
		 
		 tableViewAutoParts.getColumns().clear();
		
		 //autoPartsServiceImpl = new AutoPartsServiceImpl();
		 
		 autoPartsServiceImpl.populateAutoParts(tableViewAutoParts, main.getConnection());
	 }
	 
	@FXML private void showSellWindow() {
		autoPartsServiceImpl.showWindowForSellingAutoPart(main, autoPartsDaoImpl, tableViewAutoParts, language, 
				sellFXMLPath);
		/*if(tableViewAutoParts.getSelectionModel().getSelectedItem() != null)
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
			
			
			//autoPartsServiceImpl.getSelectedRowForSelling(tableViewAutoParts, 
		    //	autoPartsList, controller);
			
			stage.show();
			
			stage.setOnHidden(eh -> {
				showAvailableAutoPartsActions();
			});
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}*/
	}
	
	@FXML private void showAddWindow() {
		autoPartsServiceImpl.showWindowForAddingAutoPart(main, tableViewAutoParts, 
				language, addAutoPartFXMLPath);
		/*Parent root = null;
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainController.class.getResource(addAutoPartFXMLPath));
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
			showAvailableAutoPartsActions();
		});*/
	}
	
	@FXML private void showEditWindow() {
		autoPartsServiceImpl.showWindowForEditingAutoPart(main, autoPartsDaoImpl, 
				tableViewAutoParts, language, editAutoPartFXMLPath);
		/*if(tableViewAutoParts.getSelectionModel().getSelectedItem() != null)
		{
			autoPartsDaoImpl = new AutoPartsDaoImpl();
			ObservableList<AutoPart> autoPartsList = autoPartsDaoImpl.getAllAutoParts(main.getConnection());
			 
			
			Parent root = null;
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(MainController.class.getResource("../action/editFXML.fxml"));
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
				showAvailableAutoPartsActions();
			});
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}*/
	}
	
	@FXML private void showDecomissionWindow() {

		autoPartsServiceImpl.showWindowForDismissingAutoPart(main, autoPartsDaoImpl,
				tableViewAutoParts, language, decommissionAutoPartFXMLPath);
		/*if(tableViewAutoParts.getSelectionModel().getSelectedItem() != null)
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
				showAvailableAutoPartsActions();
			});
			
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}*/
	}

	
	
	@FXML private void showSoldAutoPartsActions(){
		 
		 // Visible
		 // Center
		 anchorPaneCenter.setVisible(true);
		 tableViewSoldAutoParts.setVisible(true);
		
		 // Not visible
		 // Center
		 tableViewWorkers.setVisible(false);
		 tableViewAutoParts.setVisible(false);
		 scrollPaneCenter.setVisible(false);
		 tabPane.setVisible(false);
		 
		 // Left
		 anchorPaneLeft.setVisible(false);
		 
		 
		 tableViewSoldAutoParts.getColumns().clear();
		 		 
		 autoPartsServiceImpl.populateSoldAutoParts(tableViewSoldAutoParts, 
				 					main.getConnection(), main.getWorkersName());
		  
		 // Right
		 addWorker.setVisible(false);
		 editWorker.setVisible(false);
		 dismissWorker.setVisible(false);
	}
	
	
	
	
	@FXML private void showAccounting() {
		
		// Visible
		// Center
		tabPane.setVisible(true);
		
		// Not visible
		// Center
		tableViewWorkers.setVisible(false);
		tableViewAutoParts.setVisible(false);
		scrollPaneCenter.setVisible(false);
		tableViewSoldAutoParts.setVisible(false);

				 
		// Left
		anchorPaneLeft.setVisible(false);
		
		// Right
		addWorker.setVisible(false);
		editWorker.setVisible(false);
		dismissWorker.setVisible(false);
		
		
		profitRectangle.widthProperty().bind(tabPane.widthProperty());
		profitRectangle.heightProperty().bind(tabPane.heightProperty());
		
		salaryRectangle.widthProperty().bind(tabPane.widthProperty());
		salaryRectangle.heightProperty().bind(tabPane.heightProperty());
		
		salaryForEditTableView.getItems().clear();
		autoPartsServiceImpl.populateSalariesForEdit(
				salaryForEditTableView, main.getConnection());
		
	}
	
	@FXML private void showProfitByDay() {
		profitTableView.getItems().clear();
		
		if(profitDatePicker.getValue() != null) {
			
			profitTableView.getItems().clear();
			
			ObservableList<SoldAutoPart> allSoldAutoPats =
					autoPartsDaoImpl.getAllSoldAutoPartsByDay(main.getConnection(), 
							profitDatePicker.getValue().getDayOfMonth());

			autoPartsServiceImpl.populateProfit(profitTableView, allSoldAutoPats, profitTextField);
			
			/*if(allSoldAutoPats.size() >= 1) {
				BigDecimal price = new BigDecimal("0.00");
				for(int i = 0; i < profitTableView.getItems().size(); i++) {
					price = price.add(new BigDecimal(profitTableView.getItems().get(i).getProfit().getValue().toString()));
				}
				profitTextField.setText(price.toString());
				autoPartsServiceImpl.populateProfit(profitTableView, allSoldAutoPats, profitTextField);
			}
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Nothing has been sold in this day.");
				alert.setContentText("Choose another day.");

				alert.showAndWait();
			}*/
		} else {
			profitTextField.setText("0.00");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a date.");
			alert.setContentText("Select a date!");

			alert.showAndWait();
		}
		
	}
	
	@FXML private void showProfitByMonth() {
		profitTableView.getItems().clear();
		
		if(profitDatePicker.getValue() != null) {
			
			profitTableView.getItems().clear();
			
			ObservableList<SoldAutoPart> allSoldAutoPats =
					autoPartsDaoImpl.getAllSoldAutoPartsByMonth(main.getConnection(), 
							profitDatePicker.getValue().getMonth().getValue());
			
			autoPartsServiceImpl.populateProfit(profitTableView, allSoldAutoPats, profitTextField);
			/*if(allSoldAutoPats.size() >= 1) {
				BigDecimal price = new BigDecimal("0.00");
				
				for(int i = 0; i < profitTableView.getItems().size(); i++) {
					price = price.add(new BigDecimal(profitTableView.getItems().get(i).getProfit().getValue().toString()));
				}
				
				profitTextField.setText(price.toString());
				autoPartsServiceImpl.populateProfit(profitTableView, allSoldAutoPats);
				
			} else {
				profitTextField.setText("0.00");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Nothing has been sold in this month.");
				alert.setContentText("Choose another month.");

				alert.showAndWait();
			}*/
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a date.");
			alert.setContentText("Select a date!");

			alert.showAndWait();
		}
		
	}
	
	@FXML private void showProfitByYear() {
		profitTableView.getItems().clear();
		
		if(profitDatePicker.getValue() != null) {
			
			profitTableView.getItems().clear();
			
			ObservableList<SoldAutoPart> allSoldAutoPats =
					autoPartsDaoImpl.getAllSoldAutoPartsByYear(main.getConnection(), 
							profitDatePicker.getValue().getYear());
			
			autoPartsServiceImpl.populateProfit(profitTableView, allSoldAutoPats, profitTextField);
			
			/*if(allSoldAutoPats.size() >= 1) {
				BigDecimal price = new BigDecimal("0.0");
				
				for(int i = 0; i < profitTableView.getItems().size(); i++) {
					price = price.add(new BigDecimal(profitTableView.getItems().get(i).getProfit().getValue().toString()));
				}
				profitTextField.setText(price.toString());
				autoPartsServiceImpl.populateProfit(profitTableView, allSoldAutoPats);
			} else {
				profitTextField.setText("0.00");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Nothing has been sold in this year.");
				alert.setContentText("Choose another year.");

				alert.showAndWait();
			}*/
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a date.");
			alert.setContentText("Select a date!");

			alert.showAndWait();
		}
		
	}
	
	
	
	@FXML private void showSalary() {
		autoPartsServiceImpl.showSallary(main, autoPartsDaoImpl, salaryTableView, salaryDatePicker, 
				totalProfitTextField, totalSalaryTextField, remainderTextField);
		/*if(salaryDatePicker.getValue() != null) {
			
			//sallaryDatePicker.setValue(profitDatePicker.getValue());
			
			ObservableList<SoldAutoPart> autoSoldPartsList = 
					autoPartsDaoImpl.getAllSoldAutoPartsByMonth(main.getConnection(), 
							salaryDatePicker.getValue().getMonthValue());
			
			ObservableList<Salary> allSallaries = 
					autoPartsDaoImpl.getAllSallaries(main.getConnection());
			
			if(autoSoldPartsList.size() > 0) {
				autoPartsServiceImpl.populateSalaries(salaryTableView, autoSoldPartsList, 
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
		}*/
	}
	
	public String getInterestSallary(BigDecimal workersSoldAutoParts, BigDecimal coefficient) {
		return workersSoldAutoParts.multiply(coefficient).toString();
	}
	
	@FXML private void editSalary() {
		
		autoPartsServiceImpl.showWindowForEditSalary(main, autoPartsDaoImpl, salaryForEditTableView, language, 
				editSalaryFXMLPath);
		/*if(salaryForEditTableView.getSelectionModel().getSelectedItem() != null)
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
				salaryForEditTableView.getItems().clear();
				autoPartsServiceImpl.populateSalariesForEdit(
						salaryForEditTableView, main.getConnection());
			});
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("You did not select a row.");
			alert.setContentText("Select a row!");

			alert.showAndWait();
		}*/
	}
	
	
	
	@FXML private void enLanguage() {
		 mainControllerLanguage(Language.English);
	}
	
	@FXML private void uaLanguage() {
		 mainControllerLanguage(Language.Ukrainian);
	}
	
	@FXML private void ruLanguage() {
		 mainControllerLanguage(Language.Russian);
	}
	
	@FXML private void quit() {
		if(main.getLanguage().equals(Language.English)) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText("Exit the account");
			alert.setContentText("Are you sure you want to log out of the account?");

			ButtonType buttonTypeOK = new ButtonType("OK");
			ButtonType buttonTypeCancel = new ButtonType("Cancel");
			
			alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOK){
			    this.stage.close();
			}
			if (result.get() == buttonTypeCancel){
				alert.close();
			}
		}
		
		if(main.getLanguage().equals(Language.Ukrainian)) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText("Вихід з акаунту");
			alert.setContentText("Ви впевнені, що хочете вийти з акаунту?");

			ButtonType buttonTypeOK = new ButtonType("Гаразд");
			ButtonType buttonTypeCancel = new ButtonType("Відміна");
			
			alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOK){
			    this.stage.close();
			}
			if (result.get() == buttonTypeCancel){
				alert.close();
			}
		}
		
		if(main.getLanguage().equals(Language.Russian)) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText("Выход з акаунта");
			alert.setContentText("Вы уверенны, что хотите выйти з акаунта?");

			ButtonType buttonTypeOK = new ButtonType("Хорошо");
			ButtonType buttonTypeCancel = new ButtonType("Отмена");
			
			alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOK){
			    this.stage.close();
			}
			if (result.get() == buttonTypeCancel){
				alert.close();
			}
		}
		
	}
	

}
