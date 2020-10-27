package manj.springframework.spring5recipeapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.repositories.RecipeRepository;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile multipartFile) {

		log.debug("Received a file");
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();

			Byte[] byteObjects = new Byte[multipartFile.getBytes().length];

			int i = 0;

			for (byte b : multipartFile.getBytes()) {
				byteObjects[i++] = b;
			}
			recipe.setImage(byteObjects);
			recipeRepository.save(recipe);

		} catch (Exception e) {
			log.error("Error -", e);
			e.printStackTrace();
		}
	}

}
