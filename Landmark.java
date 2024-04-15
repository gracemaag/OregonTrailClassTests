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
	private static final String[] namedLandmarks = {"Independence","Kansas River Crossing", 
			"Big Blue River Crossing","Fort Kearney", "Chimney Rock", "Fort Laramie",
			"Independence Rock", "South Pass", "Green River Crossing", "Soda Springs",
			"Fort Hall", "Snake River Crossing", "Fort Boise", "Blue Mountains",
			"Fort Walla Walla", "The Dalles", "Willamette Valley"}; 
	// green river crossway/dalles crossway/columbia river implementation later? 
	private static final int[] distanceToLandmarks = {0, 102, 
							  82, 118, 250, 86,
							  190, 120, 57, 143,
							  57, 182, 113, 160
							  55, 120, 100}; // distance to landmark
	
	private int dialogueCounter = 0; // Counter to cycle through dialogues
	private final String[] dialogues = 
		{
		    "Make sure you have extras of everything in case something happens.",
		    "Be careful when crossing the river, especially the deeper kind.",
		    "Trading is often far cheaper than buying at forts, but you never know what you'll get."
		};
	
	ImageIcon independance = new ImageIcon("C:\\Users\\gemaa\\Downloads\\Independance.jpg");
	ImageIcon kansasRiver = new ImageIcon("C:\\Users\\gemaa\\Downloads\\KansasRiverCrossing.jpg");
	ImageIcon blueRiver = new ImageIcon("C:\\Users\\gemaa\\Downloads\\BigBlueRiver.jpg");
	ImageIcon fortKearney = new ImageIcon ("C:\\Users\\gemaa\\Downloads\\fortKearny.jpg");
	
	
	private final ImageIcon[] landmarkImages = 
		{
			independance, kansasRiver, blueRiver, fortKearney
		};
	
	/**
	 * 
	 * Initialize the landmark information within a constructor
	 * 
	 */
	public Landmark()
	{
		// Storing namedLandmarks into landmarkNames ArrayList
	    for (String landmark : namedLandmarks) {
	        landmarkNames.add(landmark);
	    }

	    // Storing distanceToLandmarks into landmarkDistance ArrayList
	    for (int distance : distanceToLandmarks) {
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
		if(currentIndex < 17) {
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
	public void landmarkPassed() {
		currentIndex++;
	}
	
	/**
	 * gets the proper image icon of the landmark
	 * @return the image icon of the current landmark
	 */
	public ImageIcon getLandmarkIcon() {
		if(currentIndex > 0) {
		return landmarkImages[currentIndex-1];
		}
		return independance ;
	}
}
