package com.app.control;

import java.math.BigDecimal;
import java.sql.Connection;
import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.util.Language;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class WindowForEditController {
	
	AutoPartsDaoImpl autoPartsDaoImpl;
	@FXML private GridPane gridPane;
	
	@FXML private Label header;
	
	@FXML private Label autoPartNameLabel;
	@FXML private Label autoPartManufacturerLabel;
	@FXML private Label autoPartPriceLabel;
	@FXML private Label autoPartPriceIncreaseCoefficientLabel;
	@FXML private Label autoPartDescriptionLabel;
	@FXML private Label autoPartQuantityLabel;
	
	
	@FXML private TextField autoPartNameTextField;
	@FXML private TextField autoPartManufacturerTextField;
	@FXML private TextField autoPartPriceTextField;
	@FXML private TextField autoPartPriceIncreaseCoefficientTextField;
	@FXML private TextField autoPartDescriptionTextField;
	@FXML private TextField autoPartQuantityTextField;
	
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	private Connection connection;
	private int id;
	private Stage stage;
	
	
	@FXML void editData() {
		if(autoPartNameTextField.getText() != null
				&& autoPartManufacturerTextField.getText() != null 
				&& autoPartPriceTextField.getText() != null
				&& autoPartPriceIncreaseCoefficientTextField.getText() != null
				&& autoPartDescriptionTextField.getText() != null
				&& autoPartQuantityTextField.getText() != null) {
			
			autoPartsDaoImpl = new AutoPartsDaoImpl();
			
			autoPartsDaoImpl.updateAvailableAutoPart(connection, this.id, autoPartNameTextField.getText(),
					autoPartManufacturerTextField.getText(), 
					autoPartPriceTextField.getText(),
					Double.valueOf(autoPartPriceIncreaseCoefficientTextField.getText()),
					autoPartDescriptionTextField.getText(), 
					Integer.valueOf(autoPartQuantityTextField.getText()) );
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Update");
			alert.setHeaderText("Auto parts information is successfully updated");
			alert.setContentText("Check all fields!");

			alert.showAndWait();
			closeWindow();
			
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You wrote incorrect data.");
			alert.setContentText("Check all fields!");

			alert.showAndWait();
		}
	}
	
	@FXML void closeWindow() {
		this.stage.close();
	}
	
	public void windowControllerLanguage(Language language) {
		switch(language.getId()){
	        case 1 :
	        	header.setText("Edit auto-part");
	        	autoPartNameLabel.setText("Name:");
	        	autoPartManufacturerLabel.setText("Manufacturer:");
	        	autoPartPriceLabel.setText("Price in $:");
	        	autoPartDescriptionLabel.setText("Description:");
	        	autoPartQuantityLabel.setText("Quantity:");
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
	        	
	        case 2 :
	        	header.setText("Змінити автозапчастини");
	        	autoPartNameLabel.setText("Ім'я:");
	        	autoPartManufacturerLabel.setText("Виробник:");
	        	autoPartPriceLabel.setText("Ціна у $:");
	        	autoPartDescriptionLabel.setText("Опис:");
	        	autoPartQuantityLabel.setText("Кількість:");
	        	okButton.setText("Гаразд");
	        	cancelButton.setText("Відмінити");
	        	break;
	        	
	        case 3 :
	        	header.setText("Изменить автозапчасти");
	        	autoPartNameLabel.setText("Имя:");
	        	autoPartManufacturerLabel.setText("Производитель:");
	        	autoPartPriceLabel.setText("Цена в $:");
	        	autoPartDescriptionLabel.setText("Описание:");
	        	autoPartQuantityLabel.setText("Количество:");
	        	okButton.setText("Хорошо");
	        	cancelButton.setText("Отмена");
	        	break;
	        	
	        default:
	        	header.setText("Edit auto-part");
	        	autoPartNameLabel.setText("Name:");
	        	autoPartManufacturerLabel.setText("Manufacturer:");
	        	autoPartPriceLabel.setText("Price in $:");
	        	autoPartDescriptionLabel.setText("Description:");
	        	autoPartQuantityLabel.setText("Quantity:");
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
		}
	}
	
	public void setConnection (Connection connection) {
		this.connection = connection;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setAutoPartID(int id){
		this.id = id;
	}

	public void setAutoPartNameTextField(String name){
		autoPartNameTextField.setText(name);
	}
	
	public void setAutoPartPriceIncreaseCoefficientTextField(String priceIncreaseCoefficient) {
		autoPartPriceIncreaseCoefficientTextField.setText(priceIncreaseCoefficient);
	}
	
	public void setAutoPartManufacturerTextField(String manufacturer){
		autoPartManufacturerTextField.setText(manufacturer);
	}
	
	public void setAutoPartPriceTextField(String price){
		autoPartPriceTextField.setText(price);
	}
	
	public void setAutoPartDescriptionTextField(String description){
		autoPartDescriptionTextField.setText(description);
	}
	
	public void setAutoPartQuantityTextField(String quantity){
		autoPartQuantityTextField.setText(quantity);
	}
	
}
