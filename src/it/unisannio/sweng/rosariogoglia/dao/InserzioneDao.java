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
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPerPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return Inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioniChiusura(Integer limiteInf, Integer numInserzioniPerPagina);
		
		
	/**
	 * Visualizza le inserzioni in asta relative ad un intervallo 
	 * 
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPerPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitAsteInCorso(Integer limiteInf, Integer numInserzioniPerPagina);
		
	
	/**
	 * Visualizza le inserzioni relative ad un intervallo 
	 * 
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPerPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return inserzioni relative ad un intervallo specifico
	 */
	public List<Inserzione> getLimitInserzioni(Integer limiteInf, Integer numInserzioniPerPagina);
		
	
	/**
	 * Questo metodo carica l'intera inserzione con la lista immagini e la lista di offerte
	 * 
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return Restituisce l'inserzione
	 * 
	 */
	public Inserzione getInserzioneById(Integer idInserzione);
	
//	/**
//	 * Questo metodo carica l'intera inserzione con la lista immagini e la lista di offerte
//	 * 
//	 * Usato per test con connessione DatabaseUtil
//	 * 
//	 * @param idInserzione numero identificativo dell'inserzione
//	 * 
//	 * @return Restituisce l'inserzione
//	 * 
//	 */
//	public Inserzione getInserzioneByIdTest(Integer idInserzione);
	
	
	/**
	 * Questo metodo a differenza del getInserzioneById, carica solo l'inserzione senza la lista immagini e 
	 * la lista di offerte(utilizzato nel metodo getOffertaByIdInserzione per non creare la ricorsione tra offerta ed inserzione)
	 * 
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return Restituisce l'inserzione
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Inserzione getInserzioneByIdSenzaListe(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	

//	/**
//	 * Questo metodo a differenza del getInserzioneById, carica solo l'inserzione senza la lista immagini e 
//	 * la lista di offerte(utilizzato nel metodo getOffertaByIdInserzione per non creare la ricorsione tra offerta ed inserzione)
//	 * 
//	 * Usato per test con connessione DatabaseUtil
//	 * 
//	 * @param idInserzione numero identificativo dell'inserzione
//	 * 
//	 * @return Restituisce l'inserzione
//	 * 
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 * @throws IOException
//	 */
//	public Inserzione getInserzioneByIdSenzaListeTest(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
	
	/**
	 * Ricerca la lista delle inserzioni relative ad un prodotto associato alla keyword passata come parametro, nell’ambito della categoria passata come parametro.	
	 * 
	 * @param keyword parola chiave per la ricerca delle inserzioni
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return Restituisce la lista delle inserzioni associate ad una determinata keyword
	 */
	
	public List<Inserzione> ricercaInserzioni (String keyword, Integer idCategoria);
	
	/**
	 * Visualizza le inserzioni relative ad un intervallo specifico(utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per keyword e idCategoria
	 * 
	 * @param keyword parola chiave per la ricerca delle inserzioni
	 * @param idCategoria numero identificativo della categoria
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return Solo le inserzioni cercate relative ad un intervallo
	 */
	public List<Inserzione> ricercaLimitInserzioni(String keyword, Integer idCategoria, Integer limiteInf, Integer numInserzioniPagina);
	
	/**
	 * Visualizza le inserzioni relative ad un intervallo specifico(utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per keyword, idCategoria, idProduttore, idProdotto, titolo, prezzoMin e prezzoMax
	 * 
	 * @param keyword parola chiave per la ricerca delle inserzioni
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 * @param idProdotto numero identificativo del prodotto
	 * @param titolo titolo dell'inserzione da ricercare
	 * @param prezzoMin prezzo minimo dell'inserzione
	 * @param prezzoMax prezzo massimo dell'inserzione
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return Solo le inserzioni cercate relative ad un intervallo
	 */
	public List<Inserzione> ricercaAvanzataInserzioneLimitInserzioni(String keyword, Integer idCategoria, String titolo, Double prezzoMin, Double prezzoMax, Integer limiteInf, Integer numInserzioniPagina);
		

	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca
	 * 
	 * @param keyword parola chiave per la ricerca delle inserzioni
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return Numero totale delle inserzioni trovate filtrate per keyword e idCategoria
	 */
	public Integer getNumeroInserzioniCercate(String keyword, Integer idCategoria);
	
	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca avanzata
	 * 
	 * @param keyword parola chiave per la ricerca delle inserzioni
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 * @param idProdotto numero identificativo del prodotto
	 * @param titolo titolo dell'inserzione da cercare
	 * @param prezzoMin prezzo minimo dell'inserzione
	 * @param prezzoMax prezzo massimo dell'inserzione
	 * 
	 * @return Numero totale delle inserzioni trovate filtrate per keyword, idCategoria, idProduttore, idProdotto, titolo, prezzoMin e prezzoMax
	 */
	public Integer getNumeroInserzioniRicercaAvanzata(String keyword, Integer idCategoria, String titolo, Double prezzoMin, Double prezzoMax);
	
	/**
	 * Il metodo visualizza le inserzioni più osservate dagli utenti tra quelle aventi stato 'in asta', il numero
	 * di inserzioni da osservare dipende dal parametro 'numInserzioni'
	 * 
	 * @param numInserzioni il numero di inserzioni da visualizzare
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
	 * @param numInserzioni il numero di inserzioni da visualizzare
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
	 * @param titoloInserzione titolo dell'inserzione da cercare
	 * 
	 * @return Il numero delle inserzioni che hanno un determinto titolo
	 */
	public Integer getNumeroInserzioniPerTitolo(String titoloInserzione);
		
	
	/**
	 * Questo metodo restituisce una lista di utenti che osservano l'inserzione identificata dal parametro idInserzione
	 * 
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return lista di utente registrati
	 */
	public List<UtenteRegistrato> getUtentiRegistratiOsservanoByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
	/**
	 *  Visualizza le inserzioni relative ad un intervallo specifico (utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per titolo
	 * 
	 * @param titoloInserzione titolo dell'inserzione da cercare
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numeroInserzioniPerPagina numero di inserzioni da visualizzare per pagina
	 * 
	 * @return La liste di inserzioni comprese nell'intervallo specificato
	 */
	public List<Inserzione> ricercaLimitInserzioniPerTitolo(String titoloInserzione, Integer limiteInf, Integer numeroInserzioniPerPagina);
		
	/**
	 * Inserisce l'oggetto inserzione passato come parametro nel database.
	 * 	
	 * @param inserzione oggetto inserzione da inserire nel database
	 * 
	 * @return L'id associato all'inserzione nel database
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer insertInserzione(Inserzione inserzione) throws ClassNotFoundException, SQLException, IOException;
	
//	/**
//	 * Inserisce l'oggetto inserzione passato come parametro nel database.
//	 * 
//	 * Usato per test con connessione DatabaseUtil
//	 * 	
//	 * @param inserzione oggetto inserzione da inserire nel database
//	 * 
//	 * @return L'id associato all'inserzione nel database
//	 * 
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 * @throws IOException
//	 */
//	public Integer insertInserzioneTest(Inserzione inserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
	
	
	
	/**
	 * Il metodo aggiorna un'inserzione caricata precedentemente
	 * 
	 * @param inserzione oggetto inserzione da aggiornare
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
	 * @param statoInserzione stato dell'nserzione
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return Il numero di righe aggiornate nel database,se l'aggiornamento fallisce restituisce -1
	 */
	public Integer updateStatoInserzione(String statoInserzione, Integer idInserzione);
	
	/**
	 * Il metodo aggiorna il prezzo iniziale e la data scadenza dell'inserzione. Utilizzato nella ripubblicazione dell'inserzione.
	 * 
	 * @param prezzoIniziale prezzo iniziale dell'inserzione
	 * @param dataScadenzaAsta data di scadenza dell'asta
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return Il numero di righe aggiornate nel database,se l'aggiornamento fallisce restituisce -1
	 */
	public Integer updateRipubblicaInserzione(Double prezzoIniziale, Date dataScadenzaAsta, Integer idInserzione);
		
	/**
	 * Il metodo aggiorna l'acquirente dell'inserzione e il prezzo in base all'ultima offerta fatta
	 * 
	 * @param idAcquirente numero identificativo dell'acquirente
	 * @param prezzoAggiornato prezzo dell'inserzione aggiornato dopo un'offerta
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return numero di righe aggiornate(1 se la modifica è avvenuta, -1 nel caso contrario)
	 */
	public Integer updateAcquirenteOffertaInserzione(Integer idAcquirente, Double prezzoAggiornato, Integer idInserzione);
	
	
	
	/**
	 * Il metodo elimina un'inserzione caricata da utente dal database
	 * 
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return Il numero di righe eliminate dal database, se l'eliminazione fallisce restituisce -1
	 */
	
	
	public Integer deleteInserzione(Integer idInserzione);
	
//	/**
//	 * Il metodo elimina un'inserzione caricata da utente dal database
//	 * 
//	 * Usato per test con connessione DatabaseUtil
//	 * 
//	 * @param idInserzione numero identificativo dell'inserzione
//	 * 
//	 * @return Il numero di righe eliminate dal database, se l'eliminazione fallisce restituisce -1
//	 */
//	public Integer deleteInserzioneTest(Integer idInserzione);
	
	/**
	 * Il metodo elimina un'inserzione  dalla lista delle inserzioni osservate da un utente
	 * 
	 * @param idInserzione numero identificativo dell'inserzione
	 * @param idUtente numero identificativo dell'utente
	 * 
	 * @return Il numero di righe eliminate dal database, se l'eliminazione fallisce restituisce -1
	 */
	
	
	public Integer deleteInserzioneOsservata(Integer idInserzione, Integer idUtente);

	/**
	 * Visualizza il numero delle inserzioni ottenute in seguito ad una ricerca avanzata
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 * @param idProdotto numero identificativo del prodotto
	 * 
	 * @return Numero totale delle inserzioni trovate filtrate per idCategoria, idProduttore, idProdotto
	 */
	public int getNumeroInserzioniRicercaAvanzataProdotto(Integer idCategoria, Integer idProduttore, Integer idProdotto);

	/**
	 *  Visualizza le inserzioni relative ad un intervallo specifico(utilizzato nella paginazione delle inserzioni) in seguito ad una ricerca filtrata per idCategoria, idProduttore, idProdotto
	 *
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 * @param idProdotto numero identificativo del prodotto
	 * @param limiteInf limite inferiore dell'intervallo
	 * @param numInserzioniPagina numero di inserzioni da visualizzare per pagina
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
