package it.unisannio.sweng.rosariogoglia.model;

import java.util.List;

public interface Produttore {
	
	public int getIdProduttore();
	
	public void setIdProduttore(int idProduttore);
	
	public String getNome();
	
	public void setNome(String nome);
	
	public String getWebsite();
	
	public void setWebsite(String website);
	
	public List<Prodotto> getProdotti();

	public void setProdotti(List<Prodotto> prodotti);
	
	public String toString();
}
