package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Provincia;

public interface ProvinciaDao {
	
	public Provincia getProvinciaById(Integer idProvincia) throws ClassNotFoundException, SQLException, IOException;
	
	public List<Provincia> getProvince() throws ClassNotFoundException, IOException;
		
	
}
