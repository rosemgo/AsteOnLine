package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletSetStatoSito
 * Servlet che mette il sito in manutenzione
 */
//@WebServlet("/ServletSetStatoSito")
public class ServletSetStatoSito extends HttpServlet {
	private static final long serialVersionUID = 1L;
     

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = this.getServletConfig().getServletContext();
		/* Leggo dalla richiesta il nuovo stato del sito */
		String statoSito = request.getParameter("statoSito");
		System.out.println("Lo stato del sito è: " + statoSito);
		
		/* In base al valore di "statoSito" setto la variabile "inManutenzione" nello scope ServletContext */
		if(statoSito.equals("ON-LINE")){
			System.out.println("IMPOSTA VARIABILE INMANUTENZIONE ONLINE");
			
			context.setAttribute("inManutenzione", false);
		}
		if(statoSito.equals("OFF-LINE")){
			System.out.println("IMPOSTA VARIABILE INMANUTENZIONE OFFLINE");
			
			context.setAttribute("inManutenzione", true);
		}
		
		request.getRequestDispatcher("/amministrazione.jsp").forward(request, response);
				
	}

}
