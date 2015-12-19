package UTILS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class LectorFitxers {
	private static String bdlocation = "rutabd.txt";
			
	public static String rutabd(){
		Scanner sc;
		String res = "";
		try {
			sc = new Scanner(new File(bdlocation));
			//en la primera linea es troba la localitzacio de la bd
			res = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
