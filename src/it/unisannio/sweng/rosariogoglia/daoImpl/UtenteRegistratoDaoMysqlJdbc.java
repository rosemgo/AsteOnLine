package it.unisannio.sweng.rosariogoglia.daoImpl;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.dbUtil.DatabaseUtil;


import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Provincia;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;


import it.unisannio.sweng.rosariogoglia.modelImpl.ComuneImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProvinciaImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;







import it.unisannio.sweng.rosariogoglia.utility.Utility;

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


public class UtenteRegistratoDaoMysqlJdbc implements UtenteRegistratoDao{
	
	Logger logger = Logger.getLogger(UtenteRegistratoDaoMysqlJdbc.class);
		
	public UtenteRegistratoDaoMysqlJdbc(){
		DOMConfigurator.configure("C:/Users/Rosario/git/AsteOnLine/WebContent/WEB-INF/log4jConfig.xml");
	}
	
	
	
	public UtenteRegistrato getUtenteRegistratoById(Integer idUtente) throws ClassNotFoundException, IOException{
		logger.debug("in getUtenteRegistratoById");
		
		UtenteRegistrato utenteReg = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		
			connection = DatabaseUtil.getConnection();
			
			String sql = "SELECT * FROM utente_registrato, comune, provincia " +
						 "WHERE utente_registrato.comune_idcomune = comune.idcomune " +
						 "AND " + 
						 "comune.provincia_idprovincia = provincia.idprovincia " +
						 "AND " +
						 "utente_registrato.idutente = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtente);
			logger.debug("Select Query : " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				utenteReg = new UtenteRegistratoImpl();
				Comune comune = new ComuneImpl();
				Provincia provincia = new ProvinciaImpl();
				
				comune.setIdComune(rs.getInt("comune.idcomune"));
				comune.setNomeComune(rs.getString("comune.nome_comune"));
				comune.setIdProvincia(rs.getInt("comune.provincia_idprovincia"));
				logger.debug("comune: " + comune.getNomeComune());
				
				provincia.setIdProvincia(rs.getInt("provincia.idprovincia"));
				provincia.setNomeProvincia(rs.getString("provincia.nome_provincia"));
				logger.debug("provincia: " + provincia.getNomeProvincia());
				
				comune.setProvincia(provincia);
				
				utenteReg.setIdUtente(rs.getInt("idutente"));
				utenteReg.setNick(rs.getString("nick"));
				utenteReg.setNome(rs.getString("nome"));
				utenteReg.setCognome(rs.getString("cognome"));
				utenteReg.setPassword(rs.getString("password"));
				utenteReg.seteMail(rs.getString("e_mail"));
				utenteReg.setCodiceFiscale(rs.getString("codice_fiscale"));
				utenteReg.setNumContoCorrente(rs.getString("n_conto_corrente"));
				utenteReg.setIndirizzo(rs.getString("indirizzo"));
				utenteReg.setCap(rs.getString("cap"));
				utenteReg.setTelefono(rs.getString("telefono"));
				utenteReg.setTipologiaCliente(rs.getString("tipologia_cliente"));
				utenteReg.setDataRegistrazione(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("data_registrazione")));
				utenteReg.setFlagAbilitato(rs.getBoolean("flag_abilitato"));
				utenteReg.setIdComune(rs.getInt("comune_idcomune"));
				utenteReg.setComune(comune);
				
				logger.debug("utente registrato: " + utenteReg.toString());		
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
		return utenteReg;
	}
	
	
	
	
	public UtenteRegistrato getUtenteRegistratoByeMail(String eMail) throws ClassNotFoundException, IOException{
		logger.debug("in getUtenteRegistratoByeMail");
		
		UtenteRegistrato utenteReg = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			
			connection = DatabaseUtil.getConnection();
							
			String sql = "SELECT * FROM utente_registrato, comune, provincia " +
						 "WHERE utente_registrato.comune_idcomune = comune.idcomune " +
						 "AND " + 
						 "comune.provincia_idprovincia = provincia.idprovincia " +
						 "AND " +
						 "utente_registrato.e_mail = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, eMail);
			logger.debug("Select Query : " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			
			if(rs.next()){
				
				utenteReg = new UtenteRegistratoImpl();
				Comune comune = new ComuneImpl();
				Provincia provincia = new ProvinciaImpl();
				
				
				comune.setIdComune(rs.getInt("comune.idcomune"));
				comune.setNomeComune(rs.getString("comune.nome_comune"));
				comune.setIdProvincia(rs.getInt("comune.provincia_idprovincia"));
				logger.debug("comune: " + comune.getNomeComune());
				
				provincia.setIdProvincia(rs.getInt("provincia.idprovincia"));
				provincia.setNomeProvincia(rs.getString("provincia.nome_provincia"));
				logger.debug("provincia: " + provincia.getNomeProvincia());
				
				comune.setProvincia(provincia);
				
				utenteReg.setIdUtente(rs.getInt("idutente"));
				utenteReg.setNick(rs.getString("nick"));
				utenteReg.setNome(rs.getString("nome"));
				utenteReg.setCognome(rs.getString("cognome"));
				utenteReg.setPassword(rs.getString("password"));
				utenteReg.seteMail(rs.getString("e_mail"));
				utenteReg.setCodiceFiscale(rs.getString("codice_fiscale"));
				utenteReg.setNumContoCorrente(rs.getString("n_conto_corrente"));
				utenteReg.setIndirizzo(rs.getString("indirizzo"));
				utenteReg.setCap(rs.getString("cap"));
				utenteReg.setTelefono(rs.getString("telefono"));
				utenteReg.setTipologiaCliente(rs.getString("tipologia_cliente"));
				utenteReg.setDataRegistrazione(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("data_registrazione")));
				utenteReg.setFlagAbilitato(rs.getBoolean("flag_abilitato"));
				utenteReg.setIdComune(rs.getInt("comune_idcomune"));
				utenteReg.setComune(comune);
				
				logger.debug("(" + utenteReg.getIdUtente() + ", " + utenteReg.getNome() + ")");	
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
		
		logger.debug("utente registrato: " + utenteReg.toString());	
			
		return utenteReg;
	}
	
	
	public UtenteRegistrato getUtenteRegistratoByNick(String nick) throws ClassNotFoundException, IOException{
		logger.debug("in getUtenteRegistratoByNick");
		
		UtenteRegistrato utenteReg = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
					
			String sql = "SELECT * FROM utente_registrato, comune, provincia " +
						 "WHERE utente_registrato.comune_idcomune = comune.idcomune " +
						 "AND " + 
						 "comune.provincia_idprovincia = provincia.idprovincia " +
						 "AND " +
						 "utente_registrato.nick = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nick);
			logger.debug("Select Query : " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			
			if(rs.next()){
				
				utenteReg = new UtenteRegistratoImpl();
				Comune comune = new ComuneImpl();
				Provincia provincia = new ProvinciaImpl();
				
				
				comune.setIdComune(rs.getInt("comune.idcomune"));
				comune.setNomeComune(rs.getString("comune.nome_comune"));
				comune.setIdProvincia(rs.getInt("comune.provincia_idprovincia"));
				logger.debug("comune: " + comune.getNomeComune());
				
				provincia.setIdProvincia(rs.getInt("provincia.idprovincia"));
				provincia.setNomeProvincia(rs.getString("provincia.nome_provincia"));
				logger.debug("provincia: " + provincia.getNomeProvincia());
				
				comune.setProvincia(provincia);
				
				utenteReg.setIdUtente(rs.getInt("idutente"));
				utenteReg.setNick(rs.getString("nick"));
				utenteReg.setNome(rs.getString("nome"));
				utenteReg.setCognome(rs.getString("cognome"));
				utenteReg.setPassword(rs.getString("password"));
				utenteReg.seteMail(rs.getString("e_mail"));
				utenteReg.setCodiceFiscale(rs.getString("codice_fiscale"));
				utenteReg.setNumContoCorrente(rs.getString("n_conto_corrente"));
				utenteReg.setIndirizzo(rs.getString("indirizzo"));
				utenteReg.setCap(rs.getString("cap"));
				utenteReg.setTelefono(rs.getString("telefono"));
				utenteReg.setTipologiaCliente(rs.getString("tipologia_cliente"));
				utenteReg.setDataRegistrazione(Utility.convertitoreTimestampToDataUtil(rs.getTimestamp("data_registrazione")));
				utenteReg.setFlagAbilitato(rs.getBoolean("flag_abilitato"));
				utenteReg.setIdComune(rs.getInt("comune_idcomune"));
				utenteReg.setComune(comune);
				
				logger.debug("utente registrato: " + utenteReg.toString());	
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
		
		return utenteReg;
	}
	
	
	public Integer insertUtenteRegistrato(UtenteRegistrato utenteRegistrato) throws ClassNotFoundException, IOException, SQLException{
		logger.info("in insertRegistrato");
		Integer utenteIdKey = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO utente_registrato (nick, nome, cognome, password, e_mail, codice_fiscale, n_conto_corrente, indirizzo, cap, telefono, tipologia_cliente, data_registrazione, comune_idcomune, flag_abilitato)" +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					
			logger.debug("Inseriamo l'utente");
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, utenteRegistrato.getNick());
			pstmt.setString(2, utenteRegistrato.getNome());
			pstmt.setString(3, utenteRegistrato.getCognome());
			pstmt.setString(4, utenteRegistrato.getPassword());
			pstmt.setString(5, utenteRegistrato.geteMail());
			pstmt.setString(6, utenteRegistrato.getCodiceFiscale());
			pstmt.setString(7, utenteRegistrato.getNumContoCorrente());
			pstmt.setString(8, utenteRegistrato.getIndirizzo());
			pstmt.setString(9, utenteRegistrato.getCap());
			pstmt.setString(10, utenteRegistrato.getTelefono());
			pstmt.setString(11, utenteRegistrato.getTipologiaCliente());
			pstmt.setTimestamp(12, Utility.convertitoreDataUtilToTimestamp(utenteRegistrato.getDataRegistrazione()));
			pstmt.setInt(13, utenteRegistrato.getIdComune());
			pstmt.setBoolean(14, utenteRegistrato.isFlagAbilitato());
			logger.debug("Insert Query: " + pstmt.toString());
			int insertRows = pstmt.executeUpdate();
			logger.debug("righe inserite: "+ insertRows);
			if(insertRows == 1){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					utenteIdKey = rs.getInt(1);
				}
			}
			utenteRegistrato.setIdUtente(utenteIdKey); //setto l'id dell'utente
			logger.debug("id dell'utente è: " + utenteIdKey);
			
					
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("Rollback in inserimento prodotto");
			connection.rollback();
		}	
				
		finally {
			if (connection!=null) {
				if(rs != null)
					rs.close();
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
				logger.debug("Connection chiusa");
				
			}
		}	
	
		return utenteIdKey;
		
	}

	
	
	
	/**
	 * eliminando un utente registrato bisogna eliminare tutte le inserzioni caricate da quell'utente???? FINIREEEEEE!!!
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public Integer deleteUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, IOException, SQLException {
		logger.info("in deleteRegistrato");
		Integer updatedRows = -1;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE utente_registrato SET (flagAbilitato = ?) WHERE (idutente = ?)";
			
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setBoolean(1, false);
			pstmt.setInt(2, utente.getIdUtente());
			logger.debug("Delete Query: " + pstmt.toString());
			updatedRows = pstmt.executeUpdate();
			logger.debug("righe aggiornate: " + updatedRows);
			
			connection.commit();
				
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("Rollback in delete utente");
			connection.rollback();
		}	
		finally {
			if (connection!=null) {
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
				logger.debug("Connection chiusa");
			}
		}
		
		return updatedRows;
	}

	
	public Integer updateUtenteRegistrato(UtenteRegistrato utenteRegistrato) throws ClassNotFoundException, IOException, SQLException {
		logger.debug("In updateUtenteRegistrato");
		Integer updatedRows = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE utente_registrato SET nick = ?, nome = ?, cognome = ?, password = ?, e_mail = ?, codice_fiscale = ?, n_conto_corrente = ?, indirizzo = ?, cap = ?, telefono = ?, tipologia_cliente = ?, data_registrazione = ?, flag_abilitato = ?, comune_idcomune = ? " +
					"WHERE idutente = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, utenteRegistrato.getNick());
			pstmt.setString(2, utenteRegistrato.getNome());
			pstmt.setString(3, utenteRegistrato.getCognome());
			pstmt.setString(4, utenteRegistrato.getPassword());
			pstmt.setString(5, utenteRegistrato.geteMail());
			pstmt.setString(6, utenteRegistrato.getCodiceFiscale());
			pstmt.setString(7, utenteRegistrato.getNumContoCorrente());
			pstmt.setString(8, utenteRegistrato.getIndirizzo());
			pstmt.setString(9, utenteRegistrato.getCap());
			pstmt.setString(10, utenteRegistrato.getTelefono());
			pstmt.setString(11, utenteRegistrato.getTipologiaCliente());
			pstmt.setTimestamp(12, Utility.convertitoreDataUtilToTimestamp(utenteRegistrato.getDataRegistrazione()));
			pstmt.setBoolean(13, utenteRegistrato.isFlagAbilitato());
			pstmt.setInt(14, utenteRegistrato.getIdComune());
			logger.debug("Updated Query: " + pstmt.toString());
			updatedRows = pstmt.executeUpdate();
					
			connection.commit();
			logger.info("Utente Registrato Aggiornato");
		
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("Rollback in aggiornamenrìto Utente Registrato");
			connection.rollback();
		}		
		finally {
			if (connection!=null) {
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
				logger.debug("Connection chiusa");
			}
		}
		
		return updatedRows;
	}

	
	
	
	
	
	public Integer updateDatiPersonali(String nick, String nome, String cognome, String codFiscale, String indirizzo, Integer idComune, String cap, String telefono, String nContoCorrente, String eMail) throws SQLException, ClassNotFoundException, IOException{
		logger.debug("in updateDatiPersonali");
		Integer updatedRows = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {	
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE utente_registrato SET nome = ?, cognome = ?, codice_fiscale = ?, e_mail = ?, n_conto_corrente = ?, indirizzo = ?, cap = ?, telefono = ?, comune_idcomune = ? " +
					"WHERE nick = ? ";			
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, codFiscale);
			pstmt.setString(4, eMail);
			pstmt.setString(5, nContoCorrente);
			pstmt.setString(6, indirizzo);
			pstmt.setString(7, cap);
			pstmt.setString(8, telefono);
			pstmt.setInt(9, idComune);
			pstmt.setString(10, nick);
			logger.debug("Updated Query: " + pstmt.toString());
			updatedRows = pstmt.executeUpdate();
					
			
			connection.commit();
			logger.info("Utente Registrato Aggiornato");
		
		} catch (SQLException  e) {
			e.printStackTrace();
			logger.debug("Rollback in aggiornamento dati personali Utente Registrato");
			connection.rollback();
		}	
		finally {
			if (connection!=null) {
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
				logger.debug("Connection chiusa");
			}
		}
		
		return updatedRows;
	}
	
	
	public Integer updateStatoUtente(Integer idUtente, Boolean flagAbilitato) throws ClassNotFoundException, IOException{
		logger.debug("in updateUtente");
		Integer updatedRows = -1;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE utente_registrato SET flag_abilitato = ? WHERE idutente = ?";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setBoolean(1, flagAbilitato);
			pstmt.setInt(2, idUtente);
				
			logger.debug("Updated Query: " + pstmt.toString());
			updatedRows = pstmt.executeUpdate();
						
			connection.commit();
			logger.info("Stato Utente Registrato Aggiornato");
				
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("Rollback in aggiornamenrìto Stato Utente Registrato");
		}
		finally {
			if (connection!=null) {
				try {	
					pstmt.close();
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				logger.debug("Connection chiusa");
			}
		}		
		return updatedRows;
	}
	
	
	
	
	public Integer updateTipologiaUtente(String nick, String tipologiaCliente) throws ClassNotFoundException, SQLException, IOException {
		logger.debug("in updateTipologiaUtente");
		Integer updatedRows = -1;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE utente_registrato SET tipologia_cliente = ? WHERE nick = ?";
			
			pstmt = connection.prepareStatement(sql);
		
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, tipologiaCliente);
			
			logger.debug("Updated Query: " + pstmt.toString());
			updatedRows = pstmt.executeUpdate();
			
			connection.commit();
			logger.info("updateTipologiaUtente");
			
		} catch (SQLException  e) {
			connection.rollback();
			logger.debug("Rollback in updateTipologiaUtente");
		}
		finally {
			if (connection!=null) {
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
				logger.debug("Connection chiusa");
			}
		}
		
		return updatedRows;
	}
	
	
	public Integer updateAbilitazioneUtente(String nick, boolean flagAbilitato) throws ClassNotFoundException, IOException{
		logger.debug("in updateAbilitazioneUtente");
		Integer updatedRows = -1;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try{
			
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE utente_registrato SET flag_abilitato = ? WHERE nick = ?";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setBoolean(1, flagAbilitato);
			pstmt.setString(2, nick);
				
			logger.debug("Updated Query: " + pstmt.toString());
			updatedRows = pstmt.executeUpdate();
						
			
			connection.commit();
			logger.info("Abilitazione Utente Registrato Aggiornato");
				
		}catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.debug("Rollback in updateAbilitazioneUtente");
		}
		finally {
			if (connection!=null){
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
		return updatedRows;
	}
	
	
	
	
	
	
	
	public Integer updatePassword(String nick, String psw) throws SQLException, ClassNotFoundException, IOException {
		logger.debug("in updatePassword");
		Integer updatedRows = -1;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE utente_registrato SET password = ? " +
					"WHERE nick = ?";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, psw);
			pstmt.setString(2, nick);
			
			logger.debug("Updated Query: " + pstmt.toString());
			updatedRows = pstmt.executeUpdate();
			
			connection.commit();
			logger.info("Password Utente Registrato Aggiornata");
			
		} catch (SQLException  e) {
			connection.rollback();
			logger.debug("Rollback in aggiornamenrìto Utente Registrato");
		}
		finally {
			if (connection!=null) {
				pstmt.close();
				connection.setAutoCommit(true);
				connection.close();
				logger.debug("Connection chiusa");
			}
		}
		
		return updatedRows;
			
	}
	
	
	public Integer insertOsservaInserzione(UtenteRegistrato utente, Inserzione inserzione) throws ClassNotFoundException, IOException{
		logger.debug("in Insert osserva inserzione");
	
		int insertRow = -1;
		Connection connection = null;
		PreparedStatement pstmt = null;
		try{
			
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
						
			String sql = "INSERT INTO utente_registrato_osserva_inserzione (utente_registrato_idutente, inserzione_idinserzione) VALUES (?, ?) ";
						
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, utente.getIdUtente());
			pstmt.setInt(2, inserzione.getIdInserzione());
			logger.debug("Select Query: " + pstmt.toString());
			insertRow = pstmt.executeUpdate();
			
			logger.debug("Inserimento completato in utente_registrato_osserva_inserzione");
						
			connection.commit();
			
		} catch (SQLException e) {
			logger.debug("Rollback in insert osserva inserzione");
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
					logger.debug("Connection chiusa");
				} catch (SQLException  e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return insertRow;
	}

	
	
	public List<Inserzione> getInserzioniOsservateByIdUtente(Integer idUtente) throws ClassNotFoundException, IOException{
		logger.debug("in getInserzioniOsservateByIdUtente");
		List<Inserzione> listaInserzioniOsservate = new ArrayList<Inserzione>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			connection.setAutoCommit(false);
			
			Inserzione inserzione;

			//Mi serve solo l'id dell'inserzione su cui chiamare il metodo inserzioneById
			String sql = "SELECT * FROM utente_registrato_osserva_inserzione " +
					"WHERE utente_registrato_osserva_inserzione.utente_registrato_idutente = ? " +
					"ORDER BY inserzione_idinserzione DESC "; 			
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtente);
			logger.debug("Select Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				inserzione = new InserzioneImpl();
				
				InserzioneDao dao = new InserzioneDaoMysqlJdbc();
				inserzione = dao.getInserzioneById(rs.getInt("inserzione_idinserzione"));
				
				listaInserzioniOsservate.add(inserzione);
				logger.debug("inserzione aggiunta alla lista");
				
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
		return listaInserzioniOsservate;
	}

	
	
	public Integer getNumeroInserzioniOsservateByIdUtente(Integer idUtente) throws ClassNotFoundException, IOException{
		logger.debug("in getNumeroInserzioniOsservateByIdUtente");
		Integer numeroInserzioni = 0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
	
			
			String sql = "SELECT COUNT(*) FROM utente_registrato_osserva_inserzione " +
					"WHERE utente_registrato_osserva_inserzione.utente_registrato_idutente = ? "; //Mi serve solo l'id dell'inserzione su cui chiamare il metodo inserzioneById
			
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtente);
			logger.debug("Select Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				numeroInserzioni = rs.getInt(1);
			}
						
			logger.debug("Connessione rilasciata");
		
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
		
		return numeroInserzioni;
	}
	
	
	public Boolean checkInserzioneOsservataByIdUtente(Integer idUtenteRegistrato, Integer idInserzione) throws ClassNotFoundException, IOException{
		logger.debug("in checkInserzioneOsservataByIdUtente");
		Boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			connection = DatabaseUtil.getConnection();
			
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM utente_registrato_osserva_inserzione " +
					"WHERE utente_registrato_osserva_inserzione.utente_registrato_idutente = ? " +
					"AND utente_registrato_osserva_inserzione.inserzione_idinserzione = ? "; 
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtenteRegistrato);
			pstmt.setInt(2, idInserzione);
			logger.debug("Select Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = true;
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
	
	
	
	
	public List<Inserzione> getInserzioniByIdUtenteAcquirente(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException {
		logger.debug("in getInserzioniByIdUtenteAcquirente");
		
		List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
		Inserzione inserzione;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DatabaseUtil.getConnection();
					
			String sql = "SELECT * FROM inserzione " +
					"WHERE acquirente_utente_registrato_idutente = ? ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtenteRegistrato);
			logger.debug("Select Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				
				inserzione = new InserzioneImpl();
				InserzioneDao dao = new InserzioneDaoMysqlJdbc();
				inserzione = dao.getInserzioneById(rs.getInt("idinserzione"));
				
				listaInserzioni.add(inserzione);
				logger.debug("inserzione aggiunta alla lista");
							
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
		return listaInserzioni;
		
	}
	
	
	public List<Inserzione> getInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException {
		logger.debug("in getInserzioniAggiudicateByIdUtenteAcquirente");
		
		List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
		Inserzione inserzione;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DatabaseUtil.getConnection();
					
			String sql = "SELECT * FROM inserzione " +
					"WHERE (inserzione.stato = 'aggiudicata' OR inserzione.stato = 'pagata') " +
					"AND acquirente_utente_registrato_idutente = ? " +
					"ORDER BY inserzione.idinserzione DESC ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtenteRegistrato);
			logger.debug("Select Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				
				inserzione = new InserzioneImpl();
				InserzioneDao dao = new InserzioneDaoMysqlJdbc();
				inserzione = dao.getInserzioneById(rs.getInt("idinserzione"));
				
				listaInserzioni.add(inserzione);
				logger.debug("inserzione aggiunta alla lista");
							
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
		return listaInserzioni;
		
	}
	
	
	
	public Integer getNumeroInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException {
		logger.debug("in getLimitInserzioniAggiudicateByIdUtenteAcquirente");
		
		Integer numeroInserzioni = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = DatabaseUtil.getConnection();
		
			
			String sql = "SELECT COUNT(*) FROM inserzione " +
					"WHERE (inserzione.stato = 'aggiudicata' OR inserzione.stato = 'pagata') " +
					"AND acquirente_utente_registrato_idutente = ? ";
			
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, idUtenteRegistrato);
				
			logger.debug("Select Query: " + pstmt.toString());
			rs = pstmt.executeQuery();
				
			if(rs.next()){
				
				numeroInserzioni = rs.getInt(1);
				
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
		return numeroInserzioni;
	}
	
	
	public List<Inserzione> getInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException {
		logger.debug("in getInserzioniByIdUtenteVenditore");
			
			List<Inserzione> listaInserzioni = new ArrayList<Inserzione>();
			Inserzione inserzione;
			
			Connection connection = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				
				connection = DatabaseUtil.getConnection();
						
				String sql = "SELECT * FROM inserzione " +
						"WHERE venditore_utente_registrato_idutente = ? ";
				
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, idUtenteRegistrato);
				logger.debug("Select Query: " + pstmt.toString());
				rs = pstmt.executeQuery();
				while(rs.next()){
					
					inserzione = new InserzioneImpl();
					InserzioneDao dao = new InserzioneDaoMysqlJdbc();
					inserzione = dao.getInserzioneById(rs.getInt("idinserzione"));
					
					listaInserzioni.add(inserzione);
					logger.debug("inserzione aggiunta alla lista");
								
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
			return listaInserzioni;
		}
	
	
		
	public List<String> getNick() throws ClassNotFoundException, IOException{
		logger.debug("in getNick");
		List<String> listaNick = new ArrayList<String>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			connection = DatabaseUtil.getConnection();
						
			String sql = "SELECT DISTINCT(nick) FROM utente_registrato";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			logger.debug("Select query: " + pstmt.toString());
			
			while(rs.next()){
				listaNick.add(rs.getString("nick"));
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
		return listaNick;
	}
	
	
	
	
	
	


	

}
