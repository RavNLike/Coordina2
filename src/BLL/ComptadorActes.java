package BLL;

import java.io.File;
import java.io.IOException;

import POJO.AlumneTutor;
import POJO.MapaActes;
import POJO.Professor;
import POJO.Tutelat;
import jxl.*;
import jxl.read.biff.BiffException;

public class ComptadorActes {

	private MapaActes mapa;
	private Coordina2 bll;
	
	public ComptadorActes(){
		mapa = new MapaActes();
		bll = Coordina2.getInstancia();
	}
	
	/*
	 * NECESSITEM DE LA LLIBRERIA JXL
	 */
	public void comptarActesMes(String path) throws BiffException, IOException{
		//obrim el excell
		Workbook workbook = Workbook.getWorkbook(new File(path));
		//obtenim la primera pagina
		Sheet sheet = workbook.getSheet(0);
		//AlumnesTutors 7-8
		//Professor 9
		//Tutelats 11-26
		//recorre files
		for(int i = 0; i<sheet.getRows();i++){
			//recorre columnes
			for(int j = 7;j<=26;j++){
				String nif = sheet.getCell(i, j).getContents();
				if(j<9){
					AlumneTutor tut = bll.buscarAlumneTutor(nif);
					mapa.sumarAssistencia(tut);
				}else{
					if(j==9){
						Professor prof = bll.buscarProfessor(nif);
						mapa.sumarAssistencia(prof);
					}else{
						//no ens interesa la columna, no hi ha professor 2 
						if(j>=11){
							Tutelat tute = bll.buscarTutelat(nif);
							mapa.sumarAssistencia(tute);
						}
					}
				}
			} // for columnes
		}//for files
	}//fi metode
	
	public MapaActes obtindreMapa(){
		return mapa;
	}
}
