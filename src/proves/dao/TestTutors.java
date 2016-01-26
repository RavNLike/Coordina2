package proves.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.TutorDAO;
import pojo.AlumneTutor;
import pojo.Professor;
import pojo.exceptions.ArgumentErroniException;

public class TestTutors {
	
	public void test() throws ArgumentErroniException, SQLException{
		//afig un professoior i un tutor
		TutorDAO dao = new TutorDAO();
		//AlumneTutor a1 = new AlumneTutor("73659660K", "Vicent", "Blanes", "viblasel@inf.upv.es");
		Professor p1 = new Professor("99999999K", "Silvia", "Terrasa", "sterrasa@disc.upv.es");
		System.out.println(dao.editar(p1));
		ArrayList<AlumneTutor> pers = dao.llistarAlumnesTutors();
		for(AlumneTutor a: pers){
			System.out.println(a);
		}
		ArrayList<Professor> profs = dao.llistarProfessors();
		for(Professor a: profs){
			System.out.println(a);
		}
		
	}
}
