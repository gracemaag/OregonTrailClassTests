package miniGame;

import javax.swing.*;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiniGameMechanics extends javax.swing.JFrame
{
	// constants for stitch count 
    
    // initializing variables
	
    // private int stitches = 0;
    private int stitchesMade = 0;
    // private int clothes = 0; 
    private boolean gameOver = false;
    private Random random = new Random();
    private JButton stitchButton;
    private int clicks = 0;
    private int clicksOnButton = 0; // Track the number of successful clicks
    
    public boolean isClickInsideButton(int x, int y) 
    {
        return x >= stitchButton.getX() && x <= stitchButton.getX() + stitchButton.getWidth() &&
               y >= stitchButton.getY() && y <= stitchButton.getY() + stitchButton.getHeight();
    }
    
    public MiniGameMechanics(JButton stitchButton) 
    {
        setFocusable(true); // Necessary for events to work
        setSize(450, 300); // Set the size of the game window
        setLocationRelativeTo(null); // Place the window
        this.stitchButton = stitchButton;
        
        /*addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
               clicksOnButton++;
               System.out.println("COB:" + clicksOnButton);
            }
        });*/
    }

	public void updateStitches() 
	{
		clicksOnButton++; // Increment the clicks on button counter
        if (!gameOver) 
        {
            stitchesMade++; // Increment stitchesMade
            clicks++;
        } 
	}
	
	public void updateClicks()
	{
		clicks++;
		if (clicks >= 10)
        {
            gameOver = true; // End the game
            notifyPlayer(); // Notify the player
        }
	}
	
	public void moveStitchButtonRandomly() 
	{		
		int frameWidth = this.getWidth();
	    int frameHeight = this.getHeight();
	    
	    int minX = 74; // Minimum X coordinate, adjust based on layout
	    int maxX = frameWidth - stitchButton.getWidth() - minX; // Maximum X coordinate, adjust based on layout
	    int minY = 74; // Minimum Y coordinate, adjust based on layout
	    int maxY = frameHeight - stitchButton.getHeight() - minY;
	    
	    int newX = random.nextInt(maxX); // Ensure newX is within minX and maxX
	    int newY = random.nextInt(maxY); // Ensure newY is within minY and maxY
	    if (newX < 50)
	    {
	    	newX = 50;
	    }
	    if (newY < 50)
	    {
	    	newY = 50;
	    }
	    
	    stitchButton.setBounds(newX, newY, stitchButton.getWidth(), stitchButton.getHeight());
	    System.out.println("Coords: " + newX + " " + newY + " " + frameWidth + " " + frameHeight);
	}
	
	public void notifyPlayer() 
	{   
		System.out.println("DONE"); // Print to console
        JOptionPane.showMessageDialog(this, "Congratulations. You've made " + stitchesMade + " pieces of clothing!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
