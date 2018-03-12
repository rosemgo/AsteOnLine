package it.unisannio.sweng.rosariogoglia.daoImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import it.unisannio.sweng.rosariogoglia.dao.ComuneDao;
import it.unisannio.sweng.rosariogoglia.dao.ProvinciaDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.Provincia;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProvinciaImpl;
import it.unisannio.sweng.rosariogoglia.daoImpl.ComuneDaoMysqlJdbc;

public class ProvinciaDaoMysqlJdbc implements ProvinciaDao{

	Logger logger = Logger.getLogger(ProvinciaDaoMysqlJdbc.class);
	
	
	public List<Provincia> getProvince() {
		logger.debug("in getProvincie");
		
		List<Provincia> listaProvince = new ArrayList<Provincia>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			Provincia provincia = null;
			String sql = "SELECT * FROM provincia " +
					     "ORDER BY nome_provincia ";
				
			pstmt = connection.prepareStatement(sql);
			logger.debug("Select query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				
				provincia = new ProvinciaImpl();
							
				ComuneDao dao = new ComuneDaoMysqlJdbc();
				List<Comune> listaComuni = dao.getComuniByIdProvincia(rs.getInt("idprovincia"));
							
				provincia.setIdProvincia(rs.getInt("idprovincia"));
				provincia.setNomeProvincia(rs.getString("nome_provincia"));
				provincia.setListaComuni(listaComuni);
				
				listaProvince.add(provincia);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		finally {
			if (connection!=null) {
				try {	
					rs.close();
					pstmt.close();
					connection.close();
				} catch (SQLException  e) {
						
						e.printStackTrace();
				}
					logger.debug("Connection chiusa");
				}
			}	
		
		return listaProvince;
	}
	
	
	public Provincia getProvinciaById(Integer idProvincia) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in getProvinciaById");
		Provincia provincia = null;
		Connection connection = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
		
			String sql = "SELECT * FROM provincia " +
					"WHERE idprovincia = ? ";
				
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProvincia);	
			logger.debug("Select query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				provincia = new ProvinciaImpl();
							
				ComuneDao dao = new ComuneDaoMysqlJdbc();
				List<Comune> listaComuni = dao.getComuniByIdProvincia(idProvincia);
							
				provincia.setIdProvincia(idProvincia);
				provincia.setNomeProvincia(rs.getString("nome_provincia"));
				provincia.setListaComuni(listaComuni);
							
			}
		}
			
			finally {
				if (connection!=null) {
					try {
						rs.close();
						pstmt.close();
						connection.close();
					} catch (SQLException  e) {
						
						e.printStackTrace();
					}
					logger.debug("Connection chiusa");
				}
			}	
		return provincia;
	}
	
}
