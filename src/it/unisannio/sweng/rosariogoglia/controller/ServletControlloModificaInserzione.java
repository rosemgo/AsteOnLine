package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ServletControlloModificaInserzione
 */
//@WebServlet("/ServletControlloModificaInserzione")
public class ServletControlloModificaInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
		
		Integer idInserzione = Integer.parseInt(request.getParameter("idInserzione"));
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = daoI.getInserzioneById(idInserzione);
		
		if(utenteInSessione != null){
			/* 
			 * Verifico se l'utente in sessione è abilitato all'uso dell'applicazione e se egli stesso è il venditore dell'inserzione da modificare;
			 * se le condizioni sono vere si consente la modifica dell'inserzione a patto che non siano già state effettuate proposte d'asta;
			 * se le condizioni sono false si mostra un messaggio di errore specifico.
			 */
			if(daoU.isUtenteAbilitato(utenteInSessione.getNick())){
				
				if(inserzione != null && (utenteInSessione.getIdUtente() == inserzione.getVenditore().getIdUtente())){
					
					if(inserzione.getAcquirente() == null ){
							request.setAttribute("inserzione", inserzione);
							request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
					}else{
							String messaggio="Errore!!! Non puoi modificare questa inserzione: hai già ricevuto offerte!!!";
							request.setAttribute("messaggio", messaggio);
							request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
					}
				
				}else{
					String messaggio="Errore!!! Non puoi modificare questa inserzione: appartiene ad un altro utente!!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
				}
				
			}else{
				String messaggio="Non è possibile modificare il prodotto: utente disabilitato dall'amministratore !!! Puoi soltanto rimuovere le inserzioni scaduti !!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
			}
	
		}
		else{
			String messaggio="Nessun utente in sessione!!! Effettua il login per modificare l'inserzione!!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		
		}
				
		
	}

}
