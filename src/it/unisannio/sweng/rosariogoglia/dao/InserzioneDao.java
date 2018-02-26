package it.unisannio.sweng.rosariogoglia.dao;


import it.unisannio.sweng.rosariogoglia.model.Inserzione;





import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface InserzioneDao {
	
	/**
	 * Questo metodo carica tutte le inserzioni presenti nel database
	 * 
	 * @return restituisce l'inserzione
     */
	public List<Inserzione> getInserzioni();
		

	
	
	/**
	 * Questo metodo carica l'intera inserzione con la lista immagini e la lista di offerte
	 * @param idInserzione
	 * @return restituisce l'inserzione
	  */
	public Inserzione getInserzioneById(Integer idInserzione);
	

	/**
	 * Questo metodo a differenza del getInserzioneById, carica solo l'inserzione senza la lista immagini e 
	 * la lista di offerte(utilizzato nel metodo getOffertaByIdInserzione per non creare la ricorsione tra offerta ed inserzione)
	 * 
	 * @param idInserzione
	 * @return restituisce l'inserzione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Inserzione getInserzioneByIdSenzaListe(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
	/**
	 * Ottiene i titoli di tutte le inserzioni. Utilizzato per l'autocompletamento nella ricerca inserzioni per titoli
	 * @return una lista con tutti i titoli delle inserzioni
	 */
	public List<String> getTitoli();
	
	/**
	 * Questo metodo restituisce una lista di utenti che osservano l'inserzione identificata dal parametro idInserzione
	 * 
	 * @param idInserzione
	 * @return lista di utente registrati
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<UtenteRegistrato> getUtentiRegistratiOsservanoByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
	/**
	 * Ricerca l'inserzioni in base alla parola chiave e all'id della categoria
	 * 
	 * @param keyword
	 * @param idCategoria
	 * @return lista contenente le inserzioni corrispondenti
	 */
	public List<Inserzione> ricercaInserzioni (String keyword, Integer idCategoria);



	/**
	 * Ordina le inserzioni in base al numero di osservazioni (utenti che osservano un'inserzione)
	 * 
	 * @return Lista di inserzioni ordinate
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Inserzione> ordinaInserzioniPopolari() throws ClassNotFoundException, SQLException, IOException;



	/**
	 * Ordina le inserzioni in base al numero di osservazioni (utenti che osservano un'inserzione)
	 * 
	 * @param i, indica il numero delle inserzioni più popolari richieste
	 * @return Lista di inserzioni ordinate
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Inserzione> ricercaTopInserzioniPopolari(int i) throws ClassNotFoundException, SQLException, IOException;



	/**
	 * Ricerca le inserzioni la cui data di scadenza è più prossima.
	 *  
	 * @param i, indica il numero delle inserzioni in scadenza richieste
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Inserzione> ricercaTopInserzioniChiusura(int i) throws ClassNotFoundException, SQLException, IOException;
	
	
	/**
	 * Il metodo aggiorna lo stato dell'inserzione
	 * 
	 * @param statoInserzione
	 * @param idInserzione
	 * @return un intero che indica il numero di righe aggiornate
	 */
	public Integer updateStatoInserzione(String statoInserzione, Integer idInserzione);
	
	
	
	/**
	 * Inserimento dell'inserzione nel database
	 * 
	 * @param inserzione
	 * @return un intero che indica il numero di righe inserite
	 * @throws SQLException 
	 */
	public Integer insertInserzione(Inserzione inserzione) throws SQLException;
	
	
	/**
	 * Aggiornamento di un'inserzione precedentemente inserita
	 * 
	 * @param inserzione
	 * @return un intero che indica il numero di righe aggiornate
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateInserzione(Inserzione inserzione) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Visualizza le inserzioni in asta relative ad un intervallo 
	 * 
	 * @param limiteInf
	 * @param numInserzioniPerPagina
	 * @return inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitAsteInCorso(Integer limiteInf, Integer numInserzioniPerPagina);
		
	
	/**
	 * Visualizza le inserzioni relative ad un intervallo 
	 * 
	 * @param limiteInf
	 * @param numInserzioniPerPagina
	 * @return inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioni(Integer limiteInf, Integer numInserzioniPerPagina);
		
	/**
	 * Visualizza le inserzioni in chiusura relative ad un intervallo
	 * 
	 * @param limiteInf
	 * @param numInserzioniPerPagina
	 * @return inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioniChiusura(Integer limiteInf, Integer numInserzioniPerPagina);
		
	
}
