package presentacio.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bll.Coordina2;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pojo.Grup;


public class VistaInicialController implements Initializable {
    @FXML private HBox boxInvisible;
    public static Coordina2 cd2 = Coordina2.getInstancia();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    private void apretarLlistats(int qui){
        if(!boxInvisible.getChildren().isEmpty()){
            boxInvisible.getChildren().clear();
        }
        
        //Carreguem els grups tutorials
        List<Grup> listgrups2 = cd2.llistarGrups();
        List<String> listgrups = listGrupToString(listgrups2);
        
        //Anem posant cosetes per a que apareguen al clickar opció
        ChoiceBox<String> cb = new ChoiceBox<>(FXCollections.observableArrayList(listgrups));
        Button bu = new Button ("Ok");
        Label lb = new Label ("Indique el grup tutorial");
        Label lbwarning = new Label("");
        boxInvisible.getChildren().add(lb);
        boxInvisible.getChildren().add(cb);
        boxInvisible.getChildren().add(bu);
        boxInvisible.getChildren().add(lbwarning);
        
        //Quan es pulsa el "ok"
        bu.setOnAction((ActionEvent n) -> {
            if(cb.getValue() != null){
            	
                boxInvisible.getChildren().clear();
                
                //enviar què mostrar 0: tutelats, 1: tutorsalumnes, 2: professors
                VistaNavigator.setDesDeVistaInicialPersona(qui);
                
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
    
    /***********Utilitats per a alleugerar els mètodes**********/
    
    private List<String> listGrupToString(List<Grup> listg){
    	List<String> res = new ArrayList<>();
    	for(Grup g : listg){
    		res.add(g.getNom());
    	}
    	return res;
    }
}
