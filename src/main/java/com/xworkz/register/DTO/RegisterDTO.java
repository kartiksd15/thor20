package com.xworkz.register.DTO;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class RegisterDTO implements Serializable {
	
	private static final Logger log=Logger.getLogger(RegisterDTO.class);

	private int id;
	private String userId;
	private String email;
	private long phoneNo;
	private String course;
	private String entry;
	private String password;
	

	public RegisterDTO() {
		log.info("created\t:" + this.getClass().getSimpleName());
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RegisterDTO [id=" + id + ", userId=" + userId + ", email=" + email + ", phoneNo=" + phoneNo
				+ ", course=" + course + ", entry=" + entry + ", password=" + password + "]";
	}

}
