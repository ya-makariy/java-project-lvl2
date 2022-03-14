package hexlet.code;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static hexlet.code.formatters.JsonFormater.generateJson;
import static hexlet.code.formatters.PlainFormatter.generatePlain;
import static hexlet.code.formatters.StylishFormatter.generateStylish;

public class Differ {
    public static final int KEYLENGTH = 3;

    public static LinkedHashMap<String, Object> sortedData(Map datafile1, Map datafile2) {
        TreeMap<String, Object> fullData = new TreeMap<>();
        fullData.putAll(datafile1);
        fullData.putAll(datafile2);
        return (LinkedHashMap<String, Object>) new LinkedHashMap(fullData);
    }


    public static String generate(String filepath1, String filepath2, String formatName) throws Exception {
        Map data1 = Parser.getData(filepath1);
        Map data2 = Parser.getData(filepath2);
        LinkedHashMap<String, Object> diffData = createDiff(data1, data2, sortedData(data1, data2));

        switch (formatName) {
            case ("plain"):
                return generatePlain(data1, data2, diffData);
            case ("stylish"):
                return generateStylish(data1, data2, diffData);
            case ("json"):
                return generateJson(data1, data2, diffData);
            default:
                throw new IllegalArgumentException(String.format("ERROR: Don't know '%s' format type", formatName));
        }
    }

    public static String chnDetector(String key, Map data1, Map data2) {
        if (!data1.containsKey(key)) {
            return "ADD";
        } else if (!data2.containsKey(key)) {
            return "DEL";
        } else if (hashCode(data2.get(key))  == hashCode(data1.get(key))) {
            return "   ";
        } else {
            return "CHV";
        }
    }

    public static LinkedHashMap<String, Object> createDiff(Map data1, Map data2, Map<String, Object> sorted) {
        LinkedHashMap<String, Object> diff = new LinkedHashMap<>();
        String p;
        for (String key: sorted.keySet()) {
            p = chnDetector(key, data1, data2);
            diff.put(p + key, sorted.get(key));
        }
        return diff;
    }


    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }
}
