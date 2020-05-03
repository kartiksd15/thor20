package com.xworkz.register.DTO;

import org.apache.log4j.Logger;

public class ForgotPasswordDTO {
	
	private String email;
	
	private static final Logger log=Logger.getLogger(ForgotPasswordDTO.class);
	
	public ForgotPasswordDTO() {
		log.info("created forgotpassword:\t"+this.getClass().getSimpleName());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDTO [email=" + email + "]";
	}
	
	

}
