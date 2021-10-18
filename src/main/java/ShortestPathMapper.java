import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Iterator;

public class ShortestPathMapper extends Mapper<Text, Text, Text, Text> {

    //Алгоритм Беллмана Форда
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String str_value = value.toString();

        Separation sep = new Separation(str_value);
        if (!sep.distance.equals("inf")) {
            Node node = new Node(sep);
            int dis = node.distance;
            if (node.connect) {
                Iterator iterator = node.subNodes.iterator();
                while (iterator.hasNext()) {
                    Node.SubNode sn = (Node.SubNode) iterator.next();
                    String str=String.valueOf(sn.distance + dis);
                    Text text = new Text(str);
                    context.write(new Text(sn.name), text);
                }
            }
        }
        context.write(key, value);
    }
}
