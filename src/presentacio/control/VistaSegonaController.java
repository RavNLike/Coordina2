package presentacio.control;

import presentacio.control.VistaNavigator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VistaSegonaController implements Initializable {
    
    @FXML private TableColumn<?, ?> taulaNom;
    @FXML private TableColumn<?, ?> taulaGrupMatricula;
    @FXML private TableColumn<?, ?> taulaCorreuUPV;
    @FXML private TableColumn<?, ?> taulaCognoms;
    @FXML private TableColumn<?, ?> taulaCorreuPersonal;
    @FXML private TableColumn<?, ?> taulaDNI;
    @FXML private TableColumn<?, ?> taulaGrupPATU;
    @FXML private TableView<?> taula;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
        
    @FXML public void enrere(){VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
    
    @FXML public void llistarTutelats(){
    
    }
    
    @FXML public void llistarAlumnesTutors(){
    
    }
    
    @FXML public void llistarProfessors(){
    
    }
}
