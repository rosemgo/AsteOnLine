package it.unisannio.sweng.rosariogoglia.ajax;

import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.modelImpl.KeywordImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

/**
 * Servlet implementation class ServletAutocompletamentoKeyword
 */
//@WebServlet("/ServletAutocompletamentoKeyword")
public class ServletAutocompletamentoKeyword extends BaseAjaxServlet {
	private static final long serialVersionUID = 1L;
   
		
	private String[] keywords;
	 
	
	public String[] convertListInString(List<Keyword> keywords){
		
		String[] listaKeywords = new String[keywords.size()];
		Keyword keyword;
		for(int i=0; i< keywords.size(); i++){
			keyword = new KeywordImpl();
			keyword = keywords.get(i);
			
			listaKeywords[i] = keyword.getKeyword(); 
			System.out.println("parola aggiunta: " + listaKeywords[i]);
		}
		
		return listaKeywords;

	}


	
	private String makeKeywordList(String keywordPrefix) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    
	    for(String keyword: keywords) {
	    	
	      if(keyword.startsWith(keywordPrefix)) {
	        builder.addItem(keyword, keyword);
	      }
	      
	    }
	    return(builder.toString());
	  }
	


	@SuppressWarnings("unchecked")
	@Override
	public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("entro in autocompletamente");
		
		String keywordPrefix = request.getParameter("keywordPrefix");
		System.out.println("prefisso ricevuto: " + keywordPrefix);
		
		List<Keyword> listaKeyword = (List<Keyword>) this.getServletConfig().getServletContext().getAttribute("listaKeyword");
		keywords = this.convertListInString(listaKeyword);

		String keywordList = makeKeywordList(keywordPrefix);
	    System.out.println("lista finale: " + keywordList);
	    return(keywordList);
		
	}
	
	
   
}
