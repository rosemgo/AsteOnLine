package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

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
 * La Servlet è utilizzata per mostrare, all'amministratore del sito, la lista degli utenti registrati
 */
//@WebServlet("/ServletListaUtenti")
public class ServletListaUtenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//IN QUESTO MODO EFFETTUARE IL REDIRECT ALLA SERVLETINDEX DA QUALUNQUE ALTRA SERVLET(ES SERVLETLOGIN)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
		
	}
	
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
		//List<UtenteRegistrato> listaUtenti = new ArrayList<UtenteRegistrato>();
		
		String idNumPaginaString = request.getParameter("idNumPagina");
		
		int idNumPagina;
		if(idNumPaginaString == null){
			idNumPagina = 1;
		}
		else{
			idNumPagina = Integer.parseInt(idNumPaginaString);
		}
			
		String messaggio = (String) session.getAttribute("messaggio");
		session.removeAttribute("messaggio");
		
		int numUtenti = daoU.getNumeroUtenti();
		
		System.out.println("numero utenti: " + numUtenti);
		
		int numUtentiPerPagina = 10;
		
		List<UtenteRegistrato> intervalloListaUtenti = new ArrayList<UtenteRegistrato>();
		
		
		if(numUtenti > 0){
				
			System.out.println("numero inserzioni: " + numUtenti);
			
			Integer numeroPagine;
			/*Se il rapporto tra il numero di inserzioni e il numero di inserzioni da visualizzare per pagina è un numero intero, otteniamo direttamente dalla divisione il numero di pagine necessarie per visualizzare tutte le inserzioni
			 * Se il rapporto è dispari bisogna fare la stessa divisione ed aggiungere una pagina, necessaria per visualizzare le restanti inserzioni
			 */
			if((numUtenti % numUtentiPerPagina) == 0){
				numeroPagine = numUtenti / numUtentiPerPagina;
			}
			else{
				numeroPagine = (numUtenti / numUtentiPerPagina) + 1;
			}
			
			Integer numPaginePrecedente = (Integer) session.getAttribute("numeroPagineUtenti");
			
			Integer limiteInf = 0;
						
			HashMap<Integer, Integer> indicizzazionePagine = new HashMap<Integer, Integer>();
			
			/* Verifico che il numero di pagine richieste per visualizzare le inserzioni sia uguale al numero precedente. Nel caso fosse così, non è necessario creare una nuova hash map, quindi prelevo dalla sessione quella precedentemente creata. */
			if(!numeroPagine.equals(numPaginePrecedente)){
				System.out.println("tabella nuova");
				/*creo l'hasmap che sarà utilizzata per la corrispondenza pagina-numero tupla da cui iniziare il prelievo dal db*/
				for(int i=1; i<=numeroPagine; i++){
					
					indicizzazionePagine.put(i, limiteInf);
					
					limiteInf = limiteInf + numUtentiPerPagina;				
					
					System.out.println("aggiungo all'hash map chiave: " + i);
					
				}
			
			}
			else{
				System.out.println("tabella precedente");
				/*Poichè il numero di pagine necessario per visualizzare le inserzioni non è cambiato, riutilizzo la tabella precedente*/
				indicizzazionePagine = (HashMap<Integer, Integer>) session.getAttribute("indicizzazionePagineUtenti");
				
			}
						
			/* ottengo dall' hash map il numero di tupla delle inserzioni da cui partire e sommare il numero di inserzioni da visualizzare per pagina, da caricare dal db*/
			limiteInf = indicizzazionePagine.get(idNumPagina);
			
			/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
			intervalloListaUtenti = daoU.getLimitUtenti(limiteInf, numUtentiPerPagina);
			
			
			/*Per evitare che diverse operazioni sovrascrivano l'hasmap o il numero di pagine in sessione è importante differenziare i parametri nelle varie paginejsp/servlet*/
			session.setAttribute("indicizzazionePagineUtenti", indicizzazionePagine);
			session.setAttribute("numeroPagineUtenti", numeroPagine);
		
			
			request.setAttribute("numeroUtenti", numUtenti);
			request.setAttribute("messaggio", messaggio);			
			request.setAttribute("intervalloListaUtenti", intervalloListaUtenti);
		
		}
		else{
			
			messaggio = "Non ci sono utenti registrati!!!";
			
			request.setAttribute("numeroUtenti", numUtenti);
			
			request.setAttribute("messaggio", messaggio);
			request.setAttribute("intervalloListaUtenti", intervalloListaUtenti);
									
		}
		
		
		request.getRequestDispatcher("WEB-INF/jsp/listaUtenti.jsp").forward(request, response);
		
		
		
	}

	

}
