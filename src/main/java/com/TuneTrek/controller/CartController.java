package com.TuneTrek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.TuneTrek.global.GlobalData;
import com.TuneTrek.model.Product;
import com.TuneTrek.service.ProductService;

@Controller
public class CartController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		GlobalData.cart.add(productService.getProductById(id).get());
		return "redirect:/shop";
	}
	
	@GetMapping("/cart")
	public String cart(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total",GlobalData.cart.stream().mapToInt(Product::getPrice).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String cartRemove(@PathVariable int index) {
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}
	@GetMapping("/buy")
	public String buy(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total",GlobalData.cart.stream().mapToInt(Product::getPrice).sum());
		return "buy";
	}
}
