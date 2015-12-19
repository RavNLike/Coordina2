package POJO;

import java.util.ArrayList;

public class Grup {
	private Professor professor;
	private AlumneTutor alumne1;
	private AlumneTutor alumne2;
	ArrayList<Tutelat> tutelas;
	
	public Grup(Professor p, AlumneTutor al1){
		this.professor = p;
		this.alumne1 = al1;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public AlumneTutor getAlumne1() {
		return alumne1;
	}

	public void setAlumne1(AlumneTutor alumne1) {
		this.alumne1 = alumne1;
	}

	public AlumneTutor getAlumne2() {
		return alumne2;
	}

	public void setAlumne2(AlumneTutor alumne2) {
		this.alumne2 = alumne2;
	}

	public ArrayList<Tutelat> getTutelas() {
		return tutelas;
	}

	public void setTutelas(ArrayList<Tutelat> tutelas) {
		this.tutelas = tutelas;
	}
	
	
}
