package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import POJO.Persona;
/*
 * Com les taules TUTOR i PROFESSOR tenen la mateix info
 * creem una sola classe DAO, que usara una taula o altra
 * en cada metode hi ha una ternaria que triará la taula adecuada
 */
import POJO.EXCEPTIONS.ArgumentErroniException;

public class TutorDAO {
	private Connection c = null;
	private String path;

	public TutorDAO(String p) {
		this.path = p;
	}

	public boolean add(Persona p) throws ArgumentErroniException {
		if (!(p instanceof POJO.Tutor || p instanceof POJO.Tutelat))
			throw new ArgumentErroniException();

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + path);
			c.setAutoCommit(false);

			// aci trie la taula que toca
			String sql = "INSERT INTO " + (p instanceof POJO.Tutor ? "Tutor" : "Tutelat")
					+ "(nif, nom, cognoms, correu_upv)" + "VALUES(?,?,?,?)";

			// prepara els tokens
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, p.getNif());
			stmt.setString(2, p.getNom());
			stmt.setString(3, p.getCognoms());
			stmt.setString(3, p.getCorreu_upv());

			stmt.execute();
			stmt.close();
			c.commit();
			c.close();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (ClassNotFoundException ex) {
			return false;
		} finally {
			tancaConexio();
		}
	}

	private void tancaConexio() {
		try {
			c.close();
		} catch (Exception e) {

		}
	}

}
