package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Provincia;

public interface ProvinciaDao {
	
	/**
	 * Questo metodo fornisce la provincia che ha l'id corrispondente a quello passato come parametro
	 * 
	 * @param idProvincia
	 * 
	 * @return La provincia con id coincidente a quello passato come parametro
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	
	public Provincia getProvinciaById(Integer idProvincia) throws ClassNotFoundException, SQLException, IOException;
	
	/**
	 * Questo metodo fornisce la lista di tutte le province
	 * 
	 * @return La lista di tutte le province 
	 */
	
	public List<Provincia> getProvince();
		
	
}
