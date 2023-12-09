package pt.fcul.chatpsd.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pt.fcul.chatpsd.WindowManager;



public class MainController {
	/**
	 * Bot�o para abrir o login
	 * @param event para clicar
	 */
	    @FXML
	    void loginAccount(ActionEvent event) {			//bot�o que faz ir para o loginScreen
	    	WindowManager.openPrincialPage();
	    }
		/**
		 * Bot�o para abrir o registo
		 * @param event para clicar
		 */
	    @FXML
	    void NewAccount(ActionEvent event) {  	//boto que faz ir para o registerScreen
	    	WindowManager.openRegister();
	    }

	    
	    


}