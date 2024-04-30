/**
	Item class, contains methods for getting weight as well as 
	trading and buying functions
	@author Kaitlyn Reaser
	@version 1.0 9 April 2024
	@filename Item.java
*/

package OregonTrailV4;

import java.util.ArrayList;
import java.util.Random;

public class Item 
{
	static Random rand = new Random(); // Needed for getting random numbers
	int itemWeight; 
	double itemPrice;
	
	// Static Variables for use in both Trading methods as placeholder values
	static int tradeAmount = 0;
	static int giveAmount = 0;
	static int tradeItem = 0;
	static int giveItem = 0;
	
	/**
	 * Constructor for the Item class
	 * @param weight - Weight of the item as represented in the wagon
	 * @param price - Price of the item as represented in the wagon
	 */
	public Item (int weight, double price)
	{
		itemWeight = weight;
		itemPrice = price;
	}
	
	/**
	 * Gets an item's weight
	 * @return - The item in question's weight
	 */
	public int getWeight()
	{
		return this.itemWeight;
	}
	
	/**
	 * Compiles a random trade offer for the player based on what items and
	 * the amount of those items the player has in their wagon. Has the option for
	 * no travelers to be interested, which comes up 1/5 of the time. 
	 * @param itemAmounts - Array list containing the amount of each item in the wagon
	 * @return - A string containing the trade offer message to be displayed in the main
	 */
	static String getTradeMessage(ArrayList<Integer> itemAmounts)
	{
		// Getting the offer from the traveler
		 int randIntTrade = rand.nextInt(5);
		 String tradeString = " ";
		 int randIntAmount = 0;
		 while (randIntAmount == 0) { randIntAmount = rand.nextInt(5); } // Ensures offer is greater than 0
		 
		 // Creates a string based on the randomly selected item
		 if (randIntTrade == 3)
		 {
			tradeString = "pounds of Food";
			randIntAmount = randIntAmount * 100; // Makes trade realistic, players typically have hundreds of pounds
		 }
		 else if (randIntTrade == 0)
		 {
			 tradeString = "Wagon Wheels";
		 }
		 else if (randIntTrade == 1)
		 {
			 tradeString = "Wagon Axles";
		 }
		 else if (randIntTrade == 2)
		 {
			 tradeString = "pairs of Clothes";
		 }
		 
		 if (randIntTrade == 4)
		 {
			return "Nobody wants to trade with you today.";
			 
		 }
		 
		 // Getting the want from the traveler (what the player owes)
		 int randIntGive = 0;
		 do // Ensures the player has the item requested, and that the item request/item given is not the same
		 { 
			 randIntGive = rand.nextInt(4); 
		 } while (itemAmounts.get(randIntGive) == 0 || randIntGive == randIntTrade);
		 
		 String giveString = " ";
		 int randIntGiveAmount = 0;
		 
		 // Ensure the amount requested is not more than what the player has of that item
		 while (randIntGiveAmount == 0 || randIntGiveAmount > itemAmounts.get(randIntGive)) 
		 { 
			 randIntGiveAmount = rand.nextInt(5); 
		 }
		 
		 // Creates a string based on the randomly selected item
		 if (randIntGive == 3)
		 {
			 giveString = "pounds of Food";
			randIntGiveAmount = randIntGiveAmount * 100;
		 }
		 else if (randIntGive == 0)
		 {
			 giveString = "Wagon Wheels";
		 }
		 else if (randIntGive == 1)
		 {
			 giveString = "Wagon Axles";
		 }
		 else if (randIntGive == 2)
		 {
			 giveString = "pairs of Clothes";
		 }
		 
		 // Variables necessary for the acceptTrade method
		 tradeAmount = randIntAmount;
		 giveAmount = randIntGiveAmount;
		 tradeItem = randIntTrade;
		 giveItem = randIntGive;
		
		 return "Traveler would like to trade " + randIntAmount + " " + tradeString + ". They want " + randIntGiveAmount + " " + giveString + ".";	 
	}
	
	/**
	 * Updates the items in the player's wagon after they accept a trade
	 * Removes items they gave, and adds items they received
	 * @param itemAmounts - The amount of each item in the wagon
	 */
	static void acceptTrade(ArrayList<Integer> itemAmounts)
	{
		int currentAmountPlayer = itemAmounts.get(giveItem); 
		itemAmounts.set(giveItem, currentAmountPlayer - giveAmount);
		
		int newAmountPlayer = itemAmounts.get(tradeItem);
		itemAmounts.set(tradeItem, newAmountPlayer + tradeAmount);
	}
	
	/**
	 * Updates the wagon contents based on what the user wishes to buy from
	 * the main. Item types are represented as values, and passed through all
	 * methods as: 
	 * 0 - Wagon Wheel 1 - Wagon Axle 2 - Clothing 3 - Food 
	 * The total weight of the wagon is updated based on purchases
	 * @param itemContents - The contents of the wagon (always the same)
	 * @param itemAmounts - The amounts of each item in the wagon
	 * @param amount - Amount of item being purchased
	 * @param itemType - The type of item being purchased
	 * @param totalWeight - Total wagon weight at the time of purchasing
	 */
	static void Buy(ArrayList<Item> itemContents, ArrayList<Integer> itemAmounts, int amount, int itemType, int totalWeight)
	{
		int currentTotal = itemAmounts.get(itemType);
		itemAmounts.set(itemType, currentTotal + amount);
	}
	
	/**
	 * Returns the price of an item  based on the item type number passed
	 * to the method. Each item has a number corresponding with it:
	 * 0 - Wagon Wheel 1 - Wagon Axle 2 - Clothing 3 - Food 4 - Oxen
	 * @param itemContents - The contents of the wagon (always the same)
	 * @param itemType - The number corresponding with each item
	 * @return - The price of the item specified by the item type
	 */
	static double getPrice(ArrayList<Item> itemContents, int itemType)
	{
		Item tempItem = itemContents.get(itemType);
		return tempItem.itemPrice;
	}
	
	static String itemLoss(ArrayList<Integer> itemAmounts)
	{
		
		int randItem1 = 0;
		int randItemAmount1 = 0;
		String itemString = " ";
		
		randItem1 = rand.nextInt(5);
		while (itemAmounts.get(randItem1) == 0)
		{
			randItem1 = rand.nextInt(5);
		}
		
		randItemAmount1 = rand.nextInt(5);
		if (randItem1 != 3)
		{
			while (randItemAmount1 == 0 || randItemAmount1 > itemAmounts.get(randItem1))
			{
				randItemAmount1 = rand.nextInt(5);
			}
		}
		
		else
		{
			while (randItemAmount1 == 0 || randItemAmount1 * 100 > itemAmounts.get(randItem1))
			{
				randItemAmount1 = rand.nextInt(5) * 100;
			}
		}
		
		if (randItem1 == 0) { itemString = " Wagon Wheels";}
		else if (randItem1 == 1) { itemString = " Wagon Axles ";}
		else if (randItem1 == 2) { itemString = " pairs of Clothes";}
		else if (randItem1 == 3) { itemString = " pounds of Food"; randItemAmount1 *= 100;}
		else if (randItem1 == 4) { itemString = " Oxen";}
		
		 int currentTotal1 = itemAmounts.get(randItem1);
		 itemAmounts.set(randItem1, currentTotal1 - randItemAmount1);
		 
		System.out.println("You have lost " + randItemAmount1 + itemString );
		return "You have lost " + randItemAmount1 + itemString;
	}
	
}