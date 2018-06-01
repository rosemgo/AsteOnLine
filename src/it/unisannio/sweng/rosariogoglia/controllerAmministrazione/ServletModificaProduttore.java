package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;


import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletModificaProduttore
 */
//@WebServlet("/ServletModificaProduttore")
public class ServletModificaProduttore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = this.getServletConfig().getServletContext();
		
		Integer idProduttore = Integer.parseInt(request.getParameter("idProduttore"));
		String nomeProduttore = request.getParameter("produttore");
		String website = request.getParameter("website");
		
		System.out.println("idproduttore: " + idProduttore);
		System.out.println("produttore: " + nomeProduttore);
		
		String messaggio = "";
		
		ProduttoreDao daoP = new ProduttoreDaoMysqlJdbc();
		
		if(idProduttore != 0 && idProduttore != null){
		
			Produttore produttore = daoP.getProduttoreById(idProduttore);
			/*Aggiorno solo i campi che vengono inseriti*/
			if(nomeProduttore != null && nomeProduttore != ""){
				
				if(Utility.hasSpecialChars(nomeProduttore)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/produttori.jsp").forward(request, response);
					return;
				}
				
				produttore.setNome(nomeProduttore);
			}
			if(website != null && website != "")
				produttore.setWebsite(website);
			
			int updatedRows = daoP.updateProduttore(produttore);
				
			if(updatedRows != -1){
				messaggio = "Produttore " + produttore.getNome() + " aggiornata correttamente!!!";
			}
			else{
				messaggio = "Produttore " + produttore.getNome() + " non aggiornata!!!";
			}
				
			/* Aggiorno il parametro listaProduttori in seguito alla modifica del produttore*/ 
			List<Produttore> listaProduttori = daoP.getProduttori(); 
			context.setAttribute("listaProduttori", listaProduttori);
			System.out.println("PRODUTTORI CARICATI E AGGIUNTI ALLO SCOPE");
				
		}else {
			messaggio = "Nessuna produttore selezionato!!! Seleziona il produttore da eliminare!!!";
		}	
						
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/produttori.jsp").forward(request, response);
	  	
	}

	

}
