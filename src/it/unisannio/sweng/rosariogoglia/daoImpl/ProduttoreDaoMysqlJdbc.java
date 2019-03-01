package it.unisannio.sweng.rosariogoglia.daoImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ProduttoreDaoMysqlJdbc implements ProduttoreDao{

	Logger logger = Logger.getLogger(ProduttoreDaoMysqlJdbc.class);
		
	public List<Produttore> getProduttori(){
		List<Produttore> listaProduttori = new ArrayList<Produttore>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			//connection = DatabaseUtil.getConnection();
			connection = ConnectionPoolTomcat.getConnection();
		
			String sql = "SELECT * FROM produttore ORDER BY nome ASC ";
			pstmt = connection.prepareStatement(sql);
			
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Produttore produttore = new ProduttoreImpl();
							
				produttore.setIdProduttore(rs.getInt("idproduttore"));
				produttore.setNome(rs.getString("nome"));
				
				/*Preleviamo la lista dei prodotti relativi al produttore*/
				ProdottoDao dao = new ProdottoDaoMysqlJdbc();
				List<Prodotto> listaProdotti = dao.getProdottiByIdProduttore(rs.getInt("idproduttore"));
				produttore.setProdotti(listaProdotti);
				
				listaProduttori.add(produttore);
				logger.debug("(" + produttore.getIdProduttore() + ", " + produttore.getNome() + ")");
			}
		
			
		} catch (ClassNotFoundException	| IOException | SQLException  e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaProduttori;
	}
	
	
	public List<Produttore> getProduttoriTest(){
		List<Produttore> listaProduttori = new ArrayList<Produttore>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			
		
			String sql = "SELECT * FROM produttore ORDER BY nome ASC ";
			pstmt = connection.prepareStatement(sql);
			
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Produttore produttore = new ProduttoreImpl();
							
				produttore.setIdProduttore(rs.getInt("idproduttore"));
				produttore.setNome(rs.getString("nome"));
				
				/*Preleviamo la lista dei prodotti relativi al produttore*/
				ProdottoDao dao = new ProdottoDaoMysqlJdbc();
				List<Prodotto> listaProdotti = dao.getProdottiByIdProduttoreTest(rs.getInt("idproduttore"));
				produttore.setProdotti(listaProdotti);
				
				listaProduttori.add(produttore);
				logger.debug("(" + produttore.getIdProduttore() + ", " + produttore.getNome() + ")");
			}
		
			
		} catch (ClassNotFoundException	| IOException | SQLException  e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaProduttori;
	}
	
	
	public Produttore getProduttoreById(Integer idProduttore){
		logger.debug("in getProduttoreById");
		Produttore produttore = null;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
						
			String sql = "SELECT * FROM produttore WHERE (idproduttore = ?) ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProduttore);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				produttore = new ProduttoreImpl();
				produttore.setIdProduttore(rs.getInt("idproduttore"));
				produttore.setNome(rs.getString("nome"));
				produttore.setWebsite(rs.getString("website"));
			}
						
		} catch (SQLException  e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(produttore != null)
			logger.debug("produttore: " + produttore.toString());
		return produttore;
	}

	public Produttore getProduttoreByIdTest(Integer idProduttore){
		logger.debug("in getProduttoreById");
		Produttore produttore = null;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DatabaseUtil.getConnection();
						
			String sql = "SELECT * FROM produttore WHERE (idproduttore = ?) ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProduttore);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				produttore = new ProduttoreImpl();
				produttore.setIdProduttore(rs.getInt("idproduttore"));
				produttore.setNome(rs.getString("nome"));
				produttore.setWebsite(rs.getString("website"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(produttore != null)
			logger.debug("produttore: " + produttore.toString());
		return produttore;
	}
	
	
	
	public Produttore getProduttoreByNome(String nomeProduttore) {
		Produttore produttore = null;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			String sql = "SELECT * FROM produttore WHERE (nome = ?)";
						
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nomeProduttore);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				produttore = new ProduttoreImpl();
				produttore.setIdProduttore(rs.getInt("idproduttore"));
				produttore.setNome(rs.getString("nome"));
				produttore.setWebsite(rs.getString("website"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return produttore;
	}

	
	public Produttore getProduttoreByNomeTest(String nomeProduttore) {
		Produttore produttore = null;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM produttore WHERE (nome = ?)";
						
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nomeProduttore);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				produttore = new ProduttoreImpl();
				produttore.setIdProduttore(rs.getInt("idproduttore"));
				produttore.setNome(rs.getString("nome"));
				produttore.setWebsite(rs.getString("website"));
			}
			
			
		} catch (SQLException e) {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return produttore;
	}

	
	public List<Produttore> getProduttoriByIdCategoria(Integer idCategoria){
		logger.debug("in getProduttoriByIdCategoria");
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		Produttore produttore; 
		List<Produttore> listaProduttori = new ArrayList<Produttore>();
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			String sql = "SELECT * FROM produttore, categoria_has_produttore " +
					"WHERE produttore.idproduttore = categoria_has_produttore.produttore_idproduttore " +
					"AND categoria_has_produttore.categoria_idcategoria = ? ";
			
			pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, idCategoria);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				produttore = new ProduttoreImpl();
				
				produttore.setIdProduttore(rs.getInt("produttore.idproduttore"));
				produttore.setNome(rs.getString("produttore.nome"));
				produttore.setWebsite(rs.getString("produttore.website"));
				
				listaProduttori.add(produttore);
							
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaProduttori;
			
	}
	
	
	public List<Produttore> getProduttoriByIdCategoriaTest(Integer idCategoria){
		logger.debug("in getProduttoriByIdCategoria");
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		Produttore produttore; 
		List<Produttore> listaProduttori = new ArrayList<Produttore>();
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM produttore, categoria_has_produttore " +
					"WHERE produttore.idproduttore = categoria_has_produttore.produttore_idproduttore " +
					"AND categoria_has_produttore.categoria_idcategoria = ? ";
			
			pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, idCategoria);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				produttore = new ProduttoreImpl();
				
				produttore.setIdProduttore(rs.getInt("produttore.idproduttore"));
				produttore.setNome(rs.getString("produttore.nome"));
				produttore.setWebsite(rs.getString("produttore.website"));
				
				listaProduttori.add(produttore);
							
			}
			
			
		} catch (SQLException e) {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaProduttori;
			
	}
	
	public Integer insertProduttore(Produttore produttore){
		logger.debug("in insertProduttore");
		
		Integer autoincrementKey = -1;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = ConnectionPoolTomcat.getConnection();
			
			//connection = DatabaseUtil.getConnection(); utilizzato in caso di caricamento categorie al primo avvio, con il Test
			connection.setAutoCommit(false);
		
			String sql = "INSERT INTO produttore (nome, website) VALUES (?, ?)";
				
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, produttore.getNome());
			pstmt.setString(2, produttore.getWebsite());
				
			logger.debug("Insert Query: " + pstmt.toString());
				
			int insertStatus = pstmt.executeUpdate();
			if (insertStatus==1){
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
			        autoincrementKey = rs.getInt(1);
			    }
			}
			produttore.setIdProduttore(autoincrementKey);

			connection.commit();
				
			logger.debug("Inserimento Produttore (" + autoincrementKey + ", " + produttore.getNome() + ")");
			
		} catch (SQLException  e) {
			e.printStackTrace();
			System.out.println("Inserimento produttore non riuscito");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			if (connection!=null) {
				try {
					if(rs != null)
						rs.close();
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException  e) {
					
					e.printStackTrace();
				}
				logger.debug("Connection chiusa");
			}
		}
		
		return autoincrementKey;
	}

	
	public Integer insertProduttoreTest(Produttore produttore){
		logger.debug("in insertProduttoreTest");
		
		Integer autoincrementKey = -1;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = DatabaseUtil.getConnection(); //utilizzato in caso di caricamento categorie al primo avvio, con il Test
			
			connection.setAutoCommit(false);
		
			String sql = "INSERT INTO produttore (nome, website) VALUES (?, ?)";
				
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, produttore.getNome());
			pstmt.setString(2, produttore.getWebsite());
				
			logger.debug("Insert Query: " + pstmt.toString());
				
			int insertStatus = pstmt.executeUpdate();
			if (insertStatus==1){
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
			        autoincrementKey = rs.getInt(1);
			    }
			}
			produttore.setIdProduttore(autoincrementKey);

			connection.commit();
				
			logger.debug("Inserimento Produttore (" + autoincrementKey + ", " + produttore.getNome() + ")");
			
		} catch (SQLException  e) {
			e.printStackTrace();
			System.out.println("Inserimento produttore non riuscito");
			try {
				connection.rollback();
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
			if (connection!=null) {
				try {
					if(rs != null)
						rs.close();
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException  e) {
					
					e.printStackTrace();
				}
				logger.debug("Connection chiusa");
			}
		}
		
		return autoincrementKey;
	}
	
	
	
	public boolean checkDeleteProduttore(Integer idProduttore){
		
		boolean result = true;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			String sql = "SELECT * FROM inserzione, produttore, prodotto " +
					"WHERE produttore.idproduttore = prodotto.produttore_idproduttore " +
					"AND prodotto.idprodotto = inserzione.prodotto_idprodotto " +
					"AND idproduttore = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProduttore);
			logger.debug("Check Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = false;
			}
			
			
		} catch (SQLException  e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Integer deleteProduttore(Integer idProduttore) {
		logger.info("Eliminazione Produttore: (" + idProduttore + ")");
		Integer deletedRows = -1;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
				
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
				
				
//				//prima si elimina dalla tabella prodotto_has_keyword
//				String sql = "DELETE prodotto_has_keyword.* FROM prodotto_has_keyword, prodotto " +
//						"WHERE " +
//						"prodotto.idprodotto = prodotto_has_keyword.prodotto_idprodotto " +
//						"AND " +
//						"prodotto.produttore_idproduttore = ?";
//				pstmt = connection.prepareStatement(sql);
//				pstmt.setInt(1, idProduttore);
//				logger.debug("Delete Query: " + pstmt.toString());
//				pstmt.executeUpdate();
//				
//				//cancello il prodotto
//				sql = "DELETE FROM prodotto WHERE (produttore_idproduttore = ?)";
//				pstmt = connection.prepareStatement(sql);
//				pstmt.setInt(1, idProduttore);
//				logger.debug("Delete Query: " + pstmt.toString());
//				pstmt.executeUpdate();
//				
//				
//				//cancello l'associazione categoria-produttore
//				sql = "DELETE FROM categoria_has_produttore WHERE produttore_idproduttore = ?";
//				
//				pstmt = connection.prepareStatement(sql);
//				pstmt.setInt(1, idProduttore);
//				logger.debug("Delete Query:" + pstmt.toString());
//				pstmt.executeUpdate();			
//								
				//cancello il produttore
				String sql = "DELETE FROM produttore WHERE (idproduttore = ?)";
					
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, idProduttore);
				logger.debug("Delete Query: " + pstmt.toString());
				deletedRows = pstmt.executeUpdate();
				
				connection.commit();
				
						
				logger.info("Produttore Eliminato");
			
		} catch (SQLException  e) {
			e.printStackTrace();
			System.out.println("Cancellazione produttore non riuscita");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
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
		
		return deletedRows;
	}
	
	public Integer deleteProduttoreTest(Integer idProduttore) {
		logger.info("Eliminazione Produttore Test: (" + idProduttore + ")");
		Integer deletedRows = -1;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
				
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			//cancello il produttore
			String sql = "DELETE FROM produttore WHERE (idproduttore = ?)";
					
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProduttore);
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
				
			connection.commit();
									
			logger.info("Produttore Eliminato");
			
		} catch (SQLException  e) {
			e.printStackTrace();
			System.out.println("Cancellazione produttore non riuscita");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
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
		
		return deletedRows;
	}


	public Integer updateProduttore(Produttore produttore){
		logger.debug("in updateProduttore");
		Integer uptadedRows = -1;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE produttore SET nome = ?, website = ? WHERE idproduttore = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, produttore.getNome());
			pstmt.setString(2, produttore.getWebsite());
			pstmt.setInt(3, produttore.getIdProduttore());
			logger.debug("Update Query:" + pstmt.toString());
			
			uptadedRows = pstmt.executeUpdate();
						
			connection.commit();
			
		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
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
		logger.info("Aggiornamento Produttore: (" + produttore.getIdProduttore() + ", " + produttore.getNome() + ")");
		
		return uptadedRows;
	}
	
	
	
	
}
