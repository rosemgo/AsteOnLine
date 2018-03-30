package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.servlets.BaseAjaxServlet;

/**
 * Servlet implementation class ServletMostraKeywordMancanti
 */
//@WebServlet("/ServletMostraKeywordMancanti")
public class ServletMostraKeywordMancanti extends BaseAjaxServlet {
private static final long serialVersionUID = -3534695539530815007L;
	
	private Map<String, String> keywordMancantiMap;

	
	
	public void init() {
	    keywordMancantiMap = new HashMap<String,String>();
	    
	    ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
	    
	    for(Prodotto prodotto: prodottoDao.getProdotti()) {
	      keywordMancantiMap.put(String.valueOf(prodotto.getIdProdotto()), makeKeywordList((ArrayList<Keyword>) prodottoDao.getKeywordMancantiByIdProdotto(prodotto.getIdProdotto())));
	    }
	   
	    keywordMancantiMap.put(String.valueOf(0), "Seleziona produttore");
	     
	    ServletContext context = this.getServletConfig().getServletContext();
	    context.setAttribute("keywordMancantiMap", keywordMancantiMap);
	    
	    
	    /*Tecnica utilizzata per poter chiamare i metodi di questa classe in un'altra classe su un oggetto che istanzia ServletMostraKeyword.
	     *Metto nel contesto un oggetto di questa classe, in modo tale di poterla richiamare in un'altra classe. 
	     *Questo è necessario perchè il metodo context.getServlet("nome servlet") è stato deprecated!!!*/
	    ServletMostraKeywordMancanti servlet = new ServletMostraKeywordMancanti();
	    context.setAttribute("ServletMostraKeywordMancanti", servlet);
	  }
	
	
	 private String makeKeywordList(ArrayList<Keyword> keywords) {
		   String listaKeyword = "";
		    for(Keyword keyword: keywords) {
		    	listaKeyword = listaKeyword + keyword.getKeyword() + "<br />";
		    }
		    return(listaKeyword);
	  }
	
	
	
	@SuppressWarnings("unchecked")
	public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String prodotto = request.getParameter("prodotto");
	    System.out.println("servlet mostra keyword mancanti - RICEVO il prodotto: " + prodotto);
	    
//	    HttpSession session = request.getSession(true);
//	    session.setAttribute("categoriaScelta", categoria);
	      
	    if(prodotto == null || prodotto == ""){
	    	prodotto = "0";
	    }
	  
	    String listaKeywords;
		   
	    ServletContext context = this.getServletConfig().getServletContext();
	    keywordMancantiMap = (Map<String, String>) context.getAttribute("keywordMancantiMap");
	   	listaKeywords = keywordMancantiMap.get(prodotto);
	     
	   	if(listaKeywords == null || listaKeywords == "")
	   		listaKeywords = "Nessuna parola chiave da poter associare";
	   	
	    System.out.println("lista: " + listaKeywords);
	    
	    if (keywordMancantiMap == null) {
	      return("");
	    } else { 
	      return(listaKeywords);
	    }
		
	}
	
	
	public void aggiornaKeywordMancantiMap(Map<String, String> keywordMap){
		System.out.println("entro in aggiornakeyword");
	    ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
	    
	    for(Prodotto prodotto: prodottoDao.getProdotti()) {
	      keywordMap.put(String.valueOf(prodotto.getIdProdotto()), makeKeywordList((ArrayList<Keyword>) prodottoDao.getKeywordMancantiByIdProdotto(prodotto.getIdProdotto())));
	    }
	    keywordMap.put(String.valueOf(0), "Seleziona produttore");
	}
	
	

}
