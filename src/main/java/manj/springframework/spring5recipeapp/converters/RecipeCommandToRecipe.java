package manj.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final NotesCommandToNotes notesCommandToNotes;
	private final CategoryCommandToCategory categoryCommandToCategory;

	public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient,
			NotesCommandToNotes notesCommandToNotes, CategoryCommandToCategory categoryCommandToCategory) {
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.notesCommandToNotes = notesCommandToNotes;
		this.categoryCommandToCategory = categoryCommandToCategory;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}
		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setCookTime(source.getCookTime());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setDescription(source.getDescription());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));
		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
					.forEach(category -> recipe.getCategories().add(categoryCommandToCategory.convert(category)));
		}
		if (source.getIngredients() != null && source.getCategories().size() > 0) {
			source.getIngredients().forEach(
					ingredient -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
		}

		return recipe;
	}

}
