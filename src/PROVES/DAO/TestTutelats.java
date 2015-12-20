package PROVES.DAO;

import BLL.Coordina2;
import DAO.GrupDAO;
import DAO.TutelatDAO;
import POJO.*;

public class TestTutelats {

	public static void main (String args[]){
		Coordina2 test = Coordina2.getInstancia();
		System.out.println(test.llistarProfessors());
		System.out.println(test.llistarAlumnesTutors());
		System.out.println(test.llistarGrups());
	}
	
}
