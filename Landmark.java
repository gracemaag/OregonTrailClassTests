/**
 *Landmark subclass, contains methods to get and display Landmarks, as well as the buttons to
 *Look Around landmarks and Talk To people when located there.
 * @author Savannah Larsen
 * @version 1.4 29 April 2024
 * @filename Landmark.java
 * 
 */
package oregonTrailMVP;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;

public class Landmark extends Location
{
	
	// array list for landmark names and distances here
	ArrayList<String> landmarkNames = new ArrayList<String>();
	ArrayList<String> landmarkDistance = new ArrayList<String>();
	
	int currentIndex = 0;
	final int TOTAL_LANDMARK_INDEX = 18; // total number of landmarks
	
	private static final String[] namedLandmarks = { "Independence","Kansas River Crossing", 
			"Big Blue River Crossing","Fort Kearney", "Chimney Rock", "Fort Laramie",
			"Independence Rock", "South Pass Crossway", "Green River Crossing", "Fort Bridger", 
			"Soda Springs", "Fort Hall", "Snake River Crossing", "Fort Boise", 
			"Blue Mountains Crossway", "Fort Walla Walla", "The Dalles", "Willamette Valley" }; 
	
	// south pass: green river or fort bridger
	// blue mountains: fort walla walla or the dalles
	// after walla walla/dalles: Barlow Toll Road ($10) or Columbia River (deep river) Check 
	// dalles crossway/columbia river implementation later? 
	
	private static final int[] distanceToLandmarks = { 0, 102, 
							  82, 118, 250, 86,
							  190, 120, 57, 125,
							  143, 57, 182, 113, 
							  160, 55, 125, 100 }; // distance to landmark
	
	private int dialogueCounter = 0; // Counter to cycle through dialogues
	private final String[] dialogues = 
		{
		    "Make sure you have extras of everything in case something happens.",
		    "Be careful when crossing the river, especially the deeper kind.",
		    "Trading is often far cheaper than buying at forts, but you never know what you'll get."
		};
	
	// string array for holding licenses for images 
	private final String license[] = {"","","","","Chimney Rock 1 by Dr. Estell is licensed under CC BY-NC 4.0",
			"", "", "", "","Soda Springs is licenced under CC BY-NC-SA 2.0 DEED","","", "","Blue Mountains is"
					+ " licensed under CC BY 2.0 DEED", "", "", "Willamette Valley is licensed under CC BY 2.0 DEED"};
	
	// Initialize String array full of dialog options
	// Dialog is based off of the diaries of Anna Maria King, a woman who traveled the trail
	// with her husband, Stephan, and his little brother, Solomon, who is 12 at the time.
	private final String dialog[] = 
		{
			//Independence - Savannah Larsen
			"A friendly passerby bids you farewell:\r\n"
			+ "Now this will be a journey to remember! So you lot are traversing the trail\n"
			+ "up to Willamette, huh? Make sure you’re plenty prepared, You and those boys\n"
			+ "will need plenty of food to make it all the way up there. Remember, since\n"
			+ "you’re leaving in April, you should make it to the Valley before Winter.\n"
			+ "Keep track of your days and have plenty of extra wagon parts, and you\n"
			+ "shouldn’t have a problem, ma’am!\r\n", 
			// Kansas River Crossing - Kaitlyn Reaser
			"A woman approaches you, smiling: “The water here is so refreshing. My husband\n"
			+ " and I have been traveling for ages, having some fresh water has really done\n"
			+ "us well.” You nod and respond, stating that fresh water springs are common in\n"
			+ "your home state of Massachusetts.",
			// Big Blue River - Kaitlyn Reaser
			"A woman comes up to you: “Isn’t it just beautiful here? I love the countryside,\n"
			+ " always wanted to live out here and raise cattle... sounds like a very \n"
			+ "relaxing lifestyle.” \n" 
			+ "You laugh and agree,stating that this is also something you would love to do one day.", 
			// Fort Kearney - Kaitlyn Reaser
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
			// Independence Rock - Grace Maag
			"You receive a letter from your sister-in-law:\r\n"
			+ "“I hope this letter finds you in the best of spirits, for I am just bursting\n"
			+ "with excitement! Oh, it is a wonderful day!” I have the most joyous news, \n"
			+ "I’m now a married woman. I exchanged a vow with Mr. Amos Kind at a modest \n"
			+ "ceremony, filled with the warmth of our closest friends and family. I shall \n"
			+ "cherish the moment of walking down the aisle forever. As soon as you arrive in\n"
			+ "Oregon, you must meet him and witness the love that binds us. Until then, know\n"
			+ "that I hold you dear to my heart and cherish our bond as friends.\n"
			+ "With all my love, Melinda Fuller\r\n",
			// South Pass Crossway - Savannah Larsen
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
			// Fort Bridger - Savannah Larsen
			"A shopkeeper helps you off the wagon:\r\n"
			+ "There you go ma’am, glad you made it out here! We may not look like much, but this\n"
			+ "here fort's one of the most important places on the trail. We’ve only been here for a\n"
			+ "few short years, but we’re already one of the biggest places to stock back up on\n"
			+ "everything you need to survive the journey. You might find some wild berries out in\n"
			+ "the wilderness and you might meet a friendly native or two that can help you out, but\n"
			+ "why leave to chance when you kind folks can just get everything now?”\r\n",
			// Soda Springs - Grace Maag
			"You arrive at the springs and think:\r\n"
			+ "What a remarkable sight these springs are. You really can’t help but to be\n"
			+ "struck in awe looking at them. The water too, it must be the cleanest, chillies\n"
			+ "water in the world. Stephan and I may stay here for a few days with the rest of\n"
			+ "the party to gather supplies and allow the oxen to enjoy the water. Solomon and\n"
			+ "a few of the other young boys want to try out swimming, but I’m not too sure.\n"
			+ "One of the children could drown and that would just be plain terrible. \r\n",
			//Fort Hall - Savannah Larsen
			"A young man waves you over: \r\n"
			+ "“Hiya there ma’am, welcome to Fort Hall! I know things might be in a bit of a\n"
			+ "ruckus right now, but that’s just because we’re waitin’ to see what happens.\n"
			+ "Did ya know that we might become a US Territory? Ya picked the right time to\n"
			+ "travel through here, we’re stocking up in plenty of furs right now for the people\n"
			+ "going up the trail. Buy some clothes at the store if ya need em!”\r\n", 
			// Snake River Crossing - Savannah Larsen
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
			// Blue Mountains Crossway - Savannah Larsen
			"A seamstress smiles over at you:\r\n"
			+ "“Heading through the mountains then? Make sure you’ve got plenty of food, you’re\n"
			+ "almost there! This final stretch is one of the longest, so you best trade what you can.\n"
			+ "Take it from me, young lady, making it up these parts with a family isn’t easy. But if\n"
			+ "you make it to Oregon, you folks could even start up a ranch. I heard that you can raise\n"
			+ "as many cattle as you want and it won’t cost a penny. Great idea for folks looking to settle\n"
			+ "down over there like you.”\r\n", 
			// Fort Walla Walla - Kaitlyn Reaser
			"A man approaches you and your husband: “So close to Oregon City, we’re almost there.\n"
			+ "It's been a long journey, a lot of loss along the way. My daughter is sick, we’re\n"
			+ "hoping to get help once we reach the city.” Your husband responds for you, stating\n"
			+ "that his younger brother has also come down with a fever. He is hoping the same thing.",
			// The Dalles - Savannah Larsen
			"A guide beckons you:\r\n"
			+ "“So you’re almost there, huh? That’s great to see ma’am! Now you’ve got two choices\n"
			+ "ahead of you. Pick the longer trail over the rest of the Dalles or brave the treacherous\n"
			+ "Columbia River. Up to you folks though, ma’am. As a guide, I’d recommend risking the longer\n"
			+ "hike, that slippery river just ain’t worth it.” \r\n", 
			// Willamette Valley - Savannah Larsen
			"A merchant smiles at you:\r\n"
			+ "“Welcome good folk to Willamette! Must have been quite the trip from out East! Welcome to\n"
			+ "the new frontier, you’ve got a spectacular new life ahead of you. Congratulations on making\n"
			+ "it this far my friends, why don’t we stop and chat about your future prospects. Fine folks\n"
			+ "like you are probably looking to start a ranch, we’ve got land aplenty for the choosing, right\n"
			+ "this way!”\r\n"
		};
	
	// Initialize image icons
	ImageIcon independance = new ImageIcon("C:\\Users\\gemaa\\Downloads\\Independence.png");
	ImageIcon kansasRiver = new ImageIcon("C:\\Users\\gemaa\\Downloads\\KansasRiver.png");
	ImageIcon blueRiver = new ImageIcon("C:\\Users\\gemaa\\Downloads\\BigBlueRiver.png");
	ImageIcon fortKearney = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\FortKearny.png");
	ImageIcon chimneyRock = new ImageIcon("C:\\Users\\gemaa\\Downloads\\ChimneyRock.png");
	ImageIcon fortLaramie = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\FortLaramie.png");
	ImageIcon independanceRock = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\IndependenceRock.png");
	ImageIcon southPass = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\SouthPass.png");
	ImageIcon greenRiverCrossing = new ImageIcon("C:\\Users\\gemaa\\Downloads\\GreenRiver.png");
	ImageIcon sodaSprings = new ImageIcon("C:\\Users\\gemaa\\Downloads\\SodaSprings.png");
	ImageIcon fortHall = new ImageIcon("C:\\Users\\gemaa\\Downloads\\FortHall.png");
	ImageIcon snakeRiverCrossing = new ImageIcon("C:\\Users\\gemaa\\Downloads\\SnakeRiver.png");
	ImageIcon fortBoise = new ImageIcon("C:\\Users\\gemaa\\Downloads\\FortBoise.png");
	ImageIcon blueMountains = new ImageIcon("C:\\Users\\gemaa\\Downloads\\BlueMountains.png");
	ImageIcon fortWallaWalla = new ImageIcon("C:\\Users\\gemaa\\Downloads\\FortWallaWalla.png");
	ImageIcon theDalles = new ImageIcon("C:\\Users\\gemaa\\Downloads\\TheDalles.png");
	ImageIcon willametteValley = new ImageIcon("C:\\Users\\gemaa\\Downloads\\WillametteValley.png");
	
	ImageIcon FortBridger = new ImageIcon("");
	ImageIcon BarlowTollRoad = new ImageIcon("C:\\Users\\gemaa\\Downloads\\BarlowTollRoad.png");
	
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
		if(currentIndex < TOTAL_LANDMARK_INDEX)
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
	
	/**
	 *
	 * Checks if current landmark is a river
	 * @return true if landmark is a river, false otherwise 
	 * 
	 */
	public boolean isRiver() 
	{
		String landmark = landmarkNames.get(currentIndex);
		return landmark.contains("River");
	} 
	
	/**
	 *
	 * Checks if current landmark is a crossway
	 * @return true if landmark is a crossway, false otherwise 
	 * 
	 */
	public boolean isCrossway() 
	{
		String landmark = landmarkNames.get(currentIndex);
		return landmark.contains("Crossway");
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
		boolean atCrossway = isCrossway();
		
		if (atCrossway)
		// prompt player for choice change in implementation
		{
			Scanner scanner = new Scanner(System.in);
	        int choice = scanner.nextInt();

	        if (currentIndex == 7) //South Pass Crossway is at index 15
	        {
	            // Offer options for South Pass Crossway
	            System.out.println("You are at South Pass Crossway. Choose your next destination:");
	            System.out.println("1. Green River Crossing");
	            System.out.println("2. Fort Bridger");
	            System.out.println("Enter your choice:");

	            choice = scanner.nextInt();
	            switch (choice) {
	                case 1:
	                    currentIndex = 8; // Move to Green River Crossing
	                    break;
	                case 2:
	                    currentIndex = 9; // Move to Fort Bridger
	                    break;
	            }
	        } 
	        else if (currentIndex == 15) // Blue Mountains Crossway is at index 15
	        {
	            // Offer options for Blue Mountains Crossway
	            System.out.println("You are at Blue Mountains Crossway. Choose your next destination:");
	            System.out.println("1. Fort Walla Walla");
	            System.out.println("2. The Dalles");
	            System.out.println("Enter your choice:");

	            choice = scanner.nextInt();
	            switch (choice) {
	                case 1:
	                    currentIndex = 16; // Move to Fort Walla Walla
	                    break;
	                case 2:
	                    currentIndex = 17; // Move to The Dalles
	                    break;
	            }
	        }
	        return true;
	    }
		
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