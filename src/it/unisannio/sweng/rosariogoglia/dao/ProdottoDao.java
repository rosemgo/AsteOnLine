package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Keyword;

public interface ProdottoDao {
	
	public List<Prodotto> getProdotti();
	
	public Prodotto getProdottoById(Integer idProdotto);
	
	public List<Prodotto> getProdottiByIdProduttore(Integer idProduttore) throws ClassNotFoundException, SQLException, IOException;
	
	public List<Prodotto> getProdottiByIdCategoriaByIdProduttore(Integer idCategoria, Integer idProduttore);
			
	/**
	 * Dato il nome di un prodotto, restituisce l'intero prodotto
	 * 
	 * @param nomeProdotto
	 * @return il prodotto associato al nome passato come parametro
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Prodotto getProdottoByName(String nomeProdotto);


	/**
	 * Inserimento prodotto nel database
	 * 
	 * @param prodotto
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 */
	public Integer insertProdotto(Prodotto prodotto);
	
	
	
	/**
	 * Effettua l'associazione tra un prodotto e una parola chiave.
	 * @param idProdotto
	 * @param IdKeyword
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer insertProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
	
	/**
	 * Utilizzato per effettuare un controllo al momento dell'inserimento dell'inserzione. Controlla se il prodotto scelto corrisponde alla categoria e al produttore scelti.
	 * 
	 * @param idProdotto
	 * @param idCategoria
	 * @param idProduttore
	 * @return true se l'inserimento � corretto
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkProdottoBelongCategoriaProduttore(Integer idProdotto, Integer idCategoria, Integer idProduttore);
	
	/**
	 * Controlla se � possibile eliminare un prodotto. Per essere eliminato � necessario che non ci siano inserzioni relative ad esso
	 * 
	 * @param idProdotto
	 * @return true se � possibile eliminare il prodotto, false se non � possibile
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkDeleteProdotto(Integer idProdotto);
	
	/**
	 * Controllo se la parola chiave � effettivamente associato al prodotto. 
	 * 
	 * @param idProdotto
	 * @param idKeyword
	 * @return true se la parola chiave � associata al prodotto, false in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
	
	/**
	 * Effettua la disassociazione tra un prodotto e una parola chiave.
	 * 	
	 * @param idProdotto
	 * @param idKeyword
	 * @return 1 in caso di corretta cancellazione, -1 in caso di fallimento
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer deleteProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
		
	/**
	 * Cancellazione di un prodotto dal database
	 * 
	 * @param prodotto
	 * @return 1 in caso di corretta cancellazione, -1 in caso di fallimento
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer deleteProdotto(Prodotto prodotto);
	
	
	/**
	 * Visualizza tutte le parole chiavi presenti nel database ma non ancora associate al prodotto che viene passato come parametro
	 * 
	 * @param idProdotto
	 * @return tutte le parole chiavi non ancora associate al prodotto
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<Keyword> getKeywordMancantiByIdProdotto(Integer idProdotto);
	
	/**
	 * Modifica caratteristiche di un prodotto gi� presente nel database
	 *  
	 * @param prodotto
	 * @return
	 */
	public Integer updateProdotto(Prodotto prodotto);
	
	/**
	 * Utilizzato per aggiornare il nome del prodotto
	 * 
	 * @param prodotto
	 * @return 1 in caso di aggiornamento riuscito, -1 in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateNomeProdotto(Prodotto prodotto);
		
	

}
