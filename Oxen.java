package OregonTrailV4;

import java.util.ArrayList;

public class Oxen extends Item 
{
	static boolean isInjured;
	
	/**
	 * Constructor for the Oxen class
	 * @param itemWeight - gets from the Item superclass
	 * @param itemPrice - gets from the Item superclass
	 * @param injury - stores whether or not an oxen is sick, if one
	 * is sick and another gets sick, than that oxen dies
	 */
	public Oxen (int itemWeight, int itemPrice, boolean injury)
	{
		super(itemWeight, itemPrice);
		isInjured = injury;
	}
	
	/**
	 * Checks if an oxen is getting sick, or if one is already sick.
	 * Two oxen cannot be sick at the same time. If one is already sick, and another
	 * gets sick, the first one dies. 
	 * @param itemAmounts - gets the amount of Oxen the player has currently
	 * @return An integer corresponding to the state of Oxen's injuries
	 * -1 - No oxen are sick or dead.
	 * 1 - All oxen are dead.
	 * 2 - An oxen has dead.
	 * 3 - An oxen is sick. 
	 */
	static int injuryChance (ArrayList<Integer> itemAmounts)
	{
		if (itemAmounts.get(4) == 0) { return 1;} // no oxen left
		int injuryProb = rand.nextInt(99);
		if (injuryProb < 5)
		{
			if (Oxen.isInjured == true)
			{
				if (itemAmounts.get(4) == 0)
				{
					return 1; // No more oxen
				}
				itemAmounts.set(4, itemAmounts.get(4) - 1); // Decrementing Oxen amount
				Oxen.isInjured = false;
				return 2; // Oxen Dead
			}
			Oxen.isInjured = true;
			return 3; // Oxen Sick
		}
		return -1;
	}
	
}