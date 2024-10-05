package com.vikas.dreamshops.dtos;

import java.math.BigDecimal;


public class CartItemDto {

	private Long id;
	private Long quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;

	private CartDto cart;

	private ProductDto product;
}
