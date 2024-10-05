package com.vikas.dreamshops.service.product;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.dreamshops.dtos.ImageDto;
import com.vikas.dreamshops.dtos.ProductDto;
import com.vikas.dreamshops.dtos.ProductRequest;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Category;
import com.vikas.dreamshops.model.Image;
import com.vikas.dreamshops.model.Product;
import com.vikas.dreamshops.repository.CategoryRepository;
import com.vikas.dreamshops.repository.ImageRepository;
import com.vikas.dreamshops.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ProductService implements IProductService {
	
	private static Logger logger = LogManager.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ProductDto addProduct(ProductRequest productRequest) {
		// TODO Auto-generated method stub
		
		logger.info("Inside ProductService -> addProduct {} ", productRequest);


		
		Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(productRequest.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		
		productRequest.setCategory(category);
		
		ProductDto convertToDto = this.convertToDto(productRepository.save(createProduct(productRequest, category)));
		 
		return convertToDto;
	}
	
	private Product createProduct(ProductRequest productRequest, Category category) {
		
		logger.info("Inside ProductService -> createProduct {} ", productRequest, category);		
		
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
	public ProductDto getProductById(Long id) {
		// TODO Auto-generated method stub
		
		logger.info("Inside ProductService -> getProductById {} ", id);
		
		Product product = productRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
		
		ProductDto productDto = this.convertToDto(product);
		
		return productDto ; 
	}

	@Override
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> deleteProductById {} ", id);
		productRepository.findById(id)
		.ifPresentOrElse(productRepository::delete,
				() -> new ResourceNotFoundException("Product not found!"));
		
	}

	@Override
	public ProductDto updateProductById(ProductRequest productRequest, Long productId) {
		// TODO Auto-generated method stub
		
		logger.info("Inside ProductService -> deleteProductById {} ", productRequest, productId);
		
		Product updatedProduct = productRepository.findById(productId)
		.map(existingProduct -> updateExistingProduct(existingProduct, productRequest))
		.map(productRepository::save)
		.orElseThrow(() -> new ResourceNotFoundException("Product Not found! "));
		
		ProductDto convertToDto = this.convertToDto(updatedProduct);
		
		return convertToDto;
		
	}
	
	private Product updateExistingProduct(Product existingProduct, ProductRequest productRequest) {
		
		logger.info("Inside ProductService -> deleteProductById {} ", productRequest, existingProduct);
		
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
	public List<ProductDto> getAllProducts() {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getAllProducts {} ");
		
		List<ProductDto> convertedProducts = this.getConvertedProducts(productRepository.findAll());
		return convertedProducts;
	}

	@Override
	public List<ProductDto> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByCategory {} ", category);
		
		List<ProductDto> convertedProducts = this.getConvertedProducts(productRepository.findByCategoryName(category));
		return convertedProducts;
	}

	@Override
	public List<ProductDto> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByBrand {} ", brand);
		List<ProductDto> convertedProducts = this.getConvertedProducts(productRepository.findByBrand(brand));
		return convertedProducts;
	}

	@Override
	public List<ProductDto> getProductsByBrandAndCategory(String brand, String category) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByBrandAndCategory {} ", brand,category);
		List<ProductDto> convertedProducts = this.getConvertedProducts(productRepository.findByCategoryNameAndBrand(category,brand));
		return convertedProducts;
	}

	@Override
	public List<ProductDto> getProductsByName(String name) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByName {} ", name);
		List<ProductDto> convertedProducts = this.getConvertedProducts(productRepository.findByName(name));
		return convertedProducts;
	}

	@Override
	public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> getProductsByBrandAndName {} ",brand, name);
		List<ProductDto> byBrandAndName = this.getConvertedProducts(productRepository.findByBrandAndName(brand,name));
		return byBrandAndName;
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		logger.info("Inside ProductService -> countProductsByBrandAndName {} ",brand, name);
		return productRepository.countByBrandAndName(brand, name);
	}

	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream()
				.map(image -> modelMapper.map(image, ImageDto.class)).toList();
		
		productDto.setImageDtos(imageDtos);
		
		return productDto;
	}
	
	
	public List<ProductDto> getConvertedProducts(List<Product> products){
		return products.stream().map(this::convertToDto).toList();
	}
	

}
