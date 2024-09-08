package com.vikas.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.dreamshops.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String category);

}
