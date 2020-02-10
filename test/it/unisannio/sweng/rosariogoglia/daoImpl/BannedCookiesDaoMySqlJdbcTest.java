package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.model.BannedCookies;
import it.unisannio.sweng.rosariogoglia.modelImpl.BannedCookiesImpl;

public class BannedCookiesDaoMySqlJdbcTest {
	
	@Test
	public void testInserimentoRimozioneBannedCookies(){
		
		Integer result = -1;
		boolean result2 = false;
		BannedCookies bannedCookies = new BannedCookiesImpl();
		BannedCookiesDao bannedCookiesDao = new BannedCookiesDaoMysqlJdbc();
		
		//inserimento banned cookies
		bannedCookies.setIdUtenteBannato(3);
		bannedCookies.setCookie("marco.belfiore@gmail.com");
		result = bannedCookiesDao.insertBannedCookies(bannedCookies);
		assertEquals((Integer) bannedCookies.getIdBannedCookies(), (Integer)result);
		
		//controlla se l'utente è bannato
		result2 = bannedCookiesDao.checkUtenteRegistratoBanned(3);
		assertEquals(true, result2);
		
		//cancella utente bannato
		result = -1;
		result = bannedCookiesDao.deleteBannedCookies(3);
		assertEquals((Integer)1, result);	
	
	
	}
	
	
	

}
