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






import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.CategoriaImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;




public class CategoriaDaoMysqlJdbc implements CategoriaDao{

	Logger logger = Logger.getLogger(CategoriaDaoMysqlJdbc.class);
	
	public CategoriaDaoMysqlJdbc (){
		DOMConfigurator.configure("C:/Users/Rosario/git//WebContent/WEB-INF/log4jConfig.xml");
	}
	
	public List<Categoria> getCategorie() throws ClassNotFoundException, IOException{
		logger.debug("in getCategorie");
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Categoria> listaCategorie = new ArrayList<Categoria>();
		try {
						
			connection = DatabaseUtil.getConnection();
			
			stmt = connection.createStatement();
			
			String query = "SELECT * FROM categoria ORDER BY nome ASC";
			logger.debug("Select Query: " + query);
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				Categoria cat = new CategoriaImpl();
				
				cat.setIdCategoria(rs.getInt("idcategoria"));
				cat.setNome(rs.getString("nome"));
				
				/*Preleviamo la lista dei produttori relativi alla categoria*/
				ProduttoreDao dao = new ProduttoreDaoMysqlJdbc();
				List<Produttore> listaProduttori = dao.getProduttoriByIdCategoria(rs.getInt("idcategoria"));
				cat.setListaProduttori(listaProduttori);
				
				listaCategorie.add(cat);
				logger.debug("(" + cat.getIdCategoria() + ", " + cat.getNome() + ")");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		finally{
			try {
				rs.close();
				stmt.close();
				
				connection.close();
			
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaCategorie;
	}
	
	
		
	public Categoria getCategoriaById(Integer idCategoria) throws ClassNotFoundException, IOException{
		logger.debug("in getCategoriaById");
		Categoria categoria = null;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM categoria WHERE (idcategoria = ?) ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idCategoria);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()){
				categoria = new CategoriaImpl();
				categoria.setIdCategoria(rs.getInt("idcategoria"));
				categoria.setNome(rs.getString("nome"));
			
				logger.debug("categoria: " + categoria.toString());
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				
				connection.close();
			
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return categoria;
	}
	
	
	public Categoria getCategoriaByNome(String nomeCategoria) throws ClassNotFoundException, IOException{
		logger.debug("in getCategoriaByNome");
		Categoria categoria = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = DatabaseUtil.getConnection();
		
			String sql = "SELECT * FROM categoria WHERE nome = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nomeCategoria);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				categoria = new CategoriaImpl();
				categoria.setIdCategoria(rs.getInt("idCategoria"));
				categoria.setNome(rs.getString("nome"));
			}
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				
				connection.close();
			
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return categoria;
		
	}

	public List<Produttore> getProduttoriMancantiByIdCategoria(Integer idCategoria) throws ClassNotFoundException, IOException{
		logger.debug("in getProduttoriMancanti");
		
		List<Produttore> listaProduttori = new ArrayList<>();
		Produttore produttore;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		
			
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM produttore WHERE idproduttore " +
					"NOT IN " +
					"(SELECT produttore_idproduttore FROM categoria_has_produttore " +
					"WHERE categoria_idcategoria = ? )";
			
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
				logger.debug("produttore aggiunto alla lista: " + produttore.toString());
			
			}	
						
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			try {
				rs.close();
				pstmt.close();
				
				connection.close();
			
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaProduttori;
		
	}

	public Integer insertCategoriaHasProduttore(Integer idCategoria, Integer IdProduttore) throws ClassNotFoundException, IOException{
		logger.debug("in insertCategoriaHasProduttore");
		Integer insertRow = -1;
		Connection connection = null; 
		PreparedStatement  pstmt = null;
		
		try {
			
			
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
		
			
			String sql = "INSERT INTO categoria_has_produttore (categoria_idcategoria, produttore_idproduttore) VALUES (?, ?)";
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, idCategoria);
			pstmt.setInt(2, IdProduttore);
			logger.debug("Insert Query: " + pstmt.toString());
			insertRow = pstmt.executeUpdate();
						
				
			pstmt.close();
			connection.commit();
				
			logger.info("Inserimento nuova categoria_has_produttore (" + idCategoria + ", " + IdProduttore + ")");
						
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("Inserimento non riuscito");
		}
		finally{
			try {
				
				pstmt.close();
				
				connection.setAutoCommit(true);
				connection.close();
			
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return insertRow;
	}

	public Integer deleteCategoriaHasProduttore(Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException{
		logger.debug("in deleteCategoriaHasProduttore");
				
		Integer deletedRows = -1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		try {
		
				
				connection = DatabaseUtil.getConnection();
				connection.setAutoCommit(false);
							
				String sql = "DELETE FROM categoria_has_produttore WHERE categoria_idcategoria = ? AND produttore_idproduttore= ?";
				
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, idCategoria);
				pstmt.setInt(2, idProduttore);
				logger.debug("Delete Query:" + pstmt.toString());
				
				deletedRows = pstmt.executeUpdate();
								
				connection.commit();
				
				logger.info("Disassociazione Categoria id: (" + idCategoria + ")");
				
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				logger.debug("Roolback in cancellazione categoria_has_produttore");
			
			}
			
		finally {

			if (connection != null) {

				try {
					pstmt.close();
					connection.setAutoCommit(true);
					
					connection.close();
					logger.debug("Connection chiusa");
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}	
			
	
		
		return deletedRows;		
	}

	public Integer insertCategoria (Categoria categoria) throws ClassNotFoundException, IOException{
		logger.debug("in insertCategoria");
		Integer autoincrementKey = -1;		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
				
			connection = DatabaseUtil.getConnection();
			//connection = DatabaseUtil.getConnection(); utilizzato in caso di caricamento categorie al primo avvio, con il Test
				
			connection.setAutoCommit(false);
			
				
				String sql = "INSERT INTO categoria (nome) VALUES (?)";
				pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, categoria.getNome());
				logger.debug("Insert Query: " + pstmt.toString());
				int insertStatus = pstmt.executeUpdate();
				if (insertStatus==1){
					rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
				        autoincrementKey = rs.getInt(1);
				       }
				}
				categoria.setIdCategoria(autoincrementKey);
					
				connection.commit();
					
				logger.info("Inserimento nuova categoria (" + autoincrementKey + ", " + categoria.getNome() + ")");
					
			    
				
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			}
			logger.debug("Categoria già presente");
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				connection.setAutoCommit(true);
				
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return autoincrementKey;
	
	}

	public Integer updateCategoria(Categoria categoria) throws ClassNotFoundException, IOException{
		logger.debug("in updateCategoria");
		Integer uptadedRows = -1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE categoria SET nome = ? WHERE idcategoria = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, categoria.getNome());
			pstmt.setInt(2, categoria.getIdCategoria());
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
		finally{
			try {
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException  e) {
				e.printStackTrace();
			}			
		}
		logger.info("Aggiornamento Categoria: (" + categoria.getIdCategoria() + ", " + categoria.getNome() + ")");
		
		return uptadedRows;
	}

	public Integer deleteCategoria(Integer idCategoria) throws ClassNotFoundException, IOException{
		logger.debug("in deleteCategoria");
		Integer deletedRows = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
						
			/*Innanzi tutto elimino tutti i prodotti associati alla categoria da eliminare*/
			
			//prima si elimina dalla tabella prodotto_has_keyword
			String sql = "DELETE prodotto_has_keyword.* FROM prodotto_has_keyword, prodotto " +
					"WHERE " +
					"prodotto.idprodotto = prodotto_has_keyword.prodotto_idprodotto " +
					"AND " +
					"prodotto.categoria_idcategoria = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idCategoria);
			logger.debug("Delete Query: " + pstmt.toString());
			pstmt.executeUpdate();
		
			//cancello il prodotto
			sql = "DELETE FROM prodotto WHERE (categoria_idcategoria = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idCategoria);
			logger.debug("Delete Query: " + pstmt.toString());
			pstmt.executeUpdate();
					
			//cancello l'associazione categoria-produttore
			sql = "DELETE FROM categoria_has_produttore WHERE categoria_idcategoria = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idCategoria);
			logger.debug("Delete Query:" + pstmt.toString());
			pstmt.executeUpdate();			
						
			//cancello la categoria
			sql = "DELETE FROM categoria WHERE idcategoria = ? ";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setInt(1, idCategoria);
			logger.debug("Delete Query:" + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			connection.commit();
			logger.info("Eliminazione Categoria id: (" + idCategoria + ")");
			
		} catch (SQLException  e) {
			
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
			
		return deletedRows;		
	}

public boolean checkDeleteCategoria(Integer idCategoria) throws ClassNotFoundException, IOException{
		
		boolean result = true;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM inserzione, categoria, prodotto " +
					"WHERE categoria.idcategoria = prodotto.categoria_idcategoria " +
					"AND prodotto.idprodotto = inserzione.prodotto_idprodotto " +
					"AND idcategoria = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idCategoria);
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
			
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

public boolean checkAssociazioneCategoriaProduttore(Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException{
	boolean result = false;
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	try {
		connection = DatabaseUtil.getConnection();
		
		String sql = "SELECT * FROM categoria_has_produttore " +
				"WHERE categoria_idcategoria = ? " +
				"AND " +
				"produttore_idproduttore = ?";
		
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, idCategoria);
		pstmt.setInt(2, idProduttore);
		logger.debug("Check Query: " + pstmt.toString());
		rs = pstmt.executeQuery();
		
		if(rs.next()){
			result = true;
		}
		rs.close();
		pstmt.close();
		
		connection.close();
		
	} catch (SQLException  e) {
		e.printStackTrace();
	}
	finally{
		try {
			rs.close();
			pstmt.close();
			
			connection.close();
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	return result;
}
	

}
