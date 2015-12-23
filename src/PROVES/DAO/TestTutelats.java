package PROVES.DAO;

import BLL.Coordina2;

public class TestTutelats {

	public static void main(String args[]) {
		Coordina2 test = Coordina2.getInstancia();
		/*
		 * Grup g = test.buscarGrup("g01"); System.out.println(g); Tutelat t =
		 * new Tutelat("89562314J", "Pepot", "Blasco", "peblas@inf.upv.es",
		 * "peposex@hotmail.es", g, "1A2", "697852322"); test.afegirTutelat(t);
		 */
		System.out.println(test.llistarProfessors());
		System.out.println(test.llistarAlumnesTutors());
		System.out.println(test.llistarGrups());
		System.out.println(test.llistarTutelats());
	}

}
