package com.vikas.dreamshops.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vikas.dreamshops.model.Product;

@Service
public class ProductService implements IProductService {

	@Override
	public Product addProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProductById(Product product, Long productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProductsByBrandAndCategory(String brand, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProductsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
