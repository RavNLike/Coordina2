package bll;

import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;
import pojo.AlumneTutor;
import pojo.MapaActes;
import pojo.Professor;
import pojo.Tutelat;

public class ComptadorActes {

	private MapaActes mapa;
	private Coordina2 bll;
	
	public ComptadorActes(){
		mapa = new MapaActes();
		bll = Coordina2.getInstancia();
	}
	
	/*
	Nom i valor dels registres pertinents
	col_prof : 9
	col_tutor1 : 7
	col_tutor2 : 8
	col_tutelats_ini : 11
	col_tutelats_fi : 26*/

	
	public void comptarActesMes(String path) throws BiffException, IOException{
		//obtinc el valor de les columnes
		LectorRegistres instancia = LectorRegistres.getInstancia();
		int col_tutor1 = Integer.valueOf(instancia.getValorRegistre("col_tutor1"));
		int col_tutor2 = Integer.valueOf(instancia.getValorRegistre("col_tutor2"));
		int col_prof = Integer.valueOf(instancia.getValorRegistre("col_prof"));
		int col_tutelats_ini = Integer.valueOf(instancia.getValorRegistre("col_tutelats_ini"));
		int col_tutelats_fi = Integer.valueOf(instancia.getValorRegistre("col_tutelats_fi"));
		
		//obrim el excell
		Workbook workbook = Workbook.getWorkbook(new File(path));
		//obtenim la primera pagina
		Sheet sheet = workbook.getSheet(0);
		//recorre files
		for(int i = 0; i<sheet.getRows();i++){
			//recorre columnes
			for(int j = col_tutor1;j<=col_tutelats_fi;j++){
				String nif = sheet.getCell(i, j).getContents();
				if(j<col_prof){
					AlumneTutor tut = bll.buscarAlumneTutor(nif);
					mapa.sumarAssistencia(tut);
				}else{
					if(j==col_prof){
						Professor prof = bll.buscarProfessor(nif);
						mapa.sumarAssistencia(prof);
					}else{
						//no ens interesa la columna 10, no hi ha professor 2 
						if(j>=col_tutelats_ini){
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
