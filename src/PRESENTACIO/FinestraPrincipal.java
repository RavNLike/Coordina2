package PRESENTACIO;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FinestraPrincipal implements Initializable{
	@FXML
	public Button llistarMembresGrup;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image iconBut1 = null;
		try{
			iconBut1 = new Image(getClass().getResourceAsStream("iconollistar.jpg"));
		} catch (NullPointerException e){System.out.println("NULLPOINTER MAN");}
		llistarMembresGrup.setGraphic(new ImageView(iconBut1));
	}
}
