package com.kodnest.demo.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodnest.demo.entity.User;
@Repository
public interface UserRepositary extends JpaRepository<User, Integer> {
	
	
	User findByUsername(String username);

}
