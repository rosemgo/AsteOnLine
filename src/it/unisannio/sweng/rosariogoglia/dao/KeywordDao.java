package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.Keyword;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface KeywordDao {

	public List<Keyword> getKeywords();
	
	public Keyword getKeywordById(Integer id);
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Keyword getKeywordByWord(String key) throws ClassNotFoundException, IOException;
	
	/**
	 * Metodo usato per ottenere le parole chiave associate ad un prodotto
	 * 
	 * @param idProdotto
	 * @return restituisce la lista delle parole chiave di un prodotto
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Keyword> getKeywordByIdProdotto(Integer idProdotto) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Inserimento paroloa chiave nel database
	 * 
	 * @param keyword un oggetto keyword
	 * @return un intero che indica l'id della keyword inserita
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int insertKeyword(Keyword keyword) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Inserisci una lista di parola chiave passata come parametro
	 * 
	 * @param keywords lista di parole chiave
	 * @return restituisce l'id dell'ultima keyword inserita, oppure -1 in caso di errore
	 */
	public int insertListaKeyword(List<Keyword> keywords);
		
	/**
	 * Inserimento di una parola chiave nel database
	 * 
	 * @param keyword sottoforma di stringa
	 * @return un intero che indica l'id della keyword inserita
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int insertKeyword(String keyword) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Cancellazione di una parola chiave nel database
	 * 
	 * @param idKeyword id della keyword
	 * @return un intero che indica il numero di righe cancellate
	 */
	
	public int deleteKeyword(Integer idKeyword);
	
	/**
	 * Cancellazione di una parola chiave nel database
	 * 
	 * @param keyword id della keyword
	 * @return un intero che indica il numero delle righe aggiornata
	 */

	public int updateKeyword(Keyword keyword);
	
}
