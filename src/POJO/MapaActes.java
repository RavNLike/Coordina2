package POJO;

import java.util.HashMap;


public class MapaActes{

	private HashMap <Persona, Integer> mapa;
	
	public MapaActes(){
		mapa = new HashMap<Persona, Integer>();
	}
	
	public void sumarAssistencia(Persona p){
		//obtin el nombre de voltes que apareix 
		Integer n = mapa.get(p);
		if(n==null)
			n=0;
		mapa.put(p, ++n);
	}
}
