package com.TuneTrek.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.TuneTrek.dto.ProductDTO;
import com.TuneTrek.model.Category;
import com.TuneTrek.model.Product;
import com.TuneTrek.service.CategoryService;
import com.TuneTrek.service.ProductService;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
	
	public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/ProductImages";
	
	@Autowired
	CategoryService categoryService;
	
	
	@GetMapping("/admin")
	public String adminHome() {
		System.out.println("login admin");
		return "admin";
	}
	
	@GetMapping("/admin/categories")
	public String categories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCategories(Model model) {
		model.addAttribute("category",new Category());
		return "addCategories";
	}	
	
	@PostMapping("/admin/categories/add")
	public String postAddCategories(@ModelAttribute("category")Category category) {
		categoryService.addCategory(category);
		
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id){
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id,Model model) {
		Optional<Category> category=categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "addCategories";
		}
		else {
			return "404";
		}
	}
	
	
	//Product
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "products";	
	}
	
	@GetMapping("/admin/products/add")
	public String addGetProducts(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "addProducts";
		
	}
	
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,@RequestParam("productImage")MultipartFile file,
			@RequestParam("imgName")String imgName) throws IOException {
		Product product=new Product();
		product.setId(productDTO.getId());
		product.setQty(productDTO.getQty());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		String imageUUID;
		
		if(!file.isEmpty()) {
			imageUUID=file.getOriginalFilename();
			Path filenameAndPath=Paths.get(uploadDir, imageUUID);
			Files.write(filenameAndPath, file.getBytes());
		}else {
			imageUUID=imgName;
		}
		
		product.setImageName(imageUUID);
		
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable int id){
		productService.removeProduct(id);
		return "redirect:/admin/products";
	}
	
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id,Model model){
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO=new ProductDTO();
		
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId((product.getCategory().getId()));
		productDTO.setPrice(product.getPrice());
		product.setQty(productDTO.getQty());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		
		return "addProducts";
	}
	
}
