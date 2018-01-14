package it.unisannio.sweng.rosariogoglia.model;

public interface BannedCookies {

	public int getIdBannedCookies();
	
	public void setIdBannedCookies(int idBannedCookies);

	public int getIdUtenteBannato();

	public void setIdUtenteBannato(int idUtenteBannato);
	
	public UtenteRegistrato getUtenteBannato();

	public void setUtenteBannato(UtenteRegistrato utenteBannato);
	
	public String getCookie();
	
	public void setCookie(String cookie);
	
}
