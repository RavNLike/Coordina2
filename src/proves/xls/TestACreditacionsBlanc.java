package proves.xls;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import bll.Acreditador;

public class TestACreditacionsBlanc {
	public static void main (String args[]){
		Acreditador acr = Acreditador.getInstancia();
		try {
			acr.acreditarBlanc("/tmp");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
