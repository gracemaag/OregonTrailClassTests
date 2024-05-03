package OregonTrailV4;

import java.time.Month;
/**
*
* Weather Class. Contains values to store the amount of recent rain or snow, the 
* distances of different categories of weather, temperature values, and past weather 
* events. This class can randomly generate a temperature value based on the month and
* current location. The class also generates a random weather event each day - which can 
* include rain, snow, thunderstorms, sunny skies, cloudy skies, and blizzards.
* @author Grace Maag
* @version 1.0 29 April 2024
* @filename Weather.java
* 
*/


public class Weather
{
	//Initialize Base values for rain, snow, and temperature
	private int temp = 45;
	private double currentRainfall = 0;
	private double currentSnowfall = 0;
	private double pastRainEvent = 0;
	private double pastSnowEvent = 0;
	
	// Initialize some probability related variables
	private final double REPEATVAL = 50.0;
	private final int TEMPVARY = 30;
	private final int EVENTPROBABILITY = 5;
	private final int PRECIPTYPEPROBABILITY = 30;
	
	// Temperature value of Freezing point
	private final double FREEZETEMP = 32;
	
	// Different distances of sections on the trail
	private final int PLAINSDIST = 700;
	private final int DESERTDIST = 1200;
	private final int MOUNTAINDIST = 2000;
	
	// minimum values for each temperature range
	private final int VERYHOTMIN = 90;
	private final int HOTMIN = 70;
	private final int WARMMIN = 50;
	private final int COOLMIN = 30;
	private final int COLDMIN = 10;
	
	// values based off of the temperatures above
	private final int VERYHOTVAL = 5;
	private final int HOTVAL = 4;
	private final int WARMVAL = 3;
	private final int COOLVAL = 2;
	private final int COLDVAL = 1;
	private final int VERYCOLDVAL = 0;
	
	// precipitation values and multipliers
	private final double LIGHTPRECIP = .2;
	private final double HEAVYPRECIP = .8;
	private final double DAILYRAINLOSS = 0.1;
	private final double DAILYSNOWLOSS = 0.03;
	private final int SNOWMULTIPLIER = 10;
	
	// the current weather state - Sunny Skies is a place holder
	private String weatherMessage = "Sunny Skies";
	
	
	/**
	 * Creates a temperature value that is randomly generated, but also dependant on 
	 * the curernt month and location. 
	 * @param month
	 * @param distance
	 * @return
	 */
	public int getCurrentTemperature(Month month, int distance) 
	{
		
		//repeats past day's weather - 50% chance of happening
		double randomRepeat = Math.random() * 100 + 1;
		if(randomRepeat > REPEATVAL) 
		{
			if (temp > VERYHOTMIN) return VERYHOTVAL;
			else if (temp > HOTMIN) return HOTVAL;
			else if (temp > WARMMIN) return WARMVAL;
			else if (temp > COOLMIN) return COOLVAL;
			else if (temp > COLDMIN) return COLDVAL;
			else return VERYCOLDVAL;
		}
		double random = Math.random() * TEMPVARY + 1;
		
		// Gets temperatures for the coldest months
		if(month == Month.JANUARY || month == Month.FEBRUARY || month == Month.MARCH || month == Month.DECEMBER)
		{
			if (distance < PLAINSDIST) temp = (int) (15 + random) ;
			if (distance < DESERTDIST) temp = (int) (5 + random);
			else  temp = (int) (random);
		}
		// Gets temperatures for the fairest months - Spring and Fall
		if(month == Month.MAY || month == Month.APRIL ||  month == Month.NOVEMBER || month == Month.OCTOBER)
		{
			if (distance < PLAINSDIST) temp = (int) (40 + random) ;
			if (distance < DESERTDIST) temp = (int) (30 + random) ;
			else temp = (int) (25 + random) ;
		}
		
		// Gets temperatures for the warmest months 
		if(month == Month.JUNE || month == Month.JULY ||  month == Month.AUGUST || month == Month.SEPTEMBER)
		{
			if (distance < PLAINSDIST) temp = (int) (50 + random) ;
			if (distance < DESERTDIST) temp = (int) (60 + random) ;
			else temp = (int) (50 + random) ;
		}
		
		//returns the weather value that is dependent on the current temperature
		if (temp > VERYHOTMIN) return VERYHOTVAL;
		else if (temp > HOTMIN) return HOTVAL;
		else if (temp > WARMMIN) return WARMVAL;
		else if (temp > COOLMIN) return COOLVAL;
		else if (temp > COLDMIN) return COLDVAL;
		else return VERYCOLDVAL;
	}
	
	
	/**
	 * Creates a daily random weather event that is dependent on temperature and distance
	 * Events can include thunderstorms, light/heavy rain/snow, sunny skies, cloudy skies, and blizzards
	 * @param distance - the current distance that has been traveled by the wagon
	 * @return a String that displays the current weather event
	 */
	public String weatherRandomEvent(int distance) 
	{
		//repeats past day's weather - 50% chance of happening
		double randomRepeat = Math.random() * 100 + 1;
		if(randomRepeat > REPEATVAL) 
		{
			currentRainfall+= pastRainEvent;
			currentSnowfall += pastSnowEvent;
			return weatherMessage;
		}
		
		double random = Math.random() * 100 + 1;
		// Bliizard random event - 5% chance of happening if temperature is low enough
		if(temp < FREEZETEMP && random < EVENTPROBABILITY) 
		{
			currentSnowfall += HEAVYPRECIP * SNOWMULTIPLIER;
			weatherMessage = "Blizzard! You got stuck in snow.";
		}
		// thunderstorm random event - 5% chance of happening if temperature is high enough
		else if(random < EVENTPROBABILITY && temp > FREEZETEMP)
		{
			currentRainfall+= HEAVYPRECIP;
			weatherMessage = "Thunderstorm and Heavy Rain";
		} 
		
		// Desert weather events - this is the driest and warmest stretch
		else if(distance < DESERTDIST && distance > PLAINSDIST)
		{
			if(temp < FREEZETEMP && random < 15)
			{
				//Snowfall - 10% chance of happening
				double heavyOrLight = Math.random() * 100 + 1;
				if(heavyOrLight > PRECIPTYPEPROBABILITY) 
				{
					currentSnowfall +=  LIGHTPRECIP * SNOWMULTIPLIER;
					weatherMessage = "Light snowfall";
				}
				else 
				{
					currentSnowfall +=  HEAVYPRECIP * SNOWMULTIPLIER;
					weatherMessage = "Heavy snowfall";
				}
			}
			
			
			else if(random < 15)
			{
				//rainfall - 10% chance of happening
				double heavyOrLight = Math.random() * 100 + 1;
				if(heavyOrLight > PRECIPTYPEPROBABILITY) 
				{
					currentRainfall += LIGHTPRECIP;
					weatherMessage = "Light rainfall";
				}
				else 
				{
					currentRainfall += HEAVYPRECIP;
					weatherMessage = "Heavy rainfall";
				}
			} 
			
			// cloudy skies  - 25% chance of happening
			else if(temp < FREEZETEMP && random < 40) 	weatherMessage = "Cloudy skies";
			// otherwise weather is sunny
			else	weatherMessage = "Sunny skies";
		}
		// Plains weather events - this is the most moderate stretch
		else if (distance <= PLAINSDIST) 
		{
			if(temp < FREEZETEMP && random < 25)
			{
				//Snowfall - 20% chance of happening
				double heavyOrLight = Math.random() * 100 + 1;
				if(heavyOrLight >  PRECIPTYPEPROBABILITY) 
				{
					currentSnowfall += LIGHTPRECIP * SNOWMULTIPLIER;
					weatherMessage = "Light snowfall";
				}
				else 
				{
					currentSnowfall +=  HEAVYPRECIP * SNOWMULTIPLIER;
					weatherMessage = "Heavy snowfall";
				}
			}
			else if ( random < 25 )
			{
				//rainfall - 20% chance of happening
				double heavyOrLight = Math.random() * 100 + 1;
				if(heavyOrLight > PRECIPTYPEPROBABILITY) 
				{
					currentRainfall +=  LIGHTPRECIP;
					weatherMessage = "Light rainfall";
				}
				else 
				{
					currentRainfall += HEAVYPRECIP;
					weatherMessage = "Heavy rainfall";
				}
		 	}
			// cloudy skies  - 25% chance of happening
			else if (random < 50)  { weatherMessage = "Cloudy Skies"; }
			// otherwise weather is sunny
			else weatherMessage = "Sunny Skies";
	 	}
		
		// Mountains weather events - the coldest and harshest stretch
		else 
		{
			if(temp < FREEZETEMP && random < 20)
			{
				// Snowfall - 15% chance of happening
				double heavyOrLight = Math.random() * 100 + 1;
				if(heavyOrLight > PRECIPTYPEPROBABILITY) 
				{
					currentSnowfall += LIGHTPRECIP * SNOWMULTIPLIER;
					weatherMessage = "Light snowfall";
				}
				else 
				{
					currentSnowfall += HEAVYPRECIP * SNOWMULTIPLIER;
					weatherMessage = "Heavy snowfall";
				}
			}
			else if (random < 20 )
			{
				//Rainfall - 15% chance of happening
				double heavyOrLight = Math.random() * 100 + 1;
				if(heavyOrLight > PRECIPTYPEPROBABILITY) 
				{
					currentRainfall += LIGHTPRECIP;
					weatherMessage = "Light rainfall";
				}
				else 
				{
					currentRainfall += HEAVYPRECIP;
					weatherMessage = "Heavy rainfall";
				}
			}
		// cloudy skies  - 30% chance of happening
		 else if (random < 50)  weatherMessage = "Cloudy Skies"; 
		// otherwise sky is sunny
		 else weatherMessage = "Error";
	 	}	
		return weatherMessage;
	}
	
	/**
	 * Changes the rain or snow amounts each day - dependent on if there 
	 * was precipitation that day or if the temperature is above freezing
	 */
	public void dailyChanges() 
	{
		if(weatherMessage.contains("rain") || weatherMessage.contains("snow"))
		{
			return;
		}
		if(temp >= FREEZETEMP && currentSnowfall > 0) 
		{
			if(currentSnowfall < 5) 
			{
				currentSnowfall = 0;
			}
			else currentSnowfall -= DAILYSNOWLOSS;
			return;
		}
		else currentRainfall -= (currentRainfall * DAILYRAINLOSS);
	}
	/**Gets the weather value and outputs a String relating to the type
	 * @param tempVal
	 * @return
	 */
	public String getTempStr(int tempVal) 
	{
		switch(tempVal)
		{
			case VERYCOLDVAL : return "Very Cold";
			case COLDVAL : return "Cold";
			case COOLVAL : return "Cool";
			case WARMVAL : return "Warm";
			case HOTVAL : return "Hot";
			default: return "Very Hot";
		}
	}	
	
	/**
	 * getter for the current amount of water it has rained
	 * @return current amount of rainfall that has accumulated
	 */
	public double getWater() 
	{
		return currentRainfall;
	}
	
}

