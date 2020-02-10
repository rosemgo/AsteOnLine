package it.unisannio.sweng.rosariogoglia.daoImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.unisannio.sweng.rosariogoglia.dao.RandomPasswordDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.RandomPassword;

import org.apache.log4j.Logger;

/**
 * Questa classe è usata per il "Password dimenticata" e quindi per generare password random per permettere l'accesso ad un utente
 * 
 * @author Rosario
 */
public class RandomPasswordDaoMysqlJdbc implements RandomPasswordDao{
	
	Logger logger = Logger.getLogger(RandomPasswordDaoMysqlJdbc.class);
	
	/**
	 * Metodo per verificare se la pssword è già stata generata in passato
	 */
	public boolean checkHashPassword(String hashPassword){
		
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM random_password " + "WHERE hashpassword = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, hashPassword);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = true;
			}
						
			
		} catch (SQLException | ClassNotFoundException | IOException e) {
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
		
		return result;
			
	}
	
	
	public boolean checkHashPasswordAndIdUtente(String hashPassword, Integer idUtente){
		
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM random_password " +
					"WHERE hashpassword = ? " +
					"AND " +
					"idutente = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, hashPassword);
			pstmt.setInt(2, idUtente);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = true;
			}		
			
		} catch (SQLException | ClassNotFoundException | IOException e) {
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
		
		return result;
			
	}
	
	
	public Integer insertRandomPassword(RandomPassword randPass){
		Integer idRandomPassword = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO random_password(hashpassword, idutente) VALUES(?, ?) ";
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, randPass.getHashPassword());
			pstmt.setInt(2, randPass.getIdUtente());
			logger.debug("Insert Query: " + pstmt.toString());	
			int insertRows = pstmt.executeUpdate();
			if(insertRows == 1){
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					idRandomPassword = rs.getInt(1);
				}
				rs.close();
			}
						
			connection.commit();
			
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("Rollback in inserimento RandomPassword");
			
		}
		finally{
			try {
				connection.setAutoCommit(true);
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return idRandomPassword;
	
	}
	
	
	public Integer deleteRandomPassword(String hashPassword){
		Integer deleteRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM random_password WHERE hashpassword = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, hashPassword);
			logger.debug("Delete Query: " + pstmt.toString());			
			deleteRow = pstmt.executeUpdate();
									
			connection.commit();
			
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("Rollback in delete RandomPassword");
			
		}
		finally{
			try {
				connection.setAutoCommit(true);
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return deleteRow;
	
	}
	
	
	
	

}
