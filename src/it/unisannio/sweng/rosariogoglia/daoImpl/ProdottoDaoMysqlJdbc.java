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
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.CategoriaImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProdottoImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;

public class ProdottoDaoMysqlJdbc implements ProdottoDao{

	Logger logger = Logger.getLogger(ProdottoDaoMysqlJdbc.class);
	
	public ProdottoDaoMysqlJdbc (){
		DOMConfigurator.configure("C:/Users/Rosario/git/AsteOnLine/WebContent/WEB-INF/log4jConfig.xml");
	}
	
	public List<Prodotto> getProdotti() {
		logger.debug("in getProdotti");
		List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
		
			connection = DatabaseUtil.getConnection();
								
			stmt = connection.createStatement();
			String query = "SELECT * FROM prodotto ORDER BY nome ASC";
			
			logger.debug("Select Query per prodotti: " + query);
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				Prodotto prodotto = new ProdottoImpl();
				
				Categoria categoria = new CategoriaImpl();
				CategoriaDao dao = new CategoriaDaoMysqlJdbc();
				int idCategoria = rs.getInt("categoria_idcategoria");
				categoria = dao.getCategoriaById(idCategoria);
				
				Produttore produttore = new ProduttoreImpl();
				ProduttoreDao dao1 = (ProduttoreDao) new ProduttoreDaoMysqlJdbc();
				int idProduttore = rs.getInt("produttore_idproduttore");
				produttore = dao1.getProduttoreById(idProduttore);
				
								
				prodotto.setIdProdotto(rs.getInt("prodotto.idprodotto"));
				prodotto.setNome(rs.getString("prodotto.nome"));
				prodotto.setIdCategoria(idCategoria);
				prodotto.setCategoria(categoria);
				prodotto.setIdProduttore(idProduttore);
				prodotto.setProduttore(produttore);
				
			
							
				listaProdotti.add(prodotto);
				logger.debug("(" + prodotto.getIdProdotto() + ", " + prodotto.getNome() + ")");
			}
					
		} catch (ClassNotFoundException | SQLException | IOException  e) {
			e.printStackTrace();
		}
		
		finally{
			try {
				rs.close();
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return listaProdotti;
	}
	
	public Prodotto getProdottoById(Integer idProdotto){
		logger.debug("in getProdottoById: " + idProdotto);
		Prodotto prodotto = null;
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			//connection = DatabaseUtil.getConnection();
			
			stmt = connection.createStatement();
			String query = "SELECT * FROM prodotto " +
					"WHERE prodotto.idprodotto = " + idProdotto;
			
			logger.debug("Select Query ProdottoById: " + query);
			rs = stmt.executeQuery(query);
			
			if (rs.next()){
				prodotto = new ProdottoImpl();
							
				Categoria categoria = new CategoriaImpl();
				CategoriaDao dao = new CategoriaDaoMysqlJdbc();
				int idCategoria = rs.getInt("categoria_idcategoria");
				categoria = dao.getCategoriaById(idCategoria);
				
				Produttore produttore = new ProduttoreImpl();
				ProduttoreDao dao1 = (ProduttoreDao) new ProduttoreDaoMysqlJdbc();
				int idProduttore = rs.getInt("produttore_idproduttore");
				produttore = dao1.getProduttoreById(idProduttore);
				
							
				
				prodotto.setIdProdotto(rs.getInt("prodotto.idprodotto"));
				prodotto.setNome(rs.getString("prodotto.nome"));
				prodotto.setIdCategoria(idCategoria);
				prodotto.setCategoria(categoria);
				prodotto.setIdProduttore(idProduttore);
				prodotto.setProduttore(produttore);
				
			
				logger.debug("(" + prodotto.getIdProdotto() + ", " + prodotto.getNome() + ")");
				
			}
						
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return prodotto;
	}

	public List<Prodotto> getProdottiByIdProduttore(Integer idProduttore) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in getProdottiByIdProduttore");
		Connection connection;
		Prodotto prodotto;
		List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
		
		PreparedStatement pstmt;
		
		String sql = "SELECT * FROM prodotto " +
				"WHERE prodotto.produttore_idproduttore = ? ";
				
		connection = DatabaseUtil.getConnection();
		//connection = DatabaseUtil.getConnection();
			
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, idProduttore);
		logger.debug("Select Query:" + pstmt.toString());
		ResultSet rs = pstmt.executeQuery();
			
		while(rs.next()){
				
				ProdottoDao dao = new ProdottoDaoMysqlJdbc();
				prodotto = dao.getProdottoById(rs.getInt("prodotto.idprodotto"));
				
				listaProdotti.add(prodotto);		
				
		}
			
		rs.close();
		pstmt.close();
			
		connection.close();
				
		return listaProdotti;
	}

	public List<Prodotto> getProdottiByIdCategoriaByIdProduttore(Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException{
		logger.debug("in getProdottiByIdCategoriaByIdProduttore");
		Prodotto prodotto;
		List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM prodotto " +
				"WHERE produttore_idproduttore = ? " +
				"AND categoria_idcategoria = ? ";
				
			pstmt = connection.prepareStatement(sql);
		
			pstmt.setInt(1, idProduttore);
			pstmt.setInt(2, idCategoria);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				ProdottoDao dao = new ProdottoDaoMysqlJdbc();
				prodotto = dao.getProdottoById(rs.getInt("prodotto.idprodotto"));
				
				listaProdotti.add(prodotto);		
				
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
				e.printStackTrace();
			}
		}
		
		return listaProdotti;
		
	}
	

	public Prodotto getProdottoByName(String nomeProdotto) throws ClassNotFoundException, IOException{
		logger.debug("in getProdottiByName");
		Prodotto prodotto = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM prodotto WHERE nome = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nomeProdotto);
			logger.debug("Select query: " + pstmt.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				prodotto = new ProdottoImpl();
				
				ProdottoDao dao = new ProdottoDaoMysqlJdbc();
				prodotto = dao.getProdottoById(rs.getInt("prodotto.idprodotto"));
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
				e.printStackTrace();
			}
		}
		return prodotto;
	}

	

	
	


	
	
}
	
