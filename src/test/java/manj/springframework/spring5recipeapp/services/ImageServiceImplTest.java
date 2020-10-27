package manj.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.repositories.RecipeRepository;

class ImageServiceImplTest {

	ImageService imageService;

	@Mock
	RecipeRepository recipeRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);

	}

	@Test
	void testSaveImageFile() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		 MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
	                "Spring Framework".getBytes());
		
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		imageService.saveImageFile(1L, multipartFile);
		
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
		

	}

}
