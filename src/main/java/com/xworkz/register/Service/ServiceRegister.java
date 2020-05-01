package com.xworkz.register.Service;

import com.xworkz.register.DTO.ForgotPasswordDTO;
import com.xworkz.register.DTO.RegisterDTO;


public interface ServiceRegister {
	public boolean validateAndSave(RegisterDTO registerDTO);
	
	public Integer validateLogin(String loginEmail,String passwordLogin);
	
	public boolean setForgotPswd(ForgotPasswordDTO forgotPasswordDTO);

}
