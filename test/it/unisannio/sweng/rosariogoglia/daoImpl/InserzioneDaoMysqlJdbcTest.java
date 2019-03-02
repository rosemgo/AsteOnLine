package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.model.Immagine;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.modelImpl.ImmagineImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.InserzioneImpl;

public class InserzioneDaoMysqlJdbcTest {
	
	@Test
	public void testInserimentoRimozioneInserzione() throws ClassNotFoundException, SQLException, IOException {
		
		InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = new InserzioneImpl();
		Integer idInserzione = -1;
		inserzione.setTitolo("Inserzione Test");
		inserzione.setDescrizione("Test descrizione");
		inserzione.setPrezzoIniziale(100.00);
		inserzione.setPrezzoAggiornato(100.00);
		inserzione.setDataScadenza(new Date());
		inserzione.setStato("in asta");
		inserzione.setIdAcquirente(null);
		inserzione.setIdVenditore(2);
		inserzione.setIdProdotto(3);
		
		
		Immagine immagine1 = new ImmagineImpl();
		Immagine immagine2 = new ImmagineImpl();
		
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
		
		Integer deleteRows = inserzioneDao.deleteInserzione(idInserzione);
		
		assertEquals(deleteRows, (Integer)1);
	
		
	}
	
	@Test
	public void testRicercaInserzioni() throws ClassNotFoundException, SQLException, IOException {
		
				//ricerca inserzione per parola chiave
		
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				List<Inserzione> result = new ArrayList<Inserzione>();
				result = inserzioneDao.ricercaInserzioni("calcio", 7);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 35);
				
				//ricerca avanzata inserzioni più osservate
				
				Integer result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniRicercaAvanzata("calcio", 7, "tuta juve stagione17/18", 0.00, 100.00);
				assertEquals(result2 , (Integer) 1);
				
				//ricerca inserzioni limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaLimitInserzioni("smartphone",3,0,1);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 41);
				
				//ricerca avanzata limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaAvanzataInserzioneLimitInserzioni("smartphone", 3, "Huawei p10 usato", 0.00, 600.00, 0, 3);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 39);
				
				//ricerca inserzioni top popolari
				
				result.removeAll(result);
				result = inserzioneDao.ricercaTopInserzioniPopolari(2);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 37);
				assertEquals(result.get(1).getIdInserzione(), (Integer) 34);
				
				//ricerca top inserzioni in chiusura
				
				result.removeAll(result);
				result = inserzioneDao.ricercaTopInserzioniChiusura(3);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 34);
				assertEquals(result.get(1).getIdInserzione(), (Integer) 35);
				assertEquals(result.get(2).getIdInserzione(), (Integer) 36);
				
				//ricerca inserzione per titolo limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaLimitInserzioniPerTitolo("felpa adidas con cappuccio", 0, 3);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 38);
				
				//ricerca avanzata numero prodotto
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniRicercaAvanzataProdotto(3, 7, 45);
				assertEquals(result2 , (Integer) 1);

				//ricerca avanzata prodotto limit
				
				result.removeAll(result);
				result = inserzioneDao.ricercaAvanzataInserzioneLimitProdotti(3, 7, 45, 0, 4);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 41);


		
	}
	
	@Test
	public void testNumeroInserzioni() {
		
				//numero inserzioni presenti nel db
				
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				Integer result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioni();
				assertEquals(result2 , (Integer) 21);
				
				//numero aste in corso
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroAsteInCorso();
				assertEquals(result2 , (Integer) 15);
				
				//numero aste in chiusura
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroAsteInChiusura();
				assertEquals(result2 , (Integer) 3);

				//numero inserzioni cercate
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniCercate("smartphone", 3);
				assertEquals(result2 , (Integer) 2);
				
				//numero inserzioni per titolo
				
				result2 = -1;
				result2 = inserzioneDao.getNumeroInserzioniPerTitolo("iphone x nuovo");
				assertEquals(result2 , (Integer) 1);
				

	}
	
	@Test
	public void testGestioneAste() throws ClassNotFoundException, SQLException, IOException {
		
				//aste in chiusura limit
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				List<Inserzione> result = new ArrayList<Inserzione>();
				result.removeAll(result);
				result = inserzioneDao.getLimitInserzioniChiusura(0, 10);
				//confronto se le prime due aste in chiusura corrispondo a quelle con id 34 e 36
				assertEquals(result.get(0).getIdInserzione(), (Integer) 34);
				assertEquals(result.get(1).getIdInserzione(), (Integer) 35);
				
				//aste in corso limit
				
				result.removeAll(result);
				result = inserzioneDao.getLimitAsteInCorso(1, 3);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 35);
				assertEquals(result.get(1).getIdInserzione(), (Integer) 36);
				assertEquals(result.get(2).getIdInserzione(), (Integer) 37);
				
				//limit inserzioni
				
				result.removeAll(result);
				result = inserzioneDao.getLimitInserzioni(2, 4);
				assertEquals(result.get(0).getIdInserzione(), (Integer) 30);
				assertEquals(result.get(1).getIdInserzione(), (Integer) 31);
				assertEquals(result.get(2).getIdInserzione(), (Integer) 32);
				
				//inserzione by id
				
				Inserzione inserzione = new InserzioneImpl();
				inserzione = inserzioneDao.getInserzioneById(29);
				assertEquals(inserzione.getIdInserzione(), (Integer) 29);

				//inserzione senza liste
				
				inserzione = inserzioneDao.getInserzioneByIdSenzaListe(50);
				assertEquals(inserzione.getIdInserzione(), (Integer) 50);
		
	}
	
	@Test
	public void testOrdinamentoAste() throws ClassNotFoundException, SQLException, IOException {
		
				//ordina inserzioni popolari
				
				InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
				List<Inserzione> result = new ArrayList<Inserzione>();
				
				result = inserzioneDao.ordinaInserzioniPopolari();
				assertEquals(result.get(0).getIdInserzione(), (Integer) 37);
				assertEquals(result.get(1).getIdInserzione(), (Integer) 34);
				assertEquals(result.get(2).getIdInserzione(), (Integer) 39);
				assertEquals(result.get(3).getIdInserzione(), (Integer) 40);
				assertEquals(result.get(4).getIdInserzione(), (Integer) 41);

		
	}
	

}
