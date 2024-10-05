package com.vikas.dreamshops.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vikas.dreamshops.dtos.ImageDto;
import com.vikas.dreamshops.dtos.ProductDto;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Image;
import com.vikas.dreamshops.model.Product;
import com.vikas.dreamshops.repository.ImageRepository;
import com.vikas.dreamshops.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

	private static Logger logger = LogManager.getLogger(ImageService.class);

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Image getImageById(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside ImageService getImageById {} ", id);
		return imageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No image found with id " + id));
	}

	@Override
	public void deleteImageById(Long id) {
		// TODO Auto-generated method stub
		logger.info("Inside ImageService deleteImageById {} ", id);

		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
				() -> new ResourceNotFoundException("No image found with id " + id));

	}

	@Override
	public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
		// TODO Auto-generated method stub
		logger.info("Inside ImageService saveImage {} ", files, productId);
		ProductDto product = productService.getProductById(productId);
		List<ImageDto> savedImageDto = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));

				Product setProduct = modelMapper.map(product, Product.class);
				image.setProduct(setProduct);

				String downloadUrl = "/api/v1/images/image/download/" + image.getId();
				image.setDownloadUrl(downloadUrl);

				Image savedImage = imageRepository.save(image);

				savedImage.setDownloadUrl("/api/v1/images/image/download/" + savedImage.getId());
				imageRepository.save(savedImage);

				ImageDto imageDto = new ImageDto();
				imageDto.setId(savedImage.getId());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());
				imageDto.setFileName(savedImage.getFileName());

				savedImageDto.add(imageDto);

			} catch (IOException | SQLException e) {
				// TODO: handle exception
				throw new RuntimeException(e.getMessage());
			}
		}
		return savedImageDto;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		// TODO Auto-generated method stub
		logger.info("Inside ImageService updateImage {} ", file, imageId);

		Image image = getImageById(imageId);

		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileType(file.getContentType());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e.getMessage());
		}

	}

}
