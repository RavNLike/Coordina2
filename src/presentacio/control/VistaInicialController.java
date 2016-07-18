package presentacio.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import bll.Coordina2;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
    
}
