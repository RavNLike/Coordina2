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
	
	public Grup(String nom, Professor p, AlumneTutor al1, AlumneTutor al2) {
		this.nom = nom;
		this.professor = p;
		this.alumne1 = al1;
		this.alumne2 = al2;
	}
	
	public Grup(String nom){
		this.nom=nom;
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

	@Override
	public String toString() {
		return "Grup [nom=" + nom + ", professor=" + professor + ", alumne1=" + alumne1 + ", alumne2=" + alumne2 + "]";
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Grup){
			return this.nom.trim().equalsIgnoreCase(((Grup)o).getNom().trim());
		}
		return false;
	}

}
