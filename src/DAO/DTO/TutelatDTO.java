package DAO.DTO;

public class TutelatDTO {
	private String nif;
	private String nom;
	private String cognoms;
	private String correu_upv;
	private String correu_personal;
	private String grup_patu;
	private String grup_matricula;
	private String mobil;
	
	public TutelatDTO(String nif, String nom, String cognoms, String correu_upv, String correu_personal,
			String grup_patu, String grup_matricula, String mobil) {
		super();
		this.nif = nif;
		this.nom = nom;
		this.cognoms = cognoms;
		this.correu_upv = correu_upv;
		this.correu_personal = correu_personal;
		this.grup_patu = grup_patu;
		this.grup_matricula = grup_matricula;
		this.mobil = mobil;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognoms() {
		return cognoms;
	}

	public void setCognoms(String cognoms) {
		this.cognoms = cognoms;
	}

	public String getCorreu_upv() {
		return correu_upv;
	}

	public void setCorreu_upv(String correu_upv) {
		this.correu_upv = correu_upv;
	}

	public String getCorreu_personal() {
		return correu_personal;
	}

	public void setCorreu_personal(String correu_personal) {
		this.correu_personal = correu_personal;
	}

	public String getGrup_patu() {
		return grup_patu;
	}

	public void setGrup_patu(String grup_patu) {
		this.grup_patu = grup_patu;
	}

	public String getGrup_matricula() {
		return grup_matricula;
	}

	public void setGrup_matricula(String grup_matricula) {
		this.grup_matricula = grup_matricula;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	
	
	
	
}
