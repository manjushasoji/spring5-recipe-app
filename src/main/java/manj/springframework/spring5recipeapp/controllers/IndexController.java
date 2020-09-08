package manj.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

import manj.springframework.spring5recipeapp.domain.Category;
import manj.springframework.spring5recipeapp.domain.UnitOfMeasure;
import manj.springframework.spring5recipeapp.repositories.CategoryRepository;
import manj.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import manj.springframework.spring5recipeapp.services.RecipeService;

@Slf4j
@Controller
public class IndexController {

	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping({ "", "/", "index" })
	public String getIndexPage(Model model) {

		log.debug("inside IndexController - Getting Index page");

		model.addAttribute("recipes", recipeService.getRecipes());

		return "index";
	}

}
