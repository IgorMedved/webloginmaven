package beardreamembrace.webloginmaven;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TestReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable>
{
	@Override 
	public void reduce (Text key, Iterable<DoubleWritable> values, Context context)
	{
		while (values.iterator().hasNext())
		{
			try
			{
				context.write(key, values.iterator().next());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
