import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

public class Node  {
    int distance;  //Расстояние от начального узла
    boolean connect; //Расстояние бесконечно？
    LinkedList<SubNode> subNodes; //Расстояние до других узлов

    Node(Separation sep){
        subNodes = new LinkedList<SubNode>();
        setDistance(sep.distance);
        if (sep.nodes==null||sep.nodes.equals("")) return;
        String[] strs1 = StringUtils.split(sep.nodes, '-');
        for (int i = 0; i < strs1.length; ++i) {
            this.subNodes.add(new SubNode(strs1[i]));
        }
    }


    public void setDistance(String distance) {
        if (distance.equals("inf")) {
            connect = false;
            this.distance = 0;
        } else {
            connect=true;
            this.distance = Integer.valueOf(distance).intValue();
        }
    }

    class SubNode {
        String name;
        int distance;

        SubNode(String name,int distance){
            this.name=name;
            this.distance=distance;
        }
        SubNode(String str) {
            String[] strs = StringUtils.split(str, ',');
            this.name = strs[0];
            this.distance =Integer.valueOf(strs[1]).intValue(); ;
        }


    }
}



