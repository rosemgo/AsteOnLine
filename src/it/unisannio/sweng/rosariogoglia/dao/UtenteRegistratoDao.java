package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public interface UtenteRegistratoDao {
		
	/**
	 * Verifico se l'inserzione passata come parametro � gi� osservata dall'utente passato come parametro
	 * @param idUtenteRegistrato
	 * @param idInserzione
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Boolean checkInserzioneOsservataByIdUtente(Integer idUtenteRegistrato, Integer idInserzione) throws ClassNotFoundException, IOException;


	/**
	 * Inserisce un nuovo utente nel database.
	 * @param utente
	 * @return l'id dell'utente inserito, oppure -1 in caso di errore
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer insertUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, IOException, SQLException;
	
	/**
	 * Questo metodo effettua un inserimento nella tabella utente_registrato_osserva_inserzione, quando un utente osserva un'inserzione.
	 * 
	 * @param utente
	 * @param inserzione
	 * @return il numero di righe inserite(ossia 1)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer insertOsservaInserzione(UtenteRegistrato utente, Inserzione inserzione) throws ClassNotFoundException, IOException;


	/**
	 * In realt� questo metodo non elimina l'utente, bens� ne setta solo il flag abilitato a 0, in modo tale che l'utente 
	 * non risulta pi� abilitato alle funzioni di un utente registrato
	 * @param utente
	 * @return numero di righe cancellate
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer deleteUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, IOException, SQLException;
	
	
	public Integer updateUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, IOException, SQLException;

	/**
	 * Modifica i dati dell'utente dato il nickname
	 * 
	 * @param nick
	 * @param nome
	 * @param cognome
	 * @param indirizzo
	 * @param idComune
	 * @param cap
	 * @param telefono
	 * @param nContoCorrente
	 * @param eMail
	 * @return il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Integer updateDatiPersonali(String nick, String nome, String cognome, String codFiscale, String indirizzo, Integer idComune, String cap, String telefono, String nContoCorrente, String eMail) throws SQLException, ClassNotFoundException, IOException;
		
	/**
	 * Modifica lo stato di un utente (abilitato o disabilitato)
	 * 
	 * @param idUtente
	 * @param flagAbilitato
	 * @return 1 in caso di aggiornamento, -1 in caso di aggiornamento non riuscito
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateStatoUtente(Integer idUtente, Boolean flagAbilitato) throws ClassNotFoundException, IOException;
	
	/**
	 * Modifica la tipologia di un Utente (venditore o acquirente)
	 * 
	 * @param nick identificativo univoco dell'utente da modificare 
	 * @param stato valore dello stato da settare
	 * 
	 * @return il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateTipologiaUtente(String nick, String tipologiaCliente) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Modifica l'abilitazione di Utente (abilitato all'uso dell'applicazione o meno)
	 * 
	 * @param nick identificativo univoco dell'utente da modificare 
	 * @param flag abilitato
	 * 
	 * @return il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateAbilitazioneUtente(String nick, boolean flagAbilitato) throws ClassNotFoundException, IOException;
		
	
	/**
	 * Modifica la password dell'utente 
	 * 
	 * @param nick
	 * @param psw 
	 * 
	 * @return il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updatePassword(String nick, String psw) throws SQLException, ClassNotFoundException, IOException;
	
	/**
	 * Restituisce l'utente corrispondente alla mail passata come paramentro
	 * 
	 * @param eMail
	 * 
	 * @return l'utente corrispondente alla mail passata come paramentro
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public UtenteRegistrato getUtenteRegistratoByeMail(String eMail) throws ClassNotFoundException, IOException;


	/**
	 * Restituisce l'utente corrispondente all'id passato come paramentro
	 * @param idUtente
	 * @return l'utente
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public UtenteRegistrato getUtenteRegistratoById(Integer idUtente) throws ClassNotFoundException, IOException;


	/**
	 * Questo metodo crea una lista delle inserzioni osservate da un utente passato come parametro
	 * 
	 * @param utente
	 * @return lista delle inserzioni osservate dall' utente registrato
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException
	 */
	public List<Inserzione> getInserzioniOsservateByIdUtente(Integer idUtente) throws ClassNotFoundException, IOException;
	

	
	
	
	/**
	 * Visualizza il numero delle inserzioni osservate dall'utente passato come parametro
	 * 
	 * @param idUtente
	 * 
	 * @return numero inserzioni
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer getNumeroInserzioniOsservateByIdUtente(Integer idUtente) throws ClassNotFoundException, IOException;
	
	/**
	 * Visualizza tutti i dati relativi alle inserzioni(acquistate) dell'acquirente
	 * 
	 * @param idUtenteRegistrato identificatore univoco dell'acquirente
	 * 
	 * @return lista di prodotti relativi all'acquirente
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Inserzione> getInserzioniByIdUtenteAcquirente(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException;
	
	/**
	 * Visualizzo tutte le inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * @return lista inserzioni aggiudicate
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Inserzione> getInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException;
		
	
	/**
	 * Visualizza il numero delle inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * @param limiteInf
	 * @param numeroInserzioniPagina
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer getNumeroInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException;
		
	/**
	 * Visualizza tutti i dati relativi alle inserzioni del venditore
	 * 
	 * @param idUtenteRegistrato identificatore univoco del venditore
	 * 
	 * @return lista di prodotti relativi al venditore
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Inserzione> getInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException;
		
	/**
	 * Ottiene tutti i nickname degli utenti presenti nel database
	 * 
	 * @return lista di tutti i nickname degli utenti
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<String> getNick() throws ClassNotFoundException, IOException;
	
	/**
	 * Visualizza il numero delgli utenti presenti nel db
	 * 
	 * @return numero totale utenti
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer getNumeroUtenti() throws ClassNotFoundException, IOException;


	/**
	 * Restituisce l'utente corrispondente al nick passato come paramentro.
	 * 
	 * @param idUtente
	 * @return l'utente
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public UtenteRegistrato getUtenteRegistratoByNick(String nick) throws ClassNotFoundException, IOException;
	
	
	/**
	 * Ricerca nel database l'utente corrispondente al nickname passato come parametro, e se presente lo restituisce. 
	 * 
	 * @param nickName identificativo dell'utente.
	 * @return restituisce l'utente se il nick � esistente, null altrimenti.
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public UtenteRegistrato checkUtente(String nickName) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Controlla se l'utente definito da nick ha delle inserzioni aggiudicate da pagare
	 * 
	 * @param nick
	 * 
	 * @return true se l'utente ha almeno un' inserzione aggiudicata da pagare
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean controllaPagamenti(String nick) throws ClassNotFoundException, IOException;
	
	
	
	/**
	 * Controlla se l'utente definito da nick ha delle inserzioni scadute
	 * 
	 * @param nick
	 * 
	 * @return true se l'utente venditore ha almeno un'inserzione che � scaduta e quindi pu� cancellarla oppure ripubblicarla
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean controllaProdottiScaduti(String nick) throws ClassNotFoundException, IOException;
	
	
	/**
	 * Controlla se la mail � gi� presente nel db.
	 * 
	 * @param nickName identificativo dell'utente.
	 * @return restituisce true se il nick � esistente, false altrimenti.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 * 
	 */
	public boolean controlloeMail(String eMail) throws ClassNotFoundException, IOException;
	
	
	/**
	 * Controlla se il nick � gi� presente nel db.
	 * 
	 * @param nickName identificativo dell'utente.
	 * @return restituisce true se il nick � esistente, false altrimenti.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 * 
	 */
	public boolean controlloNick(String nickName) throws ClassNotFoundException, IOException;
	
	
	/**
	 * Il metodo viene utilizzato per prelevare dal db tutte le aste(inserzioni) a cui l'utente sta partecipando
	 * 
	 * @param idUtente
	 * @return la lista delle aste corrente a cui l'utente partecipa
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Inserzione> getMieAsteInCorsoByIdUtente(Integer idUtente) throws ClassNotFoundException, IOException;
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * 
	 * @return il numero delle inserzioni
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer getNumeroInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException;
	
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente cercate per titolo
	 * 
	 * @param idUtenteRegistrato
	 * @param titoloInserzione
	 * @return il numero delle inserzioni con quel titolo
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer getNumeroLeMieInserzioniPerTitolo(Integer idUtenteRegistrato, String titoloInserzione) throws ClassNotFoundException, IOException;
	
	
	/**
	 * Visualizza il numero delle aste a cui l'utente, passato come parametro, sta partecipando
	 * 
	 * @param idUtenteRegistrato
	 * @return numero inserzioni
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer getNumeroMieAsteInCorsoByIdUtente(Integer idUtenteRegistrato) throws ClassNotFoundException, IOException;
	
	
	
	/**
	 * Preleva tutti gli utenti registrati nel database.
	 * 
	 * @return lista di utenti
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<UtenteRegistrato> getUtenti() throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Controlla se l'utente � abilitato alle funzioni di utente registrato
	 * 
	 * @param nick
	 * 
	 * @return true se l'utente � abilitato
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public boolean isUtenteAbilitato(String nick) throws ClassNotFoundException, IOException;
	
	/**
	 * Controlla se l'utente � gi� registrato nel database
	 * 
	 * @param codiceFiscale
	 * @param tipologiaUtente
	 * 
	 * @return true se l'utente si � gi� registrato
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public boolean isUtenteRegistrato(String codiceFiscale, String tipologiaUtente) throws ClassNotFoundException, IOException;
	
	
	
}

