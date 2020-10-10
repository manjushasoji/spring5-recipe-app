package manj.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.domain.UnitOfMeasure;

class UnitOfMeasureCommandToUnitOfMeasureTest {

	UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
	public static final String DESCRIPTION = "description";
	public static final Long LONG_VALUE = 1L;

	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	public void testNullParameter() {
		assertNull(unitOfMeasureCommandToUnitOfMeasure.convert(null));

	}

	@Test
	public void testEmptyObject() {
		assertNotNull(unitOfMeasureCommandToUnitOfMeasure.convert(new UnitOfMeasureCommand()));

	}

	@Test
	void testConvert() {
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(LONG_VALUE);
		unitOfMeasureCommand.setDescription(DESCRIPTION);
		UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

		assertNotNull(unitOfMeasure);
		assertEquals(LONG_VALUE, unitOfMeasure.getId());
		assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
	}

}
