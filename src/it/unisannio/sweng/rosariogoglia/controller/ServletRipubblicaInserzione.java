package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.utility.ControlloScadenzaAste;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class ServletRipubblicaInserzione
 */
//@WebServlet("/ServletRipubblicaInserzione")
public class ServletRipubblicaInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final Logger logger = Logger.getLogger(ServletRipubblicaInserzione.class); 
	
 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		Inserzione inserzione = (Inserzione) session.getAttribute("inserzione");
		
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		
		String messaggio = "";	
		/* 
		 * Verifico se l'utente corrente è abilitato all'uso dell'applicazione:
		 * Se abilitato si consente la ripubblicazione del prodotto;
		 * se non è abilitato si mostra un messaggio di errore.
		 */
		if(utenteDao.isUtenteAbilitato(utenteInSessione.getNick())){
			
			String prezzoIniziale= request.getParameter("prezzoIniziale");
			String dataScadenza= request.getParameter("data_scadenza");
			
			System.out.println("Prezzo iniziale: " + prezzoIniziale);
			
			double pIniziale = 0;
			
			if(prezzoIniziale == null || prezzoIniziale.equals("")){
				messaggio="Errore!!! Prezzo d'asta Mancante!!! Campo obbligatorio!!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/WEB-INF/jsp/ripubblicaInserzione.jsp").forward(request, response);
				return;
			}
			if(Utility.isANumberDouble(prezzoIniziale)){
			
				pIniziale = Double.parseDouble(prezzoIniziale);
				
			}else{
					messaggio="Errore!!! Prezzo d'asta Non Corretto!!! ";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/ripubblicaInserzione.jsp").forward(request, response);
					return;
			}		
			
			//creo la data di fine asta
			Integer mese = null;
			Integer giorno = null;
			Integer anno = null;
			
			StringTokenizer tokenizer = new StringTokenizer(dataScadenza, "/"); 
			while(tokenizer.hasMoreTokens()){
				mese = Integer.parseInt(tokenizer.nextToken());
				System.out.println("mese: " + mese);
				giorno = Integer.parseInt(tokenizer.nextToken());
				System.out.println("giorno: " + giorno);
				anno = Integer.parseInt(tokenizer.nextToken());
				System.out.println("anno: " + anno);
			}
			
			Calendar c = Calendar.getInstance();
			c.set(anno, (mese-1), giorno); //mese-1 perchè i mesi nel gregorianCalendar partono da 0
			System.out.println("c: " + c.toString());
			
			//c.set(year, month, date, hourOfDay, minute, second); farsi dare anche l'orario di scadenza
						
			Date dataFineAsta = c.getTime();
			
			int updateRows = daoI.updateRipubblicaInserzione(pIniziale, dataFineAsta, inserzione.getIdInserzione());
			
			session.removeAttribute("inserzione");
			
			if(updateRows != -1){
				messaggio="L'inserzione n° "+ inserzione.getIdInserzione() +" è stato ripubblicata correttamente!!!";
				logger.info(new Date()+" "+messaggio);
				
				//CREO IL THREAD DI VERIFICA SCADENZA ASTA IN SEGUITO ALLA RIPUBBLICAZIONE
				Date fineAsta = inserzione.getDataScadenza();
				
				
				c = Calendar.getInstance();
				c.setTime(fineAsta);
				
				Calendar cOggi = Calendar.getInstance();
				
				long millisecond1 = c.getTimeInMillis();
				long millisecond2 = cOggi.getTimeInMillis();
				
				long tempoAttesa = millisecond1 - millisecond2;
				System.out.println("inserzione tempo attesa " + tempoAttesa + " ms");
				
				//AVVIO UN THREAD PER L'INSERZIONE. IL THREAD SI ATTIVA NEL MOMENTO IN CUI L'ASTA SCADE ED EFFETTUA L'AGGIORNAMENTO DELLO STATO DELL'INSERZIONE.
				//QUESTA STESSA OPERAZIONE VA FATTA OGNI VOLTA CHE SI INSERISCE UN INSERZIONE
				ControlloScadenzaAste csa = new ControlloScadenzaAste(inserzione, tempoAttesa);		
				csa.start();
				
				
				
			}
			else{
				messaggio="Errore!!! L' inserzione n° "+inserzione.getIdInserzione()+" non è stato ripubblicata!!!";
				logger.warn(new Date()+" "+messaggio);
				
			}
			
			session.setAttribute("messaggioMieInserzioni", messaggio);
			request.getRequestDispatcher("/ServletLeMieInserzioni").forward(request, response);
			
			
			
//			/*in seguito alla cancellazione dell'inserzione nel db, invio il controllo alla ServletLeMieInserzioni che si occupa della visualizzazione */
//			int numeroInserzioni = utenteDao.getNumeroInserzioniByIdUtenteVenditore(utenteInSessione.getIdUtente());
//			
//			System.out.println("numero inserzioni: " + numeroInserzioni);
//			
//			int numeroInserzioniPerPagina = 10;
//			
//			List<Inserzione> intervalloLeMieInserzioni = new ArrayList<Inserzione>();
//														
//			System.out.println("numero inserzioni: " + numeroInserzioni);
//				
//			int numeroPagine;
//			/*Se il rapporto tra il numero di inserzioni e il numero di inserzioni da visualizzare per pagina è un numero intero, otteniamo direttamente dalla divisione il numero di pagine necessarie per visualizzare tutte le inserzioni
//				 * Se il rapporto è dispari bisogna fare la stessa divisione ed aggiungere una pagina, necessaria per visualizzare le restanti inserzioni
//				 */
//			if((numeroInserzioni % numeroInserzioniPerPagina) == 0){
//				numeroPagine = numeroInserzioni / numeroInserzioniPerPagina;
//			}
//			else{
//				numeroPagine = (numeroInserzioni / numeroInserzioniPerPagina) + 1;
//			}
//				
//			//voglio solo le prime 10 inserzioni quindi il limite inferiore sarà 0
//			int limiteInf = 0;
//			
//			/* Ricavo dal db solo le prime 10 inserzioni(in seguito la paginazione sarà gestita dalla ServletLeMieInserzioni) da visualizzare relativi al venditore corrente */
//			intervalloLeMieInserzioni = utenteDao.getLimitInserzioniByIdUtenteVenditore(utenteInSessione.getIdUtente(), limiteInf, numeroInserzioniPerPagina);
//				
//				
////				System.out.println("VEDIAMO TUTTE LE INSERZIONI");
////				
////				for(int i=0; i<intervalloLeMieInserzioni.size(); i++){
////					
////								
////					System.out.println("titolo: " + intervalloLeMieInserzioni.get(i).getTitolo());
////					
////					for(int j=0; j<intervalloLeMieInserzioni.get(i).getImmagini().size(); j++)
////						System.out.println("immagine: " + intervalloLeMieInserzioni.get(i).getImmagini().get(j).getFoto());
////					
////				}
//				
//			
//			messaggio="Inserzione n° "+ inserzione.getIdInserzione() +" inserito correttamente !!!";
//			logger.info(new Date()+" "+messaggio);
//			
//			request.setAttribute("numeroPagineMieInserzioni", numeroPagine);
//			request.setAttribute("numeroInserzioni", numeroInserzioni);
//
//			request.setAttribute("intervalloLeMieInserzioni", intervalloLeMieInserzioni);
//		
//			request.setAttribute("messaggio", messaggio);	
//			request.getRequestDispatcher("WEB-INF/jsp/leMieInserzioni.jsp").forward(request, response);
//			
			
		}
		else{
			request.setAttribute("messaggio", "Non è possibile modificare il prodotto: utente disabilitato dall'amministratore!!! Puoi soltanto rimuovere le inserzioni scaduti !!!");
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
		}
			
		

		
		
		
		
		
	}

}
