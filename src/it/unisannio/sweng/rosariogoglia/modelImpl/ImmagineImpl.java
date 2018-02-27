package it.unisannio.sweng.rosariogoglia.modelImpl;

import it.unisannio.sweng.rosariogoglia.model.Immagine;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;


public class ImmagineImpl implements Immagine{
	
	public ImmagineImpl() {
		
	}
	
	public Integer getIdImmagine() {
		return idImmagine;
	}
	public void setIdImmagine(int idImmagine) {
		this.idImmagine = idImmagine;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Integer getIdInserzione() {
		return idInserzione;
	}
	public void setIdInserzione(int idInserzione) {
		this.idInserzione = idInserzione;
	}
	
	public boolean equals (String foto){
		return this.foto.equals((String)foto);
	}
	
	public Inserzione getInserzione() {
		return inserzione;
	}

	public void setInserzione(Inserzione inserzione) {
		this.inserzione = inserzione;
	}
	
	public String toString(){
		return(this.idImmagine + " " + this.foto);
	}
	
	private int idImmagine;
	private String foto; //link alla foto
	private int idInserzione;
	private Inserzione inserzione;
	

}
