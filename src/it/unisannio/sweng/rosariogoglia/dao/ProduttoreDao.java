package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Produttore;

public interface ProduttoreDao {
	
	public List<Produttore> getProduttori();
	
	public Produttore getProduttoreById(Integer idProduttore);
	
	public Produttore getProduttoreByNome (String nomeProduttore) throws ClassNotFoundException, IOException;
		
		
	public List<Produttore> getProduttoriByIdCategoria(Integer idCategoria);
		
	public Integer insertProduttore (Produttore produttore);
	
	public Integer deleteProduttore (Integer idProduttore);

	/**
	 * Aggiornamento produttore
	 * 
	 * @param produttore
	 * @return 1 se l'aggiornamento è andato a buon fine, -1 altrimenti
	 */
	public Integer updateProduttore(Produttore produttore);
		
	
	/**
	 * Controlla se è possibile eliminare un produttore. Per essere eliminato è necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * @param idProduttore
	 * @return
	 */
	public boolean checkDeleteProduttore(Integer idProduttore);
	
	
	
}
