package BLL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import DAO.DTO.TutelatDTO;
import POJO.AlumneTutor;
import POJO.Professor;
import POJO.Tutelat;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/*
 * AQUESTA CLASSE S'USA EXCLUSIVAMENT
 * PER A FER LA CARREGA INCIAL DEL SISTEMA
 */
public class CarregaInicial {
	private static CarregaInicial INSTANCIA;

	private CarregaInicial() {

	}

	public CarregaInicial getInstancia() {
		return INSTANCIA;
	}

	/******************************************
	 * LLIG I FA LA TRANSFORMACIO DELS EXCELS
	 * 
	 * @throws IOException
	 * @throws BiffException
	 *****************************************/
	public ArrayList<Professor> carregaInicialProfessors(String path) throws BiffException, IOException {
		ArrayList<Professor> llista = new ArrayList<>();
		Workbook workbook = Workbook.getWorkbook(new File(path));
		Sheet sheet = workbook.getSheet(0);
		//
		for (int i = 0; i < sheet.getRows(); i++) {
			// segons el format del excel dels alumnes nou ingres
			String nif = sheet.getCell(i, 0).getContents();
			String cognoms = sheet.getCell(i, 1).getContents();
			String nom = sheet.getCell(i, 2).getContents();
			String correu = sheet.getCell(i, 3).getContents();

			llista.add(new Professor(nif, nom, cognoms, correu));
		}
		return llista;
	}
	
	public ArrayList<AlumneTutor> carregaInicialAlumnesTutors(String path) throws BiffException, IOException {
		ArrayList<AlumneTutor> llista = new ArrayList<>();
		Workbook workbook = Workbook.getWorkbook(new File(path));
		Sheet sheet = workbook.getSheet(0);
		//
		for (int i = 0; i < sheet.getRows(); i++) {
			// segons el format del excel dels alumnes nou ingres
			String nif = sheet.getCell(i, 0).getContents();
			String cognoms = sheet.getCell(i, 1).getContents();
			String nom = sheet.getCell(i, 2).getContents();
			String correu = sheet.getCell(i, 3).getContents();

			llista.add(new AlumneTutor(nif, nom, cognoms, correu));
		}
		return llista;
	}

	public ArrayList<TutelatDTO> carrgeaInicialTutelats(String path) throws BiffException, IOException {
		ArrayList<TutelatDTO> llista = new ArrayList<>();
		// obrim el excell
		Workbook workbook = Workbook.getWorkbook(new File(path));
		// obtenim la primera pagina
		Sheet sheet = workbook.getSheet(0);
		// per files
		for (int i = 0; i < sheet.getRows(); i++) {
			// segons el format del excel dels alumnes nou ingres
			String nif = sheet.getCell(i, 0).getContents();
			// cognoms [0], nom [1]
			String nomCognoms[] = sheet.getCell(i, 1).getContents().split(",");
			String mobil = sheet.getCell(i, 2).getContents();
			String correu_upv = sheet.getCell(i, 3).getContents();
			String correu_personal = sheet.getCell(i, 4).getContents();
			String grupo = sheet.getCell(i, 5).getContents();
			// crea l'objecte
			TutelatDTO tutelat = new TutelatDTO(nif, nomCognoms[1].trim(), nomCognoms[0].trim(), correu_upv,
					correu_personal, TutelatDTO.noAsignat, grupo, mobil);
			llista.add(tutelat);
		}
		return llista;
	}
	/*
	 * ELS GRUPS NO ES LLIGEN, SOLS ELS CREEN
	 */
}
