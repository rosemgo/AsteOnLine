package it.unisannio.sweng.rosariogoglia.ajax;

import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

/**
 * Servlet implementation class ServletCercaProduttori
 */
//@WebServlet("/ServletCercaProduttori")
public class ServletCercaProduttori extends BaseAjaxServlet {
  
	
  private static final long serialVersionUID = -3534695539530815007L;
	
  private Map<String, String> produttoriMap;
  
	
  @Override
  public void init() {
    produttoriMap = new HashMap<String,String>();
    CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
    for(Categoria categoria: categoriaDao.getCategorie()) {
    	produttoriMap.put(String.valueOf(categoria.getIdCategoria()), makeProduttoriList((ArrayList<Produttore>)categoria.getListaProduttori()));
    }
    AjaxXmlBuilder builder = new AjaxXmlBuilder();
    produttoriMap.put(String.valueOf(0), (builder.addItem("Seleziona produttore", String.valueOf(0))).toString());
    
    ServletContext context = this.getServletConfig().getServletContext();
    context.setAttribute("produttoriMap", produttoriMap);
    
  }

  private String makeProduttoriList(ArrayList<Produttore> produttori) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    for(Produttore produttore: produttori) {
	      builder.addItem(produttore.getNome(), String.valueOf(produttore.getIdProduttore()));
	    }
	    return(builder.toString());
  }
  
    
  @SuppressWarnings("unchecked")
  @Override
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	String categoria = request.getParameter("categoria");
    System.out.println("servlet cerca produttori - RICEVO LA categoria: " + categoria);
    
//    HttpSession session = request.getSession(true);
//    session.setAttribute("categoriaScelta", categoria);
      
    if(categoria == null || categoria == ""){
    	categoria = "0";
    }
  
    ServletContext context = this.getServletConfig().getServletContext();
    
    String listaproduttori;
    
   	produttoriMap = (Map<String, String>) context.getAttribute("produttoriMap");
   	listaproduttori = produttoriMap.get(categoria);
     
   	if(listaproduttori == null || listaproduttori == "")
   		listaproduttori = "Nessun produttore";
   	
    System.out.println("lista: " + listaproduttori);
    
    if (produttoriMap == null) {
      return("");
    } else { 
      return(listaproduttori);
    }
    
  }
  
}
