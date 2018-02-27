package it.unisannio.sweng.rosariogoglia.modelImpl;

import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;

public class CategoriaImpl implements Categoria{
	
	public CategoriaImpl() {
	}
	
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Produttore> getListaProduttori() {
		return listaProduttori;
	}

	public void setListaProduttori(List<Produttore> listaProduttori) {
		this.listaProduttori = listaProduttori;
	}

	public String toString(){
		return(this.idCategoria + " " + this.nome);
	}
	
	//qui c'era Object nome come argomento
	public boolean equals(String nome){
		return this.nome.equals((String)nome);
	}
	

	
	private int idCategoria;
	private String nome;
	private List<Produttore> listaProduttori;

}
