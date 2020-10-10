package manj.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.spring5recipeapp.commands.NotesCommand;
import manj.springframework.spring5recipeapp.domain.Notes;

class NotesToNotesCommandTest {

	NotesToNotesCommand notesToNotesCommand;
	public static final Long ID_VALUE = 1L;
	public static final String RECIPE_NOTES = "Notes";

	@BeforeEach
	void setUp() throws Exception {
		notesToNotesCommand = new NotesToNotesCommand();
	}

	@Test
	public void testNullObj() {
		NotesCommand notesCommand = notesToNotesCommand.convert(null);
		assertNull(notesCommand);
	}

	@Test
	public void testEmptyObject() {
		NotesCommand notesCommand = notesToNotesCommand.convert(new Notes());
		assertNotNull(notesCommand);
	}

	@Test
	void testConvert() {
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setRecipeNotes(RECIPE_NOTES);
		NotesCommand notesCommand = notesToNotesCommand.convert(notes);

		assertNotNull(notesCommand);
		assertEquals(ID_VALUE, notesCommand.getId());
		assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());

	}

}
