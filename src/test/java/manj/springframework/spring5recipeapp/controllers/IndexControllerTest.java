package manj.springframework.spring5recipeapp.controllers;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.services.RecipeService;

public class IndexControllerTest {

	IndexController indexController;

	@Mock
	RecipeService recipeService;

	@Mock
	Model model;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);

	}

	@Test
	public void testGetIndexPage() {
		
		Set<Recipe> recipesTestSet = new HashSet<>();
		recipesTestSet.add(new Recipe());
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipesTestSet.add(recipe);
		Mockito.when(recipeService.getRecipes()).thenReturn(recipesTestSet);
		
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		String indexPage = indexController.getIndexPage(model);

		assertEquals("index", indexPage);
		Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
		
		
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}

}
