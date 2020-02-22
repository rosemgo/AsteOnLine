package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.ImmagineDao;
import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.OffertaDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.model.Immagine;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.Data;
import it.unisannio.sweng.rosariogoglia.modelImpl.ImmagineImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.OffertaImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;

public class InserzioneDaoMysqlJdbcTest {
	
	@Test
	public void testInserimentoRimozioneInserzione() throws ClassNotFoundException, SQLException, IOException {
		
		InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = new InserzioneImpl();
		Integer idInserzione = -1;
		Integer updateRows = -1;
		Integer deleteRows = -1;
		Integer result = -1;
		
		
		inserzione.setTitolo("Inserzione Test");
		inserzione.setDescrizione("Test descrizione");
		inserzione.setPrezzoIniziale(100.00);
		inserzione.setPrezzoAggiornato(100.00);
		inserzione.setDataScadenza(new Date());
		inserzione.setStato("in asta");
		inserzione.setIdAcquirente(null);
		inserzione.setIdVenditore(2);
		inserzione.setIdProdotto(3);
		
		
		Offerta offerta = new OffertaImpl();
		OffertaDao offertaDao = new OffertaDaoMysqlJdbc();
		Integer idOfferta = -1;
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		
		Immagine immagine1 = new ImmagineImpl();
		Immagine immagine2 = new ImmagineImpl();
		ImmagineDao ImmagineDao = new ImmagineDaoMysqlJdbc();

		
		immagine1.setFoto("Test immagine 1");
		immagine2.setFoto("Test immagine 2");
		
		List<Immagine> listImmagini = new ArrayList<>();
		listImmagini.add(immagine1);
		listImmagini.add(immagine2);
		
		inserzione.setImmagini(listImmagini);
		idInserzione = inserzioneDao.insertInserzione(inserzione);
		
		Inserzione readingInserzione = inserzioneDao.getInserzioneById(idInserzione);
		
		assertEquals(readingInserzione.getIdInserzione(), inserzione.getIdInserzione());
		assertEquals(readingInserzione.getTitolo(), inserzione.getTitolo());
		
		//aggiorno il titolo dell'inserzione e la lista immagini
		
		listImmagini.clear();
		immagine1.setFoto("updateImmagine1");
		immagine2.setFoto("updateImmagine2");
		listImmagini.add(immagine1);
		listImmagini.add(immagine2);
		inserzione.setTitolo("updateInserzione");
		inserzione.setImmagini(listImmagini);
		updateRows = inserzioneDao.updateInserzione(inserzione);
		assertEquals((Integer)1, updateRows);
		
		//visualizzo l'immagine con l'id passato come parametro
		Immagine immagine3 = new ImmagineImpl();
		immagine3 = ImmagineDao.getImmagineById(inserzione.getImmagini().get(0).getIdImmagine());
		assertEquals(immagine3.getIdImmagine(), immagine1.getIdImmagine());
		
		//cancello un'immagine dell'inserzione
		deleteRows = -1;
		deleteRows = ImmagineDao.deleteImmagine(immagine1);
		assertEquals((Integer)1, deleteRows);
		
		
		//cancello le immagini dell'inserzione
		deleteRows = -1;
		deleteRows = ImmagineDao.deleteImmagineByIdInserzione(inserzione.getIdInserzione());
		assertEquals((Integer)1, deleteRows);
		
	
		//aggiorno lo stato dell'asta
		updateRows = -1;
		updateRows = inserzioneDao.updateStatoInserzione("scaduta", inserzione.getIdInserzione());
		assertEquals((Integer)1, updateRows);
		
		//aggiorno la data di scadenza e il prezzo iniziale dell'inserzione 
		updateRows = -1;
		Calendar c = Calendar.getInstance();
		c.set(2020, 8, 11);
		Date dataScadenza = new Date();
		dataScadenza = c.getTime();
		
		updateRows = inserzioneDao.updateRipubblicaInserzione(10.00, dataScadenza, inserzione.getIdInserzione());
		assertEquals((Integer)1, updateRows);
		
		//inserisco un'offerta all'inserzione
		offerta.setSomma(15.00);
		offerta.setData(new Date());
		offerta.setIdInserzione(inserzione.getIdInserzione());
		offerta.setInserzione(inserzione);
		offerta.setIdUtenteRegistrato(3);
		offerta.setUtente(utenteDao.getUtenteRegistratoById(3));
		idOfferta = offertaDao.insertOfferta(offerta);
		
		
		//aggiorno l'acquirente e il prezzo dell'inserzione
		updateRows = -1;
		updateRows = inserzioneDao.updateAcquirenteOffertaInserzione(3, 15.00, inserzione.getIdInserzione());
		assertEquals((Integer)1, updateRows);
		
		//cancello tutte le offerte all'inserzione
		deleteRows = -1;
		deleteRows = offertaDao.deleteOffertaByIdInserzione(inserzione.getIdInserzione());
		assertEquals(deleteRows, (Integer)1);
		
		
		
		deleteRows = inserzioneDao.deleteInserzione(idInserzione);
		
		assertEquals((Integer)1,deleteRows);
	
		
	}
	
	@Test
	public void testRicercaInserzioni() throws ClassNotFoundException, SQLException, IOException {
		
		
		//****INIZIO TEST BLACK BOX SU RICERCA INSERZIONE
		
				//EXISTENCE1
				//ricerca inserzione per parola chiave keyword=null idCategoria=0
		
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				List<Inserzione> result = new ArrayList<Inserzione>();
				String keyword=null;		
				result = inserzioneDao.ricercaInserzioni(keyword, 0);
				System.out.println("result" + result.size());
					
					
				//EXISTENCE2_LENGHT2_CONTENT1_CHOICE_1
				//ricerca inserzione per parola chiave keyword='calcio' idCategoria=7
		
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("calcio", 0); //ottengo il ramo if(rs.next) preso, perch� trova qualche inserzione 
			//	assertEquals((Integer) 35,result.get(0).getIdInserzione());
				
				//EXISTENCE2_LENGHT2_CONTENT1_CHOICE_2
				//ricerca inserzione per parola chiave keyword='calcio' idCategoria=7
		
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("felpa", 7); //per ottenere il branch giallo per il blackbox, inserire calcio oppure felpa e categoria 7 cos� trova qualche inserzione, per ottenere il 100% dei branch usare linux con categoria 7 cos� non trova nessuna inserzione e copre il ramo if(rs.next) NON preso
			//	assertEquals((Integer) 35,result.get(0).getIdInserzione());
				
				
				//EXISTENCE2_LENGHT2_CONTENT2_CHOICE_1
				//ricerca inserzione per parola chiave keyword='abcdfg' idCategoria=0
				
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("abcdfg", 0);
			//	assertEquals((Integer) 35,result.get(0).getIdInserzione());
				
				//EXISTENCE2_LENGHT2_CONTENT2_CHOICE_2
				//ricerca inserzione per parola chiave keyword='abcdfg' idCategoria=7
				
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("abcdfg", 7);
			//	assertEquals((Integer) 35,result.get(0).getIdInserzione());
				
				//LENGHT1_CHOICE1
				//ricerca inserzione per parola chiave keyword='' idCategoria='0'
				
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("", 0);
				//assertEquals(null ,result.get(0).getIdInserzione());
				
				//LENGHT1_CHOICE2
				//ricerca inserzione per parola chiave keyword='' idCategoria='7'
				
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("", 7);
				//assertEquals(null ,result.get(0).getIdInserzione());
				
				
				//LENGHT3_CHOICE0
				//ricerca inserzione per parola chiave keyword='abcdfggrtedkgtrdgchjk'
				
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("abcdfggrtedkgtrdgchjk", 0);
			//	assertEquals((Integer) 35,result.get(0).getIdInserzione());
				
				//LENGHT3_CHOICE1
				//ricerca inserzione per parola chiave keyword='abcdfggrtedkgtrdgchjk'
				
				inserzioneDao = new InserzioneDaoMysqlJdbc();
				result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("abcdfggrtedkgtrdgchjk", 7);
			//	assertEquals((Integer) 35,result.get(0).getIdInserzione());
							
				
				
		//****FINE TEST BLACK BOX SU RICERCA INSERZIONE
				
				
				
				//ricerca avanzata inserzioni pi� osservate
				
				Integer result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniRicercaAvanzata("calcio", 7, "tuta juve stagione17/18", 0.00, 100.00);
				assertEquals((Integer) 1,result2);
				
				//ricerca inserzioni limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaLimitInserzioni("cellulare",3,0,1);
				assertEquals((Integer) 36,result.get(0).getIdInserzione());
				
				//ricerca avanzata limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaAvanzataInserzioneLimitInserzioni("cellulare", 3, "iPhone X NUOVO", 0.00, 1000.00, 0, 3);
				assertEquals((Integer) 33,result.get(0).getIdInserzione());
				
				//ricerca inserzioni top popolari
				
				result.removeAll(result);
				result = inserzioneDao.ricercaTopInserzioniPopolari(2);
				assertEquals((Integer) 37,result.get(0).getIdInserzione());
				assertEquals( (Integer) 35,result.get(1).getIdInserzione());
				
				//ricerca top inserzioni in chiusura
				
				result.removeAll(result);
				result = inserzioneDao.ricercaTopInserzioniChiusura(3);
				assertEquals((Integer) 49,result.get(0).getIdInserzione());
				assertEquals((Integer) 38,result.get(1).getIdInserzione());
				assertEquals((Integer) 42,result.get(2).getIdInserzione());
				
				//ricerca inserzione per titolo limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaLimitInserzioniPerTitolo("felpa adidas con cappuccio", 0, 3);
				assertEquals((Integer) 38,result.get(0).getIdInserzione());
				
				//ricerca avanzata numero prodotto
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniRicercaAvanzataProdotto(3, 5, 4);
				assertEquals((Integer) 1,result2);

				//ricerca avanzata prodotto limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaAvanzataInserzioneLimitProdotti(3, 5, 4, 0, 4);
				assertEquals((Integer) 33,result.get(0).getIdInserzione());


		
	}
	
	@Test
	public void testNumeroInserzioni() {
		
				//numero inserzioni presenti nel db
				
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				Integer result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioni();
				assertEquals((Integer) 21,result2);
				
				//numero aste in corso
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroAsteInCorso();
				assertEquals((Integer) 14,result2);
				
				//numero aste in chiusura
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroAsteInChiusura();
				assertEquals((Integer) 11,result2);

				//numero inserzioni cercate
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniCercate("cellulare", 3);
				assertEquals((Integer) 2,result2);
				
				//numero inserzioni per titolo
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniPerTitolo("iphone x nuovo");
				assertEquals(result2 , (Integer) 1);
				
				//numero inserzioni
				result2=-1;
				result2=inserzioneDao.getNumeroInserzioniPerTitolo("Asus vivobook pro");
				
				
	}
	
	@Test
	public void testGestioneAste() throws ClassNotFoundException, SQLException, IOException {
		
				//aste in chiusura limit
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				List<Inserzione> result = new ArrayList<Inserzione>();
				result.removeAll(result);
				result = inserzioneDao.getLimitInserzioniChiusura(0, 10);
				//confronto se le prime due aste in chiusura corrispondo a quelle con id 35 e 36
				assertEquals((Integer) 49,result.get(0).getIdInserzione());
				assertEquals((Integer) 38,result.get(1).getIdInserzione());
				
				//aste in corso limit
				
				result.removeAll(result);
				result = inserzioneDao.getLimitAsteInCorso(1, 3);
				assertEquals((Integer) 33,result.get(0).getIdInserzione());
				assertEquals((Integer) 35,result.get(1).getIdInserzione());
				assertEquals((Integer) 36,result.get(2).getIdInserzione());
				
				//limit inserzioni
				
				result.removeAll(result);
				result = inserzioneDao.getLimitInserzioni(2, 4);
				assertEquals((Integer) 30,result.get(0).getIdInserzione());
				assertEquals((Integer) 31,result.get(1).getIdInserzione());
				assertEquals((Integer) 32,result.get(2).getIdInserzione());
				
				//inserzione by id
				
				Inserzione inserzione = new InserzioneImpl();
				inserzione = inserzioneDao.getInserzioneById(29);
				assertEquals((Integer) 29,inserzione.getIdInserzione());

				//inserzione senza liste
				
				inserzione = inserzioneDao.getInserzioneByIdSenzaListe(50);
				assertEquals((Integer) 50,inserzione.getIdInserzione());
		
	}
	
	@Test
	public void testOrdinamentoAste() throws ClassNotFoundException, SQLException, IOException {
		
				//ordina inserzioni popolari
				
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				List<Inserzione> result = new ArrayList<Inserzione>();
				
				result = inserzioneDao.ordinaInserzioniPopolari();
				assertEquals((Integer) 37,result.get(0).getIdInserzione());
				assertEquals((Integer) 35,result.get(1).getIdInserzione());
				assertEquals((Integer) 36,result.get(2).getIdInserzione());
				assertEquals((Integer) 40,result.get(3).getIdInserzione());
				assertEquals((Integer) 29,result.get(4).getIdInserzione());

		
	}
	
	@Test
	public void testInserzioni() throws ClassNotFoundException, SQLException, IOException {
		
		InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
		
		//restituisce tutte le inserzioni presenti nel DB
		List<Inserzione> listaInserzioni=new ArrayList<Inserzione>();
		listaInserzioni=inserzioneDao.getInserzioni();
		
		//restituisce tutti gli utenti che osservano l'inserzione con id=37
		List<UtenteRegistrato> listaUtenti = new ArrayList<UtenteRegistrato>();
		listaUtenti=inserzioneDao.getUtentiRegistratiOsservanoByIdInserzione(37);
		
		//restituisce tutti i titoli delle inserzioni presenti nel db
		List<String> listaTitoli = new ArrayList<String>();
		listaTitoli=inserzioneDao.getTitoli();
		
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();	
		
		//inserisci inserzione con id 44 tra le osservate dall'utente 2
		UtenteRegistrato utente = new UtenteRegistratoImpl();
		Inserzione inserzione = new InserzioneImpl();
		
		utente=utenteDao.getUtenteRegistratoById(2);
		inserzione=inserzioneDao.getInserzioneById(44);
		
		utenteDao.insertOsservaInserzione(utente, inserzione);
		
		//rimuovi inserzione con id 44 dalle inserzioni osservate dall'utente 2
		inserzioneDao.deleteInserzioneOsservata(44, 2);
		
		
	}
	
	

}
