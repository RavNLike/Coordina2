package bll;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pojo.AlumneTutor;
import pojo.Grup;
import pojo.Professor;
import pojo.Tutelat;

public class ExportarCsv {

	
	public static void exportarGrupsCSV(String folder) throws IOException {
		/*
		 * 1) recupere els grups
		 * 2) per cada grup monte un csv amb les dades
		 * del professor, els tutors i els alumnes
		 * */
		
		Coordina2 bll = Coordina2.getInstancia();
		ArrayList<Grup> grups = bll.llistarGrups();
		for (Grup g : grups) {
			// nom del grup
			String nom = g.getNom();
			
			//escriure l'informacio en txt/csv
			BufferedWriter out = new BufferedWriter(new FileWriter(folder+"/"+nom+".csv"));
			
			//header de professor
			out.write("Professor\n");
			Professor prof1 = g.getProfessor1();
			Professor prof2 = g.getProfessor2();
			out.write(prof1.getNif()+";"+prof1.toString()+';'+prof1.getCorreu_upv()+"\n");
			if (prof2 != null)
				out.write(prof2.getNif()+";"+prof2.toString()+';'+prof2.getCorreu_upv()+"\n");
			
			//header de alumne tutor
			AlumneTutor [] tutors = {g.getAlumne1(), g.getAlumne2(), g.getAlumne3(), g.getAlumne4()};
			for (AlumneTutor t : tutors) {
				if (t != null)
					out.write(t.getNif()+";"+t.toString()+';'+t.getCorreu_upv()+"\n");
			}
			
			
			//header de tutelats
			out.write("Tutelats\n");
			ArrayList <Tutelat> tuts = bll.llistarTutelatsPerGrup(g);
			for (Tutelat tut : tuts) {
				out.write(tut.getNif()+";"+tut.toString()+';'+
							tut.getCorreu_upv()+";"+tut.getCorreu_personal()+"\n");
			}
			out.close();
		}	
	}
	
	
}
