package dao.dto;

public class GrupDTO {
	private String nom;
	private String professor1;
	private String professor2;
	private String alumne1;
	private String alumne2;
	private String alumne3;
	private String alumne4;
	
	public GrupDTO(String nom, String professor1, String professor2, String alumne1, String alumne2, String alumne3,
			String alumne4) {
		super();
		this.nom = nom;
		this.professor1 = professor1;
		this.professor2 = professor2;
		this.alumne1 = alumne1;
		this.alumne2 = alumne2;
		this.alumne3 = alumne3;
		this.alumne4 = alumne4;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getProfessor1() {
		return professor1;
	}

	public void setProfessor1(String professor1) {
		this.professor1 = professor1;
	}

	public String getProfessor2() {
		return professor2;
	}

	public void setProfessor2(String professor2) {
		this.professor2 = professor2;
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

	public String getAlumne3() {
		return alumne3;
	}

	public void setAlumne3(String alumne3) {
		this.alumne3 = alumne3;
	}

	public String getAlumne4() {
		return alumne4;
	}

	public void setAlumne4(String alumne4) {
		this.alumne4 = alumne4;
	}
	
	
	

	
	
}
