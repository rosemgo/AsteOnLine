package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Prodotto;

public interface ProdottoDao {
	
	public List<Prodotto> getProdotti();
	
	public Prodotto getProdottoById(Integer idProdotto);
	
	public List<Prodotto> getProdottiByIdProduttore(Integer idProduttore) throws ClassNotFoundException, SQLException, IOException;
	
	public List<Prodotto> getProdottiByIdCategoriaByIdProduttore(Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException;
			
	/**
	 * Dato il nome di un prodotto, restituisce l'intero prodotto
	 * 
	 * @param nomeProdotto
	 * @return il prodotto associato al nome passato come parametro
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Prodotto getProdottoByName(String nomeProdotto) throws ClassNotFoundException, IOException;


	/**
	 * Inserimento prodotto nel database
	 * 
	 * @param prodotto
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 */
	public Integer insertProdotto(Prodotto prodotto) throws ClassNotFoundException, IOException;
	
	
	
	/**
	 * Effettua l'associazione tra un prodotto e una parola chiave.
	 * @param idProdotto
	 * @param IdKeyword
	 * @return 1 in caso di corretto inserimento, -1 in caso di fallimento
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer insertProdottoHasKeyword(Integer idProdotto, Integer idKeyword) throws ClassNotFoundException, IOException;
	
	/**
	 * Utilizzato per effettuare un controllo al momento dell'inserimento dell'inserzione. Controlla se il prodotto scelto corrisponde alla categoria e al produttore scelti.
	 * 
	 * @param idProdotto
	 * @param idCategoria
	 * @param idProduttore
	 * @return true se l'inserimento è corretto
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkProdottoBelongCategoriaProduttore(Integer idProdotto, Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException;
	
	/**
	 * Controlla se è possibile eliminare un prodotto. Per essere eliminato è necessario che non ci siano inserzioni relative ad esso
	 * 
	 * @param idProdotto
	 * @return true se è possibile eliminare il prodotto, false se non è possibile
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkDeleteProdotto(Integer idProdotto) throws ClassNotFoundException, IOException;
	
	/**
	 * Controllo se la parola chiave è effettivamente associato al prodotto. 
	 * 
	 * @param idProdotto
	 * @param idKeyword
	 * @return true se la parola chiave è associata al prodotto, false in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkProdottoHasKeyword(Integer idProdotto, Integer idKeyword) throws ClassNotFoundException, IOException;
	
	
	

}
