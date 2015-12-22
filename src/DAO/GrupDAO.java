package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DAO.DTO.GrupDTO;
import POJO.Grup;

public class GrupDAO {

	Connection c;

	public GrupDAO() {
		c = ManagerDAO.getInstancia().getCon();
	}

	/*
	 * grup VARCHAR(3), professor VARCHAR(8), alumne1 VARCHAR(8), alumne2
	 * VARCHAR(8),
	 */
	public boolean afegir(Grup g) {
		String grup = g.getNom();
		String profesor = g.getProfessor().getNif();
		String al1 = g.getAlumne1().getNif();

		String sql;
		if (g.getAlumne2() == null) {
			sql = "Insert into Grup (grup, professor, alumne1) values('" + grup + "','" + profesor + "','" + al1 + "')";
		} else {
			String al2 = g.getAlumne2().getNif();
			sql = "Insert into Grup values('" + grup + "','" + profesor + "','" + al1 + "','" + al2 + "')";
		}

		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public ArrayList<GrupDTO> llistarGrups() throws SQLException {
		String sql = "select * from Grup";
		ArrayList<GrupDTO> llista = new ArrayList<>();

		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		/* UTILITZEM DTO PER A RESOLDRE DEPENDENCIES */
		while (rs.next()) {
			llista.add(new GrupDTO(rs.getString("grup"), rs.getString("professor"), rs.getString("alumne1"),
					rs.getString("alumne2")));
		}
		return llista;

	}

	public boolean esborrar(Grup g) {

		String sql = "delete from Grup where grup = '" + g.getNom()+"'";
		Statement stmt;
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean editar(Grup g) {
		String sql = "update Grup set professor = '" + g.getProfessor().getNif() + "', " + "alumne1 = '"
				+ g.getAlumne1().getNif() + "', "
				+ (g.getAlumne2() == null ? "" : "alumne2 = '" + g.getAlumne2().getNif() + "'") + " where Grup = "
				+ g.getNom();
		
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
		
	}
}
