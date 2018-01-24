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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;



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
				
// Queste righe non servono perchè nelle servlet ottengo la connessione utilizzando la classe ConnectionPoolTomcat, in realta basterebbe solo prelevare la risorsa data source dal contesto e definire la connessione.
// connection = context.getAttribute("dataSource").getConnection(); 
//PER UTILIZZARE QUESTE RIGHE AVREI DOVUTO PASSARE AI METODI DAO LA CONNESSIONE COME PARAMETRO!!! DATO CHE I METODI DAO, NELLA MIA APP CREANO DA SOLI LA CONNESSIONE E' NECESSARIO UTILIZZARE LA CLASSE ConnectionPoolTomcat
/*		Context initContext;
		Connection conn = null;
		DataSource ds = null;
		try {

			initContext = new InitialContext(); // creiamo il context per accedere al servizio dei nomi
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/AsteOnLine");
			
			context.setAttribute("dataSource", ds); //metto il dataSource nel contesto, in modo tale da richiamarlo quando serve per ottenere la connessione
			//conn = ds.getConnection();

			// System.out.println("connection: " + conn);

		} catch (NamingException e) {
			e.printStackTrace();
		}
*/	
		
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
		 * tale variabile stabilisce quando il sito è off-line(all'inizio la settiamo ON-LINE, sarà l'amministratore a mettere il sito OFF-LINE)
		 */
		context.setAttribute("inManutenzione", inManutenzione);
		
		
		
//		ControlloScadenzaAste csa = new ControlloScadenzaAste();
//		csa.start();
		
		
		//CREO UN SISTEMA DI AGGIORNAMENTO DELLO STATO DELLE ASTE DI TUTTO IL DB NEL MOMENTO IN CUI AVVIENE LA SCADENZA!!!
		Inserzione inserzione = new InserzioneImpl();
		InserzioneDao dao = new InserzioneDaoMysqlJdbc();		
	
		/* Prelevo tutte le aste nel database */
		List<Inserzione> listaInserzioni = dao.getInserzioni();
		System.out.println("PRELEVIAMO LA LISTA INSERZIONI");
	

		
    	
    }

}
