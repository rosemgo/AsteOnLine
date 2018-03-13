package it.unisannio.sweng.rosariogoglia.dao;

import it.unisannio.sweng.rosariogoglia.model.RandomPassword;

public interface RandomPasswordDao {
	
	public boolean checkHashPassword(String hashPassword);
	
	public boolean checkHashPasswordAndIdUtente(String hashPassword, Integer idUtente);
	
	public Integer insertRandomPassword(RandomPassword randPass);
	
	public Integer deleteRandomPassword(String hashPassword);
		
	
		
}
