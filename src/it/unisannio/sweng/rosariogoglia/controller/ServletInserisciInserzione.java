package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
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
 * Servlet implementation class ServletInserisciProdotto
 */
//@WebServlet("/ServletInserisciProdotto")
public class ServletInserisciInserzione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(ServletInserisciInserzione.class); 
	
	public ServletInserisciInserzione(){
		DOMConfigurator.configure("./WebContent/WEB-INF/log4jConfig.xml");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session= request.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		/* Dichiarazioni delle variabili di istanza */
		String categoria = "";
		String produttore = "";
		String prodotto = "";
		String titolo = "";
		String descrizione = "";
		String prezzoIniziale = "";
		String dataScadenza = "";
		String nomeCampoForm = "";
		String valoreCampoForm = "";
		String nomeFile = "";
		String messaggio = "";
		
		Date dataFineAsta = new Date();
		
		double pIniziale=0;
		boolean prosegui=true;
		
		ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
		
		List<Immagine> listaImmagini = new ArrayList<Immagine>();
		Immagine immagine;
		
		Integer idInserzione = -1;
		
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
			/* L'oggetto upload permette di analizzare la richiesta ed elaborare l'elenco di elementi dell'applicazione */
			ServletFileUpload upload = new ServletFileUpload(factory);	
	
						
			try {
									
				/* Analizzo la richiesta ed elaboro la lista di elementi in essa contenuti */
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
						
						if(nomeCampoForm.equals("categoria")){
							
							categoria = valoreCampoForm;
							
							if(categoria == null || categoria.equals("0")){
								
								messaggio="Errore !!! Categoria Mancante !!! Campo obbligatorio !!!";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
								
							}
							
						}
						if(nomeCampoForm.equals("produttore")){
							
							produttore = valoreCampoForm;
							
							if(produttore == null || produttore.equals("0")){
								
								messaggio="Errore !!! Produttore Mancante !!! Campo obbligatorio !!!";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
								
							}
							
						}
						if(nomeCampoForm.equals("prodotto")){
							
							prodotto = valoreCampoForm;
								
							logger.debug("il prodotto è: " + prodotto);
							
							if(prodotto == null || prodotto.equals("0")){
								
								messaggio="Errore !!! Prodotto Mancante !!! Campo obbligatorio !!!";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
								
							}
							if(!prodottoDao.checkProdottoBelongCategoriaProduttore(Integer.parseInt(prodotto), Integer.parseInt(categoria), Integer.parseInt(produttore))){
								messaggio="Errore !!! Il prodotto selezionato non appartiene alla categoria scelta o al produttore scelto!!!";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
								
							}
														
						}
											
						
						if(nomeCampoForm.equals("titolo")){
							
							titolo = valoreCampoForm;
							
							if(titolo==null || titolo.equals("")){
								messaggio="Errore !!! Titolo Mancante !!! Campo obbligatorio !!!";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
							}
							if(!Utility.controlloLunghezzaNomeProdotto(titolo)){
								messaggio="Errore !!! Nome Troppo Lungo, hai superato i 30 caratteri consecutivi !!! Inserire Spazi !!!";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
							}
							if(Utility.hasSpecialChars(titolo)){
								messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
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
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
							}
							if(Utility.isANumberDouble(prezzoIniziale)){
							
								pIniziale= Double.parseDouble(prezzoIniziale);
								
							}else{
									messaggio="Errore !!! Campo Prezzo d'Asta Non Corretto !!! ";
									request.setAttribute("messaggio", messaggio);
									request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
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
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
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
								
								messaggio="Errore !!! L'immagine deve essere in formato .jpg !!! ";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
							}
							if(dimensioneInBytes >= 1048576){
								
								logger.debug("dimensione esagerata ");
								
								messaggio="Errore !!! L'immagine deve essere Massimo di 1 MB !!! ";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
							}
							
							logger.debug("Creo un nuovo file nella directory specificata con il nome del file inserito nel form ");
														
							immagine = new ImmagineImpl();
							
							//poichè preleviamo più immagini devo creare un arrayList di immagini e settare il campo percorso
							immagine.setFoto("immagini/inserzioni/" + nomeFile); //NON METTERE / ALL'INIZIO ALTRIMENTI NON FUNZIONA LA VISUALIZZAZIONE DELLA FOTO NEL DETTAGLIO INSERZIONE
							listaImmagini.add(immagine);
					
							
							if(listaImmagini.size() == 0){
								logger.debug(" nessun immagine inserita ");
								
								messaggio="Errore !!! Devi inserire almeno un'immagine!!! ";
								request.setAttribute("messaggio", messaggio);
								request.getRequestDispatcher("/WEB-INF/jsp/inserisciInserzione.jsp").forward(request, response);
								return;
								
							}
												
													
							/* Creo un nuovo file nella directory specificata con il nome del file inserito nel form */
							File fin = new File(this.getServletContext().getRealPath("/immagini/inserzioni"), nomeFile);
							item.write(fin);
				
							System.out.println("NOME IMMAGINE: " + nomeFile );
							System.out.println("CONTENUTO FIN: " + fin.toString());	
							

							
							//serve per gestire l'eccezione del item.write(fin);
							try {
							
								//item.write(fin);
									
								System.out.println("SCRITTURA DEL FIN1: ");
		
							}catch (Exception e) {
								logger.warn(new Date()+" Scrittura file "+ nomeFile +" non eseguita correttamente !!!");
								messaggio="Errore inserimento Inserzione!!!";
								prosegui=false;
							}
							
						}
											
					}
				}
				
				
			} catch (FileUploadException e) {
				logger.warn(new Date()+" Inserimento nuovo prodotto non eseguito correttamente !!!");
				messaggio="Errore inserimenro Prodotto !!!";
				prosegui=false;
			} catch (Exception e) {
				
				e.printStackTrace();
			}	
						
		}
					
		/* Se non vengono catturate eccezioni procede nell'inserimento del prodotto */
		if(prosegui){
				
//			Calendar dataFineC = Utility.creaDataFineAsta(dataScadenza);
//			
//			System.out.println(dataFineC);		
//					
//			Date dataFineAsta = dataFineC.getTime();
			
			Inserzione inserzione = new InserzioneImpl();
			inserzione.setTitolo(titolo);
			inserzione.setDescrizione(descrizione);
			inserzione.setPrezzoIniziale(pIniziale);
			inserzione.setPrezzoAggiornato(0);
			inserzione.setDataScadenza(dataFineAsta);
			inserzione.setStato("in asta");
			inserzione.setIdVenditore(utenteInSessione.getIdUtente());
			inserzione.setVenditore(utenteInSessione);
			inserzione.setIdProdotto(Integer.parseInt(prodotto));
			
			inserzione.setImmagini(listaImmagini);			
			
//			for(int i=0;i<listaImmagini.size();i++){
//				System.out.println(listaImmagini.get(i).getFoto());
//			}
			
			
			InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
			try {
					//inserisco l'inserzione
					idInserzione = inserzioneDao.insertInserzione(inserzione);
					
					if(idInserzione != -1){
						
						messaggio="Inserzione n° "+ idInserzione +" inserita correttamente!!!";
						logger.info(new Date()+" "+messaggio);
						
						//CREO IL THREAD DI VERIFICA SCADENZA ASTA IN SEGUITO ALL'INSERIMENTO
						Date fineAsta = inserzione.getDataScadenza();
						Date odierna = new Date();
						
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
						messaggio="Inserzione n° "+ idInserzione +" non inserita correttamente!!!";
						logger.info(new Date()+" "+messaggio);				
					}
					
					
				} catch (SQLException e) {
					messaggio="Errore !!! L'inserzione non è stato inserito correttamente !!!";
					logger.warn(new Date()+ " " + messaggio);
				}
					
		}
						
		
		session.setAttribute("messaggioMieInserzioni", messaggio);
		request.getRequestDispatcher("/ServletLeMieInserzioni").forward(request, response);
		
		
	}
		
}


