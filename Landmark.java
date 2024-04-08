package oregonTrail;

import java.util.ArrayList;

public class Landmark extends Location
{
	
	// array list for landmark names and distances here
	ArrayList<String> landmarkNames = new ArrayList<String>();
	ArrayList<String> landmarkDistance = new ArrayList<String>();
	
	private static final String[] namedLandmarks = {"Independence","Kansas River Crossing", 
			"Big Blue River Crossing","Fort Kearney", "Chimney Rock"}; 
	private static final int[] distanceToLandmarks = {0, 102, 82, 118, 250}; // distance to landmark
	
	private int dialogueCounter = 0; // Counter to cycle through dialogues
	private final String[] dialogues = 
		{
		    "Make sure you have extras of everything in case something happens.",
		    "Be careful when crossing the river, especially the deeper kind.",
		    "Trading is often far cheaper than buying at forts, but you never know what you'll get."
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
		int currentDistance = getTotalTraveled(); // Use getTotalTraveled from superclass

        for (int distance : distanceToLandmarks) {
            if (distance > currentDistance) {
                return distance - currentDistance;
            }
            currentDistance += distance;
        }
        return 0; // No more landmarks
	}
	
	/**
	 * Retrieves landmark names
	 * @param selectedLandmark gets landmark title from array list
	 * @return name of landmark, otherwise returns "cannot be found"
	 * 
	 */
	
	public String getLandmarkName(String selectedLandmark)
	{
		int index = landmarkNames.indexOf(selectedLandmark);
        if (index != -1) {
            return landmarkNames.get(index);
        }
        return "Error - Location cannot be found."; // if landmark name not found
	}
	
	/**
	 *
	 * Checks if current landmark is a fort
	 * @param location -
	 * @return true if landmark is a fort, false otherwise
	 * 
	 */
	public boolean isFort(int location, int landmarkType)
	{
        String landmark = landmarkNames.get(location);
        
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
		String landmarkName = landmarkNames.get(0);
		String currentDate = DateString(); // get current date from superclass
		
		 System.out.println("[Photo Placeholder]");
		 System.out.println("You are at " + landmarkName + ".");
		 System.out.println("Today's date is " + currentDate + ".");
	}
	
	/**
	 * Displays randomized dialogue upon selection
	 * @param dialogueType - selects type of dialogue displayed
	 * @return index number of dialogue, otherwise return 0 
	 */
	public int talkToOption(int dialogueType)
	{
		System.out.println(dialogues[dialogueCounter]);
		
		// Cycle through dialogues
	    dialogueCounter = (dialogueCounter + 1) % dialogues.length;
		
	    return dialogueCounter;
	}
}
