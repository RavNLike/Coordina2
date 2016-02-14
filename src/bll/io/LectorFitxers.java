package bll.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import dao.ManagerDAO;

public class LectorFitxers {
	private static String bdlocation = "rutabd.txt";

	public static String rutabd() {
		Scanner sc;
		String res = "";
		try {
			sc = new Scanner(new File(bdlocation));
			// en la primera linea es troba la localitzacio de la bd
			res = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return res;
	}

	/*
	 * Funcio que obté el correu electronic que utilitzarà l'aplicació
	 */
	public static String llegirMail() throws SQLException {
		Connection c = ManagerDAO.getInstancia().getCon();
		String sql = "select * from Metainfo";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		return rs.getString("mail");

	}

	/*
	 * Funcio que obte la contrasenya desencriptada de la bd
	 */
	public static String llegirPass() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
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
	public static boolean estaInicialitzat() throws SQLException {
		Connection c = ManagerDAO.getInstancia().getCon();
		String sql = "select * from Metainfo";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		return rs.getInt("inicialitzat") != 0;
	}
}
