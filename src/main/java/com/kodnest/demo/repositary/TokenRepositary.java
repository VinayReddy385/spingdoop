package com.kodnest.demo.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodnest.demo.entity.Token;

@Repository
public interface TokenRepositary extends JpaRepository<Token, Integer> {
	Token findByOtp(String otp);
}
