package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.tsw.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.tsw.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.tsw.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletControlloInserisciProdotto
 */
//@WebServlet("/ServletControlloInserisciProdotto")
public class ServletControlloInserisciInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		
		/* verifico che l'utente sia abilitato ad effettuare operazioni */
		if(utenteDao.isUtenteAbilitato(utenteInSessione.getNick())){
			
			request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);		
			
		}
		else{
			String messaggio = "Non è possibile inserire nuovi prodotti: utente disabilitato dall'amministratore !!! Puoi soltanto rimuovere i prodotti scaduti !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
		
		
		
		
	}

	
}
