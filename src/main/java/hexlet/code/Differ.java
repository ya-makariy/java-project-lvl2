package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Differ {

    public static String fileToString(String filepath) throws IOException {
        Path filename = Path.of(filepath);
        return Files.readString(filename);
    }

    public static Map getData(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    public static String getStylish(Map differdata) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(differdata)
                .replaceFirst("\\{", "\\{\n  ")
                .replaceAll("\"", "")
                .replaceAll(",", ",\n  ")
                .replaceAll("\\}", "\n\\}");
    }

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
                if (sorted.get(key1).equals(data1.get(key1))) {
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

        return getStylish(newdata);

    }

}
