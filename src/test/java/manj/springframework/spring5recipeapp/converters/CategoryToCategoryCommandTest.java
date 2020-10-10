package manj.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.spring5recipeapp.commands.CategoryCommand;
import manj.springframework.spring5recipeapp.domain.Category;

class CategoryToCategoryCommandTest {

	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "description";
	CategoryToCategoryCommand categoryToCategoryCommand;

	@BeforeEach
	void setUp() throws Exception {
		categoryToCategoryCommand = new CategoryToCategoryCommand();
	}

	@Test
	public void testNullObj() throws Exception {
		assertNull(categoryToCategoryCommand.convert(null));

	}

	@Test
	private void testEmptyObj() {
		assertNotNull(categoryToCategoryCommand.convert(new Category()));

	}

	@Test
	void testConvert() throws Exception {
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);
		CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());
	}

}
