package it.unisannio.sweng.rosariogoglia.ajax;

import it.unisannio.sweng.rosariogoglia.chiaveTabellaProdotti.KeyTabellaProdotti;
import it.unisannio.sweng.rosariogoglia.controller.ServletInserisciInserzione;
import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class ServletCercaProdotto
 */
//@WebServlet("/ServletCercaProdotto")
public class ServletCercaProdotti extends BaseAjaxServlet {
	
	
	private static final long serialVersionUID = -3534695539530815007L;
	
	Logger logger = Logger.getLogger(ServletCercaProdotti.class); 
	
	
	/*Mappa con una chiave data dalla coppia "idCategoria-IdProduttore" e come valore la lista di prodotti appartenenti a quel produttore ma solo in quella categoria */
	private Map<String, String> prodottoMap;
  

//  
//  @Override
//  public void init() {
//    prodottoMap = new HashMap<String,String>();
//    ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
//    
//    /*potrei fare anche una tabella chiave costituita dall'id categoria e dall'id produttore e per valore la lista di prodotti associati.
//    In qeusto caso avrei dovuto fare 2 for innestati, il primo che scorreva tutte le categorie, il secondo che scorreva tuttii produttori di quella categoria*/
//    for(Produttore produttore: produttoreDao.getProduttori()) {
//      prodottoMap.put(String.valueOf(produttore.getIdProduttore()), makeProdottiList( (ArrayList<Prodotto>) produttore.getProdotti() ));
//    }
//  }
	
	  @Override
	  public void init() {
	    
		  
		  
		prodottoMap = new HashMap<String,String>();
	    
	    CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
	    ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
	    ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
	    
	    KeyTabellaProdotti keyMap;
	    List<Prodotto> listaProdotti;
	    
	    /* Scorro tutte le categorie, e per ogni categoria scorro tutti i produttori, in modo tale da poter inizializzare la tabella secondo la coppia categoria-prodotto --> lista produtti.
	     * in pratica la tabella, usata per la scelta dei prodotti, conterrà come "valore" la lista di prodotti associtai alla coppia "chiave" categoria-produttore.
	     * Questo perchè, ad ogni categoria appartengono dei produttori, e ad ogni coppia categoria-produttore appartengono solo una lista di prodotti relativi a quella categoria e a quel produttore (quindi nn consideriamo tutti i prodotti di quel produttore!!!)
	     */
	    for(int i=0; i < categoriaDao.getCategorie().size(); i++){
		    
	    	Categoria categoria = categoriaDao.getCategorie().get(i);
	    			
	    	for(int z=0; z < produttoreDao.getProduttoriByIdCategoria(categoria.getIdCategoria()).size(); z++){
		    	
	    		Produttore produttore = produttoreDao.getProduttoriByIdCategoria(categoria.getIdCategoria()).get(z);
	    		
		    	keyMap = new KeyTabellaProdotti(categoria.getIdCategoria(), produttore.getIdProduttore());
		    	
		    	listaProdotti = prodottoDao.getProdottiByIdCategoriaByIdProduttore(categoria.getIdCategoria(), produttore.getIdProduttore());
		    	
		    	
		    	
		    	logger.debug("chiave: " + keyMap.toString());
		    	logger.debug("valore: " + makeProdottiList( (ArrayList<Prodotto>) listaProdotti ));
		    	
		    	System.out.println("chiave: " + keyMap.toString());
		    	System.out.println("valore: " + makeProdottiList( (ArrayList<Prodotto>) listaProdotti ));
		    	
		    	prodottoMap.put(keyMap.toString(), makeProdottiList( (ArrayList<Prodotto>) listaProdotti ));
		    	
		    	System.out.println("elemento "+z+":" + prodottoMap.get(String.valueOf(keyMap)));
			    
		    }
	    	
	    }
	    
	    keyMap = new KeyTabellaProdotti(0, 0);
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    prodottoMap.put(keyMap.toString(), builder.addItem("Seleziona prodotto", String.valueOf(0)).toString() );
    	
	    
	    /*Metto nel contesto la tabella prodottoMap che associa la lista di prodotti alla coppia categoria-produttore*/
	    ServletContext context = this.getServletConfig().getServletContext();
	    context.setAttribute("prodottiMap", prodottoMap);
	    	    
	  }
  
  
	  private String makeProdottiList(ArrayList<Prodotto> prodotti) {
		    AjaxXmlBuilder builder = new AjaxXmlBuilder();
		    for(Prodotto prodotto: prodotti) {
		      builder.addItem(prodotto.getNome(), String.valueOf(prodotto.getIdProdotto()));
		    }
		    return(builder.toString());
	  }
	  
	  @SuppressWarnings("unchecked")
	  @Override
	  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		 logger.debug("entro in cerca prodotti");
		 System.out.println("entro in cerca prodotti");
		
		String produttore = request.getParameter("produttore"); 
		String categoria = request.getParameter("categoria"); 
		
	   
	    
	    System.out.println("RICEVO LA categoria: " + categoria);
	    System.out.println("RICEVO IL produttore: " + produttore);
	    
	   
	    if(categoria == null || categoria.equals("") || categoria.equals("0")){
	    	categoria = "0";
	    	produttore = "0";
	    }
	    if(produttore == null || produttore.equals("") || produttore.equals("0")){
	    	produttore = "0";
	    	categoria = "0";
	    }
	    	
	   /* 
	    ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
	    List<Prodotto> listaprodotti = daoP.getProdottiByIdCategoriaByIdProduttore(Integer.parseInt((categoria)), Integer.parseInt(produttore));
	    String listaProdottiString = makeProdottiList((ArrayList<Prodotto>) listaprodotti);
	   */
	    
	    ServletContext context = this.getServletConfig().getServletContext();
	    
	    /*la chiave della tabella è la coppia categoria-produttore, a cui sarà associata una lista di prodotti*/
	    KeyTabellaProdotti keyMap = new KeyTabellaProdotti(Integer.parseInt(categoria), Integer.parseInt(produttore));
	    
	    System.out.println("nella richiesta la chiave è: " + keyMap.toString());
	       
	    /*Prelevo dal context la tabella prodottiMap (nel caso in cui fosse stata modificata) e la utilizzo per avere la lista dei prodotti*/
	    prodottoMap = (Map<String, String>) context.getAttribute("prodottiMap");
	       
	  
	    if (prodottoMap == null) {
	      return("");
	    } else {
	    	String listaProdotti = prodottoMap.get(keyMap.toString());
		    return(listaProdotti);
	    }
	         
	  }
		
		
	
	
	

}
