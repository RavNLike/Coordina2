package presentacio.control;

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
import javafx.event.ActionEvent;
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
import pojo.Professor;
import pojo.exceptions.ArgumentErroniException;

public class VistaSegonaControllerProfessor  implements Initializable {

	@FXML private TableColumn<Professor, String> taulaNom;
	@FXML private TableColumn<Professor, String> taulaCorreuUPV;
	@FXML private TableColumn<Professor, String> taulaCognoms;
	@FXML private TableColumn<Professor, String> taulaDNI;
	@FXML private TableView<Professor> taula;
	@FXML private TextField barraBuscadora;
	private Coordina2 cd2 = Coordina2.getInstancia();
	private ArrayList<Professor> professor = cd2.llistarProfessors();
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	ObservableList<Professor> professors = FXCollections.observableArrayList(professor);
    	
    	//Ara plenar columnes
    	taulaDNI.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getNif()));
    	taulaNom.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getNom()));
    	taulaCognoms.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> ((param.getValue()).getCognoms()));
    	taulaCorreuUPV.setCellValueFactory(param -> new ReadOnlyObjectWrapper <> (param.getValue().getCorreu_upv()));
    	taula.setItems(professors);
    	
    	/****************FILTRATGE***********************/
    	FilteredList<Professor> filteredData = new FilteredList<>(professors,p -> true);
    	barraBuscadora.textProperty().addListener((ob, vell, nou) -> {
    		filteredData.setPredicate(profe -> {
				if (nou == null || nou.isEmpty()) {
					return true;
				}
				String minuscules = nou.toLowerCase();

				if (profe.getNom().toLowerCase().contains(minuscules)
						|| profe.getNif().toLowerCase().contains(minuscules)) {
					return true;
				}	
				return false;
			});
    	}); // de listener
    	SortedList<Professor> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(taula.comparatorProperty());
		taula.setItems(sortedData);
    }

    @FXML void enrere(ActionEvent event) {VistaNavigator.loadVista(VistaNavigator.VISTAINI);}

    @FXML void afegirProfessor(ActionEvent event) {
    	Dialog<Professor> dialog = new Dialog<>();
    	dialog.setTitle("Afegir Professor");
    	dialog.setHeaderText("Diàleg per a afegir un professor nou. Emplene tots els camps.");
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
    	dialog.setResultConverter(new Callback<ButtonType, Professor>() {
    		@Override
    		public Professor call(ButtonType b){
    			if(b == buttonTypeOk){
    				return new Professor(tx1.getText(), tx2.getText(), 
    						tx3.getText(), tx4.getText());
    			}
    			return null;
    		} 	
    	});    	
    	Optional<Professor> result = dialog.showAndWait();
    	if(result.isPresent()){
    		try {
				cd2.afegirProfessor(result.get());
				ObservableList<Professor> profe = FXCollections.observableArrayList(cd2.llistarProfessors());
				taula.setItems(profe);
			} catch (ArgumentErroniException e) {
				e.printStackTrace();
			}
    	}

    }

    @FXML
    void editarProfessor(ActionEvent event) {
    	if(taula.getSelectionModel().getSelectedItem() == null){
    		Alert al = new Alert (AlertType.WARNING);
    		al.setTitle("Atenció!");
    		al.setHeaderText("Seleccione un element a editar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		//construim el diàleg
    		Dialog<Professor> dialog = new Dialog<>();
    		Professor aux = taula.getSelectionModel().getSelectedItem();
        	dialog.setTitle("Editar professor");
        	dialog.setHeaderText("Diàleg per a editar un professor. \nPer a cancel·lar, prema la creu roja.");
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
        	//Li donarem format al resultat	
        	dialog.setResultConverter(new Callback<ButtonType, Professor>() {
        		@Override
        		public Professor call(ButtonType b){
        			if(b == buttonTypeOk){
        				return new Professor(tx1.getText(), tx2.getText(),
        						tx3.getText(), tx4.getText());
        			}
        			return null;
        		} 	
        	});    	
        	Optional<Professor> result = dialog.showAndWait(); //llançament
        	//Guardar resultat
        	if(result.isPresent()){
        		professor = cd2.llistarProfessors();
        		for(int i = 0; i < professor.size(); i++){
        			if(professor.get(i).getNif().equals(result.get().getNif())){ //si el troba
        				professor.remove(i);
        			}
        		}
        		professor.add(result.get());
        		try {
					cd2.editarProfessor(result.get());
    				ObservableList<Professor> profe = FXCollections.observableArrayList(professor);
    				taula.setItems(profe);
				} catch (ArgumentErroniException e) {
					e.printStackTrace();
				}
        	}
    	} // de else (si seleccionat)
    }

    @FXML
    void esborrarProfessor(ActionEvent event) {
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
    				cd2.borrarProfessor(taula.getSelectionModel().getSelectedItem());
    				ObservableList<Professor> profe = FXCollections.observableArrayList(cd2.llistarProfessors());
    				taula.setItems(profe);
    			} catch (ArgumentErroniException e){
    				e.printStackTrace();
    			}
    			
    		}
    	}
    }

}
