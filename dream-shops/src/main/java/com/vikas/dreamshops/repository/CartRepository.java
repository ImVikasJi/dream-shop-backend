package com.vikas.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.dreamshops.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	void deleteById(Cart cart);

}
