package application;

import java.util.HashMap;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class LoginPanel {
	
	private final String [] ENVIRONMENT = {"Produkcyjne", "Testowe", "Deweloperskie"};
	private HashMap<Environment, HashMap<String,String>> hashMap = new HashMap<Environment, HashMap<String,String>>();
	private ComboBox<String> typeUser;
	private PasswordField passwordField;
	private ChoiceBox<String> chooseEnvironment;
	private ButtonType loginButton;
	private Dialog<Optional<Pair<Environment, String>>> dialog;

	public LoginPanel(String titleText, String headerText) {
		dialog = new Dialog<>();
		dialog.setTitle(titleText);
		dialog.setHeaderText(headerText);     
		dialog.setResizable(true);
		initializeHashMap();
		initializePanelComponents();
	}
	
	private void initializePanelComponents() {
		
		Label environmentLabel = new Label("Środowisko: ");
		Label userLabel = new Label("Użytkownik: ");
		Label passwordLabel = new Label("Hasło: ");
		GridPane grid = new GridPane();
		typeUser = new ComboBox<String>();
		typeUser.setEditable(true);
		typeUser.getEditor().textProperty().addListener( (observable, oldValue, newValue) -> {
			typeUser.setValue(newValue);
			enableOrDisableLoginButton();
		});
			
		typeUser.setMinWidth(130);
		typeUser.setMaxWidth(130);
		chooseEnvironment = new ChoiceBox <String>(FXCollections.observableArrayList(
				ENVIRONMENT[0], ENVIRONMENT[1], ENVIRONMENT[2]));
		
		chooseEnvironment.setOnAction(value -> {
			if (convertStringToEnvironment((String) chooseEnvironment.getValue()).equals(application.Environment.PRODUCTION)) {
				typeUser.getItems().clear();
				typeUser.getItems().setAll(hashMap.get(application.Environment.PRODUCTION).keySet());
			} else if (convertStringToEnvironment((String) chooseEnvironment.getValue()).equals(application.Environment.TEST)) {
				typeUser.getItems().clear();
				typeUser.getItems().setAll(hashMap.get(application.Environment.TEST).keySet());
			} else {
				typeUser.getItems().clear();
				typeUser.getItems().setAll(hashMap.get(application.Environment.DEVELOPMENT).keySet());
			}
			enableOrDisableLoginButton();
		});
		
		
		typeUser.setOnAction( event -> {
			enableOrDisableLoginButton();
		});
		
		loginButton = new ButtonType("Logon");
		
		ButtonType cancelButton = new ButtonType("Anuluj");
		
		passwordField = new PasswordField();
		passwordField.setMinWidth(130);
		passwordField.setMaxWidth(130);
		passwordField.textProperty().addListener((observable, oldValue, newValue)-> {
			passwordField.textProperty().set(newValue);
			enableOrDisableLoginButton();
		});
		
		grid.add(passwordField, 2, 3);
		grid.add(chooseEnvironment, 2, 1);
		grid.add(typeUser, 2, 2);
		grid.add(environmentLabel, 1, 1);
		grid.add(userLabel, 1, 2);
		grid.add(passwordLabel, 1, 3);

		dialog.getDialogPane().getButtonTypes().add(loginButton);
		dialog.getDialogPane().getButtonTypes().add(cancelButton);

		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().lookupButton(loginButton).setDisable(true);
		dialog.getDialogPane().lookupButton(loginButton).addEventFilter(ActionEvent.ACTION, event -> {
			if(validateUser(convertStringToEnvironment((String)chooseEnvironment.getValue()), typeUser.getEditor().getText(), passwordField.getText())) {
				System.out.println("Good Password");
			} else {
				System.out.println("Bad Password");
			}
		});
		
	}
	
	public void show() {
		if (dialog != null) {
			dialog.showAndWait();
		}
	}
	
	private Environment convertStringToEnvironment(String str) {
		if(str.equals(ENVIRONMENT[0])) {
			return application.Environment.PRODUCTION;
		} else if (str.equals(ENVIRONMENT[1])) {
			return application.Environment.TEST;
		} else {
			return application.Environment.DEVELOPMENT;
		}
	}	
	
	private void enableOrDisableLoginButton() {
		boolean shouldBeEnabled = true;
		
		if(passwordField.getText().equals("")) {
			shouldBeEnabled = false;
		}
		
		if (chooseEnvironment.getValue() != null) {
			if(isStringBlank(chooseEnvironment.getValue().toString())) {
				shouldBeEnabled = false;
			}
		} else {
			shouldBeEnabled = false;
		}
		
		if (typeUser.getValue() != null) {
			if(isStringBlank(typeUser.getValue().toString())) {
				shouldBeEnabled = false;
			}	
		} else {
			shouldBeEnabled = false;
		}
		
		
		if (shouldBeEnabled == false) {
			dialog.getDialogPane().lookupButton(loginButton).setDisable(true); 
		} else {
			dialog.getDialogPane().lookupButton(loginButton).setDisable(false);
		}
		
	}
	
	private boolean isStringBlank(String str) {
		return (str == null || str.equals(""));
	}
	
	private boolean validateUser(application.Environment env, String login, String password) {
		return password.equals(hashMap.get(env).get(login));
	}
	
	private void initializeHashMap() {
		hashMap = DataProvider.provideSetOfData();
	}

}
