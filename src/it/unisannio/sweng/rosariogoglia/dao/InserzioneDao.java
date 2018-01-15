package it.unisannio.sweng.rosariogoglia.dao;


import it.unisannio.sweng.rosariogoglia.model.Inserzione;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public interface InserzioneDao {
	
	/**
	 * Questo metodo carica tutte le inserzioni presenti nel database
	 * 
	 * @param idInserzione
	 * @return restituisce l'inserzione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Inserzione> getInserzioni();
		
	/**
	 * Visualizza il numero delle inserzioni presenti nel db
	 *
	 * @return numero totale inserzioni
	 */
	public Integer getNumeroInserzioni();
		
	/**
	 * Visualizza il numero delle inserzioni in asta presenti nel db
	 *
	 * @return numero totale aste in corso
	 */
	
	public Integer getNumeroAsteInCorso();
	
	/**
	 * Visualizza il numero delle inserzioni in asta che scadono nell'arco di un mese presenti nel db
	 * 
	 * @return numero delle aste in chiusura nel prossimo mese
	 */
	public Integer getNumeroAsteInChiusura();
		
		
		
	
	
	/**
	 * Questo metodo carica l'intera inserzione con la lista immagini e la lista di offerte
	 * @param idInserzione
	 * @return restituisce l'inserzione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
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
	public Inserzione getInserzioneByIdSenzaListe(Integer idInserzione);
	

		
	
	public List<Inserzione> ricercaInserzioni (String keyword, Integer idCategoria);
	
			
	public List<Inserzione> ricercaTopInserzioniPopolari(int numInserzioni);
	
	public List<Inserzione> ordinaInserzioniPopolari();
	
	public List<Inserzione> ricercaTopInserzioniChiusura(int numInserzioni);
	
	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca per titolo
	 * 
	 * @param titoloInserzione
	 * @return
	 */
	public Integer getNumeroInserzioniPerTitolo(String titoloInserzione);
		

		
	public Integer insertInserzione(Inserzione inserzione);
	
	public Integer updateInserzione(Inserzione inserzione);
	
	/**
	 * Il metodo aggiorna lo stato dell'inserzione
	 * 
	 * @param statoInserzione
	 * @param idInserzione
	 * @return
	 */
	public Integer updateStatoInserzione(String statoInserzione, Integer idInserzione);
	
	/**
	 * Il metodo aggiorna il prezzo iniziale e la data scadenza dell'inserzione. Utilizzato nella ripubblicazione dell'inserzione.
	 * 
	 * @param prezzoIniziale
	 * @param dataScadenzaAsta
	 * @param idInserzione
	 * @return
	 */
	public Integer updateRipubblicaInserzione(Double prezzoIniziale, Date dataScadenzaAsta, Integer idInserzione);
		
	/**
	 * Il metodo aggiorna l'acquirente dell'inserzione e il prezzo in base all'ultima offerta fatta
	 * 
	 * @param idAcquirente
	 * @param idInserzione
	 * @return numero di righe aggiornate(1 se la modifica è avvenuta, -1 nel caso contrario)
	 */
	public Integer updateAcquirenteOffertaInserzione(Integer idAcquirente, Double prezzoAggiornato, Integer idInserzione);
		
	public Integer deleteInserzione(Integer idInserzione);
	
	public Integer deleteInserzioneOsservata(Integer idInserzione, Integer idUtente);

	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca avanzata
	 * 
	 * @param idCategoria
	 * @param idProduttore
	 * @param idProdotto
	 * 
	 * @return numero totale delle inserzioni trovate filtrate per idCategoria, idProduttore, idProdotto
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
	 * @return solo le inserzioni cercate relative ad un intervallo
	 * 
	 */
	public List<Inserzione> ricercaAvanzataInserzioneLimitProdotti(Integer idCategoria, Integer idProduttore, Integer idProdotto, Integer limiteInf, Integer numInserzioniPagina);

	/**
	 * Ottiene i titoli di tutte le inserzioni. Utilizzato per l'autocompletamento nella ricerca inserzioni per titoli
	 * @return una lista con tutti i titoli delle inserzioni
	 */
	public List<String> getTitoli();

	
			
	
}
