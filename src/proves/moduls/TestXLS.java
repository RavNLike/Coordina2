package proves.moduls;

import java.io.IOException;
import java.sql.SQLException;

import bll.Coordina2;
import jxl.read.biff.BiffException;
import pojo.exceptions.ArgumentErroniException;
import pojo.exceptions.InicialitzatException;

public class TestXLS {

	private static final String profs = "D:\\Archivos\\Dropbox\\Dropbox\\MEU\\CampDeProves\\profes.xls";
	private static final String tutors = "D:\\Archivos\\Dropbox\\Dropbox\\MEU\\CampDeProves\\tutors.xls";
	private static final String alumnes = "D:\\Archivos\\Dropbox\\Dropbox\\MEU\\CampDeProves\\tutelats.xls";
	
	public static void main(String args[]) throws BiffException, InicialitzatException, SQLException, IOException, ArgumentErroniException{
		Coordina2 coo2 = Coordina2.getInstancia();
		coo2.inicialitzarSistema(profs, tutors, alumnes);
	
	}

}
