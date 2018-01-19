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
import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;

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

	/**
	 * si presume che Categoria e Produttore associati siano già presenti nel DB!
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer insertProdotto(Prodotto prodotto) throws ClassNotFoundException, IOException {
		logger.info("in insertProdotto");
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


	
	public Integer insertProdottoHasKeyword(Integer idProdotto, Integer idKeyword) throws ClassNotFoundException, IOException{
		logger.debug("in insertProdottoHasKeyword");
		Integer insertRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
				connection = DatabaseUtil.getConnection();
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

	

public boolean checkDeleteProdotto(Integer idProdotto) throws ClassNotFoundException, IOException{
		
		boolean result = true;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
			
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
	
	public boolean checkProdottoBelongCategoriaProduttore(Integer idProdotto, Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException{
		logger.debug("in checkProdottoBelongCategoriaProduttore");
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = DatabaseUtil.getConnection();
			
			
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
	
	public boolean checkProdottoHasKeyword(Integer idProdotto, Integer idKeyword) throws ClassNotFoundException, IOException{
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
			
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
	
	
}
	
