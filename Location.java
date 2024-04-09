package OregonTrailMVP;
import java.lang.Object;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/** Location class, contains information on traveling and the date
	@author Grace Maag
	@version 1.0 9 April 2024
	@filename WagonMember.java
 */
public class Location 
{
	public Location() {	}

	/*
	 * Set up all objects and variables relating to the current in-ganme date
	 * For this MVP, the wagon will begin it's journey on April 1st, 1848
	 */
	LocalDate currentDate = LocalDate.of(1848, 4,1);
	int day = currentDate.getDayOfMonth();
	DayOfWeek dOW = currentDate.getDayOfWeek();
	Month month = currentDate.getMonth();
	int year =  currentDate.getYear();
	
	
	/*
	 * Initialize all variables relating to distances
	 * or traveling
	 */
	int distanceToNext = 102; 
	int totalTraveled = 0;
	int speed = 12;
	
	/**
	 * Combines all date variables and objects into one string
	 * @return a string as a date - Example: SATURDAY, APRIL 1, 1848
	 */
	public String DateString()
	{		
		String date = ""+ dOW +", "+ month + " " + day + ", " + year;
		return date;
	}
	
	//this also might need to go in landmark subclass
	public void setDistanceToLandmark(int distance)
	{
		distanceToNext = distance;
	}
	
	public void setSpeed(int s)
	{
		speed = s;
	}
	
	//Updates all date values to that of the next day
	public void nextDay()
	{
		currentDate = currentDate.plusDays(1);
		day = currentDate.getDayOfMonth();
		dOW = currentDate.getDayOfWeek();
		month = currentDate.getMonth();
		year =  currentDate.getYear();
	}
	
	/**
	 * Changes all distance values for one day of traveling
	 */
	public void travel() 
	{
		nextDay();
		if((distanceToNext - speed) < 0)
		{
			totalTraveled += distanceToNext;
			distanceToNext = 0;
		}
		else 
		{	
		totalTraveled += speed;  
		distanceToNext -= speed;
		}
	}
	public void setDistanceToNext(int distance)
	{
		distanceToNext = distance;
	}
	
	/**
	 * gets the total distance that the wagon has traveled throughout the game
	 * @return totalTraveled - the total distance traveled
	 */
	public int getTotalTraveled()
	{
		return totalTraveled;
	}
	
	/**
	 * gets the distance until the wagon will reach the next landmark
	 * @return distanceToNext - the distance until the next landmark
	 */
	public int getDistanceToNext() 
	{
		return distanceToNext;
		
	}
	
	/*
	 * Increments one day without changing the distance variables 
	 */
	public void rest() 
	{
			nextDay();
	}
	
	
}