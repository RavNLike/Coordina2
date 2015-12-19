package POJO;

public class Grup {
	private String nom;
	private Professor professor;
	private AlumneTutor alumne1;
	private AlumneTutor alumne2;

	public Grup(String nom, Professor p, AlumneTutor al1) {
		this.nom = nom;
		this.professor = p;
		this.alumne1 = al1;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

}
