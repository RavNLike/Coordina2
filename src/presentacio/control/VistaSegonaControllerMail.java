package presentacio.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class VistaSegonaControllerMail implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	@FXML void enrere(ActionEvent event) {VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
}
