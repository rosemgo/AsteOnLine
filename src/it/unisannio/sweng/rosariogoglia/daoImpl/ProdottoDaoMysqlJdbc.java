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
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.CategoriaImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProdottoImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;
import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.modelImpl.KeywordImpl;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;


public class ProdottoDaoMysqlJdbc implements ProdottoDao{

	Logger logger = Logger.getLogger(ProdottoDaoMysqlJdbc.class);
	
	
	public List<Prodotto> getProdotti() {
		logger.debug("in getProdotti");
		List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
		
			connection = ConnectionPoolTomcat.getConnection();
								
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
				ProduttoreDao dao1 = new ProduttoreDaoMysqlJdbc();
				int idProduttore = rs.getInt("produttore_idproduttore");
				produttore = dao1.getProduttoreById(idProduttore);
				
				List<Keyword> keywordList = new ArrayList<Keyword>();
				KeywordDao dao2 = new KeywordDaoMysqlJdbc();
				int idProdotto = rs.getInt("idprodotto");
				keywordList = dao2.getKeywordByIdProdotto(idProdotto);
				
				
				prodotto.setIdProdotto(rs.getInt("prodotto.idprodotto"));
				prodotto.setNome(rs.getString("prodotto.nome"));
				prodotto.setIdCategoria(idCategoria);
				prodotto.setCategoria(categoria);
				prodotto.setIdProduttore(idProduttore);
				prodotto.setProduttore(produttore);
				
				prodotto.setKeywordsList(keywordList); 
							
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
			
			connection = ConnectionPoolTomcat.getConnection();
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
				ProduttoreDao dao1 = new ProduttoreDaoMysqlJdbc();
				int idProduttore = rs.getInt("produttore_idproduttore");
				produttore = dao1.getProduttoreById(idProduttore);
				
				List<Keyword> keywordList = new ArrayList<Keyword>();
				KeywordDao dao2 = new KeywordDaoMysqlJdbc();
				keywordList = dao2.getKeywordByIdProdotto(idProdotto);
				
				
				prodotto.setIdProdotto(rs.getInt("prodotto.idprodotto"));
				prodotto.setNome(rs.getString("prodotto.nome"));
				prodotto.setIdCategoria(idCategoria);
				prodotto.setCategoria(categoria);
				prodotto.setIdProduttore(idProduttore);
				prodotto.setProduttore(produttore);
				
				prodotto.setKeywordsList(keywordList); 
				
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

	
	public Prodotto getProdottoByIdTest(Integer idProdotto){
		logger.debug("in getProdottoById: " + idProdotto);
		Prodotto prodotto = null;
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			
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
				categoria = dao.getCategoriaByIdTest(idCategoria);
				
				Produttore produttore = new ProduttoreImpl();
				ProduttoreDao dao1 = new ProduttoreDaoMysqlJdbc();
				int idProduttore = rs.getInt("produttore_idproduttore");
				produttore = dao1.getProduttoreByIdTest(idProduttore);
				
				List<Keyword> keywordList = new ArrayList<Keyword>();
				KeywordDao dao2 = new KeywordDaoMysqlJdbc();
				keywordList = dao2.getKeywordByIdProdottoTest(idProdotto);
				
				
				prodotto.setIdProdotto(rs.getInt("prodotto.idprodotto"));
				prodotto.setNome(rs.getString("prodotto.nome"));
				prodotto.setIdCategoria(idCategoria);
				prodotto.setCategoria(categoria);
				prodotto.setIdProduttore(idProduttore);
				prodotto.setProduttore(produttore);
				
				prodotto.setKeywordsList(keywordList); 
				
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
				
		connection = ConnectionPoolTomcat.getConnection();
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

	public List<Prodotto> getProdottiByIdProduttoreTest(Integer idProduttore) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in getProdottiByIdProduttore");
		Connection connection;
		Prodotto prodotto;
		List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
		
		PreparedStatement pstmt;
		
		String sql = "SELECT * FROM prodotto " +
				"WHERE prodotto.produttore_idproduttore = ? ";
				
		
		connection = DatabaseUtil.getConnection();
			
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, idProduttore);
		logger.debug("Select Query:" + pstmt.toString());
		ResultSet rs = pstmt.executeQuery();
			
		while(rs.next()){
				
				ProdottoDao dao = new ProdottoDaoMysqlJdbc();
				prodotto = dao.getProdottoByIdTest(rs.getInt("prodotto.idprodotto"));
				
				listaProdotti.add(prodotto);		
				
		}
			
		rs.close();
		pstmt.close();
			
		connection.close();
				
		return listaProdotti;
	}

	
	public List<Prodotto> getProdottiByIdCategoriaByIdProduttore(Integer idCategoria, Integer idProduttore){
		logger.debug("in getProdottiByIdCategoriaByIdProduttore");
		Prodotto prodotto;
		List<Prodotto> listaProdotti = new ArrayList<Prodotto>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
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
	

	public Prodotto getProdottoByName(String nomeProdotto){
		logger.debug("in getProdottiByName");
		Prodotto prodotto = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			connection = ConnectionPoolTomcat.getConnection();
			
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

	
	
	public List<Keyword> getKeywordMancantiByIdProdotto(Integer idProdotto){
		logger.debug("in getKeywordMancantiByIdProdotto");
		List<Keyword> keywordList = new ArrayList<Keyword>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			String sql = "SELECT * FROM keyword WHERE keyword.idkeyword " +
					"NOT IN " +
					"(SELECT keyword.idkeyword FROM prodotto, keyword, prodotto_has_keyword " +
					"WHERE prodotto.idprodotto = prodotto_has_keyword.prodotto_idprodotto " +
					"AND prodotto_has_keyword.keyword_idkeyword = keyword.idkeyword " +
					"AND prodotto.idprodotto = ?) ";
			
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
		
		return keywordList;
	}
	
	
	public boolean checkProdottoBelongCategoriaProduttore(Integer idProdotto, Integer idCategoria, Integer idProduttore){
		logger.debug("in checkProdottoBelongCategoriaProduttore");
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = ConnectionPoolTomcat.getConnection();
			//connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM prodotto " +
					"WHERE idprodotto = ? ";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, idProdotto);
			logger.debug("Select Query:" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				int idCategoriaProdotto = rs.getInt("categoria_idcategoria");
				int idProduttoreProdotto = rs.getInt("produttore_idproduttore");
				
				if((idCategoria == idCategoriaProdotto) && (idProduttore == idProduttoreProdotto)){
					result = true;
				}
										
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
		return result;
		
	}

	
	/**
	 * si presume che Categoria e Produttore associati siano già presenti nel DB!
	 */
	public Integer insertProdotto(Prodotto prodotto) {
		logger.info("in insertProdotto");
		Integer productIdKey = -1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
						
			try{
				
				if((prodotto.getKeywordsList() != null) && (prodotto.getKeywordsList().size() > 0)){
				
					Integer keywordIdKey = -1;
					KeywordDao keyDao = new KeywordDaoMysqlJdbc();
				
					for(int i=0; i<prodotto.getKeywordsList().size(); i++){
			
						Keyword keyword = keyDao.getKeywordByWord(prodotto.getKeywordsList().get(i).getKeyword());
						logger.debug("Dopo il controllo");
						
						if(keyword != null){
							logger.debug("La parola è presente nel database");
							keywordIdKey = keyword.getIdKeyword();
						}
						else{
							logger.debug("La parola NON è presente nel database");
							keywordIdKey = keyDao.insertKeyword(prodotto.getKeywordsList().get(i));					
						}
						prodotto.getKeywordsList().get(i).setIdKeyword(keywordIdKey);
					}
					
				}
						
				
			}
			catch (Exception e) {
				logger.debug("Rollback in inserimento keyword");
				connection.rollback();
				return productIdKey;
			}
			
			try {
					
					
					String sql = "INSERT INTO prodotto (nome, produttore_idproduttore, categoria_idcategoria) " +
							"VALUES (?, ?, ?)";
			
					logger.debug("Inseriamo il prodotto");
					pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, prodotto.getNome());
					logger.debug("Nome prodotto: " + prodotto.getNome());
					pstmt.setInt(2, prodotto.getIdProduttore());
					logger.debug("id produttore: " + prodotto.getIdProduttore());
					pstmt.setInt(3, prodotto.getIdCategoria());
					logger.debug("id categoria: " + prodotto.getIdCategoria());
					logger.debug("Insert Query: " + pstmt.toString());
					int insertRows = pstmt.executeUpdate();
					logger.debug("righe inserite: "+ insertRows);
					if (insertRows == 1) {
						rs = pstmt.getGeneratedKeys();
						if (rs.next()) {
							productIdKey = rs.getInt(1);
						}
					}
					prodotto.setIdProdotto(productIdKey); //setto l'id del prodotto
					logger.debug("id del prodotto è: " + productIdKey);
					
					
						
					if (prodotto.getKeywordsList() != null && prodotto.getKeywordsList().size() > 0) {
						sql = "INSERT INTO prodotto_has_keyword (prodotto_idprodotto, keyword_idkeyword) "
									+ "VALUES (?, ?)";
						for (int i = 0; i < prodotto.getKeywordsList().size(); i++) {
							pstmt = connection.prepareStatement(sql);
							pstmt.setInt(1, productIdKey);
							pstmt.setInt(2, prodotto.getKeywordsList().get(i).getIdKeyword()); 
							logger.debug("Insert Query: " + pstmt.toString());
							pstmt.executeUpdate();
						}
					}
					
					connection.commit();
										
					logger.debug("Inserimento Prodotto (" + productIdKey + ", "
							+ prodotto.getNome() + ")");
					
			} catch (Exception e) {
				logger.debug("Rollback in inserimento prodotto");
				logger.debug("Prodotto già presente");
				connection.rollback();
				return productIdKey;
				//ServiceException per la stampa dell'errore
			}	
		
		} catch (SQLException  e1) {
			e1.printStackTrace();
		}		
		finally {
			if (connection!=null) {
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
				logger.debug("Connection chiusa");
			}
		}				
		return productIdKey;
	}


	public Integer insertProdottoTest(Prodotto prodotto) {
		logger.info("in insertProdottoTest");
		Integer productIdKey = -1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
						
			try{
				
				if((prodotto.getKeywordsList() != null) && (prodotto.getKeywordsList().size() > 0)){
				
					Integer keywordIdKey = -1;
					KeywordDao keyDao = new KeywordDaoMysqlJdbc();
				
					for(int i=0; i<prodotto.getKeywordsList().size(); i++){
			
						Keyword keyword = keyDao.getKeywordByWordTest(prodotto.getKeywordsList().get(i).getKeyword());
						logger.debug("Dopo il controllo");
						
						if(keyword != null){
							logger.debug("La parola è presente nel database");
							keywordIdKey = keyword.getIdKeyword();
						}
						else{
							logger.debug("La parola NON è presente nel database");
							keywordIdKey = keyDao.insertKeywordTest(prodotto.getKeywordsList().get(i));					
						}
						prodotto.getKeywordsList().get(i).setIdKeyword(keywordIdKey);
					}
					
				}
						
				
			}
			catch (Exception e) {
				logger.debug("Rollback in inserimento keyword");
				connection.rollback();
				return productIdKey;
			}
			
			try {
					
					
					String sql = "INSERT INTO prodotto (nome, produttore_idproduttore, categoria_idcategoria) " +
							"VALUES (?, ?, ?)";
			
					logger.debug("Inseriamo il prodotto");
					pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, prodotto.getNome());
					logger.debug("Nome prodotto: " + prodotto.getNome());
					pstmt.setInt(2, prodotto.getIdProduttore());
					logger.debug("id produttore: " + prodotto.getIdProduttore());
					pstmt.setInt(3, prodotto.getIdCategoria());
					logger.debug("id categoria: " + prodotto.getIdCategoria());
					logger.debug("Insert Query: " + pstmt.toString());
					int insertRows = pstmt.executeUpdate();
					logger.debug("righe inserite: "+ insertRows);
					if (insertRows == 1) {
						rs = pstmt.getGeneratedKeys();
						if (rs.next()) {
							productIdKey = rs.getInt(1);
						}
					}
					prodotto.setIdProdotto(productIdKey); //setto l'id del prodotto
					logger.debug("id del prodotto è: " + productIdKey);
					System.out.println("id del prodotto è: " + productIdKey);
					
						
					if (prodotto.getKeywordsList() != null && prodotto.getKeywordsList().size() > 0) {
						sql = "INSERT INTO prodotto_has_keyword (prodotto_idprodotto, keyword_idkeyword) "
									+ "VALUES (?, ?)";
						for (int i = 0; i < prodotto.getKeywordsList().size(); i++) {
							pstmt = connection.prepareStatement(sql);
							pstmt.setInt(1, productIdKey);
							pstmt.setInt(2, prodotto.getKeywordsList().get(i).getIdKeyword()); 
							logger.debug("Insert Query: " + pstmt.toString());
							pstmt.executeUpdate();
						}
					}
					
					connection.commit();
					
					System.out.println("Inserimento Prodotto (" + productIdKey + ", "
							+ prodotto.getNome() + ")");
					
					logger.debug("Inserimento Prodotto (" + productIdKey + ", "
							+ prodotto.getNome() + ")");
					
			} catch (Exception e) {
				logger.debug("Rollback in inserimento prodotto");
				logger.debug("Prodotto già presente");
				connection.rollback();
				return productIdKey;
				//ServiceException per la stampa dell'errore
			}	
		
		} catch (SQLException  e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		finally {
			if (connection!=null) {
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
				logger.debug("Connection chiusa");
			}
		}		
		System.out.println("RETURN: " + productIdKey);
		
		return productIdKey;
	}
	
	
	
	
	
	public Integer insertProdottoHasKeyword(Integer idProdotto, Integer idKeyword){
		logger.debug("in insertProdottoHasKeyword");
		Integer insertRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
				connection = ConnectionPoolTomcat.getConnection();
				connection.setAutoCommit(false);
							
				String sql = "INSERT INTO prodotto_has_keyword(prodotto_idprodotto, keyword_idkeyword) VALUES (?, ?)";
				pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, idProdotto);
				pstmt.setInt(2, idKeyword);
				logger.debug("Insert Query: " + pstmt.toString());
				try{
					insertRow = pstmt.executeUpdate();
				}catch (Exception e) {
					logger.debug("Associazione prodotto-keyword non riuscito!!!");
				}	
				if(insertRow != -1){
					logger.info("Inserimento nuovo prodotto_has_keyword (" + idProdotto + ", " + idKeyword+ ")");
					connection.commit();
				}
							
			
		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Rollback in inserimento prodotto_has_keyword");
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
			
		return insertRow;
	}
	
	public boolean checkProdottoHasKeyword(Integer idProdotto, Integer idKeyword){
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			String sql = "SELECT * FROM prodotto_has_keyword " +
					"WHERE prodotto_idprodotto = ? " +
					"AND keyword_idkeyword = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProdotto);
			pstmt.setInt(2, idKeyword);
			logger.debug("Check Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = true;
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
		return result;
	}
	
	public Integer deleteProdottoHasKeyword(Integer idProdotto, Integer idKeyword){
		logger.debug("in deleteProdottoHasKeyword");
		Integer deleteRow = -1;
		Connection connection = null; 
		PreparedStatement pstmt = null;
		try {
			
				connection = ConnectionPoolTomcat.getConnection();
				connection.setAutoCommit(false);
							
				String sql = "DELETE FROM prodotto_has_keyword WHERE prodotto_idprodotto = ? AND keyword_idkeyword = ?";
				pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, idProdotto);
				pstmt.setInt(2, idKeyword);
				logger.debug("Delete Query: " + pstmt.toString());
				try{
					deleteRow = pstmt.executeUpdate();
				}catch (Exception e) {
					logger.debug("Disassociazione prodotto-keyword non riuscito!!!");
				}	
				if(deleteRow == 1){
					logger.info("Cancellazione prodotto_has_keyword (" + idProdotto + ", " + idKeyword+ ")");
					connection.commit();
				}
				
							
		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Rollback in inserimento prodotto_has_keyword");
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
	
		return deleteRow;
	}
	
	
	
	public boolean checkDeleteProdotto(Integer idProdotto){
		
		boolean result = true;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
			String sql = "SELECT * FROM inserzione, prodotto " +
					"WHERE prodotto.idprodotto = inserzione.prodotto_idprodotto " +
					"AND idprodotto = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idProdotto);
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
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public Integer deleteProdotto(Prodotto prodotto){
		logger.info("Eliminazione Prodotto: (" + prodotto.getIdProdotto()+ ")");
		Integer deletedRows =-1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
						
			// prima si elimina dalla tabella prodotto_has_keyword
			String sql = "DELETE FROM prodotto_has_keyword WHERE (prodotto_idprodotto = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodotto.getIdProdotto());
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
		
			
			sql = "DELETE FROM prodotto WHERE (idprodotto = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodotto.getIdProdotto());
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			connection.commit();
			logger.info("Prodotto Eliminato");
			
	
		} catch (SQLException  e1) {
			// Service Exception con messaggio di errore in pagina
			e1.printStackTrace();
			logger.debug("Rollback in cancellazione prodotto");
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
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
	
	
	public Integer deleteProdottoTest(Prodotto prodotto){
		logger.info("Eliminazione Prodotto Test: (" + prodotto.getIdProdotto()+ ")");
		System.out.println("Eliminazione Prodotto Test: (" + prodotto.getIdProdotto()+ ")");
		Integer deletedRows =-1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
						
			// prima si elimina dalla tabella prodotto_has_keyword
			String sql = "DELETE FROM prodotto_has_keyword WHERE (prodotto_idprodotto = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodotto.getIdProdotto());
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
		
			
			sql = "DELETE FROM prodotto WHERE (idprodotto = ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, prodotto.getIdProdotto());
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRows = pstmt.executeUpdate();
			
			connection.commit();
			logger.info("Prodotto Eliminato");
			
	
		} catch (SQLException  e1) {
			// Service Exception con messaggio di errore in pagina
			e1.printStackTrace();
			logger.debug("Rollback in cancellazione prodotto");
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
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
		System.out.println("Prodotto Eliminato");
		return deletedRows;
		
		
	}
	
	
	
	
	/**
	 * 
	 * //Le keyword già assegnate ad un prodotto non possono essere modificate ma si possono solo cancellare o aggiungerne altre
	 */
	public Integer updateProdotto(Prodotto prodotto){
		logger.debug("in updateProdotto");
		logger.info("Aggiornamento Prodotto: (" + prodotto.getIdProdotto() + ", " + prodotto.getNome() + ", " + prodotto.getIdProduttore() + ", " + prodotto.getIdCategoria() + ")");
		Integer uptadedRows = -1;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
			
			
		
			
				String sql1 = "INSERT INTO prodotto_has_keyword (prodotto_idprodotto, keyword_idkeyword) "
						+ "VALUES (?, ?)";
				
				if((prodotto.getKeywordsList() != null) && (prodotto.getKeywordsList().size() > 0)){
					
					Integer keywordIdKey = -1;
					KeywordDao keyDao = new KeywordDaoMysqlJdbc();
					
					for (int i = 0; i < prodotto.getKeywordsList().size(); i++) {
		
						Keyword keyword = keyDao.getKeywordByWord(prodotto.getKeywordsList().get(i).getKeyword());
		
						if (keyword != null) {
							logger.debug("La parola è presente nel database");
							keywordIdKey = keyword.getIdKeyword();
						} 
						else{
							logger.debug("La parola NON è presente nel database");
							keywordIdKey = keyDao.insertKeyword(prodotto.getKeywordsList().get(i));
						}
						if(prodotto.getKeywordsList().get(i).getIdKeyword() == null) {
							prodotto.getKeywordsList().get(i).setIdKeyword(keywordIdKey); // setto l'id della parola chiave
							logger.debug("Ho settato l'idkeyword: " + keywordIdKey   + " nella lista delle keyword del prodotto");
						}
	
						//aggiorniamo l'associazione idprodotto e idkeyword
						logger.debug("aggiorniamo l'associazione idprodotto e idkeyword");
						pstmt = connection.prepareStatement(sql1);
						pstmt.setInt(1, prodotto.getIdProdotto());
						pstmt.setInt(2, keywordIdKey);
						logger.debug("Insert Query: " + pstmt.toString());
						int insertRows = pstmt.executeUpdate(); // viene effettuato l'associazione tra l'idProdotto e l'idKeyword nella tabella prodotto_has_keyword
						logger.debug("Numero di righe inserite: " + insertRows);
					}
				}
	
				String sql2 = "UPDATE  prodotto SET nome=?, produttore_idproduttore=?, categoria_idcategoria=? WHERE idprodotto=?";
	
				pstmt = connection.prepareStatement(sql2);
				pstmt.setString(1, prodotto.getNome());
				pstmt.setInt(2, prodotto.getIdProduttore());
				pstmt.setInt(3, prodotto.getIdCategoria());
				pstmt.setInt(4, prodotto.getIdProdotto());
				logger.debug("Update Query:" + pstmt.toString());
				uptadedRows = pstmt.executeUpdate();
	
				
				connection.commit();
				logger.info("Prodotto Aggiornato");

		}catch (ClassNotFoundException | SQLException | IOException  e1) {
			
			e1.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			logger.debug("Roolback in aggiornamento prodotto");
		}
		finally {
			if (connection!=null) {
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
		return uptadedRows;
	}
	


	public Integer updateNomeProdotto(Prodotto prodotto) {
		logger.debug("in updateProdotto");
		Integer uptadedRows = -1;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
			
			String sql2 = "UPDATE prodotto SET nome=? WHERE idprodotto=?";
	
			pstmt = connection.prepareStatement(sql2);
			pstmt.setString(1, prodotto.getNome());
			pstmt.setInt(2, prodotto.getIdProdotto());
			logger.debug("Update Query:" + pstmt.toString());
			uptadedRows = pstmt.executeUpdate();
	
			
			connection.commit();
			logger.info("Prodotto Aggiornato");
	
		}catch (SQLException  e1) {
			e1.printStackTrace();
	
			try {
				connection.rollback();
				logger.debug("Roolback in aggiornamento prodotto"); 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		finally {
			if (connection!=null) {
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
		return uptadedRows;
	}
	
	
	
	
	
	
}
	
