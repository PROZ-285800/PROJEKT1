package application;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	private final String TITLE_TEXT = "Logowanie";
	private final String HEADER_TEXT = "Logowanie do systemu STYLEman";
		
	@Override
	public void start(Stage primaryStage) {
		LoginPanel loginPanel = new LoginPanel(TITLE_TEXT, HEADER_TEXT);
		loginPanel.show();
	}

	@Override
	public void stop() {
		System.out.println("stop");
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}

