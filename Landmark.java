/**
 *Landmark subclass, contains methods to get and display Landmarks, as well as the buttons to
 *Look Around landmarks and Talk To people when located there.
 * @author Savannah Larsen
 * @version 1.0 9 April 2024
 * @filename Landmark.java
 * 
 */
package OregonTrailMVP;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Landmark extends Location
{
	
	// array list for landmark names and distances here
	ArrayList<String> landmarkNames = new ArrayList<String>();
	ArrayList<String> landmarkDistance = new ArrayList<String>();
	
	int currentIndex = 0;
	private static final String[] namedLandmarks = { "Independence","Kansas River Crossing", 
			"Big Blue River Crossing","Fort Kearney", "Chimney Rock", "Fort Laramie",
			"Independence Rock", "South Pass", "Green River Crossing", "Soda Springs",
			"Fort Hall", "Snake River Crossing", "Fort Boise", "Blue Mountains",
			"Fort Walla Walla", "The Dalles", "Willamette Valley" }; 
	
	// green river crossway/dalles crossway/columbia river implementation later? 
	
	private static final int[] distanceToLandmarks = { 0, 102, 
							  82, 118, 250, 86,
							  190, 120, 57, 143,
							  57, 182, 113, 160,
							  55, 120, 100 }; // distance to landmark
	
	
	private int dialogueCounter = 0; // Counter to cycle through dialogues
	private final String[] dialogues = 
		{
		    "Make sure you have extras of everything in case something happens.",
		    "Be careful when crossing the river, especially the deeper kind.",
		    "Trading is often far cheaper than buying at forts, but you never know what you'll get."
		};
	
	private final String license[] = {"","","","","Chimney Rock 1 by Dr. Estell is licensed under CC BY-NC 4.0",
			"", "", "", "","Soda Springs is licenced under CC BY-NC-SA 2.0 DEED","","", "","Blue Mountains is"
					+ " licensed under CC BY 2.0 DEED", "", "", "Willamette Valley is licensed under CC BY 2.0 DEED"};
	
	// Initialize String array full of dialog options
	// Dialog is based off of the diaries of Anna Maria King, a woman who traveled the trail
	// with her husband, Stephan, and his little brother, Solomon, who is 12 at the time.
	private final String dialog[] = {"", //Independence  - no dialog here
					// Kansas River Crossing - Kaitlyn Reaser
					"A woman approaches you, smiling: “The water here is so refreshing. My husband\n"
					+ " and I have been traveling for ages, having some fresh water has really done\n"
					+ "us well.” You nod and respond, stating that fresh water springs are common in\n"
					+ "your home state of Massachusetts.",
					// Big Blue River - Kaitlyn Reaser
					"A woman comes up to you: “Isn’t it just beautiful here? I love the countryside,\n"
					+ " always wanted to live out here and raise cattle... sounds like a very \n"
					+ "relaxing lifestyle.”/n " 
					+ "You laugh and agree,stating that this is also something you would love to do one day.", 
					// Fort Kearny - Kaitlyn Reaser
					"A man approaches you: “Could I interest you in a shortcut to Chimney Rock?\n"
					+ " It would get you to your next stop a lot quicker. Already got about \n"
					+ "100 other wagons coming along, should be an easy trip.” Your husband declines\n"
					+ "the offer.",
					// Chimney Rock - Grace Maag
					"A sad husband says to you:\r\n"
					+ "I have something to tell you, something that weighs heavily on my heart. \n"
					+ "Mr. John King , my brother, and his wife met with a terrible fate, my dear.\n"
					+ "As they attempted to cross the Kansas River, their wagon was swept away by\n"
					+ "the current. Along with them went little Electa and their baby son.  Despite\n"
					+ "our efforts to save them, they were unable to reach safety. This loss in this\n"
					+ "tragedy will be felt by us all.\r\n",
					// Fort Laramie - Savannah Larsen
					"A young woman approaches you:\r\n"
					+ "“Welcome to Laramie, friend! Here’s a real good place to stock up on materials.\n"
					+ " Ya won’t see another fort for a while, so it’s best to stock up on materials\n"
					+ "for your family while ya can. Though, there’s plenty of the folks out on the\n"
					+ "plains in these parts ya might be able to trade with you lot when you’re out there!\r\n",
					// Independance Rock - Grace Maag
					"You receive a letter from your sister-in-law:\r\n"
					+ "“I hope this letter finds you in the best of spirits, for I am just bursting\n"
					+ "with excitement! Oh, it is a wonderful day!” I have the most joyous news, \n"
					+ "I’m now a married woman. I exchanged a vow with Mr. Amos Kind at a modest \n"
					+ "ceremony, filled with the warmth of our closest friends and family. I shall \n"
					+ "cherish the moment of walking down the aisle forever. As soon as you arrive in\n"
					+ "Oregon, you must meet him and witness the love that binds us. Until then, know\n"
					+ "that I hold you dear to my heart and cherish our bond as friends.\n"
					+ "With all my love, Melinda Fuller\r\n",
					// South Pass - Savannah Larsen
					"A guide speaks with you: \r\n"
					+ "“It sure is a good thing we found the South Pass. Without it, the people\n"
					+ "traversing this trail wouldn’t have even a glimpse of hope. I’m glad you\n"
					+ "folks already got a guide through the mountains, there’s all sorts of danger\n"
					+ "through here.”\r\n", 
					// Green River Crossing - Grace Maag
					"A little boy tells you\r\n: Excuse me Ma’am,\n"
					+ "You wouldn’t believe what I found today. Fruit! Beautiful, ripe strawberries,\n"
					+ "gooseberries, blackberries and whortleberries. They might just be the tastiest\n"
					+ "berries I’ve ever had - Don’t worry, I brought some back for you. Careful, \n"
					+ "ma’am, they stain - I stained my trousers. Oh, what will my sister say? I best\n"
					+ "be getting back for dinner and show them my findings. If you want more, just\n"
					+ "walk along the side of the river a bit. You can’t miss them.\r\n", 
					// Soda Springs - Grace Maag
					"You arrive at the springs and think:\r\n"
					+ "What a remarkable sight these springs are. You really can’t help but to be\n"
					+ "struck in awe looking at them. The water too, it must be the cleanest, chillies\n"
					+ "water in the world. Stephan and I may stay here for a few days with the rest of\n"
					+ "the party to gather supplies and allow the oxen to enjoy the water. Solomon and\n"
					+ "a few of the other young boys want to try out swimming, but I’m not too sure.\n"
					+ "One of the children could drown and that would just be plain terrible. \r\n",
					//Fort Hall
					"fort hall", 
					// Snake River - Savannah Larsen
					"A nearby blacksmith strikes up a conversation:\r\n"
					+ "“I had a cousin who went up the trail a month or so earlier. We’d been exchanging\n"
					+ "letters at forts they stopped at along the way, but she stopped responding a\n"
					+ "little while ago. Last thing I heard they’d decided to risk caulking the wagon along\n"
					+ "Snake River, guess that river got the best of them. Never trust a calm looking river,\n"
					+ "I say.”\r\n", 
					// Fort Boise - Savannah Larsen
					"A cook comes to talk to you:\r\n"
					+ "“This is the right place to be if you’re looking for a classic old welcome! Feel free\n"
					+ "to stay as long as you like, my husband and I decided to stop here along the trail a\n"
					+ "while ago and then we never left. We definitely have better food here too, ha!”\r\n", 
					"blue mountains", 
					// Fort Walla - Kaitlyn Reaser
					"A man approaches you and your husband: “So close to Oregon City, we’re almost there.\n"
					+ "It's been a long journey, a lot of loss along the way. My daughter is sick, we’re\n"
					+ "hoping to get help once we reach the city.” Your husband responds for you, stating\n"
					+ "that his younger brother has also come down with a fever. He is hoping the same thing.",
					"the dalles", "willamette valley"
	};
	
	// Initialize image icons
	ImageIcon independance = new ImageIcon("C:\\Users\\gemaa\\Downloads\\Independance.jpg");
	ImageIcon kansasRiver = new ImageIcon("C:\\Users\\gemaa\\Downloads\\KansasRiverCrossing.jpg");
	ImageIcon blueRiver = new ImageIcon("C:\\Users\\gemaa\\Downloads\\BigBlueRiver.jpg");
	ImageIcon fortKearney = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\fortKearny.jpg");
	ImageIcon chimneyRock = new ImageIcon("C:\\Users\\gemaa\\Downloads\\chimneyRock.jpg");
	ImageIcon fortLaramie = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\fortLaramie.jpg");
	ImageIcon independanceRock = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\independanceRock.jpg");
	ImageIcon southPass = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\southPass.jpg");
	ImageIcon greenRiverCrossing = new ImageIcon("C:\\Users\\gemaa\\Downloads\\greenRiverCrossing.jpg");
	ImageIcon sodaSprings = new ImageIcon("C:\\Users\\gemaa\\Downloads\\sodaSprings.jpg");
	ImageIcon fortHall = new ImageIcon("C:\\Users\\gemaa\\Downloads\\fortHall.jpg");
	ImageIcon snakeRiverCrossing = new ImageIcon("C:\\Users\\gemaa\\Downloads\\snakeRiverCrossing.jpg");
	ImageIcon fortBoise = new ImageIcon("C:\\Users\\gemaa\\Downloads\\fortBoise.jpg");
	ImageIcon blueMountains = new ImageIcon("C:\\Users\\gemaa\\Downloads\\blueMountains.jpg");
	ImageIcon fortWallaWalla = new ImageIcon("C:\\Users\\gemaa\\Downloads\\fortWallaWalla.jpg");
	ImageIcon theDalles = new ImageIcon("C:\\Users\\gemaa\\Downloads\\theDalles.jpg");
	ImageIcon willametteValley = new ImageIcon("C:\\Users\\gemaa\\Downloads\\willametteValley.jpg");
	
	// make array of the image icons to be used
	private final ImageIcon[] landmarkImages = 
		{
			independance, kansasRiver, blueRiver, fortKearney,
			chimneyRock, fortLaramie, independanceRock, southPass, greenRiverCrossing, 
			sodaSprings, fortHall, snakeRiverCrossing, fortBoise, blueMountains,
			fortWallaWalla, theDalles, willametteValley
		};
	
	/**
	 * 
	 * Initialize the landmark information within a constructor
	 * 
	 */
	public Landmark()
	{
		// Storing namedLandmarks into landmarkNames ArrayList
	    for (String landmark : namedLandmarks)
	    {
	        landmarkNames.add(landmark);
	    }

	    // Storing distanceToLandmarks into landmarkDistance ArrayList
	    for (int distance : distanceToLandmarks) 
	    {
	        landmarkDistance.add(String.valueOf(distance)); // Convert int to String
	    }
	}
	
	/**
	 * Finds distance from each landmark from current location (found in superclass)
	 * @return the total distance from the landmark compared to current distance
	 * 
	 */
	public int distanceFrom()
	{   
		int temp = distanceToLandmarks[currentIndex];
		return temp;
	}
	
	/**
	 * Retrieves landmark names
	 * @return name of landmark, otherwise returns "cannot be found"
	 * 
	 */
	public String getLandmarkName()
	{
		if(currentIndex < 17)
		{
		return landmarkNames.get(currentIndex);
		}
        return "Error - Location cannot be found."; // if landmark name not found
	}
	
	/**
	 *
	 * Checks if current landmark is a fort
	 * @return true if landmark is a fort, false otherwise
	 * 
	 */
	public boolean isFort()
	{
        String landmark = landmarkNames.get(currentIndex);
        
        return landmark.contains("Fort"); // Simple check if landmark name contains "Fort"
	}
	
	public boolean isRiver() 
	{
		String landmark = landmarkNames.get(currentIndex);
		return landmark.contains("River");
	} 
	
	/**
	 * 
	 * Checks if player is currently at landmark
	 * @param distance - distance along the trail
	 * @return true if currently at landmark, false if otherwise
	 * 
	 */
	public boolean atLandmark(int distance)
	{
		int currentDistance = getTotalTraveled(); // Use getTotalTraveled from superclass
        return distance == currentDistance;
	}
	
	
	/**
	 * Displays landmark surroundings upon selection
	 */
	public void lookAroundOption()
	{
		String landmarkName = landmarkNames.get(currentIndex);
		String currentDate = DateString(); // get current date from superclass
		
		 System.out.println("[Photo Placeholder]");
		 System.out.println("You are at " + landmarkName + ".");
		 System.out.println("Today's date is " + currentDate + ".");
	}
	
	/**
	 * Displays randomized dialogue upon selection
	 * @param dialogueType - selects type of dialogue displayed
	 * @return index number of dialogue
	 */
	public String talkToOption(int dialogueType)
	{
		System.out.println(dialogues[dialogueCounter]);
		
		// Cycle through dialogues
	    dialogueCounter = (dialogueCounter + 1) % dialogues.length;
		
	    return dialogues[dialogueCounter];
	}
	/**
	 * gets the current landmark number you are at or approaching
	 * @return index of current landmark
	 */
	public int getIndex() 
	{
		return currentIndex;
	}
	
	/**
	 * increments the landmark to the next landmark 
	 */
	public void landmarkPassed()
	{
		currentIndex++;
	}
	
	/**
	 * gets the proper image icon of the landmark
	 * @return the image icon of the current landmark
	 */
	public ImageIcon getLandmarkIcon() 
	{
		if(currentIndex > 0) 
		{
		return landmarkImages[currentIndex - 1];
		}
		return independance ;
	}
	
	/**
	 * gets the proper license of the landmark image
	 * @return the string of the current landmark license
	 */
	public String getLicense()
	{
		if(currentIndex > 0)
		{
			return license[currentIndex - 1];
		}
			return "";
	}
	
	/**
	 * returns the conversation relating to the current landmark
	 * @return a string of conversation at the current landmark
	 */
	public String getLandmarkDialog()
	{
		return dialog[currentIndex - 1];
	}
}