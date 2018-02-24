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
 * Servlet implementation class ServletInserzioniOsservate
 */
//@WebServlet("/ServletInserzioniOsservate")
public class ServletInserzioniOsservate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		String idNumPaginaString = request.getParameter("idNumPagina");
		
		int idNumPagina;
		if(idNumPaginaString == null){
			idNumPagina = 1;
		}
		else{
			idNumPagina = Integer.parseInt(idNumPaginaString);
		}
		
		System.out.println("pagina da visualizzare: " + idNumPagina);
		
		
		String messaggio = (String) session.getAttribute("messaggioOsservazione");
		session.removeAttribute("messaggioOsservazione");
		
		
		if(utenteInSessione.isFlagAbilitato()){
			
			int numeroInserzioniPerPagina = 5;
			
			List<Inserzione> intervalloInserzioniOsservate = new ArrayList<Inserzione>();
			
			UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
			//List<Inserzione> inserzioniOsservate = daoU.getInserzioniOsservateByIdUtente(utenteInSessione.getIdUtente());
		
			int numeroInserzioni = daoU.getNumeroInserzioniOsservateByIdUtente(utenteInSessione.getIdUtente());
			
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
				
				Integer numPaginePrecedente = (Integer) session.getAttribute("numeroPagineInserzioniOsservate");
				
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
					indicizzazionePagine = (HashMap<Integer, Integer>) session.getAttribute("indicizzazionePagineInserzioniOsservate");
					
				}
				
				/* ottengo dall' hash map il numero di tupla delle inserzioni da cui partire e sommare il numero di inserzioni da visualizzare per pagina, da caricare dal db*/
				limiteInf = indicizzazionePagine.get(idNumPagina);
				System.out.println("il limite inf è: " + limiteInf);
				
				/* Ricavo dal db solo le inserzioni da visualizzare(in base alla pagina scelta) relativi al venditore corrente */
				intervalloInserzioniOsservate = daoU.getLimitInserzioniOsservateByIdUtente(utenteInSessione.getIdUtente(), limiteInf, numeroInserzioniPerPagina);
								
				System.out.println("VEDIAMO TUTTE LE INSERZIONI");
				
				for(int i=0; i<intervalloInserzioniOsservate.size(); i++){
					
								
					System.out.println("titolo: " + intervalloInserzioniOsservate.get(i).getTitolo());
					
					for(int j=0; j<intervalloInserzioniOsservate.get(i).getImmagini().size(); j++)
						System.out.println("immagine: " + intervalloInserzioniOsservate.get(i).getImmagini().get(j).getFoto());
					
				}
				
				/*Per evitare che diverse operazioni sovrascrivano l'hasmap o il numero di pagine in sessione è importante differenziare i parametri nelle varie paginejsp/servlet*/
				session.setAttribute("indicizzazionePagineInserzioniOsservate", indicizzazionePagine);
				session.setAttribute("numeroPagineInserzioniOsservate", numeroPagine);
			
				
				/* Condivido i campi per la visualizzazione delle inserzioni */
				request.setAttribute("numeroInserzioni", numeroInserzioni);
				request.setAttribute("messaggio", messaggio);
				
				request.setAttribute("intervalloInserzioniOsservate", intervalloInserzioniOsservate);
								
			}
			else{
				
				messaggio="Non ci sono inserzioni osservate in questo momento!!!";
				
				session.setAttribute("numeroInserzioni", numeroInserzioni);
				session.setAttribute("intervalloInserzioniOsservate", intervalloInserzioniOsservate);
				request.setAttribute("messaggio", messaggio);
						
			}
						
			request.getRequestDispatcher("WEB-INF/jsp/inserzioniOsservate.jsp").forward(request, response);
		
		}
		else{
			/* Setto il messaggio di errore a seconda della modalità utente */
			if(utenteInSessione.getTipologiaCliente().equalsIgnoreCase("acquirente")){
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente disabilitato dall'amministratore. Puoi solo effettuare pagamenti !!!");
			}
			else{
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente disabilitato dall'amministratore. Puoi solo cancellare inserzioni scadute o effettuare pagamenti!!!");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
		
		
	}

	

}
