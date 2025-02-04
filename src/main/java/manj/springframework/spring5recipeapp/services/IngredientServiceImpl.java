package manj.springframework.spring5recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import manj.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import manj.springframework.spring5recipeapp.domain.Ingredient;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.repositories.RecipeRepository;
import manj.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (!optionalRecipe.isPresent()) {
			log.error("Recipe not found for " + ingredientCommand.getRecipeId());
			return new IngredientCommand();
		}
		Recipe recipe = optionalRecipe.get();

		Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

		if (optionalIngredient.isPresent()) {
			Ingredient ingredientFound = optionalIngredient.get();
			ingredientFound.setDescription(ingredientCommand.getDescription());
			ingredientFound.setAmount(ingredientCommand.getAmount());
			ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
					.orElseThrow(() -> new RuntimeException("UOM Not found")));

		} else {
			Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
			recipe.addIngredient(ingredient);
		}

		Recipe savedRecipe = recipeRepository.save(recipe);

		Optional<Ingredient> optionalSavedIngredient = savedRecipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
		if (!optionalSavedIngredient.isPresent()) {
			// not totally safe... But best guess
			optionalSavedIngredient = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getDescription()
							.equals(ingredientCommand.getDescription()))
					.filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
					.filter(recipeIngredients -> recipeIngredients.getUom().getId()
							.equals(ingredientCommand.getUom().getId()))
					.findFirst();
		}

		return ingredientToIngredientCommand.convert(optionalSavedIngredient.get());
	}

	@Override
	public void deleteIngredientById(Long recipeId, Long ingredientId) {
		
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if (optionalRecipe.isPresent()) {
			log.debug("Recipe Found");
			Recipe recipe = optionalRecipe.get();
			Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId) )
					.findFirst();
			if (optionalIngredient.isPresent()) {
				Ingredient ingredient = optionalIngredient.get();
				ingredient.setRecipe(null);
				recipe.getIngredients().remove(ingredient);
				recipeRepository.save(recipe);
				
				
			}else {
				log.error("Ingredient Not Found :"+ingredientId);
			}
			
		}else {
			log.error("Recipe not found :"+recipeId);
		}
		

	}

}
