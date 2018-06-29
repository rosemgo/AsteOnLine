package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;

public class ProduttoreDaoMysqlJdbcTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException {

		ProduttoreDao dao = new ProduttoreDaoMysqlJdbc();
		Produttore writingProduttore = new ProduttoreImpl();
		Produttore readingProduttore = null;
		Integer autoIncKey = -1;
		writingProduttore.setNome("TestProduttore");
		writingProduttore.setWebsite("Website Test");
		autoIncKey = dao.insertProduttoreTest(writingProduttore);
		//writingProduttore.setIdProduttore(autoIncKey);

		System.out.println("id produttore: " + writingProduttore.getIdProduttore());
		
		readingProduttore = dao.getProduttoreByNomeTest(writingProduttore.getNome());

		assertEquals(readingProduttore.getNome(), writingProduttore.getNome());
		assertEquals(readingProduttore.getIdProduttore(), writingProduttore.getIdProduttore());
		
		Integer deletedRows = dao.deleteProduttoreTest(readingProduttore.getIdProduttore());
		assertEquals(deletedRows, (Integer)1);
		
	}
	
}
