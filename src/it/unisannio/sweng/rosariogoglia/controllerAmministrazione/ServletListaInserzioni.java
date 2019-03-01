package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

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
 * La Servlet mostra all'amministratore la lista delle inserzioni caricate sul sito.
 */
//@WebServlet("/ServletListaInserzioni")
public class ServletListaInserzioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		
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
		
		int numInserzioni = daoI.getNumeroInserzioni();
		
		System.out.println("numero lista inserzioni: " + numInserzioni );
		
		int numInserzioniPerPagina = 10;
		
		List<Inserzione> intervalloListaInserzioni = new ArrayList<Inserzione>();
			
		if(numInserzioni > 0){
			
			Integer numeroPagine;
			/*Se il rapporto tra il numero di inserzioni e il numero di inserzioni da visualizzare per pagina è un numero intero, otteniamo direttamente dalla divisione il numero di pagine necessarie per visualizzare tutte le inserzioni
			 * Se il rapporto è dispari bisogna fare la stessa divisione ed aggiungere una pagina, necessaria per visualizzare le restanti inserzioni
			 */
			if((numInserzioni % numInserzioniPerPagina) == 0){
				numeroPagine = numInserzioni / numInserzioniPerPagina;
			}
			else{
				numeroPagine = (numInserzioni / numInserzioniPerPagina) + 1;
			}
			
			Integer numPaginePrecedente = (Integer) session.getAttribute("numeroPagineInserzioni");
			
			Integer limiteInf = 0;
						
			HashMap<Integer, Integer> indicizzazionePagine = new HashMap<Integer, Integer>();
			
			/* Verifico che il numero di pagine richieste per visualizzare le inserzioni sia uguale al numero precedente. Nel caso fosse così, non è necessario creare una nuova hash map, quindi prelevo dalla sessione quella precedentemente creata. */
			if(!numeroPagine.equals(numPaginePrecedente)){
				System.out.println("tabella nuova");
				/*creo l'hasmap che sarà utilizzata per la corrispondenza pagina-numero tupla da cui iniziare il prelievo dal db*/
				for(int i=1; i<=numeroPagine; i++){
					
					indicizzazionePagine.put(i, limiteInf);
					
					limiteInf = limiteInf + numInserzioniPerPagina;				
					
					System.out.println("aggiungo all'hash map chiave: " + i);
					
				}
			
			}
			else{
				System.out.println("tabella precedente");
				/*Poichè il numero di pagine necessario per visualizzare le inserzioni non è cambiato, riutilizzo la tabella precedente*/
				indicizzazionePagine = (HashMap<Integer, Integer>) session.getAttribute("indicizzazionePagineInserzioni");
				
			}
						
			/* ottengo dall' hash map il numero di tupla delle inserzioni da cui partire e sommare il numero di inserzioni da visualizzare per pagina, da caricare dal db*/
			limiteInf = indicizzazionePagine.get(idNumPagina);
			
			/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
			intervalloListaInserzioni = daoI.getLimitInserzioni(limiteInf, numInserzioniPerPagina);
			
			
			/*Per evitare che diverse operazioni sovrascrivano l'hasmap o il numero di pagine in sessione è importante differenziare i parametri nelle varie paginejsp/servlet*/
			session.setAttribute("indicizzazionePagineInserzioni", indicizzazionePagine);
			session.setAttribute("numeroPagineInserzioni", numeroPagine);
		
			
			request.setAttribute("numeroInserzioni", numInserzioni);
			request.setAttribute("messaggio", messaggio);			
			request.setAttribute("intervalloListaInserzioni", intervalloListaInserzioni);
		
		}
		else{
			
			messaggio = "Non sono presenti inserzioni!!!";
			
			request.setAttribute("numeroInserzioni", numInserzioni);
			
			request.setAttribute("messaggio", messaggio);
			request.setAttribute("intervalloListaInserzioni", intervalloListaInserzioni);
									
		}
		
		
		request.getRequestDispatcher("WEB-INF/jsp/listaInserzioni.jsp").forward(request, response);
		
		
		
		
		
		
	}

	

}
