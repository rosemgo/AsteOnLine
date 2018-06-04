package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.bean.HomeBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet che fa il redirect alla pagina index del sito
 */


/**
 * Servlet implementation class Prova
 */


public class ServletIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	
	//IN QUESTO MODO E' POSSIBILE EFFETTUARE IL REDIRECT ALLA SERVLETINDEX DA QUALUNQUE ALTRA SERVLET(ES SERVLETLOGIN)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
		
	}
	
	
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
//	
//			//CONTROLLARE SCADENZA ASTE DI TUTTO IL DB!!!
//			Inserzione inserzione = new InserzioneImpl();
//			InserzioneDao dao = new InserzioneDaoMysqlJdbc();		
//		
//			/* Prelevo tutti i prodotti nel database */
//			List<Inserzione> listaInserzioni = dao.getInserzioni();
//			System.out.println("PRELEVIAMO LA LISTA INSERZIONI");
//			
//			/*
//			 *  Per ogni prodotto controlla che sia in asta e che la data di fine asta sia minore di quella attuale
//			 *  In tal caso aggiorna lo stato del prodotto ad aggiudicato (se c'è un acquirente) o scaduto (se non c'è un acquirente) 
//			 */
//			for(int i=0; i<listaInserzioni.size(); i++){
//				
//				inserzione = listaInserzioni.get(i);
//			
//				Date fineAsta = inserzione.getDataScadenza();
//				Date odierna = new Date();
//				
//				String statoInserzione = inserzione.getStato();
//				
//				if(statoInserzione.equals("in asta") && (fineAsta.compareTo(odierna) < 0)){
//					
//					/*Se non c'è nessun acquirente l'idacquirente sarà 0*/
//					if(inserzione.getIdAcquirente() == 0){
//						statoInserzione = "scaduta";
//					}
//					else{
//						statoInserzione = "aggiudicata";
//					}
//					
//					// Esegue l'aggiornamento del prodotto nel database
//					dao.updateStatoInserzione(statoInserzione, inserzione.getIdInserzione());
//									
//				}
//							
//			}
//			System.out.println("STATO ASTE AGGIORNATO");
			
			
			
			//DOPO AVER EFFETTUATO IL CONTROLLO SULLA SCADENZA E' POSSIBILE CARICARE TUTTE LE ASTE
			
			try {
				
				HomeBean homeBean = new HomeBean();
				request.setAttribute("homeBean", homeBean);
				
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
		
		
	}
	
	

}
