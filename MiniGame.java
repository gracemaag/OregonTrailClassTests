package miniGame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiniGame {

	private MiniGameMechanics mechanics; // initializing class
	
	private JFrame sewingFrame;
	private JButton stitchButton; 
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiniGame window = new MiniGame();
					window.sewingFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MiniGame() {
		initialize();
		mechanics = new MiniGameMechanics(stitchButton);
		mechanics.moveStitchButtonRandomly(); // Start moving the button
		
		sewingFrame.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            int x = e.getX();
	            int y = e.getY();
	            System.out.println("GOC");
	            mechanics.updateClicks();
	            if (mechanics.isClickInsideButton(x, y)) {
	                mechanics.updateStitches();
	            } 
	            else 
	            {
	                mechanics.moveStitchButtonRandomly();
	            }
	        }
	    });
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		sewingFrame = new JFrame();
		sewingFrame.getContentPane().setBackground(new Color(64, 0, 0));
		sewingFrame.setBounds(100, 100, 440, 300);
		sewingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sewingFrame.getContentPane().setLayout(null);
		sewingFrame.setResizable(false);
		
		stitchButton = new JButton("");
		stitchButton.setBackground(new Color(255, 255, 255));
		stitchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				mechanics.updateStitches(); // Update stitches on click
				mechanics.moveStitchButtonRandomly();
			}
		});
		stitchButton.setBounds(171, 104, 74, 3);
		sewingFrame.getContentPane().add(stitchButton);
		stitchButton.setBorderPainted(false);
		
		// Initialize stitch fields with manual positioning
	    int fieldWidth = 25; // Width of each text field
	    int gap = 10; // Gap between each text field
	    int rowHeight = 9; // Height of each text field
	    int numberOfRows = 1; // Number of rows for top and bottom lines
	    int numberOfColumns = 12; // Number of columns for each line

	    int xOffset = 10; // Starting X offset for the first line
	    int yOffset = 11; // Starting Y position for the top row
	    for (int i = 0; i < numberOfRows; i++) {
	        for (int j = 0; j < numberOfColumns; j++) {
	            JTextField textField = new JTextField();
	            textField.setEnabled(false);
	            textField.setEditable(false);
	            textField.setColumns(10);
	            textField.setBounds(xOffset + j * (fieldWidth + gap), yOffset, fieldWidth, rowHeight);
	            sewingFrame.getContentPane().add(textField);
	        }
	        yOffset += rowHeight + gap; // Move to the next row
	    }

	    // Adjust the Y position for the bottom row if needed
	    yOffset = 245; // Starting Y position for the bottom row
	    xOffset = 10; // Starting X offset for the second line to move it over by 10 pixels
	    for (int j = 0; j < numberOfColumns; j++) {
	        JTextField rectangle = new JTextField();
	        rectangle.setEnabled(false);
	        rectangle.setEditable(false);
	        rectangle.setColumns(10);
	        rectangle.setBounds(xOffset + j * (fieldWidth + gap), yOffset, fieldWidth, rowHeight);
	        sewingFrame.getContentPane().add(rectangle);
	    }
	}
}