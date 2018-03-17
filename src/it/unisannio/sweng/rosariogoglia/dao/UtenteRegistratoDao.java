package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public interface UtenteRegistratoDao {
		
	/**
	 * Verifico se l'inserzione passata come parametro � gi� osservata dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * @param idInserzione
	 * 
	 * @return True se l'inserzione � gi� osservata dall'utente,false altrimenti
	*/
	public Boolean checkInserzioneOsservataByIdUtente(Integer idUtenteRegistrato, Integer idInserzione);


	/**
	 * Inserisce un nuovo utente nel database
	 * 
	 * @param utente
	 * 
	 * @return L'id dell'utente inserito, oppure -1 in caso di errore
	 * 
	 * @throws SQLException
	 */
	public Integer insertUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Questo metodo effettua un inserimento nella tabella utente_registrato_osserva_inserzione, quando un utente osserva un'inserzione.
	 * 
	 * @param utente
	 * @param inserzione
	 * 
	 * @return il numero di righe inserite(ossia 1)
	 */
	public Integer insertOsservaInserzione(UtenteRegistrato utente, Inserzione inserzione);


	/**
	 * In realt� questo metodo non elimina l'utente, bens� ne setta solo il flag abilitato a 0, in modo tale che l'utente 
	 * non risulta pi� abilitato alle funzioni di un utente registrato
	 * 
	 * @param utente
	 * 
	 * @return Numero di righe cancellate
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer deleteUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;
			
	/**
	 * Questo metodo aggiorna uno o pi� attributi dell'utente passato come parametro
	 * 
	 * @param utente
	 * 
	 * @return 1 se l'aggiornamento ha successo,-1 se l'aggiornamento fallisce
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public Integer updateUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;

	
	/**
	 * Visualizza le inserzioni caricate dall'utente, passato come parametro, e con parte del titolo, passato come parametro, relative ad un intervallo (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtenteRegistrato
	 * @param limiteInf
	 * 
	 * @param numInserzioniPagina
	 * 
	 * @return Le inserzioni comprese in un intervallo specifico
	 */
	public List<Inserzione> getLimitLeMieInserzioniPerTitolo(Integer idUtenteRegistrato, String titoloInserzione, Integer limiteInf, Integer numInserzioniPagina);

	
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
	 * 
	 * @return Il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
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
	 * 
	 * @return 1 in caso di aggiornamento, -1 in caso di aggiornamento non riuscito
	 */
	public Integer updateStatoUtente(Integer idUtente, Boolean flagAbilitato);
	
	/**
	 * Modifica la tipologia di un Utente (venditore o acquirente)
	 * 
	 * @param nick identificativo univoco dell'utente da modificare 
	 * @param tipologiaCliente stato valore dello stato da settare
	 * 
	 * @return Il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 * 
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
	 * @return Il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 */
	public Integer updateAbilitazioneUtente(String nick, boolean flagAbilitato);
		
	
	/**
	 * Modifica la password dell'utente 
	 * 
	 * @param nick
	 * @param psw 
	 * 
	 * @return Il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
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
	 * @return L'utente corrispondente alla mail passata come paramentro
	 */
	public UtenteRegistrato getUtenteRegistratoByeMail(String eMail);


	/**
	 * Restituisce l'utente corrispondente all'id passato come paramentro
	 * 
	 * @param idUtente
	 * 
	 * @return L'utente corrispondente all'id passato come parametro
	  */
	public UtenteRegistrato getUtenteRegistratoById(Integer idUtente);


	/**
	 * Questo metodo crea una lista delle inserzioni osservate da un utente passato come parametro
	 * 
	 * @param idUtente
	 * 
	 * @return Lista delle inserzioni osservate dall' utente registrato
	 */
	public List<Inserzione> getInserzioniOsservateByIdUtente(Integer idUtente);

	
	/**
	 * Visualizza il numero delle inserzioni osservate dall'utente passato come parametro
	 * 
	 * @param idUtente
	 * 
	 * @return Numero delle inserzioni osservate dall'utente passato come parametro
	 */
	public Integer getNumeroInserzioniOsservateByIdUtente(Integer idUtente);
	
	/**
	 * Visualizza tutti i dati relativi alle inserzioni(acquistate) dell'acquirente
	 * 
	 * @param idUtenteRegistrato identificatore univoco dell'acquirente
	 * 
	 * @return Lista di prodotti relativi all'acquirente
	 */
	public List<Inserzione> getInserzioniByIdUtenteAcquirente(Integer idUtenteRegistrato);
	
	
	/**
	 * Visualizzo tutte le inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * 
	 * @return Lista inserzioni aggiudicate
	*/
	public List<Inserzione> getInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato);
		
	
	/**
	 * Visualizza il numero delle inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * 
	 * @return Il numero delle inserzioni che l'utente passato come parametro si � aggiudicato
	 */
	public Integer getNumeroInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato);
		
	/**
	 * Visualizza tutti i dati relativi alle inserzioni del venditore
	 * 
	 * @param idUtenteRegistrato identificatore univoco del venditore
	 * 
	 * @return Lista di prodotti relativi al venditore
	 */
	public List<Inserzione> getInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato);
		
	/**
	 * Ottiene tutti i nickname degli utenti presenti nel database
	 * 
	 * @return Lista di tutti i nickname degli utenti
	 */
	public List<String> getNick();
	
	/**
	 * Visualizza il numero delgli utenti presenti nel db
	 * 
	 * @return Numero totale utenti
	 */
	public Integer getNumeroUtenti();


	/**
	 * Restituisce l'utente corrispondente al nick passato come paramentro
	 * 
	 * @param nick
	 * 
	 * @return L'utente registrato
	 */
	public UtenteRegistrato getUtenteRegistratoByNick(String nick);
	
	
	/**
	 * Ricerca nel database l'utente corrispondente al nickname passato come parametro, e se presente lo restituisce
	 * 
	 * @param nickName identificativo dell'utente
	 * 
	 * @return Restituisce l'utente se il nick � esistente, null altrimenti
	 * 
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
	 * @return True se l'utente venditore ha almeno un'inserzione che � scaduta e quindi pu� cancellarla oppure ripubblicarla
	 */
	public boolean controllaProdottiScaduti(String nick);
	
	
	/**
	 * Controlla se la mail � gi� presente nel db.
	 * 
	 * @param eMail identificativo dell'utente.
	 * 
	 * @return Restituisce true se il nick � esistente, false altrimenti.
	 */
	public boolean controlloeMail(String eMail);
	
	
	/**
	 * Controlla se il nick � gi� presente nel db.
	 * 
	 * @param nickName identificativo dell'utente.
	 * 
	 * @return Restituisce true se il nick � esistente, false altrimenti.
	 */
	public boolean controlloNick(String nickName);
	
	
	/**
	 * Il metodo viene utilizzato per prelevare dal db tutte le aste(inserzioni) a cui l'utente sta partecipando
	 * 
	 * @param idUtente
	 * 
	 * @return La lista delle aste corrente a cui l'utente partecipa
	 */
	public List<Inserzione> getMieAsteInCorsoByIdUtente(Integer idUtente);
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato
	 * 
	 * @return Il numero delle inserzioni
	 */
	public Integer getNumeroInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato);
	
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente cercate per titolo
	 * 
	 * @param idUtenteRegistrato
	 * @param titoloInserzione
	 * 
	 * @return Il numero delle inserzioni con quel titolo
	 */
	public Integer getNumeroLeMieInserzioniPerTitolo(Integer idUtenteRegistrato, String titoloInserzione);
	
	
	/**
	 * Visualizza il numero delle aste a cui l'utente, passato come parametro, sta partecipando
	 * 
	 * @param idUtenteRegistrato
	 * @return Numero inserzioni
	 */
	public Integer getNumeroMieAsteInCorsoByIdUtente(Integer idUtenteRegistrato);
	
	
	
	/**
	 * Preleva tutti gli utenti registrati nel database.
	 * 
	 * @return Lista degli utenti registrati
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
	 * @return True se l'utente � abilitato
	 */
	public boolean isUtenteAbilitato(String nick);
	
	/**
	 * Controlla se l'utente � gi� registrato nel database
	 * 
	 * @param codiceFiscale
	 * @param tipologiaUtente
	 * 
	 * @return True se l'utente si � gi� registrato
	 */
	public boolean isUtenteRegistrato(String codiceFiscale, String tipologiaUtente);
	
	
	/**
	 * Visualizza le inserzioni caricate dall'utente, passato come parametro, relative ad un intervallo (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtenteRegistrato
	 * @param limiteInf
	 * @param numInserzioniPagina
	 * 
	 * @return Le inserzioni comprese in un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato, Integer limiteInf,  Integer numInserzioniPagina);

	
	/**
	 * Visualizza le inserzioni osservate relative ad un intervallo (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtente
	 * 
	 * @return Restituisce le inserzioni osservate relative ad un certo intervallo
	 */
	public List<Inserzione> getLimitInserzioniOsservateByIdUtente(Integer idUtente, Integer limiteInf, Integer numeroInserzioniPagina);
		

	/**
	 * Visualizzo le inserzioni, relative ad un intervallo, aggiudicate dall'utente passato come parametro (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtenteRegistrato
	 * @param limiteInf
	 * @param numeroInserzioniPagina
	 * 
	 * @return Lista d inserzioni
	 */
	public List<Inserzione> getLimitInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato, Integer limiteInf, Integer numeroInserzioniPagina);

			
	/**
	 * Restituisce gli utenti relativi ad un intervallo specifico(utilizzato nella paginazione)
	 * 
	 * @return Lista degli utenti relativi ad un certo intevallo
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<UtenteRegistrato> getLimitUtenti(Integer limiteInf, Integer numUtentiPerPagina);


	/**
	 * Visualizza le aste a cui l'utente, passato come parametro, sta partecipando (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtente
	 * @param limiteInf
	 * @param numInserzioniPagina
	 * 
	 * @return La lista delle aste a cui l'utente avente l'id passato come parametro sta partecipando
	 */
	public List<Inserzione> getLimitMieAsteInCorsoByIdUtente(Integer idUtente, Integer limiteInf, Integer numInserzioniPagina);


}

