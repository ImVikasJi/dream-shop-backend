package com.vikas.dreamshops.service.cart;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.dreamshops.dtos.CartDto;
import com.vikas.dreamshops.model.Cart;

public interface ICartService{

	CartDto getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
}
