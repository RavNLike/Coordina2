package presentacio.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import bll.Coordina2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class VistaSegonaControllerCarrega implements Initializable{
	

    @FXML private Button buscarP;
    @FXML private TextField rutaT;
    @FXML private TextField rutaAT;
    @FXML private Button buscarT;
    @FXML private TextField rutaP;
    @FXML private Button buscarAT;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	buscarP.setOnAction((event) -> {rutaP.setText(seleccionarPath("Escollir fitxer de professor"));});
    	buscarAT.setOnAction((event) -> {rutaAT.setText(seleccionarPath("Escollir fitxer d'alumne tutor"));});
    	buscarT.setOnAction((event) -> {rutaT.setText(seleccionarPath("Escollir fitxer de tutelat"));});
    	
    }
    
    private String seleccionarPath(String titol){
    	FileChooser fc = new FileChooser();
    	fc.setTitle(titol);
    	fc.getExtensionFilters().addAll(
    			new ExtensionFilter("Arxius de text *.txt", "*.txt"),
    			new ExtensionFilter("Fulls de calcul Excel *.xls, *.xlsx", "*.xls", "*.xlsx"));
    	File fselected = fc.showOpenDialog(null);
    	if (fselected != null){
    		System.out.println("Llavors el path es: " + fselected.getAbsolutePath());
    		return fselected.getAbsolutePath();
    	} 
    	return "";
    }
    
    @FXML void aceptar(ActionEvent event) {
    	try {
    		Coordina2.getInstancia().inicialitzarSistema(rutaP.getText(), rutaAT.getText(), rutaT.getText());
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exit");
			alert.setHeaderText(null);
			alert.setContentText("La carrega inicial s'ha dut a terme amb exit");
			alert.showAndWait();
    	} catch (Exception e){
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Ha fallat alguna cosa");
			alert.showAndWait();
    	}

    }
    @FXML void cancelar(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.VISTAINI);
    }


}
