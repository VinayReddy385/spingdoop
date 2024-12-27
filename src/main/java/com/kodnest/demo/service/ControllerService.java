package com.kodnest.demo.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kodnest.demo.entity.Token;
import com.kodnest.demo.entity.User;
import com.kodnest.demo.repositary.TokenRepositary;
import com.kodnest.demo.repositary.UserRepositary;

@Service
public class ControllerService {
	
	@Autowired
	UserRepositary userRepositary;
	
	
	@Autowired
	TokenRepositary tokenRepositary;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public User getByUsername(String username) {
		System.out.println("getByusername" + username);
		return userRepositary.findByUsername(username);
	}
	
	
	public void sendOtp(User user) {
		String otp = String.format("%06d",new Random().nextInt(999999));
		
		Token token = new Token();
		
		token.setOtp(otp);
		token.setUser(user);
		token.setCreated_at(LocalDateTime.now());
		tokenRepositary.save(token);
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(user.getEmail());
		message.setSubject("Your otp sent");
		message.setText("Your OTP code is: " + otp);
		javaMailSender.send(message);
		}
	
		public boolean verifyOtp(String otp) {	
		
			Token token  = tokenRepositary.findByOtp(otp);	
			if(token ==null) {
				return false;
			}
			if(ChronoUnit.MINUTES.between(token.getCreated_at(),LocalDateTime.now())>1) {
				tokenRepositary.delete(token);
				return false;
			}
			return  true;
		}
		
		public String  getRollByotp(String otp) {
			Token token = tokenRepositary.findByOtp(otp);
			
			User user =token.getUser();
			String roll = user.getRoll();
			
		
			return roll;
		}
		
		
	

}
