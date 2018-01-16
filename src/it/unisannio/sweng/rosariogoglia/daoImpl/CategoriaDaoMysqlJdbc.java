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


public class CategoriaDaoMysqlJdbc implements CategoriaDao{

	Logger logger = Logger.getLogger(CategoriaDaoMysqlJdbc.class);
	
	public CategoriaDaoMysqlJdbc (){
		DOMConfigurator.configure("C:/Users/Rosario/git/WebContent/WEB-INF/log4jConfig.xml");
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
	

}
