package mpv;

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
	 */
	static void injuryChance (ArrayList<Integer> itemAmounts)
	{
		int injuryProb = rand.nextInt(99);
		if (injuryProb < 5)
		{
			System.out.println("Oxen is sick.");
			if (Oxen.isInjured == true)
			{
				if (itemAmounts.get(4) == 0)
				{
					System.out.println("No more Oxen.");
					return;
				}
				itemAmounts.set(4, itemAmounts.get(4) - 1); // Decrementing Oxen amount
				System.out.println("Oxen Dead.");
				Oxen.isInjured = false;
				return;
			}
			Oxen.isInjured = true;
		}
	}
	
}