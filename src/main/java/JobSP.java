import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobSP {
    enum eInf {
        COUNTER
    }
    //Алгоритм Беллмана Форда
    public static void main(String[] args) {
        Configuration conf = new Configuration();
//        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        try {
            FileSystem fs = FileSystem.get(conf);
            int i = 0;
            long num = 1;
            long tmp = 0;
            while (num > 0) {
                i++;
                conf.setInt("run.counter", i);
                Job job = Job.getInstance(conf);
                job.setJarByClass(JobSP.class);
                job.setMapperClass(ShortestPathMapper.class);
                job.setReducerClass(ShortestPathReducer.class);
                job.setMapOutputKeyClass(Text.class);
                job.setMapOutputValueClass(Text.class);

                //Формат key-value   первый - key，остальные - value
                job.setInputFormatClass(KeyValueTextInputFormat.class);

                //Определить адресс I/O
                if (i == 1)
                    FileInputFormat.addInputPath(job, new Path("input/"));
                else
                    FileInputFormat.addInputPath(job, new Path("output/" + (i - 1)));
                Path outPath = new Path("output/" + i);
                if (fs.exists(outPath)) {
                    fs.delete(outPath, true);
                }
                FileOutputFormat.setOutputPath(job, outPath);
                boolean b = job.waitForCompletion(true);
                if (b) {
                    num = job.getCounters().findCounter(eInf.COUNTER).getValue();
                    if (num == 0) {
                        System.out.println("Выполнено "+i+" раз");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
