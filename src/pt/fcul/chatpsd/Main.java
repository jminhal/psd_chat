package pt.fcul.chatpsd;

import javafx.application.Application;
import javafx.stage.Stage;




public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		WindowManager.setPrimaryStage(primaryStage);		
		WindowManager.openMainWindow();						
	}

	public static void main(String[] args) {
		launch(args);
	}
}