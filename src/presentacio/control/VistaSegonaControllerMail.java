package presentacio.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import bll.Coordina2;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
	private TextArea quadreMissatge;
	@FXML
	private RadioButton radioAlumnestutors, radioProfessors;
	@FXML 
	private Button enviarbutton, cancelarbutton;
	@FXML
	private GridPane gp;
	@FXML
	private ProgressBar pb;
	Task<?> copiaWorker;

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

	/**
	 * Per a enviar el email
	 * 
	 * @param event
	 */
	@FXML
	void enviar(ActionEvent event) {

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
			copiaWorker = creaWorker();
			
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
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmació");
				alert.setHeaderText("S'ha enviat el seu missatge correctament");
				alert.setContentText(null);
				alert.showAndWait();
				enrere();
			});

			/* Si al acabar l'execucio del thread ha hagut algun problema */
			copiaWorker.setOnFailed((valor) -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Ha ocurrit un error");
				alert.setContentText("Revise tots els camps.");
				alert.showAndWait();
				enrere();
			});
		}
	}

	private Task<?> creaWorker() {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

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
						}
					}

					// I ara a Professors
					if (llistaP != null) {
						for (Professor prof : llistaP) {
							cont++;
							String cosMissatge = quadreMissatge.getText() + "\n" + cd2.obtindreLlistaPerProfessor(prof);
							updateProgress(cont, numdests);
							cd2.enviarCorreu(prof, tema, cosMissatge);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		};
	}

}
