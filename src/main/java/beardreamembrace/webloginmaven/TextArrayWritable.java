package beardreamembrace.webloginmaven;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;

public class TextArrayWritable extends ArrayWritable
{
	public TextArrayWritable(Text[] values)
	{
		super(Text.class, values);
	}
	
	public TextArrayWritable()
	{
		super (Text.class);
	}

	@Override
	public Text[] get()
	{
		return (Text[]) super.get();
	}

	@Override
	public String toString()
	{
		Text[] values = get();

		StringBuilder sb = new StringBuilder(values[0].toString());
		for (int i = 1; i < values.length; i++)
		{
			sb.append(" ").append(values[i]);
		}

		return sb.toString();
	}
	
}
