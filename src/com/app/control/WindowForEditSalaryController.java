package com.app.control;

import java.sql.Connection;
import java.util.Optional;

import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.util.Language;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class WindowForEditSalaryController {
	
	private AutoPartsDaoImpl autoPartsDaoImpl  = new AutoPartsDaoImpl();
	
	@FXML private Label headerLabel;
	
	@FXML private Label interestStableLable;
	@FXML private ComboBox interestStableComboBox;
	
	@FXML private Label salaryCoefficienLabel;
	@FXML private TextField salaryCoefficienTextField;
	
	@FXML private Label salaryLabel;
	@FXML private TextField salaryTextField;
	
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	
	private Connection connection;
	private Stage stage;
	private int id;
	private String tempSalaryCoefficien;
	private String tempSalary;
	
	public void windowControllerLanguage(Language language) {
		switch(language.getId()) {
			case 1:
				headerLabel.setText("Edit workers salary");
				interestStableLable.setText("Type of Salary:");
				interestStableComboBox.setPromptText("Type");
				interestStableComboBox.getItems().clear();
				
				interestStableComboBox.getItems().add("Interest");
				interestStableComboBox.getItems().add("Stable");
				
				salaryCoefficienLabel.setText("Salary Coefficient:");
				salaryLabel.setText("Salary in $:");
				okButton.setText("OK");
				cancelButton.setText("Cancel");
				break;
			
			case 2:
				headerLabel.setText("Зміна зарплати робітника");
				interestStableLable.setText("Тип зарплати:");
				interestStableComboBox.setPromptText("Тип");
				interestStableComboBox.getItems().clear();
				
				interestStableComboBox.getItems().add("Interest");
				interestStableComboBox.getItems().add("Stable");
				
				salaryCoefficienLabel.setText("Відсоток ставки:");
				salaryLabel.setText("Зарплата у $:");
				okButton.setText("Гаразд");
				cancelButton.setText("Відміна");
				break;
				
			case 3:
				headerLabel.setText("Смена зарплаты работника");
				interestStableLable.setText("Тип зарплаты:");
				interestStableComboBox.setPromptText("Тип");
				interestStableComboBox.getItems().clear();
				
				interestStableComboBox.getItems().add("Interest");
				interestStableComboBox.getItems().add("Stable");
				
				salaryCoefficienLabel.setText("Процент ставки:");
				salaryLabel.setText("Зарплата в $:");
				
				okButton.setText("Хорошо");
				cancelButton.setText("Отмена");
				break;
				
			default:
				headerLabel.setText("Edit workers salary");
				interestStableLable.setText("Type of Salary:");
				interestStableComboBox.setPromptText("Type");
				interestStableComboBox.getItems().clear();
				
				interestStableComboBox.getItems().add("Interest");
				interestStableComboBox.getItems().add("Stable");
				
				salaryCoefficienLabel.setText("Salary Coefficient:");
				salaryLabel.setText("Salary in $:");
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
	
	public void setSalaryID(int id){
		this.id = id;
	}
	
	public void setInterestStableComboBox(String interestStable) {
		if(interestStable.equals("Interest")) {
			interestStableComboBox.getSelectionModel().select(0);
			
		} else {
			interestStableComboBox.getSelectionModel().select(1);
			
		}	
	}
	
	public void setSalaryCoefficien(String salaryCoefficien) {
		salaryCoefficienTextField.setText(salaryCoefficien);
		tempSalaryCoefficien = salaryCoefficien;
	}
	
	
	public void setSalary(String salary) {
		salaryTextField.setText(salary);
		tempSalary = salary;
	}
	
	
	@FXML private void editSalary() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Change worker salary");
		alert.setHeaderText("Confirmation");
		alert.setContentText("Are you sure, that you want to change worker salary?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			autoPartsDaoImpl.updateOnlySallary(connection, id, 
					interestStableComboBox.getSelectionModel().getSelectedItem().toString(), 
					salaryCoefficienTextField.getText(),  salaryTextField.getText());
			stage.close();
		} else {
			alert.close();
		}
	}
		
	
	@FXML private void closeWindow() {
		this.stage.close();
	}
}
