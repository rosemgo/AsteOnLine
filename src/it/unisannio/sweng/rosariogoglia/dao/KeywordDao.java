package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.Keyword;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface KeywordDao {
	
	/**
	 * Questo metodo visualizza tutte le keywords presenti nel sistema
	 * 
	 * @return La lista di tutte le keywords presenti nel database
	 */

	public List<Keyword> getKeywords();
	
	/**
	 * Questo metodo carica la keyword avente l'id passato come parametro
	 * 
	 * @param id
	 * 
	 * @return La keyword corrispondente all'id passato come parametro
	 */
	
	public Keyword getKeywordById(Integer id);
	
	/**
	 * Questo metodo carica la keyword corrispondente a quella passata come parametro
	 * 
	 * @param key
	 * 
	 * @return La keyword corrispondente a quella passata come parametro
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Keyword getKeywordByWord(String key) throws ClassNotFoundException, IOException;
	
	/**
	 * Metodo usato per ottenere le parole chiave associate ad un prodotto
	 * 
	 * @param idProdotto
	 * 
	 * @return Restituisce la lista delle parole chiave di un prodotto
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Keyword> getKeywordByIdProdotto(Integer idProdotto) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Inserimento parola chiave nel database
	 * 
	 * @param keyword un oggetto keyword
	 * 
	 * @return Un intero che indica l'id della keyword inserita
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int insertKeyword(Keyword keyword) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Inserisce una lista di parola chiave passata come parametro
	 * 
	 * @param keywords lista di parole chiave
	 * @return restituisce l'id dell'ultima keyword inserita, oppure -1 in caso di errore
	 */
	public int insertListaKeyword(List<Keyword> keywords);
		
	/**
	 * Inserisce una parola chiave nel database
	 * 
	 * @param keyword sottoforma di stringa
	 * 
	 * @return Un intero che indica l'id della keyword inserita
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int insertKeyword(String keyword) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Cancella la parola chiave corrispondente a quella passata come parametro dal database
	 * 
	 * @param idKeyword id della keyword
	 * 
	 * @return Un intero che indica il numero di righe cancellate
	 */
	
	public int deleteKeyword(Integer idKeyword);
	
	/**
	 * Modifica la parola chiave corrispondente a quella passata come parametro 
	 * 
	 * @param keyword id della keyword
	 * 
	 * @return Un intero che indica il numero delle righe aggiornata
	 */

	public int updateKeyword(Keyword keyword);
	
}
