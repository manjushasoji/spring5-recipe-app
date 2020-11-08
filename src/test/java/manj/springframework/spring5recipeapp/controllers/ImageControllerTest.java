package manj.springframework.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.services.ImageService;
import manj.springframework.spring5recipeapp.services.RecipeService;

class ImageControllerTest {

	ImageController imageController;
	MockMvc mockMvc;

	@Mock
	RecipeService recipeService;

	@Mock
	ImageService imageService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageController = new ImageController(recipeService, imageService);
		mockMvc = MockMvcBuilders.standaloneSetup(imageController)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	}

	@Test
	void testShowUploadForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk())
				.andExpect(view().name("recipe/imageuploadform")).andExpect(model().attributeExists("recipe"));
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	void testHandleImagePost() throws Exception {

		MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Spring Framework Guru".getBytes());// (name, originalFilename, contentType, content)

		mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile)).andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));

		verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}

	@Test
	public void renderImageFromDB() throws Exception {

		// given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);

		String s = "fake image text";
		Byte[] bytesBoxed = new Byte[s.getBytes().length];

		int i = 0;

		for (byte primByte : s.getBytes()) {
			bytesBoxed[i++] = primByte;
		}

		command.setImage(bytesBoxed);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);

		// when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage")).andExpect(status().isOk())
				.andReturn().getResponse();

		byte[] reponseBytes = response.getContentAsByteArray();

		assertEquals(s.getBytes().length, reponseBytes.length);
	}
	
	@Test
    public void testGetImageNumberFormatException() throws Exception {

        mockMvc.perform(get("/recipe/sometext/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

}
