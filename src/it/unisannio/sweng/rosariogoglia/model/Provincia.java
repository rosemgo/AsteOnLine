package it.unisannio.sweng.rosariogoglia.model;

import java.util.List;

public interface Provincia {
	
	public int getIdProvincia();

	public void setIdProvincia(int idProvincia);

	public String getNomeProvincia();

	public void setNomeProvincia(String nomeProvincia);
	
	public List<Comune> getListaComuni();

	public void setListaComuni(List<Comune> listaComuni);
	
	public String toString();
	
}
