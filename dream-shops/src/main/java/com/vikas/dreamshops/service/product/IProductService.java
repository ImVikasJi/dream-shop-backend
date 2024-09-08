package com.vikas.dreamshops.service.product;

import java.util.List;

import com.vikas.dreamshops.dtos.ProductRequest;
import com.vikas.dreamshops.model.Product;

public interface IProductService {

	Product addProduct(ProductRequest product);

	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProductById(ProductRequest product,Long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByBrandAndCategory(String brand,String category);
	List<Product> getProductsByName(String name);	
	List<Product> getProductsByBrandAndName(String brand,String name);	
	
	Long countProductsByBrandAndName(String brand,String name);
}
