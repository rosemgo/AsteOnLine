package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.ProvinciaDao;
import it.unisannio.sweng.rosariogoglia.model.Provincia;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProvinciaImpl;


public class ProvinciaDaoMySqlTest {
	
	@Test
	public void testVisualizzaProvince() throws ClassNotFoundException, SQLException, IOException{
	
		ProvinciaDao provinciaDao = new ProvinciaDaoMysqlJdbc();
		List<Provincia> province = new ArrayList<Provincia>();
		
		//visualizza tutte le province
		province = provinciaDao.getProvince();
				
		//visualizz la provincia dato un id
		Provincia provincia = new ProvinciaImpl();
		provincia = provinciaDao.getProvinciaById(62);
		assertEquals("Benevento", provincia.getNomeProvincia()) ;
		
		
	}
	
	

}
