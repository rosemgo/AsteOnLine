package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public interface UtenteRegistratoDao {
		
	/**
	 * Verifico se l'inserzione passata come parametro è già osservata dall'utente passato come parametro
	 * @param idUtenteRegistrato
	 * @param idInserzione
	 * @return
	*/
	public Boolean checkInserzioneOsservataByIdUtente(Integer idUtenteRegistrato, Integer idInserzione);


	/**
	 * Inserisce un nuovo utente nel database.
	 * @param utente
	 * @return l'id dell'utente inserito, oppure -1 in caso di errore
	 * @throws SQLException
	 */
	public Integer insertUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Questo metodo effettua un inserimento nella tabella utente_registrato_osserva_inserzione, quando un utente osserva un'inserzione.
	 * 
	 * @param utente
	 * @param inserzione
	 * @return il numero di righe inserite(ossia 1)
	 */
	public Integer insertOsservaInserzione(UtenteRegistrato utente, Inserzione inserzione);


	/**
	 * In realtà questo metodo non elimina l'utente, bensì ne setta solo il flag abilitato a 0, in modo tale che l'utente 
	 * non risulta più abilitato alle funzioni di un utente registrato
	 * 
	 * @param utente
	 * @return numero di righe cancellate
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer deleteUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;
			
	
	public Integer updateUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;

	
	
	
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
	 */
	public Integer updateStatoUtente(Integer idUtente, Boolean flagAbilitato);
	
	/**
	 * Modifica la tipologia di un Utente (venditore o acquirente)
	 * 
	 * @param nick identificativo univoco dell'utente da modificare 
	 * @param tipologiaCliente stato valore dello stato da settare
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
	 * @param flagAbilitato
	 * 
	 * @return il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 */
	public Integer updateAbilitazioneUtente(String nick, boolean flagAbilitato);
		
	
	/**
	 * Modifica la password dell'utente 
	 * 
	 * @param nick
	 * @param psw 
	 * @return il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 * 
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
	 */
	public UtenteRegistrato getUtenteRegistratoByeMail(String eMail);


	/**
	 * Restituisce l'utente corrispondente all'id passato come paramentro
	 * @param idUtente
	 * @return l'utente
	  */
	public UtenteRegistrato getUtenteRegistratoById(Integer idUtente);


	/**
	 * Questo metodo crea una lista delle inserzioni osservate da un utente passato come parametro
	 * 
	 * @param idUtente
	 * @return lista delle inserzioni osservate dall' utente registrato
	 */
	public List<Inserzione> getInserzioniOsservateByIdUtente(Integer idUtente);

	
	/**
	 * Visualizza il numero delle inserzioni osservate dall'utente passato come parametro
	 * 
	 * @param idUtente
	 * 
	 * @return numero inserzioni
	 */
	public Integer getNumeroInserzioniOsservateByIdUtente(Integer idUtente);
	
	/**
	 * Visualizza tutti i dati relativi alle inserzioni(acquistate) dell'acquirente
	 * 
	 * @param idUtenteRegistrato identificatore univoco dell'acquirente
	 * 
	 * @return lista di prodotti relativi all'acquirente
	 */
	public List<Inserzione> getInserzioniByIdUtenteAcquirente(Integer idUtenteRegistrato);
	
	
	/**
	 * Visualizzo tutte le inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * @return lista inserzioni aggiudicate
	*/
	public List<Inserzione> getInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato);
		
	
	/**
	 * Visualizza il numero delle inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * @return
	 */
	public Integer getNumeroInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato);
		
	/**
	 * Visualizza tutti i dati relativi alle inserzioni del venditore
	 * 
	 * @param idUtenteRegistrato identificatore univoco del venditore
	 * 
	 * @return lista di prodotti relativi al venditore
	 */
	public List<Inserzione> getInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato);
		
	/**
	 * Ottiene tutti i nickname degli utenti presenti nel database
	 * 
	 * @return lista di tutti i nickname degli utenti
	 */
	public List<String> getNick();
	
	/**
	 * Visualizza il numero delgli utenti presenti nel db
	 * 
	 * @return numero totale utenti
	 */
	public Integer getNumeroUtenti();


	/**
	 * Restituisce l'utente corrispondente al nick passato come paramentro.
	 * 
	 * @param nick
	 * @return l'utente registrato
	 */
	public UtenteRegistrato getUtenteRegistratoByNick(String nick);
	
	
	/**
	 * Ricerca nel database l'utente corrispondente al nickname passato come parametro, e se presente lo restituisce. 
	 * 
	 * @param nickName identificativo dell'utente.
	 * @return restituisce l'utente se il nick è esistente, null altrimenti.
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
	 */
	public boolean controllaPagamenti(String nick);
	
	
	
	/**
	 * Controlla se l'utente definito da nick ha delle inserzioni scadute
	 * 
	 * @param nick
	 * 
	 * @return true se l'utente venditore ha almeno un'inserzione che è scaduta e quindi può cancellarla oppure ripubblicarla
	 */
	public boolean controllaProdottiScaduti(String nick);
	
	
	/**
	 * Controlla se la mail è già presente nel db.
	 * 
	 * @param eMail identificativo dell'utente.
	 * @return restituisce true se il nick è esistente, false altrimenti.
	 */
	public boolean controlloeMail(String eMail);
	
	
	/**
	 * Controlla se il nick è già presente nel db.
	 * 
	 * @param nickName identificativo dell'utente.
	 * @return restituisce true se il nick è esistente, false altrimenti.
	 */
	public boolean controlloNick(String nickName);
	
	
	/**
	 * Il metodo viene utilizzato per prelevare dal db tutte le aste(inserzioni) a cui l'utente sta partecipando
	 * 
	 * @param idUtente
	 * @return la lista delle aste corrente a cui l'utente partecipa
	 */
	public List<Inserzione> getMieAsteInCorsoByIdUtente(Integer idUtente);
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * 
	 * @return il numero delle inserzioni
	 */
	public Integer getNumeroInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato);
	
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente cercate per titolo
	 * 
	 * @param idUtenteRegistrato
	 * @param titoloInserzione
	 * @return il numero delle inserzioni con quel titolo
	 */
	public Integer getNumeroLeMieInserzioniPerTitolo(Integer idUtenteRegistrato, String titoloInserzione);
	
	
	/**
	 * Visualizza il numero delle aste a cui l'utente, passato come parametro, sta partecipando
	 * 
	 * @param idUtenteRegistrato
	 * @return numero inserzioni
	 */
	public Integer getNumeroMieAsteInCorsoByIdUtente(Integer idUtenteRegistrato);
	
	
	
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
	 * Controlla se l'utente è abilitato alle funzioni di utente registrato
	 * 
	 * @param nick
	 * 
	 * @return true se l'utente è abilitato
	 */
	public boolean isUtenteAbilitato(String nick);
	
	/**
	 * Controlla se l'utente è già registrato nel database
	 * 
	 * @param codiceFiscale
	 * @param tipologiaUtente
	 * 
	 * @return true se l'utente si è già registrato
	 */
	public boolean isUtenteRegistrato(String codiceFiscale, String tipologiaUtente);
	
	
	/**
	 * Visualizza le inserzioni caricate dall'utente, passato come parametro, relative ad un intervallo (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtenteRegistrato
	 * @param limiteInf
	 * @param numInserzioniPagina
	 * @return le inserzioni comprese in un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato, Integer limiteInf,  Integer numInserzioniPagina);

	
	/**
	 * Visualizza le inserzioni osservate relative ad un intervallo (utilizzato nella paginazione delle inserzioni)
	 * @param idUtente
	 * @return
	 */
	public List<Inserzione> getLimitInserzioniOsservateByIdUtente(Integer idUtente, Integer limiteInf, Integer numeroInserzioniPagina);
		

	/**
	 * Visualizzo le inserzioni, relative ad un intervallo, aggiudicate dall'utente passato come parametro (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtenteRegistrato
	 * @param limiteInf
	 * @param numeroInserzioniPagina
	 * @return lista d inserzioni
	 */
	public List<Inserzione> getLimitInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato, Integer limiteInf, Integer numeroInserzioniPagina);

	
}

