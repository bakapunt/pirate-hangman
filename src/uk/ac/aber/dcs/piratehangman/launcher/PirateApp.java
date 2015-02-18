package uk.ac.aber.dcs.piratehangman.launcher;

import java.util.Scanner;

import uk.ac.aber.dcs.piratehangman.gui.GuiFrame;
import uk.ac.aber.dcs.piratehangman.text.TextApp;

/**
 * Main class for the game.
 * Allows the user to choose between the text-based and graphics-based versions of the game.  
 * 
 * @author Punit Shah (pus1)
 *
 */
public class PirateApp {

	public static void main(String[] args) {
		makeChoice();
	}
	
	public static void makeChoice() {
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println("Would you like to play the text-based game or the graphics-based game?");
			System.out.print("t - text-based \n"
					+ "g - graphics-based \n"
					+ "q - quit \n");
			String response = input.nextLine();
			if (response.equals("t")) {
				runTextApp();
				break;
			} else if (response.equals("g")) {
				runGuiApp();
				break;
			} else if(response.equals("q")) {
				break;
			} else {
				System.out.println("Invalid choice - please choose again.");
			}
		}
		input.close();
	}
	
	public static void runTextApp() {
		TextApp app = new TextApp();
		app.runApp();
	}
	
	public static void runGuiApp() {
		GuiFrame gui = new GuiFrame();
		gui.showIt();
	}

}
