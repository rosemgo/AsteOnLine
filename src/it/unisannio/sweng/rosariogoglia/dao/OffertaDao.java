package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.Offerta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface OffertaDao {
	
	/**
	 * Inserisce un'offerta
	 * 
	 * @param offerta
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Integer insertOfferta(Offerta offerta);
	
	/**
	 * Cancella offerta specifica
	 * 
	 * @param offerta
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
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
	
	/**
	 * @param idOfferta
	 * @return Restituisce l'oggetto offerta corrispondente all' id passato come paramentro
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Offerta getOffertaByIdOfferta(Integer idOfferta) throws ClassNotFoundException, SQLException, IOException;	
	
	/**
	 * Restituisce una lista di offerte relative ad un'inserzione, indicata dal parametro idInserzione
	 * 
	 * @param idInserzione
	 * @return lista di offerte relative all'inserzione
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Offerta> getOfferteByIdInserzione(Integer idInserzione);	

}
