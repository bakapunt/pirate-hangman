package uk.ac.aber.dcs.piratehangman.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.piratehangman.model.GameModel;

public class GameModelTest {
	private GameModel model;
	private String phrase;
	
	@Before
	public void setupData() {
		phrase = "test phrase";
		model = new GameModel(phrase);
	}
	
	@Test
	public void tryCorrectLetter() {
		model.tryThis('t');
		
		// check that visible was updated to show the guessed letter
		assertEquals("t**t ******",model.getVisible());
	}
	
	@Test
	public void tryIncorrectLetter() {
		model.tryThis('z');
		
		// check that the guessed letter was added to the letters array
		assertEquals('z',model.getLettersArray()[0]);
		// check that a guess is removed from guessesLeft
		assertEquals(9,model.guessLeft());
	}
	
	@Test
	public void tryCorrectPhrase() {
		boolean test = model.tryWord("test phrase");
		
		// check that visible = phrase guessed  
		assertEquals(phrase,model.getVisible()); 
		// check that the method returns true 
		assertTrue(test);
	}

	@Test
	public void tryIncorrectPhrase() {
		boolean test = model.tryWord("wrong phrase");
		
		// check that 5 guesses are removed from guessesLeft
		assertEquals(5, model.guessLeft());
		// check that the method returns false
		assertFalse(test);
	}
}
