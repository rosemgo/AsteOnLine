package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * La Servlet permette ad un utente venditore di eliminare una propria inserzione scaduta a cui nessuno ha fatto offerte.
 * 
 */
//@WebServlet("/ServletRimuoviInserzioneLeMieInserzioni")
public class ServletRimuoviInserzioneLeMieInserzioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(ServletRimuoviInserzioneLeMieInserzioni.class); 
	

  	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		Integer idInserzione = Integer.parseInt(request.getParameter("idInserzione"));
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = daoI.getInserzioneById(idInserzione);
		
		String messaggio = "";
		int deletedRows = -1;
		
		if(inserzione.getStato().equals("scaduta")){
		
			/* Verifico che l'utente corrente sia effettivamente il venditore del prodotto */
			if(inserzione.getIdVenditore() == utenteInSessione.getIdUtente()){
				
				/* Rimuovo l'inserzione dal db */
				deletedRows = daoI.deleteInserzione(idInserzione);
				System.out.println("righe cancellate: " + deletedRows);
				
				if(deletedRows == 1){
					messaggio="L'inserzione n° "+ idInserzione +" è stato rimossa correttamente !!!";
					logger.info(new Date()+" "+messaggio);
					session.setAttribute("messaggioMieInserzioni", messaggio);
				}
				else{
					messaggio="Errore!!! L' inserzione n° "+idInserzione+" non è stato rimossa !!!";
					logger.warn(new Date()+" "+messaggio);
					session.setAttribute("messaggioMieInserzioni", messaggio);
				}
				/*in seguito alla cancellazione dell'inserzione nel db, invio il controllo alla ServletLeMieInserzioni che si occupa della visualizzazione */
				request.getRequestDispatcher("/ServletLeMieInserzioni").forward(request, response);
			}
			else{
				messaggio="Errore!!! Non puoi rimuovere un'inserzione appartenente ad un altro utente!!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
			}
		}
		else{
			messaggio="Errore!!! Non puoi rimuovere non ancora scaduta!!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
	
		
	}

	

}
