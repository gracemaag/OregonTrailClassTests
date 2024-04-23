package OregonTrailMVP;

import java.time.Month;

public class Weather
{
	int temp = 0;
	public int getCurrentTemperature(Month month, int distance) 
	{
		int numMonth = month.getValue();
		double random = Math.random() * 30;
		// mountains coldest, desert middle, plains, warmest (still gets snow)
		// mountains and plains both get same amount of snow, desert gets very little
		if(numMonth == 1 || numMonth == 2 || numMonth == 12 || numMonth == 3)
		{
			if (distance < 700) temp = (int) (15 + random) ;
			if (distance < 1200) temp = (int) (5 + random);
			if (distance < 1200)  temp = (int) (0 + random);
		}
		if(numMonth == 4 || numMonth == 5 || numMonth == 10 || numMonth == 11)
		{
			if (distance < 700) temp = (int) (40 + random) ;
			if (distance < 1200) temp = (int) (30 + random) ;
			if (distance < 1200) temp = (int) (25 + random) ;
		}
		
		if(numMonth == 6 || numMonth == 7 || numMonth == 8 || numMonth == 9)
		{
			if (distance < 700) temp = (int) (50 + random) ;
			if (distance < 1200) temp = (int) (60 + random) ;
			if (distance < 1200) temp = (int) (50 + random) ;
		}
		if (temp > 70) return 4;
		else if (temp >50) return 3;
		else if (temp > 30) return 2;
		else if (temp > 10) return 1;
		else return 0;
	}
	public int weatherRandomEvent(int distance) 
	{
		double random = Math.random() * 100;
		if(temp < 30) 
		{
			
		}
		return 0;
	}


	
}

