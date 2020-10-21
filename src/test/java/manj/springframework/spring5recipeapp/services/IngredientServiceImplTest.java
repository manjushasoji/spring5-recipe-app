package manj.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import manj.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import manj.springframework.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import manj.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.domain.Ingredient;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.repositories.RecipeRepository;
import manj.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

class IngredientServiceImplTest {

	IngredientService ingredientService;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand
				,unitOfMeasureRepository ,ingredientCommandToIngredient);

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
	
	@Test
	void testSaveIngredientCommand() {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(3L);
		ingredientCommand.setRecipeId(2L);
		
		Recipe recipe = new Recipe();
		recipe.setId(2L);
		
		Optional<Recipe> optionalRecipe = Optional.of(recipe);
		
		Recipe savedRecipe = new Recipe();
		//savedRecipe.setId(1L);
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		
		
		when(recipeRepository.findById(any())).thenReturn(optionalRecipe);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		
		assertEquals(3L, savedIngredientCommand.getId());
		verify(recipeRepository, times(1)).findById(any());
		verify(recipeRepository,times(1)).save(any(Recipe.class));
	}

}
