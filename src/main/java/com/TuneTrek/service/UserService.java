package com.TuneTrek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.TuneTrek.model.UserInfo;
import com.TuneTrek.repo.UserInfoRepository;

import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	@Autowired
    private UserInfoRepository userRepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
    
    public String RegisterUser(UserInfo userInfo) {
    	userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    	userInfo.setRoles("ADMIN");
    	userRepo.save(userInfo);
        return "user added to system ";
    }
    
    public UserInfo authenticate(String name,String password) {
    	return userRepo.findByNameAndPassword(name, password).orElse(null);
    }

}
