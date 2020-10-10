package manj.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.domain.UnitOfMeasure;

class UnitOfMeasureToUnitOfMeasureCommandTest {

	public static final String DESCRIPTION = "description";
	public static final Long LONG_VALUE = 1L;

	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	public void testNullObject() {
		assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(null));

	}

	@Test
	public void testEmptyObject() {
		assertNotNull(unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure()));

	}

	@Test
	void testConvert() {
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(LONG_VALUE);
		unitOfMeasure.setDescription(DESCRIPTION);

		UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);
		assertEquals(DESCRIPTION, unitOfMeasureCommand.getDescription());
		assertEquals(LONG_VALUE, unitOfMeasureCommand.getId());
	}

}
