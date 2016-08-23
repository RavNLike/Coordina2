package presentacio.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import bll.Coordina2;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import pojo.AlumneTutor;
import pojo.Professor;

public class VistaSegonaControllerMail implements Initializable {
	private Coordina2 cd2 = Coordina2.getInstancia();
	@FXML
	private Label llistaDestinataris;
	@FXML
	private TextField quadreAssumpte;
	@FXML
	private TextArea quadreMissatge, destlist;
	@FXML
	private RadioButton radioAlumnestutors, radioProfessors;
	@FXML 
	private Button enviarbutton, cancelarbutton;
	@FXML
	private GridPane gp;
	@FXML
	private ProgressBar pb;
	Task<Boolean> copiaWorker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ToggleGroup gp = new ToggleGroup();
		radioAlumnestutors.setToggleGroup(gp);
		radioProfessors.setToggleGroup(gp);
	}

	@FXML
	void enrere() {
		VistaNavigator.loadVista(VistaNavigator.VISTAINI);
	}
	
	@FXML void informacio(){
		Alert alert = new Alert(AlertType.INFORMATION);
    	DialogPane dp = alert.getDialogPane();
    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
		alert.setTitle("Informació");
		alert.setHeaderText(null);
		/*Text text = new Text("L'opció de mailing serveix per a enviar als destinataris informació dels seus grups. Apareixeran tots els integrants del grup.");
		text.setWrappingWidth(300);
		alert.getDialogPane().setContent(text);*/
		Label label = new Label("L'opció de mailing serveix per a enviar \nals destinataris informació dels seus grups. \nApareixeran tots els integrants del grup.");
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);
		//alert.setContentText("L'opció de mailing serveix per a enviar als destinataris informació dels seus grups. \nApareixeran tots els integrants del grup.");
		alert.showAndWait();
	}	

	/**
	 * Per a enviar el email
	 * 
	 * @param event
	 */
	@FXML void enviar() {

		// Si no hem seleccionat cap llista de destinataris
		if (!radioAlumnestutors.isSelected() && !radioProfessors.isSelected()) {
			Alert alert = new Alert(AlertType.WARNING);
	    	DialogPane dp = alert.getDialogPane();
	    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
			alert.setTitle("Atenció!");
			alert.setHeaderText(null);
			alert.setContentText("No ha seleccionat destinatari");
			alert.showAndWait();

			// Si hem plenat alguna llista de destinataris
		} else {
			pb.setVisible(true);
			pb.setProgress(0);
			copiaWorker = creaWorker(); //copiaWorker guarda el valor de boolean
			
			/* Detalls com deshabilitar boto d'enviar i cancelar, posar cursor en espera */
			enviarbutton.setDisable(true);
			cancelarbutton.setDisable(true);
			gp.getScene().setCursor(Cursor.WAIT);
			
			/* Lliguem ProgressProperty a la ProgressBar i s'anira actualitzant aixi
			 * Es fa dintre del Worker amb el metode updateProgress(...) */
			pb.progressProperty().unbind();
			pb.progressProperty().bind(copiaWorker.progressProperty());
			

			/* Llancem el thread */
			new Thread(copiaWorker).start();

			/* Si hem llançat el thread i tot ha anat be */
			copiaWorker.setOnSucceeded((valor) -> {
				
				gp.getScene().setCursor(Cursor.DEFAULT);
				
				if (copiaWorker.getValue()){
					//System.out.println("El valor que retorna es... " + copiaWorker.getValue());
					gp.getScene().setCursor(Cursor.DEFAULT);
					Alert alert = new Alert(AlertType.CONFIRMATION);
			    	DialogPane dp = alert.getDialogPane();
			    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
					alert.setTitle("Confirmació");
					alert.setHeaderText("S'ha enviat el seu missatge correctament");
					alert.setContentText(null);
					ButtonType botoOk = new ButtonType("Ok", ButtonData.OK_DONE);
					alert.getButtonTypes().setAll(botoOk);
					alert.showAndWait();
				} else {
					gp.getScene().setCursor(Cursor.DEFAULT);
					Alert alert = new Alert(AlertType.ERROR);
			    	DialogPane dp = alert.getDialogPane();
			    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
					alert.setTitle("Error");
					alert.setHeaderText("Ha ocurrit un error");
					alert.setContentText("Revise tots els camps.");
					ButtonType botoOk = new ButtonType("Ok", ButtonData.OK_DONE);
					alert.getButtonTypes().setAll(botoOk);
					alert.showAndWait();
				}
				enrere();
			});

			/* Si al acabar l'execucio del thread ha hagut algun problema */
			copiaWorker.setOnFailed((valor) -> {
				gp.getScene().setCursor(Cursor.DEFAULT);
				Alert alert = new Alert(AlertType.ERROR);
		    	DialogPane dp = alert.getDialogPane();
		    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
				alert.setTitle("Error");
				alert.setHeaderText("Ha ocurrit un error");
				alert.setContentText("Revise tots els camps.");
				alert.showAndWait();
				enrere();
			});
			

		}
	}

	@FXML void infoprofessors(){
		if(destlist.isVisible()){
			destlist.setVisible(false);
			destlist.setText("");
		} else {
			destlist.setVisible(true);
			ArrayList<Professor> aprof = cd2.llistarProfessors();
			String mostrar = "DESTINATARIS: \n";
			for (Professor p : aprof){
				mostrar += p.getCognoms() + ", " + p.getNom() + " <" + p.getCorreu_upv() + "> \n";
			}
			destlist.setText(mostrar);
		}
	}
	
	@FXML void infoalumnestutors(){
		if(destlist.isVisible()){
			destlist.setVisible(false);
			destlist.setText("");
		} else {
			destlist.setVisible(true);
			ArrayList<AlumneTutor> atu = cd2.llistarAlumnesTutors();
			String mostrar = "DESTINATARIS: \n";
			for (AlumneTutor a : atu){
				mostrar += a.getCognoms() + ", " + a.getNom() + " <" + a.getCorreu_upv() + "> \n";
			}
			destlist.setText(mostrar);
		}
	}
	private Task<Boolean> creaWorker() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				Boolean result = Boolean.TRUE;
				ArrayList<AlumneTutor> llistaAT = null;
				ArrayList<Professor> llistaP = null;
				int numdests = 0;
				int cont = 0;
				String tema = quadreAssumpte.getText();
				try {
					if (radioAlumnestutors.isSelected()) {
						llistaAT = cd2.llistarAlumnesTutors();
						numdests += llistaAT.size();
					}

					if (radioProfessors.isSelected()) {
						llistaP = cd2.llistarProfessors();
						numdests += llistaP.size();
					}

					// Enviem a AlumnesTutors
					if (llistaAT != null) {
						for (AlumneTutor altutor : llistaAT) {
							cont++;
							String cosMissatge = quadreMissatge.getText() + "\n"
									+ cd2.obtindreMembresPerAlumneTutor(altutor);
							updateProgress(cont, numdests);
							cd2.enviarCorreu(altutor, tema, cosMissatge);
							System.out.println("Entra en el bucle de ATS, valor de resultat: " + result);
						}
					}

					// I ara a Professors
					if (llistaP != null) {
						for (Professor prof : llistaP) {
							cont++;
							String cosMissatge = quadreMissatge.getText() + "\n" + cd2.obtindreLlistaPerProfessor(prof);
							updateProgress(cont, numdests);
							cd2.enviarCorreu(prof, tema, cosMissatge);
							System.out.println("Entra en el bucle de PROFS, valor de resultat: " + result);
						}
					}
				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println("Entra en el CATCH");
					result = Boolean.FALSE;
				}
				System.out.println("Valor final de resultat: " + result);
				return result;				
			}
		};
	}

}
