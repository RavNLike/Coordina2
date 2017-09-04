package pojo;

public class Grup {
	private String nom;
	private Professor professor1;
	private Professor professor2;
	private AlumneTutor alumne1;
	private AlumneTutor alumne2;
	private AlumneTutor alumne3;
	private AlumneTutor alumne4;
	public final static String NO_ASSIGNAT = "NO ASSIGNAT"; 
	
	public Grup(String nom) {
		this.nom = nom;
	}

	public Grup(String nom, Professor p, AlumneTutor al1) {
		this.nom = nom;
		this.professor1 = p;
		this.alumne1 = al1;
	}
	
	public Grup(String nom, Professor p, AlumneTutor al1, AlumneTutor al2) {
		this(nom, p, al1);
		this.alumne2 = al2;
	}
	
	public Grup (String nom, Professor p1, Professor p2, AlumneTutor al1, 
			AlumneTutor al2, AlumneTutor al3, AlumneTutor al4) {
		this(nom, p1, al1);
		this.professor2 = p2;
		this.alumne2 = al2;
		this.alumne3 = al3;
		this.alumne4 = al4;
		
		
	}

	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Professor getProfessor1() {
		return professor1;
	}

	public void setProfessor1(Professor professor1) {
		this.professor1 = professor1;
	}

	public Professor getProfessor2() {
		return professor2;
	}

	public void setProfessor2(Professor professor2) {
		this.professor2 = professor2;
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

	public AlumneTutor getAlumne3() {
		return alumne3;
	}

	public void setAlumne3(AlumneTutor alumne3) {
		this.alumne3 = alumne3;
	}

	public AlumneTutor getAlumne4() {
		return alumne4;
	}

	public void setAlumne4(AlumneTutor alumne4) {
		this.alumne4 = alumne4;
	}

	@Override
	public String toString() {
		return "Grup [nom=" + nom + ", professor1=" + professor1 + ", professor2=" + professor2 + ", alumne1=" + alumne1
				+ ", alumne2=" + alumne2 + ", alumne3=" + alumne3 + ", alumne4=" + alumne4 + "]";
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Grup){
			return this.nom.trim().equalsIgnoreCase(((Grup)o).getNom().trim());
		}
		return false;
	}

}
