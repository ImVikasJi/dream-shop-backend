package com.vikas.dreamshops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.dreamshops.exceptions.AlreadyExistsException;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Category;
import com.vikas.dreamshops.response.ApiResponse;
import com.vikas.dreamshops.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

	@Autowired
	private ICategoryService iCategoryService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories() {
		try {
			List<Category> categories = this.iCategoryService.getAllCategories();
			return new ResponseEntity<>(new ApiResponse("", categories), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error ", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
		try {
			Category category = this.iCategoryService.addCategory(name);
			return ResponseEntity.ok(new ApiResponse("Success", category));
		} catch (AlreadyExistsException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/categoryId/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
		try {
			Category categoryById = this.iCategoryService.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Found ", categoryById));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND));
		}
	}

	@GetMapping("/categoryName/{name}")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
		try {
			Category categoryByName = this.iCategoryService.getCategoryByName(name);
			return ResponseEntity.ok(new ApiResponse("Found ", categoryByName));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND));
		}
	}

	@GetMapping("/categoryDelete/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
		try {
			this.iCategoryService.deleteCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Category deleted successfully ", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND));
		}
	}

	@PostMapping("/categoryUpdate/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id) {
		try {
			Category updatedCategory = this.iCategoryService.updateCategory(category, id);
			return ResponseEntity.ok(new ApiResponse("Success", updatedCategory));
		} catch (AlreadyExistsException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
