package com.app.control;

import java.sql.Connection;

import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.model.Position;
import com.app.util.Language;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class WindowForEditWorkerController {
	
	@FXML private Label headerLabel;
	@FXML private Label firstNameLabel;
	@FXML private Label secondNameLabel;
	@FXML private Label positionLabel;
	@FXML private Label interestStableLable;
	@FXML private Label passwordLabel;

	
	@FXML private TextField firstNameTextField;
	@FXML private TextField secondNameTextField;
	@FXML private ComboBox<String> positionComboBox;
	@FXML private ComboBox<String> interestStableComboBox;
	@FXML private TextField passwordTextField;
	
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	private Connection connection;
	private Stage stage;
	private int id;
	
	
	public void windowControllerLanguage(Language language) {
		switch(language.getId()){
	        case 1 :
	        	headerLabel.setText("Edit workers information");
	        	firstNameLabel.setText("First name:");
	        	secondNameLabel.setText("Second name:");
	        	positionLabel.setText("Position:");
	        	interestStableLable.setText("Type of salary:");
	        	passwordLabel.setText("Password:");
	        	
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
	        	
	        case 2 :
	        	headerLabel.setText("Змінити інформацію робітника");
	        	firstNameLabel.setText("Ім'я:");
	        	secondNameLabel.setText("Прізвище:");
	        	positionLabel.setText("Посада:");
	        	interestStableLable.setText("Тип зарплати:");
	        	passwordLabel.setText("Пароль");
	        	
	        	okButton.setText("Гаразд");
	        	cancelButton.setText("Відмінити");
	        	break;
	        	
	        case 3 :
	        	headerLabel.setText("Добавить информацию работника");
	        	firstNameLabel.setText("Імя:");
	        	secondNameLabel.setText("Фамилия:");
	        	positionLabel.setText("Должность:");
	        	interestStableLable.setText("Тип зарплаты:");
	        	passwordLabel.setText("Пароль");
	        	
	        	okButton.setText("Хорошо");
	        	cancelButton.setText("Отмена");
	        	break;
	        	
	        default:
	        	headerLabel.setText("Edit workers information");
	        	firstNameLabel.setText("First name:");
	        	secondNameLabel.setText("Second name:");
	        	positionLabel.setText("Position:");
	        	interestStableLable.setText("Type of salary:");
	        	passwordLabel.setText("Password:");
	        	
	        	okButton.setText("OK");
	        	cancelButton.setText("Cancel");
	        	break;
		}
	}

	
	
	@FXML private void editWorker() {
		
		AutoPartsDaoImpl autoPartsDaoImpl = new AutoPartsDaoImpl();
		
		if(firstNameTextField.getText() != null 
				&& secondNameTextField.getText() != null
				&& positionComboBox.getSelectionModel().getSelectedItem() != null
				&& interestStableComboBox.getSelectionModel().getSelectedItem() != null
				&& passwordTextField.getText() != null) {
			
			autoPartsDaoImpl.updateWorker(connection, id, firstNameTextField.getText(),
					secondNameTextField.getText(), 
					positionComboBox.getSelectionModel().getSelectedItem().toString(),
					interestStableComboBox.getSelectionModel().getSelectedItem().toString(),
					passwordTextField.getText());
			
			if(interestStableComboBox.getSelectionModel().getSelectedItem().toString().equals("Interest")) {
				autoPartsDaoImpl.updateSallaryInformation(connection, id, 
						new StringBuilder(
								firstNameTextField.getText() + " " 
										+ secondNameTextField.getText()).toString(), 
						positionComboBox.getSelectionModel().getSelectedItem().toString(),
						interestStableComboBox.getSelectionModel().getSelectedItem().toString(), 
						"0.02", "0.00");
			} else {
				autoPartsDaoImpl.updateSallaryInformation(connection, id, 
						new StringBuilder(
								firstNameTextField.getText() + " " 
										+ secondNameTextField.getText()).toString(), 
						positionComboBox.getSelectionModel().getSelectedItem().toString(),
						interestStableComboBox.getSelectionModel().getSelectedItem().toString(), 
						"0.02", "1000.00");
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit");
			alert.setHeaderText("Worker was successfully edited.");
			alert.setContentText("");

			alert.showAndWait();
			closeWindow();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You wrote incorrect data.");
			alert.setContentText("Check all fields and combo boxes!");

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
	
	public void setWorkerID(int id){
		this.id = id;
	}
	
	public void setFirstNameTextField(String firstName) {
		firstNameTextField.setText(firstName);
	}
	
	public void setSecondNameTextField(String secondName) {
		secondNameTextField.setText(secondName);
	}
	
	public void fillPositionComboBox() {
		for(Position position : Position.values()) {
			positionComboBox.getItems().add(position.toString());
		}
	}
	
	public void setPositionComboBox(String positionStr) {
		int i = 0;
		for(Position position : Position.values()) {
			if(position.toString().equals(positionStr)) {
				positionComboBox.getSelectionModel().select(i);
				break;
			}
			i++;
		}
		
	}
	
	public void setInterestStableComboBox(String interestStableStr) {
		int l = 0;
		for(int i = 0; i < interestStableComboBox.getItems().size(); i++) {
			String str = interestStableComboBox.getItems().get(i).toString();
			if(interestStableComboBox.getItems().get(i).toString().equals(interestStableStr)) {
				interestStableComboBox.getSelectionModel().select(i);
				break;
			}
		}
		
	}
	
	public void  setPasswordTextField(String password) {
		passwordTextField.setText(password);
	}
}
