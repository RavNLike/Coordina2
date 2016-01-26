package dao.dto;

public class GrupDTO {
	private String nom;
	private String professor;
	private String alumne1;
	private String alumne2;
	
	public GrupDTO(String nom, String professor, String alumne1, String alumne2) {
		super();
		this.nom = nom;
		this.professor = professor;
		this.alumne1 = alumne1;
		this.alumne2 = alumne2;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getAlumne1() {
		return alumne1;
	}

	public void setAlumne1(String alumne1) {
		this.alumne1 = alumne1;
	}

	public String getAlumne2() {
		return alumne2;
	}

	public void setAlumne2(String alumne2) {
		this.alumne2 = alumne2;
	}
	
	
}
