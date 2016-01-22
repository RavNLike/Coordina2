package POJO;

public abstract class Persona {
	private String nif;
	private String nom;
	private String cognoms;
	private String correu_upv;
	
	//constructor
	
	
	//Getters i setters
	public String getNif() {
		return nif;
	}
	public Persona(String nif, String nom, String cognoms, String correu_upv) {
		super();
		this.nif = nif;
		this.nom = nom;
		this.cognoms = cognoms;
		this.correu_upv = correu_upv;
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
	
	@Override
	public String toString() {
		return this.cognoms+", "+this.nom+" - "+this.nif +" - "+this.correu_upv;
	}
	
	public boolean equals(Persona o){
		return this.nif.equalsIgnoreCase(o.nif);
	}
	
	
	
}
