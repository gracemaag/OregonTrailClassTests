package mpv;

import java.util.ArrayList;

public class Food extends Item 
{
	int rationsAmount = 0;

	public Food(int itemWeight) 
	{
		super(itemWeight);
	}
	
	public void Buy(ArrayList<Item> itemContents, ArrayList<Item> itemAmounts, int amount)
	{
		itemWeight =+ amount;
	}
	
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
	
	public void dailyRations()
	{
		this.itemWeight =- rationsAmount;
	}
	
}