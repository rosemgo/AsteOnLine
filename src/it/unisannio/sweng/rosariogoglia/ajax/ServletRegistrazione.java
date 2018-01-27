package it.unisannio.sweng.rosariogoglia.ajax;


import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.BannedCookiesDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.manager_acquisti.ValidatoreCarta;
import it.unisannio.sweng.rosariogoglia.model.BannedCookies;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.BannedCookiesImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;
import it.unisannio.sweng.rosariogoglia.utility.MD5;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Servlet implementation class ServletRegistrazione
 * Servlet che si occupa della registrazione di un nuovo utente
 */
//@WebServlet("/ServletRegistrazione")
public class ServletRegistrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(ServletRegistrazione.class); 
	
	public ServletRegistrazione(){
		DOMConfigurator.configure("./WebContent/WEB-INF/log4jConfig.xml");
	}      
	
	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		/* Leggo i parametri inseriti nel form */
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String codiceFiscale = request.getParameter("codFis").toUpperCase();
		String indirizzo = request.getParameter("indirizzo");
		String idComuneS = request.getParameter("comune");
		
			
		String cap = request.getParameter("cap");
		String telefono = request.getParameter("telefono");
		String eMail = request.getParameter("mail");
		String numContoCorrente = request.getParameter("cc");
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		String confPassword = request.getParameter("password2");
		String tipologiaCliente = request.getParameter("tipCliente");
		
		System.out.println("HO PRESO TUTTI I PARAMETRI");
		
		String messaggio="";
				
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		
		/* Per alcuni campi eseguo controlli di inserimento obbligatori, controlli sul tipo di dato e controlli sulla lunghezza */
		
		if(nome==null || nome.equals("")){
			messaggio="Errore !!! Nome Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(Utility.hasSpecialChars(nome)){
			messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(cognome==null || cognome.equals("")){
			messaggio = "Errore !!! Nome Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(Utility.hasSpecialChars(cognome)){
			messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(codiceFiscale==null || codiceFiscale.equals("")){
			messaggio="Errore !!! Codice Fiscale Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.controlloLunghezza(codiceFiscale, 16)){
			messaggio="Errore !!! Codice Fiscale Errato !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(Utility.hasSpecialChars(codiceFiscale)){
			messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(indirizzo==null || indirizzo.equals("")){
			messaggio="Errore !!! Indirizzo Mancante !!! Campo obbligatorio !!!"; 
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(Utility.hasSpecialChars(indirizzo)){
			messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(idComuneS==null || idComuneS.equals("")){
			messaggio="Errore !!! Comune di appartenenza Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(cap==null || cap.equals("")){
			messaggio="Errore !!! CAP Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.controlloLunghezza(cap, 5)){
			messaggio="Errore !!! Il CAP Deve Essere un Numero di 5 Cifre !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.isANumberLongInt(cap)){
			messaggio="Errore !!! Il CAP Deve Essere un Numero !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(Utility.hasSpecialChars(cap)){
			messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(!telefono.equals("")){ //il telefeono non è obbligatorio
			if(!Utility.isANumberLongInt(telefono)){
				messaggio="Errore !!! Numero di Telefono Errato !!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
				return;
			}
		}
		
		if(eMail==null || eMail.equals("")){
			messaggio="Errore !!! Mail Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(Utility.hasSpecialChars(eMail)){
			messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}		
	
		if(numContoCorrente==null || numContoCorrente.equals("")){
			messaggio="Errore !!! Numero Carta di Cedito Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.controlloLunghezza(numContoCorrente, 16)){
			messaggio="Errore !!! Numero di Carta di Credito Errato !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.isANumberLongInt(numContoCorrente)){
			messaggio="Errore !!! Numero di Carta di Credito Errato !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
				
		if(nick==null || nick.equals("")){
			messaggio="Errore !!! Nick Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(utenteDao.controlloNick(nick)){
			messaggio="Registrazione fallita !!! Nick già utilizzato !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(Utility.hasSpecialChars(nick)){
			messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(password==null || password.equals("")){
			messaggio="Errore !!! Password Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(confPassword==null || confPassword.equals("")){
			messaggio="Errore !!! Password Di Conferma Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.controlloLunghezza(password, 8)){
			messaggio="Errore !!! Password Minore Di 8 Caratteri !!! ";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.controlloLunghezza(confPassword, 8)){
			messaggio="Errore !!! Password Di Conferma Minore Di 8 Caratteri !!! ";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		if(!Utility.controlloPassword(password, confPassword)){
			messaggio="Errore !!! Le Due Password Non Coincidono !!! ";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
		
		if(tipologiaCliente==null || tipologiaCliente.equals("")){
			messaggio="Errore !!! Modalità Utente Mancante !!! Campo obbligatorio !!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			return;
		}
			
		
		/* 
		 * Controlliamo se l'utente(identificato dal codice fiscale) è già registrato con la stessa modalità utente:
		 * Se l'utente non è registrato procedo con la registrazione;
		 * altrimenti si mostra un messaggio di errore.
		 */
		if(utenteDao.isUtenteRegistrato(codiceFiscale, tipologiaCliente)){
			logger.info(new Date()+": L'utente "+request.getRemoteHost()+" ha fallito la registrazione.");			
			messaggio="Registrazione fallita !!! Utente già registrato !!!";		
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
		}
		else{
			/* Creo un nuovo utente e lo inserisco nel db */
			UtenteRegistrato utenteReg = new UtenteRegistratoImpl();
			
			//cripto la password tramite l'algoritmo MD5
			MD5 md5 = new MD5();
			try {
				password = md5.md5(password);
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			
			//converto la stringa idComuneS in intero
			Integer idComune = Integer.parseInt(request.getParameter("comune"));
			
			utenteReg.setNick(nick);
			utenteReg.setNome(nome);
			utenteReg.setCognome(cognome);
			utenteReg.setPassword(password);
			utenteReg.seteMail(eMail);
			utenteReg.setCodiceFiscale(codiceFiscale);
			utenteReg.setNumContoCorrente(numContoCorrente);
			utenteReg.setIndirizzo(indirizzo);
			utenteReg.setCap(cap);
			utenteReg.setTelefono(telefono);
			utenteReg.setTipologiaCliente(tipologiaCliente);
			utenteReg.setDataRegistrazione(new Date());
			utenteReg.setIdComune(idComune);
			utenteReg.setFlagAbilitato(true);
			
			try {
				utenteDao.insertUtenteRegistrato(utenteReg);
				logger.info(new Date()+": L'utente " + utenteReg.getNick() + " ha effettuato la registrazione con successo");			
				
				ValidatoreCarta vd = new ValidatoreCarta();
				
				/*effettuo un controllo per stabilire la validità della carta di credito*/
				if(vd.isCodiceCorretto(numContoCorrente)){
					messaggio="Congratulazioni!!! Registrazione avvenuta con successo: ora puoi effettuare il login!!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				}
				else{
										
					//BANNO L'UTENTE IN SEGUITO AL SECONDO TENTATIVO ERRATO DI INSERIMENTO CODICE CARTA
					BannedCookiesDao daoBC = new BannedCookiesDaoMysqlJdbc();
					BannedCookies cookie = new BannedCookiesImpl();
					
					cookie.setIdUtenteBannato(utenteReg.getIdUtente()); //possiamo fare il getIdUtente() sull'utenteReg appena inserito perchè il metodo insertUtenteRegistrato() setta automaticamente l'idutente in seguito all'inserimento
					
					/*Come cookie utilizzo la mail dell'utente, in modo tale da essere sempre un valore univoco!*/
					cookie.setCookie(eMail);
					
					daoBC.insertBannedCookies(cookie);
					
					System.out.println("utente bannato");
					
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				}
				
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
					
		}
		
		
	}

}
