package it.unisannio.sweng.rosariogoglia.dao;


import it.unisannio.sweng.rosariogoglia.model.Inserzione;





import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public interface InserzioneDao {
	
	/**
	 * Questo metodo carica tutte le inserzioni presenti nel database
	 * 
	 * @return Restituisce l'inserzione
	 */
	public List<Inserzione> getInserzioni();
		
	/**
	 * Visualizza il numero delle inserzioni presenti nel db
	 *
	 * @return Numero totale inserzioni
	 */
	public Integer getNumeroInserzioni();
		
	/**
	 * Visualizza il numero delle inserzioni in asta presenti nel db
	 *
	 * @return Numero totale aste in corso
	 */
	
	public Integer getNumeroAsteInCorso();
	
	/**
	 * Visualizza il numero delle inserzioni in asta che scadono nell'arco di un mese presenti nel db
	 * 
	 * @return Numero delle aste in chiusura nel prossimo mese
	 */
	public Integer getNumeroAsteInChiusura();
		
	/**
	 * Visualizza le inserzioni in chiusura relative ad un intervallo
	 * 
	 * @param limiteInf
	 * @param numInserzioniPerPagina
	 * 
	 * @return Inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioniChiusura(Integer limiteInf, Integer numInserzioniPerPagina);
		
		
	/**
	 * Visualizza le inserzioni in asta relative ad un intervallo 
	 * 
	 * @param limiteInf
	 * @param numInserzioniPerPagina
	 * 
	 * @return inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitAsteInCorso(Integer limiteInf, Integer numInserzioniPerPagina);
		
	
	/**
	 * Visualizza le inserzioni relative ad un intervallo 
	 * 
	 * @param limiteInf
	 * @param numInserzioniPerPagina
	 * 
	 * @return inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioni(Integer limiteInf, Integer numInserzioniPerPagina);
		
	
	
	/**
	 * Questo metodo carica l'intera inserzione con la lista immagini e la lista di offerte
	 * 
	 * @param idInserzione
	 * 
	 * @return Restituisce l'inserzione
	 * 
	 */
	public Inserzione getInserzioneById(Integer idInserzione);
	
	/**
	 * Questo metodo a differenza del getInserzioneById, carica solo l'inserzione senza la lista immagini e 
	 * la lista di offerte(utilizzato nel metodo getOffertaByIdInserzione per non creare la ricorsione tra offerta ed inserzione)
	 * 
	 * @param idInserzione
	 * 
	 * @return Restituisce l'inserzione
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Inserzione getInserzioneByIdSenzaListe(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	

	/**
	 * Questo metodo visualizza la lista delle inserzioni contenenti la keyword passata come parametro.	
	 * 
	 * @param keyword
	 * @param idCategoria
	 * 
	 * @return Restituisce la lista delle inserzioni conteneti una determinata keyword
	 */
	
	public List<Inserzione> ricercaInserzioni (String keyword, Integer idCategoria);
	
	/**
	 * Visualizza le inserzioni relative ad un intervallo specifico(utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per keyword e idCategoria
	 * 
	 * @param keyword
	 * @param idCategoria
	 * @param limiteInf
	 * @param numInserzioniPagina
	 * 
	 * @return Solo le inserzioni cercate relative ad un intervallo
	 */
	public List<Inserzione> ricercaLimitInserzioni(String keyword, Integer idCategoria, Integer limiteInf, Integer numInserzioniPagina);
	
	/**
	 * Visualizza le inserzioni relative ad un intervallo specifico(utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per keyword, idCategoria, idProduttore, idProdotto, titolo, prezzoMin e prezzoMax
	 * 
	 * @param keyword
	 * @param idCategoria
	 * @param idProduttore
	 * @param idProdotto
	 * @param titolo
	 * @param prezzoMin
	 * @param prezzoMax
	 * @param limiteInf
	 * @param numInserzioniPagina
	 * 
	 * @return Solo le inserzioni cercate relative ad un intervallo
	 */
	public List<Inserzione> ricercaAvanzataInserzioneLimitInserzioni(String keyword, Integer idCategoria, String titolo, Double prezzoMin, Double prezzoMax, Integer limiteInf, Integer numInserzioniPagina);
		

	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca
	 * 
	 * @param keyword
	 * @param idCategoria
	 * 
	 * @return Numero totale delle inserzioni trovate filtrate per keyword e idCategoria
	 */
	public Integer getNumeroInserzioniCercate(String keyword, Integer idCategoria);
	
	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca avanzata
	 * 
	 * @param keyword
	 * @param idCategoria
	 * @param idProduttore
	 * @param idProdotto
	 * @param titolo
	 * @param prezzoMin
	 * @param prezzoMax
	 * 
	 * @return Numero totale delle inserzioni trovate filtrate per keyword, idCategoria, idProduttore, idProdotto, titolo, prezzoMin e prezzoMax
	 */
	public Integer getNumeroInserzioniRicercaAvanzata(String keyword, Integer idCategoria, String titolo, Double prezzoMin, Double prezzoMax);
	
	/**
	 * Il metodo visualizza le inserzioni più osservate dagli utenti tra quelle aventi stato 'in asta', il numero
	 * di inserzioni da osservare dipende dal parametro 'numInserzioni'
	 * 
	 * @param numInserzioni
	 * 
	 * @return La lista delle inserzioni più osservate 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Inserzione> ricercaTopInserzioniPopolari(int numInserzioni) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Il metodo ordina le inserzioni in base al numero di osservazioni ottenute 
	 * 
	 * @return restituisce la lista delle inserzioni ordinate per popolarità
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public List<Inserzione> ordinaInserzioniPopolari() throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Visualizza le inserzioni ordinate in base alla loro data di scadenza,il numero di isnerzioni da visualizzare
	 * dipende dal parametro 'numInserzioni'
	 * 
	 * @param numInserzioni
	 * 
	 * @return La lista ordinata delle inserzioni più prossime alla scadenza
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public List<Inserzione> ricercaTopInserzioniChiusura(int numInserzioni) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca per titolo
	 * 
	 * @param titoloInserzione
	 * 
	 * @return Il numero delle inserzioni che hanno un determintao titolo
	 */
	public Integer getNumeroInserzioniPerTitolo(String titoloInserzione);
		
	
	/**
	 * Questo metodo restituisce una lista di utenti che osservano l'inserzione identificata dal parametro idInserzione
	 * 
	 * @param idInserzione
	 * 
	 * @return lista di utente registrati
	 */
	public List<UtenteRegistrato> getUtentiRegistratiOsservanoByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
	/**
	 *  Visualizza le inserzioni relative ad un intervallo specifico (utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per titolo
	 * 
	 * @param titoloInserzione
	 * @param limiteInf
	 * @param numeroInserzioniPerPagina 
	 * 
	 * @return La liste di inserzioni comprese nell'intervallo specificato
	 */
	public List<Inserzione> ricercaLimitInserzioniPerTitolo(String titoloInserzione, Integer limiteInf, Integer numeroInserzioniPerPagina);
		
	/**
	 * Inserisce l'oggetto inserzione passato come parametro nel database.
	 * 	
	 * @param inserzione
	 * 
	 * @return L'id associato all'inserzione nel database
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer insertInserzione(Inserzione inserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
	
	/**
	 * Il metodo aggiorna un'inserzione caricata precedentemente
	 * 
	 * @param inserzione
	 * 
	 * @return Il numero di righe aggiornate nel database, se l'aggiornamento non va a buon fine viene restituito -1
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer updateInserzione(Inserzione inserzione) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Il metodo aggiorna lo stato dell'inserzione
	 * 
	 * @param statoInserzione
	 * @param idInserzione
	 * 
	 * @return Il numero di righe aggiornate nel database,se l'aggiornamento fallisce restituisce -1
	 */
	public Integer updateStatoInserzione(String statoInserzione, Integer idInserzione);
	
	/**
	 * Il metodo aggiorna il prezzo iniziale e la data scadenza dell'inserzione. Utilizzato nella ripubblicazione dell'inserzione.
	 * 
	 * @param prezzoIniziale
	 * @param dataScadenzaAsta
	 * @param idInserzione
	 * 
	 * @return Il numero di righe aggiornate nel database,se l'aggiornamento fallisce restituisce -1
	 */
	public Integer updateRipubblicaInserzione(Double prezzoIniziale, Date dataScadenzaAsta, Integer idInserzione);
		
	/**
	 * Il metodo aggiorna l'acquirente dell'inserzione e il prezzo in base all'ultima offerta fatta
	 * 
	 * @param idAcquirente
	 * @param idInserzione
	 * 
	 * @return numero di righe aggiornate(1 se la modifica è avvenuta, -1 nel caso contrario)
	 */
	public Integer updateAcquirenteOffertaInserzione(Integer idAcquirente, Double prezzoAggiornato, Integer idInserzione);
	
	
	
	/**
	 * Il metodo elimina un'inserzione caricata da utente dal database
	 * 
	 * @param idInserzione
	 * 
	 * @return Il numero di righe eliminate dal database, se l'eliminazione fallisce restituisce -1
	 */
	
	
	public Integer deleteInserzione(Integer idInserzione);
	
	/**
	 * Il metodo elimina un'inserzione  dalla lista delle inserzioni osservate da un utente
	 * 
	 * @param idInserzione
	 * @param idUtente
	 * 
	 * @return Il numero di righe eliminate dal database, se l'eliminazione fallisce restituisce -1
	 */
	
	
	public Integer deleteInserzioneOsservata(Integer idInserzione, Integer idUtente);

	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca avanzata
	 * 
	 * @param idCategoria
	 * @param idProduttore
	 * @param idProdotto
	 * 
	 * @return Numero totale delle inserzioni trovate filtrate per idCategoria, idProduttore, idProdotto
	 */
	public int getNumeroInserzioniRicercaAvanzataProdotto(Integer idCategoria, Integer idProduttore, Integer idProdotto);

	/**
	 *  Visualizza le inserzioni relative ad un intervallo specifico(utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per idCategoria, idProduttore, idProdotto
	 *
	 * @param idCategoria
	 * @param idProduttore
	 * @param idProdotto
	 * @param limiteInf
	 * @param numInserzioniPagina
	 * 
	 * @return Solo le inserzioni cercate relative ad un intervallo
	 * 
	 */
	public List<Inserzione> ricercaAvanzataInserzioneLimitProdotti(Integer idCategoria, Integer idProduttore, Integer idProdotto, Integer limiteInf, Integer numInserzioniPagina);

	/**
	 * Ottiene i titoli di tutte le inserzioni. Utilizzato per l'autocompletamento nella ricerca inserzioni per titoli
	 * 
	 * @return Una lista con tutti i titoli delle inserzioni
	 */
	public List<String> getTitoli();
	
	
}
