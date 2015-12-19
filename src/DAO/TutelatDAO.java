package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import POJO.Tutelat;

public class TutelatDAO {

	Connection c;

	public TutelatDAO() {
		c = ManagerDAO.getInstancia().getCon();
	}

	/*
	 * nif nom cognoms correu_upv  correu_personal grup_patu 
	 * grup_matricula mobil
	 */
	public boolean afegir(Tutelat t) {
		try {
			String nif = t.getNif();
			String nom = t.getNom();
			String cog = t.getCognoms();
			String corr = t.getCorreu_upv();
			String corrPers = t.getCorreu_personal();
			String grupPatu = t.getGrup_patu().getNom();
			String grupMat = t.getGrup_matricula();
			String mobil = t.getMobil();
			
			String sql = "Insert into Tutelat values('"+nif+"','"+nom+"','"+cog+"','"+corr
					+"','"+corrPers+"','"+grupPatu+"','"+grupMat+"','"+mobil+"')";
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
