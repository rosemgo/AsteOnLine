package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;

public class UtenteRegistratoDaoMysqlJdbcTest {
	
	@Test
	
	public void testInserimentoRimozioneUtente() throws ClassNotFoundException, SQLException, IOException {
		
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		UtenteRegistrato utente = new UtenteRegistratoImpl();
		Integer idUtente = -1;
		
		utente.setNick("Test Nick");
		utente.setNome("Test nome");
		utente.setCognome("Test Cognome");
		utente.setPassword("Testpass");
		utente.seteMail("Testmail");
		utente.setCodiceFiscale("BLFMRC88P29A783Y");
		utente.setNumContoCorrente("2222222222222222");
		utente.setIndirizzo("Test indirizzo");
		utente.setCap("82030");
		utente.setTelefono("TestTel");
		utente.setTipologiaCliente("venditore");
		utente.setDataRegistrazione(new Date());
		utente.setIdComune(5227);
		utente.setFlagAbilitato(true);
		
		//inserimento utente registrto
		idUtente = utenteDao.insertUtenteRegistrato(utente);
		
		//visualizza utente by id
		UtenteRegistrato readingUtente = new UtenteRegistratoImpl();
		readingUtente = utenteDao.getUtenteRegistratoById(idUtente);
		assertEquals(readingUtente.getIdUtente(), utente.getIdUtente());
		
		//aggiorna password
		int result = utenteDao.updatePassword("Test Nick", "TestUpdatePass");
		assertEquals(1, result);
		
		//aggiorna dati utente
		result=-1;
		readingUtente.setCognome("TestCognomeUpdate");
		result=utenteDao.updateUtenteRegistrato(readingUtente);
		assertEquals(1, result);
		
		//aggiorna dati utente
		result=-1;
		result=utenteDao.updateDatiPersonali("Test Nick", "TestNomeUpdate", "TestCognomeUpdate", "GGLRSR88T07A783R", "TestUdpateIndirizzo", 5227, "82030", "3387654321", "2222222222222222", "testupdatemail@gmail.com");
		assertEquals(1, result);
		
		//aggiorna stato utente
		result=-1;
		result=utenteDao.updateStatoUtente(readingUtente.getIdUtente(), false);
		result=utenteDao.updateAbilitazioneUtente("Test Nick", true);
		assertEquals(1, result);
		
		//aggiornamento tipologia utente
		result=-1;
		result=utenteDao.updateTipologiaUtente("Test Nick", "acquirente");
		assertEquals(1, result);
		
		//get utente by id Test
		UtenteRegistrato utenteTest = new UtenteRegistratoImpl();
		UtenteRegistratoDaoMysqlJdbc utenteDaoTest = new UtenteRegistratoDaoMysqlJdbc();
		utenteTest=utenteDaoTest.getUtenteRegistratoByIdTest(readingUtente.getIdUtente());
		
		//cancellazione utente registrato
		Integer deleteRows = utenteDao.removeUtenteRegistratoTest(utente);
		assertEquals(deleteRows, (Integer)1);
		
	}
	
	@Test
	
	public void testGetUtente() throws ClassNotFoundException, SQLException, IOException {
	
			//get le mie inserzioni per titolo
	
			UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
			List<Inserzione> result2 = new ArrayList<Inserzione>();
			result2 = utenteDao.getLimitLeMieInserzioniPerTitolo(2, "asus vivobook pro", 0, 3);
			assertEquals(result2.get(0).getIdInserzione(), (Integer)30);
			assertEquals(result2.get(1).getIdInserzione(), (Integer)29);
			assertEquals(result2.get(2).getIdInserzione(), (Integer)28);
			
			//get utente registrato by mail
			
			UtenteRegistrato result3 = new UtenteRegistratoImpl();
			result3 = utenteDao.getUtenteRegistratoByeMail("rosariogoglia@gmail.com");
			assertEquals(result3.getIdUtente(), 1);
			
			//inserzioni osservate dall'utente 
			
			result2.removeAll(result2);
			result2 = utenteDao.getInserzioniOsservateByIdUtente(6);
			assertEquals(result2.get(0).getIdInserzione(), (Integer) 40);
			assertEquals(result2.get(1).getIdInserzione(), (Integer) 37);
			assertEquals(result2.get(2).getIdInserzione(), (Integer) 32);
			assertEquals(result2.get(3).getIdInserzione(), (Integer) 31);
			assertEquals(result2.get(4).getIdInserzione(), (Integer) 30);
			assertEquals(result2.get(5).getIdInserzione(), (Integer) 29);
			assertEquals(result2.get(6).getIdInserzione(), (Integer) 28);

			//numero inserzioni osservate dall'utente
			
			Integer result4 = -1;
			result4 = utenteDao.getNumeroInserzioniOsservateByIdUtente(6);
			assertEquals(result4, (Integer) 7);

			//inserzioni acquistate dall'utente
			
			result2.removeAll(result2);
			result2 = utenteDao.getInserzioniByIdUtenteAcquirente(7);
			assertEquals(result2.get(0).getIdInserzione(), (Integer)28);
			
			//inserzioni aggiudicate utente
			
			result2.removeAll(result2);
			result2 = utenteDao.getInserzioniAggiudicateByIdUtenteAcquirente(5);
			assertEquals(result2.get(0).getIdInserzione(), (Integer)34);

			//numero inserzioni aggiudicate utente
			
			result4 = -1;
			result4 = utenteDao.getNumeroInserzioniAggiudicateByIdUtenteAcquirente(5);
			assertEquals(result4, (Integer) 1);
			
			//inserzioni dell'utente venditore
			
			result2.removeAll(result2);
			result2 = utenteDao.getInserzioniByIdUtenteVenditore(2);
			assertEquals(result2.get(0).getIdInserzione(), (Integer) 28);
			assertEquals(result2.get(1).getIdInserzione(), (Integer) 29);
			assertEquals(result2.get(2).getIdInserzione(), (Integer) 30);
			
			//numero utenti registrati
			
			result4 = -1;
			result4 = utenteDao.getNumeroUtenti();
			assertEquals(result4, (Integer) 11);
			
			//utente registrato by nick
			
			result3 = utenteDao.getUtenteRegistratoByNick("mikke");
			assertEquals(result3.getIdUtente(), 4);
			
			//verifica se l'inserzione con id 42 a cui l'utente con id 12 ha fatto un offerta, è ancora in corso
			boolean trovato=false;
			result2.removeAll(result2);
			result2 = utenteDao.getMieAsteInCorsoByIdUtente(12);
			System.out.println("dimensione: " + result2.size());
			for(int i=0; i<result2.size(); i++) {
				if(result2.get(i).getIdInserzione() == 42)
					trovato=true;
			}
			assertEquals(true, trovato);
			
			
			//numero inserzioni utente
			
			result4 = -1;
			result4 = utenteDao.getNumeroInserzioniByIdUtenteVenditore(6);
			assertEquals(result4, (Integer) 4);
			
			//numero le mie inserzioni per titolo
			
			result4 = -1;
			result4 = utenteDao.getNumeroLeMieInserzioniPerTitolo(2, "asus vivobook pro");
			assertEquals(result4, (Integer) 3);

			//si verifica che l'utente con id 4 sta partecipando ad una sola asta
			
			result4 = -1;
			result4 = utenteDao.getNumeroMieAsteInCorsoByIdUtente(4);
			assertEquals((Integer) 1, result4);
			
			// get utenti
			
			List<UtenteRegistrato> result5 = new ArrayList<UtenteRegistrato>();
			result5 = utenteDao.getUtenti();
			assertEquals(result5.size() , 11);
			
			//get limit inserzioni utente venditore
			
			result2.removeAll(result2);
			result2 = utenteDao.getLimitInserzioniByIdUtenteVenditore(8, 1, 3);
			assertEquals(result2.get(0).getIdInserzione(), (Integer) 39);
			assertEquals(result2.get(1).getIdInserzione(), (Integer) 38);
			assertEquals(result2.get(2).getIdInserzione(), (Integer) 37);
			
			//get limit inserzioni osservate
			
			result2.removeAll(result2);
			result2 = utenteDao.getLimitInserzioniOsservateByIdUtente(6, 0, 3);
			assertEquals(result2.get(0).getIdInserzione(), (Integer) 40);
			assertEquals(result2.get(1).getIdInserzione(), (Integer) 37);
			assertEquals(result2.get(2).getIdInserzione(), (Integer) 32);
			
			
			//get limit inserzioni aggiudicate
			
			result2.removeAll(result2);
			result2 = utenteDao.getLimitInserzioniAggiudicateByIdUtenteAcquirente(5, 0, 10);
			assertEquals(result2.get(0).getIdInserzione(), (Integer) 34);
			
			//get limit utenti 
			
			result5.removeAll(result5);
			result5 = utenteDao.getLimitUtenti(0, 3);
			assertEquals(result5.get(0).getIdUtente(), 1);
			assertEquals(result5.get(1).getIdUtente(), 2);
			assertEquals(result5.get(2).getIdUtente(), 3);
			
			//get limit le mie aste in corso
			
			result2.removeAll(result2);
			result2 = utenteDao.getLimitMieAsteInCorsoByIdUtente(4, 0, 2);
			assertEquals((Integer) 38, result2.get(0).getIdInserzione());

			
			//restituisce tutti i nickname presenti nel db
			List<String> listaNickname = new ArrayList<String>();
			listaNickname = utenteDao.getNick();
			
			
			
			
			
			
}
	
	@Test
	
	public void testCheckUtente() throws ClassNotFoundException, SQLException, IOException {
	
				//check utente
		
				UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
				UtenteRegistrato result3 = new UtenteRegistratoImpl();
				result3 = utenteDao.checkUtente("steto");
				assertEquals(result3.getIdUtente(), 5);
				
				//controlla pagamenti
				
				boolean result = false;
				result = utenteDao.controllaPagamenti("steto");
				assertEquals(result , true);
				
				//controlla prodotti scaduti
				
				result = false;
				result = utenteDao.controllaProdottiScaduti("gionny");
				assertEquals(result , true);
				
				//controllo mail
				
				result = false;
				result = utenteDao.controlloeMail("marco.belfiore@gmail.com");
				assertEquals(result , true);
				
				//controllo nick
				
				result = false;
				result = utenteDao.controlloNick("immadg");
				assertEquals(result , true);

				// is utente abilitato
				
				result = false;
				result = utenteDao.isUtenteAbilitato("immadg");
				assertEquals(result , true);
				
				//is utente registrato
				
				result = false;
				result = utenteDao.isUtenteRegistrato("BLFMRC88T28A783F","venditore");
				assertEquals(result , true);
				
				//check utente registrato con id 6 osserva l'inserzione 28
				result=false;
				result = utenteDao.checkInserzioneOsservataByIdUtente(6, 28);
				assertEquals(result , true);
	
	
}


}
