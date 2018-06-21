package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Produttore;

public interface ProduttoreDao {
	
	/**
	 * Questo metodo fornisce la lista di tutti i produttori presenti nel database
	 * 
	 * @return La lista dei produttori presenti nel database
	 */
	
	public List<Produttore> getProduttori();
	
	/**
	 * Questo metodo fornisce il produttore avente l'id coincidente con quello passato come parametro
	 * 
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return Il produttore avente l'id passato come parametro
	 */
	
	public Produttore getProduttoreById(Integer idProduttore);
	
	/**
	 * Questo metodo fornisce il produttore avente l'id coincidente con quello passato come parametro
	 * Usato per Test
	 * 
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return Il produttore avente l'id passato come parametro
	 */
	public Produttore getProduttoreByIdTest(Integer idProduttore);
		
	
	
	/**
	 * Questo metodo fornisce il produttore avente il nome corrispondente a quello passato come parametro
	 * 
	 * @param nomeProduttore nome del produttore da visualizzare
	 * 
	 * @return Il produttore che ha il nome corrispondente a quello passato come parametro
	 * 
	 */
	
	public Produttore getProduttoreByNome (String nomeProduttore);
	
	
	/**
	 * Questo metodo fornisce il produttore avente il nome corrispondente a quello passato come parametro
	 * 
	 * Usato per il test con connessione DatabaseUtil
	 * 
	 * @param nomeProduttore nome del produttore da visualizzare
	 * 
	 * @return Il produttore che ha il nome corrispondente a quello passato come parametro
	 * 
	 */
	
	public Produttore getProduttoreByNomeTest(String nomeProduttore);
	
	
	/**
	 * Questo metodo fornisce la lista dei produttori che hanno l'id della categoria coincidente a quello passato come parametro
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return La lista dei produttori aventi l'id della categoria coincidente a quello passato come parametro
	 */
		
	public List<Produttore> getProduttoriByIdCategoria(Integer idCategoria);
	
	/**
	 * Questo metodo inserisce il produttore passato come parametro nel database
	 * 
	 * @param produttore oggetto produttore da inserire nel database
	 * 
	 * @return 1 se l'inserimento ha successo, -1 se l'inserimento fallisce
	 */
		
	public Integer insertProduttore (Produttore produttore);
	
	
	/**
	 * Questo metodo inserisce il produttore passato come parametro nel database
	 * 
	 * Usato per fare il test con connessione DatabaseUtil
	 * 
	 * @param produttore oggetto produttore da inserire nel database
	 * 
	 * @return 1 se l'inserimento ha successo, -1 se l'inserimento fallisce
	 */
	public Integer insertProduttoreTest(Produttore produttore);
	
	
	
	/**
	 * Elimina dal database il produttore avente l'id passato come parametro 
	 * 
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return 1 se l'eliminazione ha successo, -1 se l'eliminazione fallisce
	 */
	
	public Integer deleteProduttore (Integer idProduttore);

	/**
	 * Aggiorna uno o più attributi del produttore
	 * 
	 * @param produttore oggetto produttore da inserire nel database
	 * 
	 * @return 1 se l'aggiornamento è andato a buon fine, -1 altrimenti
	 */
	public Integer updateProduttore(Produttore produttore);
		
	
	/**
	 * Controlla se è possibile eliminare un produttore. Per essere eliminato è necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return True se è possibile eliminare il produttore, false se non è possibile
	 */
	public boolean checkDeleteProduttore(Integer idProduttore);
	
	
	
}
