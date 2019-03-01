package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

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
 * Servlet implementation class ServletLeMieAste
 */
//@WebServlet("/ServletLeMieAste")
public class ServletLeMieAste extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession(true);
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
	/*	List<Inserzione> leMieAste = new ArrayList<Inserzione>();
		// Ricavo dal db tutte le aste a cui l' utente corrente sta partecipando	
		leMieAste = daoU.getMieAsteInCorsoByIdUtente(utenteInSessione.getIdUtente());
	*/	
		
		String idNumPaginaString = request.getParameter("idNumPagina");
		
		int idNumPagina;
		if(idNumPaginaString == null){
			idNumPagina = 1;
		}
		else{
			idNumPagina = Integer.parseInt(idNumPaginaString);
		}
		
		System.out.println("pagina da visualizzare: " + idNumPagina);
		
		
		int numeroInserzioni = daoU.getNumeroMieAsteInCorsoByIdUtente(utenteInSessione.getIdUtente());
		System.out.println("numero inserzioni: " + numeroInserzioni);
		
		int numeroInserzioniPerPagina = 5;
		
		List<Inserzione> intervalloLeMieAste = new ArrayList<Inserzione>();
			
		String messaggio = "";
				
		if(numeroInserzioni > 0){
					
						
			System.out.println("numero inserzioni: " + numeroInserzioni);
			
			Integer numeroPagine;
			/*Se il rapporto tra il numero di inserzioni e il numero di inserzioni da visualizzare per pagina è un numero intero, otteniamo direttamente dalla divisione il numero di pagine necessarie per visualizzare tutte le inserzioni
			 * Se il rapporto è dispari bisogna fare la stessa divisione ed aggiungere una pagina, necessaria per visualizzare le restanti inserzioni
			 */
			if((numeroInserzioni % numeroInserzioniPerPagina) == 0){
				numeroPagine = numeroInserzioni / numeroInserzioniPerPagina;
			}
			else{
				numeroPagine = (numeroInserzioni / numeroInserzioniPerPagina) + 1;
			}
			
			//prepariamo la hashtable
			Integer numPaginePrecedente = (Integer) session.getAttribute("numeroPagineMieAste");
			
			Integer limiteInf = 0;
						
			HashMap<Integer, Integer> indicizzazionePagine = new HashMap<Integer, Integer>();
			
			/* Verifico che il numero di pagine richieste per visualizzare le inserzioni sia uguale al numero precedente. Nel caso fosse così, non è necessario creare una nuova hash map, quindi prelevo dalla sessione quella precedentemente creata. */
			// (numeroPagine != numPaginePrecedente) ERRORE DATO DA FINDBUGS
			if(!numeroPagine.equals(numPaginePrecedente)){
				System.out.println("tabella nuova");
				/*creo l'hasmap che sarà utilizzata per la corrispondenza pagina-numero tupla da cui iniziare il prelievo dal db*/
				for(int i=1; i<=numeroPagine; i++){
					
					indicizzazionePagine.put(i, limiteInf);
					
					limiteInf = limiteInf + numeroInserzioniPerPagina;				
					
					System.out.println("aggiungo all'hash map chiave: " + i);
					
				}
			
			}
			else{
				System.out.println("tabella precedente");
				/*Poichè il numero di pagine necessario per visualizzare le inserzioni non è cambiato, riutilizzo la tabella precedente*/
				indicizzazionePagine = (HashMap<Integer, Integer>) session.getAttribute("indicizzazionePagineMieiAste");
				
			}
			
			/* ottengo dall' hash map il numero di tupla delle inserzioni da cui partire e sommare il numero di inserzioni da visualizzare per pagina, da caricare dal db*/
			limiteInf = indicizzazionePagine.get(idNumPagina);
			
			/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
			intervalloLeMieAste = daoU.getLimitMieAsteInCorsoByIdUtente(utenteInSessione.getIdUtente(), limiteInf, numeroInserzioniPerPagina);
			
			
			/*Per evitare che diverse operazioni sovrascrivano l'hasmap o il numero di pagine in sessione è importante differenziare i parametri nelle varie paginejsp/servlet*/
			session.setAttribute("indicizzazionePagineMieiAste", indicizzazionePagine);
			session.setAttribute("numeroPagineMieAste", numeroPagine);
		
			
			request.setAttribute("messaggio", messaggio);
			request.setAttribute("numeroInserzioni", numeroInserzioni);
			request.setAttribute("intervalloLeMieAste", intervalloLeMieAste);
						
		}
		else {
			messaggio = "Non sono presenti aste in corso per cui hai effettuato un'offerta!!!";
	
			request.setAttribute("messaggio", messaggio);
			request.setAttribute("numeroInserzioni", numeroInserzioni);
			request.setAttribute("intervalloLeMieAste", intervalloLeMieAste);
		}
	

		request.getRequestDispatcher("WEB-INF/jsp/leMieAste.jsp").forward(request, response);
		
		
	}
		
		
}	


	
