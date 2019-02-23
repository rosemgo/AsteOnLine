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
	public void test() throws ClassNotFoundException, SQLException, IOException {
		
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
		idInserzione = inserzioneDao.insertInserzioneTest(inserzione);
		
		Inserzione readingInserzione = inserzioneDao.getInserzioneByIdTest(idInserzione);
		
		assertEquals(readingInserzione.getIdInserzione(), inserzione.getIdInserzione());
		assertEquals(readingInserzione.getTitolo(), inserzione.getTitolo());
		
		Integer deleteRows = inserzioneDao.deleteInserzioneTest(idInserzione);
		
		assertEquals(deleteRows, (Integer)1);
	
		
		
	}
	
	

}
