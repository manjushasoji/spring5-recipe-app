package manj.springframework.spring5recipeapp.services;

import java.util.Set;

import manj.springframework.spring5recipeapp.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
}
