package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.OffertaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.OffertaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.OffertaImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Servlet implementation class ServletOfferta
 */
//@WebServlet("/ServletOfferta")
public class ServletOfferta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(ServletOfferta.class); 
	 
	private static Vector<Integer> listaInserzioniOfferte = new Vector<Integer>();
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
				
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
	//	Inserzione inserzioneInSessione = (Inserzione) session.getAttribute("inserzioneOfferta");
	//	session.removeAttribute("inserzione"); //dopo averla prelevata dalla sessione, possiamo rimuovere l'inserzione(dalla sessione)
		
		
		String idInserzioneDalForm = request.getParameter("idInserzioneForm");
		System.out.println("id: " + idInserzioneDalForm);
		
		
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		
		Inserzione inserzioneInRequest = daoI.getInserzioneById(Integer.parseInt(idInserzioneDalForm));
		
		//utilizzo un vettore sincronizzato per evitare che 2 thread (eseguiti contemporaneamente) sulla stessa inserzione aggiornino contemporaneamente gli attributi dell'inserzione nel db. 
		//Quando il primo thread avrà rilasciato la risorsa, allora sarà eseguito il secondo thred e così via.
		//Utilizzo questo metodo per evitare un doppio aggiornamento errato nel db, in pratica se 2 o più utenti clicchino contemporaneamente sul Fai Offerta, l'inserzione deve essere modificata in modo sincronizzato, ossia "uno alla volta". 
		if(!this.listaInserzioniOfferte.contains(inserzioneInRequest.getIdInserzione())){
			this.listaInserzioniOfferte.add(inserzioneInRequest.getIdInserzione());
			System.out.println("non contiene l'inserzione");
		}
		
		int posizioneIdInserzione = this.listaInserzioniOfferte.indexOf(inserzioneInRequest.getIdInserzione());
		System.out.println("posizione nel vettore: " + posizioneIdInserzione);
		
		System.out.println("id inserzione: " + inserzioneInRequest.getIdInserzione());
		
		
		synchronized (this.listaInserzioniOfferte.get(posizioneIdInserzione)){
			
			Inserzione inserzione = daoI.getInserzioneById(inserzioneInRequest.getIdInserzione()); //prelevo di nuovo l'inserzione dal database per avere tutti gli attributi dell'inserzione aggiornati(nel caso in cui qualche altro utente avesse effettuato un'offerta mentre l'utente "principale" stava visualizzando la pagina di offerta)
			
								
			String messaggio = "";
						
			
			if(utenteInSessione.getIdUtente() != inserzione.getVenditore().getIdUtente()){
				
				String sommaOffertaString = request.getParameter("sommaOfferta");
				
				System.out.println("Ricevo la somma offerta: " + sommaOffertaString);
				
				if(sommaOffertaString != null && sommaOffertaString != ""){
				
					Double sommaOfferta = Double.parseDouble(sommaOffertaString);
					
					/* 
					 * Verifico se il prezzo proposto è maggiore del prezzo attuale:
					 * (se non ci sono ancora acquirenti si considera come prezzo attuale il valore iniziale inserito dal venditore)
					 * se la verifica va a buon fine si procede con la proposta d'asta;
					 * altrimenti si mostra un messaggio di errore.
					 */
					if(sommaOfferta > inserzione.getPrezzoAggiornato() && sommaOfferta > inserzione.getPrezzoIniziale() ){
						
						System.out.println("Offerta buona!!!");
						
						/* 
						 * Verifico che la data di scadenza dell'asta del prodotto sia ancora valida:
						 * Se è valida si registra l'offerta
						 * se non è valida si mostra un messaggio di errore
						 */
						if(inserzione.getDataScadenza().compareTo(Calendar.getInstance().getTime()) > 0){
							
							/* Aggiorno il prezzo d'asta e l'acquirente nel db per l'inserzione corrente */
							int updateRow = daoI.updateAcquirenteOffertaInserzione(utenteInSessione.getIdUtente(), sommaOfferta, inserzione.getIdInserzione());
							
							
							/* Aggiorno il db aggiungendo la nuova offerta */
							OffertaDao daoO = new OffertaDaoMysqlJdbc();
							Offerta offerta = new OffertaImpl();
							offerta.setSomma(sommaOfferta);
							offerta.setData(Calendar.getInstance().getTime()); //oppure new Date() 
							offerta.setIdInserzione(inserzione.getIdInserzione());
							offerta.setIdUtenteRegistrato(utenteInSessione.getIdUtente());
							
							int insertRow = daoO.insertOfferta(offerta);
							
							System.out.println("insertRow: " + insertRow);
							
							
							
							inserzione.setIdAcquirente(utenteInSessione.getIdUtente());
							inserzione.setAcquirente(utenteInSessione);
							inserzione.setPrezzoAggiornato(sommaOfferta);
							session.setAttribute("inserzioneOfferta", inserzione);
							
							logger.info(new Date()+": Offerta effettuata dall'utente " + utenteInSessione.getNick() + " per l'inserzione "+ inserzione.getIdInserzione());
							
							messaggio = "Complimenti, offerta andata a buon fine!!! <br /> Per visualizzare tutte le aste a cui stai partecipando, seleziona 'Le Mie Aste' dal menù in alto!!!";
											
							request.setAttribute("messaggio", messaggio);
							request.getRequestDispatcher("WEB-INF/jsp/complimenti.jsp").forward(request, response); //inviare alla pagina di complimenti!!!
							
						}
						else{
							messaggio = "Inserzione scaduta, non è possibile effettuare offerte!!!";
							request.setAttribute("messaggio", messaggio);
							request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
						
						}
							
					}
					else{
						messaggio="Errore!!! La somma offerta è inferiore al prezzo attuale!!!";
						session.setAttribute("inserzioneOfferta", inserzione);
						request.setAttribute("messaggio", messaggio);
						request.getRequestDispatcher("/WEB-INF/jsp/paginaOfferta.jsp").forward(request, response);
					}
					
				}
				else{
					messaggio="Inserisci un'offerta valida!!!";
					session.setAttribute("inserzioneOfferta", inserzione);
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/paginaOfferta.jsp").forward(request, response);
				}
			
			}
			else{
				
				messaggio="Non è possibile effettuare offerte per una propria inserzione!!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
				
				
			}
		
		}
		
		
	}

}
