import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ShortestPathReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String minDis = "inf";
        Separation minNode = new Separation();

        // Найти минимальное растояние
        for (Text text : values) {
            Separation sep = new Separation(text.toString());
            if (minDis.equals("inf")) {
                minDis = sep.distance;
            } else if (!sep.distance.equals("inf")) {
                int dis = Integer.parseInt(sep.distance);
                if (dis < Integer.parseInt(minDis)) {
                    minDis = sep.distance;
                }
            }
            if (sep.nodes != null && sep.nodes != "") {
                minNode = sep;
            }

        }

        //Если оценки не изменились – выход
        if (minNode.distance.equals("inf") ||
                (!minDis.equals("inf")&&Integer.parseInt(minDis)<Integer.parseInt(minNode.distance))) {
            context.getCounter(JobSP.eInf.COUNTER).increment(1L);
        }
        minNode.distance = minDis;
        String str = minNode.getString();
        context.write(key, new Text(str));

    }


}
