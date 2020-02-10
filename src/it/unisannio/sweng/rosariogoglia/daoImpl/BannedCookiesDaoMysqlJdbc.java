package it.unisannio.sweng.rosariogoglia.daoImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.BannedCookies;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;

public class BannedCookiesDaoMysqlJdbc implements BannedCookiesDao{
	
	
	Logger logger = Logger.getLogger(BannedCookiesDaoMysqlJdbc.class);
	

	public Integer insertBannedCookies(BannedCookies cookie){
		logger.info("in insertBannedCookies");
		Integer autoincrementKey = -1;
		
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		try{
			
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
							
			String sql = "INSERT INTO banned_cookies (idutentebannato, cookie) VALUES (?, ?)";
			
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, cookie.getIdUtenteBannato());		
			pstmt.setString(2, cookie.getCookie());
			logger.debug("Insert Query: " + pstmt.toString());
			int insertStatus = pstmt.executeUpdate();
			if(insertStatus == 1){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					autoincrementKey = rs.getInt(1);
				}
			}
			cookie.setIdBannedCookies(autoincrementKey);
			logger.debug("id delcookie è: " + autoincrementKey);
			
			connection.commit();
						
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			logger.debug("Rollback in inserimento cookies");
		}
		finally{
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				connection.setAutoCommit(true);
				connection.close();	
			} catch (SQLException  e) {
				
				e.printStackTrace();
			}
			
		}
		
		return autoincrementKey;
	}
	
	
	
	public Integer deleteBannedCookies(Integer idUtente){
		logger.info("in deleteBannedCookies");
		Integer deletedRows = -1;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
			
		try{
			
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
							
			String sql = "DELETE FROM banned_cookies WHERE idutentebannato = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtente);			
			logger.debug("Insert Query: " + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			
			connection.commit();
			
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			logger.debug("Rollback in delete cookies");
		}
		finally{
			try {
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();	
			} catch (SQLException  e) {
				
				e.printStackTrace();
			}
			
		}
		
		return deletedRows ;
	}
	
	
	
	public boolean checkUtenteRegistratoBanned(Integer idUtente){
		logger.info("in checkUtenteRegistratoBanned");
		
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM banned_cookies " +
					"WHERE idutentebannato = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtente);
			logger.debug("Insert Query: " + pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				result = true;
			}
			
		} catch (SQLException | ClassNotFoundException | IOException  e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				if(pstmt!= null)
					pstmt.close();
				connection.close();	
			} catch (SQLException  e) {
				
				e.printStackTrace();
			}
			
		}	
		
		return result;
	}
	
	
	

	
	
}
