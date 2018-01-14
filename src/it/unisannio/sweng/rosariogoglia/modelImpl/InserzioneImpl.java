package it.unisannio.sweng.rosariogoglia.modelImpl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Immagine;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;


public class InserzioneImpl implements Inserzione {

	private int idInserzione;
	private String titolo;
	private String descrizione;
	private double prezzoIniziale;
	private double prezzoAggiornato;
	private Date dataScadenza;
	private String stato;
	private Integer idAcquirente;
	private UtenteRegistrato acquirente;
	private Integer idVenditore;
	private UtenteRegistrato venditore;
	private Prodotto prodotto;
	private Integer idProdotto;
	private List<Immagine> immagini;
	private List<Offerta> offerte;
	private String dataScadenzaString;


	public InserzioneImpl(){
		
	}
	
	public Integer getIdInserzione() {
		return idInserzione;
	}

	public void setIdInserzione(int idInserzione) {
		this.idInserzione = idInserzione;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzoIniziale() {
		return prezzoIniziale;
	}

	public void setPrezzoIniziale(double prezzoIniziale) {
		this.prezzoIniziale = prezzoIniziale;
	}

	public double getPrezzoAggiornato() {
		return prezzoAggiornato;
	}

	public void setPrezzoAggiornato(double prezzoAggiornato) {
		this.prezzoAggiornato = prezzoAggiornato;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
//		settiamo la data nel formato "dd/MM/yyyy HH:mm:ss"
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.dataScadenzaString = sdf.format(this.dataScadenza);
	}

	public String getDataScadenzaString() {
		return dataScadenzaString;
	}

	public void setDataScadenzaString(String dataScadenzaString) {
		this.dataScadenzaString = dataScadenzaString;
	}

	public void setDataString(String dataString) {
		this.dataScadenzaString = dataString;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public UtenteRegistrato getAcquirente() {
		return acquirente;
	}

	public void setAcquirente(UtenteRegistrato acquirente) {
		this.acquirente = acquirente;
	}
	
	public Integer getIdAcquirente() {
		return idAcquirente;
	}

	public void setIdAcquirente(Integer idAcquirente) {
		this.idAcquirente = idAcquirente;
	}
	
	public UtenteRegistrato getVenditore() {
		return venditore;
	}

	public void setVenditore(UtenteRegistrato venditore) {
		this.venditore = venditore;
	}
	
	public Integer getIdVenditore() {
		return idVenditore;
	}

	public void setIdVenditore(Integer idVenditore) {
		this.idVenditore = idVenditore;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public Integer getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(Integer idProdotto) {
		this.idProdotto = idProdotto;
	}

	public List<Immagine> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}
	
	public List<Offerta> getOfferte() {
		return offerte;
	}

	public void setOfferte(List<Offerta> offerte) {
		this.offerte = offerte;
	}

	public String toString(){
		return("Numero inserzione: " + this.idInserzione + " Titolo: " + this.titolo + " Descrizione: " + this.descrizione + " Prezzo iniziale: " + this.prezzoIniziale
				+ " Prezzo aggiornato: " + this.prezzoAggiornato + " Data scadenza: " + this.dataScadenzaString);
	}
	
/*	public boolean equals(Object idInserzione){
		return(this.idInserzione == (Integer)idInserzione);
	}
*/	
	
}
