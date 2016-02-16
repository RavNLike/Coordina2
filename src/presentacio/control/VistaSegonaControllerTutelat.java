package presentacio.control;

import presentacio.control.VistaNavigator;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import bll.Coordina2;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    @FXML private TableView<Tutelat> taula;
    @FXML private TextField barraBuscadora;
    private Coordina2 cd2 = Coordina2.getInstancia();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//Singleton
    	List<Tutelat> listTutelats = cd2.llistarTutelats();
    	for(Tutelat t : listTutelats){
    		System.out.println("Tutelat de dni: " + t.getNif() + " i nom: " + t.getNom() + " i de grup patu: " + t.getGrup_patu());
    	}
    	ObservableList<Tutelat> tutelats = FXCollections.observableArrayList(listTutelats);
    	
    	//Ara plenar columnes
    	taulaDNI.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getNif()));
    	taulaNom.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getNom()));
    	taulaCognoms.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getCognoms()));
    	taulaGrupMatricula.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getGrup_matricula()));
    	taulaCorreuUPV.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getCorreu_upv()));
    	taulaCorreuPersonal.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getCorreu_personal()));
    	taulaGrupPATU.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getGrup_patu().getNom()));
    	taula.setItems(tutelats);
    	
    	/****************FILTRATGE***********************/
    	FilteredList<Tutelat> filteredData = new FilteredList<>(tutelats,p -> true);
    	barraBuscadora.textProperty().addListener((ob, vell, nou) -> {
    		filteredData.setPredicate(tutelat -> {
				if (nou == null || nou.isEmpty()) {
					return true;
				}
				String minuscules = nou.toLowerCase();
				if (tutelat.getNom().toLowerCase().contains(minuscules)
						|| tutelat.getNif().toLowerCase().contains(minuscules)) {
					return true;
				}	
				return false;
			});
    	}); // de listener
    	SortedList<Tutelat> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(taula.comparatorProperty());
		taula.setItems(sortedData);
    }
        
    @FXML public void enrere(){VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
    

    @FXML void afegirTutelat(ActionEvent event) {
    	Dialog<Tutelat> dialog = new Dialog<>();
    	dialog.setTitle("Afegir tutelat");
    	dialog.setHeaderText("Diàleg per a afegir un tutelat nou. Emplene tots els camps.");
    	dialog.setResizable(true);
    	Label lb1 = new Label("DNI:");
    	Label lb2 = new Label("Nom:");
    	Label lb3 = new Label("Cognoms:");
    	Label lb4 = new Label("Grup de matrícula:");
    	Label lb5 = new Label("Correu UPV:");
    	Label lb6 = new Label("Correu personal:");
    	Label lb7 = new Label("Grup de PATU");
    	Label lb8 = new Label("Nombre de telèfon:");
    	TextField tx1 = new TextField();
    	TextField tx2 = new TextField();
    	TextField tx3 = new TextField();
    	TextField tx4 = new TextField();
    	TextField tx5 = new TextField();
    	TextField tx6 = new TextField();
    	TextField tx7 = new TextField();
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
    	grid.add(tx7, 2, 7);
    	grid.add(tx8, 2, 8);
    	dialog.getDialogPane().setContent(grid);
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	dialog.setResultConverter(new Callback<ButtonType, Tutelat>() {
    		@Override
    		public Tutelat call(ButtonType b){
    			if(b == buttonTypeOk){
    				Grup aux = cd2.buscarGrup(tx7.getText());
    				return new Tutelat(tx1.getText(), tx2.getText(), tx3.getText(), 
    						tx5.getText(), tx6.getText(), aux, tx4.getText(), tx8.getText());
    			}
    			return null;
    		} 	
    	});    	
    	Optional<Tutelat> result = dialog.showAndWait();
    	if(result.isPresent()){
    		cd2.afegirTutelat(result.get());
			ObservableList<Tutelat> tutel = FXCollections.observableArrayList(cd2.llistarTutelats());
			taula.setItems(tutel);
    	}
    }

    @FXML void editarTutelat(ActionEvent event) {
    	if(taula.getSelectionModel().getSelectedItem() == null){
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atenció!");
    		al.setHeaderText("Seleccione un element a editar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		Dialog<Tutelat> dialog = new Dialog<>();
    		Tutelat aux = taula.getSelectionModel().getSelectedItem();
        	dialog.setTitle("Editar tutelat");
        	dialog.setHeaderText("Diàleg per a editar un tutelat. \nPer a cancel·lar, prema la creu roja.");
        	dialog.setResizable(true);
        	Label lb1 = new Label("DNI:");
        	Label lb2 = new Label("Nom:");
        	Label lb3 = new Label("Cognoms:");
        	Label lb4 = new Label("Grup de matrícula:");
        	Label lb5 = new Label("Correu UPV:");
        	Label lb6 = new Label("Correu personal:");
        	Label lb7 = new Label("Grup de PATU");
        	Label lb8 = new Label("Nombre de telèfon:");
        	TextField tx1 = new TextField(aux.getNif());
        	TextField tx2 = new TextField(aux.getNom());
        	TextField tx3 = new TextField(aux.getCognoms());
        	TextField tx4 = new TextField(aux.getGrup_matricula());
        	TextField tx5 = new TextField(aux.getCorreu_upv());
        	TextField tx6 = new TextField(aux.getCorreu_personal());
        	TextField tx7 = new TextField(aux.getGrup_patu().getNom());
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
        	grid.add(tx7, 2, 7);
        	grid.add(tx8, 2, 8);
        	dialog.getDialogPane().setContent(grid);
        	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        	//converteix el resultat en un tutelat
        	dialog.setResultConverter(new Callback<ButtonType, Tutelat>() {
        		@Override
        		public Tutelat call(ButtonType b){
        			if(b == buttonTypeOk){
        				Grup aux = cd2.buscarGrup(tx7.getText());
        				return new Tutelat(tx1.getText(), tx2.getText(), tx3.getText(), 
        						tx5.getText(), tx6.getText(), aux, tx4.getText(), tx8.getText());
        			}
        			return null;
        		} 	
        	});    	
        	Optional<Tutelat> result = dialog.showAndWait(); //llançament
        	if(result.isPresent()){
        		cd2.editarTutelat(result.get());
        		ObservableList<Tutelat> tutel = FXCollections.observableArrayList(cd2.llistarTutelats());
        		taula.setItems(tutel);
        	}
    	} // de else (si seleccionat)
    }

    @FXML void esborrarTutelat(ActionEvent event) {
    	Tutelat aux = taula.getSelectionModel().getSelectedItem(); 
    	if(aux == null){
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atenció!");
    		al.setHeaderText("Seleccione un element a esborrar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atenció!");
    		al.setHeaderText("Està segur que vol esborrar l'element?");
    		al.setContentText(null);
    		Optional<ButtonType> result = al.showAndWait();
    		if(result.isPresent() && result.get() == ButtonType.OK){
    			cd2.borrarTutelat(aux);
    			ObservableList<Tutelat> tutel = FXCollections.observableArrayList(cd2.llistarTutelats());
    			taula.setItems(tutel);
    		}
    	}
    }
}
