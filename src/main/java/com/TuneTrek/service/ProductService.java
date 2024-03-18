package com.TuneTrek.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TuneTrek.model.Product;
import com.TuneTrek.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productRepo;
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public void addProduct(Product product) {
		productRepo.save(product);
	}
	
	public void removeProduct(long id) {
		productRepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id){
		return productRepo.findById(id);
	}
	
	public List<Product> getAllProductsByCategoryID(int id){
		return productRepo.findAllByCategory_Id(id);
	}
	
	
}
