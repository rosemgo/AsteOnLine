package it.unisannio.sweng.rosariogoglia.modelImpl;

import it.unisannio.sweng.rosariogoglia.model.BannedCookies;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

public class BannedCookiesImpl implements BannedCookies{

	int idBannedCookies;
	int idUtenteBannato;
	UtenteRegistrato utenteBannato;
	String cookie;
	
	
	public BannedCookiesImpl() {
			
	}
		
	public int getIdBannedCookies() {
		return idBannedCookies;
	}
	public void setIdBannedCookies(int idBannedCookies) {
		this.idBannedCookies = idBannedCookies;
	}
	
	public int getIdUtenteBannato() {
		return idUtenteBannato;
	}

	public void setIdUtenteBannato(int idUtenteBannato) {
		this.idUtenteBannato = idUtenteBannato;
	}

	public UtenteRegistrato getUtenteBannato() {
		return utenteBannato;
	}

	public void setUtenteBannato(UtenteRegistrato utenteBannato) {
		this.utenteBannato = utenteBannato;
	}

	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}
