package com.xworkz.register.DAO;

import com.xworkz.register.Entity.RegisterEntity;

public interface RegisterDAO {
	public void saveAndRegister(RegisterEntity entity);

	public boolean validateEmailExitOrNo(String email);

	public boolean validateUserIDExitOrNo(String userId);

	public boolean loginCheck(String loginEmail, String loginPassword);

	public Integer addAttempts(String loginEmail, int noOfAttempts);

	public Integer checkAttempts(String loginEmail);
	
	//public boolean checkEmail(String email);
	
	public boolean updatePassword(RegisterEntity registerEntity);

}
