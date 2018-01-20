package it.unisannio.sweng.rosariogoglia.dao;

import java.io.IOException;

import it.unisannio.sweng.rosariogoglia.model.BannedCookies;

/**
 * Un utente registrato se bannato non pu� usufruire di tutti i servizi del sito, e non pu� effettuare acquisti.
 * @author Rosario
 *
 */
public interface BannedCookiesDao {
	
	public Integer insertBannedCookies(BannedCookies cookie);

	/**
	 * Verifica se l'utente con l'id passato come paramentro, � bannato o meno.
	 * 
	 * @param idUtente
	 * @return true se l'utente � bannato, false in caso contrario
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkUtenteRegistratoBanned(Integer idUtente) throws ClassNotFoundException, IOException;
	
	/**
	 * Rimuovere un utente bannata dalla lista degli utenti bannati
	 * 
	 * @param idUtente
	 */
	public Integer deleteBannedCookies(Integer idUtente);
		
}
