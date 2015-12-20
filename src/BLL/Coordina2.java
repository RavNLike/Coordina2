package BLL;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.GrupDAO;
import DAO.TutelatDAO;
import DAO.TutorDAO;
import DAO.DTO.GrupDTO;
import DAO.DTO.TutelatDTO;
import POJO.AlumneTutor;
import POJO.Grup;
import POJO.Professor;
import POJO.Tutelat;
import POJO.EXCEPTIONS.ArgumentErroniException;

public class Coordina2 {
	
	private static Coordina2 INSTANCIA = new Coordina2();

	/*******************************************
	 * Arrays en memoria sobre els que treballar
	 *******************************************/
	
	private ArrayList<POJO.Professor> professors;
	private ArrayList<POJO.AlumneTutor> alumnesTutors;
	private ArrayList<POJO.Tutelat> tutelats;
	private ArrayList<POJO.Grup> grups;
	
	/*************************************
	 * Objectes dao amb els que treballar
	 **************************************/
	
	private TutorDAO daotutor = new TutorDAO();
	private TutelatDAO daotutelat = new TutelatDAO();
	private GrupDAO daogrup = new GrupDAO();
	
	/************************
	 * Patro singleton
	 ***********************/
	private Coordina2(){
		try {
			carregarSistema();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static  Coordina2 getInstancia(){
		return INSTANCIA;
	}
	
	/************************
	 * CARREGA DEL SISTEMA
	 * @throws SQLException 
	 ***********************/
	
	
	private void carregarSistema() throws SQLException{
		//independents
		carregarProfessors();
		carregarAlumnesTutors();
		//grup depen del anteriors
		carregarGrups();
		//tutelat depen de grup
		carregarTutelats();
	}
	
	private void carregarProfessors() throws SQLException{
		this.professors = daotutor.llistarProfessors();
	}
	
	private void carregarAlumnesTutors() throws SQLException{
		this.alumnesTutors = daotutor.llistarAlumnesTutors();
	}
	
	private void carregarGrups() throws SQLException{
		grups = new ArrayList<>();
		for(GrupDTO dto : daogrup.llistarGrups()){
			grups.add(dtoAgrup(dto));
		}
	}
	
	private void carregarTutelats() throws SQLException{
		tutelats = new ArrayList<>();
		for(TutelatDTO dto : daotutelat.llistarTutelats()){
			tutelats.add(dtoAtutelat(dto));
		}
	}
	
	/************************
	 * OPERACIONS SOBRE PROFESSORS
	 * @throws ArgumentErroniException 
	 ************************/
	public void afegirProfessor(Professor p) throws ArgumentErroniException{
		//maxima coherencia bd-memoria
		//afig primer a la bd, si entra afig també a l'array
		boolean bandera = daotutor.afegir(p);
		if(bandera) professors.add(p);
	}
	
	public ArrayList<Professor> llistarProfessors(){
		return professors;
	}
	
	public Professor buscarProfessor(String nif){
		for(Professor p : professors){
			if(p.getNif().trim().equalsIgnoreCase(nif)){
				return p;
			}
		}
		return null;
	}
	
	/**********************************
	 * OPERACIONS SOBRE ALUMNES TUTORS
	 * @throws ArgumentErroniException 
	 *********************************/
	
	public void afegirAlumneTutor(AlumneTutor a) throws ArgumentErroniException{
		//mateix mecanisme que amb els professors
		boolean bandera = daotutor.afegir(a);
		if(bandera) alumnesTutors.add(a);
		
	}
	
	public ArrayList<AlumneTutor> llistarAlumnesTutors(){
		return alumnesTutors;
	}
	
	public AlumneTutor buscarAlumneTutor(String nif){
		for(AlumneTutor a : alumnesTutors){
			if(a.getNif().trim().equalsIgnoreCase(nif)){
				return a;
			}
		}
		return null;
	}
	
	/************************
	 * OPERACIONS SOBRE GRUPS
	 ************************/
	public void afegirGrup(Grup g){
		boolean bandera = daogrup.afegir(g);
		if(bandera) grups.add(g);
	}
	
	public ArrayList<Grup> llistarGrups(){
		return grups;
	}
	
	public Grup buscarGrup(String nom){
		for(Grup g : grups){
			if(g.getNom().trim().equalsIgnoreCase(nom)){
				return g;
			}
		}
		return null;
	}
	
	/****************************
	 * OPERACIONS SOBRE TUTELATS
	 ***************************/
	public void afegirTutelat(Tutelat t){
		boolean bandera = daotutelat.afegir(t);
		if(bandera) tutelats.add(t);
	}
	
	public ArrayList<Tutelat> llistarTutelats(){
		return tutelats;
	}
	
	public Tutelat buscarTutelat(String nif){
		for(Tutelat t : tutelats){
			if(t.getNif().trim().equalsIgnoreCase(nif)){
				return t;
			}
		}
		return null;
	}
	
	
	/************************
	 * TRADUCCIONS DTO-NORMAL
	 ************************/
	
	public Grup dtoAgrup(GrupDTO dto){
		String nom = dto.getNom();
		Professor pAux = null;
		AlumneTutor aux1 = null;
		AlumneTutor aux2 = null;
		for(Professor p : professors){
			if(p.getNif().trim().equalsIgnoreCase(dto.getProfessor())){
				pAux = p;
			}
		}
		for(AlumneTutor a: alumnesTutors){
			if(a.getNif().trim().equalsIgnoreCase(dto.getAlumne1())){
				aux1 = a;
			}
			if(a.getNif().trim().equalsIgnoreCase(dto.getAlumne2())){
				aux2 = a;
			}
		}
		
		return new Grup(nom, pAux, aux1, aux2);
	}
	
	public Tutelat dtoAtutelat(TutelatDTO dto){
		
		 String nif = dto.getNif();
		 String nom = dto.getNom();
		 String cognoms = dto.getCognoms();
		 String correu_upv = dto.getCorreu_upv();
		 String correu_personal = dto.getCorreu_personal();
		 Grup grup_patu = null;
		 String grup_matricula = dto.getGrup_matricula();
		 String mobil = dto.getMobil();
		 
		 for(Grup g : grups){
			 if(g.getNom().trim().equalsIgnoreCase(dto.getGrup_patu())){
				 grup_patu = g;
			 }
		 }
		 
		 return new Tutelat(nif, nom, cognoms, correu_upv, correu_personal, grup_patu, grup_matricula, mobil);
	}
}
