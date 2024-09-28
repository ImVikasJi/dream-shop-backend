package com.vikas.dreamshops.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vikas.dreamshops.dtos.ImageDto;
import com.vikas.dreamshops.exceptions.ResourceNotFoundException;
import com.vikas.dreamshops.model.Image;
import com.vikas.dreamshops.response.ApiResponse;
import com.vikas.dreamshops.service.image.IImageService;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
	
	@Autowired
	private IImageService iImageService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(
			@RequestParam List<MultipartFile> files,
			@RequestParam Long productId){
		
				try {
					List<ImageDto> imageDtos = iImageService.saveImage(files, productId);
					return ResponseEntity.ok(new ApiResponse("Uploaded successfully", imageDtos));
				} catch (Exception e) {
					// TODO: handle exception
					return ResponseEntity.internalServerError().body(new ApiResponse("Upload failed! ", e.getMessage()));
				}
	}
	
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<Resource> dowloadImage(
			@PathVariable Long imageId			
			) throws SQLException{
		Image image = iImageService.getImageById(imageId);
		
			ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
					.body(resource);		
	}
	
	@PutMapping("image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(
			@PathVariable Long imageId, 
			@RequestBody MultipartFile file){
		
		Image image = iImageService.getImageById(imageId);
		
		try {
			if (image != null) {
				iImageService.updateImage(file, imageId);
				return ResponseEntity.ok(new ApiResponse("Update success! ", null));
			} 
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed! ",HttpStatus.INTERNAL_SERVER_ERROR));
						
	}
	
	@DeleteMapping("image/{imageId}/delete")
	public ResponseEntity<ApiResponse> deleteImage(
			@PathVariable Long imageId){
		
		Image image = iImageService.getImageById(imageId);
		
		try {
			if (image != null) {
				iImageService.deleteImageById(imageId);
				return ResponseEntity.ok(new ApiResponse("Deleted successfully! ", null));
			} 
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed! ",HttpStatus.INTERNAL_SERVER_ERROR));
						
	}
	
	

}
