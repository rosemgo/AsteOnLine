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

import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.modelImpl.KeywordImpl;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;

public class KeywordDaoMysqlJdbc implements KeywordDao{

	Logger logger = Logger.getLogger(KeywordDaoMysqlJdbc.class);
	

	public List<Keyword> getKeywords(){
		logger.debug("in getKeywords");
		List<Keyword> listaKeywords = new ArrayList<Keyword>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			String query = "SELECT * FROM keyword ORDER BY keyword ASC";
			
			pstmt = connection.prepareStatement(query);
			logger.debug("Select Query: " + query);
			rs = pstmt.executeQuery(query);
			
			while(rs.next()){
				Keyword key = new KeywordImpl();
				key.setIdKeyword(rs.getInt("idKeyword"));
				key.setKeyword(rs.getString("keyword"));
				listaKeywords.add(key);
				logger.debug("(" + key.getIdKeyword() + ", " + key.getKeyword() + ")");
			}
			logger.debug("Caricate " + listaKeywords.size() + " categorie");
			
		
		} catch (SQLException  e) {
				e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(rs != null) {
					rs.close();
					}
					
				if(pstmt != null) {
					pstmt.close();
					}
					
				if(connection != null) {
					connection.close();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaKeywords;
	}

	
	public Keyword getKeywordById(Integer id) {
		logger.debug("in getKeywordById");
		Keyword keyword = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM keyword WHERE (idkeyword = ?)";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()){
				keyword = new KeywordImpl();
				keyword.setIdKeyword(rs.getInt("idkeyword"));
				keyword.setKeyword(rs.getString("keyword"));
			}
						
		} catch (SQLException  e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(rs != null) {
					rs.close();
					}
					
				if(pstmt != null) {
					pstmt.close();
					}
					
				if(connection != null) {
					connection.close();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return keyword;
	
	}

	
	public Keyword getKeywordByWord(String key) {
		logger.debug("in getKeywordByWord: " + key);
		Keyword keyword = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM keyword WHERE (keyword = ?)";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, key);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()){
				logger.debug("creo la nuova parola");
				keyword = new KeywordImpl();
				keyword.setIdKeyword(rs.getInt("idkeyword"));
				keyword.setKeyword(rs.getString("keyword"));
				
				logger.debug("Keyword recuperata: ( " + keyword.getIdKeyword() + ")");
				
			}
			
		} catch (SQLException  e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(rs != null) {
					rs.close();
					}
					
				if(pstmt != null) {
					pstmt.close();
					}
					
				if(connection != null) {
					connection.close();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("Il ritorno �: " + keyword);
		return keyword;
		
	}

/*
	public Keyword getKeywordByWordTest(String key) {
		logger.debug("in getKeywordByWordTest: " + key);
		Keyword keyword = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM keyword WHERE (keyword = ?)";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, key);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()){
				logger.debug("creo la nuova parola");
				keyword = new KeywordImpl();
				keyword.setIdKeyword(rs.getInt("idkeyword"));
				keyword.setKeyword(rs.getString("keyword"));
				
				logger.debug("Keyword recuperata: ( " + keyword.getIdKeyword() + ")");
				
			}
			
		} catch (SQLException  e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("Il ritorno �: " + keyword);
		return keyword;
		
	}
	
*/
	
	public List<Keyword> getKeywordByIdProdotto(Integer idProdotto) throws ClassNotFoundException, SQLException, IOException{
		logger.debug("in getKeywordByIdProdotto");
		List<Keyword> keywordList = new ArrayList<Keyword>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//connection = ConnectionPoolTomcat.getConnection();
		connection = DatabaseUtil.getConnection();
		
		String sql = "SELECT * FROM prodotto, keyword, prodotto_has_keyword " +
					"WHERE prodotto.idprodotto = prodotto_has_keyword.prodotto_idprodotto " +
					"AND prodotto_has_keyword.keyword_idkeyword = keyword.idkeyword " +
					"AND prodotto.idprodotto = ?";
			
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, idProdotto);
		logger.debug("Select Query: " + pstmt.toString());
		rs = pstmt.executeQuery();
			
		while(rs.next()){
				
			Keyword keyword = new KeywordImpl();
			keyword.setIdKeyword(rs.getInt("keyword.idkeyword"));
			keyword.setKeyword(rs.getString("keyword.keyword"));
			keywordList.add(keyword);
			logger.debug(" Aggiunta keyword (" + keyword.getIdKeyword() + ", " + keyword.getKeyword() + ")");
				
		}
			
		rs.close();
		pstmt.close();
		connection.close();
		
		return keywordList;
	}
	
/*	
	public List<Keyword> getKeywordByIdProdottoTest(Integer idProdotto) throws ClassNotFoundException, SQLException, IOException{
		logger.debug("in getKeywordByIdProdottoTest");
		List<Keyword> keywordList = new ArrayList<Keyword>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		connection = DatabaseUtil.getConnection();
			
		String sql = "SELECT * FROM prodotto, keyword, prodotto_has_keyword " +
					"WHERE prodotto.idprodotto = prodotto_has_keyword.prodotto_idprodotto " +
					"AND prodotto_has_keyword.keyword_idkeyword = keyword.idkeyword " +
					"AND prodotto.idprodotto = ?";
			
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, idProdotto);
		logger.debug("Select Query: " + pstmt.toString());
		rs = pstmt.executeQuery();
			
		while(rs.next()){
				
			Keyword keyword = new KeywordImpl();
			keyword.setIdKeyword(rs.getInt("keyword.idkeyword"));
			keyword.setKeyword(rs.getString("keyword.keyword"));
			keywordList.add(keyword);
			logger.debug(" Aggiunta keyword (" + keyword.getIdKeyword() + ", " + keyword.getKeyword() + ")");
				
		}
			
		rs.close();
		pstmt.close();
		connection.close();
		
		return keywordList;
	}
	
*/
	
	public int insertKeyword(Keyword keyword) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in insertKeyword");
		Integer autoincrementKey = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		connection = DatabaseUtil.getConnection();// utilizzato in caso di caricamento Keyword al primo avvio, con il Test
		//connection = ConnectionPoolTomcat.getConnection();
		
		connection.setAutoCommit(false);
				
						
		String sql = "INSERT INTO keyword (keyword) VALUES (?)";
		pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, keyword.getKeyword());
		logger.debug("Insert Query: " + pstmt.toString());
		int insertStatus = pstmt.executeUpdate();
		if (insertStatus==1){
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
		        autoincrementKey = rs.getInt(1);
		    }
		}
		keyword.setIdKeyword(autoincrementKey);
			
		if(rs != null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
			
		connection.commit();
		logger.info("Inserimento nuova keyword (" + autoincrementKey + ", " + keyword.getKeyword() + ")");
			
		connection.setAutoCommit(true);
		connection.close();
		
		return autoincrementKey;
	}
/*	
	public int insertKeywordTest(Keyword keyword) throws ClassNotFoundException, SQLException, IOException{
		logger.debug("in insertKeywordTest");
		Integer autoincrementKey = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = DatabaseUtil.getConnection();// utilizzato in caso di caricamento Keyword al primo avvio, con il Test
		
		connection.setAutoCommit(false);
				
						
		String sql = "INSERT INTO keyword (keyword) VALUES (?)";
		pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, keyword.getKeyword());
		logger.debug("Insert Query: " + pstmt.toString());
		int insertStatus = pstmt.executeUpdate();
		if (insertStatus==1){
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
		        autoincrementKey = rs.getInt(1);
		    }
		}
		keyword.setIdKeyword(autoincrementKey);
			
		if(rs != null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
			
		connection.commit();
		logger.info("Inserimento nuova keyword (" + autoincrementKey + ", " + keyword.getKeyword() + ")");
			
		connection.setAutoCommit(true);
		connection.close();
		
		return autoincrementKey;
	}
*/
	
	
	public int insertListaKeyword(List<Keyword> keywords){
		logger.debug("in insertListaKeyword");
		Integer autoincrementKey = -1;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			connection.setAutoCommit(false);
						
			for(int i=0; i<keywords.size(); i++){
			
				String sql = "INSERT INTO keyword (keyword) VALUES (?)";
				pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, keywords.get(i).getKeyword());
				logger.debug("Insert Query: " + pstmt.toString());
				
				try{
					
					int insertStatus = pstmt.executeUpdate();
				
					if(insertStatus==1){
						rs = pstmt.getGeneratedKeys();
						if (rs.next()) {
					        autoincrementKey = rs.getInt(1);
					    }
						keywords.get(i).setIdKeyword(autoincrementKey);
						connection.commit();
						logger.info("Inserimento nuova keyword (" + autoincrementKey + ", " + keywords.get(i).getKeyword() + ")");
						
						
					}	
							
				}catch (Exception e) {
					logger.info("keyword ("+ keywords.get(i).getKeyword() +") non inserita!!!");
				}
				
			}
					
		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Rollback in inserimento keyword");
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {

			if (connection != null) {

				try {
					if(rs != null)
						rs.close();
					
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
					logger.debug("Connection chiusa");
				} catch (SQLException  e) {
					e.printStackTrace();
				}

			}
		}
		return autoincrementKey;
	}
	
		
	public int insertKeyword(String keyword) throws ClassNotFoundException, SQLException, IOException {
		Integer keywordIdKey = -1;
		KeywordDao dao = new KeywordDaoMysqlJdbc();
		Keyword key = new KeywordImpl();
		
		key = dao.getKeywordByWord(keyword);
		keywordIdKey = this.insertKeyword(key);
		
		return keywordIdKey;
	}
	
	
	public int deleteKeyword(Integer idKeyword){
		logger.debug("in deleteKeyword");
		Integer deletedRows = -1;	
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM prodotto_has_keyword WHERE (keyword_idkeyword = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idKeyword);
			logger.debug("Delete Query:" + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			
			sql = "DELETE FROM keyword WHERE (idkeyword = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idKeyword);
			logger.debug("Delete Query:" + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			
			logger.info("Eliminazione Keyword id: (" + idKeyword + ")");
			connection.commit();
			
		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Rollback in cancellazione Keyword");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {

			if (connection != null) {
				try {
					
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
					logger.debug("Connection chiusa");
				} catch (SQLException  e) {
					e.printStackTrace();
				}

			}
		}
		
		return deletedRows;		
		
	}

/*	
	public int deleteKeywordTest(Integer idKeyword){
		logger.debug("in deleteKeyword");
		Integer deletedRows = -1;	
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM prodotto_has_keyword WHERE (keyword_idkeyword = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idKeyword);
			logger.debug("Delete Query:" + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			
			sql = "DELETE FROM keyword WHERE (idkeyword = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idKeyword);
			logger.debug("Delete Query:" + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			
			logger.info("Eliminazione Keyword id: (" + idKeyword + ")");
			connection.commit();
			
		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Rollback in cancellazione Keyword");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {

			if (connection != null) {
				try {
					
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
					logger.debug("Connection chiusa");
				} catch (SQLException  e) {
					e.printStackTrace();
				}

			}
		}
		
		return deletedRows;		
		
	}
	
*/	
	public int updateKeyword(Keyword keyword){
		logger.debug("in updateKeyword");
		Integer uptadedRows = -1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		try {
			//connection = ConnectionPoolTomcat.getConnection();
			connection = DatabaseUtil.getConnection();
			
			connection.setAutoCommit(false);
			
			if(keyword.getIdKeyword() != null){
			
				
				String sql = "UPDATE  keyword SET keyword=? WHERE idkeyword=?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, keyword.getKeyword());
				pstmt.setInt(2, keyword.getIdKeyword());
				logger.debug("Update Query:" + pstmt.toString());
				uptadedRows = pstmt.executeUpdate();
				
				connection.commit();
				
				logger.info("Aggiornamento Keyword: (" + keyword.getIdKeyword() + ", " + keyword.getKeyword() + ")");
			}
		
		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("rollback in updateKeyword");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		return uptadedRows;
	}

	

}
