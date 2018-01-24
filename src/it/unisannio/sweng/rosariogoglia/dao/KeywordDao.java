package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.Keyword;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface KeywordDao {

	public List<Keyword> getKeywords() throws ClassNotFoundException, IOException;
	
	public Keyword getKeywordById(Integer id) throws ClassNotFoundException, IOException;
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Keyword getKeywordByWord(String key) throws ClassNotFoundException, IOException;
	
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
	public int insertListaKeyword(List<Keyword> keywords) throws ClassNotFoundException, IOException;
		
	
	public int insertKeyword(String keyword) throws ClassNotFoundException, SQLException, IOException;
	
	public int deleteKeyword(Integer idKeyword) throws ClassNotFoundException, IOException;
	
	public int updateKeyword(Keyword keyword) throws ClassNotFoundException, IOException;
	
}
