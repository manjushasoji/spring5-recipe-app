package manj.springframework.spring5recipeapp.services;

import java.util.Set;

import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long id);

	RecipeCommand findCommandById(Long id);

	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

	void deleteById(Long id);
}
