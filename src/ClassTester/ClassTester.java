package ClassTester;

import java.awt.EventQueue;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClassTester {

	
	//this is just for showing how the classes work and how they could be implemented
	
	
	private JFrame frame;
	Location game = new Location();
	Scanner in = new Scanner(System.in);
	private JTextField restTF;
	private JTextField changeSpeedTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassTester window = new ClassTester();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClassTester() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel distanceTraveledLbl = new JLabel("Total Distance Travelled: ");
		distanceTraveledLbl.setBounds(41, 25, 144, 24);
		frame.getContentPane().add(distanceTraveledLbl);
		
		JLabel distanceTraveledEditLbl = new JLabel("0");
		distanceTraveledEditLbl.setBounds(216, 30, 46, 14);
		frame.getContentPane().add(distanceTraveledEditLbl);
		
		JLabel distanceToLandmarkLbl = new JLabel("Distance to Next Landmark:");
		distanceToLandmarkLbl.setBounds(41, 60, 181, 24);
		frame.getContentPane().add(distanceToLandmarkLbl);
		
		JLabel distanceToLandmarkEditLbl = new JLabel("" + game.getDistanceToNext());
		distanceToLandmarkEditLbl.setBounds(216, 65, 46, 14);
		frame.getContentPane().add(distanceToLandmarkEditLbl);
		
		JLabel dateEditLbl = new JLabel(game.DateString());
		dateEditLbl.setBounds(152, 213, 217, 14);
		frame.getContentPane().add(dateEditLbl);
		
		JButton travelBtn = new JButton("Travel");
		travelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.travel();
				distanceTraveledEditLbl.setText("" + game.getTotalTraveled());
				distanceToLandmarkEditLbl.setText("" + game.getDistanceToNext());
				dateEditLbl.setText(game.DateString());	//food consumption here
			}
		});
		
		travelBtn.setBounds(41, 165, 89, 23);
		frame.getContentPane().add(travelBtn);
		
		restTF = new JTextField();
		restTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String restDaysStr = restTF.getText();
				int restDays = Integer.parseInt(restDaysStr);
				game.rest(restDays);
				dateEditLbl.setText(game.DateString());		//food consumption also has to go here probs
				
			}
		});
		restTF.setText("0");
		restTF.setBounds(176, 166, 86, 20);
		frame.getContentPane().add(restTF);
		restTF.setColumns(10);
		
		JLabel restLbl = new JLabel("rest?");
		restLbl.setBounds(176, 141, 46, 14);
		frame.getContentPane().add(restLbl);
		
		changeSpeedTF = new JTextField();
		changeSpeedTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String speedStr = changeSpeedTF.getText();
				int speed = Integer.parseInt(speedStr);
				game.setSpeed(speed);
			}			
		});
		
		changeSpeedTF.setText("12");
		changeSpeedTF.setBounds(295, 166, 86, 20);
		frame.getContentPane().add(changeSpeedTF);
		changeSpeedTF.setColumns(10);
		
		JLabel changeSpeedLbl = new JLabel("change speed?");
		changeSpeedLbl.setBounds(295, 141, 111, 14);
		frame.getContentPane().add(changeSpeedLbl);
		
		JLabel dateLbl = new JLabel("Today's Date: ");
		dateLbl.setBounds(41, 213, 101, 14);
		frame.getContentPane().add(dateLbl);
		
	}
}
