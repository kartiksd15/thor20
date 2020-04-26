package com.xworkz.register.DAO;

import com.xworkz.register.DTO.LoginDTO;
import com.xworkz.register.Entity.RegisterEntity;

public interface RegisterDAO {
	public void saveAndRegister(RegisterEntity entity);
	
	public boolean validateEmailExitOrNo(String email);
	
	public boolean validateUserIDExitOrNo(String userId);
	
	public RegisterEntity feachEmail(String mail);
	
	public int updateLoginCount(int loginCount, int id);

}
