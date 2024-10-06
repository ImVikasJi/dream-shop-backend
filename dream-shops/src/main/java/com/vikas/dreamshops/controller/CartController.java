package com.vikas.dreamshops.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.dreamshops.dtos.CartDto;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.response.ApiResponse;
import com.vikas.dreamshops.service.cart.ICartService;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
	
	@Autowired
	private ICartService iCartService;
	
	@GetMapping("/{cartId}/my-cart")
	public ResponseEntity<ApiResponse> getCart(
			@PathVariable Long cartId){		
		try {
			CartDto cart = this.iCartService.getCart(cartId);
			return ResponseEntity.ok(new ApiResponse("Success!! ", cart));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@DeleteMapping("/{cartId}/clear")
	public ResponseEntity<ApiResponse> clearCarts(
			@PathVariable Long cartId){
		try {
			this.iCartService.clearCart(cartId);
			return ResponseEntity.ok(new ApiResponse("cart cleared!! ", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@GetMapping("/{cartId}/cart/total-price")
	public ResponseEntity<ApiResponse> getTotalAmount(
			@PathVariable Long cartId){
		
		try {
			BigDecimal totalPrice = this.iCartService.getTotalPrice(cartId);
			return ResponseEntity.ok(new ApiResponse("Total price!! ", totalPrice));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}

}
