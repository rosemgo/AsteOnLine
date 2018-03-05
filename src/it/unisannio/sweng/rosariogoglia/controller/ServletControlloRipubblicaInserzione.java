package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletControlloRipubblicaInserzione
 */
//@WebServlet("/ServletControlloRipubblicaInserzione")
public class ServletControlloRipubblicaInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		Integer idInserzione = Integer.parseInt(request.getParameter("idInserzione"));
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = daoI.getInserzioneById(idInserzione);
				
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
				
		/* 
		 * Verifico se l'utente corrente è abilitato all'uso dell'applicazione:
		 * Se abilitato si consente la ripubblicazione del prodotto;
		 * se non è abilitato si mostra un messaggio di errore.
		 */
		if(daoU.isUtenteAbilitato(utenteInSessione.getNick())){
			
			/*
			 * Verifico che l'utente corrente sia effettivamente il venditore dell'inserzione da ripubblicare:
			 * se vero si consente alla ripubblicazione;
			 * se falso si mostra un messaggio di errore.
			 */
			if(inserzione.getIdVenditore() == utenteInSessione.getIdUtente()){
				
				if(inserzione.getStato().equals("scaduta")){
					session.setAttribute("inserzione", inserzione);
					request.getRequestDispatcher("/WEB-INF/jsp/ripubblicaInserzione.jsp").forward(request, response);
				}
				else{
					String messaggio="Errore!!! Non puoi ripubblicare un'iserzione in asta!!!";
					request.setAttribute("errore", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
				}
				
			}
			else {
				request.setAttribute("messaggio", "Errore!!! Non puoi ripubblicare un'inserzione appartenente ad un altro utente!!!");
				request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
			}
			
			
		}
		else{
			request.setAttribute("messaggio", "Non è possibile modificare il prodotto: utente disabilitato dall'amministratore!!! Puoi soltanto rimuovere le inserzioni scaduti !!!");
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
			
		
		
		
		
		
		
		
	}

	
}
