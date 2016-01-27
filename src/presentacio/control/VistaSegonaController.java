package presentacio.control;

import presentacio.control.VistaNavigator;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bll.Coordina2;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.AlumneTutor;
import pojo.Grup;
import pojo.Persona;
import pojo.Professor;
import pojo.Tutelat;

public class VistaSegonaController implements Initializable {
    
    @FXML private TableColumn<Persona, String> taulaNom;
    @FXML private TableColumn<Persona, String> taulaGrupMatricula;
    @FXML private TableColumn<Persona, String> taulaCorreuUPV;
    @FXML private TableColumn<Persona, String> taulaCognoms;
    @FXML private TableColumn<Persona, String> taulaCorreuPersonal;
    @FXML private TableColumn<Persona, String> taulaDNI;
    @FXML private TableColumn<Persona, String> taulaGrupPATU;
    @FXML private TableView<Persona> taula;
    private ObservableList<Tutelat> tutelats;
    private ObservableList<Professor> professors;
    private ObservableList<AlumneTutor> alumnestuts;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//Singleton 
    	Coordina2 cd2 = Coordina2.getInstancia();
    	
    	//Rebre paràmetre de què mostrar
    	//0: tutelats, 1: tutorsalumnes, 2: professors
    	switch (VistaNavigator.getDesDeVistaSecundariaPersona()){
    		case 0: 
    			Grup saux = cd2.buscarGrup(VistaNavigator.getDesDeVistaSecundariaGrup());
    			List<Tutelat> listTutelats = cd2.llistarTutelatsPerGrup(saux);
    			tutelats = FXCollections.observableArrayList(listTutelats);
    			//Ara plenar columnes
    			//taulaDNI.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getNif()));
    			//taulaNom.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getNom()));
    			//taulaCognoms.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getCognoms()));
    			//taula.setItems(tutelats);
    	}
    	
    }
        
    @FXML public void enrere(){VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
    
    @FXML public void llistarTutelats(){
    
    }
    
    @FXML public void llistarAlumnesTutors(){
    
    }
    
    @FXML public void llistarProfessors(){
    
    }
}
