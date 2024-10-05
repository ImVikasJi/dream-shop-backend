package com.vikas.dreamshops.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.vikas.dreamshops.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long id;
	private String name;
	private String brand;
	private String description;
	private BigDecimal price;
	private int inventory;
	
	private Category category;
	
	private List<ImageDto> imageDtos;
}
