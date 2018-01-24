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
	 * @throws SQLException
	 * @throws IOException
	 */
	public Keyword getKeywordByWord(String key);
	
	/**
	 * 
	 * @param idProdotto
	 * @return restituisce la lista delle parole chiave di un prodotto
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Keyword> getKeywordByIdProdotto(Integer idProdotto) throws ClassNotFoundException, SQLException, IOException;
		
	public int insertKeyword(Keyword keyword) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Inserisci una lista di parola chiave passata come parametro
	 * 
	 * @param keywords
	 * @return restituisce l'id dell'ultima keyword inserita, oppure -1 in caso di errore
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public int insertListaKeyword(List<Keyword> keywords);
		
	
	public int insertKeyword(String keyword) throws ClassNotFoundException, SQLException, IOException;
	
	public int deleteKeyword(Integer idKeyword);
	
	public int updateKeyword(Keyword keyword);
	
}
