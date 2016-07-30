package cscie55.hw8.p2;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.lang.reflect.*;

public class CountByTime extends Configured implements Tool {
	//assume the time period covers the full two day period
	public static String timeStart = "08-11-2009";
	public static String timeEnd = "09-11-2009";

    public static void main(String args[]) throws Exception {
       		
        int res = ToolRunner.run(new CountByTime(), args);
		System.exit(res);

    }

    public int run(String[] args) throws Exception {
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");	
		Date beginDate =simpleDateFormat.parse(timeStart);		
    	Date endDate =simpleDateFormat.parse(timeEnd);

		Configuration conf = getConf();
		conf.setLong("startTime", beginDate.getTime()/1000);
		//the time are supposed to cover the period from 0:00 AM November 8, 2009, to 11:59 PM November 9, 2009
		//hence, it needs to add 1 more day to the calculation, i.e. 24*60*60 seconds
		conf.setLong("endTime", endDate.getTime()/1000+24*60*60);
		
		Job job = new Job(conf, this.getClass().toString());

		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);

		job.setJobName("Count By Time");
		job.setJarByClass(CountByTime.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private Text url = new Text();
		private Link link;
	    
        @Override
		public void map(LongWritable key, Text value,
				Mapper.Context context) throws IOException, InterruptedException {				
			
            String line = value.toString();
			
            //get the link object	
			link=Link.parse(line);
			
            //get url	
			Long urlTime=link.timestamp();
		
			Configuration conf = context.getConfiguration();
			// starting seconds: 1257667200
	        Long startSeconds= conf.getLong("startTime", 0);
	        //endseconds:1257840000
	        Long endSeconds= conf.getLong("endTime", 0);

	        //only when timestamp meet the requirement, then find the url
			if(urlTime>startSeconds &&urlTime<endSeconds){

				url.set(link.url());
	         	context.write(url, one);
	         }

    	}
	}


	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;

            //count the one with same url
			for (IntWritable value : values) {

		    	sum += value.get();
			}

			context.write(key, new IntWritable(sum));
		}
	}
}