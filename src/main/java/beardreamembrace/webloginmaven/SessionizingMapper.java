package beardreamembrace.webloginmaven;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SessionizingMapper extends Mapper<Object, Text, Text, DoubleWritable>
{

	@Override
	protected void map(Object key, Text value, Context context)
			throws IOException, InterruptedException 
	{
		
		String delim = "\" \"|\" | \"|\"";
		String line = value.toString();
		
		String[] tokens = line.split(delim); // split line into tokens that are between the quotations mark (ie url, browser info and others)
		String[] tokens1 = tokens[0].split(" "); // get separate tokens for ip and time
		String ip = tokens1[2].split(":")[0];// get ip without port info
		String hitTime = tokens1[0];
		String url = tokens[1].split(" ")[1]; //get url ignoring REST info
		
		double seconds = Double.parseDouble(hitTime.split(":")[2].substring(0,5));
		Text ipText = new Text(ip);
		TextPair dateUrl = new TextPair(hitTime, url);
		
		System.out.println(ipText + "\t" + seconds);
		context.write(ipText, new DoubleWritable(seconds));
	}
	
}
