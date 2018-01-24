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





import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.OffertaDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.OffertaImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

public class OffertaDaoMysqlJdbc implements OffertaDao{

	
	Logger logger = Logger.getLogger(OffertaDaoMysqlJdbc.class);
	
	public OffertaDaoMysqlJdbc (){
		DOMConfigurator.configure("C:/Users/Rosario/git/AsteOnLine2/WebContent/WEB-INF/log4jConfig.xml");
	}
	
	public Integer insertOfferta(Offerta offerta){
		logger.debug("in insertOfferta");			
		int offertaIdKey = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
					
			String sql = "INSERT INTO offerta(somma, data, utente_registrato_idutente, inserzione_idinserzione) VALUES (?, ?, ?, ?) ";
			
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, offerta.getSomma());
			pstmt.setTimestamp(2, Utility.convertitoreDataUtilToTimestamp(offerta.getData()));
			pstmt.setInt(3, offerta.getIdUtenteRegistrato());
			pstmt.setInt(4, offerta.getIdInserzione());
			
			logger.debug("Insert Query: " + pstmt.toString());
			int insertRow = pstmt.executeUpdate();
			
			if(insertRow == 1){
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					offertaIdKey = rs.getInt(1);
				}
				offerta.setIdOfferta(offertaIdKey);
				rs.close();
			}
			
			connection.commit();
			
			logger.debug("inserimento completato, idofferta: " + offertaIdKey);	
			
		} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
					logger.debug("Roolback in cancellazione inserzione");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
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
		return offertaIdKey;
	}

	
	public Integer deleteOfferta(Offerta offerta) throws SQLException, ClassNotFoundException, IOException {
		logger.debug("in deleteOfferta");		
		int deletedRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM offerta WHERE idofferta = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, offerta.getIdOfferta());
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRow = pstmt.executeUpdate();
						
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Roolback in cancellazione inserzione");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}				
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
		logger.debug("offerta cancellata");
		return deletedRow;
	}
	
	
	public Integer deleteOffertaByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in deleteOffertaByIdInserzione");		
		int deletedRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM offerta WHERE inserzione_idinserzione = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idInserzione);
			logger.debug("Delete Query: " + pstmt.toString());
			deletedRow = pstmt.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				logger.debug("Roolback in deleteOffertaByIdInserzione");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}				
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
		logger.debug("offerta cancellata");
		return deletedRow;
		
	}

	
	public Offerta getOffertaByIdOfferta(Integer idOfferta) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in getOffertaById");		
		Offerta offerta = null;
		Connection connection;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = ConnectionPoolTomcat.getConnection();
			
		String sql = "SELECT * FROM offerta " +
				"WHERE idofferta = ? ";
		
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, idOfferta);
		logger.debug("Select Query: " + pstmt.toString());
		rs = pstmt.executeQuery();
		
		if(rs.next()){
			
			offerta = new OffertaImpl();
			
			UtenteRegistratoDao dao = new UtenteRegistratoDaoMysqlJdbc();
			int idUtenteRegistrato = rs.getInt("utente_registrato_idutente");
			UtenteRegistrato utente = dao.getUtenteRegistratoById(idUtenteRegistrato);
					
			InserzioneDao dao1 = new InserzioneDaoMysqlJdbc();
			int idInserzione = rs.getInt("inserzione_idinserzione");
			Inserzione inserzione = dao1.getInserzioneByIdSenzaListe(idInserzione);
			
			offerta.setIdOfferta(rs.getInt("idofferta"));
			offerta.setSomma(rs.getDouble("somma"));
			offerta.setData(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("data")));
			offerta.setIdUtenteRegistrato(rs.getInt("utente_registrato_idutente"));
			offerta.setUtente(utente);
			offerta.setIdInserzione(rs.getInt("inserzione_idinserzione"));
			offerta.setInserzione(inserzione);
			
			logger.debug("offerta caricata");	
			
		}
		rs.close();
		pstmt.close();
		
		connection.close();
		
		logger.debug("offerta restituita: " + offerta.toString());		
		return offerta;
	}

	

	public List<Offerta> getOfferteByIdInserzione(Integer idInserzione) {
		logger.debug("in getOffertaById");		
		List<Offerta> listaOfferte = new ArrayList<Offerta>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			    connection = ConnectionPoolTomcat.getConnection();
				
				
				String sql = "SELECT * FROM offerta " +
						"WHERE offerta.inserzione_idinserzione = ? " +
						"ORDER BY offerta.data DESC ";
		
			
				pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, idInserzione);
				logger.debug("Select Query: " + pstmt.toString());
				rs = pstmt.executeQuery();
				
				Offerta offerta = null;
				while(rs.next()){
					
					offerta = new OffertaImpl();
					
					UtenteRegistratoDao dao = new UtenteRegistratoDaoMysqlJdbc();
					int idUtenteRegistrato = rs.getInt("utente_registrato_idutente");
					UtenteRegistrato utente = dao.getUtenteRegistratoById(idUtenteRegistrato);
			
			/*		Non mi serve caricare anche l'inserzione, perchè è scontato che l'inserzione sia quella relativa all'id passato come paramentro.
			 * 		In quanto questo metodo viene utilizzato per vedere tutte le offerte relative ad una singola inserzione				
			 * 
					InserzioneDao dao1 = new InserzioneDaoMysqlJdbc();
					Inserzione inserzione = dao1.getInserzioneByIdSenzaListe(idInserzione); //carico solo l'inserzione, in modo tale da nn caricarmi la lista delle offerte e creare una ricorsione
			*/						
					offerta.setIdOfferta(rs.getInt("idofferta"));
					offerta.setSomma(rs.getDouble("somma"));
					offerta.setData(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("data")));
					offerta.setIdUtenteRegistrato(rs.getInt("utente_registrato_idutente"));
					offerta.setUtente(utente);
					offerta.setIdInserzione(rs.getInt("inserzione_idinserzione"));
			//		offerta.setInserzione(inserzione);
					
					logger.debug("offerta con id: " + offerta.getIdOfferta() + "caricata");
					listaOfferte.add(offerta);
					logger.debug("offerta aggiunta alla lista");
				}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
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
				
		return listaOfferte;
	}

	
	
}
