package com.vikas.dreamshops.service.cartItem;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.dreamshops.dtos.CartDto;
import com.vikas.dreamshops.dtos.CartItemDto;
import com.vikas.dreamshops.dtos.ProductDto;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Cart;
import com.vikas.dreamshops.model.CartItem;
import com.vikas.dreamshops.model.Product;
import com.vikas.dreamshops.repository.CartItemRepository;
import com.vikas.dreamshops.repository.CartRepository;
import com.vikas.dreamshops.service.cart.CartService;
import com.vikas.dreamshops.service.cart.ICartService;
import com.vikas.dreamshops.service.product.IProductService;

@Service
public class CartItemService implements ICartItemService{
	
	private static Logger logger = LogManager.getLogger(CartItemService.class);
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ICartService iCartService;
	
	@Autowired
	private IProductService iProductService;
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public void addCartItemToCart(Long cartId, Long productId, Long quantity) {
		logger.info("Inside addCartItemToCart ");
		
		// Get the product
		// Check if the product already in the cart
		// If yes, increase the quantity with the requested quantity
		// If no, initiate new cart item entry
		
		CartDto cartDto = this.iCartService.getCart(cartId);
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
		
		ProductDto productDto= this.iProductService.getProductById(productId);
		Product product = this.modelMapper.map(productDto, Product.class);
		CartItem cartItem =  cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.orElse(new CartItem());
		
		if(cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		}else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
	
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
		
		
		
	}

	@Override
	public void removeItemQuantity(Long cartId, Long productId) {
		// TODO Auto-generated method stub
		CartDto cartDto = this.iCartService.getCart(cartId);
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
		
		CartItem cartItemToRemove =  cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		
		cart.removeItem(cartItemToRemove);
		cartRepository.save(cart);
		
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, Long quantity) {
		// TODO Auto-generated method stub
		CartDto cartDto = this.iCartService.getCart(cartId);
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
		
		cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.ifPresent(item -> {
					item.setQuantity(quantity);
					item.setUnitPrice(item.getProduct().getPrice());
					item.setTotalPrice();
				});
		
		BigDecimal totalAmount = cart.getTotalAmount();		
		cart.setTotalAmount(totalAmount);		
		cartRepository.save(cart);
		
		
	}
	
	public CartItemDto getCartItem(Long cartId, Long productId) {
		
		CartDto cartDto = this.iCartService.getCart(cartId);
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
		CartItem cartItem =  cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		
		return this.cartItemToDto(cartItem);
	}
	
	public CartItem dtoToCartItem(CartItemDto cartItemDto) {
		return this.modelMapper.map(cartItemDto, CartItem.class);
	}
	
	public CartItemDto cartItemToDto(CartItem cartItem) {
		return this.modelMapper.map(cartItem, CartItemDto.class);
	}

}
