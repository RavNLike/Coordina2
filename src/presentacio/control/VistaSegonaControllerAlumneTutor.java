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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import pojo.AlumneTutor;
import pojo.exceptions.ArgumentErroniException;

public class VistaSegonaControllerAlumneTutor implements Initializable {
    
	@FXML private TableColumn<AlumneTutor, String> taulaDNI;
    @FXML private TableColumn<AlumneTutor, String> taulaNom;
    @FXML private TableColumn<AlumneTutor, String> taulaCognoms;
    @FXML private TableColumn<AlumneTutor, String> taulaCorreuUPV;
    @FXML private TableView<AlumneTutor> taula;
    @FXML private TextField barraBuscadora;
	Coordina2 cd2 = Coordina2.getInstancia();
	private ArrayList<AlumneTutor> alumnetutor = cd2.llistarAlumnesTutors();
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	ObservableList<AlumneTutor> altuts = FXCollections.observableArrayList(alumnetutor);
    	
    	//Ara plenar columnes
    	taulaDNI.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getNif()));
    	taulaNom.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getNom()));
    	taulaCognoms.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getCognoms()));
    	taulaCorreuUPV.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getCorreu_upv()));
    	taula.setItems(altuts);

    	/****************FILTRATGE***********************/
    	FilteredList<AlumneTutor> filteredData = new FilteredList<>(altuts, p -> true);
    	barraBuscadora.textProperty().addListener((ob, vell, nou) -> {
    		filteredData.setPredicate(altuu -> {
				if (nou == null || nou.isEmpty()) {
					return true;
				}
				String minuscules = nou.toLowerCase();
				if (altuu.getNom().toLowerCase().contains(minuscules)
						|| altuu.getNif().toLowerCase().contains(minuscules)) {
					return true;
				}	
				return false;
			});
    	}); // de listener
    	SortedList<AlumneTutor> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(taula.comparatorProperty());
		taula.setItems(sortedData);
    }
        
    @FXML public void enrere(){VistaNavigator.loadVista(VistaNavigator.VISTAINI);}
    
    @FXML public void afegirAlumneTutor(){
    	Dialog<AlumneTutor> dialog = new Dialog<>();
    	dialog.setTitle("Afegir Alumne Tutors");
    	dialog.setHeaderText("Diàleg per a afegir un alumne tutor nou. Emplene tots els camps.");
    	dialog.setResizable(true);
    	Label lb1 = new Label("DNI:");
    	Label lb2 = new Label("Nom:");
    	Label lb3 = new Label("Cognoms:");
    	Label lb4 = new Label("Correu UPV:");
    	TextField tx1 = new TextField();
    	TextField tx2 = new TextField();
    	TextField tx3 = new TextField();
    	TextField tx4 = new TextField();
    	GridPane grid = new GridPane();
    	grid.add(lb1, 1, 1);
    	grid.add(lb2, 1, 2);
    	grid.add(lb3, 1, 3);
    	grid.add(lb4, 1, 4);
    	grid.add(tx1, 2, 1);
    	grid.add(tx2, 2, 2);
    	grid.add(tx3, 2, 3);
    	grid.add(tx4, 2, 4);
    	dialog.getDialogPane().setContent(grid);
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	dialog.setResultConverter(new Callback<ButtonType, AlumneTutor>() {
    		@Override
    		public AlumneTutor call(ButtonType b){
    			if(b == buttonTypeOk){
    				return new AlumneTutor(tx1.getText(), tx2.getText(), 
    						tx3.getText(), tx4.getText());
    			}
    			return null;
    		} 	
    	});    	
    	Optional<AlumneTutor> result = dialog.showAndWait();
    	if(result.isPresent()){
    		try {
				cd2.afegirAlumneTutor(result.get());
				ObservableList<AlumneTutor> altu = FXCollections.observableArrayList(cd2.llistarAlumnesTutors());
				taula.setItems(altu);
				
			} catch (ArgumentErroniException e) {
				e.printStackTrace();
			}
    	}

    }
    
    @FXML public void editarAlumneTutor(){
    	if(taula.getSelectionModel().getSelectedItem() == null){
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atenció!");
    		al.setHeaderText("Seleccione un element a editar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		Dialog<AlumneTutor> dialog = new Dialog<>();
    		AlumneTutor aux = taula.getSelectionModel().getSelectedItem();
        	dialog.setTitle("Afegir Alumne Tutors");
        	dialog.setHeaderText("Diàleg per a editar un alumne tutor. Emplene tots els camps.");
        	dialog.setResizable(true);
        	Label lb1 = new Label("DNI:");
        	Label lb2 = new Label("Nom:");
        	Label lb3 = new Label("Cognoms:");
        	Label lb4 = new Label("Correu UPV:");
        	TextField tx1 = new TextField(aux.getNif());
        	tx1.setEditable(false);
        	tx1.setDisable(true);
        	TextField tx2 = new TextField(aux.getNom());
        	TextField tx3 = new TextField(aux.getCognoms());
        	TextField tx4 = new TextField(aux.getCorreu_upv());
        	GridPane grid = new GridPane();
        	grid.add(lb1, 1, 1);
        	grid.add(lb2, 1, 2);
        	grid.add(lb3, 1, 3);
        	grid.add(lb4, 1, 4);
        	grid.add(tx1, 2, 1);
        	grid.add(tx2, 2, 2);
        	grid.add(tx3, 2, 3);
        	grid.add(tx4, 2, 4);
        	dialog.getDialogPane().setContent(grid);
        	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        	dialog.setResultConverter(new Callback<ButtonType, AlumneTutor>() {
        		@Override
        		public AlumneTutor call(ButtonType b){
        			if(b == buttonTypeOk){
        				return new AlumneTutor(tx1.getText(), tx2.getText(), 
        						tx3.getText(), tx4.getText());
        			}
        			return null;
        		} 	
        	});    	
        	Optional<AlumneTutor> result = dialog.showAndWait();
        	if(result.isPresent()){
        		alumnetutor = cd2.llistarAlumnesTutors();
        		for(int i = 0; i < alumnetutor.size(); i++){
        			if(alumnetutor.get(i).getNif().equals(result.get().getNif())){ //si el troba
        				alumnetutor.remove(i);
        			}
        		}
        		alumnetutor.add(result.get());
        		try {
    				cd2.editarAlumneTutor(result.get());
    				ObservableList<AlumneTutor> altu = FXCollections.observableArrayList(alumnetutor);
    				taula.setItems(altu);
    			} catch (ArgumentErroniException e) {
    				e.printStackTrace();
    			}
        	}
    	}
    }
    
    @FXML public void esborrarAlumneTutor(){
    	if(taula.getSelectionModel().getSelectedItem() == null){
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
    			try{
    				cd2.borrarAlumneTutor(taula.getSelectionModel().getSelectedItem());
    				ObservableList<AlumneTutor> altu = FXCollections.observableArrayList(cd2.llistarAlumnesTutors());
    				taula.setItems(altu);
    			} catch (ArgumentErroniException e){
    				e.printStackTrace();
    			}
    			
    		}
    	}
    }
}