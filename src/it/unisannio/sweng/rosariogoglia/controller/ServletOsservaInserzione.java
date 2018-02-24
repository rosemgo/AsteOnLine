package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletOsservaInserzione
 */
//@WebServlet("/ServletOsservaInserzione")
public class ServletOsservaInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				
		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		String idInserzioneString = request.getParameter("idInserzione");
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = new InserzioneImpl();
		Integer idInserzione;			
		
		//caso in cui si arriva alla servlet da qualunque clic su "osserva inserzione" in seguito all'effettuazione del login.(in pratica quando l'utente già è loggato)
		if(idInserzioneString != null && idInserzioneString != ""){
			
			idInserzione = Integer.parseInt(idInserzioneString);
			inserzione = daoI.getInserzioneById(idInserzione);
			
		}//caso in cui si arriva alla servlet in seguito al "osserva inserzione" prima di eseguire il login. (In pratica quando un utente non ancora loggato prova a fare un'offerta). 
		else if(session.getAttribute("idInserzione") != null){
			
			idInserzione = (Integer) session.getAttribute("idInserzione");
			inserzione = daoI.getInserzioneById(idInserzione);
			
		}
		else{
			request.setAttribute("messaggio", "Si è verificato un errore riguardo l'inserzione richiesta. Riprovare!!!");
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
				
		
		/* 
		 * Verifico che l'utente sia loggato:
		 * Se la condizione è vera si consente di osservare un' inserzione;
		 * Se la condizione è falsa si mostra un messaggio  di errore e si memorizzano nello scope session:
		 * il prodotto, l'indirizzamento dopo il login (pagina) e il venditore del prodotto
		 * e si rimanda alla pagina di login.
		 */
		if(utenteInSessione != null){
		
			
			if(utenteInSessione.isFlagAbilitato()){
				
				
				if(utenteInSessione.getIdUtente() != inserzione.getVenditore().getIdUtente()){
				
					UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
			
					/*aggiorno il db aggiungendo l'inserzione alle inserzioni osservate dall'utente in sessione*/
					int insertRow = daoU.insertOsservaInserzione(utenteInSessione, inserzione);
					System.out.println("inserzione aggiunta alle osservazioni: " + inserzione.getIdInserzione());
				
					if(insertRow != -1){				
					
						session.setAttribute("messaggioOsservazione", "Inserzione aggiunta alle osservazioni!!!");
					
					}
					else{
						session.setAttribute("messaggioOsservazione", "Osservazione non riuscita!!!");
					}
					
					
				}
				else{
					String messaggio="Non è possibile osservare o effettuare offerte per una propria inserzione!!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
				}
				
			}
			else{
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
		else{
			
			String messaggio="Devi effettuare il login per osservare un'inserzione!!!";
			System.out.println("NESSUN UTENTE IN SESSIONE");
			
			/* Preparo i parametri nel caso in cui l'utente non è loggato */
			session.setAttribute("idInserzione", inserzione.getIdInserzione());
			session.setAttribute("pagina", "/WEB-INF/jsp/dettagliInserzione.jsp");
			
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			
		}
				
		
	}

	
}
