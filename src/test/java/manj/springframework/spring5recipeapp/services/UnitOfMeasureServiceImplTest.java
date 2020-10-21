package manj.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import manj.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.domain.UnitOfMeasure;
import manj.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

class UnitOfMeasureServiceImplTest {
	
	private
	
	UnitOfMeasureService unitOfMeasureService;
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	void testListAllUoms() {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setId(3L);
		
		Set<UnitOfMeasure> uomSet = new HashSet<>();
		uomSet.add(uom1);
		uomSet.add(uom2);
		uomSet.add(uom3);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(uomSet);
		
		Set< UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();
		assertEquals(3, unitOfMeasureCommands.size());
		verify(unitOfMeasureRepository,times(1)).findAll();
		
		
	}

}
