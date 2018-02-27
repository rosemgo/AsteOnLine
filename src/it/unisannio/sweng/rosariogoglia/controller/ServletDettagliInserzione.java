package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.OffertaDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.OffertaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class ServletDettagliInserzione
 */
//@WebServlet("/ServletDettagliInserzione")
public class ServletDettagliInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//IN QUESTO MODO E' POSSIBILE EFFETTUARE IL REDIRECT A QUESTA SERVLET DA QUALUNQUE ALTRA SERVLET(ES SERVLETLOGIN)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);		
	}    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		System.out.println("utente: " + utenteInSessione);
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = new InserzioneImpl();
		Integer idInserzione;
		
		String idInserzioneString = request.getParameter("idInserzione"); //preleviamo l'id inserzione dalla richiesta, se qualcuno clicca sul dettaglio inserzione 
				
		System.out.println("il dettaglio richiesto è dell'inserzione: " + idInserzioneString);
			
		
		//caso in cui si arriva alla servlet da qualunque clic su "dettagli inserzione"
		if(idInserzioneString != null && idInserzioneString != ""){
			
			idInserzione = Integer.parseInt(idInserzioneString);
			inserzione = daoI.getInserzioneById(idInserzione);
			
			System.out.println("utente venditore: " + inserzione.getVenditore().getNick());
			
		}//caso in cui si arriva alla servlet in seguito al clic su "osserva inserzione" oppure su "fai offerta" prima di eseguire il login. (In pratica quando un utente non ancora loggato e prova ad osservare un'inserzione, la ServletOsservaInserzione, mette in sessione la pagina dell'inserzione e l'idInserzione, poi invia alla pagina di login, e la ServletLogin, dopo aver controllato che il login sia ok, vede se in sessione vi è una pagina di "fai offerta" o di "osserva offerta", se è di osserva offerta, invia il controllo alla ServletDettagliOfferta, se è di "Fai offerta" invia il controllo alla ServletControllaOfferta ). 
		else if(session.getAttribute("idInserzione") != null){
			
			idInserzione = (Integer) session.getAttribute("idInserzione");
			inserzione = daoI.getInserzioneById(idInserzione);
			
		}
		else if (idInserzioneString.equals("")){
			
			//System.out.println("ENTRO IN ID MANCANTE");
			
			request.setAttribute("messaggio", "Si è verificato un errore riguardo l'inserzione richiesta. Riprovare!!!");
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
			return; //necessario altrimenti continuerebbe la successione di istruzioni
		}
					
		//int numeroImmagini = inserzione.getImmagini().size();
		
		boolean giaOsservata = false;
		
		if(utenteInSessione != null){
			
			//query che mi dice se l'utente già osserva o meno l'inserzione
			UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
			giaOsservata = daoU.checkInserzioneOsservataByIdUtente(utenteInSessione.getIdUtente(), inserzione.getIdInserzione());
			System.out.println("inserzione già osservata: " + giaOsservata);
			
		}
			
		
		/*Prelevo dal db tutte le offerte relative all'inserzione */
	/*	OffertaDao daoO = new OffertaDaoMysqlJdbc();
		List<Offerta> listaOfferte = daoO.getOfferteByIdInserzione(idInserzione);
		System.out.println("offerte caricate");
	*/	
		//request.setAttribute("listaOfferte", listaOfferte);
	//	request.setAttribute("numeroImmagini", numeroImmagini);
		

		session.setAttribute("inserzione", inserzione); //metto l'inserzione in sessione, da renderla disponibile per qualunque operazione. Anche i vari filtri che si attivano possono in tal mondo fare uso dell'inserzione.
			
	//	request.setAttribute("inserzione", inserzione);
		
		
		if(utenteInSessione != null && utenteInSessione.getTipologiaCliente().equals("admin")){
			request.getRequestDispatcher("/WEB-INF/jsp/dettagliInserzioneAdmin.jsp").forward(request, response);
		}
		else{
			
			request.setAttribute("giaOsservata", giaOsservata);
			request.getRequestDispatcher("/WEB-INF/jsp/dettagliInserzione.jsp").forward(request, response);
		}
				
		
	}

	
	
}
