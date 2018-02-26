package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;


public interface CategoriaDao {

	
	/**
	 * 
	 * @return tutte le categorie censite dal sistema
	 */
	public List<Categoria> getCategorie();
	
	/**
	 * 
	 * @param idCategoria è l'id della categoria da caricare
	 * @return la categoria con id uguale ad idCategoria. E' null se non c'è una categoria con tale id
	 */
	public Categoria getCategoriaById (Integer idCategoria);
	
	/**
	 * Dato il nome della categoria, la restituisce se presente, altrimenti restiruisce null
	 * @param nomeCategoria
	 * @return restituisce la categoria se presente, altrimenti restiruisce null
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Categoria getCategoriaByNome (String nomeCategoria) throws ClassNotFoundException, IOException;
	
	/**
	 * Restituisce la lista dei produttori non ancora associati alla categoria indicata dall'id passato come parametro
	 * 
	 * @param idCategoria
	 * @return la lista dei produttori non ancora associati alla categoria
	 */
	public List<Produttore> getProduttoriMancantiByIdCategoria(Integer idCategoria);
	
	/**
	 * Il metodo è utilizzato per associare un produttore ad una categoria effettuanfo un'inserimento nella tabella categoria_has_produttore
	 * 
	 * @param idCategoria
	 * @param IdProduttore
	 * 
	 * @return 1 se l'inserimento è andato a buon fine, -1 in caso contrario
	 */
	public Integer insertCategoriaHasProduttore(Integer idCategoria, Integer IdProduttore);
		
	
	/**
	 * Il metodo è utilizzato per disassociare un produttore ad una categoria effettuando una cancellazione nella tabella categoria_has_produttore
	 * 
	 * @param idCategoria
	 * @param idProduttore
	 *
	 * @return 1 se la cancellazione è andato a buon fine, -1 in caso contrario
	 */
	public Integer deleteCategoriaHasProduttore(Integer idCategoria, Integer idProduttore);
		
	
	/**
	 * Aggiunge la categoria nel db
	 * 
	 * @param categoria
	 * @return l'id della categoria inserita
	 */
	public Integer insertCategoria (Categoria categoria);
	
	/**
	 * 
	 * @param categoria da aggiornare (id è sempre lo stesso, cambia il nome)
	 * @return categoria 1 se la categoria è stata aggiornata, 0 altrimenti
	  */
	public Integer updateCategoria (Categoria categoria);

	/**
	 * Elimina dal db la categoria identificata dall'id passato come parametro
	 * 
	 * @param idCategoria
	 * 
	 * @return 1 se la cancellazione è andata a buon fine, -1 in caso contrario
	 */
	public Integer deleteCategoria(Integer idCategoria);
	
	/**
	 * Controlla se è possibile eliminare una categoria. Per essere eliminata è necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * @param idCategoria
	 * @return true se è possibile eliminare la categoria, false se non è possibile
	  */
	public boolean checkDeleteCategoria(Integer idCategoria);
		
	/**
	 * Controlla se l'associazione Categoria-Produttore sia già presente nel db
	 * 
	 * @param idCategoria
	 * @param idProduttore
	 * @return true in caso l'associazione sia presente, false in caso contrario
	 */
	public boolean checkAssociazioneCategoriaProduttore(Integer idCategoria, Integer idProduttore);
		
}
