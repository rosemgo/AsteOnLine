package it.unisannio.sweng.rosariogoglia.daoImpl;

import it.unisannio.sweng.rosariogoglia.dbUtil.ConnectionPoolTomcat;
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
import it.unisannio.sweng.rosariogoglia.modelImpl.ProdottoImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ImmagineImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class InserzioneDaoMysqlJdbc implements InserzioneDao {

	Logger logger = Logger.getLogger(InserzioneDaoMysqlJdbc.class);
	
	
	
	public List<Inserzione> getInserzioni() {
		logger.debug("in getInserzioni");
		
		List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
		Inserzione inserzione = null;
		
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectionPoolTomcat.getConnection();
			
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

			try {
					if(rs != null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
					if(connection != null){
						connection.close();
						connection.setAutoCommit(true);
					}
					
					logger.debug("Connection chiusa");
				} catch (SQLException  e) {

					e.printStackTrace();
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
			
			connection = ConnectionPoolTomcat.getConnection();
		
			
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
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(connection != null){
					connection.close();
					connection.setAutoCommit(true);
				}
				
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
			
			connection = ConnectionPoolTomcat.getConnection();
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
			
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(connection != null){
					connection.close();
					connection.setAutoCommit(true);
				}
				
				logger.debug("Connection chiusa");
			
		}		
		return inserzione;
	}

	public List<UtenteRegistrato> getUtentiRegistratiOsservanoByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException{
		logger.debug("in getUtentiRegistratiOsservanoByIdInserzione");
		List<UtenteRegistrato> listaUtentiRegistrati = new ArrayList<UtenteRegistrato>();
		Connection connection = null;
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		try{
			connection = ConnectionPoolTomcat.getConnection();
			connection.setAutoCommit(false);
						
			UtenteRegistrato utente;
			
			String sql = "SELECT * FROM utente_registrato_osserva_inserzione " +
					"WHERE inserzione_idinserzione = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idInserzione);
			logger.debug("Select Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				
				utente = new UtenteRegistratoImpl();
				UtenteRegistratoDao dao = new UtenteRegistratoDaoMysqlJdbc();
				utente = dao.getUtenteRegistratoById(rs.getInt("utente_registrato_idutente"));
				
				listaUtentiRegistrati.add(utente);
				logger.debug("utente aggiunto alla lista");
							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaUtentiRegistrati;
}	

public List<String> getTitoli(){
	logger.debug("in getTitoli");
	List<String> listaTitoli = new ArrayList<String>();
	Connection connection = null;
	PreparedStatement  pstmt = null;
	ResultSet rs = null;

	try {
		connection = ConnectionPoolTomcat.getConnection();
					
		String sql = "SELECT DISTINCT(titolo) FROM inserzione";
		pstmt = connection.prepareStatement(sql);
		rs = pstmt.executeQuery();
		logger.debug("Select query: " + pstmt.toString());
		
		while(rs.next()){
			listaTitoli.add(rs.getString("titolo"));
		}
						
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally{
		try {
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(connection != null){
				connection.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	return listaTitoli;
}

/**
 * se keyword=null significa che non bisogna filtrare per parola chiave.
 * se categoria=0 significa che non bisogna filtrare per categoria.
 */
public List<Inserzione> ricercaInserzioni(String keyword, Integer idCategoria){
	logger.debug("in ricercaInserzioni");
	List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
	
	Connection connection = null;
	PreparedStatement  pstmt = null;
	ResultSet rs = null;

	try {
		
		connection = ConnectionPoolTomcat.getConnection();
			
		String sql = "SELECT DISTINCT * FROM inserzione, categoria, prodotto, keyword, prodotto_has_keyword " +
				"WHERE " +
				"inserzione.prodotto_idprodotto = prodotto.idprodotto " +
				"AND " +
				"categoria.idcategoria = prodotto.categoria_idcategoria " +
				"AND " +
				"prodotto.idprodotto = prodotto_has_keyword.prodotto_idprodotto " +
				"AND " +
				"prodotto_has_keyword.keyword_idkeyword = keyword.idkeyword " +
				"AND " +
				"inserzione.stato = 'in asta' ";
				
		
		logger.debug(keyword);
		
		if(!keyword.equals("") && keyword != null)
			sql = sql + " AND keyword.keyword LIKE ? ";
		
		if(idCategoria != 0)
			sql = sql + " AND categoria.idcategoria = ? ";
		
		sql = sql + " GROUP BY idinserzione ";
		
		pstmt = connection.prepareStatement(sql);
					
		if(!keyword.equals("") && keyword != null && idCategoria != 0){
			System.out.println("ENTRO NEL PRIMO");
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, idCategoria);
		}
		else if(!keyword.equals("") && keyword != null ) {
			System.out.println("ENTRO IN SOLO KEYWORD PRESENTE");
			pstmt.setString(1, "%" + keyword + "%");
		}
		else if(idCategoria != 0){
			System.out.println("ENTRO IN SOLO CATEGORIA PRESENTE");
			pstmt.setInt(1, idCategoria);
		}
		
		
		logger.debug("Select Query:" + pstmt.toString());
		rs = pstmt.executeQuery();
		
		if (rs.next()) { 
					
			listaInserzioni = new ArrayList<Inserzione>();

			Inserzione inserzione;
			Prodotto prodotto;
			do{
				
				inserzione = new InserzioneImpl();
				prodotto = new ProdottoImpl();
				
				
				inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setTitolo(rs.getString("inserzione.titolo"));
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				inserzione.setStato(rs.getString("inserzione.stato"));
				
				ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
				prodotto = daoP.getProdottoById(rs.getInt("prodotto.idprodotto"));
				inserzione.setProdotto(prodotto);
				
				ImmagineDao daoI = new ImmagineDaoMysqlJdbc();
				List<Immagine> listaImmagini = daoI.getImmaginiByIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setImmagini(listaImmagini);
									
				
				listaInserzioni.add(inserzione);
				
			}while(rs.next());	
			
		}
		else{
			logger.debug("Nessun risultato");
		}
		
	} catch (SQLException | ClassNotFoundException | IOException e) {
		e.printStackTrace();
	}
	finally{
		try {
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(connection != null){
				connection.close();
				connection.setAutoCommit(true);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return listaInserzioni;
}


public List<Inserzione> ordinaInserzioniPopolari() throws SQLException{
	logger.debug("in ordinaInserzioniPopolari");
	List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
	Connection connection=null;
	PreparedStatement pstmt = null;
	
	try {
		
		connection = ConnectionPoolTomcat.getConnection();
		
		String sql = "SELECT idinserzione, titolo, prezzo_iniziale, prezzo_aggiornato, data_scadenza, count(*) AS numoss " +
				"FROM inserzione, utente_registrato_osserva_inserzione " +
				"WHERE (inserzione.idinserzione = utente_registrato_osserva_inserzione.inserzione_idinserzione) " +
				"AND inserzione.stato = 'in asta' " +
				"GROUP BY inserzione_idinserzione " +
				"ORDER BY numoss DESC, inserzione.idinserzione ASC ";
		
		
		pstmt = connection.prepareStatement(sql);
	
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){
			
			
			Inserzione inserzione;
			inserzione = new InserzioneImpl();
	
			inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
			inserzione.setTitolo(rs.getString("inserzione.titolo"));
			inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
			inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
			inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
			
			System.out.println("visualizza l'inserzione: " + inserzione.getTitolo());
			
			listaInserzioni.add(inserzione);
				
		}
				
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	finally{
		
		if(pstmt != null)
			pstmt.close();
     	if(connection != null){
			connection.close();
		}
		
	}
	
	return listaInserzioni;
}


public List<Inserzione> ricercaTopInserzioniPopolari(int numInserzioni) throws ClassNotFoundException, SQLException, IOException{
	
	List<Inserzione> listaInserzioni = new ArrayList<Inserzione>(); 
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement  pstmt = null;
	
	try {
		//connection = DatabaeUtil.getConnection();
		
		connection = ConnectionPoolTomcat.getConnection();
		
		System.out.println("connection: " + connection.toString());
		
		
		
		String sql = "SELECT idinserzione, titolo, prezzo_iniziale, prezzo_aggiornato, data_scadenza, count(*) AS numoss " +
				"FROM inserzione, utente_registrato_osserva_inserzione " +
				"WHERE (inserzione.idinserzione = utente_registrato_osserva_inserzione.inserzione_idinserzione) " +
				"AND inserzione.stato = 'in asta' " +
				"GROUP BY inserzione_idinserzione " +
				"ORDER BY numoss DESC, inserzione.idinserzione ASC " +
				"LIMIT " + numInserzioni;
		
		pstmt = connection.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		Inserzione inserzione;
		ImmagineDao dao = new ImmagineDaoMysqlJdbc();
		
		while(rs.next()){
			inserzione = new InserzioneImpl();

			inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
			inserzione.setTitolo(rs.getString("inserzione.titolo"));
			inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
			inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
			inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				
			inserzione.setImmagini(dao.getImmaginiByIdInserzione(rs.getInt("inserzione.idinserzione"))); 
				
			listaInserzioni.add(inserzione);
				
		}
				
		connection.close();
		
	
	} catch (ClassNotFoundException	| IOException | SQLException e) {
		e.printStackTrace();
	}
	
	finally{
		if(rs != null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
		if(connection != null){
			connection.close();
		}
		
	}
	
	return listaInserzioni;
}
	
	
public List<Inserzione> ricercaTopInserzioniChiusura(int numInserzioni) throws ClassNotFoundException, SQLException, IOException{
	logger.debug("in ricercaTopInserzioniChiusura");
	List<Inserzione> listaInserzioni = new ArrayList<Inserzione>(); 
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement  pstmt = null;
	try {
		connection = ConnectionPoolTomcat.getConnection();
		
		String sql = "SELECT idinserzione, titolo, prezzo_iniziale, prezzo_aggiornato, data_scadenza FROM inserzione " +
				"WHERE (( DATEDIFF(inserzione.data_scadenza, CURDATE()) > 0 ) OR ( DATEDIFF(inserzione.data_scadenza, CURDATE()) = 0 AND TIMEDIFF(inserzione.data_scadenza, NOW()) > 0 )) " +
				"ORDER BY inserzione.data_scadenza ASC " +
				"LIMIT " + numInserzioni;
		
		pstmt = connection.prepareStatement(sql);
		logger.debug("Select Query:" + pstmt.toString());
		
		rs = pstmt.executeQuery();
	
		Inserzione inserzione;
		ImmagineDao dao = new ImmagineDaoMysqlJdbc();

		while(rs.next()){
				inserzione = new InserzioneImpl();
				
				inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setTitolo(rs.getString("inserzione.titolo"));
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
			
				inserzione.setImmagini(dao.getImmaginiByIdInserzione(rs.getInt("inserzione.idinserzione"))); 
				
				listaInserzioni.add(inserzione);	
				
		}
		
		logger.debug("inserzioni in scadenza caricate");
	
	} catch (ClassNotFoundException	| IOException | SQLException e) {
		e.printStackTrace();
	}
	
	finally{
		if(rs != null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
		if(connection != null){
			connection.close();
		}
		
	}
	return listaInserzioni;
	
}
	
	
public Integer updateStatoInserzione(String statoInserzione, Integer idInserzione){
	logger.debug("in updateStatoInserzione");
	Integer updatedRows = -1;
	
	Connection connection = null;
	PreparedStatement  pstmt = null;
	try {			
		
		connection = ConnectionPoolTomcat.getConnection();
		
		connection.setAutoCommit(false);
		
		
		String sql = "UPDATE inserzione SET stato = ? " +
				"WHERE idinserzione = ? ";
		
		pstmt = connection.prepareStatement(sql);
		
		pstmt.setString(1, statoInserzione);
		pstmt.setInt(2, idInserzione);
		
		logger.debug("Update Query:" + pstmt.toString());
		updatedRows = pstmt.executeUpdate();
		
		connection.commit();
		logger.info("Inserzione Aggiornata");
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(connection != null){
					connection.close();
					connection.setAutoCommit(true);
				}
				
				logger.debug("Connection chiusa");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	return updatedRows;
}


public Integer insertInserzione(Inserzione inserzione) throws SQLException{
	logger.info("Inserimento Inserzione");
	Integer inserzioneIdKey = -1;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		connection = ConnectionPoolTomcat.getConnection();
		
		connection.setAutoCommit(false);
		
							
		String sql = "INSERT INTO inserzione (titolo, descrizione, prezzo_iniziale, prezzo_aggiornato, data_scadenza, stato,  venditore_utente_registrato_idutente, prodotto_idprodotto) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
					
		logger.debug("Inseriamo l' inserzione");
		pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setString(1, inserzione.getTitolo());
		pstmt.setString(2, inserzione.getDescrizione());
		pstmt.setDouble(3, inserzione.getPrezzoIniziale());
		pstmt.setDouble(4, inserzione.getPrezzoAggiornato());
		pstmt.setTimestamp(5, Utility.convertitoreDataUtilToTimestamp(inserzione.getDataScadenza()));
		pstmt.setString(6, inserzione.getStato());
		//pstmt.setInt(7, inserzione.getIdAcquirente()); l'acquirente viene impostato automaticamente a null nel db
		pstmt.setInt(7, inserzione.getIdVenditore());
		pstmt.setInt(8, inserzione.getIdProdotto());
		logger.debug("Insert Query: " + pstmt.toString());
		
		int insertRows = pstmt.executeUpdate();
		logger.debug("righe inserite: "+ insertRows);
		
		if(insertRows == 1){
			rs = pstmt.getGeneratedKeys();
			if(rs.next()){
				inserzioneIdKey = rs.getInt(1);
			}
		}
		inserzione.setIdInserzione(inserzioneIdKey);
		logger.debug("id dell' inserzione è: " + inserzioneIdKey);
		
		connection.commit();
							
		Integer immagineIdKey = -1;
		
		//effettuare controllo per vedere se la lista immagine è vuota
		if((inserzione.getImmagini() != null) && (inserzione.getImmagini().size() > 0)){
		
			ImmagineDao dao = new ImmagineDaoMysqlJdbc();
		//	Immagine immagine = new ImmagineImpl();
			
			for(int i=0; i<inserzione.getImmagini().size(); i++){
				
				logger.debug("nella lista immagini");
				
			//	immagine = inserzione.getImmagini().get(i); //prendo l'immagine. NB se salvo l'immagine in una variabile immagine, e faccio delle modifiche, dove saranno apportate tali modifiche? solo sull'immagine o sull'arrayList?
				
			//	immagine.setIdInserzione(inserzioneIdKey); //setto l'idinserzione relativo all' immagine
				
				inserzione.getImmagini().get(i).setIdInserzione(inserzioneIdKey); 
				
				logger.debug("idinserzione nell'immagine: " + inserzione.getImmagini().get(i).getIdInserzione());
				logger.debug(inserzione.getImmagini().get(i).getFoto());
				
				System.out.println("IN INSERIMENTO IMMAGINE: " + inserzione.getImmagini().get(i).getFoto());
				
				immagineIdKey = dao.insertImmagine(inserzione.getImmagini().get(i)); //inserisco l'immagine!!!
				
				inserzione.getImmagini().get(i).setIdImmagine(immagineIdKey);//setto l'id dell'immagine nell'arraylist delle immagini di inserzione
				
				logger.debug("id dell' immagine è: " + immagineIdKey);
				
				System.out.println("id dell' immagine è: " + immagineIdKey);
				
			}
			
			connection.commit();
			
		}
					
		logger.debug("Inserimento inserzione (" + inserzioneIdKey + ", " + inserzione.getTitolo() + ")");

	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally {

		if(rs != null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
		if(connection != null){
			connection.close();
			connection.setAutoCommit(true);
		}
				
		logger.debug("Connection chiusa");

	}

	return inserzioneIdKey;
}	
	
public Integer updateInserzione(Inserzione inserzione) throws ClassNotFoundException, SQLException, IOException{
	logger.debug("in updateInserzione");
	logger.info("Aggiornamento Inserzione: (" + inserzione.getIdInserzione()+ ", " + inserzione.getTitolo() + ", " + inserzione.getDescrizione() +")");
	Integer updatedRows = -1;
	Connection connection = null;
	PreparedStatement  pstmt = null;
	try {
		connection = ConnectionPoolTomcat.getConnection();
		
		connection.setAutoCommit(false);
		
		String sql = "DELETE FROM immagine WHERE (immagine.inserzione_idinserzione = ?) ";
	
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, inserzione.getIdInserzione());
		logger.debug("Query di cancellazione" + pstmt.toString());
		int deletedRows = pstmt.executeUpdate();
		logger.debug("righe cancellate" + deletedRows);
		
		connection.commit();
		
		//inseriamo le immagine aggiornate
		if((inserzione.getImmagini() != null) && (inserzione.getImmagini().size() > 0)){
			
			Integer immagineIdKey = -1;
			ImmagineDao dao = new ImmagineDaoMysqlJdbc();
			Immagine immagine = new ImmagineImpl();
			
			for(int i=0; i<inserzione.getImmagini().size(); i++){
				System.out.println("id inserzione dell'immagine: " + inserzione.getImmagini().get(i).getIdInserzione() );
				if(inserzione.getImmagini().get(i).getIdInserzione() == 0){ //se l'id dell'inserzione relativa all'immagine è uguale a 0(perchè essendo un int non può essere null) dobbiamo settarla con l'id dell'inserzione aggiornata
					inserzione.getImmagini().get(i).setIdInserzione(inserzione.getIdInserzione()); //setto l'idinserzione relativo all' immagine
					logger.debug("Ho settato l'idimmagine: " + inserzione.getIdInserzione() + " nella lista delle immagini dell'inserzione");
				}
				immagine = inserzione.getImmagini().get(i);
				
				immagineIdKey = dao.insertImmagine(immagine);
				logger.debug("id dell' immagine è: " + immagineIdKey);
				
				inserzione.getImmagini().get(i).setIdImmagine(immagineIdKey);//setto l'id dell'immagine nell'arraylist delle immagini di inserzione
				
			}	
		}
		
		sql = "UPDATE inserzione SET titolo = ?, descrizione = ?, prezzo_iniziale = ?, prezzo_aggiornato = ?, data_scadenza = ?, stato = ?, venditore_utente_registrato_idutente = ?, prodotto_idprodotto = ? " +
				"WHERE idinserzione = ? ";
			
		pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, inserzione.getTitolo());
		pstmt.setString(2, inserzione.getDescrizione());
		pstmt.setDouble(3, inserzione.getPrezzoIniziale());
		pstmt.setDouble(4, inserzione.getPrezzoAggiornato());
		pstmt.setTimestamp(5, Utility.convertitoreDataUtilToTimestamp(inserzione.getDataScadenza()));
		pstmt.setString(6, inserzione.getStato());
		//pstmt.setInt(7, inserzione.getIdAcquirente()); non mettiamo l'id utente perchè deve essere inserito come null, non come 0, altrimenti il db da errore perchè 0 non corrisponde all'id di nessun utente
		pstmt.setInt(7, inserzione.getIdVenditore());
		pstmt.setInt(8, inserzione.getIdProdotto());
		pstmt.setInt(9, inserzione.getIdInserzione());
		logger.debug("Update Query:" + pstmt.toString());
		updatedRows = pstmt.executeUpdate();
	
		
		connection.commit();
		logger.info("Inserzione Aggiornata");
	
	}

	finally {

		
		if(pstmt != null)
			pstmt.close();
		if(connection != null){
			connection.close();
			connection.setAutoCommit(true);
		}
		
	}
	return updatedRows;
}



public List<Inserzione> getLimitAsteInCorso(Integer limiteInf, Integer numInserzioniPerPagina) {
	logger.debug("in getLimitAsteInCorso");
	List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
	Inserzione inserzione;
	Prodotto prodotto;
	
	Connection connection = null;
	PreparedStatement  pstmt = null;
	ResultSet rs = null;
	try {
		connection = ConnectionPoolTomcat.getConnection();
								
		String sql = "SELECT * FROM inserzione, categoria, prodotto " +
				"WHERE " +
				"inserzione.prodotto_idprodotto = prodotto.idprodotto " +
				"AND " +
				"categoria.idcategoria = prodotto.categoria_idcategoria " +
				"AND " +
				"inserzione.stato = 'in asta' " +
				"LIMIT ?, ?";

		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, limiteInf);
		pstmt.setInt(2, numInserzioniPerPagina);
		logger.debug("Select Query:" + pstmt.toString());
		rs = pstmt.executeQuery();
			
		while(rs.next()){	
							
				inserzione = new InserzioneImpl();
				prodotto = new ProdottoImpl();
				
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
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				inserzione.setStato(rs.getString("inserzione.stato"));
				
				inserzione.setIdVenditore(idVenditore);
				inserzione.setVenditore(venditore);
				inserzione.setIdAcquirente(idAcquirente);
				inserzione.setAcquirente(acquirente);
				
				
				ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
				prodotto = daoP.getProdottoById(rs.getInt("prodotto.idprodotto"));
				inserzione.setIdProdotto(rs.getInt("prodotto.idprodotto"));
				inserzione.setProdotto(prodotto);
				
				ImmagineDao daoI = new ImmagineDaoMysqlJdbc();
				List<Immagine> listaImmagini = daoI.getImmaginiByIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setImmagini(listaImmagini);
									
				listaInserzioni.add(inserzione);
				
			}
		
	} catch (SQLException | ClassNotFoundException | IOException e) {
		e.printStackTrace();
	}
	finally{
		try {
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(connection != null){
				connection.close();
				connection.setAutoCommit(true);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return listaInserzioni;
	
}




public List<Inserzione> getLimitInserzioni(Integer limiteInf, Integer numInserzioniPagina) throws SQLException{
	logger.debug("in getLimitInserzioni");
	List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
	Inserzione inserzione;
	Prodotto prodotto;
	
	Connection connection = null;
	PreparedStatement  pstmt = null;
	ResultSet rs = null;
	
	try {
		
		connection = ConnectionPoolTomcat.getConnection();
		
		String sql = "SELECT * FROM inserzione, categoria, prodotto " +
				"WHERE " +
				"inserzione.prodotto_idprodotto = prodotto.idprodotto " +
				"AND " +
				"categoria.idcategoria = prodotto.categoria_idcategoria " +
				"LIMIT ?, ?";

		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, limiteInf);
		pstmt.setInt(2, numInserzioniPagina);
		logger.debug("Select Query:" + pstmt.toString());
		rs = pstmt.executeQuery();
							
			
		while(rs.next()){	
							
				inserzione = new InserzioneImpl();
				prodotto = new ProdottoImpl();
				
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
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				inserzione.setStato(rs.getString("inserzione.stato"));
				
				inserzione.setIdVenditore(idVenditore);
				inserzione.setVenditore(venditore);
				inserzione.setIdAcquirente(idAcquirente);
				inserzione.setAcquirente(acquirente);
				
				
				ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
				prodotto = daoP.getProdottoById(rs.getInt("prodotto.idprodotto"));
				inserzione.setIdProdotto(rs.getInt("prodotto.idprodotto"));
				inserzione.setProdotto(prodotto);
				
				ImmagineDao daoI = new ImmagineDaoMysqlJdbc();
				List<Immagine> listaImmagini = daoI.getImmaginiByIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setImmagini(listaImmagini);
									
				listaInserzioni.add(inserzione);
				
			}
				
		
	} catch (SQLException | ClassNotFoundException | IOException e) {
		e.printStackTrace();
	}
	finally{
		
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(connection != null){
				connection.close();
			}

	}
	return listaInserzioni;
}



public List<Inserzione> getLimitInserzioniChiusura(Integer limiteInf, Integer numInserzioniPerPagina){
	logger.debug("in ricercaTopInserzioniChiusura");
	
	List<Inserzione> listaInserzioni = null; 
	
	Connection connection = null;
	PreparedStatement  pstmt = null;
	ResultSet rs = null;
	try{
		connection = ConnectionPoolTomcat.getConnection();
				
		String sql = "SELECT idinserzione, titolo, prezzo_iniziale, prezzo_aggiornato, data_scadenza FROM inserzione " +
				"WHERE ( DATEDIFF(inserzione.data_scadenza, CURDATE()) < 31 AND DATEDIFF(inserzione.data_scadenza, CURDATE()) > 0) " +
				"OR " +
				"( DATEDIFF(inserzione.data_scadenza, CURDATE()) = 0 AND TIMEDIFF(inserzione.data_scadenza, NOW()) > 0 ) " +
				"ORDER BY inserzione.data_scadenza ASC " +
				"LIMIT ?, ?";
		
		pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, limiteInf);
		pstmt.setInt(2, numInserzioniPerPagina);
		logger.debug("Select Query:" + pstmt.toString());
		
		rs = pstmt.executeQuery();
	
		if(rs.next()){
			
			listaInserzioni = new ArrayList<Inserzione>();
			
			Inserzione inserzione;
			ImmagineDao dao = new ImmagineDaoMysqlJdbc();
			do{
				inserzione = new InserzioneImpl();
				
				inserzione.setIdInserzione(rs.getInt("inserzione.idinserzione"));
				inserzione.setTitolo(rs.getString("inserzione.titolo"));
				inserzione.setPrezzoIniziale(rs.getDouble("inserzione.prezzo_iniziale"));
				inserzione.setPrezzoAggiornato(rs.getDouble("inserzione.prezzo_aggiornato"));
				inserzione.setDataScadenza(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("inserzione.data_scadenza")));
				
				inserzione.setImmagini(dao.getImmaginiByIdInserzione(rs.getInt("inserzione.idinserzione"))); 
								
				listaInserzioni.add(inserzione);	
				
			}while(rs.next());
		
		}
						
	} catch (SQLException | ClassNotFoundException | IOException e) {
		e.printStackTrace();
	}
	finally{
		try {
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(connection != null){
				connection.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	logger.debug("inserzioni in scadenza caricate");
	
	return listaInserzioni;
	
	
}






}
