package pt.fcul.chatpsd.models;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class resposavel para a cria��o de popouts
 */
public class Dialog {
	
	/**
	 * 	 * Dialog de warning
	 * @param title titulo do warning
	 * @param text texto do warning
	 * @param Header cabe�alho do warning
	 */
	public static void warningDialog(String title, String text, String Header) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setContentText(text);
		alert.setHeaderText(Header);

		alert.showAndWait();
		
	}
	
	/**
	 * Dialog de information
	 * @param title titulo do warning
	 * @param text texto do warning
	 * @param Header cabe�alho do warning
	 */
	public static void informationDialog(String title, String text, String Header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(text);
		alert.setHeaderText(Header);

		alert.showAndWait();
	}




}