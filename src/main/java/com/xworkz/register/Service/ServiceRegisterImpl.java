package com.xworkz.register.Service;

import java.util.Objects;
import java.util.Random;

import org.hibernate.HibernateException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xworkz.register.DAO.RegisterDAO;
import com.xworkz.register.DTO.ForgotPasswordDTO;
import com.xworkz.register.DTO.LoginDTO;
import com.xworkz.register.DTO.RegisterDTO;
import com.xworkz.register.Entity.RegisterEntity;

@Component
public class ServiceRegisterImpl implements ServiceRegister {
	@Autowired
	private RegisterDAO registerDAO;

	public ServiceRegisterImpl() {
		System.out.println("created:\t" + this.getClass().getSimpleName());
	}

	public boolean validateAndSave(RegisterDTO registerDTO) {
		System.out.println("invoking validateAndSave: ");
		boolean flag = false;

		String userid = registerDTO.getUserId();
		if (userid != null && !userid.isEmpty() && userid.length() >= 5 && userid.length() <= 10) {
			boolean valid = this.registerDAO.validateUserIDExitOrNo(userid);
			if (valid) {
				flag = false;
				System.out.println("userid is already exist: " + userid);

				return flag;
			}

		} else {
			System.out.println("enter userid once again userid" + userid);
			flag = false;
			return flag;
		}

		String em = registerDTO.getEmail();
		if (em != null && !em.isEmpty()) {
			boolean e = this.registerDAO.validateEmailExitOrNo(em);
			if (e) {
				flag = false;
				System.out.println("Email id already exist : " + em);

				return flag;
			}
		} else {
			System.out.println("please enter the  different email.. " + em);
			flag = false;
			return flag;
		}

		long ph = registerDTO.getPhoneNo();
		if (ph >= 10) {
			System.out.println("phone is valid" + ph);
			flag = true;
		} else {
			System.out.println("phone num is not valid" + ph);
		}

		String course = registerDTO.getCourse();
		if (!course.isEmpty()) {
			System.out.println("selected course is valid" + course);
			flag = true;
		} else {
			System.out.println("select any one course:" + course);
			flag = false;
			return flag;
		}

		String userEntry = registerDTO.getEntry();
		if ("yes".equals(userEntry)) {
			System.out.println("select entry is valid" + userEntry);

			flag = true;
		} else {
			System.out.println("select agree button,then only your registration will be complited" + userEntry);
			flag = false;
			return flag;

		}

		if (flag == true) {
			System.out.println("all the feilds are valid save in DB: " + flag);

			String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
			String password = "";
			int length = 8;

			Random random = new Random();
			char[] text = new char[length];
			for (int i = 0; i < length; i++) {
				text[i] = chars.charAt(random.nextInt(chars.length()));
				password += text[i];
			}
			System.out.println("system generated password is ..." + password);
			RegisterEntity registerEntity = new RegisterEntity();

			System.out.println("password set:");
			System.out.println("Password saved to DB" + password);
			System.out.println("here registerDTO objects are send to rsgisterEntity");
			BeanUtils.copyProperties(registerDTO, registerEntity);
			System.out.println("passed to entity :" + password);

			registerEntity.setPassword(password);
			this.registerDAO.saveAndRegister(registerEntity);
		}
		return flag;
	}

	@Override
	public Integer validateLogin(String loginEmail, String loginPassword) {

		boolean isEmailPasswordValid = this.registerDAO.loginCheck(loginEmail, loginPassword); // true
		Integer attemptCount = this.registerDAO.checkAttempts(loginEmail); // no of attempts
		System.out.println("value of isEmailPasswordValid :" + isEmailPasswordValid);
		System.out.println("value of checkAttempts :" + attemptCount);

		if (attemptCount <= 3) {
			if (isEmailPasswordValid == false) {

				System.out.println("inside validateLogin in ServiceDAOImpl with email and password..." + loginEmail
						+ "/t" + loginPassword + "/n");
				System.out.println("Check Email or Password......");
				attemptCount++;
				System.out.println("number of attempts :" + attemptCount);
				return this.registerDAO.addAttempts(loginEmail, attemptCount);
			}

		}

		if (attemptCount > 3) {
			System.out.println("you have attempted more than 3 times..");

			return attemptCount;
		}

		return 0;
	}

	@Override
	public boolean setForgotPswd(ForgotPasswordDTO forgotPasswordDTO) {
		System.out.println("invoking setforgotpaswrd...");
		boolean flag = false;

		String em = forgotPasswordDTO.getEmail();
		if (em != null && !em.isEmpty()) {
			// boolean e = this.registerDAO.checkEmail(em);
			boolean e = this.registerDAO.validateEmailExitOrNo(em);
			if (e) {
				flag = false;
				System.out.println("Email id already exist : " + em);

				String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
				String password = "";
				int length = 8;

				Random random = new Random();
				char[] text = new char[length];
				for (int i = 0; i < length; i++) {
					text[i] = chars.charAt(random.nextInt(chars.length()));
					password += text[i];
				}
				System.out.println("system generated password is ..." + password);
				RegisterEntity registerEntity = new RegisterEntity();

				System.out.println("password set:");
				System.out.println("New Password saved to DB" + password);
				System.out.println("here registerDTO objects are send to rsgisterEntity");
				BeanUtils.copyProperties(forgotPasswordDTO, registerEntity);
				System.out.println("passed to entity :" + password);

				registerEntity.setPassword(password);
				registerEntity.setLoginCount(0);
				this.registerDAO.updatePassword(registerEntity);

				return flag = true;

			}

			return flag = true;

		} else {
			System.out.println("please enter the exist email.. " + em);
			flag = false;
			return flag;

		}

	}

}
