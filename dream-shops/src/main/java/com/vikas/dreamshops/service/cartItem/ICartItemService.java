package com.vikas.dreamshops.service.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.dreamshops.model.CartItem;

public interface ICartItemService extends JpaRepository<CartItem, Long>{

}
