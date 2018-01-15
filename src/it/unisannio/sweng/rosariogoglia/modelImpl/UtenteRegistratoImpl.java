package it.unisannio.sweng.rosariogoglia.modelImpl;

import java.util.Date;

import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public class UtenteRegistratoImpl implements UtenteRegistrato {
	
	private int idUtente;
	private String nick;
	private String nome;
	private String cognome;
	private String password;
	private String eMail;
	private String codiceFiscale;
	private String numContoCorrente;
	private String indirizzo;
	private String cap;
	private String telefono;
	private String tipologiaCliente;//indica se un utente è registrato come venditore o acquirente
	private Date dataRegistrazione;
	private int idComune;
	private Comune comune;
	private boolean flagAbilitato; //indica se l'utente è abilitato o meno


	public UtenteRegistratoImpl() {
	}
	
	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}



	public String getNick() {
		return nick;
	}


	public void setNick(String nick) {
		this.nick = nick;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String geteMail() {
		return eMail;
	}


	public void seteMail(String eMail) {
		this.eMail = eMail;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getNumContoCorrente() {
		return numContoCorrente;
	}


	public void setNumContoCorrente(String numContoCorrente) {
		this.numContoCorrente = numContoCorrente;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getCap() {
		return cap;
	}


	public void setCap(String cap) {
		this.cap = cap;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getTipologiaCliente() {
		return tipologiaCliente;
	}


	public void setTipologiaCliente(String tipologiaCliente) {
		this.tipologiaCliente = tipologiaCliente;
	}


	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}


	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public int getIdComune() {
		return idComune;
	}


	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}
	
	public Comune getComune() {
		return comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}

	public boolean isFlagAbilitato() {
		return flagAbilitato;
	}

	public void setFlagAbilitato(boolean flagAbilitato) {
		this.flagAbilitato = flagAbilitato;
	}

	
	public void clonaUtente(UtenteRegistrato utente){
		
		this.nome = utente.getNome();
		this.cognome = utente.getCognome();
		this.indirizzo = utente.getIndirizzo();
		this.idComune = utente.getIdComune();
		this.cap = utente.getCap();
		this.telefono = utente.getTelefono();
		this.eMail = utente.geteMail();
		this.numContoCorrente = utente.getNumContoCorrente();
		this.codiceFiscale = utente.getCodiceFiscale();
		this.dataRegistrazione = utente.getDataRegistrazione();
		this.flagAbilitato = utente.isFlagAbilitato();
		this.idUtente = utente.getIdUtente();
		this.nick = utente.getNick();
		this.password = utente.getPassword();
		this.tipologiaCliente = utente.getTipologiaCliente();
		
	}
	
	/*
	public UtenteRegistrato clonaUtente(UtenteRegistrato utente){
		UtenteRegistrato utenteProva = new UtenteRegistratoImpl();
		
		utenteProva.setNome(utente.getNome());
		utenteProva.setCognome(utente.getCognome());
		utenteProva.setIndirizzo(utente.getIndirizzo());
		utenteProva.setIdComune(utente.getIdComune());
		utenteProva.setCap(utente.getCap());
		utenteProva.setTelefono(utente.getTelefono());
		utenteProva.seteMail(utente.geteMail());
		utenteProva.setNumContoCorrente(utente.getNumContoCorrente());
		utenteProva.setCodiceFiscale(utente.getCodiceFiscale());
		utenteProva.setDataRegistrazione(utente.getDataRegistrazione());
		utenteProva.setFlagAbilitato(utente.isFlagAbilitato());
		utenteProva.setIdUtente(utente.getIdUtente());
		utenteProva.setNick(utente.getNick());
		utenteProva.setPassword(utente.getPassword());
		utenteProva.setTipologiaCliente(utente.getTipologiaCliente());
		
		return utenteProva;
	}
	*/
		
	public boolean checkPassword(String password){
	    if (password.equals(this.password))
	   		return true;
	    else
	    	return false;
	}  
	
	
	public String toString(){
		return(this.nick + " " + this.nome + " " + this.cognome + " " + this.password + " " + this.eMail + " " + this.codiceFiscale 
				+ " " + this.numContoCorrente + " " + this.tipologiaCliente);
	}

	
	public boolean cambioComune() {
		// TODO Auto-generated method stub
		return false;
	}



}
