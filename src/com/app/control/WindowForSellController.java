package com.app.control;

import java.math.BigDecimal;
import java.sql.Connection;

import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.service.impl.AutoPartsServiceImpl;
import com.app.util.Language;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class WindowForSellController {
	
	@FXML private Label header;
	@FXML private Label autoPartNameLabel;
	@FXML private Label autoPartManufacturerLabel;
	@FXML private Label autoPartPriceLabel;
	@FXML private ComboBox<Integer> autoPartQuantityComboBox;
	@FXML private Label autoPartDescriptionLabel;
	@FXML private Label autoPartDateLabel;
	
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	private Connection connection;
	private int id;
	private String worker;
	private String profit;
	private Stage stage;
	private String price;
	
	private AutoPartsServiceImpl autoPartsServiceImpl = new AutoPartsServiceImpl();
	
	
	public void windowControllerLanguage(Language language) {
		switch(language.getId()){
	        case 1 :
	        	header.setText("Sell Auto Part");
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
	        	
	        case 2 :
	        	header.setText("Продати автозапчастини");
	        	okButton.setText("Гаразд");
	        	cancelButton.setText("Відмінити");
	        	break;
	        	
	        case 3 :
	        	header.setText("Продать автозапчасти");
	        	okButton.setText("Хорошо");
	        	cancelButton.setText("Отмена");
	        	break;
	        	
	        default:
	        	header.setText("Sell Auto Part");
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
		}
	}
	
	@FXML void sellData() {
		if(autoPartQuantityComboBox.getSelectionModel().getSelectedItem() != null) {
			new AutoPartsDaoImpl().deleteAutoPartFromAvailableAutoParts(connection, id, worker, 
					"Sold", autoPartNameLabel.getText(), 
					autoPartManufacturerLabel.getText(),
					autoPartPriceLabel.getText(), 
					autoPartsServiceImpl.getProfit(autoPartPriceLabel,
							autoPartQuantityComboBox, price), 
					autoPartDescriptionLabel.getText(), 
					autoPartQuantityComboBox.getSelectionModel().getSelectedItem().intValue());
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("Auto parts was successful sold!");
			alert.setContentText("");

			alert.showAndWait();
			closeWindow();
			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You did not choose quantity of auto parts.");
			alert.setContentText("Choose quantity of auto parts!");

			alert.showAndWait();
		}
		
	}
	
	@FXML void closeWindow() {
		this.stage.close();
	}
	
	public void setConnection (Connection connection) {
		this.connection = connection;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setAutoPartID(int id) {
		this.id = id;
	}
	
	public void setWorker(String worker) {
		this.worker = worker;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public void setAutoPartNameLabel(String autoPartName) {
		autoPartNameLabel.setText(autoPartName);
	}
	
	public void setAutoPartManufacturerLabel(String autoPartManufacturer) {
		autoPartManufacturerLabel.setText(autoPartManufacturer);
	}
	
	public void setAutoPartPriceLabel(String autoPartPrice) {
		autoPartPriceLabel.setText(autoPartPrice);
	}
	
	public void setAutoPartQuantityComboBox(int  quantity) {
		
		ObservableList<Integer> quantityList = FXCollections.observableArrayList();
		for(int i = 0; i < quantity; i++){
			quantityList.add(i + 1);
		}
		autoPartQuantityComboBox.getItems().addAll(quantityList);
	}
	
	public void setAutoPartDescriptionLabel(String autoPartDescription) {
		autoPartDescriptionLabel.setText(autoPartDescription);
	}
	
	public void setAutoPartDateLabel(String autoPartDate) {
		autoPartDateLabel.setText(autoPartDate);
	}
	
}
