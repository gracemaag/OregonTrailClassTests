/**
	Food class, subclass of Item. Contains override of the Buy method from
	the Item class, as well as methods for changing rations
	@author Kaitlyn Reaser
	@version 1.0 9 April 2024
	@filename Food.java
*/
package OregonTrailMVP;

import java.util.ArrayList;

public class Food extends Item 
{

	static int rationsAmount = 15; // Amount of rations consumed per day

	/**
	 * Constructor for the Food Class
	 * @param itemWeight - gets from the Item superclass
	 */
	public Food(int itemWeight) 
	{
		super(itemWeight);
	}
	
	/**
	 * Override of the Buy method in the Item superclass, Food needs to have
	 * weight treated differently than other items for rationing
	 * @param itemContents - Contains contents of the wagon
	 * @param itemAmounts - Contains the amount of each type of item in the wagon
	 * @param amount - The amount of food purchased (in pounds)
	 */
	public void Buy(ArrayList<Item> itemContents, ArrayList<Integer> itemAmounts, int amount)
	{
		itemWeight =+ amount;
	}
	
	/**
	 * Changes the rations based on the user's input, 
	 * the amount of living wagon members weighs in on 
	 * the amount of food consumed
	 * Formulas are taken from the resource You have Died of Dysentery by
	 * R Philip Bouchard
	 * @param memberAmount
	 * @param rationsValue
	 */
	public void changeRations(int memberAmount, int rationsValue)
	{
		if (rationsValue == 1) // Bare Bones
		{
			rationsAmount = 1 * memberAmount;
		}
		else if (rationsValue == 2) // Meager
		{
			rationsAmount = 2 * memberAmount;
		}
		else if (rationsValue == 3) // Filling
		{
			rationsAmount = 3 * memberAmount;
		}
	}
	
	/**
	 * Gets the amount of food rations 
	 * @return - The amount of rations per person per day
	 */
	public int getRationsAmount() {
		return rationsAmount;
	}
	
	/**
	 * Gets the weight of the food
	 * @param itemAmounts - Array of amounts of each item
	 * @return - The weight of the food in the wagon
	 */
	public int getWeight(ArrayList<Integer> itemAmounts) 
	{
		return itemAmounts.get(3);
	}
	
}
