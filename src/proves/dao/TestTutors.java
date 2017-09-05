package proves.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.GrupDAO;
import dao.TutelatDAO;
import dao.TutorDAO;
import pojo.AlumneTutor;
import pojo.Grup;
import pojo.Professor;
import pojo.Tutelat;
import pojo.exceptions.ArgumentErroniException;

public class TestTutors {
	
	public void test() throws ArgumentErroniException, SQLException{
		//afig un professoior i un tutor
		TutorDAO daotutor = new TutorDAO();
		TutelatDAO daotutelat = new TutelatDAO();
		GrupDAO gdao = new GrupDAO();
		AlumneTutor a1 = new AlumneTutor("73659660K", "Vicent", "Blanes", "viblasel@inf.upv.es");
		AlumneTutor a2 = new AlumneTutor("736", "Lorenso", "Van Boi", "viblasel@inf.upv.es");
		Professor p1 = new Professor("99999999K", "Silvia", "Terrasa", "sterrasa@disc.upv.es");
		//System.out.println(dao.editar(p1));
		Grup g = new Grup("G00", p1, a2);
		Tutelat t1 = new Tutelat("77", "Pep", "Pepot", "", "", g, "", "");
		Tutelat t2 = new Tutelat("79", "Pepet", "Pepot", "", "", g, "", "");
		
		
		daotutor.afegir(p1);
		daotutor.afegir(a1);
		daotutor.afegir(a2);
		daotutelat.afegir(t1);
		daotutelat.afegir(t2);
		gdao.afegir(g);
		
		
		g.setAlumne1(a1);
		g.setAlumne2(a1);
		g.setAlumne3(a1);
		g.setAlumne4(a1);
		
		ArrayList<AlumneTutor> pers = daotutor.llistarAlumnesTutors();
		for(AlumneTutor a: pers){
			System.out.println(a);
		}
		ArrayList<Professor> profs = daotutor.llistarProfessors();
		for(Professor a: profs){
			System.out.println(a);
		}
		
		gdao.editar(g);
		
	}
}
