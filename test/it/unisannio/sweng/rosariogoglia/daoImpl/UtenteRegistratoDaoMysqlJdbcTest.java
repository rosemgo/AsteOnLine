package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;

public class UtenteRegistratoDaoMysqlJdbcTest {
	
	@Test
	
	public void test() throws ClassNotFoundException, SQLException, IOException {
		
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		UtenteRegistrato utente = new UtenteRegistratoImpl();
		Integer idUtente = -1;
		
		utente.setNick("TestNick");
		utente.setNome("Test nome");
		utente.setCognome("Test Cognome");
		utente.setPassword("Testpass");
		utente.seteMail("Testmail");
		utente.setCodiceFiscale("BLFMRC88P27A783Z");
		utente.setNumContoCorrente("'5234171055368084'");
		utente.setIndirizzo("Test indirizzo");
		utente.setCap("82030");
		utente.setTelefono("TestTel");
		utente.setTipologiaCliente("venditore");
		utente.setDataRegistrazione(new Date());
		utente.setIdComune(5227);
		utente.setFlagAbilitato(true);
		
		idUtente = utenteDao.insertUtenteRegistratoTest(utente);
		
		UtenteRegistrato readingUtente = new UtenteRegistratoImpl();
		readingUtente = utenteDao.getUtenteRegistratoByIdTest(idUtente);
		
		assertEquals(readingUtente.getIdUtente(), utente.getIdUtente());
		
		Integer deleteRows = utenteDao.deleteUtenteRegistratoTest(utente);
		assertEquals(deleteRows, (Integer)1);
		
	}

}
