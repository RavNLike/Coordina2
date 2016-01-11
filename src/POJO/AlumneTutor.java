package POJO;

public class AlumneTutor extends Persona{

	public AlumneTutor(String nif, String nom, String cognoms, String correu_upv) {
		super(nif, nom, cognoms, correu_upv);
	}
	
	public boolean equals (Object o){
		if(o instanceof AlumneTutor){
			return ((AlumneTutor)o).getNif().equals(this.getNif());
		}
		return false;
	}

}
