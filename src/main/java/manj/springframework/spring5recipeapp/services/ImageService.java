package manj.springframework.spring5recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	void saveImageFile(Long recipeId, MultipartFile multipartFile);

}
