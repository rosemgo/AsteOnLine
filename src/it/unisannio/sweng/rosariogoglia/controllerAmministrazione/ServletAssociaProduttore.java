package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;



import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;

/**
 * Servlet utilizzata per associare un produttore ad una categoria
 */
//@WebServlet("/ServletAssociaProduttore")
public class ServletAssociaProduttore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String idCategoriaString = request.getParameter("categoria");
		String idProduttoreString = request.getParameter("produttore");
		
		CategoriaDao daoC = new CategoriaDaoMysqlJdbc();
		ProduttoreDao daoP = new ProduttoreDaoMysqlJdbc();
		
		int insertRow = -1;
		String messaggio = "";
		
		if((idCategoriaString != null && !idCategoriaString.equals("0")) && (idProduttoreString != null && !idProduttoreString.equals("0"))){
			
			Integer idCategoria = Integer.parseInt(idCategoriaString);
			Integer idProduttore = Integer.parseInt(idProduttoreString);
			
			
			insertRow = daoC.insertCategoriaHasProduttore(idCategoria, idProduttore);
			
			Categoria categoria = daoC.getCategoriaById(idCategoria);
			Produttore produttore = daoP.getProduttoreById(idProduttore);
			
			if(insertRow != -1){
				
				/*modifico la tabella dei produttori mancanti relativi ad ogni categoria presente nel contesto!!! Tale modifica � necessaria per avere la tabella aggiornata in caso di futura associazione*/
				ServletContext context = getServletContext();
				
				Map<String, String> produttoriMancantiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perch� la tabella � un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaProduttoriMancanti(produttoriMancantiMap);
				context.setAttribute("produttoriMancantiMap", produttoriMancantiMap);
				
				/* modifico anche la tabella dei produttori relativi ad ogni categoria presente nel contesto!!! Tale modifica � necessaria per avere la tabella aggiornata*/
				Map<String, String> produttoriMap = new HashMap<String,String>();
				this.aggiornaTabellaProduttori(produttoriMap);
				context.setAttribute("produttoriMap", produttoriMap);
					
				
				messaggio = "Produttore " + produttore.getNome() + " associato correttamente alla categoria " + categoria.getNome() + "!!!";
			}
			else{
				messaggio = "Produttore " + produttore.getNome() + " non associato correttamente alla categoria " + categoria.getNome() + "!!!";
			}
		}
		else {
			messaggio = "Inserisci una categoria ed un produttore validi!!!";
		}
		
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/categorie.jsp").forward(request, response);
		
	}

	
	public String makeProduttoriList(ArrayList<Produttore> produttori) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    for(Produttore produttore: produttori) {
	      builder.addItem(produttore.getNome(), String.valueOf(produttore.getIdProduttore()));
	    }
	    return(builder.toString());
	}
  
	
  
	public void aggiornaTabellaProduttoriMancanti(Map<String, String> produttoriMancantiMap ){
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
	    for(Categoria categoria: categoriaDao.getCategorie()){
	      produttoriMancantiMap.put(String.valueOf(categoria.getIdCategoria()), makeProduttoriList((ArrayList<Produttore>)categoriaDao.getProduttoriMancantiByIdCategoria(categoria.getIdCategoria())));
	    }
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    produttoriMancantiMap.put(String.valueOf(0), (builder.addItem("Seleziona produttore", String.valueOf(0))).toString());
	   
	}


	
	public void aggiornaTabellaProduttori(Map<String, String> produttoriMap ){
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
	    	for(Categoria categoria: categoriaDao.getCategorie()) {
	    		produttoriMap.put(String.valueOf(categoria.getIdCategoria()), makeProduttoriList((ArrayList<Produttore>)categoria.getListaProduttori()));
	    	}
	    	AjaxXmlBuilder builder = new AjaxXmlBuilder();
	        produttoriMap.put(String.valueOf(0), (builder.addItem("Seleziona produttore", String.valueOf(0))).toString());
	        
	}
	
}
