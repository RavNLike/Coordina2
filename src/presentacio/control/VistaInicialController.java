package presentacio.control;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import bll.Coordina2;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import pojo.exceptions.SeguretatException;

public class VistaInicialController implements Initializable {
    @FXML private HBox boxInvisible;
    public static Coordina2 cd2 = Coordina2.getInstancia();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    @FXML public void eixir(){Platform.exit();}
    @FXML public void apretarTutelats(){VistaNavigator.loadVista(VistaNavigator.VISTASEGTUTELAT);}
    @FXML public void apretarTutorsAlumnes(){VistaNavigator.loadVista(VistaNavigator.VISTASEGALTUT);}
    @FXML public void apretarProfessors(){VistaNavigator.loadVista(VistaNavigator.VISTASEGPROF);}
    @FXML public void apretarGrups(){VistaNavigator.loadVista(VistaNavigator.VISTASEGRUP);}
    @FXML public void apretarMailing(){VistaNavigator.loadVista(VistaNavigator.VISTAMAIL);}
    @FXML public void apretarCarregaInicial(){VistaNavigator.loadVista(VistaNavigator.VISTACARREGA);}
    @FXML public void apretarAcreditacions(){
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("Seleccione el directori on es guardaran les acreditacions.");
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	
    	try{
    		File arxiu = chooser.showDialog(null);
    		if (arxiu == null){
    			Alert al = new Alert (AlertType.WARNING);
            	DialogPane dp = al.getDialogPane();
            	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
        		al.setTitle("Atenció");
        		al.setHeaderText(null);
        		al.setContentText("No s'ha seleccionat cap path");
        		al.showAndWait();
    		} else {
    			cd2.crearAcreditacions(arxiu.getAbsolutePath());
        		Alert al = new Alert (AlertType.CONFIRMATION);
            	DialogPane dp = al.getDialogPane();
            	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
        		al.setTitle("Èxit!");
        		al.setHeaderText(null);
        		al.setContentText("Les acreditacions s'han guardat correctament");
        		al.showAndWait();
    		}
    		
    	} catch (Exception e){
    		Alert al = new Alert (AlertType.ERROR);
        	DialogPane dp = al.getDialogPane();
        	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		al.setTitle("Error!");
    		al.setHeaderText("Ha ocorregut un error inesperat");
    		al.setContentText(null);
    		al.showAndWait();
    	}
    	
    }
    
    @FXML public void borrarBD(){
    	Alert al = new Alert (AlertType.WARNING);
    	DialogPane dp = al.getDialogPane();
    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
		al.setTitle("Atenció!");
		al.setHeaderText("Està segur que vol esborrar totes les dades?");
		al.setContentText(null);
		ButtonType botoOk = new ButtonType("Si");
		ButtonType botoCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		al.getButtonTypes().setAll(botoOk, botoCancel);
		Optional<ButtonType> result = al.showAndWait();
		if(result.get() == botoOk){
			try{
				cd2.borraBD();
				Alert al2 = new Alert (AlertType.CONFIRMATION);
		    	DialogPane dip = al2.getDialogPane();
		    	dip.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
	    		al2.setTitle("Exit");
	    		al2.setHeaderText("Base de dades esborrada correctament");
	    		al2.setContentText(null);
	    		ButtonType bOk = new ButtonType("Ok", ButtonData.FINISH);
	    		al2.getButtonTypes().setAll(bOk);
	    		al2.showAndWait();
			} catch (SQLException e){
				Alert al2 = new Alert (AlertType.ERROR);
		    	DialogPane dip = al2.getDialogPane();
		    	dip.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
	    		al2.setTitle("Error");
	    		al2.setHeaderText("Ha hagut un error amb SQL");
	    		al2.setContentText(null);
	    		ButtonType bOk = new ButtonType("Ok", ButtonData.FINISH);
	    		al2.getButtonTypes().setAll(bOk);
	    		al2.showAndWait();
			} catch (SeguretatException e){
				Alert al2 = new Alert (AlertType.ERROR);
		    	DialogPane dip = al2.getDialogPane();
		    	dip.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
	    		al2.setTitle("Error");
	    		al2.setHeaderText("Ha hagut un error de seguretat");
	    		al2.setContentText(null);
	    		ButtonType bOk = new ButtonType("Ok", ButtonData.FINISH);
	    		al2.getButtonTypes().setAll(bOk);
	    		al2.showAndWait();
			}
		} else {
			//System.out.println("Fora");
		}
    }
   /* @FXML public void apretarActes(){
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("Seleccione el directori on es guardaran les actes.");
    	chooser.setInitialDirectory(new File("/home"));
    	File arxiu = chooser.showDialog(null);
    	try{
    		cd2.crearActes(arxiu.getAbsolutePath());
    		Alert al = new Alert (AlertType.CONFIRMATION);
	    	DialogPane dip = al.getDialogPane();
	    	dip.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		al.setTitle("Èxit!");
    		al.setHeaderText("Les actes s'han guardat correctament");
    		al.setContentText(null);
    		al.showAndWait();
    	} catch (Exception e){
    		Alert al = new Alert (AlertType.ERROR);
	    	DialogPane dip = al.getDialogPane();
	    	dip.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		al.setTitle("Error!");
    		al.setHeaderText("Ha ocorregut un error inesperat");
    		al.setContentText(null);
    		al.showAndWait();
    	}
    	
    }*/
}
