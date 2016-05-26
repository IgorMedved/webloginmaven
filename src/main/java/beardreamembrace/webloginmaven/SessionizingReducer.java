package beardreamembrace.webloginmaven;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class SessionizingReducer extends Reducer<Text, TextPair, TextPair, TextArrayWritable>
{
	public static final long TIME_WINDOW = (long) (10E6*60*15); // 15 MIN
	
	@Override
	public void reduce(Text key, Iterable<TextPair> values, Context context)
	{
		
		System.out.println("Do I get here");
		String  dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		
		Iterator<TextPair> value = values.iterator();
		LocalDateTime sessionStart=null;

		TextPair tempPair = value.hasNext()? value.next(): null;
		
		if (tempPair!=null)
		{
			List<Text> urls = new ArrayList<>();

			sessionStart = LocalDateTime.parse(tempPair.getDate().toString(), formatter);
			urls.add(value.next().getUrl());
		
		
			LocalDateTime lastTime =  sessionStart;
			LocalDateTime currentTime;
			Duration clickTime;
			

			while (value.hasNext())
			{
				tempPair = value.next();
				currentTime = LocalDateTime.parse(tempPair.getDate().toString(), formatter);
				clickTime = Duration.between(lastTime, currentTime);
				
				if (clickTime.toNanos()/1000 < TIME_WINDOW)
				{
					lastTime = currentTime;
					urls.add(tempPair.getUrl());
				}
				else // write down old session initiate new one
				{
					Duration sessionTime = Duration.between(sessionStart, currentTime);
					TextPair outKey = new TextPair (key, new Text(Utils.format(sessionTime)));
					
					try
					{
						context.write(outKey, new TextArrayWritable ((Text[])urls.toArray()));
					} catch (IOException | InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			
			}
		}
		
	}
	
	

	


	
}