package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Produttore;

public interface ProduttoreDao {
	
	public List<Produttore> getProduttori();
	
	public Produttore getProduttoreById(Integer idProduttore);
	
	public Produttore getProduttoreByNome (String nomeProduttore);
		
		
	public List<Produttore> getProduttoriByIdCategoria(Integer idCategoria);
		
	public Integer insertProduttore (Produttore produttore);
	
	public Integer deleteProduttore (Integer idProduttore);

	/**
	 * Aggiornamento produttore
	 * 
	 * @param produttore
	 * @return 1 se l'aggiornamento � andato a buon fine, -1 altrimenti
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateProduttore(Produttore produttore);
		
	
	/**
	 * Controlla se � possibile eliminare un produttore. Per essere eliminato � necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * @param idProduttore
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkDeleteProduttore(Integer idProduttore);
	
	
	
}
