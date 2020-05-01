package com.xworkz.register.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xworkz.register.DTO.ForgotPasswordDTO;
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

		System.out.println("invoking onLogin...!");

		Integer datafrmDB = this.serviceRegister.validateLogin(loginDTO.getEmail(), loginDTO.getPassword());
		System.out.println("data from db:" + datafrmDB);

		if (datafrmDB == 0) {
			System.out.println("invoking login:");
			System.out.println("model attribute:" + loginDTO);

			model.addAttribute("Message", "User loggedIn succefully:\n" + "User.ID :" + loginDTO.getEmail() + "\n"
					+ "Password: " + loginDTO.getPassword());
			return "Home";

		} else if (datafrmDB <= 3) {

			model.addAttribute("Message", "Please check entered Email and Password ");
			return "Login";
		} else {
			model.addAttribute("Message", "You have made already 3 attempts, Please try resetting password");
			return "Login";
		}
	}
	
	@RequestMapping("/Reset.do")
	public String setForgotPassword(ForgotPasswordDTO forgotPasswordDTO,Model model) {
		System.out.println("invoking setForgotPassword:");
	
		boolean isValid = this.serviceRegister.setForgotPswd(forgotPasswordDTO);
		System.out.println("isValid inside the controller"+isValid);
		if (isValid == true) {
			System.out.println("invoking register:");
			System.out.println("model attribute:" + forgotPasswordDTO);

			model.addAttribute("Message", "password reset successful");
			return "GetfwgtPwrd";
		} else {
			model.addAttribute("Message", "password reset not successful: ");
			return "Forgot";
		}
	}
}
