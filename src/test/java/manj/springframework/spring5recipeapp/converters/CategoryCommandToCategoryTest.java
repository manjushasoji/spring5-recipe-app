package manj.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.spring5recipeapp.commands.CategoryCommand;
import manj.springframework.spring5recipeapp.domain.Category;

class CategoryCommandToCategoryTest {

	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "description";

	CategoryCommandToCategory categoryCommandToCategory;

	@BeforeEach
	void setUp() throws Exception {
		categoryCommandToCategory = new CategoryCommandToCategory();
	}

	@Test
	public void testNullObject() {
		assertNull(categoryCommandToCategory.convert(null));

	}

	@Test
	public void testEmptyObject() {
		assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));

	}

	@Test
	public void testConvert() throws Exception {
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setDescription(DESCRIPTION);
		Category category = categoryCommandToCategory.convert(categoryCommand);

		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}

}
