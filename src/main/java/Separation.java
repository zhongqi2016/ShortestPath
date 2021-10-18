import org.apache.commons.lang3.StringUtils;

public class Separation {
    String distance; //Расстояние от начального узла
    String nodes;  //Расстояние до других узлов

    Separation() {
        distance = "";
        nodes = "";
    }

    Separation(String value) {
        String[] strs = StringUtils.split(value, ' ');
        this.distance = strs[0];
        if (strs.length < 2) return;
        this.nodes = strs[1];
    }

    String getString() {
        return distance + " " + nodes;
    }

}
