package com.xworkz.register.DTO;

public class ForgotPasswordDTO {
	
	private String email;
	
	public ForgotPasswordDTO() {
		System.out.println("created forgotpassword:\t"+this.getClass().getSimpleName());
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
