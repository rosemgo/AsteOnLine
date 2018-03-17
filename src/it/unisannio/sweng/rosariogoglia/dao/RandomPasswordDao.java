package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.RandomPassword;

public interface RandomPasswordDao {
	
	/**
	 * Verifica se l'hashpassword passata come parametro è presente nel database
	 * 
	 * @param hashPassword
	 * 
	 * @return True se l'hashpassword è presente, false se non è presente
	 */
	
	public boolean checkHashPassword(String hashPassword);
	
	/**
	 * Verifica se nel database c'è almeno una riga avente l'hashpassword e l'id utente passati come parametro
	 * 
	 * @param hashPassword
	 * @param idUtente
	 * 
	 * @return true se la riga è presente,false se la riga non è presente
	 */
	
	public boolean checkHashPasswordAndIdUtente(String hashPassword, Integer idUtente);
	
	/**
	 * Inserisce la RandomPassword passata come parametro nel database
	 * 
	 * @param randPass
	 * 
	 * @return L'id della password inserita se l'inserimento va a buon fine,-1 se l'inserimento fallisce
	 */
	
	public Integer insertRandomPassword(RandomPassword randPass);
	
	/**
	 * Elimina dal database la RandomPassword passata come parametro 
	 * 
	 * @param hashPassword
	 * 
	 * @return 1 se l'eliminazione ha successo,-1 se l'eliminazione fallisce
	 */
	
	public Integer deleteRandomPassword(String hashPassword);
		
	
		
}
