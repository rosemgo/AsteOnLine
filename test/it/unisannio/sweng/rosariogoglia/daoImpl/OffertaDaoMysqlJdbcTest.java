package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.OffertaDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.modelImpl.OffertaImpl;

public class OffertaDaoMysqlJdbcTest {
	
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException{
		
		OffertaDao offertaDao = new OffertaDaoMysqlJdbc(); 
		InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
		UtenteRegistratoDao utenteDao = new UtenteRegistratoDaoMysqlJdbc();
		
		Offerta writingofferta = new OffertaImpl();
		Integer idOfferta = -1;
		
		writingofferta.setSomma(1002.00);
		writingofferta.setData(new Date());
		writingofferta.setIdInserzione(28);
		writingofferta.setInserzione(inserzioneDao.getInserzioneByIdTest(28));
		writingofferta.setIdUtenteRegistrato(5);
		writingofferta.setUtente(utenteDao.getUtenteRegistratoByIdTest(5));
			
		
		idOfferta = offertaDao.insertOffertaTest(writingofferta);
		
		System.out.println("writing offerta: " + writingofferta.getIdOfferta());
	
		Offerta readingOfferta = offertaDao.getOffertaByIdOffertaTest(idOfferta);
		
		System.out.println("reading offerta: " + readingOfferta.getIdOfferta() );
		
		assertEquals(readingOfferta.getIdOfferta(), writingofferta.getIdOfferta());
		
		Integer deleteRows = offertaDao.deleteOffertaTest(writingofferta);
		
		assertEquals(deleteRows, (Integer)1);
		
	}

}
