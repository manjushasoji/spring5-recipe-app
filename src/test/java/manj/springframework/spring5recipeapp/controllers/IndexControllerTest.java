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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);

	}

	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("index"));

	}

	@Test
	public void testGetIndexPage() throws Exception {

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
