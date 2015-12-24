package PRESENTACIO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClassePrincipalApp extends Application {
	
	public static void main (String [] args) { launch(args); }
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("FinestraPrincipal.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Informació sobre AENA");
	        primaryStage.setScene(scene);
	        //primaryStage.getIcons().add(new Image("icon.png"));
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
