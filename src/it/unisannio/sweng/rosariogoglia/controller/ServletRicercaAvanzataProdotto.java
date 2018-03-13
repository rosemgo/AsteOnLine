package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
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
 * Servlet implementation class ServletRicercaAvanzataProdotto
 */
//@WebServlet("/ServletRicercaAvanzataProdotto")
public class ServletRicercaAvanzataProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		
		String idCategoriaString = request.getParameter("categoria");
		int idCategoria;
		if(idCategoriaString == null){
			idCategoria = 0;
		}
		else {
			idCategoria = Integer.parseInt(idCategoriaString);
		}
		
		String idProduttoreString = request.getParameter("produttore");
		int idProduttore;
		if(idProduttoreString == null){
			idProduttore = 0;
		}
		else {
			idProduttore = Integer.parseInt(idProduttoreString);
		}
		
		String idProdottoString = request.getParameter("prodotto");
		int idProdotto;
		if(idProdottoString == null){
			idProdotto = 0;
		}
		else {
			idProdotto = Integer.parseInt(idProdottoString);
		}
				
		String idNumPaginaString = request.getParameter("idNumPagina");
		
		int idNumPagina;
		if(idNumPaginaString == null){
			idNumPagina = 1;
		}
		else{
			
			idNumPagina = Integer.parseInt(idNumPaginaString);
			System.out.println("sto navigando tra le pagine: " + idNumPagina);
			/*Prelevo i parametri dalla sessione per effettuare la stessa ricerca(sugli stessi parametri) effettuata in precedenza. */
			idCategoria = (Integer) session.getAttribute("idCategoriaAvanzataProdotto");
			idProduttore = (Integer) session.getAttribute("idProduttoreAvanzataProdotto");
			idProdotto = (Integer) session.getAttribute("idProdottoAvanzataProdotto");
		
		}
		System.out.println("pagina da visualizzare: " + idNumPagina);
		
		/*Se non viene selezionato nessun parametro, la ricerca restituirà tutte le inserzioni */
		if(idCategoria != 0 || idProduttore != 0 || idProdotto != 0){
		
			ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
			
			if(!prodottoDao.checkProdottoBelongCategoriaProduttore(idProdotto, idCategoria, idProduttore)){
				String messaggio="Errore !!! Il prodotto selezionato non appartiene alla categoria scelta o al produttore scelto!!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("ricercaAvanzata.jsp").forward(request, response);
				return;
			}
			
		}
		
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();		
		int numeroInserzioni = daoI.getNumeroInserzioniRicercaAvanzataProdotto(idCategoria, idProduttore, idProdotto);
		
		
		System.out.println("numero inserzioni: " + numeroInserzioni);
		
		int numeroInserzioniPerPagina = 10;
		
		List<Inserzione> intervalloInserzioniCercate = new ArrayList<Inserzione>();
		
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
		
			
			Integer numPaginePrecedente = (Integer) session.getAttribute("numeroPagineRicercaAvanzataProdotto");
			
			Integer limiteInf = 0;
			
			HashMap<Integer, Integer> indicizzazionePagine = new HashMap<Integer, Integer>();
						
			/* Verifico che il numero di pagine richieste per visualizzare le inserzioni sia uguale al numero precedente. Nel caso fosse così, non è necessario creare una nuova hash map, quindi prelevo dalla sessione quella precedentemente creata. */
			if(numeroPagine != numPaginePrecedente){
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
				indicizzazionePagine = (HashMap<Integer, Integer>) session.getAttribute("indicizzazionePagineRicercaAvanzataProdotto");
				
			}
			
			/* ottengo dall' hash map il numero di tupla delle inserzioni da cui partire e sommare il numero di inserzioni da visualizzare per pagina, da caricare dal db*/
			limiteInf = indicizzazionePagine.get(idNumPagina);
			
			/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
			intervalloInserzioniCercate = daoI.ricercaAvanzataInserzioneLimitProdotti(idCategoria, idProduttore, idProdotto, limiteInf, numeroInserzioniPerPagina);
		
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
			
			/*Per evitare che diverse operazioni sovrascrivano l'hashmap o il numero di pagine in sessione è importante differenziare i parametri nelle varie paginejsp/servlet*/
			session.setAttribute("indicizzazionePagineRicercaAvanzataProdotto", indicizzazionePagine);
			session.setAttribute("numeroPagineRicercaAvanzataProdotto", numeroPagine);
		
			/*Mettiamo in sessione anche parola chiave e categoria in modo tale che durante la navigazione tra le pagine, venga fatta sempre la stessa ricerca.
			 *Al contrario, non mettendo in sessione tali parametri, cliccando su un numero di pagina otterremmo in input dei valori null sia per keyword che per idcategoria(in realtà 0) 
			 *e quindi avremmo una ricerca diversa.*/
			session.setAttribute("idCategoriaAvanzataProdotto", idCategoria);
			session.setAttribute("idProduttoreAvanzataProdotto", idProduttore);
			session.setAttribute("idProdottoAvanzataProdotto", idProdotto);
						
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
				
		request.getRequestDispatcher("/WEB-INF/jsp/visualizzaProdottiRicercaAvanzata.jsp").forward(request, response); 
		
		
	}
		
		
		
}


