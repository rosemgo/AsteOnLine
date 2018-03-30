package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;


import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

/**
 * La servlet mostra i produttori non associati alla categoria selezionata.
 */
//@WebServlet("/ServletCercaProduttoriMancanti")
public class ServletCercaProduttoriMancanti extends BaseAjaxServlet {
	private static final long serialVersionUID = -3534695539530815007L;
	
	private Map<String, String> produttoriMancantiMap;
  
	
  @Override
  public void init() {
    produttoriMancantiMap = new HashMap<String,String>();
    CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
    for(Categoria categoria: categoriaDao.getCategorie()) {
      produttoriMancantiMap.put(String.valueOf(categoria.getIdCategoria()), makeProduttoriList((ArrayList<Produttore>)categoriaDao.getProduttoriMancantiByIdCategoria(categoria.getIdCategoria())));
    }
    AjaxXmlBuilder builder = new AjaxXmlBuilder();
    produttoriMancantiMap.put(String.valueOf(0), (builder.addItem("Seleziona produttore", String.valueOf(0))).toString());
   
    ServletContext context = this.getServletConfig().getServletContext();
    context.setAttribute("produttoriMancantiMap", this.produttoriMancantiMap);
    
  }

  public String makeProduttoriList(ArrayList<Produttore> produttori) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    for(Produttore produttore: produttori) {
	      builder.addItem(produttore.getNome(), String.valueOf(produttore.getIdProduttore()));
	    }
	    return(builder.toString());
	  }
  
  
//  public void aggiornaTabella(){
//	  CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
//	    for(Categoria categoria: categoriaDao.getCategorie()){
//	      produttoriMancantiMap.put(String.valueOf(categoria.getIdCategoria()), makeProduttoriList((ArrayList<Produttore>)categoriaDao.getProduttoriMancantiByIdCategoria(categoria.getIdCategoria())));
//	    } 
//  }
  
  
  @SuppressWarnings("unchecked")
  @Override
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
	//this.init();
	//this.aggiornaTabella();
	  
	  
	String categoria = request.getParameter("categoria");
    System.out.println("servlet cerca produttori Mancanti - RICEVO LA categoria: " + categoria);
      
    
    if(categoria == null || categoria == ""){
    	categoria = "0";
    }
  
    ServletContext context = this.getServletConfig().getServletContext();
    
    String listaproduttori;
    
   	this.produttoriMancantiMap = (Map<String, String>) context.getAttribute("produttoriMancantiMap");
      
    if (produttoriMancantiMap == null) {
      return("");
    } else { 
    	listaproduttori = produttoriMancantiMap.get(categoria);
    	System.out.println("lista: " + listaproduttori);
    	return(listaproduttori);
    }
  }
}

