package pojo;

public class Tutelat extends Persona {

	private String correu_personal;
	private Grup grup_patu;
	private String grup_matricula;
	private String mobil;

	public Tutelat(String nif, String nom, String cognoms, String correu_upv, String correu_personal, Grup grup_patu,
			String grup_matricula, String mobil) {

		super(nif, nom, cognoms, correu_upv);
		this.correu_personal = correu_personal;
		this.grup_patu = grup_patu;
		this.grup_matricula = grup_matricula;
		this.mobil = mobil;

	}

	//getters i setters
	public String getCorreu_personal() {
		return correu_personal;
	}

	public void setCorreu_personal(String correu_personal) {
		this.correu_personal = correu_personal;
	}

	public Grup getGrup_patu() {
		return grup_patu;
	}

	public void setGrup_patu(Grup grup_patu) {
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
	
	
	public String info_completa(){
		return super.getCognoms()+", "+super.getNom()+" - "+super.getNif();
	}
	
	@Override
	public String toString(){
		return super.getCognoms()+", "+super.getNom();
	}
	
	

}
