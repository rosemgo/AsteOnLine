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
		
		Offerta offerta = new OffertaImpl();
		Integer idOfferta = -1;
		
		offerta.setSomma(1002.00);
		offerta.setData(new Date());
		offerta.setIdInserzione(28);
		offerta.setInserzione(inserzioneDao.getInserzioneByIdTest(28));
		offerta.setIdUtenteRegistrato(5);
		offerta.setUtente(utenteDao.getUtenteRegistratoByIdTest(5));
		
		idOfferta = offertaDao.insertOffertaTest(offerta);
		
		Offerta readingOfferta = offertaDao.getOffertaByIdOffertaTest(idOfferta);
		
		assertEquals(readingOfferta.getIdOfferta(), offerta.getIdOfferta());
		
		Integer deleteRows = offertaDao.deleteOffertaTest(offerta);
		
		assertEquals(deleteRows, (Integer)1);
		
	}

}
