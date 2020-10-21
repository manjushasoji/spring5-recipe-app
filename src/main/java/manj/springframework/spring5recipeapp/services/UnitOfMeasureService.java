package manj.springframework.spring5recipeapp.services;

import java.util.Set;

import manj.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();

}
