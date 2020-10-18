package manj.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import manj.springframework.spring5recipeapp.services.IngredientService;
import manj.springframework.spring5recipeapp.services.RecipeService;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable String id, Model model) {

		log.debug("getting recipe ingredients");

		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

		return "/recipe/ingredient/list";
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/ingredient/{ingredientId}/show")
	public String showIngredient(@PathVariable String id, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(id), Long.valueOf(ingredientId)));

		return "/recipe/ingredient/show";
	}

}
