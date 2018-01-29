package it.unisannio.sweng.rosariogoglia.dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Comune;

public interface ComuneDao {
	
	public Comune getComuneById(int idComune) throws ClassNotFoundException, SQLException, IOException;
	
	public List<Comune> getComuni();
	
	public List<Comune> getComuniByIdProvincia(Integer idProvincia);
	
}
