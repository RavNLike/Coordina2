package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pojo.AlumneTutor;
import pojo.Persona;
import pojo.Professor;
import pojo.exceptions.ArgumentErroniException;

public class TutorDAO {
	private String professor = "Professor";
	private String alumneTutor = "AlumneTutor";
	Connection c;

	public TutorDAO() {
		c = ManagerDAO.getInstancia().getCon();
	}

	public boolean afegir(Persona p) throws ArgumentErroniException {

		if (!(p instanceof pojo.AlumneTutor || p instanceof pojo.Professor))
			throw new ArgumentErroniException();
		try {
			String nif = p.getNif();
			String nom = p.getNom();
			String cog = p.getCognoms();
			String corr = p.getCorreu_upv();

			// aci trie la taula que toca
			String sql = "INSERT INTO " + (p instanceof pojo.AlumneTutor ? alumneTutor : professor)
					+ "(nif, nom, cognoms, correu_upv)" + "VALUES('" + nif + "','" + nom + "','" + cog + "','" + corr
					+ "')";

			// prepara els tokens
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);

			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public ArrayList<Professor> llistarProfessors() throws SQLException {
		ArrayList<Professor> llista = new ArrayList<>();
		String sql = "select * from " + professor +" order by cognoms";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String nif = rs.getString("nif");
			String nom = rs.getString("nom");
			String cognoms = rs.getString("cognoms");
			String correu = rs.getString("correu_upv");
			llista.add(new Professor(nif, nom, cognoms, correu));
		}
		return llista;
	}

	public ArrayList<AlumneTutor> llistarAlumnesTutors() throws SQLException {
		ArrayList<AlumneTutor> llista = new ArrayList<>();
		String sql = "select * from " + alumneTutor + " order by cognoms";
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			String nif = rs.getString("nif");
			String nom = rs.getString("nom");
			String cognoms = rs.getString("cognoms");
			String correu = rs.getString("correu_upv");
			llista.add(new AlumneTutor(nif, nom, cognoms, correu));
		}
		return llista;
	}

	public boolean esborrar(Persona p) throws ArgumentErroniException {
		if (!(p instanceof pojo.AlumneTutor || p instanceof pojo.Professor))
			throw new ArgumentErroniException();

		String sql = "delete from " + (p instanceof pojo.AlumneTutor ? alumneTutor : professor) + " where nif = '"
				+ p.getNif() + "'";

		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public boolean editar(Persona p) throws ArgumentErroniException {
		if (!(p instanceof pojo.AlumneTutor || p instanceof pojo.Professor))
			throw new ArgumentErroniException();

		
		String sql = "update " + (p instanceof pojo.AlumneTutor ? alumneTutor : professor) + " set nom = '" + p.getNom()
				+ "', cognoms = '" + p.getCognoms() + "', correu_upv = '" + p.getCorreu_upv() + "' where nif = '"
				+ p.getNif()+"'";
		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt = c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			return false;
		}
		return true;
		
	}

}
