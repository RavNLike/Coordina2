package PRESENTACIO.CONS;

import java.util.Scanner;
import BLL.Coordina2;
import POJO.AlumneTutor;
import POJO.Grup;
import POJO.Professor;
import POJO.Tutelat;
import POJO.EXCEPTIONS.ArgumentErroniException;

/*
 * Interficie temporal sobre consola,
 * l'objectiu es provar la funcionalitat i anar evolucionant
 * fins que l'interficie grafica estiga preparada
 */
public class InterficieConsola {

	// conexio amb la capa logica
	private static Coordina2 bll;
	static Scanner tec;

	public static void main(String args[]) throws ArgumentErroniException {
		// el primer que fas es inicialitzar la instancia
		bll = Coordina2.getInstancia();
		// crea scanner
		tec = new Scanner(System.in);
		// variable per a les opcions
		int opcio = -1;
		while (opcio != 0) {
			pintaMenuPrincipal();
			opcio = Integer.parseInt(tec.nextLine());
			switch (opcio) {
			case 0:
				System.out.println("Adeu!");
				break;
			case 1:
				crudTutelats();
				break;
			case 2:
				crudProfessors();
				break;
			case 3:	
				crudAlumnesTutors();
				break;
			case 4: 
				crudGrups();
				break;
			case 5:
				//mailing TODO
				System.err.println("No implementat encara");
				break;
			case 6: 
				//llistats
				operacionsLlistar();
				break;
			case 7: 
				//carrega inicial TODO
				System.err.println("No implementat encara");
				break;
			case 8:
				//contar actes TODO
				System.err.println("No implementat encara");
				break;
			}
		}

	}

	/*****************
	 * OPERACIONS CRUD
	 ****************/
	private static void crudTutelats() {
		pintaSubMenus("tutelat");
		int opcio = Integer.parseInt(tec.nextLine());
		switch (opcio) {
		case 1:
			bll.afegirTutelat(obDadesTutelat());
			break;

		case 2:
			for (Tutelat aux : bll.llistarTutelats()) {
				System.out.println(aux);
			}
			break;
		case 3:
			bll.editarTutelat(obDadesTutelat());
			break;

		case 4:
			System.out.println("Nif del tutelat a esborrar");
			String nif = tec.nextLine();
			bll.borrarTutelat(bll.buscarTutelat(nif));
			break;
		}

	}

	private static void crudProfessors() throws ArgumentErroniException {
		pintaSubMenus("professor");
		int opcio = Integer.parseInt(tec.nextLine());
		switch (opcio) {
		case 1:
			bll.afegirProfessor(obDadesProfessor());
			break;
		case 2:
			for (Professor aux : bll.llistarProfessors()) {
				System.out.println(aux);
			}
			break;
		case 3:
			bll.editarProfessor(obDadesProfessor());
			break;
		case 4:
			System.out.println("Nif del professor a esborrar");
			String nif = tec.nextLine();
			bll.borrarProfessor(bll.buscarProfessor(nif));
		}
	}
	
	private static void crudAlumnesTutors() throws ArgumentErroniException{
		pintaSubMenus("alumne tutor");
		int opcio = Integer.parseInt(tec.nextLine());
		switch (opcio) {
		case 1:
			bll.afegirAlumneTutor(obDadesAlumneTutor());
			break;
		case 2:
			for (AlumneTutor aux : bll.llistarAlumnesTutors()) {
				System.out.println(aux);
			}
			break;
		case 3:
			bll.editarAlumneTutor(obDadesAlumneTutor());
			break;
		case 4:
			System.out.println("Nif del alumne tutor a esborrar");
			String nif = tec.nextLine();
			bll.borrarAlumneTutor(bll.buscarAlumneTutor(nif));
			break;
		}
	}
	
	private static void crudGrups(){
		pintaSubMenus("grup");
		int opcio = Integer.parseInt(tec.nextLine());
		switch (opcio) {
		case 1:
			bll.afegirGrup(obDadesGrup());
			break;
		case 2:
			for (Grup aux : bll.llistarGrups()) {
				System.out.println(aux);
			}
			break;
		case 3:
			bll.editarGrup(obDadesGrup());
			break;
		case 4:
			System.out.println("Nom del grup a esborrar");
			String nom = tec.nextLine();
			bll.borrarGrup(bll.buscarGrup(nom));
			break;
		}
	}

	/**************************
	 * PINTA COSES PER PANTALLA
	 ***************************/
	private static void pintaSubMenus(String objecte) {
		System.out.println("1. Afegir " + objecte);
		System.out.println("2. Llistar " + objecte + "(s)");
		System.out.println("3. Editar " + objecte);
		System.out.println("4. Esborrar " + objecte);
	}

	private static void pintaMenuPrincipal() {
		// mostra totes les posibilitats
		System.out.println("1. Operacions sobre Tutelats");
		System.out.println("2. Operacions sobre Professors");
		System.out.println("3. Operacions sobre Tutor");
		System.out.println("4. Operacions sobre Grups");
		System.out.println("5. Mailing");
		System.out.println("6. Llistats");
		System.out.println("7. Carrega inicial");
		System.out.println("8. Contar actes");
		System.out.println("0. Eixir");
	}

	/*******************
	 * Pilla dades de teclat
	 ******************/
	private static Tutelat obDadesTutelat() {

		System.out.println("Introdueix el nif");
		String nif = tec.nextLine();

		System.out.println("Introdueix el nom");
		String nom = tec.nextLine();

		System.out.println("Introdueix els cognoms");
		String cognoms = tec.nextLine();

		System.out.println("Introdueix el correu upv");
		String correu_upv = tec.nextLine();
		// coses exclusives dels tutelats

		System.out.println("Introdueix el correu personal");
		String correu_pers = tec.nextLine();

		System.out.println("Introdueix el mobil");
		String mobil = tec.nextLine();

		System.out.println("Introdueix el grup matricula");
		String grup_mat = tec.nextLine();

		System.out.println("Introdueix el grup patu");
		String grup_patu = tec.nextLine();

		// busca la referencia al grup patu
		Grup g = bll.buscarGrup(grup_patu);

		return new Tutelat(nif, nom, cognoms, correu_upv, correu_pers, g, grup_mat, mobil);

	}

	private static AlumneTutor obDadesAlumneTutor() {
		System.out.println("Introdueix el nif");
		String nif = tec.nextLine();

		System.out.println("Introdueix el nom");
		String nom = tec.nextLine();

		System.out.println("Introdueix els cognoms");
		String cognoms = tec.nextLine();

		System.out.println("Introdueix el correu upv");
		String correu_upv = tec.nextLine();

		return new AlumneTutor(nif, nom, cognoms, correu_upv);
	}

	private static Professor obDadesProfessor() {
		System.out.println("Introdueix el nif");
		String nif = tec.nextLine();

		System.out.println("Introdueix el nom");
		String nom = tec.nextLine();

		System.out.println("Introdueix els cognoms");
		String cognoms = tec.nextLine();

		System.out.println("Introdueix el correu upv");
		String correu_upv = tec.nextLine();

		return new Professor(nif, nom, cognoms, correu_upv);
	}

	private static Grup obDadesGrup(){
		System.out.println("Introdueix el nom del grup");
		String nom = tec.nextLine();
		
		System.out.println("Introdueix el dni del professor");
		Professor prof = bll.buscarProfessor(tec.nextLine());
		
		System.out.println("Introdueix el dni del alumne tutor 1");
		AlumneTutor al1 = bll.buscarAlumneTutor(tec.nextLine());
		
		System.out.println("Introdueix el dni del alumne tutor 2");
		AlumneTutor al2 = bll.buscarAlumneTutor(tec.nextLine());
		
		return new Grup(nom, prof, al1, al2);
	}
	
	

	public static void operacionsLlistar(){
		System.out.println("1. llistar tutelats per grup");
		System.out.println("2. llistar per professor");
		
		int opcio = Integer.parseInt(tec.nextLine());
		switch(opcio){
		case 1: 
			System.out.println("Introdueix el nom del grup patu");
			String nom = tec.nextLine();
			for(Tutelat aux : bll.llistarTutelatsPerGrup(bll.buscarGrup(nom))){
				System.out.println(aux);
			}
			break;
		case 2:
			System.out.println("Introdueix el nif del professor");
			String nif = tec.nextLine();
			System.out.println(bll.obtindreLlistaPerProfessor(bll.buscarProfessor(nif)));
			break;
		}
		
	}
	
}
