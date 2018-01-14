package it.unisannio.sweng.rosariogoglia.modelImpl;


import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;

import java.util.List;


public class ProdottoImpl implements Prodotto{

	private int idprodotto;
	private String nome;
	private List <Keyword> keywords;
	private Integer idProduttore;
	private Produttore produttore;
	private Integer idCategoria;
	private Categoria categoria;
	
	
	public ProdottoImpl() {
	}
	
	public int getIdProdotto() {
		return idprodotto;
	}
	
	public void setIdProdotto(int idprodotto) {
		this.idprodotto = idprodotto;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Keyword> getKeywordsList() {
		return keywords;
	}
	
	public void setKeywordsList(List<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	public Integer getIdProduttore() {
		return idProduttore;
	}
	
	public void setIdProduttore(Integer idProduttore) {
		this.idProduttore = idProduttore;
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public String toString(){
		return(this.idprodotto + " " + this.nome + " " + this.idProduttore + " " + this.idCategoria);
	}
	
	public Produttore getProduttore() {
		return produttore;
	}

	public void setProduttore(Produttore produttore) {
		this.produttore = produttore;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
