package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




/**
 * Servlet implementation class ServletControlloModificaPassword
 * Servlet di controllo per la modifica della password dell'amministratore
 */
//@WebServlet("/ServletControlloModificaPassword")
public class ServletControlloModificaPassword extends HttpServlet{
private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		
		/* 
		 * Verifico se l'utente corrente è abilitato all'uso dell'applicazione:
		 * se è abilitato si consente la modifica della password;
		 * se non è abilitato si mostra un messaggio di errore.
		 */
		if(utenteDao.isUtenteAbilitato(utenteInSessione.getNick())){
			
			if(utenteInSessione.getTipologiaCliente().equals("admin")){
				/*Visualizzazione della pagina "modificaPassword.jsp"*/
				request.getRequestDispatcher("/WEB-INF/jsp/modificaPasswordAdmin.jsp").forward(request, response);
				
			}
			else{

				/*Visualizzazione della pagina "modificaPassword.jsp"*/
				request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
				
			}
			
			
			
		}else{
			/* Setto il messaggio di errore a seconda della modalità utente */
			if(utenteInSessione.getTipologiaCliente().equalsIgnoreCase("acquirente")){
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente disabilitato dall'amministratore. Puoi solo effettuare pagamenti !!!");
			}
			else{
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente disabilitato dall'amministratore. Puoi solo cancellare inserzioni scadute !!!");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);				
		}
		
	}
	
}
