package it.unisannio.sweng.rosariogoglia.bean;


import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;


import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class HomeBean implements Serializable{

	private static final long serialVersionUID = 1L;

	//private InserzioneDao inserzioneDao;
	private List<Inserzione> listaInserzioniPopolari;
	private List<Inserzione> listaTopInserzioniPopolari;
	private List<Inserzione> listaTopInserzioniInScadenza;
	
				
	
	public HomeBean() throws ClassNotFoundException, SQLException, IOException{
		
		
		// caricare inserzioni popolari
		// caricare inserzioni in scadenza
		InserzioneDao inserzioneDao = new InserzioneDaoMysqlJdbc();
		listaInserzioniPopolari = inserzioneDao.ordinaInserzioniPopolari();
		System.out.println("Più popolari");
		listaTopInserzioniPopolari = inserzioneDao.ricercaTopInserzioniPopolari(6);
		System.out.println("Top popolari");
		listaTopInserzioniInScadenza = inserzioneDao.ricercaTopInserzioniChiusura(5);
		System.out.println("Top in chiusura");	
		
		
	}
	
		
	// setters and getters
	
	public List<Inserzione> getListaInserzioniPopolari() {
		return listaInserzioniPopolari;
	}

	public void setListaInserzioniPopolari(List<Inserzione> listaInserzioniPopolari) {
		this.listaInserzioniPopolari = listaInserzioniPopolari;
	}

	public List<Inserzione> getListaTopInserzioniPopolari() {
		return listaTopInserzioniPopolari;
	}

	public void setListaTopInserzioniPopolari(
			List<Inserzione> listaTopInserzioniPopolari) {
		this.listaTopInserzioniPopolari = listaTopInserzioniPopolari;
	}
	
	public List<Inserzione> getListaTopInserzioniInScadenza() {
		return listaTopInserzioniInScadenza;
	}
	
	public void setListaTopInserzioniInScadenza(
			List<Inserzione> listaTopInserzioniInScadenza) {
		this.listaTopInserzioniInScadenza = listaTopInserzioniInScadenza;
	}

	
}
