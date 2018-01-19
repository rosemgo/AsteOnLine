package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.Offerta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface OffertaDao {
	
	public Integer insertOfferta(Offerta offerta) throws ClassNotFoundException, IOException;
	
	public Integer deleteOfferta(Offerta offerta) throws SQLException, ClassNotFoundException, IOException;
	
	/**
	 * Cancella tutte le offerte relative ad un'inserzione
	 * 
	 * @param idOfferta
	 * @return Restituisce il numero di righe cancellate
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Integer deleteOffertaByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	
	
}
