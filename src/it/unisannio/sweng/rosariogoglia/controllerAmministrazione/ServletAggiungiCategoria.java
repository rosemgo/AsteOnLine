package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.CategoriaImpl;
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
 * Servlet implementation class ServletAggiungiCategoria
 */
//@WebServlet("/ServletAggiungiCategoria")
public class ServletAggiungiCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext context = this.getServletConfig().getServletContext();
			
		String nomeCategoria = request.getParameter("categoria");
		Integer idCategoria = -1;
		String messaggio = "";
		
		CategoriaDao daoC = new CategoriaDaoMysqlJdbc();
		
		System.out.println("nomecategoria: " + nomeCategoria);
		
		if(nomeCategoria != null && nomeCategoria != ""){ 
				
			if(Utility.hasSpecialChars(nomeCategoria)){
				messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/categorie.jsp").forward(request, response);
				return;
			}
			
			
				/*Se non esiste nessuna categoria con il nome indicato, effettuo l'inserimento */
				if(daoC.getCategoriaByNome(nomeCategoria) == null){
				
					Categoria categoria = new CategoriaImpl();
					categoria.setNome(nomeCategoria);
					
					idCategoria = daoC.insertCategoria(categoria);
					System.out.println("categoria con id settato: " + categoria.getIdCategoria());
					
					if(idCategoria != -1){
						messaggio = "Categoria " + categoria.getNome() + " aggiunta correttamente! Puoi associare produttori a questa categoria!";
					
						/* Aggiorno il parametro listaCategorie in seguito all'aggiunta della categoria appena inserita */ 
						List<Categoria> listaCategorie = daoC.getCategorie(); 
						context.setAttribute("listaCategorie", listaCategorie);
						System.out.println("CATEGORIE CARICATE E AGGIUNTE ALLO SCOPE");
						
						
						Map<String, String> produttoriMancantiMap = new HashMap<String,String>();
						//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
						this.aggiornaTabellaProduttoriMancanti(produttoriMancantiMap);
						context.setAttribute("produttoriMancantiMap", produttoriMancantiMap);
						
						/* modifico anche la tabella dei produttori relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata*/
						Map<String, String> produttoriMap = new HashMap<String,String>();
						this.aggiornaTabellaProduttori(produttoriMap);
						context.setAttribute("produttoriMap", produttoriMap);
					
					}
					else{
						messaggio = "Categoria " + categoria.getNome() + " non aggiunta correttamente!";
					}
					
				
					
				}
				else {
					messaggio = "Categoria già presente!!! <br> Non puoi inserire una categoria con lo stesso nome di un'altra già presente!!!";
				}
		
		}else{
			messaggio = "Nome categoria mancante!!! Inserisci un nome valido!!!";
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
