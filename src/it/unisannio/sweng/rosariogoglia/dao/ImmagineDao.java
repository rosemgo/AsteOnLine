package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Immagine;

public interface ImmagineDao {
	

	/** 
	 * @return l'immagine corrispondente all' id passato come parametro
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Immagine getImmagineById(Integer idImmagine) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * 
	 * @param idInserzione
	 * @return Dato l'id di un'inserzione ne restituisce tutte le immagini associate a quell'inserzione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Immagine> getImmaginiByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
		
	/** 
	 * @return l'id dell' immagine inserita
	 */
	public Integer insertImmagine(Immagine immagine);

	
	
	public Integer deleteImmagine(Immagine immagine);
	
	/**
	 * Cancella tutte le immagini relative ad un'inserzione
	 * @param idInserzione
	 * @return numero di righe cancellate
	 */
	public Integer deleteImmagineByIdInserzione(Integer idInserzione);
	
	
	

}
