package uk.ac.aber.dcs.piratehangman.text;

import java.io.IOException;
import java.util.Scanner;

import uk.ac.aber.dcs.piratehangman.model.GameModel;
import uk.ac.aber.dcs.piratehangman.model.PhraseList;

/**
 * Application class for the text-based version of the game.
 * 
 * @author Punit Shah (pus1)
 *
 */
public class TextApp {
	private GameModel model;
	private Scanner input;
	private PhraseList phraselist;
	
	/**
	 * Constructor for the text-based app that uses a random phrase from an external phrase list.
	 * Loads the phrase list from the file 'piratewords.txt' and sets the hidden phrase 
	 * to a random phrase from the list.
	 */
	public TextApp() {
		try {
			phraselist = new PhraseList("piratewords.txt");
		} catch (IOException e) {
			System.out.println("There was a problem with the text file piratewords.txt. "
					+"Please make sure piratewords.txt exists and is in a format readable by this application.");
		}
		model = new GameModel(phraselist.getRandomPhrase());
		input = new Scanner(System.in);
	}
	
	/**
	 * Constructor for the text-based app that allows use of a specified phrase.
	 * Sets the hidden phrase to the specified phrase. This constructor can be used for
	 * testing.
	 * 
	 * @param phrase	phrase to be used as the hidden phrase
	 */
	public TextApp(String phrase) {
		model = new GameModel(phrase);
		input = new Scanner(System.in);
	}
	
	/**
	 * Prints the current status of the game.
	 * Includes guessed letters, how many guesses the user has left, and what the user
	 * can see (visible).
	 */
	public void printStatus() {
		System.out.println("You have guessed these incorrect letters so far: \n" + 
				model.getLetters());
		System.out.println("You have " + model.guessLeft() + " guesses left.");
		System.out.println("You have worked out the following: \n" + 
				model.getVisible());
	}
	
	/**
	 * Runs the game application.
	 * Runs a loop letting the user make guesses until there is a winner or the user 
	 * runs out of guesses.
	 */
	public void runApp() {
		System.out.println("Welcome to this game of hangman!\n***************");
		do {
			printStatus();
			System.out.print("\nType 'l' to guess a letter or 'p' to guess the whole phrase: ");
			String response = input.nextLine();
			if (response.equalsIgnoreCase("l")) {
				if (guessLetter()) {
					break; //user is winner
				}
			} else if (response.equalsIgnoreCase("p")) {
				if (guessPhrase()) {
					break; //user is winner
				}
			} else {
				System.out.println("Invalid choice. Please enter either 'l' or 'p'.");
			}
		} while(model.guessLeft() > 0);
										//loop will break
		if (model.guessLeft() <= 0) { 	//if the user runs out of guesses
			System.out.println("Game over - you have run out of guesses!");
		} else {						//or if the user wins
			System.out.println("Congratulations, you have won!\n"
					+ "The phrase was '" + model.getHidden() + "'");
		}
	}
	
	/**
	 * Takes letter from user input and tries it.
	 * 
	 * @return	whether there is a winner
	 */
	public boolean guessLetter() {
		System.out.print("Guess a letter: ");
		String l = input.nextLine();
		if (l.length() == 1) {
			char letter = l.charAt(0);
			if (model.tryThis(letter)) {
				return true; //user is winner
			}
		} else {
			System.out.println("You must enter exactly one letter.");
			return false;
		}
		return false;
	}
	
	/**
	 * Takes phrase from user input and tries it.
	 * 
	 * @return	whether there is a winner
	 */
	public boolean guessPhrase() {
		System.out.print("Guess a phrase: ");
		String p = input.nextLine();
		if (p.length() > 0) {
			if (model.tryWord(p)) {
				return true; //user is winner
			}
		} else {
			System.out.println("You must enter a phrase.");
			return false;
		}
		return false;
	}
}
