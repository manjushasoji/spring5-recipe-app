package manj.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import manj.springframework.spring5recipeapp.domain.Category;
import manj.springframework.spring5recipeapp.domain.UnitOfMeasure;
import manj.springframework.spring5recipeapp.repositories.CategoryRepository;
import manj.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Controller
public class IndexController {

	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;

	public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@RequestMapping({ "", "/", "index" })
	public String getIndexPage() {
		
		Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
		Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		
		System.out.println("Cat Id : "+categoryOptional.get().getId());
		System.out.println("UOM Id : "+unitOfMeasureOptional.get().getId());
		
		return "index";
	}

}
