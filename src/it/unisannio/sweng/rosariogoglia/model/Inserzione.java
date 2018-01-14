package it.unisannio.sweng.rosariogoglia.model;



import java.util.Date;
import java.util.List;

public interface Inserzione {
		
	public Integer getIdInserzione();

	public void setIdInserzione(int idInserzione);

	public String getTitolo();

	public void setTitolo(String titolo);
	
	public String getDescrizione();

	public void setDescrizione(String descrizione);

	public double getPrezzoIniziale();

	public void setPrezzoIniziale(double prezzoIniziale);

	public double getPrezzoAggiornato();

	public void setPrezzoAggiornato(double prezzoAggiornato);

	public Date getDataScadenza();

	public void setDataScadenza(Date dataScadenza);
	
	public String getDataScadenzaString();

	public void setDataScadenzaString(String dataScadenzaString);
	
	public String getStato();

	public void setStato(String stato);

	public UtenteRegistrato getAcquirente();

	public void setAcquirente(UtenteRegistrato acquirente);
		
	public Integer getIdAcquirente();

	public void setIdAcquirente(Integer idAcquirente);
	
	public UtenteRegistrato getVenditore();

	public void setVenditore(UtenteRegistrato venditore);	

	public Integer getIdVenditore();

	public void setIdVenditore(Integer idVenditore);

	public Prodotto getProdotto();

	public void setProdotto(Prodotto prodotto);

	public Integer getIdProdotto();

	public void setIdProdotto(Integer idProdotto);

	public List<Immagine> getImmagini();

	public void setImmagini(List<Immagine> immagini);

	public List<Offerta> getOfferte();

	public void setOfferte(List<Offerta> offerte);
	
	public String toString();
	
	public boolean equals(Object idInserzione);
	

}

