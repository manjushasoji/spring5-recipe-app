package manj.springframework.spring5recipeapp.services;

import manj.springframework.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	
	void deleteIngredientById(Long recipeId, Long ingredientId);

}
