package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.RandomPasswordDao;
import it.unisannio.sweng.rosariogoglia.model.RandomPassword;
import it.unisannio.sweng.rosariogoglia.modelImpl.RandomPasswordImpl;

public class RandomPasswordDaoMysqlJdbcTest {
	
	@Test
	public void testInserimentoRimozione() {
		
		Integer result = -1;
		boolean result2 = false;
		RandomPassword randomPass = new RandomPasswordImpl();
		RandomPasswordDao randomPassDao = new RandomPasswordDaoMysqlJdbc();
		
		//inserimento hashpassword temporanea all'utente 3
		randomPass.setIdUtente(3);
		randomPass.setHashPassword("34446ggwbjwebjk343434343sedd");
		result = randomPassDao.insertRandomPassword(randomPass);
		assertEquals((Integer) 1, result);
		
		//check per verificare se la pass è stata inserita
		result2 = randomPassDao.checkHashPassword("34446ggwbjwebjk343434343sedd");
		assertEquals(true, result2);
		
		//check per verificare se la pass è stata associata all'utente
		result2 = false;
		result2 = randomPassDao.checkHashPasswordAndIdUtente("34446ggwbjwebjk343434343sedd", 3);
		assertEquals(true, result2);
		
		//inserimento hashpassword temporanea dell'utente 3
		result=-1;
		result = randomPassDao.deleteRandomPassword("34446ggwbjwebjk343434343sedd");
		assertEquals((Integer) 1, result);

		
	}
	
	
	
	

}
