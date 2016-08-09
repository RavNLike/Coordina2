package presentacio.control;

import presentacio.control.VistaNavigator;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import bll.Coordina2;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import pojo.Grup;
import pojo.Tutelat;

public class VistaSegonaControllerTutelat implements Initializable {
    
    @FXML private TableColumn<Tutelat, String> taulaNom;
    @FXML private TableColumn<Tutelat, String> taulaGrupMatricula;
    @FXML private TableColumn<Tutelat, String> taulaCorreuUPV;
    @FXML private TableColumn<Tutelat, String> taulaCognoms;
    @FXML private TableColumn<Tutelat, String> taulaCorreuPersonal;
    @FXML private TableColumn<Tutelat, String> taulaDNI;
    @FXML private TableColumn<Tutelat, String> taulaGrupPATU;
    @FXML private TableColumn<Tutelat, String> taulaTelefon;
    @FXML private TableView<Tutelat> taula;
    @FXML private TextField barraBuscadora;
    @FXML private Button afegirT, editarT, esborrarT;
    private Coordina2 cd2 = Coordina2.getInstancia();
    private ArrayList<Tutelat> tutelat = cd2.llistarTutelats();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	//Ara plenar columnes
    	taulaDNI.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getNif()));
    	taulaNom.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getNom()));
    	taulaCognoms.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getCognoms()));
    	taulaGrupMatricula.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getGrup_matricula()));
    	taulaCorreuUPV.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getCorreu_upv()));
    	taulaCorreuPersonal.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getCorreu_personal()));
    	taulaGrupPATU.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getGrup_patu().getNom()));
    	taulaTelefon.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getMobil()));
    	ObservableList<Tutelat> tutelatsObs = FXCollections.observableArrayList(tutelat);
    	taula.setItems(tutelatsObs);
    	
    	barraBuscadora.textProperty().addListener((ob, vell, nou) -> {filtratge(nou);});
    	
    	afegirT.setOnAction((event) -> {afegirTutelat();});
		editarT.setOnAction((event) -> {editarTutelat();});
		esborrarT.setOnAction((event) -> {esborrarTutelat();});
    	
    }
        
    @FXML public void enrere(){VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
    

    @FXML void afegirTutelat() {
    	//Creacio del formulari de afegir 
    	Dialog<Tutelat> dialog = new Dialog<>();
    	DialogPane dp = dialog.getDialogPane();
    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    	dialog.setTitle("Afegir tutelat");
    	dialog.setHeaderText(null);
    	dialog.setResizable(true);
    	Label lb1 = new Label("DNI:");
    	Label lb2 = new Label("Nom:");
    	Label lb3 = new Label("Cognoms:");
    	Label lb4 = new Label("Grup de matricula:");
    	Label lb5 = new Label("Correu UPV:");
    	Label lb6 = new Label("Correu personal:");
    	Label lb7 = new Label("Grup de PATU");
    	Label lb8 = new Label("Nombre de telefon:");
    	TextField tx1 = new TextField();
    	TextField tx2 = new TextField();
    	TextField tx3 = new TextField();
    	TextField tx4 = new TextField();
    	TextField tx5 = new TextField();
    	TextField tx6 = new TextField();
    	ComboBox<String> cbGrup = new ComboBox<String>();
    	ArrayList<Grup> listgrups = cd2.llistarGrups();
    	for(Grup gr : listgrups){
    		cbGrup.getItems().add(gr.getNom());	
    	}
    	TextField tx8 = new TextField();
    	GridPane grid = new GridPane();
    	grid.add(lb1, 1, 1);
    	grid.add(lb2, 1, 2);
    	grid.add(lb3, 1, 3);
    	grid.add(lb4, 1, 4);
    	grid.add(lb5, 1, 5);
    	grid.add(lb6, 1, 6);
    	grid.add(lb7, 1, 7);
    	grid.add(lb8, 1, 8);
    	grid.add(tx1, 2, 1);
    	grid.add(tx2, 2, 2);
    	grid.add(tx3, 2, 3);
    	grid.add(tx4, 2, 4);
    	grid.add(tx5, 2, 5);
    	grid.add(tx6, 2, 6);
    	grid.add(cbGrup, 2, 7);
    	grid.add(tx8, 2, 8);
    	dialog.getDialogPane().setContent(grid);
    	//Fi de la creacio del dialeg
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	dialog.setResultConverter(new Callback<ButtonType, Tutelat>() {
    		@Override
    		public Tutelat call(ButtonType b){
    			if(b == buttonTypeOk){
    				Grup aux = cd2.buscarGrup(cbGrup.getSelectionModel().getSelectedItem());
    				return new Tutelat(tx1.getText(), tx2.getText(), tx3.getText(), 
    						tx5.getText(), tx6.getText(), aux, tx4.getText(), tx8.getText());
    			}
    			return null;
    		} 	
    	});    	
    	Optional<Tutelat> result = dialog.showAndWait();
    	if(result.isPresent()){
    		cd2.afegirTutelat(result.get());
    		tutelat = cd2.llistarTutelats();
			ObservableList<Tutelat> tutel = FXCollections.observableArrayList(tutelat);
			taula.setItems(tutel);
    	}
    }

    @FXML void editarTutelat() {
    	if(taula.getSelectionModel().getSelectedItem() == null){
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atencio!");
    		al.setHeaderText("Seleccione un element a editar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		Dialog<Tutelat> dialog = new Dialog<>();
    		Tutelat aux = taula.getSelectionModel().getSelectedItem();
        	dialog.setTitle("Editar tutelat");
        	dialog.setHeaderText("Dialeg per a editar un tutelat. \nPer a cancelar, prema la creu roja.");
        	dialog.setResizable(true);
        	Label lb1 = new Label("DNI:");
        	Label lb2 = new Label("Nom:");
        	Label lb3 = new Label("Cognoms:");
        	Label lb4 = new Label("Grup de matricula:");
        	Label lb5 = new Label("Correu UPV:");
        	Label lb6 = new Label("Correu personal:");
        	Label lb7 = new Label("Grup de PATU");
        	Label lb8 = new Label("Nombre de telefon:");
        	TextField tx1 = new TextField(aux.getNif());
        	tx1.setEditable(false);
        	tx1.setDisable(true);
        	TextField tx2 = new TextField(aux.getNom());
        	TextField tx3 = new TextField(aux.getCognoms());
        	TextField tx4 = new TextField(aux.getGrup_matricula());
        	TextField tx5 = new TextField(aux.getCorreu_upv());
        	TextField tx6 = new TextField(aux.getCorreu_personal());
        	ComboBox<String> cbGrup = new ComboBox<String>();
        	ArrayList<Grup> listgrups = cd2.llistarGrups();
        	for(Grup gr : listgrups){
        		cbGrup.getItems().add(gr.getNom());	
        	}
        	cbGrup.getSelectionModel().select(aux.getGrup_patu().getNom());
        	TextField tx8 = new TextField(aux.getMobil());
        	GridPane grid = new GridPane();
        	grid.add(lb1, 1, 1);
        	grid.add(lb2, 1, 2);
        	grid.add(lb3, 1, 3);
        	grid.add(lb4, 1, 4);
        	grid.add(lb5, 1, 5);
        	grid.add(lb6, 1, 6);
        	grid.add(lb7, 1, 7);
        	grid.add(lb8, 1, 8);
        	grid.add(tx1, 2, 1);
        	grid.add(tx2, 2, 2);
        	grid.add(tx3, 2, 3);
        	grid.add(tx4, 2, 4);
        	grid.add(tx5, 2, 5);
        	grid.add(tx6, 2, 6);
        	grid.add(cbGrup, 2, 7);
        	grid.add(tx8, 2, 8);
        	dialog.getDialogPane().setContent(grid);
        	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        	//converteix el resultat en un tutelat
        	dialog.setResultConverter(new Callback<ButtonType, Tutelat>() {
        		@Override
        		public Tutelat call(ButtonType b){
        			if(b == buttonTypeOk){
        				Grup aux = cd2.buscarGrup(cbGrup.getSelectionModel().getSelectedItem());
        				return new Tutelat(tx1.getText(), tx2.getText(), tx3.getText(), 
        						tx5.getText(), tx6.getText(), aux, tx4.getText(), tx8.getText());
        			}
        			return null;
        		} 	
        	});    	
        	Optional<Tutelat> result = dialog.showAndWait(); //llancament
        	if(result.isPresent()){  
        		/*tutelat = cd2.llistarTutelats();
        		for(int i = 0; i < tutelat.size(); i++){
        			if(tutelat.get(i).getNif().equals(result.get().getNif())){ //si el troba
        				tutelat.remove(i);
        			}
        		}
        		tutelat.add(result.get());*/
        		cd2.editarTutelat(result.get());
        		tutelat = cd2.llistarTutelats();
        		ObservableList<Tutelat> tutel = FXCollections.observableArrayList(tutelat);
        		taula.setItems(tutel);
        		taula.getColumns().get(0).setVisible(false);
				taula.getColumns().get(0).setVisible(true);
        	}
    	} // de else (si seleccionat)
    }

    @FXML void esborrarTutelat() {
    	Tutelat aux = taula.getSelectionModel().getSelectedItem(); 
    	if(aux == null){
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atencio!");
    		al.setHeaderText("Seleccione un element a esborrar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atencio!");
    		al.setHeaderText("Esta segur que vol esborrar l'element?");
    		al.setContentText(null);
    		Optional<ButtonType> result = al.showAndWait();
    		if(result.isPresent() && result.get() == ButtonType.OK){
    			cd2.borrarTutelat(aux);
    			tutelat = cd2.llistarTutelats();
    			ObservableList<Tutelat> tutel = FXCollections.observableArrayList(tutelat);
    			taula.setItems(tutel);
    		}
    	}
    }
    
    public void filtratge(String nou){
    	ObservableList<Tutelat> tu = FXCollections.observableArrayList(tutelat);
    	FilteredList<Tutelat> filteredData = new FilteredList<>(tu, p -> true);
	
		filteredData.setPredicate(tutelat -> {
			if (nou == null || nou.isEmpty()) {
				return true;
			}
			String minuscules = nou.toLowerCase();
			if (tutelat.getNom().toLowerCase().contains(minuscules)
					|| tutelat.getNif().toLowerCase().contains(minuscules)
					|| tutelat.getCognoms().toLowerCase().contains(minuscules)) {
				return true;
			}	
			return false;
		});
	
    	SortedList<Tutelat> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(taula.comparatorProperty());
		taula.setItems(sortedData);
    }
    
}
