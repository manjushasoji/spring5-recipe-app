package manj.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import manj.springframework.spring5recipeapp.commands.NotesCommand;
import manj.springframework.spring5recipeapp.domain.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{
	
	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		if (source==null) {
			return null;
		}
		final NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(source.getId());
		notesCommand.setRecipeNotes(source.getRecipeNotes());
		return notesCommand;
	}

}
