package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Produttore;

public interface ProduttoreDao {
	
	public List<Produttore> getProduttori();
	
	public Produttore getProduttoreById(Integer idProduttore) throws ClassNotFoundException, IOException;
	
	public Produttore getProduttoreByNome (String nomeProduttore) throws ClassNotFoundException, IOException;
		
		
	public List<Produttore> getProduttoriByIdCategoria(Integer idCategoria) throws ClassNotFoundException, IOException;
		
	public Integer insertProduttore (Produttore produttore) throws ClassNotFoundException, IOException;
	
	public Integer deleteProduttore (Integer idProduttore) throws ClassNotFoundException, IOException;

	/**
	 * Aggiornamento produttore
	 * 
	 * @param produttore
	 * @return 1 se l'aggiornamento è andato a buon fine, -1 altrimenti
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer updateProduttore(Produttore produttore) throws ClassNotFoundException, IOException;
		
	
	/**
	 * Controlla se è possibile eliminare un produttore. Per essere eliminato è necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * @param idProduttore
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkDeleteProduttore(Integer idProduttore) throws ClassNotFoundException, IOException;
	
	
	
}
