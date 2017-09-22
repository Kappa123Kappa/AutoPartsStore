package com.app.control;

import com.app.action.Main;
import com.app.dao.impl.AutoPartsDaoImpl;
import com.app.service.impl.AutoPartsServiceImpl;
import com.app.util.Language;
import com.app.util.impl.AutoPartsStoreUtilImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class SignInController implements Initializable {
	
	@FXML private GridPane gridPane;
	
    @FXML private Text actiontarget;
    @FXML private Text header;
    
    @FXML private ComboBox<String> comboBoxSignIn;
    
    @FXML private Label userLabel;
    @FXML private Label passwordLabel;
    
    @FXML private TextField textField;
    @FXML private PasswordField passwordField;
    
    @FXML private Button buttonSignIn;
    
    private Language language = Language.English;
    
    private AutoPartsStoreUtilImpl autoPartsStoreUtilImpl = new AutoPartsStoreUtilImpl();
    private AutoPartsDaoImpl autoPartsDaoImpl = new AutoPartsDaoImpl();
    //private AutoPartsServiceImpl  autoPartsServiceImpl = new AutoPartsServiceImpl();
    
    
    @FXML protected void handleSubmitButtonAction() {
      
    	if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty()
    			| !textField.getText().isEmpty() | !passwordField.getText().isEmpty()) {
    		 
    		try {
    			
				autoPartsStoreUtilImpl.setProperties("lib/properties.xml");
				
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Input/Output Exception");
				alert.setContentText(e.toString());

				alert.showAndWait();
			}
    		
    		Connection connection = null;
			try {
				connection = autoPartsStoreUtilImpl.getConnection();
			} catch (ClassNotFoundException | SQLException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Can't connect to data base!");
				alert.setContentText(e.toString());

				alert.showAndWait();
			}
    		try {
				autoPartsStoreUtilImpl.createDataBase(connection);
				autoPartsStoreUtilImpl.createTable(connection, "mysql/createTableWorkers.sql");
	    		autoPartsStoreUtilImpl.createTable(connection, "mysql/createAvailableAutoParts.sql");
	    		autoPartsStoreUtilImpl.createTable(connection, "mysql/createSoldAutoParts.sql");
	    		autoPartsStoreUtilImpl.createTable(connection, "mysql/createTableSallaries.sql");
			} catch (SQLException | FileNotFoundException e) {
				e.printStackTrace();
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Can't create tables!");
				alert.setContentText(e.toString());

				alert.showAndWait();
			}
    		
    		autoPartsDaoImpl.addDefaultWorkers(connection);
    		autoPartsDaoImpl.addDefaultAvailableAutoParts(connection);
    		autoPartsDaoImpl.addDefaultSallaries(connection);
            
    		String position = new AutoPartsServiceImpl().signIn(
    				connection, textField.getText(), passwordField.getText());
            //textField.getText() passwordField.getText()
            if(position != null) {
            	
            	actiontarget.setText("Welcome " + textField.getText());
            	
            	try {
            		
            		buttonSignIn.getScene().getWindow().hide();
            		// name
    				new Main(connection, autoPartsStoreUtilImpl, textField.getText(), 
    						position, language);
    				
    			} catch (IOException e) {
    				actiontarget.setText("Error, incorrect name or password");
    			}
            } else {
            	switch(language.getId()){
	    	    	case 1:
	    				actiontarget.setText("Error, incorrect name or password");
	    	    		
	    	    		break;
	        		case 2:
	        			actiontarget.setText("Помилка, невірне ім'я чи пароль");
	            		
	            		break;
	        		case 3:
	        			actiontarget.setText("Ошибка, неправильное имя или пароль");
	            		break;
	            	default:
	            		actiontarget.setText("Error, incorrect name or password");
	    	    		break;
            	}
            	
            	Timeline timeline = new Timeline();
            	timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
            	    new EventHandler<ActionEvent>() {

            	        @Override
            	        public void handle(ActionEvent event) {
            	        	actiontarget.setText("");
            	        }
            	 }));
            	
            	timeline.play();
            }
    	} else {
			/*Timeline timeline = new Timeline();
	    	timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
	    	    new EventHandler<ActionEvent>() {

	    	        @Override
	    	        public void handle(ActionEvent event) {
	    	        	switch(language){
	        	        case English :
	        	        	actiontarget.setText("Error, incorrect name  or password");
	        	        	break;
	        	        	
	        	        case Українська :
	        	        	actiontarget.setText("Помилка, невірне ім'я чи пароль");
	        	        	break;
	        	        	
	        	        case Русский:
	        	        	actiontarget.setText("Ошибка, неправильное имя или пароль");
	        	        	break;
	        	        	
	        	        default:
	        	        	actiontarget.setText("Error, incorrect name or password");
	        	        	break;
	    	        	}
	    	        	
	    	        }
	    	    }));
	    	
	    	timeline.play();*/
    		
    		switch(language.getId()){
	        case 1 :
	        	actiontarget.setText("Error, incorrect name  or password");
	        	break;
	        	
	        case 2 :
	        	actiontarget.setText("Помилка, невірне ім'я чи пароль");
	        	break;
	        	
	        case 3:
	        	actiontarget.setText("Ошибка, неправильное имя или пароль");
	        	break;
	        	
	        default:
	        	actiontarget.setText("Error, incorrect name or password");
	        	break;
        	}
        }
        
    }
    
    @FXML protected void languagesComboBoxAction() {
    	
    	switch(comboBoxSignIn.getSelectionModel().getSelectedItem()){
	    	case "English":
				actiontarget.setText("English language is selected.");
	    		language = Language.English;
	    		changeLanguage(language);
	    		break;
    		case "Українська":
    			actiontarget.setText("Вибрана Українська мова");
        		language = Language.Ukrainian;
        		changeLanguage(language);
        		break;
    		case "Русский":
    			actiontarget.setText("Выбран русский язык");
        		language = Language.Russian;
        		changeLanguage(language);
        		break;
        	default:
        		actiontarget.setText("English language is selected.");
	    		language = Language.English;
	    		changeLanguage(language);
	    		break;
    	}
    	
    	
    	Timeline timeline = new Timeline();
    	timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
    	    new EventHandler<ActionEvent>() {

    	        @Override
    	        public void handle(ActionEvent event) {
    	        	actiontarget.setText("");
    	        }
    	    }));
    	
    	timeline.play();
    }
    
    public void changeLanguage(Language language) {
    	
    	switch(language.getId()){
	    	case 1 :
	    		header.setText("Sign in");
	     		userLabel.setText("User name :");
	     		passwordLabel.setText("password :");
	     		buttonSignIn.setText("Sign In");
	     		break;
			case 2 :
				header.setText("Увійдіть");
	     		userLabel.setText("Ім'я користувача :");
	     		passwordLabel.setText("пароль :");
	     		buttonSignIn.setText("Увійдіть");
	    		break;
			case 3 :
				header.setText("Войдите");
	     		userLabel.setText("Имя пользователя :");
	     		passwordLabel.setText("пароль :");
	     		buttonSignIn.setText("Войти");
	    		break;
	    	default:
	    		header.setText("Sign in");
	     		userLabel.setText("User name :");
	     		passwordLabel.setText("password :");
	     		buttonSignIn.setText("Sign In");
	     		break;
    	}
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
    
}
