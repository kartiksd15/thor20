package com.xworkz.register.Service;

import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xworkz.register.DAO.RegisterDAO;
import com.xworkz.register.DTO.ForgotPasswordDTO;

import com.xworkz.register.DTO.RegisterDTO;
import com.xworkz.register.Entity.RegisterEntity;

@Component
public class ServiceRegisterImpl implements ServiceRegister {
	@Autowired
	private RegisterDAO registerDAO;

	private static final Logger log = Logger.getLogger(ServiceRegisterImpl.class);

	public ServiceRegisterImpl() {
		log.info("created:\t" + this.getClass().getSimpleName());
	}

	public boolean validateAndSave(RegisterDTO registerDTO) {
		log.info("invoking validateAndSave: ");
		boolean flag = false;

		String userid = registerDTO.getUserId();
		if (userid != null && !userid.isEmpty() && userid.length() >= 5 && userid.length() <= 10) {
			boolean valid = this.registerDAO.validateUserIDExitOrNo(userid);
			if (valid) {
				flag = false;
				log.info("userid is already exist: " + userid);

				return flag;
			}

		} else {
			log.info("enter userid once again userid" + userid);
			flag = false;
			return flag;
		}

		String em = registerDTO.getEmail();
		if (em != null && !em.isEmpty()) {
			boolean e = this.registerDAO.validateEmailExitOrNo(em);
			if (e) {
				flag = false;
				log.info("Email id already exist : " + em);

				return flag;
			}
		} else {
			log.info("please enter the  different email.. " + em);
			flag = false;
			return flag;
		}

		long ph = registerDTO.getPhoneNo();
		if (ph >= 10) {
			log.info("phone is valid" + ph);
			flag = true;
		} else {
			log.info("phone num is not valid" + ph);
		}

		String course = registerDTO.getCourse();
		if (!course.isEmpty()) {
			log.info("selected course is valid" + course);
			flag = true;
		} else {
			log.info("select any one course:" + course);
			flag = false;
			return flag;
		}

		String userEntry = registerDTO.getEntry();
		if ("yes".equals(userEntry)) {
			log.info("select entry is valid" + userEntry);

			flag = true;
		} else {
			log.info("select agree button,then only your registration will be complited" + userEntry);
			flag = false;
			return flag;

		}

		if (flag == true) {
			log.info("all the feilds are valid save in DB: " + flag);

			String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
			String password = "";
			int length = 8;

			Random random = new Random();
			char[] text = new char[length];
			for (int i = 0; i < length; i++) {
				text[i] = chars.charAt(random.nextInt(chars.length()));
				password += text[i];
			}
			log.info("system generated password is ..." + password);
			RegisterEntity registerEntity = new RegisterEntity();

			log.info("password set:");
			log.info("Password saved to DB" + password);
			log.info("here registerDTO objects are send to rsgisterEntity");
			BeanUtils.copyProperties(registerDTO, registerEntity);
			log.info("passed to entity :" + password);

			registerEntity.setPassword(password);
			this.registerDAO.saveAndRegister(registerEntity);
		}
		return flag;
	}

	@Override
	public Integer validateLogin(String loginEmail, String loginPassword) {

		boolean isEmailPasswordValid = this.registerDAO.loginCheck(loginEmail, loginPassword); // true
		Integer attemptCount = this.registerDAO.checkAttempts(loginEmail); // no of attempts
		log.info("value of isEmailPasswordValid :" + isEmailPasswordValid);
		log.info("value of checkAttempts :" + attemptCount);

		if (attemptCount <= 3) {
			if (isEmailPasswordValid == false) {

				log.info("inside validateLogin in ServiceDAOImpl with email and password..." + loginEmail + "/t"
						+ loginPassword + "/n");
				log.info("Check Email or Password......");
				attemptCount++;
				log.info("number of attempts :" + attemptCount);
				return this.registerDAO.addAttempts(loginEmail, attemptCount);
			}

		}

		if (attemptCount > 3) {
			log.info("you have attempted more than 3 times..");

			return attemptCount;
		}

		return 0;
	}

	@Override
	public boolean setForgotPswd(ForgotPasswordDTO forgotPasswordDTO) {
		log.info("invoking setforgotpaswrd...");
		boolean flag = false;

		String em = forgotPasswordDTO.getEmail();
		if (em != null && !em.isEmpty()) {
		
			boolean e = this.registerDAO.validateEmailExitOrNo(em);
			if (e) {
				flag = false;
				log.info("Email id already exist : " + em);

				String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
				String password = "";
				int length = 8;

				Random random = new Random();
				char[] text = new char[length];
				for (int i = 0; i < length; i++) {
					text[i] = chars.charAt(random.nextInt(chars.length()));
					password += text[i];
				}
				log.info("system generated password is ..." + password);
				RegisterEntity registerEntity = new RegisterEntity();

				log.info("password set:");
				log.info("New Password saved to DB : " + password);
				log.info("here registerDTO objects are send to rsgisterEntity");
				BeanUtils.copyProperties(forgotPasswordDTO, registerEntity);
				log.info("passed to entity :" + password);

				registerEntity.setPassword(password);
				registerEntity.setLoginCount(0);
				this.registerDAO.updatePassword(registerEntity);

				return flag = true;

			}

			return flag = true;

		} else {
			log.info("please enter the exist email.. " + em);
			flag = false;
			return flag;

		}

	}

}
