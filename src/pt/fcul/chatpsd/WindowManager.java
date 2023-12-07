package pt.fcul.chatpsd;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.fcul.chatpsd.controllers.MainController;
import pt.fcul.chatpsd.controllers.PrincialPageController;
/**
 * Esta classe vai servir para carregar um nova janela com o respetivo controlador
 * e tambem fazer o load de um FXML dentro de um mesmo cenario.
 */
public class WindowManager {

	private static Stage primaryStage;
	public static void setPrimaryStage(Stage primaryStage) {
		WindowManager.primaryStage = primaryStage;
	}
	/**
	 * Metodo vai servir para abrir o StartScreen.
	 */
	public static void openMainWindow() {//fun��o que chama o primeiro fxml
		openWindow("views/StartScreen.fxml",
				primaryStage,new MainController());
		primaryStage.show();
	}

	/**
	 * Metodo vai servir para abrir o pagina de registo.
	 */
	public static void openRegister() {  // fun��o que chama a pagina de registo

	}
	/**

	/**
	 * Metodo vai servir para abrir o Login.
	 */
	public static void openPrincialPage() {   //fun��o que chama o Login
		openWindow("views/PrincialPage.fxml",
				primaryStage,new PrincialPageController());
		primaryStage.show();
	}


	/**
	 * 	 * Declaracao do metodo para abrir nova janela com o controlador
	 * @param viewPath link do fxml
	 * @param window janela
	 * @param controller controlador
	 */
	public static void openWindow(String viewPath, Stage window, 
			Object controller) {
		try {
			Parent root = createNewNodeTree(viewPath, controller);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			window.setScene(scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 	 *Metedo que vai adicionar o cenario(FXML) ao controlador
	 * @param viewPath link do cenario
	 * @param controller  controlador do cenario
	 * @return root
	 * @throws IOException d� erro
	 */
	public static Parent createNewNodeTree(String viewPath, Object controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				Main.class.getResource(viewPath));
		loader.setController(controller);;
		Parent root = loader.load();
		return root;
	}
	/**
	 * Metodo que vai servir para fazer o load de um FXML dentro de um mesmo scene
	 * Vai ser preciso do nome do FXML, controller e do borderPane
	 * @param viewPath local da janela
	 * @param controller controlador da janela
	 * @param pane borderPane da janela
	 */
	public static void loadUI(String viewPath, Object controller, BorderPane pane) {
		AnchorPane root = null;
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(viewPath));// vai buscar o "link"
			loader.setController(controller);  // define o controllador 
			root = loader.load();//d� load
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		pane.setCenter(root);
	}

}
	
