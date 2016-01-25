package presentacio;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Vista Viewer");
        Scene sc = new Scene(loadMainPane());
        stage.setScene(sc);
        stage.show();
    }
    
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
            getClass().getResourceAsStream(
                VistaNavigator.MAIN
            )
        );

        MainController mainController = loader.getController();

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(VistaNavigator.VISTAINI);

        return mainPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}