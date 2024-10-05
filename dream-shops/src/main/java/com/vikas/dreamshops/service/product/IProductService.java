package com.vikas.dreamshops.service.product;

import java.util.List;

import com.vikas.dreamshops.dtos.ProductDto;
import com.vikas.dreamshops.dtos.ProductRequest;

public interface IProductService {

	ProductDto addProduct(ProductRequest product);

	ProductDto getProductById(Long id);

	void deleteProductById(Long id);

	ProductDto updateProductById(ProductRequest product, Long productId);

	List<ProductDto> getAllProducts();

	List<ProductDto> getProductsByCategory(String category);

	List<ProductDto> getProductsByBrand(String brand);

	List<ProductDto> getProductsByBrandAndCategory(String brand, String category);

	List<ProductDto> getProductsByName(String name);

	List<ProductDto> getProductsByBrandAndName(String brand, String name);

	Long countProductsByBrandAndName(String brand, String name);
}
