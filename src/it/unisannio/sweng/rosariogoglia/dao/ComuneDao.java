package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Comune;

public interface ComuneDao {
	
	/**
	 * Questo metodo carica il comune avente l'id passato come parametro
	 * 
	 * @param idComune
	 * 
	 * @return Restituisce il comune
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public Comune getComuneById(int idComune) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Questo metodo visualizza la lista di tutti i comuni
	 * 
	 * @return La lista contenente tutti i comuni
	 */
	
	public List<Comune> getComuni();
	
	/**
	 * Questo metodo visualizza tutti i comuni aventi l'idProvincia passato come parametro.
	 * 
	 * @param idProvincia
	 * 
	 * @return La lista dei comuni appartenenti ad una provincia
	 */
	
	public List<Comune> getComuniByIdProvincia(Integer idProvincia);
	
}
