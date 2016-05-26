package beardreamembrace.webloginmaven;

import java.time.Duration;

public class Utils
{
	
	public static String format (Duration sessionTime)
	{
		
		Duration temp;
		long days = sessionTime.toDays();
		temp = sessionTime.minusDays(days);
		long hours = temp.toHours();
		temp = temp.minusHours(hours);
		long minutes = temp.toMinutes();
		temp = temp.minusMinutes(minutes);
		long seconds = temp.getSeconds();
		temp = temp.minusSeconds(seconds);
		long micros = temp.getNano()/1000;
		
		
		
		return String.format("%d:%d:%d:%d.%d", days, hours, minutes, seconds, micros);
	}

}
