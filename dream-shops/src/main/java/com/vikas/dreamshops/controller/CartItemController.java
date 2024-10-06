package com.vikas.dreamshops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.response.ApiResponse;
import com.vikas.dreamshops.service.cartItem.ICartItemService;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

	@Autowired
	private ICartItemService iCartItemService;
	
	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addCartItemToCart(
			@RequestParam Long cartId,
			@RequestParam Long productId,
			@RequestParam Long quantity){
		
		try {
			this.iCartItemService.addCartItemToCart(cartId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Item added successfully!! ", null));
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@DeleteMapping("/cart/{cartId}/item/{itemId}/")
	public ResponseEntity<ApiResponse> removeItemQuantity(
			@PathVariable Long cartId,
			@PathVariable Long itemId){
		
		try {
			this.iCartItemService.removeItemQuantity(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Item removed successfully!! ", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	
	@PutMapping("cart/{cartId}/item/{itemId}/update")
	public ResponseEntity<ApiResponse> updateItemQuantity(
			@PathVariable Long cartId,
			@PathVariable Long itemId, 
			@RequestParam Long quantity){
		
		try {
			this.iCartItemService.updateItemQuantity(cartId, itemId, quantity);
			return ResponseEntity.ok(new ApiResponse("Item updated successfully!! ", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
	}
	}
}
