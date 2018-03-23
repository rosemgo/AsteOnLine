package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class ServletAmministrazione
 */

public class ServletAmministrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	//IN QUESTO MODO EFFETTUARE IL REDIRECT ALLA SERVLETAMMINISTRAZIONE DA QUALUNQUE ALTRA SERVLET(ES SERVLETLOGIN)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessione = request.getSession();
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) sessione.getAttribute("utente");
		
		if(utenteInSessione == null){
			request.getRequestDispatcher("/WEB-INF/jsp/loginAmministratore.jsp").forward(request, response);
			
		}
		else{
			
			/*Verifico che l'utente in sessione sia un amministratore, gli utenti non admin non hanno accesso all'area riservata*/
			if(utenteInSessione.getTipologiaCliente().equals("admin")){			
				request.getRequestDispatcher("/amministrazione.jsp").forward(request, response);
			}
			else{
				request.getRequestDispatcher("/WEB-INF/jsp/erroreAreaRiservata.jsp").forward(request, response);
			}
		}
	}

	

}
