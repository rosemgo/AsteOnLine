package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public interface UtenteRegistratoDao {
		
	/**
	 * Verifico se l'inserzione passata come parametro è già osservata dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'utente registrato
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return True se l'inserzione è già osservata dall'utente,false altrimenti
	*/
	public Boolean checkInserzioneOsservataByIdUtente(Integer idUtenteRegistrato, Integer idInserzione);


	/**
	 * Inserisce un nuovo utente nel database
	 * 
	 * @param utente oggetto utente da inserire nel database
	 * 
	 * @return L'id dell'utente inserito, oppure -1 in caso di errore
	 * 
	 * @throws SQLException
	 */
	public Integer insertUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;

//	/**
//	 * Inserisce un nuovo utente nel database
//	 * 
//	 * Usato per test con connessione DatabaseUtil
//	 * 
//	 * @param utente oggetto utente da inserire nel database
//	 * 
//	 * @return L'id dell'utente inserito, oppure -1 in caso di errore
//	 * 
//	 * @throws SQLException
//	 */
//	public Integer insertUtenteRegistratoTest(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;

	
	
	/**
	 * Questo metodo effettua un inserimento nella tabella utente_registrato_osserva_inserzione, quando un utente osserva un'inserzione.
	 * 
	 * @param utente oggetto utente che osserva un'inserzione
	 * @param inserzione oggetto inserzione osservata
	 * 
	 * @return il numero di righe inserite(ossia 1)
	 * 
	 */
	
	
	public Integer insertOsservaInserzione(UtenteRegistrato utente, Inserzione inserzione);


	/**
	 * In realtà questo metodo non elimina l'utente, bensì ne setta solo il flag abilitato a 0, in modo tale che l'utente 
	 * non risulta più abilitato alle funzioni di un utente registrato
	 * 
	 * @param utente da disabilitare nel database
	 * 
	 * @return Numero di righe aggiornate
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer deleteUtenteRegistrato(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;
			
//	/**
//	 * In realtà questo metodo non elimina l'utente, bensì ne setta solo il flag abilitato a 0, in modo tale che l'utente 
//	 * non risulta più abilitato alle funzioni di un utente registrato
//	 * 
//	 * Usato per test con connessione DatabaseUtil
//	 * 
//	 * @param utente da disabilitarenel database
//	 * 
//	 * @return Numero di righe aggiornate
//	 * 
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 * @throws IOException
//	 */
//	public Integer deleteUtenteRegistratoTest(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException;
			
	/**
	 * Metodo per eliminare l'utente, usato solo in fase di test perchè l'applicazione non prevede funzionalità per l'eliminazione di un utente.
	 * 
	 * Usato per test con connessione DatabaseUtil
	 * 
	 * @param utente da elinimare nel database
	 * 
	 * @return Numero di righe cancellate
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */	
	public Integer removeUtenteRegistratoTest(UtenteRegistrato utente) throws ClassNotFoundException, SQLException, IOException; 

	/**
	 * Questo metodo aggiorna uno o più attributi dell'utente passato come parametro
	 * 
	 * @param utente oggetto utente da aggiornare nel database
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
	 * @param idUtenteRegistrato numero identificativo dell'utente registrato
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return Le inserzioni comprese in un intervallo specifico
	 */
	public List<Inserzione> getLimitLeMieInserzioniPerTitolo(Integer idUtenteRegistrato, String titoloInserzione, Integer limiteInf, Integer numInserzioniPagina);

	
	/**
	 * Modifica i dati dell'utente dato il nickname
	 * 
	 * @param nick nickname dell'utente
	 * @param nome nome dell'utente
	 * @param cognome cognome dell'utente
	 * @param indirizzo indirizzo dell'utente
	 * @param idComune numero identificativo del comune
	 * @param cap codice avviamento postale dell'utente
	 * @param telefono numero di telefono dell'utente
	 * @param nContoCorrente numero di conto corrente dell'utente
	 * @param eMail indirizzo e-mail dell'utente
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
	 * @param idUtente numero identificativo dell'utente
	 * @param flagAbilitato flag che indica se l'utente è abilitato o meno
	 * 
	 * @return 1 in caso di aggiornamento, -1 in caso di aggiornamento non riuscito
	 */
	public Integer updateStatoUtente(Integer idUtente, Boolean flagAbilitato);
	
	/**
	 * Modifica la tipologia di un Utente (venditore o acquirente)
	 * 
	 * @param nick numero identificativo dell'utente da modificare 
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
	 * @param flagAbilitato flag che indica se l'utente è abilitato o meno
	 * 
	 * @return Il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 */
	public Integer updateAbilitazioneUtente(String nick, boolean flagAbilitato);
		
	
	/**
	 * Modifica la password dell'utente 
	 * 
	 * @param nick identificativo univoco dell'utente
	 * @param psw password da modificare
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
	 * @param eMail indirzzo e-mail dell'utente
	 * 
	 * @return L'utente corrispondente alla mail passata come paramentro
	 */
	public UtenteRegistrato getUtenteRegistratoByeMail(String eMail);


	/**
	 * Restituisce l'utente corrispondente all'id passato come paramentro
	 * 
	 * @param idUtente numero identificativo dell'utente
	 * 
	 * @return L'utente corrispondente all'id passato come parametro
	  */
	public UtenteRegistrato getUtenteRegistratoById(Integer idUtente);


//	/**
//	 * Restituisce l'utente corrispondente all'id passato come paramentro
//	 * 
//	 * Usato per test con connessione DatabaseUtil
//	 * 
//	 * @param idUtente numero identificativo dell'utente
//	 * 
//	 * @return L'utente corrispondente all'id passato come parametro
//	  */
//	public UtenteRegistrato getUtenteRegistratoByIdTest(Integer idUtente);
	

	/**
	 * Questo metodo crea una lista delle inserzioni osservate da un utente passato come parametro
	 * 
	 * @param idUtente numero identificativo dell'utente
	 * 
	 * @return Lista delle inserzioni osservate dall' utente registrato
	 */
	public List<Inserzione> getInserzioniOsservateByIdUtente(Integer idUtente);

	
	/**
	 * Visualizza il numero delle inserzioni osservate dall'utente passato come parametro
	 * 
	 * @param idUtente numero identificativo dell'utente
	 * 
	 * @return Numero delle inserzioni osservate dall'utente passato come parametro
	 */
	public Integer getNumeroInserzioniOsservateByIdUtente(Integer idUtente);
	
	/**
	 * Visualizza tutti i dati relativi alle inserzioni(acquistate) dell'acquirente
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'acquirente
	 * 
	 * @return Lista di prodotti relativi all'acquirente
	 */
	public List<Inserzione> getInserzioniByIdUtenteAcquirente(Integer idUtenteRegistrato);
	
	
	/**
	 * Visualizzo tutte le inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'acquirente
	 * 
	 * @return Lista inserzioni aggiudicate
	*/
	public List<Inserzione> getInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato);
		
	
	/**
	 * Visualizza il numero delle inserzioni aggiudicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'acquirente
	 * 
	 * @return Il numero delle inserzioni che l'utente passato come parametro si è aggiudicato
	 */
	public Integer getNumeroInserzioniAggiudicateByIdUtenteAcquirente(Integer idUtenteRegistrato);
		
	/**
	 * Visualizza tutti i dati relativi alle inserzioni del venditore
	 * 
	 * @param idUtenteRegistrato numero identificativo del venditore
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
	 * @param nick identificativo univoco dell'utente
	 * 
	 * @return L'utente registrato
	 */
	public UtenteRegistrato getUtenteRegistratoByNick(String nick);
	
	
	/**
	 * Ricerca nel database l'utente corrispondente al nickname passato come parametro, e se presente lo restituisce
	 * 
	 * @param nickName identificativo dell'utente
	 * 
	 * @return Restituisce l'utente se il nick è esistente, null altrimenti
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public UtenteRegistrato checkUtente(String nickName) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Controlla se l'utente definito da nick ha delle inserzioni aggiudicate da pagare
	 * 
	 * @param nick identificativo univoco dell'utente
	 * 
	 * @return true se l'utente ha almeno un' inserzione aggiudicata da pagare
	 */
	public boolean controllaPagamenti(String nick);
	
	
	
	/**
	 * Controlla se l'utente definito da nick ha delle inserzioni scadute
	 * 
	 * @param nick identificativo univoco dell'utente
	 * 
	 * @return True se l'utente venditore ha almeno un'inserzione che è scaduta e quindi può cancellarla oppure ripubblicarla
	 */
	public boolean controllaProdottiScaduti(String nick);
	
	
	/**
	 * Controlla se la mail è già presente nel db
	 * 
	 * @param eMail indirizzo e-mail dell'utente
	 * 
	 * @return Restituisce true se il nick è esistente, false altrimenti
	 */
	public boolean controlloeMail(String eMail);
	
	
	/**
	 * Controlla se il nick è già presente nel db
	 * 
	 * @param nickName identificativo dell'utente
	 * 
	 * @return Restituisce true se il nick è esistente, false altrimenti
	 */
	public boolean controlloNick(String nickName);
	
	
	/**
	 * Il metodo viene utilizzato per prelevare dal db tutte le aste(inserzioni) a cui l'utente sta partecipando
	 * 
	 * @param idUtente numero identificativo dell'utente
	 * 
	 * @return La lista delle aste corrente a cui l'utente partecipa
	 */
	public List<Inserzione> getMieAsteInCorsoByIdUtente(Integer idUtente);
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente passato come parametro
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'utente registrato
	 * 
	 * @return Il numero delle inserzioni
	 */
	public Integer getNumeroInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato);
	
	
	
	/**
	 * Visualizza il numero delle inserzioni pubblicate dall'utente cercate per titolo
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'utente registrato
	 * @param titoloInserzione titolo dell'inserzione da visualizzare
	 * 
	 * @return Il numero delle inserzioni con quel titolo
	 */
	public Integer getNumeroLeMieInserzioniPerTitolo(Integer idUtenteRegistrato, String titoloInserzione);
	
	
	/**
	 * Visualizza il numero delle aste a cui l'utente, passato come parametro, sta partecipando
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'utente registrato
	 * 
	 * @return Numero inserzioni
	 */
	public Integer getNumeroMieAsteInCorsoByIdUtente(Integer idUtenteRegistrato);
	
	
	
	/**
	 * Preleva tutti gli utenti registrati nel database.
	 * 
	 * @return Lista degli utenti registrati
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<UtenteRegistrato> getUtenti() throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Controlla se l'utente è abilitato alle funzioni di utente registrato
	 * 
	 * @param nick identificativo univoco dell'utente
	 * 
	 * @return True se l'utente è abilitato
	 */
	public boolean isUtenteAbilitato(String nick);
	
	/**
	 * Controlla se l'utente è già registrato nel database
	 * 
	 * @param codiceFiscale codice fiscale dell'utente
	 * @param tipologiaUtente indica se l'utente è un 'venditore' o un 'acquirente'
	 * 
	 * @return True se l'utente si è già registrato
	 */
	public boolean isUtenteRegistrato(String codiceFiscale, String tipologiaUtente);
	
	
	/**
	 * Visualizza le inserzioni caricate dall'utente, passato come parametro, relative ad un intervallo (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'utente registrato
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return Le inserzioni comprese in un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioniByIdUtenteVenditore(Integer idUtenteRegistrato, Integer limiteInf,  Integer numInserzioniPagina);

	
	/**
	 * Visualizza le inserzioni osservate relative ad un intervallo (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtente numero identificativo dell'utente
	 * 
	 * @return Restituisce le inserzioni osservate relative ad un certo intervallo
	 */
	public List<Inserzione> getLimitInserzioniOsservateByIdUtente(Integer idUtente, Integer limiteInf, Integer numeroInserzioniPagina);
		

	/**
	 * Visualizzo le inserzioni, relative ad un intervallo, aggiudicate dall'utente passato come parametro (utilizzato nella paginazione delle inserzioni)
	 * 
	 * @param idUtenteRegistrato numero identificativo dell'utente registrato
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numeroInserzioniPagina numero di inserzioni da visualizzare per pagina
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
	 * @param idUtente numero identificativo dell'utente
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return La lista delle aste a cui l'utente avente l'id passato come parametro sta partecipando
	 */
	public List<Inserzione> getLimitMieAsteInCorsoByIdUtente(Integer idUtente, Integer limiteInf, Integer numInserzioniPagina);


}

