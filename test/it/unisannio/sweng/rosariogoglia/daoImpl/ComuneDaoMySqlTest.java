package it.unisannio.sweng.rosariogoglia.daoImpl;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.ComuneDao;
import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.modelImpl.ComuneImpl;

public class ComuneDaoMySqlTest {
	
	@Test
	public void testVisualizzaComuni() throws ClassNotFoundException, SQLException, IOException{
	
		ComuneDao comuneDao = new ComuneDaoMysqlJdbc();
		List<Comune> comuni = new ArrayList<Comune>();
		
		//visualizza tutti i comuni
		comuni = comuneDao.getComuni();
		
		//visualizz il comune dato un id
		Comune comune = new ComuneImpl();
		comune = comuneDao.getComuneById(5227);
		assertEquals("Vitulano", comune.getNomeComune()) ;
		
		//visualizza tutti i comuni di una provincia
		comuni = comuneDao.getComuniByIdProvincia(62);
		
		
		
		
		
	}
	
	

}
