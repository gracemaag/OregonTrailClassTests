package OregonTrailMVP;

/**
 * Windlass Hill Oregon Trail MVP
 * @author Grace Maag, Kaitlyn Reaser, and Savannah Larsen
 * @filename OregonTrailMVP.java
 * @version 1.0 9 April 2024
 * 
 * Oregon Trail Minimum Viable Product(MVP) contains the basics for the game "The Oregon Trail."
 * This product includes a naming the characters, buying vital items from the game's store,
 * a date system that runs on a clock, a rest function, distances traveled, rations, speed of 
 * the wagon, food left in wagon, trading, landmarks and forts, and images of different places 
 * along the trail.   
 * 
 */
import java.awt.EventQueue;
import java.util.Scanner;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.JRadioButton;
import javax.swing.JInternalFrame;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class OregonTrailMVP {
	
	//Initialize objects and object ArrayLists
	Location game = new Location();
	Landmark landmark = new Landmark();
	WagonMember wagonLeader = new WagonMember("");
	WagonMember character2 = new WagonMember("");
	WagonMember character3 = new WagonMember("");
	WagonMember character4 = new WagonMember("");
	WagonMember character5 = new WagonMember("");
	Timer clock;
	
	// Array Lists for keeping track of wagon members, items, and amounts 
	ArrayList<WagonMember> characterList = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	ArrayList<Item> itemContents = new ArrayList<>();

	// Initializing item objects and their weights
	Item wagonWheel = new Item(10);
	Item wagonAxle = new Item(5);
	Item clothing = new Item(2);
	Food food = new Food(1);
	Item water = new Item(7);
	
	//Implement some of the JFrame components 
	JFrame frame;
	JTextField restTF;
	JLabel dateEditLbl= new JLabel("");
	JLabel distanceTraveledEditLbl = new JLabel("0");
	JLabel distanceToLandmarkEditLbl = new JLabel("102");
	JLabel restLbl = new JLabel("Days to rest:");
	JButton controlBtn = new JButton("Travel");
	JButton lookAroundBtn = new JButton("Look Around");
	JInternalFrame buyFrame = new JInternalFrame("Buy Frame");
	JButton buyBtn = new JButton("Buy");
	JTextArea outputTextArea = new JTextArea();
	JLabel imageLbl = new JLabel("");
	JButton tradeBtn = new JButton("Trade");
	
	JTextField wagonLeaderTF;
	JTextField character2TF;
	JTextField character3TF;
	JTextField character4TF;
	JTextField character5TF;
	
	ButtonGroup rationsGroup = new ButtonGroup();
	JRadioButton bareBonesBtn = new JRadioButton("Bare Bones");
	JRadioButton meagerBtn = new JRadioButton("Meager");
	JRadioButton fillingBtn = new JRadioButton("Filling");
	JLabel foodInWagonEditLbl = new JLabel("");
	
	JTextField kindField;
	JTextField amountField;
	
	ImageIcon trailImage = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\trailImage.jpg");
	ImageIcon mapImage = new ImageIcon("C:\\Users\\gemaa\\Downloads\\images.jpeg");
	
	int gameState = 1; // 1 for pause game, 2 for resume game.
	int restCount = 0;
	int itemKind = 0;
	int itemAmount = 0;
	int totalWagonWeight = 0;
	int currentDistanceFrom = 102;
	
			
	/**
	 * Launch the application.
	 */	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					OregonTrailMVP window = new OregonTrailMVP();
					window.frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public OregonTrailMVP()
	{
		outputTextArea.setWrapStyleWord(true);
		outputTextArea.setText("You are beginning your journey in " + 
				landmark.getLandmarkName() + ".  ");
		clock = new javax.swing.Timer(2500, new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) 
			{
				
				/**
				 *	checks to see if the wagon should be currently resting or at a landmark
				 *	and updates wagon contents and distances relating to the current
				 *	state of rest or travel
				 */
				if(game.getDistanceToNext() == 0 && game.getTotalTraveled() != 0 ) 
				{
					clock.stop();
					controlBtn.setText("Resume game");
					gameState = 1;
					lookAroundBtn.setEnabled(true);
					currentDistanceFrom = landmark.distanceFrom();
					game.setDistanceToNext(currentDistanceFrom);
					System.out.println("distance to:" + currentDistanceFrom);
					outputTextArea.setText("You have arrived at " + landmark.getLandmarkName() 
							+ ". What would you like to do? ");
					if(landmark.isFort()) {
						buyBtn.setEnabled(true);
					}
					else {
						buyBtn.setEnabled(false);
					}
					landmark.landmarkPassed();
				}
				else if(landmark.getIndex() == 0)
				{
					landmark.landmarkPassed();
					buyBtn.setEnabled(false);
				}
				else if(restCount > 0) 
				{
					buyBtn.setEnabled(false);
					lookAroundBtn.setEnabled(false);
					game.rest();
					dateEditLbl.setText(game.DateString());
					restCount--;
					outputTextArea.setText("Currently resting for "+ restCount + " more days");
					itemAmounts.set(3, itemAmounts.get(3) - Food.rationsAmount);
					imageLbl.setIcon(trailImage);
					foodInWagonEditLbl.setText("" + itemAmounts.get(3));
					distanceTraveledEditLbl.setText("" + game.getTotalTraveled());
					distanceToLandmarkEditLbl.setText("" + game.getDistanceToNext());
				}
				else 
				{
					buyBtn.setEnabled(false);
					lookAroundBtn.setEnabled(false);
					game.travel();
					dateEditLbl.setText(game.DateString());
					restLbl.setText("Days to Rest: ");
					outputTextArea.setText("Currently traveling the trail");
					itemAmounts.set(3, itemAmounts.get(3) - Food.rationsAmount);
					imageLbl.setIcon(trailImage);
					foodInWagonEditLbl.setText("" + itemAmounts.get(3));
					distanceTraveledEditLbl.setText("" + game.getTotalTraveled());
					distanceToLandmarkEditLbl.setText("" + game.getDistanceToNext());
				}
				
				/**
				 * checks to see if there is enough food in the wagon
				 * closes the program and gives a message to the user if food reaches 0
				 */
				if(itemAmounts.get(3) <= 0)
				{
					JOptionPane.showMessageDialog(null, "Game over... make sure to pack "
							+ "enough food next time.");
					frame.dispose();	
				}
			}
		});
		initialize();	
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// Add each wagon member to the character array list
		characterList.add(wagonLeader);
		characterList.add(character2);
		characterList.add(character3);
		characterList.add(character4);
		characterList.add(character5);
		
		rationsGroup.add(bareBonesBtn);
		rationsGroup.add(meagerBtn);
		rationsGroup.add(fillingBtn);
		
		// Each has a corresponding index to access its amount
		itemContents.add(wagonWheel); // 0 - Wagon Wheel
		itemContents.add(wagonAxle); // 1 - Wagon Axle
		itemContents.add(clothing); // 2 - Clothing
		itemContents.add(food); // 3 - Food
		itemContents.add(water); // 4 - Water (Not changeable right now)
		
		// Initializes the amount of each item as 0 until user buys
		itemAmounts.add(0);
		itemAmounts.add(0);
		itemAmounts.add(0);
		itemAmounts.add(0);
		itemAmounts.add(20);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 793, 899);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel distanceTraveledLbl = new JLabel("Total Distance Traveled: ");
		distanceTraveledLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		distanceTraveledLbl.setBounds(361, 532, 165, 24);
		frame.getContentPane().add(distanceTraveledLbl);
	
		distanceTraveledEditLbl.setBounds(511, 539, 46, 14);
		frame.getContentPane().add(distanceTraveledEditLbl);
		
		JLabel distanceToLandmarkLbl = new JLabel("Distance to Next Landmark:");
		distanceToLandmarkLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		distanceToLandmarkLbl.setBounds(361, 555, 181, 24);
		frame.getContentPane().add(distanceToLandmarkLbl);

		distanceToLandmarkEditLbl.setBounds(511, 562, 46, 14);
		frame.getContentPane().add(distanceToLandmarkEditLbl);
		
		dateEditLbl = new JLabel(game.DateString());
		dateEditLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dateEditLbl.setBounds(511, 483, 258, 14);
		frame.getContentPane().add(dateEditLbl);
		
		controlBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		controlBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Makes default image visible
				imageLbl.setIcon(trailImage);
				imageLbl.setVisible(true);
				
				//pauses the game
				if (Food.rationsAmount != 0)
				{
					if(gameState == 2)
					{
						clock.stop();
						controlBtn.setText("Resume game");
						gameState = 1;
					}
					//resumes the game
					else if(gameState == 1)
					{
						clock.start();
						controlBtn.setText("Pause Game");
						gameState = 2;
					}
				}
				buyFrame.setVisible(false);
			}
		});
		
		controlBtn.setBounds(104, 555, 162, 39);
		frame.getContentPane().add(controlBtn);
		
		restTF = new JTextField();
		restTF.setFont(new Font("Tahoma", Font.PLAIN, 10));
		restTF.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Get the days to be rested from user input
				String restStr = restTF.getText();
				restCount = Integer.parseInt(restStr);
				restTF.setText("");
			}
		});
		
		restTF.setBounds(662, 510, 22, 20);
		frame.getContentPane().add(restTF);
		restTF.setColumns(10);
		restLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		restLbl.setBounds(579, 512, 95, 14);
		frame.getContentPane().add(restLbl);
		
		JLabel changeSpeedLbl = new JLabel("Current Speed:");
		changeSpeedLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		changeSpeedLbl.setBounds(361, 589, 111, 14);
		frame.getContentPane().add(changeSpeedLbl);
		
		JLabel dateLbl = new JLabel("Today's Date: ");
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dateLbl.setBounds(362, 481, 152, 18);
		frame.getContentPane().add(dateLbl);	
		
		JButton acceptButton = new JButton("Accept");
		acceptButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				//  Accepts a trade offer when it is presented to the user
				Item.acceptTrade(itemAmounts);
				System.out.println(itemAmounts);
				acceptButton.setVisible(false);
				clock.stop(); // Clock stops until user has finished trading, can resume
				controlBtn.setText("Resume game");
				gameState = 1;
			}
		});
		acceptButton.setVisible(false);
		acceptButton.setBounds(258, 608, 93, 17);
		frame.getContentPane().add(acceptButton);
		
		
		tradeBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tradeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Grabs a randomized trade prompt for the user to either accept or decline
				System.out.println(itemAmounts);
				String tradePrompt = Item.getTradeMessage(itemAmounts);
				outputTextArea.setText (tradePrompt);
				clock.stop(); // Clock stops until user has decided to accept the trade
				controlBtn.setText("Resume game");
				gameState = 1;
				acceptButton.setVisible(true);
			}
		});
		tradeBtn.setBounds(123, 605, 122, 23);
		frame.getContentPane().add(tradeBtn);
		
		JLabel rationsLbl = new JLabel("Rations");
		rationsLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rationsLbl.setBounds(631, 539, 73, 14);
		frame.getContentPane().add(rationsLbl);
		bareBonesBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bareBonesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets the food rations to 'Bare Bones'
				food.changeRations(5, 1);
			}
		});
		bareBonesBtn.setBounds(544, 556, 85, 23);
		frame.getContentPane().add(bareBonesBtn);
		
		meagerBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		meagerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets the food rations to 'Meager'
				food.changeRations(5, 2);
			}
		});
		meagerBtn.setBounds(631, 556, 73, 23);
		frame.getContentPane().add(meagerBtn);
		
		fillingBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		fillingBtn.setSelected(true);
		fillingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets the food rations to 'Filling'
				food.changeRations(5, 3);
			}
		});
		fillingBtn.setBounds(708, 558, 109, 23);
		frame.getContentPane().add(fillingBtn);
		
		buyFrame.setBounds(0, 0, 777, 472);
		frame.getContentPane().add(buyFrame);
		buyFrame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html>1 - Wagon Wheel <br> 2 - Wagon Axle <br> 3 - Clothing <br> 4 - Food </html>");
		lblNewLabel.setBounds(10, 0, 160, 131);
		buyFrame.getContentPane().add(lblNewLabel);
		
		kindField = new JTextField();
		kindField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemKind = Integer.parseInt(kindField.getText());
			}
		});
		kindField.setBounds(84, 142, 86, 20);
		buyFrame.getContentPane().add(kindField);
		kindField.setColumns(10);
		
		/**
		 * Grabs the amount of an item the user wishes to purchase.
		 * Updates the wagon contents and the total wagon weight accordingly.
		 */
		amountField = new JTextField();
		amountField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemAmount = Integer.parseInt(amountField.getText());
				Item.Buy(itemContents, itemAmounts, itemAmount, itemKind - 1, totalWagonWeight);
				System.out.println(itemAmounts);
				Item tempItem = itemContents.get(itemKind - 1);
				totalWagonWeight += tempItem.getWeight() * itemAmount;
				System.out.println(totalWagonWeight);
				foodInWagonEditLbl.setText("" + itemAmounts.get(3));
			}
		});
		amountField.setBounds(84, 187, 86, 20);
		buyFrame.getContentPane().add(amountField);
		amountField.setColumns(10);
		
		JLabel kindLabel = new JLabel("Which Item?");
		kindLabel.setBounds(10, 145, 74, 14);
		buyFrame.getContentPane().add(kindLabel);
		
		JLabel amountLabel = new JLabel("Amount?");
		amountLabel.setBounds(10, 190, 74, 14);
		buyFrame.getContentPane().add(amountLabel);
		
		JButton nextBuybtn = new JButton("Next");
		nextBuybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Gets rid of the Buying frame and puts up the default traveling image
				buyFrame.setVisible(false);	
				imageLbl.setIcon(trailImage);
				imageLbl.setVisible(true);
			}
		});
		nextBuybtn.setBounds(84, 240, 89, 23);
		buyFrame.getContentPane().add(nextBuybtn);
		
		JLabel foodInWagonLbl = new JLabel("Food in Wagon:");
		foodInWagonLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		foodInWagonLbl.setBounds(361, 512, 95, 14);
		frame.getContentPane().add(foodInWagonLbl);
		
		foodInWagonEditLbl.setBounds(486, 512, 40, 14);
		frame.getContentPane().add(foodInWagonEditLbl);
		
		// User drags the slider to their desired speed, speed updated accordingly
		JSlider currentSpeedSlider = new JSlider(JSlider.HORIZONTAL,12,20,12);
		currentSpeedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					int speed = (int)source.getValue();
					game.setSpeed(speed);
					System.out.println("" + speed);
				}
			}
		});
		currentSpeedSlider.setBounds(361, 609, 200, 26);
		frame.getContentPane().add(currentSpeedSlider);
		
		JButton talkBtn = new JButton("Talk");
		talkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Shows the user a random message 
				clock.stop();
				controlBtn.setText("Resume game");
				gameState = 1;
				JOptionPane.showMessageDialog(null, landmark.talkToOption(0));							
			}
		});
		talkBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		talkBtn.setBounds(123, 639, 122, 23);
		frame.getContentPane().add(talkBtn);
		
		lookAroundBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//gets the image icon of the current location and displays it
				imageLbl.setIcon(landmark.getLandmarkIcon());
			}
		});
		lookAroundBtn.setEnabled(true);
		lookAroundBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lookAroundBtn.setBounds(123, 704, 122, 23);
		frame.getContentPane().add(lookAroundBtn);
		
		buyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//pauses the game and lets the user buy, only works at forts
				clock.stop();
				controlBtn.setText("Resume game");
				gameState = 1;
				buyFrame.setVisible(true);
			}
		});
		buyBtn.setEnabled(true);
		buyBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		buyBtn.setBounds(123, 673, 122, 23);
		frame.getContentPane().add(buyBtn);	
		
		outputTextArea.setLineWrap(true);
		outputTextArea.setEditable(false);
		outputTextArea.setBounds(76, 476, 217, 71);
		frame.getContentPane().add(outputTextArea);
		
		imageLbl.setBounds(0, 0, 777, 472);
		imageLbl.setIcon(trailImage);
		frame.getContentPane().add(imageLbl);	
		
		JButton lookAtMapLbl = new JButton("Look at Map");
		lookAtMapLbl.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Gives an image of a map of the Oregon Trail when clicked
				imageLbl.setIcon(mapImage);
				controlBtn.setText("Resume game");
				gameState = 1;
				clock.stop();
			}
		});
		lookAtMapLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lookAtMapLbl.setEnabled(true);
		lookAtMapLbl.setBounds(123, 734, 122, 23);
		frame.getContentPane().add(lookAtMapLbl);
		
		JButton lookAtSupplies = new JButton("Look At Supplies");
		lookAtSupplies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Stops the clock and shows the user how much of each item they have at the moment
				clock.stop();
				gameState = 1;
				controlBtn.setText("Resume game");
				JOptionPane.showMessageDialog(null, "Wagon Wheels: " + itemAmounts.get(0) 
				+ " | Wagon Axles: " + itemAmounts.get(1) + " | Clothes: " + itemAmounts.get(2)
				+ "  | Food: " + itemAmounts.get(3) + " | Water: " + itemAmounts.get(4));
			}
		});
		lookAtSupplies.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lookAtSupplies.setEnabled(true);
		lookAtSupplies.setBounds(255, 639, 122, 23);
		frame.getContentPane().add(lookAtSupplies);
		
		JInternalFrame namingFrame = new JInternalFrame("Name Your Character");
		namingFrame.setBounds(-8, 0, 777, 472);
		frame.getContentPane().add(namingFrame);
		namingFrame.getContentPane().setLayout(null);
		
		JLabel nameCharactersLbl = new JLabel("Name your Characters: ");
		nameCharactersLbl.setFont(new Font("Tahoma", Font.PLAIN, 24));
		nameCharactersLbl.setBounds(36, 32, 380, 29);
		namingFrame.getContentPane().add(nameCharactersLbl);
		
		JLabel wagonLeaderLbl = new JLabel("Wagon Leader:");
		wagonLeaderLbl.setBounds(36, 84, 87, 14);
		namingFrame.getContentPane().add(wagonLeaderLbl);
		
		wagonLeaderTF = new JTextField();
		wagonLeaderTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets the name of the 'Wagon Leader' as what the user inputs
				String name = wagonLeaderTF.getText();
				wagonLeader.setName(name);
			}
		});
		wagonLeaderTF.setBounds(162, 81, 86, 20);
		namingFrame.getContentPane().add(wagonLeaderTF);
		wagonLeaderTF.setColumns(10);
		
		JLabel character2Lbl = new JLabel("Character #2:");
		character2Lbl.setBounds(36, 109, 87, 14);
		namingFrame.getContentPane().add(character2Lbl);
		
		JLabel character3Lbl = new JLabel("Character #3:");
		character3Lbl.setBounds(36, 134, 87, 14);
		namingFrame.getContentPane().add(character3Lbl);
		
		character2TF = new JTextField();
		character2TF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets name of 'Character 2'
				String name = character2TF.getText();
				character2.setName(name);
			}
		});
		
		character2TF.setColumns(10);
		character2TF.setBounds(162, 106, 86, 20);
		namingFrame.getContentPane().add(character2TF);
		
		character3TF = new JTextField();
		character3TF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets name of 'Character 3'
				String name = character3TF.getText();
				character3.setName(name);
			}
		});
		
		character3TF.setColumns(10);
		character3TF.setBounds(162, 131, 86, 20);
		namingFrame.getContentPane().add(character3TF);
		
		JLabel character4Lbl = new JLabel("Character #4:");
		character4Lbl.setBounds(36, 159, 87, 14);
		namingFrame.getContentPane().add(character4Lbl);
		
		character4TF = new JTextField();
		character4TF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets name of 'Character 4'
				String name = character4TF.getText();
				character4.setName(name);
			}
		});
		
		character4TF.setColumns(10);
		character4TF.setBounds(162, 156, 86, 20);
		namingFrame.getContentPane().add(character4TF);
		
		character5TF = new JTextField();
		character5TF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets name of 'Character 5'
				String name = character5TF.getText();
				character5.setName(name);
			}
		});
		
		character5TF.setColumns(10);
		character5TF.setBounds(162, 182, 86, 20);
		namingFrame.getContentPane().add(character5TF);
		
		JLabel character5Lbl = new JLabel("Character #5:");
		character5Lbl.setBounds(36, 185, 87, 14);
		namingFrame.getContentPane().add(character5Lbl);
		
		JButton nextBtn = new JButton("Next Button");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Progresses the user from the Naming Frame to the Buying frame at the start
				namingFrame.setVisible(false);
				buyFrame.setVisible(true);
			}
		});
		nextBtn.setBounds(146, 231, 122, 23);
		namingFrame.getContentPane().add(nextBtn);
		imageLbl.setVisible(false);
		namingFrame.setVisible(true);
		buyFrame.setVisible(false);
	}
}