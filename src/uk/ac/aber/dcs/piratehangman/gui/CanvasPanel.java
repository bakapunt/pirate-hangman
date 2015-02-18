package uk.ac.aber.dcs.piratehangman.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Panel in which the images for the game are drawn and animated in.
 * 
 * @author Punit Shah (pus1)
 *
 */
public class CanvasPanel extends JPanel implements Runnable {
	private MediaTracker mTracker;
	
	private Image bgImage;
	private int bgImageID;
	
	private Image[] animateImage = new Image[2];
	private int[] animateImageID = new int[2];
	
	private Thread animator;
	private int current;
	
	private int xPos;
	private int yPos;
	private boolean dead;

	/**
	 * Constructor for the canvas panel.
	 * Creates the panel and sets up the media tracker to track images for the
	 * background and the animation.
	 */
	public CanvasPanel() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(700, 400));
		setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		mTracker = new MediaTracker(this);
		
		//set up background
		bgImageID = 0;
		bgImage = Toolkit.getDefaultToolkit().getImage("img/piratebg.png");
		mTracker.addImage(bgImage,bgImageID);
		try {
            mTracker.waitForID(bgImageID);
        } catch(InterruptedException e) {
            System.out.println("Error loading background");
        }
		
		//set up animation of 2 images
		for (int i=0;i<2;i++) {
			animateImageID[i] = 1;
			animateImage[i] = Toolkit.getDefaultToolkit().getImage(
				"img/sadpirate0"+i+".png"); //sadpirate00.png, sadpirate01.png
			mTracker.addImage(animateImage[i], animateImageID[i]);
			try {
				mTracker.waitForID(animateImageID[i]);
			} catch (InterruptedException e) {
				System.out.println("Error loading img/sadpirate0"+i+".png");
			}
		}
		xPos = 420;
		yPos = 60;
		
		repaint();
		current = 0;
	}
	
	/**
	 * Starts the animator thread.
	 */
	void move() {
		current = 0;
		animator = new Thread(this);
		animator.start();
	}

	/**
	 * Moves the pirate along the plank. If the user has run out of guesses, the pirate will also fall off the plank.
	 */
	@Override
	public void run() {
		for (int i=0;i<6;i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				break;
			}
			xPos -= 5; // moves the pirate in the x direction
			current = (current+1)%2;
			repaint();
		}
		if (dead) {
			for (int i=0;i<15;i++) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					break;
				}
				xPos -= 5;	// moves the pirate in the x direction
				yPos += 20;	// and the y direction
				current = (current+1)%2;
				repaint();
			}
		}
	}
	
	/**
	 * Changes the image for the pirate to one with a smile on his face.
	 */
	public void win() {
		animateImage[current] = Toolkit.getDefaultToolkit().getImage("img/happypirate.png");
		repaint();
	}
	
	/**
	 * Sets the dead boolean to true and moves the pirate (falls off the plank).
	 */
	public void die() {
		dead = true;
		move();
	}
	
	/**
	 * Draws the images.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, this);
		g.drawImage(animateImage[current], xPos, yPos, this);
	}
}
