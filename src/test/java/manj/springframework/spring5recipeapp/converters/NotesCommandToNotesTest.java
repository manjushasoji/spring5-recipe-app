package manj.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manj.springframework.spring5recipeapp.commands.NotesCommand;
import manj.springframework.spring5recipeapp.domain.Notes;

class NotesCommandToNotesTest {

	NotesCommandToNotes notesCommandToNotes;
	public static final Long ID_VALUE = 1L;
	public static final String RECIPE_NOTES = "Notes";

	@BeforeEach
	void setUp() throws Exception {
		notesCommandToNotes = new NotesCommandToNotes();
	}

	@Test
	public void testNullObj() {
		Notes notes = notesCommandToNotes.convert(null);
		assertNull(notes);
	}

	@Test
	public void testEmptyObject() {
		Notes notes = notesCommandToNotes.convert(new NotesCommand());
		assertNotNull(notes);
	}

	@Test
	void testConvert() {
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(ID_VALUE);
		notesCommand.setRecipeNotes(RECIPE_NOTES);
		Notes notes = notesCommandToNotes.convert(notesCommand);

		assertNotNull(notes);
		assertEquals(ID_VALUE, notes.getId());
		assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
	}

}
