package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletControlloOfferta
 */
//@WebServlet("/ServletControlloOfferta")
public class ServletControlloOfferta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//IN QUESTO MODO E' POSSIBILE EFFETTUARE IL REDIRECT A QUESTA SERVLET DA QUALUNQUE ALTRA SERVLET(ES SERVLETLOGIN)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
   	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = new InserzioneImpl();
		Integer idInserzione;
		
		String idInserzioneString = request.getParameter("idInserzione");
				
		
		//caso in cui si arriva alla servlet da qualunque clic sul "fai offerta" in seguito all'effettuazione del login.(in pratica quando l'utente già è loggato)
		if(idInserzioneString != null && idInserzioneString != ""){
			
			idInserzione = Integer.parseInt(idInserzioneString);
			inserzione = daoI.getInserzioneById(idInserzione);
			
		}//caso in cui si arriva alla servlet in seguito al "fai offerta" prima di eseguire il login(In pratica quando un utente non ancora loggato prova a fare un'offerta). 
		else if(session.getAttribute("idInserzione") != null){
			
			idInserzione = (Integer) session.getAttribute("idInserzione");
			inserzione = daoI.getInserzioneById(idInserzione);
			
		}
		else{
			request.setAttribute("messaggio", "Si è verificato un errore riguardo l'inserzione richiesta. Riprovare!!!");
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
			
			
			
		System.out.println("stato inserzione: " + inserzione.getStato());
		
		System.out.println("DATA ODIERNA: "+ Calendar.getInstance().getTime());
		
		System.out.println("DATA SCADENZA " + inserzione.getDataScadenza());
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
		
		
		/* 
		 * Verifico che la data di scadenza dell'asta del prodotto sia ancora valida:
		 * Se è valida si consente di effettuare la proposta d'asta
		 * se non è valida si mostra un messaggio di errore
		 */
		if(inserzione.getDataScadenza().compareTo(Calendar.getInstance().getTime()) > 0){
			
			/* 
			 * Verifico che l'utente sia loggato:
			 * Se la condizione è vera si consente di effettuare la proposta d'asta;
			 * Se la condizione è falsa si mostra un messaggio  di errore e si memorizzano nello scope session:
			 * il prodotto, l'indirizzamento dopo il login (pagina) e il venditore del prodotto
			 * e si rimanda alla pagina di login.
			 */
			if(utenteInSessione != null){
				
				/* 
				 * Verifico se l'utente corrente è abilitato all'uso dell'applicazione:
				 * se abilitato si consente di effettuare la proposta d'asta;
				 * se non è abilitato si mostra un messaggio di errore.
				 */
				if(daoU.isUtenteAbilitato(utenteInSessione.getNick())){
					
						//l'utente venditore non può effettuare offerte per i suoi prodotti
						if(utenteInSessione.getIdUtente() != inserzione.getVenditore().getIdUtente()){
						
							/*Se l'utente in precedenza ha tentato l'offerta su un prodotto lo si manda alla pagina "paginaOfferta.jsp" relativa a quel prodotto */
							//session.setAttribute("inserzioneOfferta", inserzione);
							request.setAttribute("inserzioneOfferta", inserzione);
							request.getRequestDispatcher("/WEB-INF/jsp/paginaOfferta.jsp").forward(request, response);
							
						}
						else{
							String messaggio="Non è possibile effettuare offerte per una propria inserzione!!!";
							request.setAttribute("messaggio", messaggio);
							request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
						}
				
				}				
				else{
					 String messaggio="Non è possibile effettuare offerte: utente disabilitato dall'amministratore!!!";
					 request.setAttribute("messaggio", messaggio);
					 request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
				}
				
			}
			else{
				String messaggio="Devi effettuare il login per fare un'offerta!!!";
				System.out.println("NESSUN UTENTE IN SESSIONE");
				
				/* Preparo i parametri nel caso in cui l'utente non sia loggato */
				session.setAttribute("idInserzione", inserzione.getIdInserzione());
				session.setAttribute("pagina", "/WEB-INF/jsp/paginaOfferta.jsp");
				
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
	
		}
		else{
									
			String messaggio = "Inserzione scaduta, non è possibile effettuare offerte!!!";
			
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
			
		}
		
		
		
		
	}


}
