package com.vikas.dreamshops.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vikas.dreamshops.dtos.ImageDto;
import com.vikas.dreamshops.model.Image;

public interface IImageService {

	Image getImageById(Long id);

	void deleteImageById(Long id);

	List<ImageDto> saveImage(List<MultipartFile> file, Long productId);

	void updateImage(MultipartFile file, Long imageId);
}
