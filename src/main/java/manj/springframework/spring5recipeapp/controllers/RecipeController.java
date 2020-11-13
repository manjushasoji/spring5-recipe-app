package manj.springframework.spring5recipeapp.controllers;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import manj.springframework.spring5recipeapp.commands.RecipeCommand;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.exceptions.NotFoundException;
import manj.springframework.spring5recipeapp.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	private final RecipeService recipeService;
	

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping({ "/recipe/{id}/show" })
	public String showById(@PathVariable String id, Model model) {
		Recipe recipe = recipeService.findById(Long.valueOf(id));
		model.addAttribute("recipe", recipe);
		return "recipe/show";
	}

	@GetMapping("recipe/new")
	public String newRecipe(Model model) {

		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}

	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {

		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return RECIPE_RECIPEFORM_URL;
	}

	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(errorObj -> log.debug(errorObj.toString()));
			return RECIPE_RECIPEFORM_URL;
		}
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
	}

	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {

		log.debug("Deleting Recipe id: " + id);
		recipeService.deleteById(Long.valueOf(id));

		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {

		log.error("RecipeController :Not Found Exception");
		log.error(exception.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", exception);
		return modelAndView;

	}

}
