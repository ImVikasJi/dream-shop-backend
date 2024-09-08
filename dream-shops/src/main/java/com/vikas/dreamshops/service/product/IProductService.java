package com.vikas.dreamshops.service.product;

import java.util.List;

import com.vikas.dreamshops.model.Product;

public interface IProductService {

	Product addProduct(Product product);

	Product getProductById(Long id);
	void deleteProductById(Long id);
	void updateProductById(Product product,Long productId);
	
	List<Product> getAllProducts();
	List<Product> getAllProductsByCategory(String category);
	List<Product> getAllProductsByBrand(String brand);
	List<Product> getAllProductsByBrandAndCategory(String brand,String category);
	List<Product> getAllProductsByName(String name);	
	List<Product> getAllProductsByBrandAndName(String brand,String name);	
	
	Long countProductsByBrandAndName(String brand,String name);
}
