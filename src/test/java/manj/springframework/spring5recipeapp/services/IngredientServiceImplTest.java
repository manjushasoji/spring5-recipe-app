package manj.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import manj.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.domain.Ingredient;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.repositories.RecipeRepository;

class IngredientServiceImplTest {

	IngredientService ingredientService;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	@Mock
	RecipeRepository recipeRepository;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);

	}

	@Test
	void testFindByRecipeIdAndIngredientId() {

		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);

		Optional<Recipe> optionalRecipe = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

		assertEquals(3L, ingredientCommand.getId());
		assertEquals(1L, ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());

	}

}
