package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.BannedCookiesDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.manager_acquisti.ValidatoreCarta;
import it.unisannio.sweng.rosariogoglia.model.BannedCookies;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.BannedCookiesImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * Servlet implementation class ServletModificaDati
 * Servlet che modifica i dati di un utente inseriti durante la registrazione
 */
//@WebServlet("/ServletModificaDati")
public class ServletModificaDati extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(ServletModificaDati.class); 
	

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    	HttpSession session = request.getSession(true);
		UtenteRegistrato utente = (UtenteRegistrato) session.getAttribute("utente");
	
		try {
			if(utente != null){
	    	
		    	/* Leggo i parametri inseriti nel form */
				String nome = request.getParameter("nome");
				String cognome = request.getParameter("cognome");
				String codiceFiscale = request.getParameter("codFis").toUpperCase();
				String indirizzo = request.getParameter("indirizzo");
				String idComuneString = request.getParameter("comune");
				String cap = request.getParameter("cap");
				String telefono = request.getParameter("telefono");
				String eMail = request.getParameter("mail");
				String numContoCorrente = request.getParameter("cc");
				
				System.out.println("HO PRESO TUTTI I PARAMETRI");
				
				String messaggio=" ";
						
				UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
				
				ValidatoreCarta vd = new ValidatoreCarta();
				
				/* Per alcuni campi eseguo controlli di inserimento obbligatori, controlli sul tipo di dato e controlli sulla lunghezza */
				
				if(nome==null || nome.equals("")){
					messaggio="Errore !!! Nome Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(Utility.hasSpecialChars(nome)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				
				if(cognome==null || cognome.equals("")){
					messaggio = "Errore !!! Nome Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(Utility.hasSpecialChars(cognome)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				
				if(codiceFiscale==null || codiceFiscale.equals("")){
					messaggio="Errore !!! Codice Fiscale mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(!Utility.controlloLunghezza(codiceFiscale, 16)){
					messaggio="Errore !!! Codice Fiscale errato, lunghezza richiesta 16 caratteri !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(Utility.hasSpecialChars(codiceFiscale)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
					
				if(indirizzo==null || indirizzo.equals("")){
					messaggio="Errore !!! Indirizzo Mancante !!! Campo obbligatorio !!!"; 
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(Utility.hasSpecialChars(indirizzo)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				
				if(idComuneString==null || idComuneString.equals("")){
					messaggio="Errore !!! Comune di appartenenza Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
							
				
				if(cap==null || cap.equals("")){
					messaggio="Errore !!! CAP Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(!Utility.controlloLunghezza(cap, 5)){
					messaggio="Errore !!! Il CAP Deve Essere un Numero di 5 Cifre !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(!Utility.isANumberLongInt(cap)){
					messaggio="Errore !!! Il CAP Deve Essere un Numero !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(Utility.hasSpecialChars(cap)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				
				if(!telefono.equals("")){ //il telefeono non è obbligatorio
					if(!Utility.isANumberLongInt(telefono)){
						messaggio="Errore !!! Numero di Telefono Errato !!!";
						request.setAttribute("messaggio", messaggio);
						request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
						return;
					}
				}
				
				if(eMail==null || eMail.equals("")){
					messaggio="Errore !!! Mail Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(Utility.hasSpecialChars(eMail)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}		
			
				if(numContoCorrente==null || numContoCorrente.equals("")){
					messaggio="Errore !!! Numero Carta di Credito Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				
				
//				/*effettuo un controllo per stabilire la validità della carta di credito*/
//				if(!vd.isCodiceCorretto(numContoCorrente)){
//					messaggio="Errore !!! Numero Carta di Credito Non Valida !!! Inserisci nuovamente questa carta di credito !!!";
//					request.setAttribute("messaggio", messaggio);
//					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
//					return;
//				}
//									
					
				if(!Utility.controlloLunghezza(numContoCorrente, 16)){
					messaggio="Errore !!! Numero di Carta di Credito Errato !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				if(!Utility.isANumberLongInt(numContoCorrente)){
					messaggio="Errore !!! Numero di Carta di Credito Errato !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaDati.jsp").forward(request, response);
					return;
				}
				
				//converto l'idComuneString in intero, ho la certezza che non sia null
				Integer idComune = Integer.parseInt(idComuneString);
				
				
				String nick = utente.getNick();
				
				BannedCookiesDao bannDao = new BannedCookiesDaoMysqlJdbc();
				
				/* Aggiorno i dati dell'utente nel db */
				if(utenteDao.updateDatiPersonali(nick, nome, cognome, codiceFiscale, indirizzo, idComune, cap, telefono, numContoCorrente, eMail) == 1){
					/* Aggiorno l'utente in sessione */
					utente = utenteDao.getUtenteRegistratoByNick(nick); //dopo aver modificato i dati dell'utente nel db, andiamo a prelevare l'utente aggiornato dal db e lo "rimettiamo in sessione".
					System.out.println("HO L'UTENTE: " + utente.getNick());
					
					session.setAttribute("utente", utente);
						
					
					if(!vd.isCodiceCorretto(numContoCorrente)){
						
						//se l'utente ha inserito una carta falsa e non è ancora bannato, lo banno
						if(!bannDao.checkUtenteRegistratoBanned(utente.getIdUtente())){
							
							BannedCookies cookie = new BannedCookiesImpl();
							cookie.setIdUtenteBannato(utente.getIdUtente());
							/*Come cookie utilizzo la mail dell'utente, in modo tale da essere sempre un valore univoco!*/
							cookie.setCookie(utente.geteMail());
							
							bannDao.insertBannedCookies(cookie);
							
							messaggio="Dati aggiornati!!! Utente Bannato per inserimento di una carta di credito non valida!!! Per rimuovere il Ban è necessario modificare i propri dati, inserendo una carta di credito valida!!!";
							
							logger.info(new Date()+": L'utente " + utente.getNick() + " ha modificato i suoi dati. La carta inserita non è valida, utente bannato.");
						}
						else{
							
							messaggio="Dati aggiornati!!! L'utente resta Bannato per avere inserito una nuova carta di credito non valida!!! Per rimuovere il Ban è necessario modificare i propri dati, inserendo una carta di credito valida!!!";
							
							logger.info(new Date()+": L'utente " + utente.getNick() + " ha modificato i suoi dati con una nuova carta di credito non valida, l'utente rimane bannato.");
						
						}
						
						
											
					}
					else{
						
						//Rimuoviamo l'utente dai bannati in caso di modifica con una carta di credito valida, se l'utente era bannato.
						if(bannDao.checkUtenteRegistratoBanned(utente.getIdUtente())){
							bannDao.deleteBannedCookies(utente.getIdUtente());
							
							messaggio = "Dati aggiornati con successo!!! La carta di credito inserita è valida, l'utente non è più Bannato, hai accesso a tutte le funzionalità del sito!!!";
							
							logger.info(new Date()+": L'utente " + utente.getNick() + " ha modificato i suoi dati con successo. L'utente non è più bannato.");
						}
						
						//messaggio valido per gli utenti che non erano bannati, ed hanno modificato correttamente i loro dati.
						messaggio="Congratulazioni !!! Dati aggiornati con successo !!!";
						
					}
					
					request.setAttribute("messaggio", messaggio);
					
				}
				else{
					logger.warn(new Date()+": L'utente " + utente.getNick() + " non ha modificato i suoi dati con successo.");
					messaggio="Errore !!! Dati non aggiornati !!!";
					request.setAttribute("messaggio", messaggio);
					
				}
				
				
				//verifico se l'utente sia bannato o meno!!!
				boolean isBanned = false;
				if(bannDao.checkUtenteRegistratoBanned(utente.getIdUtente())){
					isBanned = true;
				}
				
				request.setAttribute("isBanned", isBanned);
				request.getRequestDispatcher("/WEB-INF/jsp/iMieiDati.jsp").forward(request, response);
				
			}	
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
			 	
    	
    }
}