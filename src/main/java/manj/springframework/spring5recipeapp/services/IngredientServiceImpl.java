package manj.springframework.spring5recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.repositories.RecipeRepository;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if (!optionalRecipe.isPresent()) {
			log.error("Recipe id not found: " + recipeId);
		}
		Recipe recipe = optionalRecipe.get();

		Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		if (!optionalIngredientCommand.isPresent()) {
			log.error("Ingredient id not found: " + ingredientId);
		}
		IngredientCommand ingredientCommand = optionalIngredientCommand.get();

		return ingredientCommand;
	}

}
