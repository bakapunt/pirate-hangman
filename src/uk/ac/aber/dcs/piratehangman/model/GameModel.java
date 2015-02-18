package uk.ac.aber.dcs.piratehangman.model;

/**
 * Game model for hangman type games.
 * 
 * @author Punit Shah (pus1)
 *
 */
public class GameModel implements GameModelInterface {
	private String hidden;
	private String visible;
	private int guessesLeft;
	private char[] letters;
	private int lettersnum;
	
	/**
	 * Constructor for the game model.
	 * Sets the hidden phrase and creates the initial visible string from it. 
	 * @param phrase	The phrase to be guessed
	 */
	public GameModel(String phrase) {
		hidden = phrase;
		guessesLeft = 10;
		letters = new char[10];
		lettersnum = 0;
		initialVisible();
	}

	/**
	 * Returns what the user is allowed to see.
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * Returns the hidden phrase.
	 */
	public String getHidden() {
		return hidden;
	}

	/**
	 * Returns the number of guesses the user has left.
	 */
	public int guessLeft() {
		return guessesLeft;
	}

	/**
	 * Returns letters that have already been guessed incorrectly as a string.
	 */
	public String getLetters() {
		String l = new String(letters).toUpperCase(); 
		return l;
	}
	
	/**
	 * Returns the array of incorrectly guessed letters.
	 */
	public char[] getLettersArray() {
		return letters;
	}

	/**
	 * Checks if the letter guessed is in the phrase.
	 * If it is, visible is updated.
	 * If not, a guess is removed from guessesLeft.
	 * 
	 * @param l	the letter guessed
	 * @return 			whether there is a winner
	 */
	public boolean tryThis(char l) {
		char letter = Character.toLowerCase(l); // to prevent problems with same letter being guessed in both upper case and lower case
		if ((alreadyGuessed(letter) == false)) {
			if (hidden.contains(Character.toString(letter))) {
				updateVisible(letter);
			} else {
				System.out.println("Sorry, your guess is wrong.");
				letters[lettersnum] = letter;
				lettersnum++;
				guessesLeft--;
			}
		} else {
			System.out.println("You have already guessed that letter.");
		}
		
		if (visible.equalsIgnoreCase(hidden)) {
			return true; //user is winner
		} else {
			return false;
		}
	}

	/**
	 * Checks if the phrase guessed is correct.
	 * If it is, visible is updated.
	 * If not, 5 guesses are removed from guessesLeft.
	 * 
	 * @param guess		the phrase guessed
	 * @return			whether there is a winner
	 */
	public boolean tryWord(String guess) {
		if (guess.equalsIgnoreCase(hidden)) {
			visible = guess;
			return true; //user is winner
		} else {
			System.out.println("Sorry, your guess is wrong.");
			guessesLeft -= 5;
			return false;
		}
	}
	
	/**
	 * Creates the initial visible string.
	 * Converts each letter in the hidden phrase to a star (*).
	 * Any characters that are not letters of the alphabet remain as they are.
	 */
	private void initialVisible() {
		char[] visCharArray = hidden.toCharArray();
		for (int i=0;i<hidden.length();i++) {
			if (Character.isLetter(visCharArray[i])) {
				visCharArray[i] = '*';
			}
		}
		String vis = new String(visCharArray);
		visible = vis;
	}
	
	/**
	 * Updates the visible string to show letters that have been correctly guessed.
	 * Called whenever a letter is guessed. If the letter guessed appears in the hidden
	 * phrase, it will be shown in the visible string.
	 * 
	 * @param letter	the letter guessed
	 */
	private void updateVisible(char letter) {
		char[] visCharArray = visible.toCharArray();
		for (int i=0;i<hidden.length();i++) {
			if (letter == hidden.charAt(i)) {
				visCharArray[i] = letter;
			}
		}
		String vis = new String(visCharArray);
		visible = vis;
	}
	
	/**
	 * Checks if a letter has already been guessed.
	 * Returns true if the letter is in the visible string or in the letters array.
	 * 
	 * @param letter	letter to be checked
	 * @return			whether the letter has already been guessed
	 */
	private boolean alreadyGuessed(char letter) {
		if (visible.contains(Character.toString(letter))) {
			return true;
		}
		for (int i=0;i<lettersnum;i++) {
			if (letter == letters[i]) {
				return true;
			}
		}
		return false;
	}
}
