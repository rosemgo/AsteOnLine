package it.unisannio.sweng.rosariogoglia.utility;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;



	/**
	 * Questa classe contiene diversi metodi utilizzati nell'applicazione
	 * 
	 * @author Rosario Goglia
	 */
	public class Utility {
				
		//public static HashMap<Integer, Integer> indicizazionePagine = new HashMap<Integer, Integer>();

		/**
		 * Metodo utilizzato per la paginazione di una tabella. Precisamente mi restituisce un hash map che avrà per chiave il numero della pagine e per valore il limite inferiore dell'intervallo di tuple da prelevare nel db
		 * 
		 * @param numeroInserzioni
		 * @param numeroInserzioniPerPagina
		 * 
		 * @return un hash map completa
		 */
		public static HashMap<Integer, Integer> paginazione(Integer numeroInserzioni, Integer numeroInserzioniPerPagina){
			
			HashMap<Integer, Integer> indicizazionePagine = new HashMap<Integer, Integer>();

		
			int numeroPagine;
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
			int limiteInf = 0;
			
			for(int i=1; i<=numeroPagine; i++){
				
				indicizazionePagine.put(i, limiteInf);
				
				limiteInf = limiteInf + numeroInserzioniPerPagina;				
				
				System.out.println("aggiungo all'hash map chiave: " + i);
				
			}
			
			return indicizazionePagine;
				
		}
		
		
		/**
		 * Controlla che la lunghezza della stringa passata come argomento sia di n caratteri
		 * 
		 * @param str Stringa da controllare
		 * @param n Numero di caratteri
		 * 
		 * @return true se la stringa passata come argomento è di n caratteri
		 */
		public static boolean controlloLunghezza(String str, int n){
			
			return(str.length() >= n);
						
		}
		
		/**
		 * Controlla se la password immessa è uguale a quella di conferma
		 * 
		 * @param psw1 prima password
		 * @param psw2 password di conferma
		 * 
		 * @return true se è uguale
		 */
		public static boolean controlloPassword(String psw1, String psw2){

			return(psw1.equals(psw2));
		}
		
		/**
		 * Controlla che la stringa immessa sia un numero intero positivo
		 * 
		 * @param n Stringa
		 * 
		 * @return true se è un numero intero positivo
		 */
		public static boolean isANumberLongInt(String n){
			
			boolean result = true;
			try{
				long num = Long.parseLong(n);
				if(num > 0)
					result = true;
				else
					result = false;
			}
			catch (NumberFormatException e) {
				result = false;
			}
			return result;
		}
		
		/**
		 * Controlla che la stringa immessa sia un numero double positivo
		 * 
		 * @param n Stringa
		 * 
		 * @return true se è un numero double positivo
		 */
		public static boolean isANumberDouble(String n){
					
			boolean result=true;
			try{
				double num= Double.parseDouble(n);
				if(num >= 0){
					result=true;
				}else{
					result=false;
				}
			}
			catch(NumberFormatException e){
				result=false;
			}
			return result;
		}
		
		
		/**
		 * Controlla che la lunghezza del nome del prodotto non sia più di 30 caratteri consecutivi
		 * 
		 * @return true se caratteri consecutivi minori di 30
		 */
		public static boolean controlloLunghezzaNomeProdotto(String n){
			
			boolean result = true;
			char[] nome = n.toCharArray();		
			int lunghezzaNome = 0;
			
			for(int i=0; i<n.length(); i++){
				if(nome[i]!=' '){
					lunghezzaNome = lunghezzaNome + 1;
					if(lunghezzaNome>=30)
						result = false;
				}
				else{
					lunghezzaNome = 0;
				}
			}
			return result;
		}
		
		
		/**
		 * Verifica la presenza di caratteri speciali nella stringa
		 * 
		 * @param str Stringa da controllare
		 * 
		 * @return true se nella stringa è presente un carattere speciale, altrimenti false
		 */
		public static boolean hasSpecialChars(String str) {
			
			boolean result = false;
			char c;			
			for(int i=0; i<str.length(); i++){
				c = str.charAt(i);
				switch (c) {
				case '<': result=true; break;
				case '>': result=true; break;
				case '%': result=true; break;
				case '&': result=true; break;
				case '£': result=true; break;
				case '?': result=true; break;
				case '"': result=true; break;
				}
			}
			return result;
		}
		
		/**
		 * Controlla se l'immagine inserita è in formato .jpg
		 * 
		 * @param img nome immagine
		 * 
		 * @return true se l'immagine è in formato .jpg
		 */
		public static boolean isImmagineJpg(String img){
			
			boolean result= false;
			String sub=img.substring(img.length()-4);
			if(sub.equalsIgnoreCase(".jpg")){
				result=true;
			}
			return result;
		}
		
		/**
		 * Crea la data di fine asta: somma alla data corrente i giorni della durata dell'asta
		 * 
		 * @param durataAsta Giorni di durata dell'asta
		 * 
		 * @return Calendar aggiornato
		 */
		public static Calendar creaDataFineAsta(String durataAsta){
		 
			int gg= Integer.parseInt(durataAsta.substring(0, durataAsta.length()-3));
			Calendar c= Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, gg);
			return c;
			
		}

		/**
		 * Variabile statica che stabilisce il formato della data
		 */
		public static final String DATE_FORMAT_NOW = "dd/MM/yyyy HH:mm:ss";

		/**
		 * Formatta il Calendario secondo un format specifico
		 * 
		 * @param c Calendario da formattare
		 * 
		 * @return Calendario formattato
		 */
		public static String myFormatCalendar(Calendar c) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
			return sdf.format(c.getTime());
		}

		/**
		 * Converte una data Calender in una dataSql, e la restituisce.
		 * 
		 * @param c
		 * 
		 * @return sqlData
		 */		
		public static java.sql.Date convertitoreDataCalenderToDataSql(Calendar c){
		    
			java.sql.Date javaSqlDate = new java.sql.Date(c.getTime().getTime());
				    
		    return javaSqlDate;
			
		}
		
		
		/**
		 * Converte una dataUtil in una dataSql, e la restituisce.
		 * 
		 * @param utilDate
		 * 
		 * @return sqlData
		 */		
		public static java.sql.Date convertitoreDataUtilToDataSql(java.util.Date utilDate){
		    
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		 		    
		    return sqlDate;
			
		}
		
		/**
		 * Converte una dataSql in una dataUtil, e la restituisce.
		 * 
		 * @param sqlDate
		 * 
		 * @return utilData
		 */
		public static java.util.Date convertitoreDataSqlToDataUtil(java.sql.Date sqlDate){
		    
			java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
			
			return utilDate;
		
		}
		
		/**
		 * Converte una data Timestamp in una dataUtil, e la restituisce
		 * 
		 * @param dataTimestamp
		 * @return utilDate
		 */
		public static java.util.Date convertitoreTimestampToDataUtil(Timestamp dataTimestamp){
		    
			java.util.Date utilDate = new java.util.Date(dataTimestamp.getTime());
			
			return utilDate;
		
		}
		
		/**
		 * Converte una data dataUtil in una Timestamp, e la restituisce
		 * 
		 * @param utilDate
		 * @return data espressa in formato TimeStamp
		 */
		public static Timestamp convertitoreDataUtilToTimestamp(java.util.Date utilDate){
		    
			Timestamp dataTimestamp = new Timestamp(utilDate.getTime());
			
			return dataTimestamp;
		
		}
		
		
		/**
		 * Timer scadenza aste
		 */
		public static String scadenzaTimer(Date dataScadenza){
			
			@SuppressWarnings("deprecation")
			int secondiTotali = dataScadenza.getSeconds();
			
			int giorni = secondiTotali / 86400;
			int restoGiorni = secondiTotali % 86400;
			
			int ore = restoGiorni / 3600;
			int restoOre = restoGiorni % 3600;
			
			int minuti = restoOre / 60;
			int restoMinuti = restoOre % 60;
			
			int secondi = restoMinuti;
			
			return (giorni + "g " + ore + ":" + minuti + ":" + secondi);
						
		}
		
	
	}
