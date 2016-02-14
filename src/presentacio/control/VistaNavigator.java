package presentacio.control;

import presentacio.control.MainController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class VistaNavigator {

    public static final String MAIN    = "/presentacio/vista/main.fxml";
    public static final String VISTAINI = "/presentacio/vista/VistaInicial.fxml";
    public static final String VISTASEGTUTELAT = "/presentacio/vista/VistaSegonaTutelat.fxml";
    public static final String VISTASEGALTUT = "/presentacio/vista/VistaSegonaAlumneTutor.fxml";
    public static final String VISTASEGPROF = "/presentacio/vista/VistaSegonaProfessor.fxml";
    public static final String VISTASEGRUP = "/presentacio/vista/VistaSegonaGrup.fxml";
    
    private static MainController mainController;
    
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
}
