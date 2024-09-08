package com.vikas.dreamshops.service.category;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Category;
import com.vikas.dreamshops.repository.CategoryRepository;

@Service
public class CategoryService implements ICategoryService{
	
	private static Logger logger = LogManager.getLogger(CategoryService.class);
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside CategoryService --> getCategoryById {} ",id);
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		// TODO Auto-generated method stub
		
		logger.info("Inside CategoryService --> getCategoryByName {} ",name);
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		logger.info("Inside CategoryService --> getAllCategories {} ");
		
		return null;
	}

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category updateCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCategoryById(Long id) {
		// TODO Auto-generated method stub
		
		logger.info("Inside CategoryService --> deleteCategoryById {} ",id);
		
		categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, 
				() -> new ResourceNotFoundException("Category not found"));
		
	}


}
