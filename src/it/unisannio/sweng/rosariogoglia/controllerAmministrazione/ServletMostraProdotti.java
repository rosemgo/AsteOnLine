package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.chiaveTabellaProdotti.KeyTabellaProdotti;
import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.servlets.BaseAjaxServlet;

/**
 * La Servlet mostra i prodotti presenti dopo aver scelto la categoria ed il produttore.
 */
//@WebServlet("/ServletMostraProdotti")
public class ServletMostraProdotti extends BaseAjaxServlet {
	
	private static final long serialVersionUID = -3534695539530815007L;
	
	private Map<String, String> prodottoMap;

	
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
		    	
		    	System.out.println("chiave: " + keyMap.toString());
		    	System.out.println("valore: " + makeProdottiList( (ArrayList<Prodotto>) listaProdotti ));
		    	
		    	prodottoMap.put(keyMap.toString(), makeProdottiList( (ArrayList<Prodotto>) listaProdotti ));
		    	
		    	System.out.println("elemento "+z+":" + prodottoMap.get(String.valueOf(keyMap)));
			    
		    }
	    	
	    }
	    
	    keyMap = new KeyTabellaProdotti(0, 0);
	    prodottoMap.put(keyMap.toString(), "Seleziona categoria e produttore");
    	
	    
	    /*Metto nel contesto la tabella prodottoMap che associa la lista di prodotti alla coppia categoria-produttore*/
	    ServletContext context = this.getServletConfig().getServletContext();
	    context.setAttribute("mostraProdottiMap", prodottoMap);
	    	    
	  }
	
	  /*Creo la lista dei prodotti da visualizzare*/
	  private String makeProdottiList(ArrayList<Prodotto> prodotti) {
		    String listaProdotti = "";
		    for(Prodotto prodotto: prodotti) {
		    	listaProdotti = listaProdotti + prodotto.getNome() + "<br />";
		    }
		    return(listaProdotti);
	  }
	  
	  
	  
	  @SuppressWarnings("unchecked")
	  @Override
	  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("entro in MOSTRA prodotti");
		
		String categoria = request.getParameter("categoria");
		   
		String produttore = request.getParameter("produttore"); 
		   
	    
	    System.out.println("RICEVO LA categoria: " + categoria);
	    System.out.println("RICEVO IL produttore: " + produttore);
	    
	    
	    if(categoria == null || categoria == "" || categoria.equals("0")){
	    	categoria = "0";
	    	produttore = "0";
	    }
	    if(produttore == null || produttore == "" || produttore.equals("0")){
	    	produttore = "0";
	    	categoria = "0";
	    }
	    	
	    ServletContext context = this.getServletConfig().getServletContext();
	    
	    /*la chiave della tabella è la coppia categoria-produttore, a cui sarà associata una lista di prodotti*/
	    KeyTabellaProdotti keyMap = new KeyTabellaProdotti(Integer.parseInt(categoria), Integer.parseInt(produttore));
	    
	    System.out.println("nella richiesta la chiave è: " + keyMap.toString());
	       
	    /*Prelevo dal context la tabella prodottiMap (nel caso in cui fosse stata modificata) e la utilizzo per avere la lista dei prodotti*/
	    prodottoMap = (Map<String, String>) context.getAttribute("mostraProdottiMap");
		String listaProdotti = prodottoMap.get(keyMap.toString());
	    
		if(listaProdotti == "" || listaProdotti == null)
			listaProdotti = "Nessun prodotto presente";
		
		System.out.println("lista prodotti: " + listaProdotti);
	  
	    if (prodottoMap == null) {
	      return("");
	    } else { 
	      return(listaProdotti);
	    }
	     
	    
	  }
	

}
