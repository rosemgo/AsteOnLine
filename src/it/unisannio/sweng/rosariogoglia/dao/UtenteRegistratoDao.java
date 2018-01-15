package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public interface UtenteRegistratoDao {
	

	
	/**
	 * Restituisce l'utente corrispondente all'id passato come paramentro
	 * @param idUtente
	 * @return l'utente
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public UtenteRegistrato getUtenteRegistratoById(Integer idUtente);
	
	
	/**
	 * Restituisce l'utente corrispondente alla mail passata come paramentro
	 * 
	 * @param eMail
	 * 
	 * @return l'utente corrispondente alla mail passata come paramentro
	 */
	public UtenteRegistrato getUtenteRegistratoByeMail(String eMail);
	
	/**
	 * Restituisce l'utente corrispondente al nick passato come paramentro.
	 * 
	 * @param idUtente
	 * @return l'utente
	 */
	public UtenteRegistrato getUtenteRegistratoByNick(String nick);
		
	
	/**
	 * Inserisce un nuovo utente nel database.
	 * @param utente
	 * @return l'id dell'utente inserito, oppure -1 in caso di errore
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer insertUtenteRegistrato(UtenteRegistrato utente);
	
	/**
	 * In realt� questo metodo non elimina l'utente, bens� ne setta solo il flag abilitato a 0, in modo tale che l'utente 
	 * non risulta pi� abilitato alle funzioni di un utente registrato
	 * @param utente
	 * @return numero di righe cancellate
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer deleteUtenteRegistrato(UtenteRegistrato utente);
	
	
	public Integer updateUtenteRegistrato(UtenteRegistrato utente);

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
	 * @param stato valore dello stato da settare
	 * 
	 * @return il numero di righe aggiornate(1 in caso di successo, 0 in caso di fallimento)
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateTipologiaUtente(String nick, String tipologiaCliente);
	
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
	public Integer updateAbilitazioneUtente(String nick, boolean flagAbilitato);
		
	
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
	public Integer updatePassword(String nick, String psw);
	
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
	public Integer insertOsservaInserzione(UtenteRegistrato utente, Inserzione inserzione);
	
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
	 * Verifico se l'inserzione passata come parametro � gi� osservata dall'utente passato come parametro
	 * @param idUtenteRegistrato
	 * @param idInserzione
	 * @return
	 */
	public Boolean checkInserzioneOsservataByIdUtente(Integer idUtenteRegistrato, Integer idInserzione);
			
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
	 * @param limiteInf
	 * @param numeroInserzioniPagina
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
	
	
}
