package it.unisannio.sweng.rosariogoglia.model;


import java.util.Date;

public interface Offerta {
	
	public Integer getIdOfferta();
	
	public void setIdOfferta(Integer idOfferta);
	
	public Double getSomma();

	public void setSomma(Double somma); 

	public Date getData();

	public void setData(Date data);
	
	public Inserzione getInserzione();

	public void setInserzione(Inserzione inserzione);

	public Integer getIdInserzione(); 

	public void setIdInserzione(Integer idInserzione); 

	public UtenteRegistrato getUtente();

	public void setUtente(UtenteRegistrato utente);

	public Integer getIdUtenteRegistrato();

	public void setIdUtenteRegistrato(Integer idUtenteRegistrato);

}
