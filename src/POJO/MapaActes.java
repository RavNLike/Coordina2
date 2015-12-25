package POJO;

import java.util.ArrayList;
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
	
	public ArrayList<Persona> claus(){
		ArrayList<Persona> llista = new ArrayList<>();
		for(Persona aux : mapa.keySet()){
			llista.add(aux);
		}
		return llista;
	}
	
	/*
	 * TODO obtindre la infor per les distintes categories de persona
	 * esperar a saber el format d'entrega de les actes
	 */
}
