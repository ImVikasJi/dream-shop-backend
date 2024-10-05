package com.vikas.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.dreamshops.dtos.CartDto;
import com.vikas.dreamshops.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	void deleteAllByCartId(CartDto cartDto);

}
