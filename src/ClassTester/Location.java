package ClassTester;
import java.lang.Object;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Calendar.Builder;

public class Location 
{
	public Location() 
	{
		
	}
	int currentSpeed = 12; //current speed of wagon, values can range from 12 to 20 miles traveled per day.
	

	//date setup 
	LocalDate currentDate = LocalDate.of(1848, 3,1);
	int day = currentDate.getDayOfMonth();
	DayOfWeek dOW = currentDate.getDayOfWeek();
	Month month = currentDate.getMonth();
	int year =  currentDate.getYear();
	
	//300 is just a placeholder value, each landmark will have their own.
	//this might need to be done in the subclass instead
	int distanceToNext = 300;
	
	int totalTraveled = 0;
	int speed = 12;
	
	// returns the date in a nice string format
	public String DateString()
	{		
		String date = ""+ dOW +". "+ month + " " + day + ", " + year;
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
	
	//moves to the next day
	public void nextDay()
	{
		currentDate = currentDate.plusDays(1);
		day = currentDate.getDayOfMonth();
		dOW = currentDate.getDayOfWeek();
		month = currentDate.getMonth();
		year =  currentDate.getYear();
	}
	
	//changes all values for 1 day of traveling.
	//we are going to have to add in the food consumption for
	//1 day here too.
	public void travel() 
	{
		nextDay();
		totalTraveled += speed;
		distanceToNext -=speed;
		if(distanceToNext <= 0) 
		{
			distanceToNext = 400;
			//not sure what to do here
		}
	}
	
	public int getTotalTraveled()
	{
		return totalTraveled;
	}
	
	public int getDistanceToNext() 
	{
		return distanceToNext;
	}
	
	//just a rest method, food consumption just needs to be added. 
	public void rest(int daysToRest) 
	{
		for(int i = 0; i <daysToRest; i ++) 
		{
			nextDay();
		}
	}
	
	
}