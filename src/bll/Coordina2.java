package bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.itextpdf.text.DocumentException;

import dao.GrupDAO;
import dao.TutelatDAO;
import dao.TutorDAO;
import dao.dto.GrupDTO;
import dao.dto.TutelatDTO;
import jxl.read.biff.BiffException;
import pojo.AlumneTutor;
import pojo.Grup;
import pojo.MapaActes;
import pojo.Persona;
import pojo.Professor;
import pojo.Tutelat;
import pojo.exceptions.ArgumentErroniException;
import pojo.exceptions.InicialitzatException;
import pojo.exceptions.SeguretatException;

public class Coordina2 {

	private static Coordina2 INSTANCIA = new Coordina2();

	/*******************************************
	 * Arrays en memoria sobre els que treballar
	 *******************************************/

	private ArrayList<pojo.Professor> professors;
	private ArrayList<pojo.AlumneTutor> alumnesTutors;
	private ArrayList<pojo.Tutelat> tutelats;
	private ArrayList<pojo.Grup> grups;

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
		// afig primer a la bd, si entra afig tambe a l'array
		boolean bandera = daotutor.afegir(p);
		if (bandera)
			professors.add(p);
	}

	public ArrayList<Professor> llistarProfessors() {
		/*Ordena els professors primer*/
		Collections.sort(professors, new Comparator<Professor>() {
	        
			@Override
			public int compare(Professor arg0, Professor arg1) {
				return arg0.getCognoms().compareTo(arg1.getCognoms());
			}
	    });
		
		
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
		for (int i = 0; i < professors.size(); i++) {
			if (professors.get(i).getNif().equalsIgnoreCase(p.getNif())) {
				boolean bandera = daotutor.esborrar(p);
				if (bandera)
					professors.remove(i);
			}
		}
	}

	public void editarProfessor(Professor p) throws ArgumentErroniException {
		// busca el objecte antic (aux)
		for (int i = 0; i < professors.size(); i++) {
			// if (aux.getNif().equalsIgnoreCase(p.getNif())) {
			if (professors.get(i).getNif().equalsIgnoreCase(p.getNif())) {
				// si conseguisc editarlo en la bd, edite els valors
				boolean bandera = daotutor.editar(p);
				if (bandera) {
					professors.get(i).setNif(p.getNif());
					professors.get(i).setNom(p.getNom());
					professors.get(i).setCognoms(p.getCognoms());
					professors.get(i).setCorreu_upv(p.getCorreu_upv());
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
		
		/*Ordena els alumnes tutors primer*/
		Collections.sort(alumnesTutors, new Comparator<AlumneTutor>() {
	        
			@Override
			public int compare(AlumneTutor arg0, AlumneTutor arg1) {
				return arg0.getCognoms().compareTo(arg1.getCognoms());
			}
	    });
		
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
		for (int i = 0; i < alumnesTutors.size(); i++) {
			if (alumnesTutors.get(i).getNif().equalsIgnoreCase(a.getNif())) {
				boolean bandera = daotutor.esborrar(a);
				if (bandera)
					alumnesTutors.remove(i);
			}
		}
	}

	public void editarAlumneTutor(AlumneTutor a) throws ArgumentErroniException {
		for (int i = 0; i < alumnesTutors.size(); i++) {
			if (alumnesTutors.get(i).equals(a)) {
				boolean bandera = daotutor.editar(a);
				if (bandera) {
					alumnesTutors.get(i).setNif(a.getNif());
					alumnesTutors.get(i).setNom(a.getNom());
					alumnesTutors.get(i).setCognoms(a.getCognoms());
					alumnesTutors.get(i).setCorreu_upv(a.getCorreu_upv());
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
		
		/*Ordena els grups primer*/
		Collections.sort(grups, new Comparator<Grup>() {
	        
			@Override
			public int compare(Grup arg0, Grup arg1) {
				return arg0.getNom().compareTo(arg1.getNom());
			}
	    });
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
		for (int i = 0; i < grups.size(); i++) {
			if (g.getNom().equalsIgnoreCase(grups.get(i).getNom())) {
				boolean bandera = daogrup.esborrar(g);
				if (bandera)
					grups.remove(i);
			}
		}
	}

	public void editarGrup(Grup g) {
		for (int i = 0; i < grups.size(); i++) {
			if (g.getNom().equalsIgnoreCase(grups.get(i).getNom())) {
				boolean bandera = daogrup.editar(g);
				if (bandera) {
					grups.get(i).setAlumne1(g.getAlumne1());
					grups.get(i).setAlumne2(g.getAlumne2());
					grups.get(i).setProfessor1(g.getProfessor1());
					//nou
					grups.get(i).setProfessor2(g.getProfessor2());
					grups.get(i).setAlumne3(g.getAlumne3());
					grups.get(i).setAlumne4(g.getAlumne4());
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
		/*Ordena els tutelats primer*/
		Collections.sort(tutelats, new Comparator<Tutelat>() {
	        
			/*Ordena primer per grup de matricula i despres per nom*/
			@Override
			public int compare(Tutelat arg0, Tutelat arg1) {
				return arg0.getGrup_patu().getNom().compareTo(arg1.getGrup_patu().getNom())*1000
						+arg0.getCognoms().compareTo(arg1.getCognoms());
			}
	    });
		
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
		for (int i = 0; i < tutelats.size(); i++) {
			if (tutelats.get(i).getNif().equalsIgnoreCase(t.getNif())) {
				boolean bandera = daotutelat.esborrar(t);
				if (bandera)
					tutelats.remove(i);
			}
		}
	}

	public void editarTutelat(Tutelat t) {
		for (int i = 0; i < tutelats.size(); i++) {
			if (tutelats.get(i).getNif().equalsIgnoreCase(t.getNif())) {
				boolean bandera = daotutelat.editar(t);
				if (bandera) {
					tutelats.get(i).setNif(t.getNif());
					tutelats.get(i).setNom(t.getNom());
					tutelats.get(i).setCognoms(t.getCognoms());
					tutelats.get(i).setCorreu_upv(t.getCorreu_upv());
					tutelats.get(i).setGrup_matricula(t.getGrup_matricula());
					tutelats.get(i).setCorreu_personal(t.getCorreu_personal());
					tutelats.get(i).setGrup_patu(t.getGrup_patu());
					tutelats.get(i).setMobil(t.getMobil());
				}
			}
		}
	}

	public ArrayList<Tutelat> llistarTutelatsPerGrup(Grup g) {
		ArrayList<Tutelat> llista = new ArrayList<>();
		for (Tutelat aux : tutelats) {
			if (aux.getGrup_patu().equals(g)) {
				llista.add(aux);
			}
		}
		return llista;
	}

	/************************
	 * TRADUCCIONS DTO-NORMAL
	 ************************/

	public Grup dtoAgrup(GrupDTO dto) {
		String nom = dto.getNom();
		Professor p1 = null;
		Professor p2 = null;
		AlumneTutor aux1 = null;
		AlumneTutor aux2 = null;
		AlumneTutor aux3 = null;
		AlumneTutor aux4 = null;
		for (Professor p : professors) {
			if (p.getNif().trim().equalsIgnoreCase(dto.getProfessor1())) {
				p1 = p;
			}
			if (p.getNif().trim().equalsIgnoreCase(dto.getProfessor2())) {
				p2 = p;
			}
		}
		for (AlumneTutor a : alumnesTutors) {
			if (a.getNif().trim().equalsIgnoreCase(dto.getAlumne1())) {
				aux1 = a;
			}
			if (a.getNif().trim().equalsIgnoreCase(dto.getAlumne2())) {
				aux2 = a;
			}
			if (a.getNif().trim().equalsIgnoreCase(dto.getAlumne3())) {
				aux3 = a;
			}
			if (a.getNif().trim().equalsIgnoreCase(dto.getAlumne4())) {
				aux4 = a;
			}
		}

		return new Grup(nom, p1, p2, aux1, aux2, aux3, aux4);
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
	public ArrayList<Persona> obtindreMembresGrup(String nomGrup) {
		// crea la llista de persones
		ArrayList<Persona> llista = new ArrayList<>();
		// busca el grup en questió
		Grup aux = buscarGrup(nomGrup);
		if (aux != null) {
			// obtenir el professor
			if (aux.getProfessor1() != null) {
				llista.add(aux.getProfessor1());
			}
			if (aux.getProfessor2() != null) {
				llista.add(aux.getProfessor2());
			}
			// obtenir l'alumne 1
			if (aux.getAlumne1() != null) {
				llista.add(aux.getAlumne1());
			}
			// obtenir l'alumne 2
			if (aux.getAlumne2() != null) {
				llista.add(aux.getAlumne2());
			}
			// obtenir l'alumne 3
			if (aux.getAlumne3() != null) {
				llista.add(aux.getAlumne3());
			}
			// obtenir l'alumne 4
			if (aux.getAlumne4() != null) {
				llista.add(aux.getAlumne4());
			}
			// buscar els alumnes d'este grup
			for (Tutelat tut : tutelats) {
				if (tut.getGrup_patu().equals(aux)) {
					llista.add(tut);
				}
			}
		}
		return llista;
	}

	public ArrayList<Tutelat> obtindreTutelatsPerGrup(String nomGrup) {
		ArrayList<Tutelat> res = new ArrayList<>();
		for (Tutelat t : tutelats) {
			if (t != null && t.getGrup_patu().getNom().equals(nomGrup)) {
				res.add(t);
			}
		}
		return res;
	}

	public ArrayList<Grup> grupsPerAlumneTutor(AlumneTutor al) {
		ArrayList<Grup> llista = new ArrayList<>();
		for (Grup aux : grups) {
			if (aux.getAlumne1().equals(al) || 
					(aux.getAlumne2() != null && aux.getAlumne2().equals(al)) ||
					(aux.getAlumne3() != null && aux.getAlumne3().equals(al)) ||
					(aux.getAlumne4() != null && aux.getAlumne4().equals(al))) {
				llista.add(aux);
			}
		}
		return llista;
	}

	public ArrayList<Grup> grupsPerProfessor(Professor prof) {
		ArrayList<Grup> llista = new ArrayList<>();
		String dni = prof.getNif();
		for (Grup g : grups) {
			if (g.getProfessor1().getNif().equalsIgnoreCase(dni)) {
				llista.add(g);
			}
			if (g.getProfessor2() != null && g.getProfessor2().getNif().equalsIgnoreCase(dni)) {
				llista.add(g);
			}
		}

		return llista;
	}

	/*****************
	 * CARDINALITATS
	 *****************/
	public int nProfessors() {
		return professors.size();
	}

	public int nAlumnesTutors() {
		return alumnesTutors.size();
	}

	public int nGrups() {
		return grups.size();
	}

	public int nTutelats() {
		return tutelats.size();
	}

	/*********************
	 * MAILING
	 * 
	 * @throws SQLException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws MessagingException 
	 * @throws AddressException 
	 *******************/
	public void enviarCorreu(Persona desti, String tema, String cosMissatge)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, SQLException, AddressException, MessagingException {
		EnviarCorreu env = new EnviarCorreu();
		env.enviar(desti, tema, cosMissatge);
	}

	/************************
	 * LLISTATS EN STRING
	 ***********************/

	/*
	 * Donat un professor construeix un missatge amb els seus tutors i els seus
	 * tutelats es importan el format: cognoms, nom -- correu_upv
	 */
	public String obtindreLlistaPerProfessor(Professor profe) {
		String res = "";
		for (Grup aux : grups) {
			// busca por profesor uno o dos si existe
			if (aux.getProfessor1().getNif().equalsIgnoreCase(profe.getNif()) ||
				(aux.getProfessor2() != null && aux.getProfessor2().getNif().equalsIgnoreCase(profe.getNif()))) {
				// trobe un grup d'eixe profe
				Grup grup = aux;
				res += "GRUP - " + grup.getNom() + "\n";
				res += "TUTOR(S)\n-------\n";
				if (grup.getAlumne1() != null) {
					AlumneTutor a1 = grup.getAlumne1();
					res += a1.getCognoms()+", "+a1.getNom()+" -- "+a1.getCorreu_upv()+ "\n";
				}
				if (grup.getAlumne2() != null) {
					AlumneTutor a2 = grup.getAlumne2();
					res += a2.getCognoms()+", "+a2.getNom()+" -- "+a2.getCorreu_upv()+ "\n";
				}
				if (grup.getAlumne3() != null) {
					AlumneTutor a3 = grup.getAlumne3();
					res += a3.getCognoms()+", "+a3.getNom()+" -- "+a3.getCorreu_upv()+ "\n";
				}
				if (grup.getAlumne4() != null) {
					AlumneTutor a4 = grup.getAlumne4();
					res += a4.getCognoms()+", "+a4.getNom()+" -- "+a4.getCorreu_upv()+ "\n";
				}
				res += "\nTUTELATS\n-------\n";
				for (Tutelat t : llistarTutelatsPerGrup(grup)) {
					res += t.getCognoms()+", "+t.getNom()+" -- "+t.getCorreu_upv()+ "\n";
				}
				res += "-------\n";

			}
		}
		return res;
	}


	public String obtindreMembresPerAlumneTutor(AlumneTutor tut) {
		ArrayList<Grup> llistat = grupsPerAlumneTutor(tut);
		String res = "";
		for (Grup aux : llistat) {
			res += "GRUP - " + aux.getNom() + "\n------\n";
			res += "PROFESSOR\n------\n";
			res += aux.getProfessor1().infoCompleta()+ "\n";
			if (aux.getProfessor2()!=null) {
				res+= aux.getProfessor2().infoCompleta() + "\n";
			}
			res += "\nTUTOR(S)\n-------\n";
			if (aux.getAlumne1() != null) {
				AlumneTutor a1 = aux.getAlumne1();
				res += a1.getCognoms()+", "+a1.getNom()+" -- "+a1.getCorreu_upv()+ "\n";
			}
			if (aux.getAlumne2() != null) {
				AlumneTutor a2 = aux.getAlumne2();
				res += a2.getCognoms()+", "+a2.getNom()+" -- "+a2.getCorreu_upv()+ "\n";
			}
			if (aux.getAlumne3() != null) {
				AlumneTutor a3 = aux.getAlumne3();
				res += a3.getCognoms()+", "+a3.getNom()+" -- "+a3.getCorreu_upv()+ "\n";
			}
			if (aux.getAlumne4() != null) {
				AlumneTutor a4 = aux.getAlumne4();
				res += a4.getCognoms()+", "+a4.getNom()+" -- "+a4.getCorreu_upv()+ "\n";
			}
			res += "\nTUTELATS\n-------\n";
			for (Tutelat t : llistarTutelatsPerGrup(aux)) {
				res += t.getCognoms()+", "+t.getNom()+" -- "+t.getCorreu_upv()+ "\n";
			}
			res += "-------\n";

		}
		return res;

	}

	/*******************
	 * TODO COMPTADOR DE DNI: PENDENT DE SER REFORMAT
	 * 
	 * @throws IOException
	 * @throws BiffException
	 ******************/
	// donats un fitxares d'entrada, ompli lo mapa
	private MapaActes obtindreMapaAssistencies(String[] entrades) throws BiffException, IOException {
		ComptadorActes comptador = new ComptadorActes();
		// per cada entrada aplica l'agoritme comptador
		for (int i = 0; i < entrades.length; i++) {
			comptador.comptarActesMes(entrades[i]);
		}
		return comptador.obtindreMapa();
	}

	private void escriureMapaActes(String eixida, MapaActes mapa) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(eixida));
		// separem per a ordenar despres en l'arxiu
		ArrayList<Persona> profes = new ArrayList<>();
		ArrayList<Persona> tutelats = new ArrayList<>();
		ArrayList<Persona> alumnes = new ArrayList<>();
		// recorrem el mapa per classificar
		for (Persona p : mapa.claus()) {
			if (p instanceof Professor) {
				profes.add(p);
			} else {
				if (p instanceof Tutelat) {
					tutelats.add(p);
				} else {
					alumnes.add(p);
				}
			}
		}
		// ara escribim les dades en el fitxer de forma ordenada
		pw.write("PROFESSORS \n-----------\n\n");
		for (Persona p : profes) {
			pw.write(p.getCognoms() + ", " + p.getNom() + " - " + p.getNif() + " - " + mapa.valorDe(p) + "\n");
		}
		pw.write("\nALUMNES TUTORS \n-----------\n\n");
		for (Persona p : alumnes) {
			pw.write(p.getCognoms() + ", " + p.getNom() + " - " + p.getNif() + " - " + mapa.valorDe(p) + "\n");
		}
		pw.write("\nTUTELATS \n-----------\n\n");
		for (Persona p : tutelats) {
			pw.write(p.getCognoms() + ", " + p.getNom() + " - " + p.getNif() + " - " + mapa.valorDe(p) + "\n");
		}

	}

	/*
	 * Métode public que compta i escriu en el fitxer
	 */
	public void comptaDNIs(String[] entrades, String eixida) throws FileNotFoundException, BiffException, IOException {
		escriureMapaActes(eixida, obtindreMapaAssistencies(entrades));
	}

	public void crearActes(String absolutePath) {

	}

	/****************************************
	 * METODE PER A FER LA CARREGA INICIAL
	 * 
	 * @throws ArgumentErroniException
	 * @throws InicialitzatException
	 * @throws SQLException
	 * @throws IOException
	 * @throws BiffException
	 ****************************************/

	private void guardaEstatInicial() throws ArgumentErroniException {
		// 1) professors i tutors 2) grups 3) tutelats
		for (Professor p : professors) {
			daotutor.afegir(p);
		}
		for (AlumneTutor t : alumnesTutors) {
			daotutor.afegir(t);
		}
		for (Grup g : grups) {
			daogrup.afegir(g);
		}

		for (Tutelat t : tutelats) {
			daotutelat.afegir(t);
		}
	}

	public void inicialitzarSistema(String profs, String tutors, String alumnes)
			throws InicialitzatException, SQLException, BiffException, IOException, ArgumentErroniException {
		CarregaInicial carrega = CarregaInicial.getInstancia();
		carrega.inicialitzaTOT(profs, tutors, alumnes);
		guardaEstatInicial();
		// tanca per a que no puga tornar a carregar-se
		LectorRegistres.getInstancia().marcaInicialitzat();
		// com poden haver linies en blanc, tornem a carregar
		carregarSistema();
	}

	/*************************************
	 * METODE PER A CREAR LES ACREDITACIONS
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 **************************************/

	public void crearAcreditacions(String pathdesti) throws IOException, DocumentException {
		Acreditador acre = Acreditador.getInstancia();
		acre.acreditarProfessors(pathdesti);
		acre.acreditarAlumnesTutors(pathdesti);
		acre.acreditarTutelats(pathdesti);
	}
	
	/**********************
	 * LA SEGUENT FUNCIO CREA UNA PAGINA D'ACREDITACIONS (6) EN BLANC
	 * TODO IMPLEMENTAR A LA VISTA
	 * @throws IOException 
	 * @throws DocumentException 
	 **********************/
	
	public void crearAcreditacionsBlanc(String desti) throws DocumentException, IOException{
		Acreditador acre = Acreditador.getInstancia();
		acre.acreditarBlanc(desti);
	}

	/************************
	 * METODE QUE BORRA TOTA LA BASE DE DADES NECESSITE QUE EL EL REGISTRE
	 * "FORELLAT" ES TROBE A 0 ES UN METODE NECESSARI PER A LES PROVES
	 * 
	 * @throws SQLException
	 * @throws SeguretatException 
	 ************************/
	public void borraBD() throws SQLException, SeguretatException {
		LectorRegistres.getInstancia().deixaEnBlancBD();
		carregarSistema();
	}

}
