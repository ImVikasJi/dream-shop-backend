package com.vikas.dreamshops.service.cartItem;

import com.vikas.dreamshops.dtos.CartItemDto;

public interface ICartItemService{

	void addCartItemToCart(Long cartId, Long productId,Long quantity);
	void removeItemQuantity(Long cartId, Long productId);
	void updateItemQuantity(Long cartId, Long productId, Long quantity);
	
}
