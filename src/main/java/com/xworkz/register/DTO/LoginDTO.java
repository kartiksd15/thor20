package com.xworkz.register.DTO;

import java.io.Serializable;

import org.apache.log4j.Logger;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class LoginDTO implements Serializable{

	private String email;
	private String password;
	
	private static final Logger log=Logger.getLogger(LoginDTO.class);

	public LoginDTO() {
		log.info("created:\t" + this.getClass().getSimpleName());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", password=" + password + "]";
	}
	
	
}
