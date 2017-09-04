package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.dto.GrupDTO;
import pojo.Grup;

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
		String profesor1 = g.getProfessor1().getNif();
		String al1 = g.getAlumne1().getNif();
		
		// professor 1
		String sql = "Insert into Grup values('" + grup + "','" + profesor1 + "'";
		// profesor 2 (opcional)
		if (g.getProfessor2() != null) {
			sql += ", '" + g.getProfessor2().getNif() + "'";
		}else {
			sql += ", 'null'";
		}
		
		//alumne 1
		sql += ", '" + al1 + "'";
		// alumne 2, 3 i 4 (opcional)
		if (g.getAlumne2() != null) {
			sql += ", '" + g.getAlumne2().getNif() + "'";
		}
		else {
			sql += ", 'null'";
		}
		//
		if (g.getAlumne3() != null) {
			sql += ", '" + g.getAlumne3().getNif() + "'";
		}
		else {
			sql += ", 'null'";
		}
		//
		if (g.getAlumne4() != null) {
			sql += ", '" + g.getAlumne4().getNif() + "'";
		}else {
			sql += ", 'null'";
		}
		//fin del query
		sql+= ")";
		
		
		/*
		 * CODI ANTIC QUAN SOLS HI HAVIA UN PROF I DOS TUTORS
		 * 
		if (g.getAlumne2() == null) {
			sql = "Insert into Grup (grup, professor, alumne1) values('" + grup + "','" + profesor + "','" + al1 + "')";
		} else {
			String al2 = g.getAlumne2().getNif();
			sql = "Insert into Grup values('" + grup + "','" + profesor + "','" + al1 + "','" + al2 + "')";
		}
		*/

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
			llista.add(new GrupDTO(rs.getString("grup"), 
					rs.getString("professor1"), 
					rs.getString("professor2"), 
					rs.getString("alumne1"),
					rs.getString("alumne2"), 
					rs.getString("alumne3"),
					rs.getString("alumne4")));
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
		/*
		 * 	CODI ANTIC
		 * 
		String sql = "update Grup set professor = '" + g.getProfessor().getNif() + "', " + "alumne1 = '"
				+ g.getAlumne1().getNif() + "' "
				+ (g.getAlumne2() == null ? "" : ", alumne2 = '" + g.getAlumne2().getNif() + "'") + " where Grup = '"
				+ g.getNom()+"'";
		*/
		
		String sql = "update Grup set professor1 = '" + g.getProfessor1().getNif() + "'";
		// profesor 2 (opcional)
			if (g.getProfessor2() != null) {
				sql += ", professor2 = '" + g.getProfessor2().getNif() + "'";
			}else {
				sql += ", 'null'";
			}
			
			//alumne 1
			sql += ", alumne1 = '" + g.getAlumne1().getNif() + "'";
			// alumne 2, 3 i 4 (opcional)
			if (g.getAlumne2() != null) {
				sql += ", alumne2 = '" + g.getAlumne2().getNif() + "'";
			}
			else {
				sql += ", alumne2 = 'null'";
			}
			//
			if (g.getAlumne3() != null) {
				sql += ", alumne3 = '" + g.getAlumne3().getNif() + "'";
			}
			else {
				sql += ", alumne3 = 'null'";
			}
			//
			if (g.getAlumne4() != null) {
				sql += ", alumne4 = '" + g.getAlumne4().getNif() + "'";
			}
			else {
				sql += ", alumne4 = 'null'";
			}
			//fin del query
			sql+= ")";	
	
		
		
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
