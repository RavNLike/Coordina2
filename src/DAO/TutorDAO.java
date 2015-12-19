package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import POJO.AlumneTutor;
import POJO.Persona;
import POJO.Professor;
/*
 * Com les taules TUTOR i PROFESSOR tenen la mateix info
 * creem una sola classe DAO, que usara una taula o altra
 * en cada metode hi ha una ternaria que triar� la taula adecuada
 */
import POJO.EXCEPTIONS.ArgumentErroniException;

public class TutorDAO {
	private String professor = "Professor";
	private String alumneTutor = "AlumneTutor";
	Connection c;

	public TutorDAO() {
		c = ManagerDAO.getInstancia().getCon();
	}

	public boolean afegir(Persona p) throws ArgumentErroniException {

		if (!(p instanceof POJO.AlumneTutor || p instanceof POJO.Professor))
			throw new ArgumentErroniException();
		try {
			String nif = p.getNif();
			String nom = p.getNom();
			String cog = p.getCognoms();
			String corr = p.getCorreu_upv();

			// aci trie la taula que toca
			String sql = "INSERT INTO " + (p instanceof POJO.AlumneTutor ? alumneTutor : professor)
					+ "(nif, nom, cognoms, correu_upv)" + "VALUES('" + nif + "','" + nom + "','" + cog + "','" + corr
					+ "')";

			// prepara els tokens
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Professor> llistarProfessors() throws SQLException {
		ArrayList<Professor> llista = new ArrayList<>();
		String sql = "select * from " + professor;
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
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
		String sql = "select * from " + alumneTutor;
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			String nif = rs.getString("nif");
			String nom = rs.getString("nom");
			String cognoms = rs.getString("cognoms");
			String correu = rs.getString("correu_upv");
			llista.add(new AlumneTutor(nif, nom, cognoms, correu));
		}
		return llista;
	}
	
	public boolean esborrar(Persona p) throws ArgumentErroniException{
		if (!(p instanceof POJO.AlumneTutor || p instanceof POJO.Professor))
			throw new ArgumentErroniException();
		
		String sql = "delete from "+ (p instanceof POJO.AlumneTutor ? alumneTutor : professor)
				+" where nif = '"+p.getNif()+"'";
		
		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

}
