package com.vikas.dreamshops.service.cart;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.vikas.dreamshops.dtos.CartDto;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Cart;
import com.vikas.dreamshops.model.CartItem;
import com.vikas.dreamshops.repository.CartItemRepository;
import com.vikas.dreamshops.repository.CartRepository;

@Service
public class CartService implements ICartService{
	
	private static Logger logger = LogManager.getLogger(CartService.class);
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final AtomicLong cartIdGenerator = new AtomicLong(0);

	@Override
	public CartDto getCart(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside CartService getCart ",id);
		Cart cart = cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
		
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		
		this.cartRepository.save(cart);
		
		CartDto cartByIdDto = this.modelMapper.map(cart, CartDto.class);
		return cartByIdDto;
	}

	@Override
	public void clearCart(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside CartService clearCart ",id);
		CartDto cartDto = this.getCart(id);
		cartItemRepository.deleteAllByCartId(cartDto);
		
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
		
		cart.getItems().clear();
		cartRepository.deleteById(cart);
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside CartService getTotalPrice ",id);
		CartDto cartDto = this.getCart(id);
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
		
		BigDecimal totalPrice = cart.getItems().stream()
		.map(CartItem::getTotalPrice)
		.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		return totalPrice;
	}
	
	public Long initializeNewCart() {
		Cart newCart = new Cart();
		Long newCartId = cartIdGenerator.incrementAndGet();
		newCart.setId(newCartId);
		
		return cartRepository.save(newCart).getId();
	}

}
