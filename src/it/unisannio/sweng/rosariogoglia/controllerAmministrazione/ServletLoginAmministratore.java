package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servelt utilizzata per il login dell'amministratore. In caso di credenziali corrette, inivia alla area di amministrazione
 */
//@WebServlet("/ServletLoginAmministratore")
public class ServletLoginAmministratore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/* prelevo i dati utente dal form login dell'amministrazione */
		String nick= request.getParameter("nick");
		String password= request.getParameter("password");
		
		UtenteRegistrato utente = new UtenteRegistratoImpl();
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		
		HttpSession session = request.getSession(true);
		
			
		try {
				
			utente = utenteDao.checkUtente(nick);
			
			if(utente == null){ //in questo caso è sbagliato il nickname
				request.setAttribute("messaggio", "LOGIN ERRATO: nickname non esistente");
				request.getRequestDispatcher("/WEB-INF/jsp/loginAmministratore.jsp").forward(request, response);
				
			}
			
			else{
				
				if(utente.getTipologiaCliente().equals("admin")){
					
					if(!utente.checkPassword(password)) { //in questo caso è sbagliata la password
						request.setAttribute("messaggio", "LOGIN ERRATO: password errata");
						request.getRequestDispatcher("/WEB-INF/jsp/loginAmministratore.jsp").forward(request, response);
						
					}
					else{
						session.setAttribute("utente", utente);
						request.getRequestDispatcher("/amministrazione.jsp").forward(request, response);
					}
					
				}
				else{
					request.setAttribute("messaggio", "AREA RISERVATA SOLO ALL'ACCESSO DEGLI AMMINISTRATORI!!!");
					request.getRequestDispatcher("/WEB-INF/jsp/loginAmministratore.jsp").forward(request, response);
				}
				
			}
				
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
	}

}
