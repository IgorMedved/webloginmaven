package beardreamembrace.webloginmaven;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;




public class WebLogChallengeDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new WebLogChallengeDriver(), args);
		System.exit(exitCode);
	}
	
	public int run(String[] args) throws Exception
	{
		if (args.length!=2)
		{
			System.err.printf ("Usage: %s <input path> <output path>", getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		//Define sessionizing job
		
		Configuration conf = getConf();
		//System.out.println(conf);
	    Job sessionizingJob = Job.getInstance(conf, "sessionizing");
	    sessionizingJob.setJarByClass(WebLogChallengeDriver.class);
	    sessionizingJob.setMapperClass(SessionizingMapper.class);
	   // job.setCombinerClass(IntSumReducer.class);
	    
	     sessionizingJob.setReducerClass(TestReducer.class);
	   // sessionizingJob.setOutputKeyClass(TextPair.class);
	   // sessionizingJob.setOutputValueClass(TextArrayWritable.class);
	    sessionizingJob.setMapOutputKeyClass(Text.class);
	    sessionizingJob.setMapOutputValueClass(DoubleWritable.class);
	    sessionizingJob.setOutputKeyClass(Text.class);
	    sessionizingJob.setOutputValueClass(DoubleWritable.class);
	    
	    sessionizingJob.setNumReduceTasks(1);
	    FileInputFormat.addInputPath(sessionizingJob, new Path(args[0]));
	    FileOutputFormat.setOutputPath(sessionizingJob, new Path(args[1]));
	    
	    if (sessionizingJob.waitForCompletion(true)){
	    	return 1;
	    }
	    
	   
	   /* Job maximumTimeJob = Job.getInstance(conf, "Longest session");
	    maximumTimeJob.setJarByClass(WebLogChallengeDriver.class);
	    maximumTimeJob.setMapperClass(MaximumTimeMapper.class);
	    maximumTimeJob.setCombinerClass(MaximumTimeReducer.class);
	    maximumTimeJob.setReducerClass(MaximumTimeReducer.class);
	    maximumTimeJob.setOutputKeyClass(Text.class);
	    maximumTimeJob.setOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(maximumTimeJob, new Path(System.getProperty("user.dir") +""))*/
	    
	    return 0;
	}

}