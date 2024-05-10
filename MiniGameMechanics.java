/**
 * MiniGameMechanics class. Contains methods for initializing and keeping score
 * of sewing minigame. Tracks number of clicks and regsiters succesful clicks on
 * button as successful sewing. Takes number of clothes made at end of game and 
 * allows player to get money in return. 
 * @author Savannah Larsen
 * @version 1.0 06 May 2024
 * @filename MiniGameMechanics.java
 */
package oregonTrail;

import javax.swing.*;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiniGameMechanics extends javax.swing.JFrame
{
	// constants for stitch count 
    private int TOTAL_CLICKS = 10;
    private int FRAME_BOUNDS = 50;
	
    // initializing variables
    private int stitchesMade = 0;
    private boolean gameOver = false;
    private Random random = new Random();
    private JButton stitchButton;
    private int clicks = 0;
    int totalMoney = 0;
    private int clicksOnButton = 0; // Track the number of successful clicks
    
    /**
	 * Checks if click is inside of the button
	 * @return the x location of the button, y location of button
     */
    public boolean isClickInsideButton(int x, int y) 
    {
        return x >= stitchButton.getX() && x <= stitchButton.getX() + stitchButton.getWidth() &&
               y >= stitchButton.getY() && y <= stitchButton.getY() + stitchButton.getHeight();
    }
    
    /**
	 * Constructor, passes stitchButton to class
     */
    public MiniGameMechanics(JButton stitchButton) 
    {
        setFocusable(true); // Necessary for events to work
        setSize(450, 300); // Set the size of the game window
        setLocationRelativeTo(null); // Place the window
        this.stitchButton = stitchButton;
    }

    /**
	 * Checks if button is clicked and increments if while game is not over
     */
	public void updateStitches() 
	{
		clicksOnButton++;
        if (!gameOver) 
        {
            stitchesMade++;
            clicks++;
        } 
	}
	
	/**
	 * Checks if there is any click within the frame and increments while game is not over
     */
	public void updateClicks()
	{
		clicks++;
		if (clicks >= TOTAL_CLICKS)
        {
            gameOver = true; // End the game
        }
	}
	
	/**
	 * Moves stitchButton to random places throughout screen 
     */
	public void moveStitchButtonRandomly() 
	{		
		int frameWidth = 450;
	    int frameHeight = 300;
	    
	    // minimum and maximum x and y coordinates 
	    int minX = 74;
	    int maxX = frameWidth - stitchButton.getWidth() - minX;
	    int minY = 74;
	    int maxY = frameHeight - stitchButton.getHeight() - minY;
	    
	    // keeps x and y bounds within the frame
	    int newX = random.nextInt(maxX);
	    int newY = random.nextInt(maxY);
	    
	    // prevents stitchButton from going out of frame
	    if (newX < FRAME_BOUNDS)
	    {
	    	newX = FRAME_BOUNDS;
	    }
	    if (newY < FRAME_BOUNDS)
	    {
	    	newY = FRAME_BOUNDS;
	    }
	    
	    stitchButton.setBounds(newX, newY, stitchButton.getWidth(), stitchButton.getHeight());
	    System.out.println("Coords: " + newX + " " + newY + " " + frameWidth + " " + frameHeight);
	}
	
	/**
	 * Notifies player of the amount of money made off of the minigame, displays in popup
	 * @return Total Money player wins
     */
	public int notifyPlayer() 
	{   
		totalMoney = 5 * stitchesMade;
		JOptionPane.showMessageDialog(this, "Congratulations. You've made $" + totalMoney + " dollars!", "Game Over", JOptionPane.INFORMATION_MESSAGE);     
        return totalMoney;
    }
	
	/**
	 * Gets the current status of the game to see if not over
	 */
	public boolean getGameStatus()
	{
		return gameOver;
	}
}
