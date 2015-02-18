package uk.ac.aber.dcs.piratehangman.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Stores a list of phrases loaded from an external text file to be used in hangman type games.
 * 
 * @author Punit Shah (pus1)
 *
 */
public class PhraseList {
	private ArrayList<String> phrases;
	private String filename;
	
	/**
	 * Constructor for the phrase list.
	 * 
	 * @param filename		the path of the file with phrases to be loaded in
	 * @throws IOException
	 */
	public PhraseList(String filename) throws IOException {
		phrases = new ArrayList<String>();
		this.filename = filename;
		loadPhrases();
	}
	
	/**
	 * Loads phrases from the external text file specified when the object was constructed.
	 * 
	 * @throws IOException
	 */
	public void loadPhrases() throws IOException {
		Scanner infile = new Scanner(new InputStreamReader(new FileInputStream(filename)));
		int num = infile.nextInt(); infile.nextLine();
		for (int i=0;i<num;i++) {
			String p = infile.nextLine();
			phrases.add(p);
		}
		infile.close();
	}
	
	/**
	 * Returns a random phrase from the list of phrases.
	 */
	public String getRandomPhrase() {
		Random rand = new Random();
		return phrases.get(rand.nextInt(phrases.size()));
	}
}
