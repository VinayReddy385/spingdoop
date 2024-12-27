package com.kodnest.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.demo.entity.User;
import com.kodnest.demo.service.ControllerService;

@Controller
@RequestMapping("/api")
public class MainController {
	@Autowired
	ControllerService controllerService;
	@GetMapping("/")
	public String home() {
		return "Login";
	}
	@PostMapping("/login")
	public String login(@RequestParam String username,@RequestParam String password) {
		User user = controllerService.getByUsername(username);
		if(user!=null && password.equals(user.getPassword())) {
			controllerService.sendOtp(user);
			return "verify-otp";
		}
		return "verify-otp";
	}
	@PostMapping("/verifyotp")
	public String verifyOtp(@RequestParam String otp) {
		if(controllerService.verifyOtp(otp)) {
			if(controllerService.getRollByotp(otp).equals("user")) {
				return "userdashboard";
			}
			else {
				return "customerdashboard";
			}	
		}
		return "login";
	}
}
