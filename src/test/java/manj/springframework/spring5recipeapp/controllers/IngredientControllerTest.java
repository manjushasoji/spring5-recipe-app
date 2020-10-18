package manj.springframework.spring5recipeapp.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.services.IngredientService;
import manj.springframework.spring5recipeapp.services.RecipeService;

class IngredientControllerTest {

	IngredientController ingredientController;
	MockMvc mockMvc;

	@Mock
	RecipeService recipeService;

	@Mock
	IngredientService ingredientService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}

	@Test
	void testListIngredients() throws Exception {

		RecipeCommand recipeCommand = new RecipeCommand();

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));

		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void testShowIngredient() throws Exception {

		IngredientCommand ingredientCommand = new IngredientCommand();

		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

		mockMvc.perform(get("/recipe/1/ingredient/1/show")).andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredient/show")).andExpect(model().attributeExists("ingredient"));

	}

}
