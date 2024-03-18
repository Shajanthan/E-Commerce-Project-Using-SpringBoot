package com.TuneTrek.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TuneTrek.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
