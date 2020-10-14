package manj.springframework.spring5recipeapp.controllers;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.services.RecipeService;

class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	RecipeController recipeController;
	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
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

		mockMvc.perform(MockMvcRequestBuilders.post("/recipe"))
				// .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));

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

		mockMvc.perform(MockMvcRequestBuilders.post("/recipe/2/delete"))
				// .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/"));

	}

}
