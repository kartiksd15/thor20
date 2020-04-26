package com.xworkz.register.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xworkz.register.DTO.LoginDTO;
import com.xworkz.register.DTO.RegisterDTO;
import com.xworkz.register.Entity.RegisterEntity;
import com.xworkz.register.Service.ServiceRegister;

@Component
@RequestMapping("/")
public class RegisterController {
	@Autowired
	private ServiceRegister serviceRegister;

	public RegisterController() {
		System.out.println("created:\t" + this.getClass().getSimpleName());
	}

	@RequestMapping("/register.do")
	public String register(@ModelAttribute("user") RegisterDTO dto, Model model) {
		System.out.println("invoking register:");
		System.out.println("model attribute:" + dto);
		model.addAttribute("Message", "registerd succefully:");

		boolean isValid = this.serviceRegister.validateAndSave(dto);
		if (isValid == true) {
			System.out.println("invoking register:");
			System.out.println("model attribute:" + dto);

			model.addAttribute("Message", "registerd succefully:");
			return "SuccessRegistration";
		} else {
			model.addAttribute("Message", "not successful: ");
			return "Register";
		}

	}

	@RequestMapping("/login.do")
	public String onLogin(LoginDTO loginDTO, Model model) {

		String page = "";
		System.out.println("invoking login...!");
		String mail = loginDTO.getEmail();
		System.out.println("login mail:" + mail);

		String pwd = loginDTO.getPassword();
		System.out.println("login password:.." + pwd);

		try {
			String datafrmDB = this.serviceRegister.validateLogin(loginDTO);
			System.out.println("data from db:" + datafrmDB);

			if (datafrmDB.equals("loginSuccess")) {
				model.addAttribute("Login", "SignIn Succesfuly");
				return "Home";
			} else if (datafrmDB.equals("loginFailed")) {
				System.out.println("email or password is wrong");
				model.addAttribute("LoginMsg", "email or password is wrong");
				return "Login";
			} else {
				System.out.println("your account has been lock due to wrong password..:");
				model.addAttribute("BlockLogin", "your account has been lock due to wrong password..:");
				return "BlockLogin";
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return "Login";

	}

}
