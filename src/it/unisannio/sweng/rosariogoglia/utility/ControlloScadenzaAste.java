package it.unisannio.sweng.rosariogoglia.utility;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.BannedCookiesDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;

import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ControlloScadenzaAste extends Thread{

	Logger logger = Logger.getLogger(ControlloScadenzaAste.class);
	
	private Inserzione inserzione;
	private long tempoAttesa; //sarebbe la differenza tra la data di scadenza e la data odierna. In pratica il thread di aggiornamento stato dell'inserzione si attiva nel momento in cui l'inserzione scade
	
	private static Vector<Integer> listaInserzioni = new Vector<Integer>();
	
		
	public ControlloScadenzaAste() {
		DOMConfigurator.configure("./WebContent/WEB-INF/log4jConfig.xml");
	}


	public ControlloScadenzaAste(Inserzione inserzione, long tempoAttesa) {
		this.inserzione = inserzione;
		this.tempoAttesa = tempoAttesa;
	}


	@SuppressWarnings("static-access")
	public void run(){
		
			if(!this.listaInserzioni.contains(this.inserzione.getIdInserzione())){
				this.listaInserzioni.add(this.inserzione.getIdInserzione());
				System.out.println("non contiene l'inserzione");
			}
			
			int posizioneIdInserzione = this.listaInserzioni.indexOf(this.inserzione.getIdInserzione());
			System.out.println("posizione nel vettore: " + posizioneIdInserzione);
			
			System.out.println("id inserzione: " + this.inserzione.getIdInserzione());
			
			
			//CONTROLLARE SCADENZA ASTE DI TUTTO IL DB!!!
			InserzioneDao dao = new InserzioneDaoMysqlJdbc();		
						
			System.out.println("attendo prima di avviare il thread");
			
			//se l'inserzione non ancora è scaduta è necessario aspettare la scadenza e poi avviarne il thread, nel caso fosse già scaduta bisogna effettuare direttamente l'aggiornamento
			if(this.tempoAttesa > 0){
							
				//Integer idInserzione = this.inserzione.getIdInserzione();
				
				
				try {
					Thread.sleep(this.tempoAttesa);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				//VERIFICO CHE DOPO IL PERIODO DI ATTESA L'INSERZIONE SIA SEMPRE PRESENTE NEL DB E NON SIA STATA MODIFICATA!!!
				//PER QUESTA RAGIONE LA PRELEVO DAL DBUTILIZZANDO L'ID CHE PRECEDENTEMENTE AVEVO SALVATO
				
				
				synchronized (this.listaInserzioni.get(posizioneIdInserzione)) {
				
					System.out.println("ARRIVA PRIMA L'INSERZIONE: " + this.inserzione.getIdInserzione());
					
					this.inserzione = dao.getInserzioneById(this.listaInserzioni.get(posizioneIdInserzione)); 
					
					System.out.println("prelevo di nuovo l'inserzione");
					
				}
								
			}
			
			//SE L'INSERZIONE ESISTE ANCORA(DOPO IL PERIODO DI ATTESA) EFFETTUO IL CONTROLLO SULLA SCADENZA(SE L'INSERZIONE HA SUBITO DELLE MODIFICHE SI ENTRARE IN QUESTO PRIMO IF, MA NN SARA' EFFETTUATO L'AGGIORNAMENTO STATO)
			//NEL CASO IN CUI NON SI ABBIA UN PERIODO DI ATTESA PERCHè L'INSERZIONE E' GIà SCADUTA, ABBIAMO LA CERTEZZA CHE SIA DIVERSA DA NULL 
			if(this.inserzione != null){
				
				System.out.println("avvio il thread");
					
				/*
				 *  Per ogni prodotto controlla che sia in asta e che la data di fine asta sia minore di quella attuale
				 *  In tal caso aggiorna lo stato del prodotto ad aggiudicato (se c'è un acquirente) o scaduto (se non c'è un acquirente) 
				 */
				Date fineAsta = this.inserzione.getDataScadenza();
				Date odierna = new Date();
				
				String statoInserzione = this.inserzione.getStato();
				
				//solo un thread alla volta può effettuare operazione con lo stesso idinserzione (quindi sulla stessa RISORSA!!!)
				synchronized (this.listaInserzioni.get(posizioneIdInserzione)) {
									
					if(statoInserzione.equals("in asta") && (fineAsta.compareTo(odierna) <= 0)){
							
						/*Se non c'è nessun acquirente l'idacquirente sarà 0*/
						if(inserzione.getIdAcquirente() == 0){
							statoInserzione = "scaduta";
						}
						else{
							statoInserzione = "aggiudicata";
						}
							
						// Esegue l'aggiornamento dell'inserzione nel database
						dao.updateStatoInserzione(statoInserzione, this.inserzione.getIdInserzione());
					
						
						//in seguito all'aggiornamento, se l'inserzione è stata aggiudicata invio la mail.
						if(statoInserzione.equals("aggiudicata")){
							//posso inviare la mail all'utente acquirente e all'utente venditore
							
							System.out.println("INVIO MAIL DI CONFERMA VENDITA");
						
							
							String mittente = "amministrazione@asteonline.com";
						    String mailVenditore = this.inserzione.getVenditore().geteMail();
						    String mailAcquirente = this.inserzione.getAcquirente().geteMail();
						    String oggetto = "ASTEONLINE: INSERZIONE AGGIUDICATA!!!";
						    String testoVenditore = ("" + '\n' +'\n' + "Siamo lieti di comunicarti che la tua inserzione è stata venduta!!! " + '\n' + '\n' + '\n' +
						    		"Inserzione: " + inserzione.toString() + '\n' + '\n' + '\n' +
						    		"Acquirente: " + inserzione.getAcquirente().toString() + '\n' + '\n' + '\n' +
						    		"Accedi direttamente al sito dal link sottostante e... Buon AsteOnline!!!" + '\n' + '\n' + '\n' +
						    		"                    http://localhost:8080/AsteOnLine/index");
						    String testoAcquirente = ("" + '\n' +'\n' + "Siamo lieti di comunicarti sei il vincitore dell'asta: " + '\n' + '\n' + '\n' +
						    		inserzione.toString() + '\n' + '\n' + '\n'+
						    		"Accedi direttamente al sito dal link sottostante e... Buon AsteOnline!!!" + '\n' + '\n' + '\n' +
						    		"                    http://localhost:8080/AsteOnLine/index");
						   	
						    try{
						    	
						      System.out.println("INVIO LA MAIL ORA");	
						      MailUtility.sendMail(mailVenditore, mittente, oggetto, testoVenditore);
						      
						      MailUtility.sendMail(mailAcquirente, mittente, oggetto, testoAcquirente);
						      
						      logger.debug("Mail inviata correttamente sia al venditore che all'acquirente");
						    }
						    catch (MessagingException exc){
						    	
						    	//INSERIRE L'ECCEZIONE!!! MESSAGGIO DI ERRORE!!!
						    	System.out.println("errore invio mail");
						    	
						    }
								
						}
									
						System.out.println("STATO ASTE AGGIORNATO dal thread con sincro");
					}
				
				}
					
				
			}
			
		}	
	
}
	
