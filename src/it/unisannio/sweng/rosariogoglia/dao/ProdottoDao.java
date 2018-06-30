package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Keyword;

public interface ProdottoDao {
	
	public List<Prodotto> getProdotti();
	
	/**
	 * Questo metodo fornisce una lista di tutti i prodotti presenti nel database
	 * 
	 * @param idProdotto numero identificativo del prodotto
	 * 
	 * @return La li sta di tutti i prodotti presenti nel database
	 */
	
	public Prodotto getProdottoById(Integer idProdotto);
	
	
	/**
	 * Questo metodo fornisce una lista di tutti i prodotti presenti nel database
	 * 
	 * Usato per fare il Test con connessione DatabaseUtil
	 * 
	 * @param idProdotto numero identificativo del prodotto
	 * 
	 * @return La li sta di tutti i prodotti presenti nel database
	 */
	public Prodotto getProdottoByIdTest(Integer idProdotto);
	
	
	
	/**
	 * Questo metodo fornisce il prodotto avente l'id passato come parametro
	 * 
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return Il prodotto avente id corrispondente a quello passato come parametro
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public List<Prodotto> getProdottiByIdProduttore(Integer idProduttore) throws ClassNotFoundException, SQLException, IOException;
	
	
	/**
	 * Questo metodo fornisce il prodotto avente l'id passato come parametro
	 * 
	 * Usato per fare il Test con connessione DatabaseUtil
	 * 
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return Il prodotto avente id corrispondente a quello passato come parametro
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public List<Prodotto> getProdottiByIdProduttoreTest(Integer idProduttore) throws ClassNotFoundException, SQLException, IOException;
	
	
	/**
	 * Questo metodo fornisce la lista dei prodotti il cui id del produttore corrisponde a quello passato come parametro
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return La lista dei prodotti che hanno l'id del produttore coincidente con quello passato come paramentro
	 */
	
	public List<Prodotto> getProdottiByIdCategoriaByIdProduttore(Integer idCategoria, Integer idProduttore);
			
	/**
	 * Dato il nome di un prodotto, restituisce l'intero prodotto
	 * 
	 * @param nomeProdotto nome del prodotto da visualizzare
	 * 
	 * @return Il prodotto associato al nome passato come parametro
	 * 
	 */
	public Prodotto getProdottoByName(String nomeProdotto);


	/**
	 * Inserimento di un prodotto nel database
	 * 
	 * @param prodotto l'oggetto prodotto da inserire nel database
	 * 
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 * 
	 */
	public Integer insertProdotto(Prodotto prodotto);
	
	
	/**
	 * Inserimento di un prodotto nel database
	 * 
	 * Usato per il test con connessione DatabaseUtil 
	 * 
	 * @param prodotto l'oggetto prodotto da inserire nel database
	 * 
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 * 
	 */
	public Integer insertProdottoTest(Prodotto prodotto);
		
	
	
	/**
	 * Effettua l'associazione tra un prodotto e una parola chiave.
	 * 
	 * @param idProdotto numero identificativo del prodotto
	 * @param idKeyword numero identificativo della keyword
	 * 
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 */
	public Integer insertProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
	
	/**
	 * Utilizzato per effettuare un controllo al momento dell'inserimento dell'inserzione. Controlla se il prodotto scelto corrisponde alla categoria e al produttore scelti.
	 * 
	 * @param idProdotto numero identificativo del prodotto
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return True se l'inserimento è corretto
	 */
	public boolean checkProdottoBelongCategoriaProduttore(Integer idProdotto, Integer idCategoria, Integer idProduttore);
	
	/**
	 * Controlla se è possibile eliminare un prodotto. Per essere eliminato è necessario che non ci siano inserzioni relative ad esso
	 * 
	 * @param idProdotto numero identificativo del prodotto
	 * 
	 * @return True se è possibile eliminare il prodotto, false se non è possibile
	 */
	public boolean checkDeleteProdotto(Integer idProdotto);
	
	/**
	 * Controllo se la parola chiave è effettivamente associato al prodotto. 
	 * 
	 * @param idProdotto numero identificativo del prodotto
	 * @param idKeyword numero identificativo della keyword
	 * 
	 * @return True se la parola chiave è associata al prodotto, false in caso contrario
	 */
	public boolean checkProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
	
	/**
	 * Effettua la disassociazione tra un prodotto e una parola chiave.
	 * 	
	 * @param idProdotto numero identificativo del prodotto
	 * @param idKeyword numero identificativo della keyword
	 * 
	 * @return 1 in caso di corretta cancellazione, -1 in caso di fallimento
	 */
	public Integer deleteProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
		
	/**
	 * Cancellazione di un prodotto dal database
	 * 
	 * @param prodotto numero identificativo del prodotto
	 * 
	 * @return 1 in caso di corretta cancellazione, -1 in caso di fallimento
	 */
	public Integer deleteProdotto(Prodotto prodotto);
	
	
	/**
	 * Cancellazione di un prodotto dal database
	 * 
	 * Usato per fare il Test con connessione DatabaseUtil
	 * 
	 * @param prodotto numero identificativo del prodotto
	 * 
	 * @return 1 in caso di corretta cancellazione, -1 in caso di fallimento
	 */
	public Integer deleteProdottoTest(Prodotto prodotto);
	
	/**
	 * Visualizza tutte le parole chiavi presenti nel database ma non ancora associate al prodotto che viene passato come parametro
	 * 
	 * @param idProdotto numero identificativo del prodotto
	 * 
	 * @return Tutte le parole chiavi non ancora associate al prodotto
	 */
	public List<Keyword> getKeywordMancantiByIdProdotto(Integer idProdotto);
	
	/**
	 * Modifica caratteristiche di un prodotto già presente nel database
	 *  
	 * @param prodotto oggetto prodotto da aggiornare nel database
	 * 
	 * @return 1 in caso di aggiornamento riuscito, -1 in caso contrario
	 */
	public Integer updateProdotto(Prodotto prodotto);
	
	/**
	 * Utilizzato per aggiornare il nome del prodotto
	 * 
	 * @param prodotto oggetto prodotto di cui aggiornare il nome nel database
	 * 
	 * @return 1 in caso di aggiornamento riuscito, -1 in caso contrario
	 */
	public Integer updateNomeProdotto(Prodotto prodotto);
		
	

}
