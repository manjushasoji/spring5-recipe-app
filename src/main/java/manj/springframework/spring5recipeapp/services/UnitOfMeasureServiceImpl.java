package manj.springframework.spring5recipeapp.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import manj.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import manj.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {

		return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
				.map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());

	}

}
