package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;


import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

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

import org.ajaxtags.helpers.AjaxXmlBuilder;

/**
 * Servlet implementation class ServletAggiungiProdotti
 */
//@WebServlet("/ServletAggiungiProdotti")
public class ServletAggiungiProduttore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext context = this.getServletConfig().getServletContext();
		
		String nomeProduttore = request.getParameter("produttore");
		String websiteProduttore = request.getParameter("website");
		
		Integer idProduttore = -1;
		String messaggio = "";
		
		ProduttoreDao daoP = new ProduttoreDaoMysqlJdbc();
		
		System.out.println("nomeProduttore: " + nomeProduttore);
		System.out.println("website: " + websiteProduttore);
		
		if(nomeProduttore != null && nomeProduttore != ""){
			
			if(Utility.hasSpecialChars(nomeProduttore)){
				messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/produttori.jsp").forward(request, response);
				return;
			}
						
				/*Se non esiste nessuna categoria con il nome indicato, effettuo l'inserimento */
				if(daoP.getProduttoreByNome(nomeProduttore) == null){
				
					Produttore produttore = new ProduttoreImpl();
					produttore.setNome(nomeProduttore);
					produttore.setWebsite(websiteProduttore);
					
					idProduttore = daoP.insertProduttore(produttore);
					System.out.println("produttore con id settato: " + produttore.getIdProduttore());
					
					if(idProduttore != -1){
						messaggio = "Produttore " + produttore.getNome() + " aggiunta correttamente!";
						
						/* Aggiorno il parametro listaCategorie in seguito all'aggiunta della categoria appena inserita */ 
						List<Produttore> listaProduttori = daoP.getProduttori(); 
						context.setAttribute("listaProduttori", listaProduttori);
						System.out.println("PRODUTTORI CARICATI E AGGIUNTI ALLO SCOPE");
						
						Map<String, String> produttoriMancantiMap = new HashMap<String,String>();
						//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipo NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
						this.aggiornaTabellaProduttoriMancanti(produttoriMancantiMap);
						context.setAttribute("produttoriMancantiMap", produttoriMancantiMap);
						
						/* modifico anche la tabella dei produttori relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata*/
						Map<String, String> produttoriMap = new HashMap<String,String>();
						this.aggiornaTabellaProduttori(produttoriMap);
						context.setAttribute("produttoriMap", produttoriMap);
						
						
					}
					else{
						messaggio = "Produttore " + produttore.getNome() + " non aggiunta correttamente!";
					}
					
					
				}
				else {
					messaggio = "Produttore già presente!!! <br> Non puoi inserire una produttore con lo stesso nome di un altro già presente!!!";
				}
		
		}else{
			messaggio = "Nome produttore mancante!!! Inserisci un nome valido!!!";
		}
		
		
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/produttori.jsp").forward(request, response);
	  	
		
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
