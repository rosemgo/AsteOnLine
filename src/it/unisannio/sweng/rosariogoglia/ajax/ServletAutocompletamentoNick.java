package it.unisannio.sweng.rosariogoglia.ajax;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

/**
 * Servlet implementation class ServletAutocompletamentoNick
 */
//@WebServlet("/ServletAutocompletamentoNick")
public class ServletAutocompletamentoNick extends BaseAjaxServlet {
	private static final long serialVersionUID = 1L;
       

	private List<String> listaNick;
	
	
	private String makeKeywordList(String nickPrefix) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    
	    for(String nickname: listaNick) {
	    	
	      if(nickname.startsWith(nickPrefix)) {
	        builder.addItem(nickname, nickname);
	      }
	      
	    }
	    return(builder.toString());
	}
	
	@Override
	public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("entro in autocompletament nick");
		
		String nickPrefix = request.getParameter("nickPrefix");
		System.out.println("prefisso ricevuto: " + nickPrefix);
		
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
		
		listaNick = daoU.getNick();
		
		String nickList = makeKeywordList(nickPrefix);
	    System.out.println("lista finale: " + nickList);
	    return(nickList);
		
	}

}
