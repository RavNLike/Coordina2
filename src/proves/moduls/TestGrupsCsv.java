package proves.moduls;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import bll.Coordina2;
import pojo.Grup;
import pojo.Professor;
import pojo.Tutelat;

public class TestGrupsCsv {

	//TODO insercio en laplicacio i ampliacio
	public static void exportar() throws IOException {
		/*
		 * 1) recupere els grups
		 * 2) per cada grup monte un csv amb les dades
		 * del professor, els tutors i els alumnes
		 * */
		
		Coordina2 bll = Coordina2.getInstancia();
		ArrayList<Grup> grups = bll.llistarGrups();
		for (Grup g : grups) {
			String nom = g.getNom();
			BufferedWriter out = new BufferedWriter(new FileWriter(nom+".csv"));
			out.write("Professor\n");
			Professor prof = g.getProfessor1();
			out.write(prof.getNif()+";"+prof.toString()+';'+prof.getCorreu_upv()+"\n");
			out.write("Tutelats\n");
			ArrayList <Tutelat> tuts = bll.llistarTutelatsPerGrup(g);
			for (Tutelat tut : tuts) {
				out.write(tut.getNif()+";"+tut.toString()+';'+
							tut.getCorreu_upv()+";"+tut.getCorreu_personal()+"\n");
			}
			out.close();
		}	
	}
	
	
	/*main de la classe de proves*/
	public static void main (String args[]) throws IOException {
		exportar();
	}
	
}
