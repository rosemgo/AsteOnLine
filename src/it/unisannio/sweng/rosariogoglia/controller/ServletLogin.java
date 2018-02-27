package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;
//import it.unisannio.sweng.rosariogoglia.utility.MD5;

import it.unisannio.sweng.rosariogoglia.utility.MD5;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletLogin
 * Servlet che gestisce il login
 */
//@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;  

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
		
	
	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* prelevo i dati utente dal form login */
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
	
	
		//cripto la password tramite l'algoritmo MD5 per vedere se corrisponde con quella nel db 
		MD5 md5 = new MD5();
		String passwordCrypted = "";
		try {
			passwordCrypted = md5.md5(password);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		UtenteRegistrato utente = new UtenteRegistratoImpl();
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		
		HttpSession session = request.getSession(true);
		
		try {
			
			utente = utenteDao.checkUtente(nick);
						
			if(utente == null){ //in questo caso è sbagliato il nickname
				request.setAttribute("messaggio", "LOGIN ERRATO: nickname non esistente");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
			else if (utente.getTipologiaCliente().equals("admin")) {
						
					if(!utente.checkPassword(password)) { //in questo caso è sbagliata la password
						request.setAttribute("messaggio", "LOGIN ERRATO: password errata");
						request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
					}
					else{
						session.setAttribute("utente", utente); //metto l'utente in sessione!!!
						
						/* Se l'utente accede in seguito ad una richiesta di offerta o di osservazione inserzione, come amministratore(e quindi non può fare offerte) togliamo dalla sessione i parametri pagina e inserzione che avevamo pensato per inviare l'utente(in caso non fosse stato un amministratore) direttamente alla pagina offerta relativa a quell'inserzione */
						session.removeAttribute("idInserzione");
						session.removeAttribute("pagina");
						
						request.setAttribute("messaggio", "");
						
						request.getRequestDispatcher("/amministrazione").forward(request, response);
				
					}
		
				
			}
			else if(!utente.checkPassword(passwordCrypted)) { //in questo caso è sbagliata la password
				request.setAttribute("messaggio", "LOGIN ERRATO: password errata");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
			else{
				
				//se l'utente è disabilitato non gli faccio eseguire il login a meno che non abbia delle inserzioni scadute da cancellare oppure inserzioni aggiudicate da pagare
				if(!utente.isFlagAbilitato()){
					
					//se l'utente è disabilitato ed è un venditore, e non ha pagamenti da fare e nemmeno inserzioni scadute da cancellare non gli permetto di avere accesso al sito
					if(utente.getTipologiaCliente().equals("venditore") && !utenteDao.controllaProdottiScaduti(nick) && !utenteDao.controllaPagamenti(nick)){
						request.setAttribute("messaggio", "Attenzione!!! Utente Disabilitato!!! Contattare l'amministratore: rosariogoglia@gmail.com oppure marco.belfiore88@gmail.com!!!");
						request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
						return;
					}
					//se l'utente è disabilitato ed è un acquirente e non ha pagamenti da fare non gli permetto di avere accesso al sito
					if(utente.getTipologiaCliente().equals("acquirente") && !utenteDao.controllaPagamenti(nick)){
						request.setAttribute("messaggio", "Attenzione!!! Utente Disabilitato!!! Contattare l'amministratore: rosariogoglia@gmail.com oppure marco.belfiore88@gmail.com!!!");
						request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
						return;
					}
					
				}
								
				
				session.setAttribute("utente", utente); //metto l'utente in sessione!!!
				
				String pagina = (String) session.getAttribute("pagina");
					
					if(session.getAttribute("pagina") != null){
						
						System.out.println("ABBIAMO UNA PAGINA IN SESSIONE: "+ session.getAttribute("pagina"));
						
						Integer idInserzione = (Integer) session.getAttribute("idInserzione");
						InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
						Inserzione inserzione = daoI.getInserzioneById(idInserzione);
						
						//se la pagine in sessione è successiva ad una richiesta di offerta
						if(session.getAttribute("pagina").equals("/WEB-INF/jsp/paginaOfferta.jsp")){
													
							//se l'utente in sessione non è il venditore dell'inserzione può fare offerte oppure osservarla a meno che nn la osservi già. 
							if(utente.getIdUtente() != inserzione.getIdVenditore()){
								/* Se l'utente accede in seguito ad una richiesta di offerta su un' inserzione lo si manda alla pagina "offerta.jsp" relativa a quell'inserzione */
								request.getRequestDispatcher("/ServletControlloOfferta").forward(request, response);
							}
							else {
								request.setAttribute("messaggio", "Benvenuto " + utente.getNick() + "!!! Utilizza il menù in alto per navigare nel sito e... buon AsteOnline!!! <br /> <br /> Non è possibile effettuare offerte oppure osservare una propria inserzione!!!");
								request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
								
							}
							
						}
						else{ //se invece la pagina in sessione è "osserva inserzione" devo mandarla alla pagina dettagli inserzione
							
							if(utente.getIdUtente() == inserzione.getIdVenditore()){
								request.setAttribute("messaggio", "Benvenuto " + utente.getNick() + "!!! Utilizza il menù in alto per navigare nel sito e... buon AsteOnline!!! <br /> <br /> Non è possibile effettuare offerte oppure osservare una propria inserzione!!!");
								request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
							}
							else if (utenteDao.checkInserzioneOsservataByIdUtente(utente.getIdUtente(), inserzione.getIdInserzione())) {
								request.setAttribute("messaggio", "Benvenuto " + utente.getNick() + "!!! Utilizza il menù in alto per navigare nel sito e... buon AsteOnline!!! <br /> <br /> Già osservi questa inserzione!!!");
								request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
							}
							else{
								//sostituire con ServletOsservaInserzione se voglio far inserire direttamente l'inserzione tra quelle osservate
								request.getRequestDispatcher("/ServletDettagliInserzione").forward(request, response);
								
							}
								
							
						}
						
						//andrebbe tolto se voglio far osservare subito l'inserzione
						
						session.removeAttribute("pagina");
						System.out.println("RIMUOVO DALLA SESSIONE LA PAGINA");
						session.removeAttribute("idInserzione");
						System.out.println("RIMUOVO DALLA SESSIONE L'INSERZIONE");
					
						
					}
					else{
						request.setAttribute("messaggio", "Benvenuto " + utente.getNick() + "!!! Utilizza il menù in alto per navigare nel <br /> sito e... buon AsteOnline!!!");
						request.getRequestDispatcher("/index").forward(request, response);
					}
					
//				}
				
			}
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	


}