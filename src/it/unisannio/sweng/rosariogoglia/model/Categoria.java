package it.unisannio.sweng.rosariogoglia.model;

import java.util.List;

public interface Categoria {
	
	public int getIdCategoria();
	
	public void setIdCategoria(int idCategoria);
	
	public String getNome();
	
	public void setNome(String nome);
	
	public List<Produttore> getListaProduttori();

	public void setListaProduttori(List<Produttore> listaProduttori);
	
	public String toString();
	

}
