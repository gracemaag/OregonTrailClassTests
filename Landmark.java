package oregonTrail;

import java.util.ArrayList;

public class Landmark extends Location
{
	
	// array list for landmark names and distances here
	static ArrayList<String> landmarkNames= new ArrayList<String>();
	static ArrayList<String> landmarkDistance= new ArrayList<String>();
	
	private static final String[] namedLandmarks = {"Independence","Kansas River Crossing", 
			"Big Blue River Crossing","Fort Kearney", "Chimney Rock"}; 
	private static final int[] distanceToLandmarks = {0, 102, 82, 118, 250}; // distance to landmark

	/**
	 * Finds distance from each landmark from current location (found in superclass)
	 * @return the total distance from the landmark compared to current distance
	 * 
	 */
	public static int distanceFrom()
	{
		// add in inheritance from superclass
		int currentDistance = 0;
		
	    for (int distance : distanceToLandmarks) 
	    {
	        if (distance > currentDistance) 
	        {
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
	
	public static String getLandmarkName(String selectedLandmark)
	{
		
		
		return "Error - Location cannot be found."; // if landmark name not found
	}
	
	/**
	 *
	 * Checks if current landmark is a fort
	 * @param location -
	 * @return true if landmark is a fort, false otherwise
	 * 
	 */
	public static boolean isFort(int location, int landmarkType)
	{
		
		
		return false; // if not fort - default
	}
	
	/**
	 * 
	 * Checks if player is currently at landmark
	 * @param distance - distance along the trail
	 * @return true if currently at landmark, false if otherwise
	 * 
	 */
	public static boolean atLandmark(int distance)
	{
		int currentDistance = 0;
		
		if (distance == currentDistance)
		{
			// locationInstance.nextDay(); 
		}
		return false;
	}
	
	
	/**
	 * Displays landmark surroundings upon selection
	 */
	public static void lookAroundOption()
	{
		
	}
	
	/**
	 * Displays randomized dialogue upon selection
	 * @param dialogueType - selects type of dialogue displayed
	 * @return index number of dialogue, otherwise return 0 
	 */
	public static int talkToOption(int dialogueType)
	{
		// use Array or ArrayList to store dialog?
		
		return 0; // if random fails
	}
}
