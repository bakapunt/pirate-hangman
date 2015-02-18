package uk.ac.aber.dcs.piratehangman.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import uk.ac.aber.dcs.piratehangman.model.PhraseList;

public class PhraseListTest {
	
	private PhraseList phraselist;

	@Test
	public void loadPhraseListAndGetRandomPhrase() throws IOException {
		phraselist = new PhraseList("piratewords.txt");
		String randomPhrase = null;
		randomPhrase = phraselist.getRandomPhrase();
		
		// check that getRandomPhrase does not return a null
		assertNotNull(randomPhrase);
	}

}
