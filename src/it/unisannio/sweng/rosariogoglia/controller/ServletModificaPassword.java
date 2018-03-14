package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.RandomPasswordDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.RandomPasswordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.utility.MD5;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;



/**
 * Servlet implementation class ServletModificaDati
 * Servlet che modifica i dati di un utente inseriti durante la registrazione
 */
//@WebServlet("/ServletModificaPassword")
public class ServletModificaPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(ServletModificaPassword.class); 
	
	
    @SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(true);
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		try{
			if(utenteInSessione != null){
				
				/* Prelevo i valori dal form */
				String oldPsw= request.getParameter("oldPsw");
				String newPsw= request.getParameter("psw");
				String confPsw= request.getParameter("psw2");
				
				String messaggio="";
				
				
				//cripto la password tramite l'algoritmo MD5
				MD5 md5 = new MD5();
				try {
					oldPsw = md5.md5(oldPsw);
					newPsw = md5.md5(newPsw);
					confPsw = md5.md5(confPsw);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				
				
				
				
				UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
				
				/* Per tutti i campi eseguo controlli di inserimento obbligatori e controlli sulla lunghezza */
				if(oldPsw==null || oldPsw.equals("")){
					messaggio="Errore !!! Vecchia Password Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
					return;
				}
				
				if(newPsw==null || newPsw.equals("")){
					messaggio="Errore !!! Nuova Password Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
					return;
				}
	
				if(confPsw==null || confPsw.equals("")){
					messaggio="Errore !!! Password Di Conferma Mancante !!! Campo obbligatorio !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
					return;
				}
				
				if(!oldPsw.equals(utenteInSessione.getPassword())){
					messaggio="Errore !!! Vecchia Password Non Corretta !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
					return;
				}
				
				if(!Utility.controlloLunghezza(newPsw, 8)){
					messaggio="Errore !!! Password Minore Di 8 Caratteri !!! ";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
					return;
				}
				
				if(!Utility.controlloLunghezza(confPsw, 8)){
					messaggio="Errore !!! Password Di Conferma Minore Di 8 Caratteri !!! ";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
					return;
				}
				
				if(!Utility.controlloPassword(newPsw, confPsw)){
					messaggio="Errore !!! Le Due Password Non Coincidono !!! ";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaPassword.jsp").forward(request, response);
					return;
				}
				
				
				String nick = utenteInSessione.getNick();
				
				/* Aggiorno i dati dell'utente nel db */
				
				if(utenteDao.updatePassword(nick, newPsw) == 1){
					
					RandomPasswordDao rpdDao = new RandomPasswordDaoMysqlJdbc();
					//se la vecchia password è associata ad un idutente nella tabella RandomPassword, significa che la pass è stata generata in seguito ad un recupero pass da parte dell'utente.
					//posso cancellare la pass dalla tabella RandomPassword, per poterla riutilizzare in futuro
					if(rpdDao.checkHashPasswordAndIdUtente(oldPsw, utenteInSessione.getIdUtente())){
						rpdDao.deleteRandomPassword(oldPsw);
					}
					
					
					/* Aggiorno l'utente in sessione */
					utenteInSessione = utenteDao.getUtenteRegistratoByNick(nick); //dopo aver modificato i dati dell'utente nel db, andiamo a prelevare l'utente aggiornato dal db e lo "rimettiamo in sessione".
					System.out.println("HO L'UTENTE: " + utenteInSessione.getNick());
						
					session.setAttribute("utente", utenteInSessione);
						
					if(utenteInSessione.getTipologiaCliente().equals("admin")){
						logger.info(new Date()+": L'amministratore" + utenteInSessione.getNick() + " ha modificato la password con successo.");
					}
					else{
						logger.info(new Date()+": L'utente " + utenteInSessione.getNick() + " ha modificato la password con successo.");
					}
					
					messaggio="Congratulazioni!!! Password aggiornata con successo!!!";
					request.setAttribute("messaggio", messaggio);
						
				}
				else{
					if(utenteInSessione.getTipologiaCliente().equals("admin")){
						logger.warn(new Date()+": L'amministratore" + utenteInSessione.getNick() + " non ha modificato la password con successo.");
					}
					else{
						logger.warn(new Date()+": L'utente " + utenteInSessione.getNick() + " non ha modificato la password con successo.");
					}
					
					
					messaggio="Errore!!! Password non aggiornata!!!";
					request.setAttribute("messaggio", messaggio);
						
				}
				
				if(utenteInSessione.getTipologiaCliente().equals("admin")){
					request.getRequestDispatcher("/ServletListaUtenti").forward(request, response);
				}
				else{
					request.getRequestDispatcher("/WEB-INF/jsp/iMieiDati.jsp").forward(request, response);
				}
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    }
    
    
}
    
