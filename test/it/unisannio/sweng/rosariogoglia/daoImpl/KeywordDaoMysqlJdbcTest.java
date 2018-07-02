package it.unisannio.sweng.rosariogoglia.daoImpl;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.modelImpl.KeywordImpl;

import org.junit.Test;

public class KeywordDaoMysqlJdbcTest {

	@Test
	public void testKeywordDaoMysqlJdbc() throws ClassNotFoundException, SQLException, IOException {
		KeywordDao dao = new KeywordDaoMysqlJdbc();
		Keyword writingKeyword = new KeywordImpl();
		Keyword readingKeyword = null;
		int autoIncKey = -1;
		
		writingKeyword.setKeyword("KeywordTest");
		autoIncKey = dao.insertKeywordTest(writingKeyword);
		writingKeyword.setIdKeyword(autoIncKey);

		readingKeyword = dao.getKeywordByWordTest(writingKeyword.getKeyword());

		assertEquals(readingKeyword.getKeyword(), writingKeyword.getKeyword());
		assertEquals(readingKeyword.getIdKeyword(), writingKeyword.getIdKeyword());
		
		Integer deletedRows = dao.deleteKeywordTest(readingKeyword.getIdKeyword());
		assertEquals(deletedRows, (Integer)1);
	
		
		// verificare la lista di parole choave associate ad un prodotto
		int idProdotto = 3;
		List<Keyword> lista = dao.getKeywordByIdProdottoTest(idProdotto);
		
		for(int i=0; i<lista.size(); i++){
			System.out.println("Parola " + lista.get(i).toString());
		}
		
		
	}

}
