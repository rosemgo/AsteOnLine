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
import net.sourceforge.htmlunit.corejs.javascript.ast.KeywordLiteral;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ProdottoDaoMysqlTest {

	@Test
	public void testInserimentoRimozioneProdotto() throws ClassNotFoundException, SQLException, IOException {

		
		ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
		Prodotto prodotto = new ProdottoImpl();
		
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
		Categoria categoria = null;
		Integer idCategoria = -1;
		
		ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		Produttore produttore = null;
		Integer idProduttore = -1;
		
		prodotto.setNome("Samsung Galaxy Infinity");
		
		categoria = categoriaDao.getCategoriaByNome("Audio e TV");
		if (categoria==null){
			categoria = new CategoriaImpl();
			categoria.setNome("Audio e TV");
			idCategoria = categoriaDao.insertCategoria(categoria);
			categoria.setIdCategoria(idCategoria);
		}
		prodotto.setIdCategoria(categoria.getIdCategoria());

		produttore = produttoreDao.getProduttoreByNome("Samsung");
		if (produttore==null){
			produttore = new ProduttoreImpl();
			produttore.setNome("Samsung");
			idProduttore = produttoreDao.insertProduttore(produttore);
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
		
		Integer autoincrementKey =  prodottoDao.insertProdotto(prodotto);
		
		//controllo le parole non associate al prodotto
		keywordList.removeAll(keywordList);
		keywordList = prodottoDao.getKeywordMancantiByIdProdotto(prodotto.getIdProdotto());
		assertEquals(keywordList.get(0).getKeyword(), "4k");
		
		//associo keywords a prodotto
		prodottoDao.insertProdottoHasKeyword(prodotto.getIdProdotto(), keyword.getIdKeyword());
		
		System.out.println("AUTO INC: " + autoincrementKey);
		prodotto.setIdProdotto(autoincrementKey);
		
		System.out.println("INSERIMENTO PRODOTTO COMPLETATO");
		
		Prodotto readingProdotto = null;
	
	    readingProdotto = prodottoDao.getProdottoById(autoincrementKey);
		
		System.out.println(readingProdotto.toString());

		assertEquals(readingProdotto.getNome(), prodotto.getNome());
		assertEquals(readingProdotto.getIdProdotto(), prodotto.getIdProdotto());
		
		//aggiorno il prodotto modificando il nome della categoria e la keywordList
		categoria.setNome("abcdef");
		prodotto.setCategoria(categoria);
		keywordList.removeAll(keywordList);
		keyword.setKeyword("abcd");
		keywordList.add(keyword);
		prodotto.setKeywordsList(keywordList);
		
		Integer updateRows = prodottoDao.updateProdotto(prodotto);
		assertEquals(updateRows, (Integer)1);
		
		//disassocio parola chiave e prodotto
		prodottoDao.deleteProdottoHasKeyword(prodotto.getIdProdotto(), keyword.getIdKeyword());
		
		//aggiorno il nome del prodotto
		updateRows = -1;
		prodotto.setNome("Apple");
		updateRows = prodottoDao.updateNomeProdotto(prodotto);
		
		Integer deletedRows = prodottoDao.deleteProdotto(readingProdotto);
		assertEquals(deletedRows, (Integer)1); //delete rows è 1 se è stata cancellata una riga
	
		System.out.println("CANCELLAZIONE PRODOTTO COMPLETATO");
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
	
	@Test
	public void testGetProdotto() {
		
		//AGGIUNGERE GETPRODOTTI
		
				//get prodotti by id categoria e id produttore
				
				ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
				List<Prodotto> result = new ArrayList<Prodotto>();
				result = prodottoDao.getProdottiByIdCategoriaByIdProduttore(3, 7);
				assertEquals(result.get(0).getIdProdotto(),45);
				assertEquals(result.get(1).getIdProdotto(),46);
				
				//get prodotto by nome
				
				Prodotto result2 = new ProdottoImpl();
				result2 = prodottoDao.getProdottoByName("iPhone X");
				assertEquals(result2.getIdProdotto(), 4);
				
				//get prodotti
				result.removeAll(result);
				result = prodottoDao.getProdotti();
				assertEquals(result.get(0).getIdProdotto(),11);
				
				
				
		
	}
	
	@Test
	public void testCheckProdotto() {
		
				//check prodotto appartiene a categoria e produttore
		
				ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
				boolean result3;
				result3 = prodottoDao.checkProdottoBelongCategoriaProduttore(5, 3, 1);
				assertEquals(result3 , true);
				
				//check delete prodotto
				
				boolean result4;
				result4 = prodottoDao.checkDeleteProdotto(10);
				assertEquals(result4 , true);
				
				//check keyword associata al prodotto
				
				boolean result5;
				result5 = prodottoDao.checkProdottoHasKeyword(3, 2);
				assertEquals(result5 , true);



		
	}
	
}
