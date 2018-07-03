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




import it.unisannio.sweng.rosariogoglia.dao.ImmagineDao;
import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.model.Immagine;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.modelImpl.ImmagineImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;

public class ImmagineDaoMysqlJdbc implements ImmagineDao{

	Logger logger = Logger.getLogger(ImmagineDaoMysqlJdbc.class);
	
	
	public Immagine getImmagineById(Integer idImmagine) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in getImmagineById");
		Immagine immagine = null;
		Connection connection;
		
		connection = ConnectionPoolTomcat.getConnection();
			
		
		String sql = "SELECT *  FROM immagine " + "WHERE idimmagine = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, idImmagine);
		
		rs = pstmt.executeQuery();
		if(rs.next()){
			immagine = new ImmagineImpl();
			
			Inserzione inserzione = new InserzioneImpl();
			InserzioneDao dao = new InserzioneDaoMysqlJdbc();
			
			int idInserzione = rs.getInt("inserzione_idinserzione");//dal risultato della query prelevo solo il campo che mi serve per caricare l'inserzione
			
			inserzione = dao.getInserzioneByIdSenzaListe(idInserzione);
			
			logger.debug("inserzione caricata");
			
			immagine.setIdImmagine(idImmagine);
			immagine.setIdInserzione(idInserzione);
			immagine.setInserzione(inserzione);
			immagine.setFoto(rs.getString("foto"));
			
			logger.debug("(" + immagine.getIdImmagine() + ", " + immagine.getFoto() + ")");
		}

		rs.close();
		pstmt.close();
		
		connection.close();
	
		return immagine;
	}

	/**
	 * 
	 * @param idInserzione
	 * @return Dato l'id di un'inserzione ne restituisce tutte le immagini associate a quell'inserzione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Immagine> getImmaginiByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException{
		logger.debug("In getImmaginiByIdInserzione");
		List<Immagine> listaImmagini = new ArrayList<Immagine>(); 
		
		Connection connection;
		
		connection = ConnectionPoolTomcat.getConnection();
				
		PreparedStatement pstmt;
		
		String sql = "SELECT * FROM inserzione, immagine " +
				"WHERE (inserzione.idinserzione = immagine.inserzione_idinserzione) " +
				"AND (inserzione.idinserzione = " + idInserzione + ")";
		
		pstmt = connection.prepareStatement(sql);
		logger.debug("Select query: " + pstmt.toString());
		ResultSet rs = pstmt.executeQuery();
		logger.debug("DOPO IL RESULTSET MOMENTANEO!!!");
		
		if(rs.next()){
			logger.debug("Lista immagini presente");
			
			Immagine immagine = null;
			do{
				immagine = new ImmagineImpl();				
				immagine.setIdImmagine(rs.getInt("immagine.idimmagine"));
				immagine.setFoto(rs.getString("immagine.foto"));
				listaImmagini.add(immagine);
				
				logger.debug("immagine aggiunta all'arraylist: " + immagine.toString());
				
			}while(rs.next());
		}
		
		rs.close();
		pstmt.close();
				
		connection.close();
		
		return listaImmagini;
	}
	
	public List<Immagine> getImmaginiByIdInserzioneTest(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException{
		logger.debug("In getImmaginiByIdInserzione");
		List<Immagine> listaImmagini = new ArrayList<Immagine>(); 
		
		Connection connection;
		
		connection = DatabaseUtil.getConnection();
				
		PreparedStatement pstmt;
		
		String sql = "SELECT * FROM inserzione, immagine " +
				"WHERE (inserzione.idinserzione = immagine.inserzione_idinserzione) " +
				"AND (inserzione.idinserzione = " + idInserzione + ")";
		
		pstmt = connection.prepareStatement(sql);
		logger.debug("Select query: " + pstmt.toString());
		ResultSet rs = pstmt.executeQuery();
		logger.debug("DOPO IL RESULTSET MOMENTANEO!!!");
		
		if(rs.next()){
			logger.debug("Lista immagini presente");
			
			Immagine immagine = null;
			do{
				immagine = new ImmagineImpl();				
				immagine.setIdImmagine(rs.getInt("immagine.idimmagine"));
				immagine.setFoto(rs.getString("immagine.foto"));
				listaImmagini.add(immagine);
				
				logger.debug("immagine aggiunta all'arraylist: " + immagine.toString());
				
			}while(rs.next());
		}
		
		rs.close();
		pstmt.close();
				
		connection.close();
		
		return listaImmagini;
	}
	
	
	/**
	 * Metodo di inserimento immagine
	 */
	
	public Integer insertImmagine(Immagine immagine) {
		logger.debug("in insert immagine");
		Integer immagineIdKey = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
						
			String sql = "INSERT INTO immagine (inserzione_idinserzione, foto) " +
						"VALUES (?, ?)"; 
			
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, immagine.getIdInserzione());
			logger.debug("inserzioneId: " + immagine.getIdInserzione());
			pstmt.setString(2, immagine.getFoto());
			logger.debug("link foto: " + immagine.getFoto());
			
			System.out.println("link foto: " + immagine.getFoto());
			
			logger.debug("Insert Query: " + pstmt.toString());
			
			System.out.println("Insert Query: " + pstmt.toString());
			int insertRows = pstmt.executeUpdate();
			
			logger.debug("righe inserite: "+ insertRows);
			
			if(insertRows == 1){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					immagineIdKey = rs.getInt(1);
				}
			}
			
			immagine.setIdImmagine(immagineIdKey);
						
			connection.commit();
			System.out.println("id immagine inserita: " + immagineIdKey);
			
			logger.debug("id immagine inserita: " + immagineIdKey);

			} catch (SQLException e) {
//				try {
//					connection.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
				logger.debug("Errore inserimento immagine");
				System.out.println("Errore inserimento immagine");
				
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
		
		return immagineIdKey;
	
	}
	
	public Integer insertImmagineTest(Immagine immagine) {
		logger.debug("in insert immagine");
		Integer immagineIdKey = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
						
			String sql = "INSERT INTO immagine (inserzione_idinserzione, foto) " +
						"VALUES (?, ?)"; 
			
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, immagine.getIdInserzione());
			logger.debug("inserzioneId: " + immagine.getIdInserzione());
			pstmt.setString(2, immagine.getFoto());
			logger.debug("link foto: " + immagine.getFoto());
			
			System.out.println("link foto: " + immagine.getFoto());
			
			logger.debug("Insert Query: " + pstmt.toString());
			
			System.out.println("Insert Query: " + pstmt.toString());
			int insertRows = pstmt.executeUpdate();
			
			logger.debug("righe inserite: "+ insertRows);
			
			if(insertRows == 1){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					immagineIdKey = rs.getInt(1);
				}
			}
			
			immagine.setIdImmagine(immagineIdKey);
						
			connection.commit();
			System.out.println("id immagine inserita: " + immagineIdKey);
			
			logger.debug("id immagine inserita: " + immagineIdKey);

			} catch (SQLException e) {
//				try {
//					connection.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
				logger.debug("Errore inserimento immagine");
				System.out.println("Errore inserimento immagine");
				
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
		
		return immagineIdKey;
	
	}
	
	
	
	
	public Integer deleteImmagine(Immagine immagine){
		logger.debug("in deleteImmagine");		
		int deletedRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null; 
		try {
			connection = ConnectionPoolTomcat.getConnection();
		    connection.setAutoCommit(false);
			
			String sql = "DELETE * FROM immagine WHERE idimmagine = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, immagine.getIdImmagine());
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRow = pstmt.executeUpdate();
			
			connection.commit();
		
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("Errore delete immagine");
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
		
		logger.debug("immagine cancellata");
		return deletedRow;
	}
	
	public Integer deleteImmagineByIdInserzione(Integer idInserzione){
		logger.debug("in deleteImmagineByIdInserzione");		
		int deletedRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE * FROM immagine WHERE inserzione_idinserzione = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idInserzione);
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRow = pstmt.executeUpdate();
						
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
				logger.debug("rollback in deleteImmagineByIdInserzione");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
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
			
		logger.debug("immagine cancellata");
		
		return deletedRow;
	}

	
	
	

}


