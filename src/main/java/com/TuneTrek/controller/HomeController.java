package com.TuneTrek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.TuneTrek.global.GlobalData;
import com.TuneTrek.service.CategoryService;
import com.TuneTrek.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping({"/","/home","/login?logout"})
	public String home(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("products",productService.getAllProducts());
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "shop";
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model,@PathVariable int id) {
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("products",productService.getAllProductsByCategoryID(id));
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model,@PathVariable int id) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("product",productService.getProductById(id).get());
		return "viewProduct";
	}
	
	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "contact";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "about";
	}

}
