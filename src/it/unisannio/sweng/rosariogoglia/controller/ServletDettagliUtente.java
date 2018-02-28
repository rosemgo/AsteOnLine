package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.BannedCookiesDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletDettagliUtente
 * Servlet che gestisce la visualizzazione dei dettagli di un utente:
 * Se l'utente corrente è l'amministratore inoltra i dati di visualizzazione alla pagina "dettagliUtente.jsp";
 * se l'utente corrente è un acquirente o un venditore inoltra la richiesta alla pagina "iMieiDati.jsp" che preleva i dati da visualizzare dalla sessione corrente.
 */
//@WebServlet("/ServletDettagliUtente")
public class ServletDettagliUtente extends HttpServlet{
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		
		if(utenteInSessione.getTipologiaCliente().equals("admin")){
						
			
				/* Recupero i dati dell'utente da visualizzare dal db tramite il nick */
				Integer idUtente = Integer.parseInt(request.getParameter("idUtente"));
				
				
				UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
				UtenteRegistrato utenteDaVisualizzare = utenteDao.getUtenteRegistratoById(idUtente);
				
				BannedCookiesDao bannDao = new BannedCookiesDaoMysqlJdbc();
				
				//verifico se l'utente sia bannato o meno!!!
				boolean isBanned = false;
				if(bannDao.checkUtenteRegistratoBanned(utenteDaVisualizzare.getIdUtente())){
					isBanned = true;
				}
				
				
				/* Inoltro la richiesta alla pagina "dettagliUtente.jsp" */
				request.setAttribute("utenteDaVisualizzare", utenteDaVisualizzare);
				request.setAttribute("isBanned", isBanned);
				request.getRequestDispatcher("/WEB-INF/jsp/dettagliUtente.jsp").forward(request, response);
						
		
		}
		else{
			
			BannedCookiesDao bannDao = new BannedCookiesDaoMysqlJdbc();
			
			//verifico se l'utente sia bannato o meno!!!
			boolean isBanned = false;
			if(bannDao.checkUtenteRegistratoBanned(utenteInSessione.getIdUtente())){
				isBanned = true;
			}
			
			/* Inoltro la richiesta alla pagina "iMieiDati.jsp" */
			request.setAttribute("isBanned", isBanned);
			request.getRequestDispatcher("/WEB-INF/jsp/iMieiDati.jsp").forward(request, response);
		}
				
	}
	
	
	

}
