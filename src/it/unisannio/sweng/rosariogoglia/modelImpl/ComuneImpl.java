package it.unisannio.sweng.rosariogoglia.modelImpl;

import it.unisannio.sweng.rosariogoglia.model.Comune;
import it.unisannio.sweng.rosariogoglia.model.Provincia;

public class ComuneImpl implements Comune{
	
	private int idComune;
	private String nomeComune;
	private int idProvincia;
	private Provincia provincia;
	
	public ComuneImpl() {
		
	}

	public int getIdComune() {
		return idComune;
	}

	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}

	public String getNomeComune() {
		return nomeComune;
	}

	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
	public String toString(){
		return(this.idComune + " " + this.nomeComune + " " + this.idProvincia);
	}

}
