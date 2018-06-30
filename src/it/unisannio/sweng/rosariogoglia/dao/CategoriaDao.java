package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;


public interface CategoriaDao {

	
	/**
	 * Questo metodo carica tutte le Categorie presenti nel sistema
	 * 
	 * @return Tutte le categorie censite dal sistema
	 */
	public List<Categoria> getCategorie();
	
	/**
	 * Questo metodo carica tutte le Categorie presenti nel sistema
	 * 
	 *  Usato per test con connessione DatabaseUtil
	 * 
	 * @return Tutte le categorie censite dal sistema
	 */
	public List<Categoria> getCategorieTest();
	
	/**
	 * Questo metodo carica la Categoria avente l'id passato come parametro
	 * 
	 * @param idCategoria è l'id della categoria da caricare
	 * 
	 * @return La categoria con id uguale ad idCategoria. E' null se non c'è una categoria con tale id
	 */
	public Categoria getCategoriaById (Integer idCategoria);
	
	/**
	 * Questo metodo carica la Categoria avente l'id passato come parametro
	 * 
	 * Usato per test con connessione DatabaseUtil
	 * 
	 * @param idCategoria è l'id della categoria da caricare
	 * 
	 * @return La categoria con id uguale ad idCategoria. E' null se non c'è una categoria con tale id
	 */
	public Categoria getCategoriaByIdTest (Integer idCategoria);
	
	
	/**
	 * Dato il nome della categoria, la restituisce se presente, altrimenti restiruisce null
	 * 
	 * @param nomeCategoria nome della categoria
	 * 
	 * @return Restituisce la categoria se presente, altrimenti restiruisce null
	 * 
	 */
	public Categoria getCategoriaByNome (String nomeCategoria);
	
	/**
	 * Dato il nome della categoria, la restituisce se presente, altrimenti restiruisce null
	 * 
	 * Usato per eseguire il test con connessione DatabaseUtil 
	 * 
	 * @param nomeCategoria nome della categoria
	 * 
	 * @return Restituisce la categoria se presente, altrimenti restiruisce null
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Categoria getCategoriaByNomeTest (String nomeCategoria) throws ClassNotFoundException, IOException;
	
	/**
	 * Restituisce la lista dei produttori non ancora associati alla categoria indicata dall'id passato come parametro
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return La lista dei produttori non ancora associati alla categoria
	 */
	public List<Produttore> getProduttoriMancantiByIdCategoria(Integer idCategoria);
	
	/**
	 * Restituisce la lista dei produttori non ancora associati alla categoria indicata dall'id passato come parametro
	 * 
	 * Usato per eseguire il test con connessione DatabaseUtil
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return La lista dei produttori non ancora associati alla categoria
	 */
	public List<Produttore> getProduttoriMancantiByIdCategoriaTest(Integer idCategoria);
	
	
	/**
	 * Il metodo è utilizzato per associare un produttore ad una categoria effettuanfo un'inserimento nella tabella categoria_has_produttore
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * @param IdProduttore numero identificativo del produttore
	 * 
	 * @return 1 se l'inserimento è andato a buon fine, -1 in caso contrario
	 */
	public Integer insertCategoriaHasProduttore(Integer idCategoria, Integer IdProduttore);
		
	
	/**
	 * Il metodo è utilizzato per disassociare un produttore ad una categoria effettuando una cancellazione nella tabella categoria_has_produttore
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 *
	 * @return 1 se la cancellazione è andato a buon fine, -1 in caso contrario
	 */
	public Integer deleteCategoriaHasProduttore(Integer idCategoria, Integer idProduttore);
		
	
	/**
	 * Aggiunge la categoria nel db
	 * 
	 * @param categoria l'oggetto categoria da inserire
	 * 
	 * @return L'id della categoria inserita
	 */
	public Integer insertCategoria (Categoria categoria);
	
	
	
	
	/**
	 * Aggiunge la categoria nel db
	 * 
	 * Usato con connessione DatabaseUtil 
	 * 
	 * @param categoria l'oggetto categoria da inserire
	 * 
	 * @return L'id della categoria inserita
	 */
	public Integer insertCategoriaTest (Categoria categoria);
	
	
	
	/**
	 * Questo metodo aggiorna il nome di una categoria presente nel sistema
	 * 
	 * @param categoria da aggiornare (id è sempre lo stesso, cambia il nome)
	 * 
	 * @return 1 se la categoria è stata aggiornata, 0 altrimenti
	  */
	public Integer updateCategoria (Categoria categoria);

	/**
	 * Elimina dal db la categoria identificata dall'id passato come parametro
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return 1 se la cancellazione è andata a buon fine, -1 in caso contrario
	 */
	public Integer deleteCategoria(Integer idCategoria);
	
	/**
	 * Elimina dal db la categoria identificata dall'id passato come parametro
	 * 
	 * Usato con connessione DatabaseUtil 
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return 1 se la cancellazione è andata a buon fine, -1 in caso contrario
	 */
	public Integer deleteCategoriaTest(Integer idCategoria);
	
	/**
	 * Controlla se è possibile eliminare una categoria. Per essere eliminata è necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return True se è possibile eliminare la categoria, false se non è possibile
	  */
	public boolean checkDeleteCategoria(Integer idCategoria);
		
	/**
	 * Controlla se è possibile eliminare una categoria. Per essere eliminata è necessario che non ci siano inserzioni con prodotti ad essa associati
	 * 
	 * Usato con connessione DatabaseUtil
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * 
	 * @return True se è possibile eliminare la categoria, false se non è possibile
	  */
	public boolean checkDeleteCategoriaTest(Integer idCategoria);
	
	/**
	 * Controlla se l'associazione Categoria-Produttore sia già presente nel db
	 * 
	 * @param idCategoria numero identificativo della categoria
	 * @param idProduttore numero identificativo del produttore
	 * 
	 * @return True in caso l'associazione sia presente, false in caso contrario
	 */
	public boolean checkAssociazioneCategoriaProduttore(Integer idCategoria, Integer idProduttore);
		
}
