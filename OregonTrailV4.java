package OregonTrailV4;

/**
 * Windlass Hill Oregon Trail 
 * @author Grace Maag, Kaitlyn Reaser, and Savannah Larsen
 * @filename OregonTrailV4.java
 * @version 1.4 29 April 2024
 * 
 * Oregon Trail Version 4 contains the game "The Oregon Trail." Some elements of the game's
 * story comes from the Diarest Anna Maria King, a woman who traveled the trail in 1845 with 
 * her husband and her young brother-in-law. 
 * This product includes a naming the characters, buying vital items from the game's store,
 * a date system that runs on a clock, a rest function, distances traveled, rations, speed of 
 * the wagon, food left in wagon, trading, landmarks and forts, weather, wagon member health, 
 * and images of different places along the trail.    
 * 
 */
import java.awt.EventQueue;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JInternalFrame;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;


public class OregonTrailV4 {
	
	//Initialize objects and object ArrayLists
	Location game = new Location();
	Landmark landmark = new Landmark();
	River river = new River();
	Weather weather = new Weather();
	WagonMember wagonLeader = new WagonMember("", false);
	WagonMember character2 = new WagonMember("", false);
	WagonMember character3 = new WagonMember("", false);
	WagonMember character4 = new WagonMember("", false);
	WagonMember character5 = new WagonMember("", false);
	String outputWeather = ("Sunny Skies");
	Timer clock;
	
	// Array Lists for keeping track of wagon members, items, and amounts 
	ArrayList<WagonMember> members = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	ArrayList<Item> itemContents = new ArrayList<>();

	// Initializing item objects, their weights, and their prices
	Item wagonWheel = new Item(10, 10);
	Item wagonAxle = new Item(5 , 10);
	Item clothing = new Item(2 , 10);
	Food food = new Food(1, .20);
	Item water = new Item(7,25);	
	Item oxen = new Item(0,20);
	
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
	JButton rejectButton = new JButton("Reject");
	JLabel choiceLbl = new JLabel("Choice:");
	JButton lookAtMapBtn = new JButton("Look at Map");
	JButton talkBtn = new JButton("Talk");
	JLabel licenseLbl = new JLabel("");
	JLabel tempLbl = new JLabel("Cool");
	JLabel weatherLbl = new JLabel("Sunny Skies");
	JTextField choiceTF;
	JTextField character2TF;
	JTextField character3TF;
	JTextField character4TF;
	JTextField character5TF;
	
	ButtonGroup rationsGroup = new ButtonGroup();
	JRadioButton bareBonesBtn = new JRadioButton("Bare Bones");
	JRadioButton meagerBtn = new JRadioButton("Meager");
	JRadioButton fillingBtn = new JRadioButton("Filling");
	JLabel foodInWagonEditLbl = new JLabel("");
	JLabel healthChangeLabel = new JLabel("");
	
	JTextField kindField;
	JTextField amountField;
	
	ImageIcon trailImage = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\trailImage.jpg");
	ImageIcon mapImage = new ImageIcon("C:\\Users\\gemaa\\Downloads\\images.jpeg");
	
	int gameState = 1; // 1 for pause game, 2 for resume game.
	int restCount = 0; // Amount of days left to rest
	int itemKind = 0; 
	int itemAmount = 0;
	int totalWagonWeight = 0;
	int totalMoney = 800;
	int currentDistanceFrom = 102;
	int livingMems = 4;
	int rationsValue = 3;
			
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
					OregonTrailV4 window = new OregonTrailV4();
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
	
	public OregonTrailV4()
	{
		outputTextArea.setForeground(new Color(0, 128, 0));
		outputTextArea.setBackground(new Color(0, 0, 0));
		outputTextArea.setWrapStyleWord(true);
		outputTextArea.setText("You are beginning your journey in " + 
			landmark.getLandmarkName() + ".  ");
		clock = new javax.swing.Timer(2500, new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) 
			{
				
				int oxenChance = Oxen.injuryChance(itemAmounts);
				/**
				 *	checks to see if the wagon should be currently resting or at a landmark
				 *	and updates wagon contents and distances relating to the current
				 *	state of rest or travel
				 */
				if(game.getDistanceToNext() == 0 && game.getTotalTraveled() != 0 ) 
				{
					/**
					 * The player is currently at a landmark. The game pauses for the user
					 * to choose what to do. Enables some buttons
					 */
					clock.stop();
					controlBtn.setText("Resume game");
					gameState = 1;
					talkBtn.setEnabled(true);
					lookAroundBtn.setEnabled(true);
					currentDistanceFrom = landmark.distanceFrom();
					game.setDistanceToNext(currentDistanceFrom);
					outputTextArea.setText("You have arrived at " + landmark.getLandmarkName() 
							+ ". What would you like to do? ");
					if(landmark.isFort()) {
						buyBtn.setEnabled(true);
					}
					else {
						buyBtn.setEnabled(false);
					}
					if(landmark.isRiver()) 
					{
						controlBtn.setEnabled(false);
						outputTextArea.setText(river.displayRiverStats(weather.getWater()));
						choiceLbl.setVisible(true);
						choiceTF.setVisible(true);
					}
					
					landmark.landmarkPassed();
				}
				else if(landmark.getIndex() == 0)
				{
					/**
					 * Player is starting the game - keeps the game from getting stuck at the beginning
					 */
					landmark.landmarkPassed();
					distanceTraveledEditLbl.setText("" + game.getTotalTraveled());
					buyBtn.setEnabled(false);
				}
				else if(restCount > 0 || oxenChance == 1) 
				{
					/**
					 * Player is currently resting - decrements the counter each day for rest
					 * and changes all values (health, date, and food) accordingly.
					 */
					buyBtn.setEnabled(false);
					lookAroundBtn.setEnabled(false);
					talkBtn.setEnabled(false);
					game.rest();
					dateEditLbl.setText(game.dateString());
					restCount--;
					if (oxenChance == 1)
					{
						outputTextArea.setText("You have no oxen. The wagon is currently stopped "
								+ "until you get more.");
					}
					else if (restCount > 0)
					{
						outputTextArea.setText("Currently resting for "+ restCount + " more days");
					}
					itemAmounts.set(3, itemAmounts.get(3) - Food.rationsAmount);
					imageLbl.setIcon(trailImage);
					foodInWagonEditLbl.setText("" + itemAmounts.get(3));
					distanceTraveledEditLbl.setText("" + game.getTotalTraveled());
					distanceToLandmarkEditLbl.setText("" + game.getDistanceToNext());
					WagonMember.restingState();
					oxenChance = Oxen.injuryChance(itemAmounts);
				}
				else 
				{
					/**
					 * Player is currently traveling. All values for distances, health, date, and food
					 * are changed accordingly.
					 */
					buyBtn.setEnabled(false);
					lookAroundBtn.setEnabled(false);
					talkBtn.setEnabled(false);
					game.travel();
					dateEditLbl.setText(game.dateString());
					restLbl.setText("Days to Rest: ");
					outputTextArea.setText("Currently traveling to " + landmark.getLandmarkName());
					itemAmounts.set(3, itemAmounts.get(3) - Food.rationsAmount);
					imageLbl.setIcon(trailImage);
					foodInWagonEditLbl.setText("" + itemAmounts.get(3));
					distanceTraveledEditLbl.setText("" + game.getTotalTraveled());
					distanceToLandmarkEditLbl.setText("" + game.getDistanceToNext());
					
					int paceAmount = game.getSpeed();
					int weatherValue = weather.getCurrentTemperature(game.getMonth(), game.getTotalTraveled());
					WagonMember.environmentalDecrement(rationsValue, weatherValue, itemAmounts, livingMems, paceAmount);

					if (oxenChance == 2) {JOptionPane.showMessageDialog(null, "An Oxen has died.");}
					if (oxenChance == 3) {JOptionPane.showMessageDialog(null, "An Oxen is sick.");}
					
					if (livingMems != 0)
					{
						WagonMember.randomHealthEvent();
						int sickMember = WagonMember.illnessProb(members, livingMems);
						if (sickMember != -1 && sickMember != -2)
						{
							String sickName = members.get(sickMember).memberName;
							if ((members.get(sickMember)).hasIllness == true)
							{
								members.remove(sickMember);
								livingMems--;
								JOptionPane.showMessageDialog(null, sickName + " has died.");
							}
							else 
							{
								String illness = WagonMember.getIllness();
								(members.get(sickMember)).hasIllness = true;
								JOptionPane.showMessageDialog(null, sickName + " is sick with " + illness);
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "All of your members are dead. Game over.");
					}
					
					if (WagonMember.memberHealth >= 140)
					{
						JOptionPane.showMessageDialog(null, "Health conditions worsened to a point where all members have died. Game over.");
					}
					
					int ifBetter = WagonMember.isBetter(members,  livingMems);
					if (ifBetter != -1) 
					{
						String betterName =members.get(ifBetter).memberName;
						JOptionPane.showMessageDialog(null, betterName + " is better!");
					}
				}
				System.out.println("Member Health: " + WagonMember.memberHealth);
				healthChangeLabel.setText(WagonMember.getHealthDescript());
				
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
				/*
				 * Changes the weather each day
				 */
				outputWeather = weather.weatherRandomEvent(game.getTotalTraveled());
				if(outputWeather.contains("Thunder") || outputWeather.contains("Blizzard")) 
				{
					outputTextArea.setText(outputWeather);
					if(outputWeather.contains("Thunder"))	weatherLbl.setText("Thunderstorm");	
					else weatherLbl.setText("Blizzard");
				}
				else  weatherLbl.setText(outputWeather); 
				System.out.println(outputWeather);
				tempLbl.setText(""+ weather.getTempStr(weather.getCurrentTemperature(game.getMonth(), game.getTotalTraveled())));
				licenseLbl.setText("");
			}
		});
		initialize();	
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// Add each wagon member to the character array list aside from Wagon Leader
		members.add(character2);
		members.add(character3);
		members.add(character4);
		members.add(character5);
		
		// create the radio button group for rations
		rationsGroup.add(bareBonesBtn);
		rationsGroup.add(meagerBtn);
		rationsGroup.add(fillingBtn);
		
		// Each has a corresponding index to access its amount
		itemContents.add(wagonWheel); // 0 - Wagon Wheel
		itemContents.add(wagonAxle); // 1 - Wagon Axle
		itemContents.add(clothing); // 2 - Clothing
		itemContents.add(food); // 3 - Food
		itemContents.add(oxen); // 4 - Oxen
		itemContents.add(water); // 5 - Water (Not changeable right now)
		
		// Initializes the amount of each item as 0 until user buys
		itemAmounts.add(0);
		itemAmounts.add(0);
		itemAmounts.add(0);
		itemAmounts.add(0);
		itemAmounts.add(0);
		itemAmounts.add(20);
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 793, 899);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel distanceTraveledLbl = new JLabel("Total Distance Traveled: ");
		distanceTraveledLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		distanceTraveledLbl.setBounds(361, 532, 165, 24);
		frame.getContentPane().add(distanceTraveledLbl);
		distanceTraveledEditLbl.setForeground(new Color(0, 128, 0));
		distanceTraveledEditLbl.setFont(new Font("Perpetua", Font.BOLD, 11));
	
		distanceTraveledEditLbl.setBounds(511, 539, 46, 14);
		frame.getContentPane().add(distanceTraveledEditLbl);
		
		JLabel distanceToLandmarkLbl = new JLabel("Distance to Next Landmark:");
		distanceToLandmarkLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		distanceToLandmarkLbl.setBounds(361, 555, 152, 24);
		frame.getContentPane().add(distanceToLandmarkLbl);
		distanceToLandmarkEditLbl.setFont(new Font("Perpetua", Font.BOLD, 11));
		distanceToLandmarkEditLbl.setForeground(new Color(0, 128, 0));

		distanceToLandmarkEditLbl.setBounds(511, 562, 46, 14);
		frame.getContentPane().add(distanceToLandmarkEditLbl);
		
		dateEditLbl = new JLabel(game.dateString());
		dateEditLbl.setFont(new Font("Perpetua", Font.BOLD, 16));
		dateEditLbl.setBounds(511, 483, 258, 14);
		frame.getContentPane().add(dateEditLbl);
		controlBtn.setForeground(new Color(255, 255, 255));
		controlBtn.setBackground(new Color(0, 0, 0));
		controlBtn.setEnabled(false);
		
		controlBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//controls the state of the game - "paused" or "resumed"
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
				else 
				{
					JOptionPane.showMessageDialog(null, "Don't forget to pack food.");
				}
				buyFrame.setVisible(false);
			}
		});
		
		controlBtn.setBounds(48, 589, 162, 39);
		frame.getContentPane().add(controlBtn);
		
		restTF = new JTextField();
		restTF.setForeground(new Color(0, 128, 0));
		restTF.setBackground(new Color(0, 0, 0));
		restTF.setFont(new Font("Perpetua", Font.BOLD, 10));
		// allows the user to rest
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
		restLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		
		restLbl.setBounds(579, 512, 95, 14);
		frame.getContentPane().add(restLbl);
		
		JLabel changeSpeedLbl = new JLabel("Current Speed:");
		changeSpeedLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		changeSpeedLbl.setBounds(626, 589, 111, 14);
		frame.getContentPane().add(changeSpeedLbl);
		
		JLabel dateLbl = new JLabel("Today's Date: ");
		dateLbl.setForeground(new Color(0, 0, 0));
		dateLbl.setBackground(new Color(0, 0, 0));
		dateLbl.setFont(new Font("Perpetua", Font.BOLD, 16));
		dateLbl.setBounds(362, 481, 152, 18);
		frame.getContentPane().add(dateLbl);	
		
		JButton acceptButton = new JButton("Accept");
		acceptButton.setForeground(new Color(255, 255, 255));
		acceptButton.setBackground(new Color(0, 0, 0));
		acceptButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		// allows the user to accept a trade
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				//  Accepts a trade offer when it is presented to the user
				Item.acceptTrade(itemAmounts);
				System.out.println(itemAmounts);
				acceptButton.setVisible(false);
				rejectButton.setVisible(false);
				clock.start(); // Clock stops until user has finished trading, can resume
				controlBtn.setText("Pause game");
				gameState = 2;
			}
		});
		acceptButton.setVisible(false);
		acceptButton.setBounds(20, 734, 93, 17);
		frame.getContentPane().add(acceptButton);
		rejectButton.setForeground(new Color(255, 255, 255));
		rejectButton.setBackground(new Color(0, 0, 0));
		
		rejectButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		// rejects a trade
		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				// rejects a trade offer when it is presented to the user
				acceptButton.setVisible(false);
				rejectButton.setVisible(false);
				clock.start(); // Clock stops until user has finished trading, can resume
				controlBtn.setText("Pause game");
				gameState = 2;
			}
		});
		acceptButton.setVisible(false);
		rejectButton.setVisible(false);
		rejectButton.setBounds(20, 754, 93, 17);
		frame.getContentPane().add(rejectButton);
		tradeBtn.setForeground(new Color(255, 255, 255));
		tradeBtn.setBackground(new Color(0, 0, 0));
		tradeBtn.setEnabled(false);
		
		tradeBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		// allows the user to trade
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
				rejectButton.setVisible(true);
			}
		});
		tradeBtn.setBounds(10, 704, 122, 23);
		frame.getContentPane().add(tradeBtn);
		
		JLabel rationsLbl = new JLabel("Rations");
		rationsLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		rationsLbl.setBounds(631, 539, 73, 14);
		frame.getContentPane().add(rationsLbl);
		bareBonesBtn.setForeground(new Color(0, 128, 0));
		bareBonesBtn.setBackground(new Color(255, 255, 255));
		bareBonesBtn.setFont(new Font("Perpetua", Font.BOLD, 10));
		bareBonesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets the food rations to 'Bare Bones'
				rationsValue = 1;
				food.changeRations(5, rationsValue);
			}
		});
		bareBonesBtn.setBounds(544, 556, 85, 23);
		frame.getContentPane().add(bareBonesBtn);
		meagerBtn.setForeground(new Color(0, 128, 0));
		meagerBtn.setBackground(new Color(255, 255, 255));
		
		meagerBtn.setFont(new Font("Perpetua", Font.BOLD, 10));
		meagerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets the food rations to 'Meager'
				rationsValue = 2;
				food.changeRations(5, rationsValue);
			}
		});
		meagerBtn.setBounds(631, 556, 73, 23);
		frame.getContentPane().add(meagerBtn);
		fillingBtn.setForeground(new Color(0, 128, 0));
		fillingBtn.setBackground(new Color(255, 255, 255));
		
		fillingBtn.setFont(new Font("Perpetua", Font.BOLD, 10));
		fillingBtn.setSelected(true);
		fillingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets the food rations to 'Filling'
				rationsValue = 3;
				food.changeRations(5, rationsValue);
			}
		});
		fillingBtn.setBounds(708, 558, 109, 23);
		frame.getContentPane().add(fillingBtn);
		
		/**
		 * Grabs the amount of an item the user wishes to purchase.
		 * Updates the wagon contents and the total wagon weight accordingly.
		 */
		
		JLabel foodInWagonLbl = new JLabel("Food in Wagon:");
		foodInWagonLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		foodInWagonLbl.setBounds(361, 589, 95, 14);
		frame.getContentPane().add(foodInWagonLbl);
		foodInWagonEditLbl.setForeground(new Color(0, 128, 0));
		foodInWagonEditLbl.setFont(new Font("Perpetua", Font.BOLD, 11));
		
		foodInWagonEditLbl.setBounds(502, 590, 40, 14);
		frame.getContentPane().add(foodInWagonEditLbl);
		
		// User drags the slider to their desired speed, speed updated accordingly
		JSlider currentSpeedSlider = new JSlider(JSlider.HORIZONTAL,12,20,12);
		currentSpeedSlider.setForeground(new Color(0, 128, 0));
		currentSpeedSlider.setBackground(new Color(255, 255, 255));
		currentSpeedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					int speed = (int)source.getValue();
					game.setSpeed(speed);
				}
			}
		});
		currentSpeedSlider.setBounds(555, 615, 200, 26);
		frame.getContentPane().add(currentSpeedSlider);
		talkBtn.setForeground(new Color(255, 255, 255));
		talkBtn.setBackground(new Color(0, 0, 0));
		
		talkBtn.setEnabled(false);
		talkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Shows the user a message relating to the current dialog
				clock.stop();
				controlBtn.setText("Resume game");
				gameState = 1;
				JOptionPane.showMessageDialog(null, landmark.getLandmarkDialog());									
			}
		});
		talkBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		talkBtn.setBounds(138, 639, 122, 23);
		frame.getContentPane().add(talkBtn);
	
		licenseLbl.setBounds(0, 813, 769, 26);
		frame.getContentPane().add(licenseLbl);
		lookAroundBtn.setForeground(new Color(255, 255, 255));
		lookAroundBtn.setBackground(new Color(0, 0, 0));
		
		lookAroundBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//gets the image icon of the current location and displays it
				//and the licensing
				imageLbl.setIcon(landmark.getLandmarkIcon());
				licenseLbl.setText(landmark.getLicense());
			}
		});
		lookAroundBtn.setEnabled(false);
		lookAroundBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lookAroundBtn.setBounds(138, 704, 122, 23);
		frame.getContentPane().add(lookAroundBtn);
		buyBtn.setForeground(new Color(255, 255, 255));
		buyBtn.setBackground(new Color(0, 0, 0));
		
		buyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//pauses the game and lets the user buy, only works at forts
				clock.stop();
				controlBtn.setText("Resume game");
				gameState = 1;
				buyFrame.setVisible(true);
			}
		});
		buyBtn.setEnabled(false);
		buyBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		buyBtn.setBounds(138, 673, 122, 23);
		frame.getContentPane().add(buyBtn);	
		
		outputTextArea.setLineWrap(true);
		outputTextArea.setEditable(false);
		outputTextArea.setBounds(10, 476, 330, 103);
		frame.getContentPane().add(outputTextArea);
		lookAtMapBtn.setForeground(new Color(255, 255, 255));
		lookAtMapBtn.setBackground(new Color(0, 0, 0));
		
		lookAtMapBtn.addActionListener(new ActionListener() 
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
		lookAtMapBtn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lookAtMapBtn.setEnabled(false);
		lookAtMapBtn.setBounds(10, 639, 122, 23);
		frame.getContentPane().add(lookAtMapBtn);
		
		JButton lookAtSupplies = new JButton("Look At Supplies");
		lookAtSupplies.setForeground(new Color(255, 255, 255));
		lookAtSupplies.setBackground(new Color(0, 0, 0));
		lookAtSupplies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Stops the clock and shows the user how much of each item they have at the moment
				clock.stop();
				gameState = 1;
				controlBtn.setText("Resume game");
				JOptionPane.showMessageDialog(null, "Wagon Wheels: " + itemAmounts.get(0) 
				+ " | Wagon Axles: " + itemAmounts.get(1) + " | Clothes: " + itemAmounts.get(2)
				+ "  | Food: " + itemAmounts.get(3) +  " | Oxen: " + itemAmounts.get(4) + 
				" | Water: " + itemAmounts.get(5));
			}
		});
		lookAtSupplies.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lookAtSupplies.setEnabled(true);
		lookAtSupplies.setBounds(10, 673, 122, 23);
		frame.getContentPane().add(lookAtSupplies);
		
		choiceTF = new JTextField();
		choiceTF.setBackground(new Color(0, 0, 0));
		choiceTF.setForeground(new Color(0, 128, 0));
		choiceTF.setFont(new Font("Perpetua", Font.BOLD, 11));
		choiceTF.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//gets the user's inputs
				String selStr = choiceTF.getText();
				int selection = Integer.parseInt(selStr);
				// checks to make sure there is currently 3 clothes currently in the wagon if
				// the user selected the option that requires this.
				if(selection == 3 && itemAmounts.get(2) < 3 && river.getRiverName().contains("Snake"))	
				{
					JOptionPane.showMessageDialog(null, "Not enough clothes in wagon."
							+ " Please pick a different option");
					return;
				}
				
				// otherwise processes the user's input and displays the output
				String message = river.crossRiver(selection, totalWagonWeight, weather.getWater());
				outputTextArea.setText(message);
				
				// ends the game if crossing the river failed
				if(message.contains("Game Over.")) 
				{
					JOptionPane.showMessageDialog(null, message);
					frame.dispose();	
				}
				// will cause some items in wagon to be lost, based randomly
				else if (message.contains("lost some"))
				{
					outputTextArea.setText("Your wagon got swept away. " + Item.itemLoss(itemAmounts));
				}
				// removes $5.00 from the user if that option was selected
				else if(message.contains("$5.00"))
				{
					totalMoney -= 5;
				}
				// removes 3 clothing from the user if that option was selected
				else if(message.contains("hired"))
				{
					itemAmounts.set(2, itemAmounts.get(2) - 3);
				}
				//skips a couple of days, changes food amounts and the day
				else if (message.contains("waited")) 
				{
					game.nextDay();
					game.nextDay();
					itemAmounts.set(3, itemAmounts.get(3) - Food.rationsAmount);
					itemAmounts.set(3, itemAmounts.get(3) - Food.rationsAmount);
					foodInWagonEditLbl.setText("" + itemAmounts.get(3));	
				}
				
				// nothing happens if the crossing the river was successful
				else if (message.contains("safely")) {	}
				
				// skips one day for getting stuck in the mud
				else if (message.contains("mud")) 
				{
					game.nextDay();
					itemAmounts.set(3, itemAmounts.get(3) - Food.rationsAmount);
					foodInWagonEditLbl.setText("" + itemAmounts.get(3));
				}
				
				// passes the river and sets all components used with river crossing invisible
				river.riverPassed();
				choiceLbl.setVisible(false);
				choiceTF.setText("");
				choiceTF.setVisible(false);
				
				// allows the user to resume the game
				controlBtn.setEnabled(true);
			}
		});
		choiceTF.setBounds(284, 601, 22, 20);
		frame.getContentPane().add(choiceTF);
		choiceTF.setVisible(false);
		choiceTF.setColumns(10);
		choiceLbl.setFont(new Font("Perpetua", Font.BOLD, 14));
		
		choiceLbl.setBounds(229, 602, 66, 14);
		choiceLbl.setVisible(false);
		frame.getContentPane().add(choiceLbl);
		
		weatherLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		weatherLbl.setBounds(361, 512, 95, 14);
		frame.getContentPane().add(weatherLbl);
		tempLbl.setForeground(new Color(0, 128, 0));
		
		tempLbl.setFont(new Font("Perpetua", Font.BOLD, 10));
		tempLbl.setBounds(512, 514, 30, 14);
		frame.getContentPane().add(tempLbl);
		
		JLabel healthLabel = new JLabel("Health:");
		healthLabel.setFont(new Font("Perpetua", Font.BOLD, 10));
		healthLabel.setBounds(361, 601, 73, 50);
		frame.getContentPane().add(healthLabel);
		healthChangeLabel.setForeground(new Color(0, 128, 0));
		healthChangeLabel.setFont(new Font("Perpetua", Font.BOLD, 11));
		
		
		healthChangeLabel.setBounds(433, 601, 79, 50);
		frame.getContentPane().add(healthChangeLabel);
		
		JLabel leftLabel = new JLabel("800");
		leftLabel.setFont(new Font("Perpetua", Font.BOLD, 14));
		leftLabel.setForeground(new Color(255, 255, 255));
		leftLabel.setBounds(163, 280, 48, 14);
		buyFrame.getContentPane().add(leftLabel);
		buyFrame.getContentPane().setBackground(new Color(0, 0, 0));
		buyFrame.getContentPane().setForeground(new Color(0, 0, 0));
		buyFrame.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("<html>1 - Wagon Wheel($10) <br> 2 - Wagon Axle($10) <br> 3 - Clothing($10) <br> 4 - Food($0.20/lb)  <br>5 - Oxen($20) </html>");
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Perpetua", Font.BOLD, 14));
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
		amountField = new JTextField();
		amountField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemAmount = Integer.parseInt(amountField.getText());
				double itemPricing = Item.getPrice(itemContents, itemKind - 1);
				
				if (itemPricing * itemAmount <= totalMoney)
				{
					Item.Buy(itemContents, itemAmounts, itemAmount, itemKind - 1, totalWagonWeight);
					System.out.println(itemAmounts);
					Item tempItem = itemContents.get(itemKind - 1);
					totalWagonWeight += tempItem.getWeight() * itemAmount;

					totalMoney -= (itemPricing * itemAmount);
					leftLabel.setText("" + totalMoney);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Not enough money to complete this action!");
				}
			}
		});
		amountField.setBounds(84, 187, 86, 20);
		buyFrame.getContentPane().add(amountField);
		amountField.setColumns(10);
		
		JLabel kindLabel = new JLabel("Which Item?");
		kindLabel.setFont(new Font("Perpetua", Font.PLAIN, 14));
		kindLabel.setForeground(new Color(255, 255, 255));
		kindLabel.setBounds(10, 145, 74, 14);
		buyFrame.getContentPane().add(kindLabel);
		
		JLabel amountLabel = new JLabel("Amount?");
		amountLabel.setFont(new Font("Perpetua", Font.PLAIN, 14));
		amountLabel.setForeground(new Color(255, 255, 255));
		amountLabel.setBounds(10, 190, 74, 14);
		buyFrame.getContentPane().add(amountLabel);
		
		JButton nextBuybtn = new JButton("Next");		
		// resumes the game after the buy button is pressed
		nextBuybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Gets rid of the Buying frame and puts up the default traveling image
				buyFrame.setVisible(false);	
				imageLbl.setIcon(trailImage);
				imageLbl.setVisible(true);
				lookAroundBtn.setEnabled(true);
				buyBtn.setEnabled(true);
				tradeBtn.setEnabled(true);
				lookAtMapBtn.setEnabled(true);
				talkBtn.setEnabled(false);
				buyBtn.setEnabled(true);
				controlBtn.setEnabled(true);
			}
		});
		nextBuybtn.setBounds(84, 240, 89, 23);
		buyFrame.getContentPane().add(nextBuybtn);
		
		JLabel amount1Label = new JLabel("Money Remaining: ");
		amount1Label.setForeground(new Color(255, 255, 255));
		amount1Label.setFont(new Font("Perpetua", Font.BOLD, 14));
		amount1Label.setToolTipText("");
		amount1Label.setBounds(10, 280, 149, 14);
		buyFrame.getContentPane().add(amount1Label);
		
		JLabel generalStoreLabel = new JLabel("General Store");
		generalStoreLabel.setFont(new Font("Perpetua", Font.BOLD, 40));
		generalStoreLabel.setForeground(new Color(0, 128, 0));
		generalStoreLabel.setBounds(358, 30, 347, 58);
		buyFrame.getContentPane().add(generalStoreLabel);
		
		JLabel generalStoreLabel2 = new JLabel("Hope you find everything you need! Here are some reccomendations:");
		generalStoreLabel2.setFont(new Font("Perpetua", Font.PLAIN, 14));
		generalStoreLabel2.setForeground(new Color(255, 255, 255));
		generalStoreLabel2.setBounds(368, 80, 391, 14);
		buyFrame.getContentPane().add(generalStoreLabel2);
		
		JLabel lblYouShouldBe = new JLabel("<html> - You should be getting at least 200 pounds of food per member! If you don't want to starve. <br> <br> - At least two pairs of clothes for each person for those cold days! <br><br> Also, I suggest at least 5 oxen... there's been a lot of sickness going around!");
		lblYouShouldBe.setForeground(new Color(255, 255, 255));
		lblYouShouldBe.setFont(new Font("Perpetua", Font.BOLD, 14));
		lblYouShouldBe.setBounds(358, 117, 341, 146);
		buyFrame.getContentPane().add(lblYouShouldBe);
		buyFrame.setBounds(0, 0, 777, 472);
		frame.getContentPane().add(buyFrame);
		imageLbl.setBounds(40, 0, 777, 472);
		frame.getContentPane().add(imageLbl);
		imageLbl.setIcon(trailImage);
		
		JInternalFrame namingFrame = new JInternalFrame("Name Your Character");
		namingFrame.setBounds(0, 0, 777, 472);
		frame.getContentPane().add(namingFrame);
		namingFrame.getContentPane().setBackground(new Color(0, 0, 0));
		namingFrame.getContentPane().setForeground(new Color(0, 0, 0));
		namingFrame.getContentPane().setLayout(null);
		
		JLabel nameCharactersLbl = new JLabel("Name your Characters: ");
		nameCharactersLbl.setForeground(new Color(0, 128, 0));
		nameCharactersLbl.setFont(new Font("Perpetua", Font.BOLD, 24));
		nameCharactersLbl.setBounds(36, 11, 380, 29);
		namingFrame.getContentPane().add(nameCharactersLbl);
		
		JLabel character2Lbl = new JLabel("Character #2:");
		character2Lbl.setForeground(new Color(0, 128, 0));
		character2Lbl.setFont(new Font("Perpetua", Font.BOLD, 11));
		character2Lbl.setBounds(36, 109, 87, 14);
		namingFrame.getContentPane().add(character2Lbl);
		
		JLabel character3Lbl = new JLabel("Character #3:");
		character3Lbl.setForeground(new Color(0, 128, 0));
		character3Lbl.setFont(new Font("Perpetua", Font.BOLD, 11));
		character3Lbl.setBounds(36, 134, 87, 14);
		namingFrame.getContentPane().add(character3Lbl);
		
		character2TF = new JTextField();
		character2TF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets name of 'Character 2'
				String name = character2TF.getText();
				character2.memberName = name;
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
				character3.memberName = name;
			}
		});
		
		character3TF.setColumns(10);
		character3TF.setBounds(162, 131, 86, 20);
		namingFrame.getContentPane().add(character3TF);
		
		JLabel character4Lbl = new JLabel("Character #4:");
		character4Lbl.setFont(new Font("Perpetua", Font.BOLD, 11));
		character4Lbl.setForeground(new Color(0, 128, 0));
		character4Lbl.setBounds(36, 159, 87, 14);
		namingFrame.getContentPane().add(character4Lbl);
		
		character4TF = new JTextField();
		character4TF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Sets name of 'Character 4'
				String name = character4TF.getText();
				character4.memberName = name;
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
				character5.memberName = name;
			}
		});
		
		character5TF.setColumns(10);
		character5TF.setBounds(162, 182, 86, 20);
		namingFrame.getContentPane().add(character5TF);
		
		JLabel character5Lbl = new JLabel("Character #5:");
		character5Lbl.setForeground(new Color(0, 128, 0));
		character5Lbl.setFont(new Font("Perpetua", Font.BOLD, 11));
		character5Lbl.setBounds(36, 185, 87, 14);
		namingFrame.getContentPane().add(character5Lbl);
		
		JButton nextBtn = new JButton("Continue");
		nextBtn.setFont(new Font("Perpetua", Font.BOLD, 13));
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Progresses the user from the Naming Frame to the Buying frame at the start
				namingFrame.setVisible(false);
				buyFrame.setVisible(true);
			}
		});
		nextBtn.setBounds(146, 231, 122, 23);
		namingFrame.getContentPane().add(nextBtn);
		
		JLabel annaLabel = new JLabel("You'll be travelling with Anna Marie King, your Wagon Leader. Who else is coming along?");
		annaLabel.setForeground(new Color(255, 255, 255));
		annaLabel.setFont(new Font("Perpetua", Font.BOLD, 14));
		annaLabel.setBounds(36, 43, 571, 29);
		namingFrame.getContentPane().add(annaLabel);
		namingFrame.setVisible(true);
		
		
		imageLbl.setVisible(false);
		buyFrame.setVisible(false);
	}
}