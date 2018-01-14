package it.unisannio.sweng.rosariogoglia.model;

import java.util.Date;

public interface UtenteRegistrato {
	
	public int getIdUtente();

	public void setIdUtente(int idUtente);
	
	public String getNick();
	
	public void setNick(String nick);
	
	public String getNome();
	
	public void setNome(String nome);
	
	public String getCognome();
	
	public void setCognome(String cognome);
	
	public String getPassword();
	
	public void setPassword(String password);
	
	public String geteMail();
	
	public void seteMail(String eMail);
	
	public String getCodiceFiscale();
	
	public void setCodiceFiscale(String codiceFiscale);
	
	public String getNumContoCorrente();
	
	public void setNumContoCorrente(String numContoCorrente);
	
	public String getTipologiaCliente();
	
	public void setTipologiaCliente(String tipologiaCliente);
	
	public Date getDataRegistrazione();
	
	public void setDataRegistrazione(Date dataRegistrazione);
	
	public String getIndirizzo();
	
	public void setIndirizzo(String indirizzo);
	
	public String getCap();

	public void setCap(String cap);

	public String getTelefono();

	public void setTelefono(String telefono);

	public int getIdComune();

	public void setIdComune(int idComune);
	
	public Comune getComune();
	
	public void setComune(Comune comune);
	
	public boolean isFlagAbilitato();
	
	public void setFlagAbilitato(boolean flagAbilitato);
	
	public boolean checkPassword(String password);
	
	public boolean equals(Object UtenteRegistrato);
	
	public void clonaUtente(UtenteRegistrato utente);
	
	public String toString();

}
