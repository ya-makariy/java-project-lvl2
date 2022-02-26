package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Differ{

    public static String fileToString(String filepath) throws IOException {
        Path filename = Path.of(filepath);
        return Files.readString(filename);
    }

    public static Map getData (String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    public static String getStylish (Map differdata) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(differdata)
                .replaceFirst("\\{", "\\{\n  ")
                .replaceAll("\"", "")
                .replaceAll(",", ",\n  ")
                .replaceAll("\\}", "\n\\}");
    }


    public static String generate(Map datafile1, Map datafile2) throws JsonProcessingException {
        //в другой метод
        TreeMap fullData = new TreeMap<>();
        fullData.putAll(datafile1);
        fullData.putAll(datafile2);
        //return fullData.toString();

        Map<String, Object> datadata = new LinkedHashMap(fullData);

        //sorting
        Map<String, Object>  newdata = new LinkedHashMap();
        for (String key1: datadata.keySet()) {
            if (datafile1.containsKey(key1) && datafile2.containsKey(key1)) {
                if (datadata.get(key1).equals(datafile1.get(key1))) {
                    String key = "  " + key1;
                    newdata.put(key, datadata.get(key1));
                } else {
                    String mkey = "- " + key1;
                    String pkey = "+ " + key1;
                    newdata.put(mkey, datafile1.get(key1));
                    newdata.put(pkey, datadata.get(key1));
                }
            } else if (datafile1.containsKey(key1)) {
                String key = "- " + key1;
                newdata.put(key, datadata.get(key1));
            } else if (datafile2.containsKey(key1)) {
                String key = "+ " + key1;
                newdata.put(key, datadata.get(key1));
            }
        }

        return getStylish(newdata);

    }

}