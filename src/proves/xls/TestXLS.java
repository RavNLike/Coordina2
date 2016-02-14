package proves.xls;

import java.io.FileNotFoundException;
import java.io.IOException;

import bll.Coordina2;
import jxl.read.biff.BiffException;

public class TestXLS {

	static String entrades[] = { "septembre.xls", "octubre.xls", "noovembre.xls", "decembre.xls", "gener.xls" };
	static String eixida = "solucio.txt";
	public static void main(String[] args) throws FileNotFoundException, BiffException, IOException {
		Coordina2 bll = Coordina2.getInstancia();
		bll.comptaDNIs(entrades, eixida);
	}

}
