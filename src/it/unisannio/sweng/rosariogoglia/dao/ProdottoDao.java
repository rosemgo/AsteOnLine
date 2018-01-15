package it.unisannio.sweng.rosariogoglia.dao;

import java.util.List;


import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;

public interface ProdottoDao {
	
	public List<Prodotto> getProdotti();
	
	public Prodotto getProdottoById(Integer idProdotto);
	
	public List<Prodotto> getProdottiByIdProduttore(Integer idProduttore);
	
	public List<Prodotto> getProdottiByIdCategoriaByIdProduttore(Integer idCategoria, Integer idProduttore);
			
	/**
	 * Dato il nome di un prodotto, restituisce l'intero prodotto
	 * 
	 * @param nomeProdotto
	 * @return il prodotto associato al nome passato come parametro
	 */
	public Prodotto getProdottoByName(String nomeProdotto);

	
	/**
	 * Visualizza tutte le parole chiavi non ancora associate al prodotto passato come parametro
	 * 
	 * @param idProdotto
	 * @return tutte le parole chiavi non ancora associate al prodotto
	 */
	public List<Keyword> getKeywordMancantiByIdProdotto(Integer idProdotto);
		
	
	/**
	 * Utilizzato per effettuare un controllo al momento dell'inserimento dell'inserzione. Controlla se il prodotto scelto corrisponde alla categoria e al produttore scelti.
	 * 
	 * @param idProdotto
	 * @param idCategoria
	 * @param idProduttore
	 * @return true se l'inserimento è corretto
	 */
	public boolean checkProdottoBelongCategoriaProduttore(Integer idProdotto, Integer idCategoria, Integer idProduttore);
	
	/**
	 * Controlla se è possibile eliminare un prodotto. Per essere eliminato è necessario che non ci siano inserzioni relative ad esso
	 * 
	 * @param idProdotto
	 * @return true se è possibile eliminare il prodotto, false se non è possibile
	 */
	public boolean checkDeleteProdotto(Integer idProdotto);
	
	/**
	 * Controllo se la parola chiave è effettivamente associato al prodotto. 
	 * 
	 * @param idProdotto
	 * @param idKeyword
	 * @return true se la parola chiave è associata al prodotto, false in caso contrario
	 */
	public boolean checkProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
	
	
	public Integer insertProdotto(Prodotto prodotto); 
	
	/**
	 * Effettua l'associazione tra un prodotto e una parola chiave.
	 * @param idProdotto
	 * @param IdKeyword
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 */
	public Integer insertProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
	
	/**
	 * Effettua la disassociazione tra un prodotto e una parola chiave.
	 * 	
	 * @param idProdotto
	 * @param idKeyword
	 * @return 1 in caso di corretta cancellazione, -1 in caso di fallimento
	 */
	public Integer deleteProdottoHasKeyword(Integer idProdotto, Integer idKeyword);
			
	public Integer deleteProdotto(Prodotto prodotto);
	
	public Integer updateProdotto(Prodotto prodotto);
	
	/**
	 * Utilizzato per aggiornare il nome del prodotto
	 * 
	 * @param prodotto
	 * @return 1 in caso di aggiornamento riuscito, -1 in caso contrario
	 */
	public Integer updateNomeProdotto(Prodotto prodotto);
		
	

}
