package manj.springframework.spring5recipeapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.exceptions.NotFoundException;
import manj.springframework.spring5recipeapp.services.RecipeService;
import org.springframework.http.MediaType;

class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	RecipeController recipeController;
	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	}

	@Test
	void testShowById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		when(recipeService.findById(anyLong())).thenReturn(recipe);
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("recipe/show"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
	}

	@Test
	void testRecipeNotFound() throws Exception {
		when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(view().name("404error"));

	}

	@Test
	void testRecipeNumberFormat() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/sometext/show"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(view().name("400error"));

	}

	@Test
	public void testGetNewRecipeForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

	}

	@Test
	public void testPostNewRecipeForm() throws Exception {

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);

		// when
		when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                	.param("id", "")
                	.param("description", "some string")
                	.param("directions", "some directions"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/show"));

	}
	@Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                						.param("id", "")
                						.param("cookTime", "30000"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);

		// when
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/update")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

	}

	@Test
	public void testDeleteAction() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/delete"))
				// .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/"));

	}

}
