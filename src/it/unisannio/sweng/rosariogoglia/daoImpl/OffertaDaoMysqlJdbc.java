package it.unisannio.sweng.rosariogoglia.daoImpl;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;





import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.OffertaDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.OffertaImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

public class OffertaDaoMysqlJdbc implements OffertaDao{

	
	Logger logger = Logger.getLogger(OffertaDaoMysqlJdbc.class);
	
	public OffertaDaoMysqlJdbc (){
		DOMConfigurator.configure("C:/Users/Rosario/git/AsteOnLine2/WebContent/WEB-INF/log4jConfig.xml");
	}
	
	public Integer insertOfferta(Offerta offerta) throws ClassNotFoundException, IOException{
		logger.debug("in insertOfferta");			
		int offertaIdKey = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
					
			String sql = "INSERT INTO offerta(somma, data, utente_registrato_idutente, inserzione_idinserzione) VALUES (?, ?, ?, ?) ";
			
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, offerta.getSomma());
			pstmt.setTimestamp(2, Utility.convertitoreDataUtilToTimestamp(offerta.getData()));
			pstmt.setInt(3, offerta.getIdUtenteRegistrato());
			pstmt.setInt(4, offerta.getIdInserzione());
			
			logger.debug("Insert Query: " + pstmt.toString());
			int insertRow = pstmt.executeUpdate();
			
			if(insertRow == 1){
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					offertaIdKey = rs.getInt(1);
				}
				offerta.setIdOfferta(offertaIdKey);
				rs.close();
			}
			
			connection.commit();
			
			logger.debug("inserimento completato, idofferta: " + offertaIdKey);	
			
		} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
					logger.debug("Roolback in cancellazione inserzione");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
		}
		finally{
			if (connection!=null) {
				try {
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException  e) {
					e.printStackTrace();
				}
				logger.debug("Connection chiusa");
			}
		}		
		return offertaIdKey;
	}

	
	public Integer deleteOfferta(Offerta offerta) throws SQLException, ClassNotFoundException, IOException {
		logger.debug("in deleteOfferta");		
		int deletedRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM offerta WHERE idofferta = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, offerta.getIdOfferta());
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRow = pstmt.executeUpdate();
						
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Roolback in cancellazione inserzione");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}				
		}
		finally{
			if (connection!=null) {
				try {
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException  e) {
					e.printStackTrace();
				}
				logger.debug("Connection chiusa");
			}
		}	
		logger.debug("offerta cancellata");
		return deletedRow;
	}
	
	
	public Integer deleteOffertaByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in deleteOffertaByIdInserzione");		
		int deletedRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM offerta WHERE inserzione_idinserzione = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idInserzione);
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRow = pstmt.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Roolback in deleteOffertaByIdInserzione");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}				
		}
		finally{
			if (connection!=null) {
				try {
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException  e) {
					e.printStackTrace();
				}
				logger.debug("Connection chiusa");
			}
		}	
		logger.debug("offerta cancellata");
		return deletedRow;
		
	}

	

	
	
	
}
