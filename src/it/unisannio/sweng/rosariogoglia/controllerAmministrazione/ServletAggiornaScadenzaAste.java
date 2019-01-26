package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.modelImpl.Data;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletAggiornaScadenzaAste
 */

public class ServletAggiornaScadenzaAste extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		
		Inserzione inserzione = new InserzioneImpl();
		InserzioneDao dao = new InserzioneDaoMysqlJdbc();		
	
		/* Prelevo tutti i prodotti nel database */
		List<Inserzione> listaInserzioni = dao.getInserzioni();
		System.out.println("PRELEVIAMO LA LISTA INSERZIONI");
		
		/*
		 *  Per ogni prodotto controlla che sia in asta e che la data di fine asta sia minore di quella attuale
		 *  In tal caso aggiorna lo stato del prodotto ad aggiudicato (se c'è un acquirente) o scaduto (se non c'è un acquirente) 
		 */
		for(int i=0; i<listaInserzioni.size(); i++){
			
			inserzione = listaInserzioni.get(i);
		
			Date fineAsta = inserzione.getDataScadenza();
			Date odierna = new Date();
			
			String statoInserzione = inserzione.getStato();
			
			if(statoInserzione.equals("in asta") && (fineAsta.compareTo(odierna) <= 0)){
				
				/*Se non c'è nessun acquirente l'idacquirente sarà 0*/
				if(inserzione.getIdAcquirente() == 0){
					statoInserzione = "scaduta";
				}
				else{
					statoInserzione = "aggiudicata";
				}
				
				// Esegue l'aggiornamento del prodotto nel database
				dao.updateStatoInserzione(statoInserzione, inserzione.getIdInserzione());
								
			}
			
			
			//furbata usata perche se un'inserzione è scaduta e voglio rimetterla in asta manualmente modificando il db, dovrei modificare anche lo stato oltre alla data, invece così modifico solo la data manualmente e lo stato lo cambia in automatico
			if(statoInserzione.equals("scaduta") && (fineAsta.compareTo(odierna) > 0)){
				
				statoInserzione = "in asta";
				
				// Esegue l'aggiornamento del prodotto nel database
				dao.updateStatoInserzione(statoInserzione, inserzione.getIdInserzione());
								
			}
			
						
		}
		
		request.getRequestDispatcher("/amministrazione.jsp").forward(request, response);
		
		
	}

	

}
