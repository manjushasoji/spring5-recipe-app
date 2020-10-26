package manj.springframework.spring5recipeapp.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.services.IngredientService;
import manj.springframework.spring5recipeapp.services.RecipeService;
import manj.springframework.spring5recipeapp.services.UnitOfMeasureService;

class IngredientControllerTest {

	IngredientController ingredientController;
	MockMvc mockMvc;

	@Mock
	RecipeService recipeService;

	@Mock
	IngredientService ingredientService;
	
	@Mock
	UnitOfMeasureService unitOfMeasureService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService,unitOfMeasureService);
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
	
	@Test
	public void testUpdateIngredient() throws Exception{
		
		IngredientCommand ingredientCommand =  new IngredientCommand();
		ingredientCommand.setRecipeId(1L);
		ingredientCommand.setId(3L);
		when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);
		
		mockMvc.perform(post("/recipe/1/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
				)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredient/3/show"));
	}
	
	@Test
	public void testNewIngredient() throws Exception{
		
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/1/ingredient/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient","uomList"));
		verify(recipeService,times(1)).findCommandById(anyLong());
		
	}
	
	@Test
	public void testDeleteIngredient() throws Exception{
		mockMvc.perform(get("/recipe/2/ingredient/3/delete")
		        )
		                .andExpect(status().is3xxRedirection())
		                .andExpect(view().name("redirect:/recipe/2/ingredients"));

		        verify(ingredientService, times(1)).deleteIngredientById(anyLong(), anyLong());
	}

}
