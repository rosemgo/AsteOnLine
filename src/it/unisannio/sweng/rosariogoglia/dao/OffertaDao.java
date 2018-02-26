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
	 * @return un intero che indica l'id dell'offerta inserita
	 */
	public Integer insertOfferta(Offerta offerta);
	
	/**
	 * Cancella offerta specifica
	 * 
	 * @param offerta
	 * @return un intero che indica il numero delle righe cancellate
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Integer deleteOfferta(Offerta offerta) throws SQLException, ClassNotFoundException, IOException;
	
	/**
	 * Cancella tutte le offerte relative ad un'inserzione
	 * 
	 * @param idInserzione
	 * @return Restituisce il numero di righe cancellate
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Integer deleteOffertaByIdInserzione(Integer idInserzione) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Metodo utilizzato per ottenere l'oggetto offerta relativo al suo id.
	 * 
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
	 */
	public List<Offerta> getOfferteByIdInserzione(Integer idInserzione);	

}
