package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Categoria;


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
	 * @param idCategoria è l'id della categoria da caricare
	 * @return la categoria con id uguale ad idCategoria. E' null se non c'è una categoria con tale id
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
	
	
		
}
