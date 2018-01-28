package it.unisannio.sweng.rosariogoglia.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletLogout
 * Servlet che esegue il logout di un utente
 */
//@WebServlet("/ServletLogout")
public class ServletLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		/* Invalido gli attributi della sessione dell'utente corrente e annoto l'operazione sul file "logOperazioni" */
		
		session.invalidate();
		
		request.getRequestDispatcher("/index").forward(request, response);
	}
}
