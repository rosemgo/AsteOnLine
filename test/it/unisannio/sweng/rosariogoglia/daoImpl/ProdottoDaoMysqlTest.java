package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;
import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.CategoriaImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.KeywordImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProdottoImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ProdottoDaoMysqlTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException {

		
		ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
		Prodotto prodotto = new ProdottoImpl();
		
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
		Categoria categoria = null;
		Integer idCategoria = -1;
		
		ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		Produttore produttore = null;
		Integer idProduttore = -1;
		
		prodotto.setNome("Samsung Galaxy Infinity");
		
		categoria = categoriaDao.getCategoriaByNomeTest("Audio e TV");
		if (categoria==null){
			categoria = new CategoriaImpl();
			categoria.setNome("Audio e TV");
			idCategoria = categoriaDao.insertCategoriaTest(categoria);
			categoria.setIdCategoria(idCategoria);
		}
		prodotto.setIdCategoria(categoria.getIdCategoria());

		produttore = produttoreDao.getProduttoreByNomeTest("Samsung");
		if (produttore==null){
			produttore = new ProduttoreImpl();
			produttore.setNome("Samsung");
			idProduttore = produttoreDao.insertProduttoreTest(produttore);
			produttore.setIdProduttore(idProduttore);
		}
		
		prodotto.setIdProduttore(produttore.getIdProduttore());
		
		List<Keyword> keywordList = new ArrayList<Keyword>(); 
		Keyword keyword = new KeywordImpl();
		Keyword keyword2 = new KeywordImpl();
		keyword.setKeyword("smartphone");
		keywordList.add(keyword);
		keyword2.setKeyword("cellulare");
		keywordList.add(keyword2);
		
		prodotto.setKeywordsList(keywordList);
		
		Integer autoincrementKey =  prodottoDao.insertProdottoTest(prodotto);
		
		System.out.println("AUTO INC: " + autoincrementKey);
		prodotto.setIdProdotto(autoincrementKey);
		
		System.out.println("INSERIMENTO PRODOTTO COMPLETATO");
		
		Prodotto readingProdotto = null;
	
	    readingProdotto = prodottoDao.getProdottoByIdTest(autoincrementKey);
		
		System.out.println(readingProdotto.toString());

		assertEquals(readingProdotto.getNome(), prodotto.getNome());
		assertEquals(readingProdotto.getIdProdotto(), prodotto.getIdProdotto());
		
		Integer deletedRows = prodottoDao.deleteProdottoTest(readingProdotto);
		assertEquals(deletedRows, (Integer)1); //delete rows è 1 se è stata cancellata una riga
	
    /*
		System.out.println("lista parole prodotto ");
		for(int i=0; i<readingProdotto.getKeywordsList().size(); i++){
			System.out.println(readingProdotto.getKeywordsList().get(i).getKeyword());
		}
		
	
		Keyword keyword3 = new KeywordImpl();
		keyword3.setKeyword("Provaaaa");
		
		List<Keyword> keywordList = readingProdotto.getKeywordsList();
		keywordList.set(1, keyword3);
		
		System.out.println("lista parole prodotto dopo modifica");
		for(int i=0; i<readingProdotto.getKeywordsList().size(); i++){
			System.out.println(readingProdotto.getKeywordsList().get(i).getKeyword());
		}
		
		
		readingProdotto.setDescrizione("Ultimissima generazione");
		readingProdotto.setKeywordsList(keywordList);
		
		for(int i=0; i<keywordList.size(); i++){
			System.out.println("La parola nella posizione " + i +" è: " + readingProdotto.getKeywordsList().get(i).getKeyword());
		}
		
		
		Integer updatedRows = prodottoDao.updateProdotto(readingProdotto);
		assertEquals(updatedRows, (Integer)1);
	*/
		
	}
	
}
