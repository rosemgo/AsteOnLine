package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet che permette ad un utente amministratore di modificare una categoria
 */
//@WebServlet("/ServletModificaCategoria")
public class ServletModificaCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		ServletContext context = this.getServletConfig().getServletContext();
		
		Integer idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
		String nomeCategoria = request.getParameter("categoria");
		
		System.out.println("idcategoria: " + idCategoria);
		System.out.println("categoria: " + nomeCategoria);
		
		String messaggio = "";
		
		CategoriaDao daoC = new CategoriaDaoMysqlJdbc();
		
		if(idCategoria != 0 && idCategoria != null){
		
			if(Utility.hasSpecialChars(nomeCategoria)){
				messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/categorie.jsp").forward(request, response);
				return;
			}
			
			Categoria categoria = daoC.getCategoriaById(idCategoria);
			categoria.setNome(nomeCategoria);
			
			int updatedRows = daoC.updateCategoria(categoria);
				
			if(updatedRows != -1){
				messaggio = "Categoria " + categoria.getNome() + " aggiornata correttamente!!!";
			}
			else{
				messaggio = "Categoria " + categoria.getNome() + " non aggiornata!!!";
			}
				
			/* Aggiorno il parametro listaCategorie in seguito alla modifica della categoria appena inserita */ 
			List<Categoria> listaCategorie = daoC.getCategorie(); 
			context.setAttribute("listaCategorie", listaCategorie);
			System.out.println("CATEGORIE CARICATE E AGGIUNTE ALLO SCOPE");
				
		}else {
			messaggio = "Nessuna categoria selezionata!!! Seleziona la categoria da eliminare!!!";
		}	
						
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/categorie.jsp").forward(request, response);
	  	
		
	}

	

}
