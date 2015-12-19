package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import UTILS.LectorFitxers;

public class ManagerDAO {
	private static ManagerDAO INSTANCIA = new ManagerDAO(LectorFitxers.rutabd());
	private Connection c = null;

	private ManagerDAO(String path) {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + path);
			c.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public Connection getCon() {
		return this.c;
	}

	public static ManagerDAO getInstancia() {
		return INSTANCIA;
	}
}
