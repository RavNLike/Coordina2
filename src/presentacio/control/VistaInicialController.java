package presentacio.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class VistaInicialController implements Initializable {
    @FXML private HBox boxInvisible;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Aqui es carregaria la llista de TOTS els grups tutorials: G01-G33 Per exemple
    }
    
    private void apretarLlistats(int qui){
        if(!boxInvisible.getChildren().isEmpty()){
            boxInvisible.getChildren().clear();
        }
        //De prova faig una llista amb 3 grups tutorials
        ChoiceBox<String> cb = new ChoiceBox<>(FXCollections.observableArrayList("G01", "G02", "G03"));
        Button bu = new Button ("Ok");
        Label lb = new Label ("Indique el grup tutorial");
        Label lbwarning = new Label("");
        boxInvisible.getChildren().add(lb);
        boxInvisible.getChildren().add(cb);
        boxInvisible.getChildren().add(bu);
        boxInvisible.getChildren().add(lbwarning);
        bu.setOnAction((ActionEvent n) -> {
            if(cb.getValue() != null){
                boxInvisible.getChildren().clear();
                switch(qui){
                    case 0: break; //Tutelats
                    case 1: break; //Tutors alumnes
                    case 2: break; //Professors
                } 
                
                //I canviem de finestra
                VistaNavigator.loadVista(VistaNavigator.VISTASEG);
            } else {
                lbwarning.setText("Seleccione el grup");
            }
        });
    }
    
    @FXML public void eixir(){Platform.exit();}
    @FXML public void apretarTutelats(){apretarLlistats(0);}
    @FXML public void apretarTutorsAlumnes(){apretarLlistats(1);}
    @FXML public void apretarProfessors(){apretarLlistats(2);}
}
