package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Immagine;

public interface ImmagineDao {
	

	/** 
	 * Questo metodo visualizza l'immagine avente l'id passato come parametro
	 * 
	 * @return L'immagine corrispondente all' id passato come parametro
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Immagine getImmagineById(Integer idImmagine) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Questo metodo carica tutte le immagini dell'inserzione avente l'id passato come parametro
	 * 
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return Restituisce tutte le immagini associate ad un'inserzione
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Immagine> getImmaginiByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
		
	
	/** 
	 * Questo metodo inserisce un'immagine nel database
	 * 
	 * @param immagine l'oggetto immagine
	 * 
	 * @return l'id dell' immagine inserita
	 */
	public Integer insertImmagine(Immagine immagine);

	
	
	/**
	 * Questo metodo elimina un'immagine dal database
	 * 
	 * @param immagine l'oggetto immagine
	 * 
	 * @return Restituisce il numero di righe eliminate dal database,se l'eliminazione fallisce restituisce -1
	 */
	
	public Integer deleteImmagine(Immagine immagine);
	
	/**
	 * Cancella tutte le immagini relative ad un'inserzione
	 * 
	 * @param idInserzione numero identificativo dell'inserzione
	 * 
	 * @return Numero di righe cancellate
	 */
	public Integer deleteImmagineByIdInserzione(Integer idInserzione);
	
	
	

}
