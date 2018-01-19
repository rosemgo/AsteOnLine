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
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Categoria> getCategorie() throws ClassNotFoundException, IOException;
	
	/**
	 * 
	 * @param idCategoria � l'id della categoria da caricare
	 * @return la categoria con id uguale ad idCategoria. E' null se non c'� una categoria con tale id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Categoria getCategoriaById (Integer idCategoria) throws ClassNotFoundException, IOException;
	
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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public List<Produttore> getProduttoriMancantiByIdCategoria(Integer idCategoria) throws ClassNotFoundException, IOException;
	
	/**
	 * Il metodo � utilizzato per associare un produttore ad una categoria effettuanfo un'inserimento nella tabella categoria_has_produttore
	 * 
	 * @param idCategoria
	 * @param IdProduttore
	 * 
	 * @return 1 se l'inserimento � andato a buon fine, -1 in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer insertCategoriaHasProduttore(Integer idCategoria, Integer IdProduttore) throws ClassNotFoundException, IOException;
		
	
	/**
	 * Il metodo � utilizzato per disassociare un produttore ad una categoria effettuando una cancellazione nella tabella categoria_has_produttore
	 * 
	 * @param idCategoria
	 * @param idProduttore
	 *
	 * @return 1 se la cancellazione � andato a buon fine, -1 in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer deleteCategoriaHasProduttore(Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException;
		
	
	/**
	 * Aggiunge la categoria nel db
	 * 
	 * @param categoria
	 * @return l'id della categoria inserita
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer insertCategoria (Categoria categoria) throws ClassNotFoundException, IOException;
	
	/**
	 * 
	 * @param categoria da aggiornare (id � sempre lo stesso, cambia il nome)
	 * @return categoria 1 se la categoria � stata aggiornata, 0 altrimenti
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer updateCategoria (Categoria categoria) throws ClassNotFoundException, IOException;

	/**
	 * Elimina dal db la categoria identificata dall'id passato come parametro
	 * 
	 * @param idCategoria
	 * 
	 * @return 1 se la cancellazione � andata a buon fine, -1 in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Integer deleteCategoria(Integer idCategoria) throws ClassNotFoundException, IOException;
	
	/**
	 * Controlla se � possibile eliminare una categoria. Per essere eliminata � necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * @param idCategoria
	 * @return true se � possibile eliminare la categoria, false se non � possibile
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkDeleteCategoria(Integer idCategoria) throws ClassNotFoundException, IOException;
		
	/**
	 * Controlla se l'associazione Categoria-Produttore sia gi� presente nel db
	 * 
	 * @param idCategoria
	 * @param idProduttore
	 * @return true in caso l'associazione sia presente, false in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkAssociazioneCategoriaProduttore(Integer idCategoria, Integer idProduttore) throws ClassNotFoundException, IOException;
		
}
