package com.xworkz.register.Service;

import java.util.Objects;
import java.util.Random;

import org.hibernate.HibernateException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xworkz.register.DAO.RegisterDAO;
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
	public String validateLogin(LoginDTO loginDTO) {
		System.out.println("invoking validatelogin...:");
		boolean flag = false;
		try {
			RegisterEntity registerEntity = this.registerDAO.feachEmail(loginDTO.getEmail());
			System.out.println("invoke registerEntity:" + registerEntity);

			if (Objects.nonNull(registerEntity)) {
				return "loginFailed";
			}

			String pwdfmDB = registerEntity.getPassword();
			System.out.println("password from db" + pwdfmDB);
			int idfmDB = registerEntity.getId();
			System.out.println("id frm db:" + idfmDB);

			int countPassWordAttempt = registerEntity.getLoginCount();
			System.out.println(countPassWordAttempt);
			if (countPassWordAttempt >= 0 && countPassWordAttempt < 3) {
				if (loginDTO.getPassword().equals(pwdfmDB)) {
					System.out.println("password is match..");
					flag = true;
				} else {
					countPassWordAttempt++;
					System.out.println("password is faild");
					this.registerDAO.updateLoginCount(countPassWordAttempt, idfmDB);
				}

			} else {
				if (countPassWordAttempt == 3) {
					return "blockLogin";
				}
			}
			if (flag == true) {
				return "loginSuccess";
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return "loginFailed";
	}

}
