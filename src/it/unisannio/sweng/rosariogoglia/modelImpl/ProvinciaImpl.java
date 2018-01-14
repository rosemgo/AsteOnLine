package it.unisannio.sweng.rosariogoglia.modelImpl;

import java.util.List;

import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.Provincia;

public class ProvinciaImpl implements Provincia{
	
	private int idProvincia;
	private String nomeProvincia;
	private List<Comune> listaComuni;
	


	public ProvinciaImpl() {
	
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNomeProvincia() {
		return nomeProvincia;
	}

	public void setNomeProvincia(String nomeProvincia) {
		this.nomeProvincia = nomeProvincia;
	}
	
	public List<Comune> getListaComuni() {
		return listaComuni;
	}

	public void setListaComuni(List<Comune> listaComuni) {
		this.listaComuni = listaComuni;
	}
	
	public String toString(){
		return(this.idProvincia + " " + this.nomeProvincia);
	}
	

}
