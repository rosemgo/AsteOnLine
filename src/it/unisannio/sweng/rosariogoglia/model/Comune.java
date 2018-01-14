package it.unisannio.sweng.rosariogoglia.model;

public interface Comune {
	
	public int getIdComune();

	public void setIdComune(int idComune);

	public String getNomeComune();

	public void setNomeComune(String nomeComune);

	public int getIdProvincia();

	public void setIdProvincia(int idProvincia);

	public Provincia getProvincia();

	public void setProvincia(Provincia provincia);
	
	public String toString();
	
}
