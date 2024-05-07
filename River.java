package OregonTrailV4;

/**
 *
 * River subclass. Contains methods to display variables relating to each river, including
 * flow rate, name of the river, width of the river, and the width of the river. This also 
 * contains a method for crossing the river, which is reliant on a random number and probability.
 * Future versions will be able to change river depth and flow rate based on weather.
 * @author Grace Maag
 * @version 1.0 16 April 2024
 * @filename River.java
 * 
 */

public class River extends Landmark
{

	public River(){}
	
	// Initialize river names and related values for each river
	private String riverName[] = {"Kansas River Crossing", "Big Blue River Crossing", 
			"Green River Crossing", "Snake River Crossing"};
	private int width[] = {620, 200, 1000,  800};
	private int depth[] = {2, 5, 20, 8};
	private String flowRate[] = {"slow", "fast", "slow", "fast"};
	private int riverIndex = 0;
	private final int rainMultiplier = 4;
	private final int FORDCHOICE = 1; //fording the river option
	private final int CAULKCHOICE = 2; // caulking the wagon option
	private final int MULTICHOICE = 3; // variety of different options here - depends on current river
	
	/**
	 * displayRiverStats - will display each river's stats as well as display
	 * the options than can be taken at each river
	 * @return message - a string that contains all stats and options for crossing
	 */
	public String displayRiverStats(double rain) 
	{
		double tempDepth = depth[riverIndex] +
				rainMultiplier * rain; 
		String.format("$%", ".1f", tempDepth);
		String message = "You have arrived at " + riverName[riverIndex]
				+ ". This river has a " + flowRate[riverIndex] + " current and is " 
				+ width[riverIndex]  + " feet wide and " + tempDepth
				+ " feet deep. You can: "+ "\n[1] Ford "
						+ "the river \n[2] Caulk the wagon ";	
		if(riverName[riverIndex].contains("Green River"))
		{
			message += "\n[3] Take a Ferry for $5.00";
		}
		else if (riverName[riverIndex].contains("Snake River")) 
		{
			message += "\n[3] Hire a guide for 3 sets of clothing";
		}
		else if (riverName[riverIndex].contains("Big Blue River")) 
		{
			message += "\n[3] Wait 2 days for the current to slow";
		}
		
		return message += "\n Please enter your choice:";		
	}
	
	
	
	/**
	 * crossRiver - will use a random value and probability based on river width,
	 * depth, flow, and wagon weight. This also takes into account the user's choice
	 * in method of crossing the river (such as fording, caulking the wagon, taking a ferry,
	 * waiting for the river current to slow down, and hiring a guide).
	 * Some information used can be found at https://www.philipbouchard.com/oregon-trail/crossing-rivers.html
	 * @param choice - the user's input on what method they would like to take while crossing the river
	 * @param wagonWeight - the current total weight of the wagon
	 * @param rain - the recent amount of rain that will affect each river's depth
	 * @return a string to display of what event occurred when crossing the river
	 */
	public String crossRiver(int choice, int wagonWeight, double rain) 
	{
		/*
		 *  create a temporary depth variable to store the new river depth that is dependent on recent rain
		 */
		double tempDepth =  depth[riverIndex] + rainMultiplier * rain;
		/*
		 *  choice 1: fording the river. Player can get stuck in mud, lose items,
		 *  safely cross the river, or lose the entire wagon and end the game.
		 This is all dependent on the depth of the river
		 */
		if(choice == FORDCHOICE) 
		{
			if(tempDepth < 2.5)
			{
				double random = Math.random() *100;
				// 40% chance of getting stuck in mud
				if(random < 40)	 return "You are stuck in mud. Days lost: 1."; 
				else return "You have safely forded the river.";
			}	
			
			if(tempDepth <= 5 ) 
			{
				double random = Math.random() *100;
				System.out.println(random);
				// 20% chance of getting stuck in mud
				if(random < 20)	{ return "You are stuck in mud. Days lost: 1."; }
				// 70% chance of losing some supplies
				else if (random < 90) { return "You have lost some supplies."; }
				else { return "You have safely forded the river.";}
			}
			// if the depth is more than 5 feet, game automatically ends
			return "You lost your wagon and all supplies in it. Game Over.";
		}
		
		// choice 2: caulking the wagon.  Player can get stuck in mud, lose items,
		// safely cross the river, or lose the entire wagon and end the game.This is
		// Dependent on current (flow rate), river width, and wagon weight.
		if(choice == CAULKCHOICE)
		{
			if(flowRate[riverIndex].contains("fast")) return "You have lost some supplies."; 
			if(tempDepth < 2.5)	return "You are stuck in mud. Days lost: 1.";	
			if(width[riverIndex] > 1100 ) 
			{
				double random = Math.random() * 100;
				System.out.println(random);
				System.out.println("Wagon weight: " + wagonWeight);
				if(wagonWeight < 1500) 
				{
					if(random < 20) return "You have lost some supplies.";		
					else return "You have safely crossed the river.";
				}
				if(wagonWeight < 2000) 
				{
					if(random < 50) return "You have lost some supplies.";	
					if(random < 65) return "You lost your wagon and all supplies in it. Game over.";
					return "You have safely crossed the river.";
				}
				if(wagonWeight <= 2300) 
				{
					if(random < 80) return "You have lost some supplies.";	
					return "You lost your wagon and all supplies in it. Game over.";
				}
				return "You have lost your wagon and all supplied in it. Game over.";
			}
			if(width[riverIndex] > 600) 
			{
				double random = Math.random() * 100;
				if(wagonWeight < 1500) 
				{
					if(random < 10) return "You have lost some supplies.";		
					else return "You have safely crossed the river.";
				}
				if(wagonWeight < 2000) 
				{
					if(random < 40) return "You have lost some supplies.";	
					if(random < 50) return "You lost your wagon and all supplies in it. Game over.";
					return "You have safely crossed the river.";
				}
				if(wagonWeight <= 2300) 
				{
					if(random < 80) return "You have lost some supplies.";	
					return "You lost your wagon and all supplies in it. Game over.";
				}
				return "You have lost your wagon and all supplied in it. Game over.";
			}
			if(width[riverIndex] > 150) 
			{
				double random = Math.random() * 100;
				if(wagonWeight < 1500) 
				{
					if(random < 10) return "You have lost some supplies.";		
					else return "You have safely crossed the river.";
				}
				if(wagonWeight < 2000) 
				{
					if(random < 40) return "You have lost some supplies.";	
					if(random < 45) return "You lost your wagon and all supplies in it. Game over.";
					return "You have safely crossed the river.";
				}
				if(wagonWeight <= 2300) 
				{
					if(random < 60) return "You have safely crossed the river.";
					if(random < 90) return "You have lost some supplies.";	
					return "You lost your wagon and all supplies in it. Game over.";
				}
				return "You have lost your wagon and all supplied in it. Game over.";
			}
		}
		
		// choice 3: dependent on what river the player is currently at. This information used can
		// be found at https://gamefaqs.gamespot.com/mac/564877-the-oregon-trail/faqs/30964
		if(choice == MULTICHOICE)
		{
			//index 1: wait for the current to slow. Will result in losing 2 day's time and food.
			// This is 
			if(riverName[riverIndex].contains("Blue")) 
			{
				return "You waited 2 days to safely cross the river.";
			}
			//index 2: take a ferry to cross, safe every time. Will result in losing $5.00
			if(riverName[riverIndex].contains("Green"))
			{
				return "You took a ferry for $5.00 and safely crossed the river.";
			}
			//index 3: hire a guide to cross, safe every time. Will result in losing 3 clothing
			if(riverName[riverIndex].contains("Snake"))
			{
				return "You hired a guide for 3 clothing and safely crossed the river.";
			}
		}
		return "Error - Invalid Input. Please try again.\n"
				+ "Enter 1 to ford the river or 2 to caulk your wagon";
	}
	
	
	/**
	 * riverPassed - updates index to the next index
	 */
	public void riverPassed()
	{
		riverIndex++;
	}
	
	/**
	 * getRiverName - returns the current river
	 * @return the name of the current river 
	 */
	public String getRiverName() 
	{
		return riverName[riverIndex];
	}
}