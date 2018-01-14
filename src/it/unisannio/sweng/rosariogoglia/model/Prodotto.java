package it.unisannio.sweng.rosariogoglia.model;


import java.util.List;

public interface Prodotto {
	
	public int getIdProdotto();
	
	public void setIdProdotto(int idProdotto);
	
	public String getNome();
	
	public void setNome(String nome); 
	
	public Integer getIdProduttore();
	
	public void setIdProduttore(Integer idProduttore); 
	
	public Integer getIdCategoria();
	
	public void setIdCategoria(Integer idCategoria);
	
	public Produttore getProduttore();
	
	public void setProduttore(Produttore produttore);
	
	public Categoria getCategoria();

	public void setCategoria(Categoria categoria);
	
	public List<Keyword> getKeywordsList();
	
	public void setKeywordsList(List<Keyword> keywordsList);
	
	public String toString();
}
