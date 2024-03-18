package com.TuneTrek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.TuneTrek.global.GlobalData;
import com.TuneTrek.model.UserInfo;
import com.TuneTrek.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("registerRequest",new UserInfo());
		return "signup";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		GlobalData.cart.clear();
		model.addAttribute("loginRequest",new UserInfo());
		return "login";
	}
	/*
	@PostMapping("/login")
	public String login(@ModelAttribute("loginRequest") UserInfo user) {
		System.out.println("login request: "+user.getName());
		userService.authenticate(user.getName(),user.getPassword());
		return user==null ? "error" : "redirect:/home";
	}
	*/
	@PostMapping("/signup")
	public String signup(@ModelAttribute UserInfo user) {
		System.out.println("register request: "+user.getName());
		userService.RegisterUser(user);
		return user==null ? "error" : "redirect:/login";
	}
	
	@RequestMapping(value = {"/logout"})
    public String logoutDo(HttpServletRequest request,HttpServletResponse response){
		GlobalData.cart.clear();
		return "redirect:/signup";
    }
	
	
}
