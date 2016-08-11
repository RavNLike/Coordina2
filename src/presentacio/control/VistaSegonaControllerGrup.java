package presentacio.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import bll.Coordina2;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import pojo.AlumneTutor;
import pojo.Grup;
import pojo.Professor;
import pojo.Tutelat;

public class VistaSegonaControllerGrup implements Initializable{

    @FXML private TableView<Grup> taula;
	@FXML private TableColumn<Grup, AlumneTutor> columnaPrimerAL;
    @FXML private TableColumn<Grup, String> columnaNomGrup;
    @FXML private TableColumn<Grup, AlumneTutor> columnaSegonAL;
    @FXML private TableColumn<Grup, String> columnaProfessor;
    @FXML private TableView<Tutelat> taulaTutelats;
    @FXML private TableColumn<Tutelat, String> columnaTutelats;
    @FXML private TextField barraBuscadora;
    @FXML private Button afegirG, editarG, esborrarG, botoenrere;
    private Coordina2 cd2 = Coordina2.getInstancia();
	private ArrayList<Grup> grup = cd2.llistarGrups();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	ObservableList<Grup> grups = FXCollections.observableArrayList(grup);
    	columnaNomGrup.setCellValueFactory(param -> new SimpleStringProperty((param.getValue()).getNom()));
    	columnaProfessor.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getProfessor().toString()));
    	columnaPrimerAL.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getAlumne1()));
    	columnaSegonAL.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>((param.getValue()).getAlumne2()));
    	taula.setItems(grups);
    	
    	taula.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, 
    			newSelection) -> {plenarTaula(newSelection);});
    	
    	barraBuscadora.textProperty().addListener((ob, vell, nou) -> {filtratge(nou);});
    	
		/* Listeners de les opcions CRUD */
		afegirG.setOnAction((event) -> {afegirGrup();});
		editarG.setOnAction((event) -> {editarGrup();});
		esborrarG.setOnAction((event) -> {esborrarGrup();});
    	
    }
    
    @FXML void enrere(ActionEvent event) {VistaNavigator.loadVista(VistaNavigator.VISTAINI);}

    @FXML
    void afegirGrup() {
    	Dialog<Grup> dialog = new Dialog<>();
    	DialogPane dp = dialog.getDialogPane();
    	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    	dialog.setTitle("Afegir grup");
    	dialog.setHeaderText(null);
    	dialog.setResizable(true);
    	Label lb1 = new Label("Nom:");
    	Label lb2 = new Label("Professor:");
    	Label lb3 = new Label("Alumne Tutor 1:");
    	Label lb4 = new Label("Alumne Tutor 2:");
    	TextField tx1 = new TextField();
    	ComboBox<String> tx2 = new ComboBox<String>();
    	ArrayList<Professor> profes = cd2.llistarProfessors();
    	for(Professor p : profes){
    		tx2.getItems().add(p.getNif() + " - " + p.getNom()+ ", " + p.getCognoms());
    	}
    	ComboBox<String> tx3 = new ComboBox<String>();
    	ComboBox<String> tx4 = new ComboBox<String>();
    	ArrayList<AlumneTutor> alumnestutors = cd2.llistarAlumnesTutors();
    	for(AlumneTutor altutor : alumnestutors){
    		tx3.getItems().add(altutor.getNif() + " - " + altutor.getNom()+ ", " + altutor.getCognoms());
    		tx4.getItems().add(altutor.getNif() + " - " + altutor.getNom()+ ", " + altutor.getCognoms());
    	}
    	GridPane grid = new GridPane();
    	grid.add(lb1, 1, 1);
    	grid.add(lb2, 1, 2);
    	grid.add(lb3, 1, 3);
    	grid.add(lb4, 1, 4);
    	grid.add(tx1, 2, 1);
    	grid.add(tx2, 2, 2);
    	grid.add(tx3, 2, 3);
    	grid.add(tx4, 2, 4);
    	grid.setHgap(1);
    	grid.setVgap(1);
    	dialog.getDialogPane().setContent(grid);
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	dialog.setResultConverter(new Callback<ButtonType, Grup>() {
    		@Override
    		public Grup call(ButtonType b){
    			if(b == buttonTypeOk){
    				
    				Professor p = null;
    				if(tx2.getSelectionModel().getSelectedItem() != null && 
    						!tx2.getSelectionModel().getSelectedItem().isEmpty()){
    					p = cd2.buscarProfessor(tx2.getSelectionModel().getSelectedItem().split(" -")[0]);
    				}
    				AlumneTutor	at1 = null;
    				if(tx3.getSelectionModel().getSelectedItem() != null && 
    						!tx3.getSelectionModel().getSelectedItem().isEmpty()){
    					at1 = cd2.buscarAlumneTutor(tx3.getSelectionModel().getSelectedItem().split(" -")[0]);
    				}
    				AlumneTutor at2 = null;
    				if (tx4.getSelectionModel().getSelectedItem() != null && 
    						!tx4.getSelectionModel().getSelectedItem().isEmpty()){
    					at2 = cd2.buscarAlumneTutor(tx4.getSelectionModel().getSelectedItem().split(" -")[0]);
    				}
    				
    				if (p == null || at1 == null){
    					return null;
    				} else {
    					return new Grup(tx1.getText(), p, at1, at2);
    				}
    				
    			} else {
    				return null;
    			}
    		} 	
    	});    	
    	Optional<Grup> result = dialog.showAndWait();
    	if(result.isPresent()){
    		cd2.afegirGrup(result.get());
    		grup = cd2.llistarGrups();
			ObservableList<Grup> grups = FXCollections.observableArrayList(grup);
			taula.setItems(grups);
    	} else {
    		Alert al = new Alert (AlertType.ERROR);
        	DialogPane dip = al.getDialogPane();
        	dip.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		al.setTitle("Error!");
    		al.setHeaderText("No s'ha creat el grup");
    		al.setContentText("Revise tots els camps");
    		al.showAndWait();
    	}
    }

    @FXML
    void editarGrup() {
    	if(taula.getSelectionModel().getSelectedItem() == null){
    		Alert al = new Alert (AlertType.WARNING);
        	DialogPane dp = al.getDialogPane();
        	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		al.setTitle("Atencio!");
    		al.setHeaderText("Seleccione un element a editar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		Dialog<Grup> dialog = new Dialog<>();
        	DialogPane dp = dialog.getDialogPane();
        	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		Grup aux = taula.getSelectionModel().getSelectedItem();
        	dialog.setTitle("Editar Grup");
        	dialog.setHeaderText(null);
        	dialog.setResizable(true);
        	Label lb1 = new Label("Nom:");
        	Label lb2 = new Label("Professor:");
        	Label lb3 = new Label("Alumne Tutor 1:");
        	Label lb4 = new Label("Alumne Tutor 2:");
        	TextField tx1 = new TextField(aux.getNom());
        	tx1.setEditable(false);
        	tx1.setDisable(true);
        	
        	ComboBox<String> tx2 = new ComboBox<String>();
        	ArrayList<Professor> listprofes = cd2.llistarProfessors();
        	for(Professor p : listprofes){
        		tx2.getItems().add(p.getNif() + " - " + p.getNom() + ", " + p.getCognoms());
        	}
        	tx2.getSelectionModel().select(aux.getProfessor().getNif() + " - " + aux.getProfessor().getNom()
        								+ ", " + aux.getProfessor().getCognoms());
        	
        	ComboBox<String> tx3 = new ComboBox<String>();
        	ArrayList<AlumneTutor> listaltutors = cd2.llistarAlumnesTutors();
        	for(AlumneTutor p : listaltutors){
        		tx3.getItems().add(p.getNif() + " - " + p.getNom() + ", " + p.getCognoms());
        	}
        	tx3.getSelectionModel().select(aux.getAlumne1().getNif() + " - " + aux.getAlumne1().getNom()
        								+ ", " + aux.getAlumne1().getCognoms());
        	
        	ComboBox<String> tx4 = new ComboBox<String>();
        	for(AlumneTutor p : listaltutors){
        		tx4.getItems().add(p.getNif() + " - " + p.getNom() + ", " + p.getCognoms());
        	}
        	if (aux.getAlumne2() != null){
        		tx4.getSelectionModel().select(aux.getAlumne2().getNif() + " - " + aux.getAlumne2().getNom()
						+ ", " + aux.getAlumne2().getCognoms());
        	}

        	GridPane grid = new GridPane();
        	grid.add(lb1, 1, 1);
        	grid.add(lb2, 1, 2);
        	grid.add(lb3, 1, 3);
        	grid.add(lb4, 1, 4);
        	grid.add(tx1, 2, 1);
        	grid.add(tx2, 2, 2);
        	grid.add(tx3, 2, 3);
        	grid.add(tx4, 2, 4);
        	grid.setHgap(1);
        	grid.setVgap(1);
        	dialog.getDialogPane().setContent(grid);
        	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        	dialog.setResultConverter(new Callback<ButtonType, Grup>() {
        		@Override
        		public Grup call(ButtonType b){
        			Professor p = null; 
        			AlumneTutor al1 = null, al2 = null;
        			if(tx2.getSelectionModel().getSelectedItem() != null && 
    						!tx2.getSelectionModel().getSelectedItem().isEmpty()){
    					p = cd2.buscarProfessor(tx2.getSelectionModel().getSelectedItem().split(" -")[0]);
    				}
        			if(tx3.getSelectionModel().getSelectedItem() != null && 
    						!tx3.getSelectionModel().getSelectedItem().isEmpty()){
    					al1 = cd2.buscarAlumneTutor(tx3.getSelectionModel().getSelectedItem().split(" -")[0]);
    				}
        			if (tx4.getSelectionModel().getSelectedItem() != null && 
    						!tx4.getSelectionModel().getSelectedItem().isEmpty()){
    					al2 = cd2.buscarAlumneTutor(tx4.getSelectionModel().getSelectedItem().split(" -")[0]);
    				}
        			if(b == buttonTypeOk && p != null && al1 != null){
        				return new Grup(tx1.getText(), p, al1, al2);
        			} else {
        				return null;
        			}
        		} 	
        	});    	
        	Optional<Grup> result = dialog.showAndWait();
			if(result.isPresent()){
				/*grup = cd2.llistarGrups();
        		for(int i = 0; i < grup.size(); i++){
        			if(grup.get(i).getNom().equals(result.get().getNom())){ //si el troba
        				grup.remove(i);
        			}
        		}
        		grup.add(result.get());*/
				cd2.editarGrup(result.get());
				grup = cd2.llistarGrups();
				ObservableList<Grup> altu = FXCollections.observableArrayList(grup);
				taula.setItems(altu);
        		taula.getColumns().get(0).setVisible(false);
				taula.getColumns().get(0).setVisible(true);
        	} else {
        		Alert al = new Alert (AlertType.ERROR);
            	DialogPane dip = al.getDialogPane();
            	dip.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
        		al.setTitle("Error!");
        		al.setHeaderText("No s'ha editat el grup");
        		al.setContentText("Revise tots els camps");
        		al.showAndWait();
        	}
    	}
    }

    @FXML
    void esborrarGrup() {
    	Grup aux = taula.getSelectionModel().getSelectedItem(); 
    	if(aux == null){
    		Alert al = new Alert (AlertType.WARNING);
        	DialogPane dp = al.getDialogPane();
        	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		al.setTitle("Atencio!");
    		al.setHeaderText("Seleccione un element a esborrar");
    		al.setContentText(null);
    		al.showAndWait();
    	} else {
    		Alert al = new Alert (AlertType.WARNING);
        	DialogPane dp = al.getDialogPane();
        	dp.getStylesheets().add(getClass().getResource("dialogs.css").toExternalForm());
    		al.setTitle("Atencio!");
    		al.setHeaderText("Esta segur que vol esborrar l'element?");
    		al.setContentText(null);
    		Optional<ButtonType> result = al.showAndWait();
    		if(result.isPresent() && result.get() == ButtonType.OK){
    			cd2.borrarGrup(aux);
    			grup = cd2.llistarGrups();
    			ObservableList<Grup> grups = FXCollections.observableArrayList(grup);
    			taula.setItems(grups);
    		}
    	}
    }

    public void filtratge(String nou){
    	ObservableList<Grup> grups = FXCollections.observableArrayList(grup);
    	FilteredList<Grup> filteredData = new FilteredList<>(grups, p -> true);
    	
		filteredData.setPredicate(grup -> {
			if (nou == null || nou.isEmpty()) {
				return true;
			}
			String minuscules = nou.toLowerCase();

			if (grup.getNom().toLowerCase().contains(minuscules)) {
				return true;
			}	
			return false;
		});

    	SortedList<Grup> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(taula.comparatorProperty());
		taula.setItems(sortedData);
    }

    public void plenarTaula(Grup g){
    	ObservableList<Tutelat> personesgrup = FXCollections.observableArrayList();
    	
    	if(g != null){
    		
    		List<Tutelat> llistapersgrup = cd2.obtindreTutelatsPerGrup(g.getNom());
    		
    		for(Tutelat t : llistapersgrup){
    			personesgrup.add(t);
    		}
			columnaTutelats.setCellValueFactory(param -> new ReadOnlyObjectWrapper <>(param.getValue().getNif() 
					+ " - " + param.getValue().getCognoms() + ", "+ param.getValue().getNom()));
			taulaTutelats.setItems(personesgrup);
		}
    }
}
