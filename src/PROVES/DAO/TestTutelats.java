package PROVES.DAO;

import DAO.GrupDAO;
import DAO.TutelatDAO;
import POJO.*;

public class TestTutelats {

	public static void main (String args[]){
		AlumneTutor a1 = new AlumneTutor("73659660K", "Vicent", "Blanes", "viblasel@inf.upv.es");
		Professor p1 = new Professor("99999999K", "Silvia", "Terrasa", "sterrasa@disc.upv.es");
		Grup g1 = new Grup("G01",p1,a1);
		Tutelat t1 = new Tutelat("69696969J", "Pepet", "Lascollera", "pepet@inf.upv.es", "pepetxulot@hotmail.es", 
				g1, "1A2", "696696969");
		
		GrupDAO gdao = new GrupDAO();
		TutelatDAO tdao = new TutelatDAO();
		
		//System.out.println(gdao.afegir(g1));
		System.out.println(tdao.afegir(t1));
	}
	
}
