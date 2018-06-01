package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;


import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet utilizzata per associare una keyword ad un prodotto.
 */
//@WebServlet("/ServletAssociaProdottoKeyword")
public class ServletAssociaProdottoKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String idProdottoString = request.getParameter("prodotto");
		String word = request.getParameter("keyword");
		
		
		ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
		KeywordDao daoK = new KeywordDaoMysqlJdbc();
		
		int insertRow = -1;
		String messaggio = "";
		
		if((idProdottoString != null && !idProdottoString.equals("0")) && (word != null && !word.equals(""))){
			
			Integer idProdotto = Integer.parseInt(idProdottoString);
			
			Keyword keyword = daoK.getKeywordByWord(word);
			
			if(keyword != null){
				
			Integer idKeyword = keyword.getIdKeyword();
			
			insertRow = daoP.insertProdottoHasKeyword(idProdotto, idKeyword);
			
			Prodotto prodotto = daoP.getProdottoById(idProdotto);
						
			if(insertRow != -1){
				
				messaggio = "Prodotto " + prodotto.getNome() + " associato correttamente alla keyword " + keyword.getKeyword() + "!!!";
				
				ServletContext context = getServletContext();
				
				/*Modifico la tabella keyword(contente tutte le parole chiave associate ad ogni prodotto). Per fare ciò, ho utilizzato una procedura diversa dalle precedenti modifiche. 
				 * In pratica, non definisco il metodo aggiornaKeywordMap in questa classe ma, lo definisco nella classe ServletMostraKeyword e lo richiamo su un oggetto che istanzia la classe ServletMostraKeyword*/
				
				ServletMostraKeywordMancanti servlet = (ServletMostraKeywordMancanti) context.getAttribute("ServletMostraKeywordMancanti");
				HashMap<String, String> keywordMancantiMap = new HashMap<String, String>();
				servlet.aggiornaKeywordMancantiMap(keywordMancantiMap);
				context.setAttribute("keywordMancantiMap", keywordMancantiMap);
				
								
				ServletMostraKeyword servlet2 = (ServletMostraKeyword) context.getAttribute("ServletMostraKeyword");
				/*Se ServletMostraKeyword non è stato mai utilizzata e quindi non stato invocato il metodo init, non l'avrò ancora nel contesto*/
				if(servlet2 != null){
					HashMap<String, String> keywordMap = new HashMap<String, String>();
					servlet2.aggiornaKeywordMap(keywordMap);
					context.setAttribute("keywordMap", keywordMap);
				}
				
				
				
			}
			else{
				messaggio = "Prodotto " + prodotto.getNome() + " non associato correttamente alla keyword " + keyword.getKeyword() + "!!!";
			}
		}
		else{
			messaggio = "La parola chiave inserita non è presente!!! Inserire una parola chiave presente nell'elenco!!!";
		}
			
			
		}
		
		
		else {
			messaggio = "Inserisci un prodotto ed una keyword validi!!!";
		}
				
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
		
		
	}

//	
//	private String makeKeywordList(ArrayList<Keyword> keywords) {
//		   String listaKeyword = "";
//		    for(Keyword keyword: keywords) {
//		    	listaKeyword = listaKeyword + keyword.getKeyword() + "<br />";
//		    }
//		    return(listaKeyword);
//	  }
//	
//	
//	public void aggiornaKeywordMap(Map<String, String> keywordMap){
//		System.out.println("entro in aggiornakeyword");
//	    ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
//	    
//	    for(Prodotto prodotto: prodottoDao.getProdotti()) {
//	      keywordMap.put(String.valueOf(prodotto.getIdProdotto()), makeKeywordList((ArrayList<Keyword>) prodotto.getKeywordsList()));
//	    }
//	    keywordMap.put(String.valueOf(0), "Seleziona produttore");
//	}
//	
//	
//	
	
	
	
	
	
	
}
