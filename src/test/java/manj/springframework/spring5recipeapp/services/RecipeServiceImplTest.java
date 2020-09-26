package manj.springframework.spring5recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;

import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeRepository recipeRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository);

	}

	@Test
	public void testGetRecipes() {
		Recipe recipe = new Recipe();
		HashSet<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);

		Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);
		Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(recipes.size(), 1);
		Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
	}
	@Test
	public void testGetRecipesById() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
		Recipe returnedRecipe = recipeService.findById(1L);
		assertNotNull("Null Recipe Returned", returnedRecipe);
		verify(recipeRepository,times(1)).findById(anyLong());
		verify(recipeRepository,never()).findAll();
	}

}
