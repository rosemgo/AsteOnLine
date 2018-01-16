package it.unisannio.sweng.rosariogoglia.dao;


import it.unisannio.sweng.rosariogoglia.model.Inserzione;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface InserzioneDao {
	
	/**
	 * Questo metodo carica tutte le inserzioni presenti nel database
	 * 
	 * @param idInserzione
	 * @return restituisce l'inserzione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Inserzione> getInserzioni() throws ClassNotFoundException, IOException;
		

	
	
	/**
	 * Questo metodo carica l'intera inserzione con la lista immagini e la lista di offerte
	 * @param idInserzione
	 * @return restituisce l'inserzione
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Inserzione getInserzioneById(Integer idInserzione);
	


	
			
	
}
