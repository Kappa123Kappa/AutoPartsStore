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
	        	uaMenuItem.setText("Óêðà¿íñüêà");
	        	ruMenuItem.setText("Ðóññêèé");
	        	
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
	        	dataBaseMenu.setText("Áàçà äàíèõ");
	        	workers.setText("Ðîá³òíèêè");
	        	availableAutoPartsMenuItem.setText("Äîñòóïí³ àâòîçàï÷àñòèíè");
	        	soldAutoPartsMenuItem.setText("Ïðîäàí³ àâòîçàï÷àñòèíè");
	        	accounting.setText("Áóõãàëòåð³ÿ");
	        	
	        	windowMenu.setText("Â³êíî");
	        	chooseLanguageMenu.setText("Îáðàòè ìîâó");
	        	enMenuItem.setText("English");
	        	uaMenuItem.setText("Óêðà¿íñüêà");
	        	ruMenuItem.setText("Ðóññêèé");
	        	
	        	helpMenu.setText("Äîïîìîãà");
	        	aboutMenuItem.setText("Ïðî ïðîãðàìó");

	        	sellButton.setText("Ïðîäàòè àâòîçàï÷àñòèíè");
	        	addButton.setText("Äîäàòè àâòîçàï÷àñòèíè");
	        	editButton.setText("Çì³íèòè àâòîçàï÷àñòèíè");
	        	decommissionButton.setText("Ñïèñàòè àâòîçàï÷àñòèíè");
	        	
	        	labelTotalPrice.setText("Çàãàëüíà ö³íà:");
	        	
	        	nameLabel.setText("Íàçâà");
	        	manufacturerLabel.setText("Âèðîáíèê");
	        	priceLabel.setText("Ö³íà ó $");
	        	descriptionLabel.setText("Îïèñ");
	        	quantityLabel.setText("Ê³ëüê³ñòü");
	        	dateLabel.setText("Äàòà");
	        	
	        	
	        	
	        	profitTab.setText("Ïðèáóòîê");
	        	choseDateProfitLabel.setText("Îáåð³òü äàòó:");
	        	selectProfitLabel.setText("Îáåðèòü ïðèáóòîê çà:");
	        	totalProfitLabel.setText("Çàã. ïðèáóòîê ó $:");
	        	byDayButton.setText("Äåíü");
	        	byMonthButton.setText("Ì³ñÿöü");
	        	byYearButton.setText("Ð³ê");
	        	
	        	salaryTab.setText("Çàðïëàòà");
	        	choseDateSalaryLabel.setText("Îáåð³òü äàòó:");
	        	showSalaryBotton.setText("Ïîêàçàòè ì³ñÿ÷íó çàðïëàòó");
	        	
	        	profitLabel.setText("Çàãàëüíèé ïðèáóòîê ó $:");
	        	sallaryLabel.setText("Ñóìà çàðïëàò ó $:");
	        	remainderLabel.setText("Çàëèøîê ó $:");
	        	
	        	editSalaryTab.setText("Çì³íèòè çàðïëàòè");
	        	editSalaryBotton.setText("Çì³íèòè çàðïëàòó");
	        	
	        	
	        	addWorker.setText("Äîäàòè ðîá³òíèêà");
	        	editWorker.setText("Çì³íòè ðîá³òíèêà");
	        	dismissWorker.setText("Çâ³ëüíèòè ðîá³òíèêà");
	        	quitButton.setText("Âèéòè");
	        	
	        	break;
	        	
	        case 3 :
	        	dataBaseMenu.setText("Áàçà äàííûõ");
	        	workers.setText("Ðàáîòíèêè");
	        	availableAutoPartsMenuItem.setText("Äîñòóïíûå àâòîçàï÷àñòè");
	        	soldAutoPartsMenuItem.setText("Ïðîäàííûå àâòîçàï÷àñòè");
	        	accounting.setText("Áóõãàëòåðèÿ");
	        	
	        	windowMenu.setText("Îêíî");
	        	chooseLanguageMenu.setText("Âûáðàòü ÿçûê");
	        	enMenuItem.setText("English");
	        	uaMenuItem.setText("Óêðà¿íñüêà");
	        	ruMenuItem.setText("Ðóññêèé");
	        	
	        	helpMenu.setText("Ïîìîùü");
	        	aboutMenuItem.setText("Îá ïðîãðàìå");

	        	sellButton.setText("Ïðîäàòü àâòîçàï÷àñòè");
	        	addButton.setText("Äîáàâèòü àâòîçàï÷àñòè");
	        	editButton.setText("Èçìåíèòü àâòîçàï÷àñòè");
	        	decommissionButton.setText("Ñïèñàòü àâòîçàï÷àñòè");
	        	
	        	labelTotalPrice.setText("Îáùàÿ öåíà:");
	        	
	        	nameLabel.setText("Íàçâàíèå");
	        	manufacturerLabel.setText("Ïðîèçâîäèòåëü");
	        	priceLabel.setText("Öåíà â $");
	        	descriptionLabel.setText("Îïèñàíèå");
	        	quantityLabel.setText("Êîëè÷åñòâî");
	        	dateLabel.setText("Äàòà");
	        	
	        	
	        	
	        	profitTab.setText("Ïðèáûëü");
	        	choseDateProfitLabel.setText("Âûáåðèòå äàòó:");
	        	selectProfitLabel.setText("Âûáåðèòå ïðèáûëü çà:");
	        	totalProfitLabel.setText("Îáùàÿ ïðèáûëü â $:");
	        	byDayButton.setText("Äåíü");
	        	byMonthButton.setText("Ìåñÿö");
	        	byYearButton.setText("Ãîä");
	        	
	        	salaryTab.setText("Çàðïëàòà");
	        	choseDateSalaryLabel.setText("Âûáåðèòå äàòó:");
	        	showSalaryBotton.setText("Ïîêàçàòü ìåñÿ÷íóþ çàðïëàòó");
	        	
	        	profitLabel.setText("Îáùàÿ ïðèáûëü â $:");
	        	sallaryLabel.setText("Ñóììà çàðïëàò â $:");
	        	remainderLabel.setText("Îñòàòîê â $:");
	        	
	        	editSalaryTab.setText("Ñìåíèòü çàðïëàòû");
	        	editSalaryBotton.setText("Ñìåíèòü çàðïëàòó");
	        	
	        	addWorker.setText("Äîáàâèòü ðàáîòíèêà");
	        	editWorker.setText("Èçìåíèòü ðàáîòíèêà");
	        	dismissWorker.setText("Óâîëèòü ðàáîòíèêà");
	        	quitButton.setText("Âûéòè");
	        	
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
	        	uaMenuItem.setText("Óêðà¿íñüêà");
	        	ruMenuItem.setText("Ðóññêèé");
	        	
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
	}
	
	@FXML private void editWorker() {
		autoPartsServiceImpl.showWindowForEditingWorker(main, tableViewWorkers, language, 
				editWorkerFXMLPath);
	
	}
	
	@FXML private void dismissWorker() {
		
		autoPartsServiceImpl.showWindowForDismissingWorker(main, autoPartsDaoImpl, 
				tableViewWorkers, language);
		
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
		
	}
	
	@FXML private void showAddWindow() {
		autoPartsServiceImpl.showWindowForAddingAutoPart(main, tableViewAutoParts, 
				language, addAutoPartFXMLPath);
		
	}
	
	@FXML private void showEditWindow() {
		autoPartsServiceImpl.showWindowForEditingAutoPart(main, autoPartsDaoImpl, 
				tableViewAutoParts, language, editAutoPartFXMLPath);
		
	}
	
	@FXML private void showDecomissionWindow() {

		autoPartsServiceImpl.showWindowForDismissingAutoPart(main, autoPartsDaoImpl,
				tableViewAutoParts, language, decommissionAutoPartFXMLPath);
		
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
		
	}
	
	public String getInterestSallary(BigDecimal workersSoldAutoParts, BigDecimal coefficient) {
		return workersSoldAutoParts.multiply(coefficient).toString();
	}
	
	@FXML private void editSalary() {
		
		autoPartsServiceImpl.showWindowForEditSalary(main, autoPartsDaoImpl, salaryForEditTableView, language, 
				editSalaryFXMLPath);
		
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
			alert.setHeaderText("Âèõ³ä ç àêàóíòó");
			alert.setContentText("Âè âïåâíåí³, ùî õî÷åòå âèéòè ç àêàóíòó?");

			ButtonType buttonTypeOK = new ButtonType("Ãàðàçä");
			ButtonType buttonTypeCancel = new ButtonType("Â³äì³íà");
			
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
			alert.setHeaderText("Âûõîä ç àêàóíòà");
			alert.setContentText("Âû óâåðåííû, ÷òî õîòèòå âûéòè ç àêàóíòà?");

			ButtonType buttonTypeOK = new ButtonType("Õîðîøî");
			ButtonType buttonTypeCancel = new ButtonType("Îòìåíà");
			
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
