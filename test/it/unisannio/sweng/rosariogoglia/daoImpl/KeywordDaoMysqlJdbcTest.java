package it.unisannio.sweng.rosariogoglia.daoImpl;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.modelImpl.KeywordImpl;

import org.junit.Test;

public class KeywordDaoMysqlJdbcTest {

	@Test
	public void testInserimentoRimozioneKeyword() throws ClassNotFoundException, SQLException, IOException {
		KeywordDao dao = new KeywordDaoMysqlJdbc();
		Keyword writingKeyword = new KeywordImpl();
		Keyword readingKeyword = null;
		int autoIncKey = -1;
		
		writingKeyword.setKeyword("KeywordTest");
		autoIncKey = dao.insertKeyword(writingKeyword);
		writingKeyword.setIdKeyword(autoIncKey);

		readingKeyword = dao.getKeywordByWord(writingKeyword.getKeyword());

		assertEquals(readingKeyword.getKeyword(), writingKeyword.getKeyword());
		assertEquals(readingKeyword.getIdKeyword(), writingKeyword.getIdKeyword());
		
		Integer deletedRows = dao.deleteKeyword(readingKeyword.getIdKeyword());
		assertEquals(deletedRows, (Integer)1);
		
		//Inserisco una lista d parole chiave
		List<Keyword> keywordList = new ArrayList<Keyword>();
		Keyword keyword1 = new KeywordImpl("keyTest1");
		Keyword keyword2 = new KeywordImpl("keyTest2");
		keywordList.add(keyword1);
		keywordList.add(keyword2);
		KeywordDao keyDao = new KeywordDaoMysqlJdbc();
		Integer rowid = keyDao.insertListaKeyword(keywordList);
		assertEquals(rowid, (Integer)40);
		
		//aggiorno una parola chiave
		keyword1.setKeyword("UpdateKey");
		Integer updateRows = keyDao.updateKeyword(keyword1);
		assertEquals(updateRows, (Integer)1);
		
		
		deletedRows = -1;
		deletedRows = dao.deleteKeyword(keywordList.get(0).getIdKeyword());
		assertEquals(deletedRows, (Integer)1);
		deletedRows = -1;
		deletedRows = dao.deleteKeyword(keywordList.get(1).getIdKeyword());
		assertEquals(deletedRows, (Integer)1);
		
		
		
		// verificare la lista di parole chiave associate ad un prodotto
		int idProdotto = 3;
		List<Keyword> lista = dao.getKeywordByIdProdotto(idProdotto);
		
		for(int i=0; i<lista.size(); i++){
			System.out.println("Parola " + lista.get(i).toString());
		}
		
		
	}
	
	@Test
	public void testKeywordbyId() {
		
				//get Keyword by id
		
				KeywordDao dao = new KeywordDaoMysqlJdbc(); 
				Keyword result = new KeywordImpl();
				result = dao.getKeywordById(22);
				assertEquals(result.getKeyword() , "Pc");
				
				//get Keywords
				
				List<Keyword> result2 = new ArrayList<Keyword>();
				result2 = dao.getKeywords();
				assertEquals(result2.get(0).getKeyword() , "4k");
				
				
}
	

}
