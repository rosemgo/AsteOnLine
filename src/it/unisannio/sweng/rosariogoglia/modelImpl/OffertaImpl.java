package it.unisannio.sweng.rosariogoglia.modelImpl;


import java.util.Date;

import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.Offerta;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public class OffertaImpl implements Offerta{

	public OffertaImpl(){
		
	}

	public Integer getIdOfferta() {
		return idOfferta;
	}

	public void setIdOfferta(Integer idOfferta) {
		this.idOfferta = idOfferta;
	}

	public Double getSomma() {
		return somma;
	}

	public void setSomma(Double somma) {
		this.somma = somma;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Inserzione getInserzione() {
		return inserzione;
	}

	public void setInserzione(Inserzione inserzione) {
		this.inserzione = inserzione;
	}

	public Integer getIdInserzione() {
		return idInserzione;
	}

	public void setIdInserzione(Integer idInserzione) {
		this.idInserzione = idInserzione;
	}

	public UtenteRegistrato getUtente() {
		return utente;
	}

	public void setUtente(UtenteRegistrato utente) {
		this.utente = utente;
	}

	public Integer getIdUtenteRegistrato() {
		return idUtenteRegistrato;
	}

	public void setIdUtenteRegistrato(Integer idUtenteRegistrato) {
		this.idUtenteRegistrato = idUtenteRegistrato;
	}

	public String toString(){
		return(this.idOfferta + " " + this.somma + " " + this.data.toString() + " " + this.inserzione.toString() + " " + this.utente.toString());
	}
	
	public boolean equals(Integer idOfferta){
		return(this.idOfferta.equals((Integer)idOfferta));
	}
	
	private Integer idOfferta;
	private Double somma;
	private Date data;
	private Inserzione inserzione;
	private Integer idInserzione;
	private UtenteRegistrato utente;
	private Integer idUtenteRegistrato;
	
	
}
