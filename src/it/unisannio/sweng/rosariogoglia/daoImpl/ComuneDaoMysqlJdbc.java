package it.unisannio.sweng.rosariogoglia.daoImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.unisannio.sweng.rosariogoglia.dao.ComuneDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.Provincia;
import it.unisannio.sweng.rosariogoglia.modelImpl.ComuneImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProvinciaImpl;

public class ComuneDaoMysqlJdbc implements ComuneDao{

	Logger logger = Logger.getLogger(ComuneDaoMysqlJdbc.class);
	


	public Comune getComuneById(int idComune) throws ClassNotFoundException, SQLException, IOException {
		Comune comune = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			String sql = "SELECT * FROM comune, provincia " +
					"WHERE comune.provincia_idprovincia = provincia.idprovincia " +
					"AND" +
					"comune.idcomune = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idComune);
			logger.debug("Select query " + pstmt.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				
				comune = new ComuneImpl();
				Provincia provincia = new ProvinciaImpl();
				
				provincia.setIdProvincia(rs.getInt("provincia.idprovincia"));
				provincia.setNomeProvincia(rs.getString("provincia.nome_provincia"));
				
				comune.setIdComune(rs.getInt("comune.idcomune"));
				comune.setNomeComune(rs.getString("comune.nome_comune"));
				comune.setIdProvincia(rs.getInt("comune.provincia_idprovincia"));
			
				comune.setProvincia(provincia);
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			rs.close();
			pstmt.close();
			connection.close();
		}
	
		return comune;
	}
	
	public List<Comune> getComuni(){
		logger.debug("in getComuni");
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Comune> listaComuni = new ArrayList<Comune>();

		try {
				connection = ConnectionPoolTomcat.getConnection();
			
				Comune comune = null;
				
				String sql = "SELECT * FROM comune, provincia " +
						"WHERE comune.provincia_idprovincia = provincia.idprovincia " +
						"ORDER BY comune.nome_comune";
				
				pstmt = connection.prepareStatement(sql);
				logger.debug("Select query " + pstmt.toString());
				rs = pstmt.executeQuery();
				while(rs.next()){
					
					comune = new ComuneImpl();
					Provincia provincia = new ProvinciaImpl();
					
					provincia.setIdProvincia(rs.getInt("provincia.idprovincia"));
					provincia.setNomeProvincia(rs.getString("provincia.nome_provincia"));
					
					comune.setIdComune(rs.getInt("comune.idcomune"));
					comune.setNomeComune(rs.getString("comune.nome_comune"));
					comune.setIdProvincia(rs.getInt("comune.provincia_idprovincia"));
				
					comune.setProvincia(provincia);
					
					listaComuni.add(comune);		
					
				}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally{
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaComuni;
		
	}
	
	
	public List<Comune> getComuniByIdProvincia(Integer idProvincia){
		logger.debug("In getComuniByIdProvincia");
		List<Comune> listaComuni = new ArrayList<Comune>();
		Comune comune = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
						
			String sql = "SELECT * FROM comune, provincia " +
					"WHERE comune.provincia_idprovincia = provincia.idprovincia " +
					"AND comune.provincia_idprovincia = ? " +
					"ORDER BY comune.nome_comune ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProvincia);
			logger.debug("Select query " + pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				
				comune = new ComuneImpl();
				Provincia provincia = new ProvinciaImpl();
				
				provincia.setIdProvincia(rs.getInt("provincia.idprovincia"));
				provincia.setNomeProvincia(rs.getString("provincia.nome_provincia"));
				
				comune.setIdComune(rs.getInt("comune.idcomune"));
				comune.setNomeComune(rs.getString("comune.nome_comune"));
				comune.setIdProvincia(rs.getInt("comune.provincia_idprovincia"));
			
				comune.setProvincia(provincia);
				
				listaComuni.add(comune);		
				
				
			}
					
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaComuni;
		
	}
	
	

}
