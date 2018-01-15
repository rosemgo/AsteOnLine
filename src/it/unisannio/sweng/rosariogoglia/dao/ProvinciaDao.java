package it.unisannio.sweng.rosariogoglia.dao;

import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Provincia;

public interface ProvinciaDao {
	
	public Provincia getProvinciaById(Integer idProvincia);
	
	public List<Provincia> getProvince();
		
	
}
