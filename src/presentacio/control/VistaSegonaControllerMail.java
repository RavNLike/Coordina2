package presentacio.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import bll.Coordina2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pojo.AlumneTutor;
import pojo.Persona;
import pojo.Professor;

public class VistaSegonaControllerMail implements Initializable {
	private Coordina2 cd2 = Coordina2.getInstancia();
	@FXML private Label llistaDestinataris;
	@FXML private TextField quadreAssumpte;
	@FXML private TextArea quadreMissatge;
    @FXML private RadioButton radioAlumnestutors;
    @FXML private RadioButton radioProfessors;
    @FXML private GridPane gp;
    @FXML private ProgressBar pb;
	
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
		
		//Per a la barra de progres hem de trobar quants destinataris hi ha en total,
		//per a dividir el nombre de persones que hem enviat ja dividit per el total,
		//i fer progressar la barra
		
		int numdestinataris = 0;
		
		//Mirem quins radiobutton estan seleccionats
		if(radioAlumnestutors.isSelected()){
			llistatutor = cd2.llistarAlumnesTutors();
			numdestinataris += llistatutor.size();
		}
		if (radioProfessors.isSelected()){
			llistaprofessor = cd2.llistarProfessors();
			numdestinataris += llistaprofessor.size();
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
		    	
		    	//Si hem plenat alguna llista de destinataris
			} else {
				
				//Per a fer progressar la barra;
				int cont = 0;
				pb.setVisible(true);
				
				if(llistatutor != null){
					for(AlumneTutor altutor : llistatutor){
						String cosMissatge = quadreMissatge.getText() + "\n" + 
								cd2.obtindreMembresPerAlumneTutor(altutor);
						cd2.enviarCorreu(altutor, tema, cosMissatge);
						cont++;
						pb.setProgress(cont/numdestinataris);
					}
				}
				if(llistaprofessor != null){
					for(Professor prof : llistaprofessor){
						String cosMissatge = quadreMissatge.getText() + "\n" + 
								cd2.obtindreLlistaPerProfessor(prof);
						cd2.enviarCorreu(prof, tema, cosMissatge);
						cont++;
						pb.setProgress(cont/numdestinataris);
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
