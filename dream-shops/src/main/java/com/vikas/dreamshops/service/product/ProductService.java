package com.vikas.dreamshops.service.product;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.dreamshops.dtos.ProductRequest;
import com.vikas.dreamshops.exceptions.ProductNotFoundException;
import com.vikas.dreamshops.model.Category;
import com.vikas.dreamshops.model.Product;
import com.vikas.dreamshops.repository.CategoryRepository;
import com.vikas.dreamshops.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ProductService implements IProductService {
	
	private static Logger logger = LogManager.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Product addProduct(ProductRequest productRequest) {
		// TODO Auto-generated method stub
		
		Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(productRequest.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		
		productRequest.setCategory(category);
		 
		return productRepository.save(createProduct(productRequest, category));
	}
	
	private Product createProduct(ProductRequest productRequest, Category category) {
		return new Product(
				productRequest.getName(),
				productRequest.getBrand(),
				productRequest.getDescription(),
				productRequest.getPrice(),
				productRequest.getInventory(),
				category
				);
	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		
		logger.info("Inside ProductService -> getProductById {} ", id);
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found!")); 
	}

	@Override
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> deleteProductById {} ", id);
		productRepository.findById(id)
		.ifPresentOrElse(productRepository::delete,
				() -> new ProductNotFoundException("Product not found!"));
		
	}

	@Override
	public Product updateProductById(ProductRequest productRequest, Long productId) {
		// TODO Auto-generated method stub
		return productRepository.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, productRequest))
				.map(productRepository::save)
				.orElseThrow(() -> new ProductNotFoundException("Product Not found! "));
		
	}
	
	private Product updateExistingProduct(Product existingProduct, ProductRequest productRequest) {
		existingProduct.setBrand(productRequest.getBrand());
		existingProduct.setCategory(productRequest.getCategory());
		existingProduct.setName(productRequest.getName());
		existingProduct.setDescription(productRequest.getDescription());
		existingProduct.setPrice(productRequest.getPrice());
		existingProduct.setInventory(productRequest.getInventory());
		
		Category category = categoryRepository.findByName(productRequest.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getAllProducts {} ");
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByCategory {} ", category);
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByBrand {} ", brand);
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByBrandAndCategory(String brand, String category) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByBrandAndCategory {} ", brand,category);
		return productRepository.findByBrandNameAndCategory(brand, category);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByName {} ", name);
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByBrandAndName {} ",brand, name);
		return productRepository.findByBrandAndName(brand,name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> countProductsByBrandAndName {} ",brand, name);
		return productRepository.countByBrandAndName(brand, name);
	}

	

}
