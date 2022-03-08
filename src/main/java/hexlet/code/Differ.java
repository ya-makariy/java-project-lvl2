package hexlet.code;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Differ {

    public static LinkedHashMap<String, Object> sorteddata(Map datafile1, Map datafile2) {
        TreeMap<String, Object> fullData = new TreeMap<>();
        fullData.putAll(datafile1);
        fullData.putAll(datafile2);
        //return fullData.toString();

        //udalit
        return (LinkedHashMap<String, Object>) new LinkedHashMap(fullData);
    }

    public static String generate(Map data1, Map data2, Map<String, Object> sorted) throws JsonProcessingException {

        Map<String, Object>  newdata = new LinkedHashMap();
        for (String key1: sorted.keySet()) {
            if (data1.containsKey(key1) && data2.containsKey(key1)) {
                if (sorted.get(key1) == (data1.get(key1))) {
                    String key = "  " + key1;
                    newdata.put(key, sorted.get(key1));
                } else {
                    String mkey = "- " + key1;
                    String pkey = "+ " + key1;
                    newdata.put(mkey, data1.get(key1));
                    newdata.put(pkey, sorted.get(key1));
                }
            } else if (data1.containsKey(key1)) {
                String key = "- " + key1;
                newdata.put(key, sorted.get(key1));
            } else {
                String key = "+ " + key1;
                newdata.put(key, sorted.get(key1));
            }
        }

        return Parser.getStylish(newdata);

    }

}
