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
 * Servlet implementation class ServletRicercaAvanzataInserzione
 */
//@WebServlet("/ServletRicercaAvanzataInserzione")
public class ServletRicercaAvanzataInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		
			
		String keyword = request.getParameter("parola_chiave");
		
		String idCategoriaString = request.getParameter("categoria");
		Integer idCategoria;
		if(idCategoriaString == null){
			idCategoria = 0;
		}
		else {
			idCategoria = Integer.parseInt(idCategoriaString);
		}
				
		String titolo = request.getParameter("titolo");
		
		String prezzoMinString = request.getParameter("prezzoMin");
		Double prezzoMin;
		if(prezzoMinString == "" || prezzoMinString == null){
			prezzoMin = null;
		}
		else {
			prezzoMin = Double.parseDouble(prezzoMinString);
		}
		
		String prezzoMaxString = request.getParameter("prezzoMax");
		Double prezzoMax;
		if(prezzoMaxString == "" || prezzoMaxString == null){
			prezzoMax = null;
		}
		else {
			prezzoMax = Double.parseDouble(prezzoMaxString);
		}
		
		System.out.println("keyword: " + keyword);
		System.out.println("id categoria: " + idCategoria);
		System.out.println("titolo: " + titolo);
		System.out.println("prezzoMin: " + prezzoMin);
		System.out.println("prezzoMax: " + prezzoMax);
		
		String idNumPaginaString = request.getParameter("idNumPagina");
		
		int idNumPagina;
		if(idNumPaginaString == null){
			idNumPagina = 1;
		}
		else{
			
			idNumPagina = Integer.parseInt(idNumPaginaString);
			System.out.println("sto navigando tra le pagine: " + idNumPagina);
			
			/*Prelevo keyword e idCategoria dalla sessione per effettuare la stessa ricerca(sugli stessi parametri) effettuata in precedenza. */
			keyword = (String) session.getAttribute("keywordAvanzata");
			idCategoria = (Integer) session.getAttribute("idCategoriaAvanzata");
			titolo = (String) session.getAttribute("titoloAvanzata");
			prezzoMin = (Double) session.getAttribute("prezzoMinAvanzata");
			prezzoMax = (Double) session.getAttribute("prezzoMaxAvanzata");
		}
		
		System.out.println("pagina da visualizzare: " + idNumPagina);
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		int numeroInserzioni = daoI.getNumeroInserzioniRicercaAvanzata(keyword, idCategoria, titolo, prezzoMin, prezzoMax);
		
		System.out.println("numero inserzioni: " + numeroInserzioni);
		
		int numeroInserzioniPerPagina = 10;
		
		List<Inserzione> intervalloInserzioniCercate = new ArrayList<Inserzione>();
		
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
		
			
			Integer numPaginePrecedente = (Integer) session.getAttribute("numeroPagineRicercaAvanzataInserzione");
			
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
				indicizzazionePagine = (HashMap<Integer, Integer>) session.getAttribute("indicizzazionePagineRicercaAvanzataInserzione");
				
			}
			
			/* ottengo dall' hash map il numero di tupla delle inserzioni da cui partire e sommare il numero di inserzioni da visualizzare per pagina, da caricare dal db*/
			limiteInf = indicizzazionePagine.get(idNumPagina);
			
			/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
			intervalloInserzioniCercate = daoI.ricercaAvanzataInserzioneLimitInserzioni(keyword, idCategoria, titolo, prezzoMin, prezzoMax, limiteInf, numeroInserzioniPerPagina);
		
//			System.out.println("VEDIAMO TUTTE LE INSERZIONI");
//			
//			for(int i=0; i<intervalloInserzioniCercate.size(); i++){
//				
//							
//				System.out.println("titolo: " + intervalloInserzioniCercate.get(i).getTitolo());
//				
//				for(int j=0; j<intervalloInserzioniCercate.get(i).getImmagini().size(); j++)
//					System.out.println("immagine: " + intervalloInserzioniCercate.get(i).getImmagini().get(j).getFoto());
//				
//			}
			
			/*Per evitare che diverse operazioni sovrascrivano l'hasmap o il numero di pagine in sessione � importante differenziare i parametri nelle varie paginejsp/servlet*/
			session.setAttribute("indicizzazionePagineRicercaAvanzataInserzione", indicizzazionePagine);
			session.setAttribute("numeroPagineRicercaAvanzataInserzione", numeroPagine);
		
			/*Mettiamo in sessione anche parola chiave e categoria in modo tale che durante la navigazione tra le pagine, venga fatta sempre la stessa ricerca.
			 *Al contrario, non mettendo in sessione tali parametri, cliccando su un numero di pagina otterremmo in input dei valori null sia per keyword che per idcategoria(in realt� 0) 
			 *e quindi avremmo una ricerca diversa.*/
			session.setAttribute("keywordAvanzata", keyword);
			session.setAttribute("idCategoriaAvanzata", idCategoria);
//			session.setAttribute("idProduttore", idProduttore);
//			session.setAttribute("idProdotto", idProdotto);
			session.setAttribute("titoloAvanzata", titolo);
			session.setAttribute("prezzoMinAvanzata", prezzoMin);
			session.setAttribute("prezzoMaxAvanzata", prezzoMax);
						
			request.setAttribute("messaggio", messaggio);
			request.setAttribute("numeroInserzioni", numeroInserzioni);
			
			request.setAttribute("intervalloInserzioniCercate", intervalloInserzioniCercate);	
			
		}
		else{
			
			messaggio = "La ricerca non ha prodotto risultati!!!";
			
			request.setAttribute("messaggio", messaggio);
			request.setAttribute("numeroInserzioni", numeroInserzioni);
			request.setAttribute("intervalloInserzioniCercate", intervalloInserzioniCercate);
			
		}
				
		request.getRequestDispatcher("/WEB-INF/jsp/visualizzaInserzioniRicercaAvanzata.jsp").forward(request, response); 
			
		
	}


}
