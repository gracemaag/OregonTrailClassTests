/**
	Wagon Member class, contains a method for adding
	the wagon member's name whenever a new object is
	instantiated. More on health to go here later.
	@author Kaitlyn Reaser
	@version 1.0 9 April 2024
	@filename WagonMember.java
*/
package OregonTrailV4;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WagonMember {
	public String memberName;
	public boolean hasIllness;
	public static Random rand = new Random(); // Needed for getting random numbers
    public	static List<String> illnesses = Arrays.asList("Cholera", "Typhoid", "Measels", "Dysentery", "Fever");	
	
	/**
	 * 0 - 34: Good Health
	 * 35 - 65: Fair Health
	 * 70 - 104: Poor Health
	 * 105 - 139: Very Poor Health
	 * 140 - Remaining Party Members Dead
	 */
	static int memberHealth = 0;  
	
	/**
	 * Constructor for the Wagon Member Class
	 * @param name - Name of a wagon member
	 */
	public WagonMember (String name, boolean illness)
	{
		memberName = name;
		hasIllness = illness;
	}
	
	/**
	 * Decrements health value by 10% of the current value each day
	 * Rounds this value to the nearest integer to keep health at a 
	 * whole value; Meant to represent wagon members healing through time
	 */
	public static void dailyDecrement()
	{
		double healthPercentage =  (memberHealth * 0.20);
		memberHealth -= healthPercentage;
	}
	
	/** 
	 * Controls the Probability of the following random events
	 * that can have a negative effect on Health, and their corresponding
	 * values to represent them:
	 * - Lost Trail (2): 7% Change w/ +10 Health
	 * - Rough Trail (3): 8% Chance w/ +10 Health
	 * @return An integer corresponding with the random event happening, 
	 * or 0 if none are happening
	 */
	public static int randomHealthEvent()
	{	
		int lostTrailProb = rand.nextInt(99);
		if (lostTrailProb < 7)
		{
			memberHealth += 10;
			return 2;
		}
		int roughTrailProb = rand.nextInt(99);
		if (roughTrailProb < 8) 
		{ 
			memberHealth += 10;
			return 3;
		}
		return 0;
	}

	/**
	 * Does daily incrementing of health based on environmental factors that have been set by
	 * either the game or player: Rations, Weather conditions, and pace.
	 * @param rationsValue - Bare Bones - 1, Meager - 2, Filling - 3
	 * @param weatherValue - Very Hot - 5 Warm - 4 Cool - 3 Cold - 1 Very Cold - 0
	 * @param itemAmounts - Array List containing the amount of clothes (2) the player has
	 * @param livingMembers - The amount of living wagon members
	 * @param paceAmount - Value from 12 to 20: 12-15 = Steady, 16-18 = Strenuous, 19-20 = Grueling
	 */
	public static void environmentalDecrement(int rationsValue, int weatherValue, ArrayList<Integer> itemAmounts, int livingMembers, int paceAmount)
	{
		if (rationsValue == 1) {memberHealth += 4;} // Bare Bones - higher effect than Meager, Filling has no adverse effects
		else if (rationsValue == 2) {memberHealth += 2;} // Meager
		
		// Very Cold and Cold have no effect if the wagon has enough pairs of clothes for each member. => 3 for Very Cold, => 2 for Cold
		// Cool (4) and Warm (5) weather has no adverse effects on health, so not included
		if (weatherValue == 0 && (itemAmounts.get(2) / livingMembers) < 3) {memberHealth += 4;} // Very Cold
		else if (weatherValue == 1 && (itemAmounts.get(2) / livingMembers) < 2) {memberHealth += 2;} // Cold
		else if (weatherValue == 4 ) {memberHealth += 1;} // Hot
		else if (weatherValue == 5 ) {memberHealth += 2;} // Very Hot
		
		if (paceAmount <= 15 ) {memberHealth += 2;} // Steady
		else if (paceAmount > 15 && paceAmount <= 18) {memberHealth += 4;} // Strenuous
		else if (paceAmount > 18) {memberHealth += 6;} // Grueling
		
	}
	
	/**
	 * Calculates a random number based on current member health for 
	 * whether or not a member gets sick, the higher the member health
	 * the greater the chance of sickness. 
	 * @param members - Array List of current living member's names 
	 * @param livingMembers - Number of living members, should be >0
	 * @return - Returns the index of the specific sick member, -1 if no members 
	 * sick or -2 if no members are living
	 */
	public static int illnessProb(ArrayList<WagonMember> members, int livingMembers)
	{
		System.out.println("Living Members: " + livingMembers);
		if (livingMembers == 0) // If all members are dead, none can get sick
		{
			return -2;
		}
		int illProb = 0;
		int illMember = -1;
		
		// Higher the member health, greater the sickness chance
		if (memberHealth < 34) {illProb = rand.nextInt(99); }
		else if (memberHealth < 65) {illProb = rand.nextInt(79); }
		else if (memberHealth < 104) {illProb = rand.nextInt(59); }
		else if (memberHealth < 139) {illProb = rand.nextInt(39); }
		
		// Checks if there are more than one living members
		if (livingMembers > 1)
		{
			if (illProb < 7) // Checks if illness chance met
			{
				illMember = rand.nextInt(livingMembers - 1);
			}
		}
		else 
		{
			if (illProb < 7)
			{
				illMember = 0; // If only one member, always returns the first value
			}
		}
		return illMember;
	}
	
	/**
	 * Gets a random illness based on the list of illnesses at top of class
	 * @return - Illness as a string
	 */
	public static String getIllness()
	{
		 int randIllness = rand.nextInt(5);
		 String illness = illnesses.get(randIllness);
		 return illness;
	}
	
	/**
	 * Checks if a member is sick, and if so, checks if they are better. 
	 * There is a 10% chance that the member gets better each day.
	 * @param members - Array List of the current living member's names
	 * @param livingMembers - The number of living members 
	 */
	public static int isBetter(ArrayList<WagonMember> members, int livingMembers)
	{
		for (int i = livingMembers - 1; i >= 0; i--)
		{
			if((members.get(i)).hasIllness == true) // Checks each member for illness
			{
				int better = rand.nextInt(99);
				if (better < 10)
				{
					(members.get(i)).hasIllness = false; // Removes that illness
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * Decrements the wagon member's health each day 
	 * that they are resting. 
	 */
	public static void restingState()
	{
		if (memberHealth != 0)
		{
			memberHealth -= 5;
		}
		else if (memberHealth < 0)
		{
			memberHealth = 0;
		}
	}
	
	/**
	 * Returns the health description corresponding to the current health
	 * of the wagon.
	 * Good - 0 to 34
	 * Fair - 35 to 64
	 * Poor - 65 to 104
	 * Very Poor - 105 to 140
	 * @return - String corresponding to the wagon health value.
	 */
	public static String getHealthDescript()
	{
		if (memberHealth <35) { return "Good";}
		else if (memberHealth <65) { return "Fair";}
		else if (memberHealth <105) { return "Poor";}
		else if (memberHealth <=140) { return "Very Poor";}
		else {return " ";}
	}
	
}