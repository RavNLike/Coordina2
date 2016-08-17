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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
	@FXML private ProgressBar pb;


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
    			new ExtensionFilter("Fulls de calcul Excel *.xls, *.xlsx", "*.xls", "*.xlsx"));
    	File fselected = fc.showOpenDialog(null);
    	if (fselected != null){
    		return fselected.getAbsolutePath();
    	} 
    	return "";
    }
    
    @FXML void aceptar(ActionEvent event) {
    	try {
    		Coordina2.getInstancia().inicialitzarSistema(rutaP.getText(), rutaAT.getText(), rutaT.getText());
    		Alert alert = new Alert(AlertType.CONFIRMATION);
        	DialogPane dp = alert.getDialogPane();
        	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
			alert.setTitle("Exit");
			alert.setHeaderText(null);
			alert.setContentText("La carrega inicial s'ha dut a terme amb exit");
			alert.showAndWait();
    	} catch (Exception e){
    		Alert alert = new Alert(AlertType.ERROR);
        	DialogPane dp = alert.getDialogPane();
        	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Ha fallat alguna cosa");
			alert.showAndWait();
			e.printStackTrace();
    	} finally {
    		VistaNavigator.loadVista(VistaNavigator.VISTAINI);
    	}

    }
    @FXML void cancelar(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.VISTAINI);
    }
    
    @FXML void enrere(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.VISTAINI);
    }
    
    @FXML void informacio(){
		Alert alert = new Alert(AlertType.INFORMATION);
    	DialogPane dp = alert.getDialogPane();
    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
		alert.setTitle("Informació");
		alert.setHeaderText(null);
		Label label = new Label("L'opció de càrrega incial serveix per a esborrar \nla base de dades i carregar les noves dades \ndes d'un fitxer .xls.");
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);
		alert.showAndWait();
	}


}
