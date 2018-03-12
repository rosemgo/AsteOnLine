package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletAsteInCorso
 */
//@WebServlet("/ServletAsteInCorso")
public class ServletAsteInCorso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		HttpSession session = request.getSession(true);
		
		
		String idNumPaginaString = (request.getParameter("idNumPagina"));
		
		int idNumPagina;
		if(idNumPaginaString == null){
			idNumPagina = 1;
		}
		else{
			idNumPagina = Integer.parseInt(idNumPaginaString);
		}
		
		System.out.println("pagina da visualizzare: " + idNumPagina);
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		int numeroInserzioni = daoI.getNumeroAsteInCorso();
		System.out.println("numero aste in corso: " + numeroInserzioni);
		
		Integer numeroInserzioniPerPagina = 12;
		
		List<Inserzione> intervalloAsteInCorso = new ArrayList<Inserzione>();
			
		String messaggio = "";
				
		if(numeroInserzioni > 0){
					
						
			System.out.println("numero inserzioni: " + numeroInserzioni);
			
			Integer numeroPagine;
			/*Se il rapporto tra il numero di inserzioni e il numero di inserzioni da visualizzare per pagina � un numero intero, otteniamo direttamente dalla divisione il numero di pagine necessarie per visualizzare tutte le inserzioni
			 * Se il rapporto � dispari bisogna fare la stessa divisione ed aggiungere una pagina, necessaria per visualizzare le restanti inserzioni
			 */
			if((numeroInserzioni % numeroInserzioniPerPagina) == 0){
				numeroPagine = numeroInserzioni / numeroInserzioniPerPagina;
			}
			else{
				numeroPagine = (numeroInserzioni / numeroInserzioniPerPagina) + 1;
			}
			
			//prepariamo la hashtable
			Integer numPaginePrecedente = (Integer) session.getAttribute("numeroPagineAsteInCorso");
			
			Integer limiteInf = 0;
						
			HashMap<Integer, Integer> indicizzazionePagine = new HashMap<Integer, Integer>();
			
			/* Verifico che il numero di pagine richieste per visualizzare le inserzioni sia uguale al numero precedente. Nel caso fosse cos�, non � necessario creare una nuova hash map, quindi prelevo dalla sessione quella precedentemente creata. */
			if(!numeroPagine.equals(numPaginePrecedente)){
				System.out.println("tabella nuova");
				/*creo l'hasmap che sar� utilizzata per la corrispondenza pagina-numero tupla da cui iniziare il prelievo dal db*/
				for(int i=1; i<=numeroPagine; i++){
					
					indicizzazionePagine.put(i, limiteInf);
					
					limiteInf = limiteInf + numeroInserzioniPerPagina;				
					
					System.out.println("aggiungo all'hash map chiave: " + i);
					
				}
			
			}
			else{
				System.out.println("tabella precedente");
				/*Poich� il numero di pagine necessario per visualizzare le inserzioni non � cambiato, riutilizzo la tabella precedente*/
				indicizzazionePagine = (HashMap<Integer, Integer>) session.getAttribute("indicizzazioneAsteInCorso");
			}
			
			/* ottengo dall' hash map il numero di tupla delle inserzioni da cui partire e sommare il numero di inserzioni da visualizzare per pagina, da caricare dal db*/
			limiteInf = indicizzazionePagine.get(idNumPagina);
			
			/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
			intervalloAsteInCorso = daoI.getLimitAsteInCorso(limiteInf, numeroInserzioniPerPagina);
			
			
			/*Per evitare che diverse operazioni sovrascrivano l'hasmap o il numero di pagine in sessione � importante differenziare i parametri nelle varie paginejsp/servlet*/
			session.setAttribute("indicizzazioneAsteInCorso", indicizzazionePagine);
			session.setAttribute("numeroPagineAsteInCorso", numeroPagine);
		
			
			request.setAttribute("numeroInserzioni", numeroInserzioni);
			request.setAttribute("intervalloAsteInCorso", intervalloAsteInCorso);
						
		}
		else {
			request.setAttribute("numeroInserzioni", numeroInserzioni);
			request.setAttribute("intervalloAsteInCorso", intervalloAsteInCorso);
		}
	

		request.getRequestDispatcher("WEB-INF/jsp/asteInCorso.jsp").forward(request, response);
		
		
	}

	

}
