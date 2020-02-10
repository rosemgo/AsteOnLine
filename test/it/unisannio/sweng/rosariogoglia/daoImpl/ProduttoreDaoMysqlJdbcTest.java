package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;

public class ProduttoreDaoMysqlJdbcTest {

	@Test
	public void testInserimentoRimozioneProduttore() throws ClassNotFoundException, SQLException, IOException {

		ProduttoreDao dao = new ProduttoreDaoMysqlJdbc();
		Produttore writingProduttore = new ProduttoreImpl();
		Produttore readingProduttore = null;
		Integer autoIncKey = -1;
		Integer updateRows = -1;
		writingProduttore.setNome("TestProduttore");
		writingProduttore.setWebsite("Website Test");
		autoIncKey = dao.insertProduttore(writingProduttore);
		//writingProduttore.setIdProduttore(autoIncKey);

		System.out.println("id produttore: " + writingProduttore.getIdProduttore());
		
		readingProduttore = dao.getProduttoreByNome(writingProduttore.getNome());

		assertEquals(readingProduttore.getNome(), writingProduttore.getNome());
		assertEquals(readingProduttore.getIdProduttore(), writingProduttore.getIdProduttore());
		
		//aggiorno il nome e il sito web del produttore
		writingProduttore.setNome("updatepProduttore");
		writingProduttore.setWebsite("update website");
		updateRows = dao.updateProduttore(writingProduttore);
		assertEquals((Integer)1, updateRows);
		
		boolean result = false;
		result = dao.checkDeleteProduttore(writingProduttore.getIdProduttore());
		assertEquals(true, result);
		Integer deletedRows = dao.deleteProduttore(readingProduttore.getIdProduttore());
		assertEquals(deletedRows, (Integer)1);
		
	}
	
	@Test
	public void testGetProduttore() {
		
		//lista produttori nel db
		
		ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		List<Produttore> result = new ArrayList<Produttore>();
		result = produttoreDao.getProduttori();
		assertEquals("Adidas", result.get(0).getNome());
		assertEquals("Apple", result.get(1).getNome());
		
		//metodo che restituisce un produttora dall'id
			
		Produttore produttore = new ProduttoreImpl();
		produttore = produttoreDao.getProduttoreById(3);
		assertEquals("HP", produttore.getNome());
		
		//lista produttori per categoria
			
		result.removeAll(result);
		result = produttoreDao.getProduttoriByIdCategoria(3);
		assertEquals("Samsung", result.get(0).getNome());
		assertEquals("LG", result.get(1).getNome());
		assertEquals("Apple", result.get(2).getNome());
		assertEquals("Huawei", result.get(3).getNome());
		
		
	}
	
}
