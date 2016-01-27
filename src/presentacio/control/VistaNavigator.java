package presentacio.control;

import presentacio.control.MainController;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class VistaNavigator {

    public static final String MAIN    = "/presentacio/vista/main.fxml";
    public static final String VISTAINI = "/presentacio/vista/VistaInicial.fxml";
    public static final String VISTASEG = "/presentacio/vista/VistaSegona.fxml";
    private static MainController mainController;
    private static int queMostrar; //Serveix per a quan es migra des de vista inicial a vista segona, què es mostra (alumnes, profes, tutelats...?)
    private static String quinGrup; //Quin grup s'ha triat en la primera finestra
    
    public static void setMainController(MainController mainController) {
        VistaNavigator.mainController = mainController;
    }

    public static void loadVista(String fxml) {
        try {
            mainController.setVista(
                FXMLLoader.load(VistaNavigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /********************************/
    /****MÈTODES DE COMUNICACIO******/
    /********************************/
    
    public static String getDesDeVistaSecundariaGrup(){return quinGrup;}
    public static void setDesDeVistaInicialGrup(String s){quinGrup = s;}
    public static int getDesDeVistaSecundariaPersona(){return queMostrar;}
    public static void setDesDeVistaInicialPersona(int x){queMostrar = x;}

}
