package presentacio.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bll.Coordina2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import pojo.Grup;
import pojo.Tutelat;

public class VistaSegonaControllerGrup implements Initializable{

    @FXML private TableView<Grup> taula;
	@FXML private TableColumn<Grup, String> columnaPrimerAL;
    @FXML private TableColumn<Grup, String> columnaNomGrup;
    @FXML private TableColumn<Grup, String> columnaSegonAL;
    @FXML private TableColumn<Grup, String> columnaProfessor;
    @FXML private TableView<Tutelat> taulaTutelats;
    @FXML private TableColumn<Tutelat, String> columnaTutelats;
    @FXML private TextArea barraBuscadora;
    private Coordina2 cd2 = Coordina2.getInstancia();
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	List<Grup> listGrups = cd2.llistarGrups();
    	ObservableList<Grup> grups = FXCollections.observableArrayList(listGrups);
    }
    
    @FXML void enrere(ActionEvent event) {VistaNavigator.loadVista(VistaNavigator.VISTAINI);}

    @FXML
    void afegirGrup(ActionEvent event) {

    }

    @FXML
    void editarGrup(ActionEvent event) {

    }

    @FXML
    void esborrarGrup(ActionEvent event) {

    }
}
