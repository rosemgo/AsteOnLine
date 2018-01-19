package it.unisannio.sweng.rosariogoglia.daoImpl;


import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;
import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;


import it.unisannio.sweng.rosariogoglia.dao.ImmagineDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.ImmagineDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Immagine;
import it.unisannio.sweng.rosariogoglia.dao.OffertaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.OffertaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Offerta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class InserzioneDaoMysqlJdbc implements InserzioneDao {

	Logger logger = Logger.getLogger(InserzioneDaoMysqlJdbc.class);
	
	public InserzioneDaoMysqlJdbc (){
		DOMConfigurator.configure("C:/Users/Rosario/git/AsteOnLine/WebContent/WEB-INF/log4jConfig.xml");
	}

	
	public List<Inserzione> getInserzioni() throws ClassNotFoundException, IOException {
		logger.debug("in getInserzioni");
		
		List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
		Inserzione inserzione = null;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM inserzione ";
						
			pstmt = connection.prepareStatement(sql);
		
			logger.debug("select inserzione" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				inserzione = new InserzioneImpl();
				
				
				UtenteRegistratoDao dao = new UtenteRegistratoDaoMysqlJdbc();
				Integer idAcquirente = rs.getInt("inserzione.acquirente_utente_registrato_idutente"); 
				UtenteRegistrato acquirente = null;
				if(idAcquirente != 0){ //lo 0 equivale al null
					 acquirente = dao.getUtenteRegistratoById(idAcquirente); 
				}
				logger.debug("idAcquirente: " + idAcquirente);
				
				int idVenditore = rs.getInt("inserzione.venditore_utente_registrato_idutente");
				UtenteRegistrato venditore = dao.getUtenteRegistratoById(idVenditore);
				
				
				inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setTitolo(rs.getString("inserzione.titolo"));
				inserzione.setDescrizione(rs.getString("inserzione.descrizione"));
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				inserzione.setStato(rs.getString("inserzione.stato"));
				
				inserzione.setIdAcquirente(idAcquirente);
				inserzione.setAcquirente(acquirente);
								
				inserzione.setIdVenditore(idVenditore);
				inserzione.setVenditore(venditore);
				
				inserzione.setIdProdotto(rs.getInt("inserzione.prodotto_idprodotto"));
				
				listaInserzioni.add(inserzione);
				
				logger.debug("inserzione caricata: " + inserzione.getIdInserzione() + " " + inserzione.getTitolo());
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {

			if (connection != null) {

				try {
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
		
		return listaInserzioni;
	}
	

	
	
	public Inserzione getInserzioneById(Integer idInserzione){
		logger.debug("in getInserzioneById");
		Inserzione inserzione = null;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
		
			
			String sql = "SELECT * FROM inserzione " +
						"WHERE (inserzione.idinserzione = ?)";
		
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idInserzione);
			logger.debug("select inserzione" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				inserzione = new InserzioneImpl();
				
				UtenteRegistratoDao dao = new UtenteRegistratoDaoMysqlJdbc();
				Integer idAcquirente = null; 
				UtenteRegistrato acquirente = null;
				idAcquirente = rs.getInt("inserzione.acquirente_utente_registrato_idutente"); //la funzione rs.getInt() restituisce 0 se il campo acquirente nella tabella inserzione è NULL
				logger.debug("idAcquirente: " + idAcquirente);
				if(idAcquirente != 0){ //se l'inserzione ha un acquirente andiamo a caricarlo
					logger.debug("Entro per l'acquirente");
					acquirente = dao.getUtenteRegistratoById(idAcquirente);
					System.out.println("ACQUIRENTE CARICATO");
				}
			
			/*	soluzione alternativa	
				if (rs.getObject("inserzione.acquirente_utente_registrato_idutente") != null && !rs.wasNull()) {
					idAcquirente = rs.getInt("inserzione.acquirente_utente_registrato_idutente"); //DA RIVEDERE SE ACQUIRENTE è NULL!!!!!!!!!!!!!
					acquirente = dao.getUtenteRegistratoById(idAcquirente); //SI INCAZZA CON IDACQUIRENTE NULL
				}
			*/
				
				
				int idVenditore = rs.getInt("inserzione.venditore_utente_registrato_idutente");
				UtenteRegistrato venditore = dao.getUtenteRegistratoById(idVenditore);
				System.out.println("VENDITORE CARICATO");
				
				ProdottoDao dao1 = new ProdottoDaoMysqlJdbc();
				int idProdotto = rs.getInt("inserzione.prodotto_idprodotto");
				Prodotto prodotto = dao1.getProdottoById(idProdotto); //ho l'intero prodotto
				logger.debug("prodotto caricato: " + prodotto.toString());
				System.out.println("PRODOTTO CARICATO");
				
				ImmagineDao dao2 = new ImmagineDaoMysqlJdbc();
				List<Immagine> immagini = dao2.getImmaginiByIdInserzione(rs.getInt("inserzione.idinserzione")); //prelevo tutte le immagine dell'inserzione
				logger.debug("immagini caricate");
				System.out.println("IMMAGINI CARICATE");
				
				OffertaDao dao3 = new OffertaDaoMysqlJdbc();
				List<Offerta> listaOfferte = dao3.getOfferteByIdInserzione(idInserzione);
				logger.debug("offerte caricate: ");
				System.out.println("OFFERTE CARICATE");
								
				inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setTitolo(rs.getString("inserzione.titolo"));
				inserzione.setDescrizione(rs.getString("inserzione.descrizione"));
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				
				inserzione.setStato(rs.getString("inserzione.stato"));
				
				inserzione.setIdAcquirente(idAcquirente); //se l'acquirente è null, L' IDACQUIRENTE VIENE VISTO COME 0
				inserzione.setAcquirente(acquirente); 
				
				inserzione.setIdVenditore(idVenditore);
				inserzione.setVenditore(venditore);
				inserzione.setIdProdotto(idProdotto);
				inserzione.setProdotto(prodotto);
				inserzione.setImmagini(immagini); //carico le immagini
				inserzione.setOfferte(listaOfferte); //carico tutte le offerte
				
				
				logger.debug("inserzione caricata: " + inserzione.getIdInserzione() + " " + inserzione.getTitolo());
			}
			
		} catch (ClassNotFoundException	| IOException | SQLException e) {
			// TODO Auto-generated catch block
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
			
		return inserzione;
		
	}
	

	public Inserzione getInserzioneByIdSenzaListe(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException{
		logger.debug("in getInserzioneByIdSenzaListe");
		Inserzione inserzione = null;
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM inserzione, prodotto, categoria, produttore, prodotto_has_keyword, keyword " +
						"WHERE (inserzione.idinserzione = ?)";
		
				
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idInserzione);
			logger.debug("select inserzione" + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				inserzione = new InserzioneImpl();
				
				UtenteRegistratoDao dao = new UtenteRegistratoDaoMysqlJdbc();
				Integer idAcquirente = rs.getInt("inserzione.acquirente_utente_registrato_idutente"); 
				UtenteRegistrato acquirente = null;
				if(idAcquirente != 0){ //lo 0 equivale al null
					 acquirente = dao.getUtenteRegistratoById(idAcquirente); 
				}
				logger.debug("idAcquirente: " + idAcquirente);
				
				int idVenditore = rs.getInt("inserzione.venditore_utente_registrato_idutente");
				UtenteRegistrato venditore = dao.getUtenteRegistratoById(idVenditore);
				
				
				ProdottoDao dao1 = new ProdottoDaoMysqlJdbc();
				int idProdotto = rs.getInt("prodotto.idprodotto");
				Prodotto prodotto = dao1.getProdottoById(idProdotto); //ho l'intero prodotto
				logger.debug("prodotto caricato: " + prodotto.toString());
				
								
				inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setTitolo(rs.getString("inserzione.titolo"));
				inserzione.setDescrizione(rs.getString("inserzione.descrizione"));
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				inserzione.setStato(rs.getString("inserzione.stato"));
				
				inserzione.setIdAcquirente(idAcquirente); //se l'acquirente è null, L' IDACQUIRENTE VIENE VISTO COME 0
				inserzione.setAcquirente(acquirente); 
				
				inserzione.setIdVenditore(idVenditore);
				inserzione.setVenditore(venditore);
				inserzione.setIdProdotto(idProdotto);
				inserzione.setProdotto(prodotto);				
				
				logger.debug("inserzione caricata: " + inserzione.getIdInserzione() + " " + inserzione.getTitolo());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (connection!=null) {
				rs.close();
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
				logger.debug("Connection chiusa");
			}
		}		
		return inserzione;
	}
	
}
