package com.xworkz.register.Service;

import com.xworkz.register.DTO.LoginDTO;
import com.xworkz.register.DTO.RegisterDTO;
import com.xworkz.register.Entity.RegisterEntity;

public interface ServiceRegister {
	public boolean validateAndSave(RegisterDTO registerDTO);
	
	public String validateLogin(LoginDTO dto);

}
