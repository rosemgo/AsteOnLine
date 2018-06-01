package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.BannedCookiesDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.BannedCookies;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.BannedCookiesImpl;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet per modificare lo stato di abilitazione all'uso delle funzionalità dell'aplicazione da parte di un utente registrato.
 */
//@WebServlet("/ServletSetStatoUtente")
public class ServletSetStatoUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(ServletSetStatoUtente.class); 
		
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		Integer idUtente = Integer.parseInt(request.getParameter("idUtente"));
		
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
		UtenteRegistrato utenteDaModificare = daoU.getUtenteRegistratoById(idUtente);
		
		String messaggio = "";
		String stato = "";
		int updateRow = -1;
		
		if(utenteDaModificare.isFlagAbilitato()){
			System.out.println("utente abilitato, quindi da disabilitare");
			updateRow = daoU.updateStatoUtente(idUtente, false);
			stato = "disabilitato";	
			
		}
		else{
			System.out.println("utente disabilitato, quindi da abilitare");
			updateRow = daoU.updateStatoUtente(idUtente, true);
			stato = "abilitato";
			
	/*		
	 		//Abilitare un utente, significa toglierlo ANCHE dagli utenti bannati se presente.
			BannedCookiesDao daoB = new BannedCookiesDaoMysqlJdbc();
			if(daoB.checkUtenteRegistratoBanned(idUtente)){
				daoB.deleteBannedCookies(idUtente);
			}
	*/		
			
		}
		
		if(updateRow == 1){
			messaggio="L'utente " + utenteDaModificare.getNick() + " è stato " + stato + " correttamente !!!";
			logger.info(new Date()+": L'utente " + utenteDaModificare.getNick() + " è stato "+stato+" dall'Amministratore");
			
		}
		else{
			messaggio="L'utente " + utenteDaModificare.getNick() + " non è stato " + stato + " correttamente !!!";
			logger.warn(new Date()+": L'utente " + utenteDaModificare.getNick() + " non è stato " + stato + " correttamente dall'Amministratore");
			
		}

		session.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/ServletListaUtenti").forward(request, response);		
		
		
	}
	

}
