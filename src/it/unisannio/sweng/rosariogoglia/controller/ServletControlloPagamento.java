package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletControlloPagamento
 */
//@WebServlet("/ServletControlloPagamento")
public class ServletControlloPagamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		Integer idInserzione = Integer.parseInt(request.getParameter("idInserzione"));
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = daoI.getInserzioneById(idInserzione);
		
		/* Verifico che l'utente corrente sia l'aggiudicatario effettivo dell'inserzione:
	     * Se vero si consente il pagamento;
	     * Se falso si mostra un messaggio di errore.
	    */
		if(inserzione.getIdAcquirente() == utenteInSessione.getIdUtente()){
			
			/* 
		       * Verifico che l'inserzione sia effettivamente aggiudicata:
		       * Se vero procedo con il pagamento inoltrando la richiesta alla pagina "pagamento.jsp";
		       * se falso si mostra un messaggio di errore
		    */
			if(inserzione.getStato().equals("aggiudicata")){
				
				//session.setAttribute("inserzioneDaPagare", inserzione);
				request.setAttribute("inserzioneDaPagare", inserzione);
				request.getRequestDispatcher("/WEB-INF/jsp/pagamento.jsp").forward(request, response);
				
			}
			else{
				 String messaggio="Errore!!! Non puoi pagare un'inserzione ancora in asta!!!";
	    		 request.setAttribute("messaggio", messaggio);
	    		 request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
			}
			
		}
		else{
			 String messaggio="Errore!!! Non puoi pagare un'inserzione che non ti sei aggiudicato!!!";
	    	 request.setAttribute("messaggio", messaggio);
	    	 request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
		
	}

	

}
