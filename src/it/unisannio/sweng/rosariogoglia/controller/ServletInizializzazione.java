package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ComuneDao;
import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.dao.ProvinciaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ComuneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProvinciaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.model.Provincia;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.utility.ControlloScadenzaAste;



import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;


/**
 * Servlet implementation class ServletInizializzazione
 */
//@WebServlet("/ServletInizializzazione")
public class ServletInizializzazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public void init(){
    	
    	   	
    	
//    	DOMConfigurator.configure("./WebContent/WEB-INF/log4jConfigAccessoRisorse.xml");
//    	
//    	Logger logAccessi = Logger.getLogger("asteonline.logaccessi");
//    		
    	
		boolean inManutenzione = false;
    	
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
		List<Categoria> listaCategorie = categoriaDao.getCategorie();
		System.out.println("CATEGORIE CARICATE");
		
		ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		List<Produttore> listaProduttori = produttoreDao.getProduttori();
		System.out.println("PRODUTTORI CARICATI");
		
		ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
		List<Prodotto> listaProdotti = prodottoDao.getProdotti();
		System.out.println("PRODOTTI CARICATI");
		
		KeywordDao keywordDao = new KeywordDaoMysqlJdbc();
		List<Keyword> listaKeyword = keywordDao.getKeywords();
		System.out.println("KEYWORD CARICATE");
		
		ProvinciaDao provinciaDao = new ProvinciaDaoMysqlJdbc();
		List<Provincia> listaProvince = provinciaDao.getProvince();
		System.out.println("PROVINCE CARICATE");
		
		
		ComuneDao comuneDao = new ComuneDaoMysqlJdbc();
		List<Comune> listaComuni = comuneDao.getComuni();
		System.out.println("COMUNI CARICATI");
			
		ServletContext context = this.getServletConfig().getServletContext();
		
		/* 
		 * Setto nel contesto dell'applicazione la lista categorie":
		 * tale variabile rappresenta la lista di tutte le categorie
		 */
		context.setAttribute("listaCategorie", listaCategorie);
		
		/* 
		 * Setto nel contesto dell'applicazione la "listaProduttori":
		 * tale variabile rappresenta la lista di tutti i produttori
		 */
		context.setAttribute("listaProduttori", listaProduttori);
		
		/* 
		 * Setto nel contesto dell'applicazione la "listaProdotti":
		 * tale variabile rappresenta la lista di tutti i produttori
		 */
		context.setAttribute("listaProdotti", listaProdotti);
		
		/* 
		 * Setto nel contesto dell'applicazione la "listaKeyword":
		 * tale variabile rappresenta la lista di tutti i produttori
		 */
		context.setAttribute("listaKeyword", listaKeyword);
		
		
		/* 
		 * Setto nel contesto dell'applicazione la lista province":
		 * tale variabile rappresenta la lista di tutte le province che saranno scelte in fase di registrazione
		 */
		context.setAttribute("listaProvince", listaProvince);
		
		/* 
		 * Setto nel contesto dell'applicazione la lista comuni":
		 * tale variabile rappresenta la lista di tutti i comuni che saranno scelti in fase di registrazione
		 */
		context.setAttribute("listaComuni", listaComuni);
		System.out.println("lista comuni inserita nel contesto");
		/* 
		 * Setto nel contesto dell'applicazione la variabile "inManutenzione":
		 * tale variabile stabilisce quando il sito � off-line(all'inizio la settiamo ON-LINE, sar� l'amministratore a mettere il sito OFF-LINE)
		 */
		context.setAttribute("inManutenzione", inManutenzione);

		
		//CREO UN SISTEMA DI AGGIORNAMENTO DELLO STATO DELLE ASTE DI TUTTO IL DB NEL MOMENTO IN CUI AVVIENE LA SCADENZA!!!
		Inserzione inserzione = new InserzioneImpl();
		InserzioneDao dao = new InserzioneDaoMysqlJdbc();		
	
		/* Prelevo tutte le aste nel database */
		List<Inserzione> listaInserzioni = dao.getInserzioni();
		System.out.println("PRELEVIAMO LA LISTA INSERZIONI");
		
		/*
		 *  Per ogni prodotto controlla che sia in asta e che la data di fine asta sia minore di quella attuale
		 *  In tal caso aggiorna lo stato del prodotto ad aggiudicato (se c'� un acquirente) o scaduto (se non c'� un acquirente) 
		 */
		for(int i=0; i<listaInserzioni.size(); i++){
			
			inserzione = listaInserzioni.get(i);
		
			Date fineAsta = inserzione.getDataScadenza();
			Date odierna = new Date();
			
			Calendar c = Calendar.getInstance();
			c.setTime(fineAsta);
			
			Calendar cOggi = Calendar.getInstance();
			
			long millisecond1 = c.getTimeInMillis();
			long millisecond2 = cOggi.getTimeInMillis();
			
			long tempoAttesa = millisecond1 - millisecond2;
			System.out.println("inserzione " + i + " tempo attesa " + tempoAttesa + " ms");
			
			
			//AVVIO UN THREAD PER OGNI INSERZIONE PRESENTE NEL DB. IL THREAD SI ATTIVA NEL MOMENTO IN CUI L'ASTA SCADE ED EFFETTUA L'AGGIORNAMENTO DELLO STATO DELL'INSERZIONE.
			//QUESTA STESSA OPERAZIONE VA FATTA OGNI VOLTA CHE SI INSERISCE UN INSERZIONE
			ControlloScadenzaAste csa = new ControlloScadenzaAste(inserzione, tempoAttesa);		
			csa.start();
			
		
		}
		
		
    	
    }

}
