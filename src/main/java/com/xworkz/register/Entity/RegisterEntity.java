package com.xworkz.register.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "registration_table")
public class RegisterEntity {

	@Id
	@GenericGenerator(name = "ref", strategy = "increment")
	@GeneratedValue(generator = "ref")
	@Column(name = "register_Id")
	private int id;
	@Column(name = "User_Id")
	private String userId;
	@Column(name = "email")
	private String email;
	@Column(name = "phoneNum")
	private long phoneNo;
	@Column(name = "course")
	private String course;
	@Column(name = "entry")
	private String entry;
	@Column(name = "password")
	private String password;
	@Column(name="loginCount")
	private int loginCount;

	public RegisterEntity() {
		System.out.println("created:\t" + this.getClass().getSimpleName());
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	
	@Override
	public String toString() {
		return "RegisterEntity [id=" + id + ", userId=" + userId + ", email=" + email + ", phoneNo=" + phoneNo
				+ ", course=" + course + ", entry=" + entry + ", password=" + password + ", loginCount=" + loginCount
				+ "]";
	}



}
