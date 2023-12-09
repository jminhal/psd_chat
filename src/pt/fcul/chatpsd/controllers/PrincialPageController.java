package pt.fcul.chatpsd.controllers;

import java.awt.TextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import pt.fcul.chatpsd.WindowManager;
import pt.fcul.chatpsd.models.Dialog;





public class PrincialPageController {

	
	
    /**
	 * ID da lable do username
	 */
    @FXML
    private TextField username;

    @FXML
    void loginAccount(ActionEvent event) {
        if (!username.getText().isEmpty()) {
            WindowManager.openPrincialPage(); // bot�o que filtra(se � user ou mod) e faz o login no ModScreen ou UserScreen
        } else {
        	Dialog.warningDialog("Conta não encontrada", "Essa conta não existe na nossa base de dados, crie uma conta primeiro!", "Atenção");

        }
    }

}
