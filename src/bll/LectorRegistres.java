package bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import dao.ManagerDAO;

public class LectorRegistres {
	
	private static LectorRegistres INSTANCIA = new LectorRegistres();
	private String fitxerRegistres = "registres.txt";
	private HashMap<String, String> registre;
	
	private LectorRegistres(){
		//inicialitza el mapa els valors dels registres
		registre = new HashMap<>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fitxerRegistres));
			//el format dels registres es clau : valor
			while(sc.hasNext()){
				String linea = sc.nextLine();
				//si es un comentari passe 
				if (linea.startsWith("#"))
					continue;
				String[] separador = linea.split(":");
				registre.put(separador[0].trim(), separador[1].trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
	}

	/*
	 * Funcio que obté el correu electronic que utilitzarà l'aplicació
	 */
	public String llegirMail() throws SQLException {
		Connection c = ManagerDAO.getInstancia().getCon();
		String sql = "select * from Metainfo";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		return rs.getString("mail");

	}

	/*
	 * Funcio que obte la contrasenya desencriptada de la bd
	 */
	public String llegirPass() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, SQLException {
		Connection c = ManagerDAO.getInstancia().getCon();
		String sql = "select * from Metainfo";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		// obtinc la contrasenya encriptada
		String contrasenya = rs.getString("pass");
		// la desencripte, generant la clau
		Encriptador enc = new Encriptador();
		// clau per defecte (que es el mail asociat)
		return enc.desencriptar(contrasenya, enc.generarClau());
	}

	/*
	 * Este metode retorna un true o false a la pregunta Han sigut volcades les
	 * dades a la BD?
	 */
	public boolean estaInicialitzat() throws SQLException {
		Connection c = ManagerDAO.getInstancia().getCon();
		String sql = "select * from Metainfo";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		return rs.getInt("inicialitzat") != 0;
	}
	
	/*Este metode tanca la BD per a que no es puga tornar a inicialitzar*/
	public void marcaInicialitzat() throws SQLException{
		Connection c = ManagerDAO.getInstancia().getCon();
		String sql = "UPDATE Metainfo SET inicialitzat = 1";
		Statement stmt = c.createStatement();
		stmt.executeQuery(sql);
	}
	

	public String getValorRegistre(String clauRegistre){
		return registre.get(clauRegistre);
	}
	
	public int getIntRegistre(String clauRegistre){
		return Integer.parseInt(registre.get(clauRegistre));
	}
	
	public static LectorRegistres getInstancia(){
		return INSTANCIA;
	}
	
}
