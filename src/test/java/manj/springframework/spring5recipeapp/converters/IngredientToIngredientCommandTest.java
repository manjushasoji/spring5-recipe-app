package manj.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.spring5recipeapp.commands.IngredientCommand;
import manj.springframework.spring5recipeapp.domain.Ingredient;
import manj.springframework.spring5recipeapp.domain.Recipe;
import manj.springframework.spring5recipeapp.domain.UnitOfMeasure;

class IngredientToIngredientCommandTest {
	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	public static final Recipe RECIPE = new Recipe();
	public static final BigDecimal AMOUNT = new BigDecimal("1");
	public static final String DESCRIPTION = "Cheeseburger";
	public static final Long ID_VALUE = 1L;
	public static final Long UOM_ID = 2L;

	@BeforeEach
	void setUp() throws Exception {
		ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		
	}

	@Test
	void testConvert() {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setAmount(AMOUNT);
		ingredient.setRecipe(RECIPE);
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(UOM_ID);
		unitOfMeasure.setDescription(DESCRIPTION);
		ingredient.setUom(unitOfMeasure);
		IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
		
		assertNotNull(ingredientCommand);
		assertNotNull(ingredientCommand.getUom());
		assertEquals(ID_VALUE,ingredientCommand.getId());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(UOM_ID, ingredientCommand.getUom().getId());
	}
	
	@Test
	void testConvertWithNullUom() {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setAmount(AMOUNT);
		ingredient.setRecipe(RECIPE);
	
		IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
		
		assertNotNull(ingredientCommand);
		assertNull(ingredientCommand.getUom());
		assertEquals(ID_VALUE,ingredientCommand.getId());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
		assertEquals(AMOUNT, ingredientCommand.getAmount());
	}

}
