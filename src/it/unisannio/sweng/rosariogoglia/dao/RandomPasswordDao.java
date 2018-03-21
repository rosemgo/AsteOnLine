package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.RandomPassword;

public interface RandomPasswordDao {
	
	/**
	 * Verifica se l'hashpassword passata come parametro � presente nel database
	 * 
	 * @param hashPassword password da verificare
	 * 
	 * @return True se l'hashpassword � presente, false se non � presente
	 */
	
	public boolean checkHashPassword(String hashPassword);
	
	/**
	 * Verifica se nel database c'� almeno una riga avente l'hashpassword e l'id utente passati come parametro
	 * 
	 * @param hashPassword password da verificare
	 * @param idUtente numero identificativo dell'utente
	 * 
	 * @return true se la riga � presente,false se la riga non � presente
	 */
	
	public boolean checkHashPasswordAndIdUtente(String hashPassword, Integer idUtente);
	
	/**
	 * Inserisce la RandomPassword passata come parametro nel database
	 * 
	 * @param randPass password da inserire nel database
	 * 
	 * @return L'id della password inserita se l'inserimento va a buon fine,-1 se l'inserimento fallisce
	 */
	
	public Integer insertRandomPassword(RandomPassword randPass);
	
	/**
	 * Elimina dal database la RandomPassword passata come parametro 
	 * 
	 * @param hashPassword password da eliminare nel database
	 * 
	 * @return 1 se l'eliminazione ha successo,-1 se l'eliminazione fallisce
	 */
	
	public Integer deleteRandomPassword(String hashPassword);
		
	
		
}
