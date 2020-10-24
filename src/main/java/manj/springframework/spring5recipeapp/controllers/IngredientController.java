package manj.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.services.IngredientService;
import manj.springframework.spring5recipeapp.services.RecipeService;
import manj.springframework.spring5recipeapp.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
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

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {

		// make sure we have a good id value
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));

		model.addAttribute("ingredient", ingredientCommand);

		ingredientCommand.setUom(new UnitOfMeasureCommand());

		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/ingredient/{ingredientId}/update")
	public String updateIngredient(@PathVariable String id, @PathVariable String ingredientId, Model model) {

		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(id), Long.valueOf(ingredientId)));

		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}

	@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {

		IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

		log.debug("saved receipe id:" + savedIngredientCommand.getRecipeId());
		log.debug("saved ingredient id:" + savedIngredientCommand.getId());

		return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/"
				+ savedIngredientCommand.getId() + "/show";
	}
	
	@GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id){

        log.debug("deleting ingredient id:" + id);
        ingredientService.deleteIngredientById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
