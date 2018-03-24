package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet utilizzata per permettere ad un amministratore di cercare un utente regestrato in base al nickname dell'utente registrato.
 */
//@WebServlet("/ServletRicercaUtente")
public class ServletRicercaUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String nick = request.getParameter("nick");
		
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
		
		
		UtenteRegistrato utenteCercato = daoU.getUtenteRegistratoByNick(nick);
		
		String messaggio="";
		if(utenteCercato == null){
			messaggio = "La ricerca non ha prodotto risultati!!!";
		}
		
		
		request.setAttribute("messaggio", messaggio);
		request.setAttribute("utenteCercato", utenteCercato);
		request.getRequestDispatcher("/WEB-INF/jsp/visualizzaUtenteCercato.jsp").forward(request, response);
		
		
	}

	

}
