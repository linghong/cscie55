package cscie55.hw8.p1;
import cscie55.hw8.p1.Link;

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
import org.apache.hadoop.mapreduce.Mapper;

public class CountByTag extends Configured implements Tool {

    public static void main(String args[]) throws Exception {
		int res = ToolRunner.run(new CountByTag(), args);
		System.exit(res);
    }

    public int run(String[] args) throws Exception {
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);

		Configuration conf = getConf();
		Job job = new Job(conf, this.getClass().toString());

		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);

		job.setJobName("Count By Tag");
		job.setJarByClass(CountByTag.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class Map extends Mapper<LongWritable, Text, Text, Text> {

		private final static IntWritable one = new IntWritable(1);
		private Link link;
		private Text url = new Text();
		private Text tags =new Text();

	    @Override
		public void map(LongWritable key, Text value,
				Mapper.Context context) throws IOException, InterruptedException {				
			
			String line = value.toString();

			//get the link object	
			link=Link.parse(line);

			//convert arrayList tags to a string
			String sumString ="";
			for (String tag: link.tags()){				
				sumString =sumString+tag+",";
			}
			
			//map
			tags.set(sumString);
			url.set(link.url());
         	context.write(url, tags);
    	}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
				
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			//the string that combine a url's all tags 
			String tagsString="";

			//System.out.println("start for");
			for (Text value: values){

				//get the tag string that was sent by the map class
				String tagLine =value.toString();

				//for the rest tag string input, check whether a new tag has been already added on the tagsString.
				//If not, add the tag to that tagsString
				for (String tag: tagLine.split(",")){

					if(!tagsString.contains(tag +",")){
						tagsString =tagsString +tag+",";
					}
				}						
			}

			//get rid of the last ','
			tagsString=tagsString.substring(0,tagsString.length()-1);
			
			context.write(key, new Text(tagsString));							
		}

	}
}