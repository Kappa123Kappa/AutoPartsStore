package com.app.control;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.util.Language;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class WindowForAddController {
	
	@FXML private Label headerLabel;
	@FXML private Label nameLabel;
	@FXML private Label manufacturerLabel;
	@FXML private Label priceLabel;
	@FXML private Label priceincreaseCoefficientLable;
	@FXML private Label descriptionLabel;
	@FXML private Label quantityLabel;
	
	@FXML private TextField nameTextField;
	@FXML private TextField manufacturerTextField;
	@FXML private TextField priceTextField;
	@FXML private TextField priceincreaseCoefficientTextField;
	@FXML private TextField descriptionTextField;
	@FXML private TextField quantityTextField;
	
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	private Connection connection;
	private Stage stage;
	
	
	@FXML void addData(){
		AutoPartsDaoImpl autoPartsDaoImpl = new AutoPartsDaoImpl();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		
		if(nameTextField.getText() != null 
			&& manufacturerTextField.getText() != null
			&& priceTextField.getText() != null
			&& priceincreaseCoefficientTextField.getText() != null
			&& descriptionTextField.getText() != null
			&& quantityTextField.getText() != null) {
			autoPartsDaoImpl.addAutoPart(connection, 
					autoPartsDaoImpl.getAllAutoParts(connection).size() + 1,
					nameTextField.getText(), manufacturerTextField.getText(),
					priceTextField.getText(), priceincreaseCoefficientTextField.getText(), 
					descriptionTextField.getText(), Integer.valueOf(quantityTextField.getText()), 
					dtf.format(now).toString());
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add");
			alert.setHeaderText("Auto parts were successfully added");
			alert.setContentText("");

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
	        	headerLabel.setText("Add Auto Part");
	        	nameLabel.setText("Name:");
	        	manufacturerLabel.setText("Manufacturer:");
	        	priceLabel.setText("Price in $:");
	        	priceincreaseCoefficientLable.setText("Price increase in %:");
	        	descriptionLabel.setText("Description:");
	        	quantityLabel.setText("Quantity");
	        	
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
	        	
	        case 2 :
	        	headerLabel.setText("Додати автозапчастини");
	        	nameLabel.setText("Назва:");
	        	manufacturerLabel.setText("Виробник:");
	        	priceLabel.setText("Ціна у $:");
	        	priceincreaseCoefficientLable.setText("Збільшення ціни у %:");
	        	descriptionLabel.setText("Опис:");
	        	quantityLabel.setText("Кількість:");
	        	okButton.setText("Гаразд");
	        	cancelButton.setText("Відмінити");
	        	break;
	        	
	        case 3 :
	        	headerLabel.setText("Добавить автозапчасти");
	        	nameLabel.setText("Название:");
	        	manufacturerLabel.setText("Производитель:");
	        	priceLabel.setText("Цена в $:");
	        	priceincreaseCoefficientLable.setText("Увеличение цены в %:");
	        	descriptionLabel.setText("Описание:");
	        	quantityLabel.setText("Количество:");
	        	
	        	okButton.setText("Хорошо");
	        	cancelButton.setText("Отмена");
	        	break;
	        	
	        default:
	        	headerLabel.setText("Add Auto Part");
	        	nameLabel.setText("Name:");
	        	manufacturerLabel.setText("Manufacturer:");
	        	priceLabel.setText("Price in $:");
	        	priceincreaseCoefficientLable.setText("Price increase in %:");
	        	descriptionLabel.setText("Description:");
	        	quantityLabel.setText("Quantity");
	        	
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
		}
	}
	
	public void setConnection (Connection connection) {
		this.connection = connection;
	}

	public void setPriceTextFieldPromptText(String text) {
		priceTextField.setPromptText(text);
	}
	
	public void setPriceincreaseCoefficientTextFieldPromptText(String text) {
		priceincreaseCoefficientTextField.setPromptText(text);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
