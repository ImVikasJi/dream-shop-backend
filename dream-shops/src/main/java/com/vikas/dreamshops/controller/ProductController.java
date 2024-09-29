package com.vikas.dreamshops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.dreamshops.dtos.ProductRequest;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Product;
import com.vikas.dreamshops.response.ApiResponse;
import com.vikas.dreamshops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	
	@Autowired
	private IProductService iProductService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(){
		
		List<Product> allProducts = this.iProductService.getAllProducts();
		return ResponseEntity.ok(new ApiResponse("success", allProducts));
	} 
	
	@GetMapping("/product/{productId}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
		
		try {
			Product productById = this.iProductService.getProductById(productId);
			return ResponseEntity.ok(new ApiResponse("success", productById));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	} 
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(
			@RequestBody ProductRequest product){
		try {
			Product addedProduct = this.iProductService.addProduct(product);
			return ResponseEntity.ok(new ApiResponse("Product added successfully! ", addedProduct));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/produuct/{productId}/update")
	public ResponseEntity<ApiResponse> updateProduct(
			@RequestBody ProductRequest productRequest,
			@PathVariable Long productId){
		try {
			Product updatedProductById = this.iProductService.updateProductById(productRequest, productId);
			return ResponseEntity.ok(new ApiResponse("Product updated successfully! ", updatedProductById));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/product/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(
			@PathVariable Long productId ){
		try {
			this.iProductService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("Product deleted successfully! ", productId));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
			
		}
	}

	@GetMapping("/by/brand/{brandName}/productName/{productName}")
	public ResponseEntity<ApiResponse> getProductsByBrandName(
			@PathVariable String brandName,
			@PathVariable String productName){
		try {
			List<Product> products= this.iProductService.getProductsByBrandAndName(brandName,
					productName);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found for " + productName, null));
			}
			return ResponseEntity.ok(new ApiResponse("Success! ", products));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by/brand/{brandName}/category/{categoryName}")
	public ResponseEntity<ApiResponse> getProductsByBrandAndCategory(
			@PathVariable String brandName,
			@PathVariable String categoryName){
		try {
			List<Product> products= this.iProductService.getProductsByBrandAndCategory(brandName,
					categoryName);
			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found!!" , null));
			}
			return ResponseEntity.ok(new ApiResponse("Success! ", products));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by/productName/{productName}")
	public ResponseEntity<ApiResponse> getProductsByName(
			@PathVariable String productName){
		
		try {
			List<Product> productsByName = this.iProductService.getProductsByName(productName);
			if(productsByName.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found!!" , null));
			}
			return ResponseEntity.ok(new ApiResponse("Success! ", productsByName));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by/brand/{brand}")
	public ResponseEntity<ApiResponse> getProductsByBrand(
			@PathVariable String brand){
		
		try {
			List<Product> brands = this.iProductService.getProductsByBrand(brand);
			if(brands.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found!!" , null));
			}
			return ResponseEntity.ok(new ApiResponse("Success! ", brands));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by/category/{category}")
	public ResponseEntity<ApiResponse> getProductsByCategory(
			@PathVariable String category){
		
		try {
			List<Product>  categories= this.iProductService.getProductsByCategory(category);
			if(categories.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found!!" , null));
			}
			return ResponseEntity.ok(new ApiResponse("Success! ", categories));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/product/by/count/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(
			@RequestParam String brand,
			@RequestParam String name){
		
		try {
			Long countProductsByBrandAndName = this.iProductService.countProductsByBrandAndName(brand, name);
			
			return ResponseEntity.ok(new ApiResponse("Product count! ", countProductsByBrandAndName));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	
	
	
	
	
	
}
