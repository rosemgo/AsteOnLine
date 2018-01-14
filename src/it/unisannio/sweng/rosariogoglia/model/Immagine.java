package it.unisannio.sweng.rosariogoglia.model;


public interface Immagine {

	public Integer getIdImmagine();
	
	public void setIdImmagine(int idImmagine);
	
	public String getFoto();
	
	public void setFoto(String foto);
	
	public Integer getIdInserzione();
	
	public void setIdInserzione(int idInserzione);
	
	public Inserzione getInserzione();

	public void setInserzione(Inserzione inserzione);
	
	public String toString();
	
}
