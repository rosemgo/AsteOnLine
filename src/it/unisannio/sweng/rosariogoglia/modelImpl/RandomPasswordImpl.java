package it.unisannio.sweng.rosariogoglia.modelImpl;

import it.unisannio.sweng.rosariogoglia.model.RandomPassword;

public class RandomPasswordImpl implements RandomPassword{

	private int idRandomPassword;
	private String hashPassword;
	private int idUtente;
	
	
	public RandomPasswordImpl() {

	}


	public int getIdRandomPassword() {
		return idRandomPassword;
	}


	public void setIdRandomPassword(int idRandomPassword) {
		this.idRandomPassword = idRandomPassword;
	}


	public String getHashPassword() {
		return hashPassword;
	}


	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}


	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
		
	
	
	
}
