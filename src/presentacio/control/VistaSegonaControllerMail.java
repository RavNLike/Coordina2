package presentacio.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class VistaSegonaControllerMail implements Initializable {
	private static String missatge;
	@FXML private TextArea quadreMissatge;
    @FXML private RadioButton radioAlumnestutors;
    @FXML private RadioButton radioProfessors;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//String aux = quadreMissatge.getText();
		ToggleGroup gp = new ToggleGroup();
		radioAlumnestutors.setToggleGroup(gp);
		radioProfessors.setToggleGroup(gp);
		gp.selectedToggleProperty().addListener((obser, vell, nou) -> {
			
		});
	}
	@FXML void enrere(ActionEvent event) {VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
	
	
}
