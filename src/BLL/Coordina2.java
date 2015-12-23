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
import POJO.Persona;
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
	private Coordina2() {
		try {
			carregarSistema();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Coordina2 getInstancia() {
		return INSTANCIA;
	}

	/************************
	 * CARREGA DEL SISTEMA
	 * 
	 * @throws SQLException
	 ***********************/

	private void carregarSistema() throws SQLException {
		// independents
		carregarProfessors();
		carregarAlumnesTutors();
		// grup depen del anteriors
		carregarGrups();
		// tutelat depen de grup
		carregarTutelats();
	}

	private void carregarProfessors() throws SQLException {
		this.professors = daotutor.llistarProfessors();
	}

	private void carregarAlumnesTutors() throws SQLException {
		this.alumnesTutors = daotutor.llistarAlumnesTutors();
	}

	private void carregarGrups() throws SQLException {
		grups = new ArrayList<>();
		for (GrupDTO dto : daogrup.llistarGrups()) {
			grups.add(dtoAgrup(dto));
		}
	}

	private void carregarTutelats() throws SQLException {
		tutelats = new ArrayList<>();
		for (TutelatDTO dto : daotutelat.llistarTutelats()) {
			tutelats.add(dtoAtutelat(dto));
		}
	}

	/************************
	 * OPERACIONS SOBRE PROFESSORS
	 * 
	 * @throws ArgumentErroniException
	 ************************/
	public void afegirProfessor(Professor p) throws ArgumentErroniException {
		// maxima coherencia bd-memoria
		// afig primer a la bd, si entra afig també a l'array
		boolean bandera = daotutor.afegir(p);
		if (bandera)
			professors.add(p);
	}

	public ArrayList<Professor> llistarProfessors() {
		return professors;
	}

	public Professor buscarProfessor(String nif) {
		for (Professor p : professors) {
			if (p.getNif().trim().equalsIgnoreCase(nif)) {
				return p;
			}
		}
		return null;
	}

	public void borrarProfessor(Professor p) throws ArgumentErroniException {
		// busqueda i borrat a ma
		for (Professor aux : professors) {
			if (aux.getNif().equalsIgnoreCase(p.getNif())) {
				boolean bandera = daotutor.esborrar(p);
				if (bandera)
					professors.remove(aux);
			}
		}
	}

	public void editarProfessor(Professor p) throws ArgumentErroniException {
		// busca el objecte antic (aux)
		for (Professor aux : professors) {
			if (aux.getNif().equalsIgnoreCase(p.getNif())) {
				// si conseguisc editarlo en la bd, lleve el vell i fique el nou
				boolean bandera = daotutor.editar(p);
				if (bandera) {
					professors.remove(aux);
					professors.add(p);
				}
			}
		}
	}

	/**********************************
	 * OPERACIONS SOBRE ALUMNES TUTORS
	 * 
	 * @throws ArgumentErroniException
	 *********************************/

	public void afegirAlumneTutor(AlumneTutor a) throws ArgumentErroniException {
		// mateix mecanisme que amb els professors
		boolean bandera = daotutor.afegir(a);
		if (bandera)
			alumnesTutors.add(a);

	}

	public ArrayList<AlumneTutor> llistarAlumnesTutors() {
		return alumnesTutors;
	}

	public AlumneTutor buscarAlumneTutor(String nif) {
		for (AlumneTutor a : alumnesTutors) {
			if (a.getNif().trim().equalsIgnoreCase(nif)) {
				return a;
			}
		}
		return null;
	}

	public void borrarAlumneTutor(AlumneTutor a) throws ArgumentErroniException {
		for (AlumneTutor aux : alumnesTutors) {
			if (aux.getNif().equalsIgnoreCase(a.getNif())) {
				boolean bandera = daotutor.esborrar(a);
				if (bandera)
					alumnesTutors.remove(aux);
			}
		}
	}

	public void editarAlumneTutor(AlumneTutor a) throws ArgumentErroniException {
		for (AlumneTutor aux : alumnesTutors) {
			if (aux.getNif().equalsIgnoreCase(a.getNif())) {
				boolean bandera = daotutor.editar(a);
				if (bandera) {
					alumnesTutors.remove(aux);
					alumnesTutors.add(a);
				}
			}
		}
	}

	/************************
	 * OPERACIONS SOBRE GRUPS
	 ************************/
	public void afegirGrup(Grup g) {
		boolean bandera = daogrup.afegir(g);
		if (bandera)
			grups.add(g);
	}

	public ArrayList<Grup> llistarGrups() {
		return grups;
	}

	public Grup buscarGrup(String nom) {
		for (Grup g : grups) {
			if (g.getNom().trim().equalsIgnoreCase(nom)) {
				return g;
			}
		}
		return null;
	}

	public void borrarGrup(Grup g) {
		for (Grup aux : grups) {
			if (g.getNom().equalsIgnoreCase(aux.getNom())) {
				boolean bandera = daogrup.esborrar(g);
				if (bandera)
					grups.remove(aux);
			}
		}
	}

	public void editarGrup(Grup g) {
		for (Grup aux : grups) {
			if (g.getNom().equalsIgnoreCase(aux.getNom())) {
				boolean bandera = daogrup.editar(g);
				if (bandera) {
					grups.remove(aux);
					grups.add(g);
				}
			}
		}
	}

	/****************************
	 * OPERACIONS SOBRE TUTELATS
	 ***************************/
	public void afegirTutelat(Tutelat t) {
		boolean bandera = daotutelat.afegir(t);
		if (bandera)
			tutelats.add(t);
	}

	public ArrayList<Tutelat> llistarTutelats() {
		return tutelats;
	}

	public Tutelat buscarTutelat(String nif) {
		for (Tutelat t : tutelats) {
			if (t.getNif().trim().equalsIgnoreCase(nif)) {
				return t;
			}
		}
		return null;
	}

	public void borrarTutelat(Tutelat t) {
		for (Tutelat aux : tutelats) {
			if (aux.getNif().equalsIgnoreCase(t.getNif())) {
				boolean bandera = daotutelat.esborrar(t);
				if (bandera)
					tutelats.remove(aux);
			}
		}
	}

	public void editarTutelat(Tutelat t) {
		for (Tutelat aux : tutelats) {
			if (aux.getNif().equalsIgnoreCase(t.getNif())) {
				boolean bandera = daotutelat.esborrar(t);
				if (bandera) {
					tutelats.remove(aux);
					tutelats.add(t);
				}
			}
		}
	}

	/************************
	 * TRADUCCIONS DTO-NORMAL
	 ************************/

	public Grup dtoAgrup(GrupDTO dto) {
		String nom = dto.getNom();
		Professor pAux = null;
		AlumneTutor aux1 = null;
		AlumneTutor aux2 = null;
		for (Professor p : professors) {
			if (p.getNif().trim().equalsIgnoreCase(dto.getProfessor())) {
				pAux = p;
			}
		}
		for (AlumneTutor a : alumnesTutors) {
			if (a.getNif().trim().equalsIgnoreCase(dto.getAlumne1())) {
				aux1 = a;
			}
			if (a.getNif().trim().equalsIgnoreCase(dto.getAlumne2())) {
				aux2 = a;
			}
		}

		return new Grup(nom, pAux, aux1, aux2);
	}

	public Tutelat dtoAtutelat(TutelatDTO dto) {

		String nif = dto.getNif();
		String nom = dto.getNom();
		String cognoms = dto.getCognoms();
		String correu_upv = dto.getCorreu_upv();
		String correu_personal = dto.getCorreu_personal();
		Grup grup_patu = null;
		String grup_matricula = dto.getGrup_matricula();
		String mobil = dto.getMobil();

		for (Grup g : grups) {
			if (g.getNom().trim().equalsIgnoreCase(dto.getGrup_patu())) {
				grup_patu = g;
			}
		}

		return new Tutelat(nif, nom, cognoms, correu_upv, correu_personal, grup_patu, grup_matricula, mobil);
	}

	/********************
	 * CASOS D'US CONCRET
	 ********************/
	/*
	 * Retorna un arraylist amb les persones que pertanyen al grup indicat
	 */
	public ArrayList<Persona> obtindreMembresGrup(String nomGrup){
		//crea la llista de persones
		ArrayList<Persona> llista = new ArrayList<>();
		//busca el grup en questió
		Grup aux = buscarGrup(nomGrup);
		if(aux!=null){
			//obtenir el professor
			if(aux.getProfessor()!=null){
				llista.add(aux.getProfessor());
			}
			//obtenir l'alumne 1
			if(aux.getAlumne1()!=null){
				llista.add(aux.getAlumne1());
			}
			//obtenir l'alumne 2
			if(aux.getAlumne2()!=null){
				llista.add(aux.getAlumne2());
			}
			//buscar els alumnes d'este grup
			for(Tutelat tut : tutelats){
				if(tut.getGrup_patu().equals(aux)){
					llista.add(tut);
				}
			}
		}
		return llista;
	}
	
}
