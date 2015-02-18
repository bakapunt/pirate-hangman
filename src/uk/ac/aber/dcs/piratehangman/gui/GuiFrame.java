package uk.ac.aber.dcs.piratehangman.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import uk.ac.aber.dcs.piratehangman.model.GameModel;
import uk.ac.aber.dcs.piratehangman.model.PhraseList;

/**
 * The frame for the graphical version of the game.
 * 
 * @author Punit Shah (pus1)
 *
 */
public class GuiFrame extends JFrame implements ActionListener {
	private GameModel model;
	private PhraseList phraselist;
	
	private GuessPanel guesspanel;
	private InfoPanel infopanel;
	private CanvasPanel canvas;
	
	private int guessesLeft;
	
	/**
	 * Constructor for the graphical user interface's frame.
	 * Loads a random phrase from the phrase list and builds the GUI in the 
	 * event dispatch thread.
	 */
	public GuiFrame() {
		try {
			phraselist = new PhraseList("piratewords.txt");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "There was a problem with the text file piratewords.txt. "
					+"Please make sure piratewords.txt exists and is in a format readable by this application.");
		}
		model = new GameModel(phraselist.getRandomPhrase());
		guessesLeft = model.guessLeft();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildGui();
			}
		});
	}
	
	/**
	 * Builds the GUI, creating and positioning its components.
	 */
	public void buildGui() {
		this.setLocation(300, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Drown the Scurvy Dog!");
		
		guesspanel = new GuessPanel(this);
		add(guesspanel,BorderLayout.WEST);
		
		infopanel = new InfoPanel();
		add(infopanel,BorderLayout.EAST);
		
		canvas = new CanvasPanel();
		add(canvas,BorderLayout.SOUTH);
		
		pack();
	}
	
	/**
	 * Makes the frame visible.
	 */
	public void showIt() {
		this.setVisible(true);
	}
	
	/**
	 * Executed when a button is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == guesspanel.letterbutton) {
			guessLetter();
		} else if (e.getSource() == guesspanel.phrasebutton) {
			guessPhrase();
		}
	}
	
	/**
	 * Takes letter from text field and tries it.
	 */
	public void guessLetter() {
		String l = guesspanel.letterfield.getText();
		if (l.length() == 1) {
			char letter = l.charAt(0);
			if (model.tryThis(letter)) {
				win(); // user is winner
			}
			infopanel.update();
			if (updateGuessesLeft() == 0) {
				if (model.guessLeft() <= 0) {
					die(); // game over
				}
				canvas.move();
			}
		} else {
			JOptionPane.showMessageDialog(null, "You must guess exactly one letter.");
		}
		guesspanel.letterfield.setText(""); //reset text field
	}
	
	/**
	 * Takes phrase from text field and tries it.
	 */
	public void guessPhrase() {
		String p = guesspanel.phrasefield.getText();
		if (p.length() > 0) {
			if (model.tryWord(p)) {
				win(); // user is winner
			}
			infopanel.update();
			if (updateGuessesLeft() == 1) {
				for (int i=0;i<5;i++) {
					canvas.move();
				}
				if (model.guessLeft() <= 0) {
					die(); // game over
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "You must guess a phrase.");
		}
		guesspanel.phrasefield.setText(""); //reset text field
	}
	
	/**
	 * Checks if the variable guessLeft in the model has changed.
	 * 
	 * @return	0 if one guess has been taken away, 1 if five guesses have been taken away, -1 if guessLeft has not changed.
	 */
	public int updateGuessesLeft() {
		if (model.guessLeft() == guessesLeft-1) {
			guessesLeft = model.guessLeft();
			return 0;
		} else if (model.guessLeft() == guessesLeft-5) {
			guessesLeft = model.guessLeft();
			return 1;
		} else {
			return -1;
		}
	}
	
	/**
	 * Puts a smile on the pirate's face, displays a congratulatory message, and exits the game.
	 */
	public void win() {
		canvas.win();
		JOptionPane.showMessageDialog(null, "Congratulations, you get to survive!");
		this.dispose();
	}
	
	/**
	 * Drowns the pirate, displays a sad message, and exits the game.
	 */
	public void die() {
		canvas.die();
		JOptionPane.showMessageDialog(null, "Oh dear, you are now at the bottom of the sea.\n"
				+ "Game over!");
		this.dispose();
	}
	
	// inner classes
	/**
	 * Panel to show incorrectly guessed letters and the visible string.
	 */
	class InfoPanel extends JPanel {
		JLabel letters;
		JLabel visible;
		
		/**
		 * Constructor for the information panel.
		 * Creates labels with relevant information and adds them to a grid layout.
		 */
		public InfoPanel() {
			this.setPreferredSize(new Dimension(385, 100));
			this.setLayout(new GridLayout(2, 2, 5, 5));

			JLabel lettersLabel = new JLabel("Incorrect letters guessed: ");
			this.add(lettersLabel);
			letters = new JLabel(model.getLetters());
			this.add(letters);

			JLabel visibleLabel = new JLabel("Worked out so far: ");
			this.add(visibleLabel);
			visible = new JLabel(model.getVisible());
			this.add(visible);
		}
		
		/**
		 * Updates the information in the labels.
		 */
		public void update() {
			letters.setText(model.getLetters());
			visible.setText(model.getVisible());
		}
	}
	
	/**
	 * Panel that allows users to make guesses.
	 */
	class GuessPanel extends JPanel {
		JTextField letterfield;
		JTextField phrasefield;
		JButton letterbutton;
		JButton phrasebutton;
		
		/**
		 * Constructor for the guess panel.
		 * Creates text fields and corresponding buttons to allow the user to
		 * guess a letter or the phrase.
		 * 
		 * @param actlist	ActionListener that listens to the buttons being clicked 
		 */
		public GuessPanel(ActionListener actlist) {
			this.setPreferredSize(new Dimension(270, 100));
			this.setLayout(new GridLayout(2, 2, 5, 5));
			
			letterfield = new JTextField(10);
			this.add(letterfield);
			letterbutton = new JButton("Guess Letter");
			this.add(letterbutton);
			letterbutton.addActionListener(actlist);
			
			phrasefield = new JTextField(10);
			this.add(phrasefield);
			phrasebutton = new JButton("Guess Phrase");
			this.add(phrasebutton);
			phrasebutton.addActionListener(actlist);
		}
	}
}
