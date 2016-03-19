package presentacio.control;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import bll.Coordina2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pojo.AlumneTutor;
import pojo.Persona;
import pojo.Professor;

public class VistaSegonaControllerMail implements Initializable {
	private Coordina2 cd2 = Coordina2.getInstancia();
	@FXML private TextField quadreAssumpte;
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
	@FXML void enrere() {VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
	
	/**
	 * Per a enviar el email
	 * @param event
	 */
	@FXML void enviar(ActionEvent event){
		ArrayList<AlumneTutor> llistatutor = null;
		ArrayList<Professor> llistaprofessor = null;
		
		//Mirem quins radiobutton estan seleccionats
		if(radioAlumnestutors.isSelected()){
			llistatutor = cd2.llistarAlumnesTutors();
		}
		if (radioProfessors.isSelected()){
			llistaprofessor = cd2.llistarProfessors();
		}
		
		try {
			//String cosMissatge = quadreMissatge.getText();
			String tema = quadreAssumpte.getText();
			
			//Si no hem seleccionat cap llista de destinataris
			if(llistatutor == null && llistaprofessor == null){
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Informació");
		    	alert.setHeaderText(null);
		    	alert.setContentText("No ha seleccionat destinatari");
		    	alert.showAndWait();
		    	enrere();
		    	
		    	//Si hem plenat alguna llista de destinataris
			} else {
				if(llistatutor != null){
					for(AlumneTutor altutor : llistatutor){
						String cosMissatge = quadreMissatge.getText() + "\n" + 
								cd2.obtindreMembresPerAlumneTutor(altutor);
						cd2.enviarCorreu(altutor, tema, cosMissatge);
					}
				}
				if(llistaprofessor != null){
					for(Professor prof : llistaprofessor){
						String cosMissatge = quadreMissatge.getText() + "\n" + 
								cd2.obtindreLlistaPerProfessor(prof);
						cd2.enviarCorreu(prof, tema, cosMissatge);
					}
				}
				Alert alert = new Alert(AlertType.CONFIRMATION);
		    	alert.setTitle("Confirmació");
		    	alert.setHeaderText("S'ha enviat el seu missatge correctament");
		    	alert.setContentText(null);
		    	alert.showAndWait();
		    	enrere();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error");
	    	alert.setHeaderText("Ha ocurrit un error");
	    	alert.setContentText("Revise tots els camps.");
	    	alert.showAndWait();
			e.printStackTrace();
		}
	}
	
}
