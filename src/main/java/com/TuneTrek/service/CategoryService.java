package com.TuneTrek.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TuneTrek.model.Category;
import com.TuneTrek.repo.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	
	public List<Category> getAllCategory(){
		return categoryRepo.findAll();
	}
	
	public void addCategory(Category category) {
		categoryRepo.save(category);
	}
	
	public void removeCategoryById(int id) {
		categoryRepo.deleteById(id);
	}
	
	public Optional<Category> getCategoryById(int id){
		return categoryRepo.findById(id);
	}
}
