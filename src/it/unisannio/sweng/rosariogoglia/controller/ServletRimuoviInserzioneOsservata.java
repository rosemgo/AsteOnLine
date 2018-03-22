package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Servlet implementation class ServletRimuoviInserzioneOsservata
 */
//@WebServlet("/ServletRimuoviInserzioneOsservata")
public class ServletRimuoviInserzioneOsservata extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(ServletRimuoviInserzioneOsservata.class); 
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		Integer idInserzione = Integer.parseInt(request.getParameter("idInserzione"));
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		int deletedRows = daoI.deleteInserzioneOsservata(idInserzione, utenteInSessione.getIdUtente());
		
		String messaggio = "";
		
		if(deletedRows == 1){
			messaggio="L'inserzione n° "+ idInserzione +" è stato rimossa correttamente!!!";
			logger.info(new Date()+" "+messaggio);
			session.setAttribute("messaggioOsservazione", messaggio);
		}
		else{
			messaggio="Errore!!! L' inserzione n° "+idInserzione+" non è stato rimossa!!!";
			logger.warn(new Date()+" "+messaggio);
			session.setAttribute("messaggioOsservazione", messaggio);
		}
		/*in seguito alla cancellazione dell'inserzione nel db, invio il controllo alla ServletLeMieInserzioni che si occupa della visualizzazione */
		request.getRequestDispatcher("/ServletInserzioniOsservate").forward(request, response);

		
		
		//una volta fatto l'inserimento invio la lista dei primi 10 prodotti aggiornata
//		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
//	
//		int numeroInserzioni = utenteDao.getNumeroInserzioniByIdUtenteVenditore(utenteInSessione.getIdUtente());
//		
//		List<Inserzione> intervalloInserzioniOsservate = new ArrayList<Inserzione>();
//		
//		if(numeroInserzioni > 0){
//				
//			System.out.println("numero inserzioni: " + numeroInserzioni);
//	
//			int numeroInserzioniPerPagina = 10;
//				
//			System.out.println("numero inserzioni: " + numeroInserzioni);
//	
//			int numeroPagine;
//			/*
//			 * Se il rapporto tra il numero di inserzioni e il numero di
//			 * inserzioni da visualizzare per pagina è un numero intero,
//			 * otteniamo direttamente dalla divisione il numero di pagine
//			 * necessarie per visualizzare tutte le inserzioni Se il
//			 * rapporto è dispari bisogna fare la stessa divisione ed
//			 * aggiungere una pagina, necessaria per visualizzare le
//			 * restanti inserzioni
//			 */
//			if ((numeroInserzioni % numeroInserzioniPerPagina) == 0) {
//				numeroPagine = numeroInserzioni / numeroInserzioniPerPagina;
//			} else {
//				numeroPagine = (numeroInserzioni / numeroInserzioniPerPagina) + 1;
//			}
//	
//			// voglio solo le prime 10 inserzioni quindi il limite inferiore
//			// sarà 0
//			int limiteInf = 0;
//			
//			/*
//			 * Ricavo dal db solo le prime 10 inserzioni(in seguito la
//			 * paginazione sarà gestita dalla ServletLeMieInserzioni) da
//			 * visualizzare relativi al venditore corrente
//			 */
//			/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
//			intervalloInserzioniOsservate = utenteDao.getLimitInserzioniOsservateByIdUtente(utenteInSessione.getIdUtente(), limiteInf, numeroInserzioniPerPagina);
//			
//			request.setAttribute("numeroPagineInserzioniOsservate", numeroPagine);
//			request.setAttribute("numeroInserzioni", numeroInserzioni);
//			request.setAttribute("messaggio", messaggio);
//			
//			request.setAttribute("intervalloInserzioniOsservate", intervalloInserzioniOsservate);
//		}
//		else{
//			
//			messaggio="Non ci sono inserzioni osservate in questo momento!!!";
//			
//			session.setAttribute("numeroInserzioni", numeroInserzioni);
//			session.setAttribute("intervalloInserzioniOsservate", intervalloInserzioniOsservate);
//			request.setAttribute("messaggio", messaggio);
//					
//		}	
//		
//		request.getRequestDispatcher("WEB-INF/jsp/inserzioniOsservate.jsp").forward(request, response);
			
	}

	
}
