package it.unisannio.sweng.rosariogoglia.ajax;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

/**
 * Servlet implementation class ServletAutocompletamentoTitolo
 */
//@WebServlet("/ServletAutocompletamentoTitolo")
public class ServletAutocompletamentoTitolo extends BaseAjaxServlet {
	private static final long serialVersionUID = 1L;
    
	private List<String> titoli;
	
	
	private String makeKeywordList(String titoloPrefix) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    
	    for(String titolo: titoli) {
	    	
	      if(titolo.startsWith(titoloPrefix)) {
	        builder.addItem(titolo, titolo);
	      }
	      
	    }
	    return(builder.toString());
	}
	
	@Override
	public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("entro in autocompletament titolo");
		
		String titoloPrefix = request.getParameter("titoloPrefix");
		System.out.println("prefisso ricevuto: " + titoloPrefix);
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		
		titoli = daoI.getTitoli();
		
		String titoliList = makeKeywordList(titoloPrefix);
	    System.out.println("lista finale: " + titoliList);
	    return(titoliList);
		
	}

}
