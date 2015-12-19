package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
		if(g.getAlumne2() == null){
			sql = "Insert into Grup (grup, professor, alumne1) values('"+grup+"','"+profesor+"','"+al1+"')";
		}else{
			String al2 = g.getAlumne2().getNif();
			sql = "Insert into Grup values('"+grup+"','"+profesor+"','"+al1+"','"+al2+"')";
		}
		
		try{
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
		}catch(SQLException e){
			return false;
		}
		return true;
	}
}
