package com.vikas.dreamshops.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

	private Long id;
	private String fileName;
	private String downloadUrl;

}
