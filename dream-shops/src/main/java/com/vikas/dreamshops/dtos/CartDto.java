package com.vikas.dreamshops.dtos;

import java.math.BigDecimal;
import java.util.Set;

public class CartDto {

	
	private Long id;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private Set<CartItemDto> items;
}
