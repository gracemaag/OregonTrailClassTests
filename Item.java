package mpv;

import java.util.ArrayList;
import java.util.Random;

public class Item 
{
	static Random rand = new Random();
	int itemWeight;
	static int tradeAmount = 0;
	static int giveAmount = 0;
	static int tradeItem = 0;
	static int giveItem = 0;
	
	public Item (int weight)
	{
		itemWeight = weight;
	}
	
	public int getWeight()
	{
		return this.itemWeight;
	}
	
	static String getTradeMessage(ArrayList<Integer> itemAmounts)
	{
		 int randIntTrade = rand.nextInt(5);
		 String tradeString = " ";
		 int randIntAmount = 0;
		 while (randIntAmount == 0) { randIntAmount = rand.nextInt(5); }
		 if (randIntTrade == 3)
		 {
			tradeString = "pounds of Food";
			randIntAmount = randIntAmount * 100;
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
		 
		 int randIntGive = 0;
		 do 
		 { 
			 randIntGive = rand.nextInt(4); 
		 } while (itemAmounts.get(randIntGive) == 0 || randIntGive == randIntTrade);
		 
		 String giveString = " ";
		 int randIntGiveAmount = 0;
		 while (randIntGiveAmount == 0 || randIntGiveAmount > itemAmounts.get(randIntGive)) { randIntGiveAmount = rand.nextInt(5); }
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
		 
		 tradeAmount = randIntAmount;
		 giveAmount = randIntGiveAmount;
		 tradeItem = randIntTrade;
		 giveItem = randIntGive;
		
		 return "Traveler would like to trade " + randIntAmount + " " + tradeString + ". They want " + randIntGiveAmount + " " + giveString + ".";	 
	}
	
	static void acceptTrade(ArrayList<Integer> itemAmounts)
	{
		int currentAmountPlayer = itemAmounts.get(giveItem); 
		itemAmounts.set(giveItem, currentAmountPlayer - giveAmount);
		
		int newAmountPlayer = itemAmounts.get(tradeItem);
		itemAmounts.set(tradeItem, newAmountPlayer + tradeAmount);
	}
	
	// 0 - Wagon Wheel 1 - Wagon Axle 2 - Clothing 3 - Food 
	static void Buy(ArrayList<Item> itemContents, ArrayList<Integer> itemAmounts, int amount, int itemType, int totalWeight)
	{
		int currentTotal = itemAmounts.get(itemType);
		itemAmounts.set(itemType, currentTotal + amount);
	}
}