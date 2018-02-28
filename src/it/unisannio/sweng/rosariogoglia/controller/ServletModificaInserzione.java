package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Immagine;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.ImmagineImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.utility.ControlloScadenzaAste;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Servlet implementation class ServletModificaInserzione
 */
//@WebServlet("/ServletModificaInserzione")
public class ServletModificaInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(ServletModificaInserzione.class); 
	

	
 	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session= request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
				
		String idInserzioneDaModificare = request.getParameter("idInserzioneDaModificare");
		
		InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = inserzioneDao.getInserzioneById(Integer.parseInt(idInserzioneDaModificare));
		
		//posso salvarmi la vecchia data scadenza e confrontarla con la nuova. In caso di modifica devo lanciare il thread per l'inserzione
		//Date vecchiaDataScadenza = inserzione.getDataScadenza(); 
		
		
		/* Dichiarazioni delle variabili di istanza */
		String titolo = "";
		String descrizione = "";
		String prezzoIniziale = "";
		String dataScadenza = "";
		String nomeCampoForm = "";
		String valoreCampoForm = "";
		String nomeFile = "";
		String messaggio = "";
		Date dataFineAsta = null;
		double pIniziale=0;
		boolean prosegui=true;
		
		int updateRows = -1;
		
		List<Immagine> listaImmagini = new ArrayList<Immagine>();
		Immagine immagine;
		
		
		/* Analizzo la richiesta per definirne il contentType */
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		logger.debug("multipart è: " +isMultipart);
		
		/* Verifico che il type della richiesta sia MULTIPART/FORM-DATA */
		if(isMultipart){
			
			logger.debug("è  multipart");
			
			/* 
			 * Definisco un factory di tipo Disk per la creazione di oggetti FileItem
			 * Tale factory mantiene in memoria i FileItem di dimensioni minori di 10KB, per FileItem più grandi li memorizza nella directory temporanea del sistema.
			 */
			DiskFileItemFactory factory = new DiskFileItemFactory();
			/* L'oggetto upload permette di analizzare la richiesta e elaborare l'elenco di elementi dell'applicazione */
			ServletFileUpload upload = new ServletFileUpload(factory);	
			
			try {
				/* Analizzo la richiesta e elaboro la lista di elementi in essa contenuti */
				List<FileItem> itemList = upload.parseRequest(request);
				
				logger.debug("creiamo la lista item");
				
				for(int i=0; i<itemList.size(); i++){
					FileItem item = itemList.get(i);
					
					logger.debug("scorriamo ogni elemento");
					
					
					/* 
					 * Verifico se il FileItem corrente è un campo di un FORM html oppure è un elemento diverso:
					 * Se è un campo FORM prelevo le proprietà nome e valore del campo;
					 * altrimenti gestisco l'elemento file scrivendone il contenuto nella directory specificata.
					 */
					if(item.isFormField()){
						
						logger.debug("è un FORM FIELD");
						
						nomeCampoForm = item.getFieldName(); //corrispondente all'attributo name=" " del form
						valoreCampoForm = item.getString(); //corrispondente al valore inserito dall' utente nel form
													
						if(nomeCampoForm.equals("titolo")){
							
							titolo = valoreCampoForm;
							
							if(titolo==null || titolo.equals("")){
								messaggio="Errore !!! Titolo Mancante !!! Campo obbligatorio !!!";
								request.setAttribute("messaggio", messaggio);
								request.setAttribute("inserzione", inserzione);
								request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
								
								return;
							}
							if(!Utility.controlloLunghezzaNomeProdotto(titolo)){
								messaggio="Errore !!! Nome Troppo Lungo, hai superato i 30 caratteri consecutivi !!! Inserire Spazi !!!";
								request.setAttribute("messaggio", messaggio);
								request.setAttribute("inserzione", inserzione);
								request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
								return;
							}
							if(Utility.hasSpecialChars(titolo)){
								messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
								request.setAttribute("messaggio", messaggio);
								request.setAttribute("inserzione", inserzione);
								request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
								return;
							}
							
						}
						if(nomeCampoForm.equals("descrizione")){
							
							descrizione = valoreCampoForm;
							
						}
						
						if(nomeCampoForm.equalsIgnoreCase("prezzoIniziale")){
							
							prezzoIniziale= valoreCampoForm;
							
							if(prezzoIniziale==null || prezzoIniziale.equals("")){
								messaggio="Errore !!! Prezzo d'Asta Mancante !!! Campo obbligatorio !!!";
								request.setAttribute("messaggio", messaggio);
								request.setAttribute("inserzione", inserzione);
								request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
								return;
							}
							if(Utility.isANumberDouble(prezzoIniziale)){
							
								pIniziale= Double.parseDouble(prezzoIniziale);
								
							}else{
									messaggio="Errore !!! Campo Prezzo d'Asta Non Corretto !!! ";
									request.setAttribute("messaggio", messaggio);
									request.setAttribute("inserzione", inserzione);
									request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
									return;
								}
						}
						
						if(nomeCampoForm.equalsIgnoreCase("data_scadenza")){
							
							dataScadenza = valoreCampoForm;
							System.out.println("data scadenza: " + dataScadenza);
							
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
							c.set(anno, mese-1, giorno); //mese-1 perchè i mesi nel gregorianCalendar partono da 0
							System.out.println("c: " + c.toString());
							
							//c.set(year, month, date, hourOfDay, minute, second); farsi dare anche l'orario di scadenza
							dataFineAsta = c.getTime();
							logger.debug("data fine asta creata: " + dataFineAsta);
							
							//compariamo la data di fine asta con la data odierna
							if(dataFineAsta.compareTo(Calendar.getInstance().getTime()) <= 0){
								messaggio="Errore!!! Data non valida!!! ";
								request.setAttribute("messaggio", messaggio);
								request.setAttribute("inserzione", inserzione);
								request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
								return;
							}
							
							
						}
												
					}
					else{
						
						logger.debug("E' UN'IMMAGINE");
						
						nomeFile = item.getName(); //corrispondente al nome del File
						
						logger.debug("nome file: " + nomeFile);
						
						long dimensioneInBytes = item.getSize(); //corrispondente alla dimensione del File		
						
						logger.debug("dimensione file: " + dimensioneInBytes);
						
					/*	if(nomeFile==null || nomeFile.equals("")){
							
							logger.debug("nome file null");
							
							messaggio="Errore !!! Immagine Mancante !!! Campo obbligatorio !!!";
							request.setAttribute("messaggio", messaggio);
							request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
							return;
						}			
					*/	
						
						if(nomeFile==null || nomeFile.equals("")){
							
							logger.debug("nome file null");
							
						}						
						else{
								
							
							/* Controllo che l'immagine sia in formato jpg e che la dimensione non super 1 MB */
							if(!Utility.isImmagineJpg(nomeFile)){
								
								logger.debug("immagine jpg ");
								
								messaggio="Errore!!! L'immagine deve essere in formato .jpg!!! ";
								request.setAttribute("messaggio", messaggio);
								request.setAttribute("inserzione", inserzione);
								request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
								return;
							}
							if(dimensioneInBytes >= 1048576){
								
								logger.debug("dimensione esagerata ");
								
								messaggio="Errore!!! L'immagine deve essere Massimo di 1 MB!!! ";
								request.setAttribute("messaggio", messaggio);
								request.setAttribute("inserzione", inserzione);
								request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
								return;
							}
							
							logger.debug("Creo un nuovo file nella directory specificata con il nome del file inserito nel form ");
							
							/* Creo un nuovo file nella directory specificata con il nome del file inserito nel form */
							File fin=new File( getServletContext().getRealPath("/immagini/inserzioni"), nomeFile);
							
							try {
							
								item.write(fin);
								
								immagine = new ImmagineImpl();
								
								//poichè preleviamo più immagini devo creare un arrayList di immagini e settare il campo percorso
								immagine.setFoto("immagini/inserzioni/" + nomeFile);
								listaImmagini.add(immagine);
								
								logger.debug("immagine aggiunta");
							
							
							}catch (Exception e) {
								logger.warn(new Date()+" Scrittura file "+nomeFile+" non eseguita correttamente !!!");
								messaggio="Errore inserimenro Prodotto!!!";
								prosegui=false;
							}
							
						}
											
					}
				}
				
				//almeno un'immagine deve essere caricata
				if(listaImmagini.size() == 0){
					logger.debug(" nessun immagine inserita ");
					
					messaggio="Errore!!! Devi inserire almeno un' immagine!!! ";
					request.setAttribute("messaggio", messaggio);
					request.setAttribute("inserzione", inserzione);
					request.getRequestDispatcher("/WEB-INF/jsp/modificaInserzione.jsp").forward(request, response);
					return;
					
				}
				
				
			} catch (FileUploadException e) {
				logger.warn(new Date()+" Inserimento nuovo prodotto non eseguito correttamente!!!");
				messaggio="Errore inserimenro Prodotto!!!";
				prosegui=false;
			}	
						
		}
					
		/* Se non vengono catturate eccezioni procede nell'inserimento del prodotto */
		if(prosegui){

			
			inserzione.setTitolo(titolo);
			inserzione.setDescrizione(descrizione);
			inserzione.setPrezzoIniziale(pIniziale);
			inserzione.setPrezzoAggiornato(0);
			inserzione.setDataScadenza(dataFineAsta);
			inserzione.setStato("in asta");
			
			inserzione.setImmagini(listaImmagini);			
			
			
			
			try {
				
				//inserisco l'inserzione
				updateRows = inserzioneDao.updateInserzione(inserzione);
				
				session.removeAttribute("inserzione");
								
				if(updateRows != -1){
					
					
					messaggio="Inserzione n° "+ inserzione.getIdInserzione() +" aggiornata correttamente!!!";
					logger.info(new Date()+" "+messaggio);
					
					
					
					//NON SAREBBE NECESSARIO AVVIARE UN NUOVO THREAD SE L'UTENTE NON MODIFICASSE LA DATA DI SCADENZA!!! BASTA CONFRONTARE LA CECCHIA DATA DI SCADENZA CON QUELLA NUOVA, NEL CASO SIANO DIVERSE SI AVVIA IL THREAD
					//if(vecchiaDataScadenza.compareTo(dataFineAsta) != 0){ AVVIO IL THREAD }
					
					//CREO IL THREAD DI VERIFICA SCADENZA ASTA IN SEGUITO ALLA MODIFICA
					Date fineAsta = inserzione.getDataScadenza();
										
					Calendar c = Calendar.getInstance();
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
					logger.warn(new Date()+" "+messaggio);
					messaggio="Errore!!! L'inserzione non è stata aggiornata correttamente!!!";					
				}
				
		
					
			} catch (SQLException e) {
				messaggio="Errore!!! L'inserzione non è stata aggiornata correttamente !!!";
				logger.warn(new Date()+ " " + messaggio);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
					
		}
		
		session.setAttribute("messaggioMieInserzioni", messaggio);
		request.getRequestDispatcher("/ServletLeMieInserzioni").forward(request, response);
		
		
		
	}

}
