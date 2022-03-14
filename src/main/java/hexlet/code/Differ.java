package hexlet.code;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

public class Differ {


    public static LinkedHashMap<String, Object> sortedData(Map datafile1, Map datafile2) {
        TreeMap<String, Object> fullData = new TreeMap<>();
        fullData.putAll(datafile1);
        fullData.putAll(datafile2);
        return (LinkedHashMap<String, Object>) new LinkedHashMap(fullData);
    }


    public static String generate(String filepath1, String filepath2, String formatName) throws Exception {
        Map data1 = Parser.getData(filepath1);
        Map data2 = Parser.getData(filepath2);
        LinkedHashMap<String, Object> sortedmap = Differ.sortedData(data1, data2);
        switch (formatName) {
            case ("plain"):
                return plainDiffer(data1, data2, sortedmap);
            case ("stylish"):
                return stylishDiffer(data1, data2, sortedmap);
            default:
                throw new IllegalArgumentException(String.format("ERROR: Don't know '%s' format type", formatName));
        }
    }

    public static String plainDiffer(Map data1, Map data2, Map<String, Object> sorted) {
        StringBuilder output = new StringBuilder();
        for (String key: sorted.keySet()) {
            if (data1.containsKey(key) && data2.containsKey(key)) {
                if (hashCode(sorted.get(key))  == hashCode(data1.get(key))) {
                    output.append(PlainFormatter.unchanged());
                } else {
                    output.append(PlainFormatter.wasUpdated(key, data1.get(key), sorted.get(key)));
                }
            } else if (data1.containsKey(key)) {
                output.append(PlainFormatter.wasRemoved(key));
            } else {
                output.append(PlainFormatter.wasAdded(key, sorted.get(key)));
            }
        }
        return output.toString();
    }

    public static String stylishDiffer(Map data1, Map data2, Map<String, Object> sorted) {
        StringBuilder output = new StringBuilder("{\n");
        for (String key: sorted.keySet()) {
            if (data1.containsKey(key) && data2.containsKey(key)) {
                if (hashCode(sorted.get(key))  == hashCode(data1.get(key))) {
                    output.append(StylishFormatter.unchanged(key, sorted.get(key)));
                } else {
                    output.append(StylishFormatter.wasUpdated(key, data1.get(key), sorted.get(key)));
                }
            } else if (data1.containsKey(key)) {
                output.append(StylishFormatter.wasRemoved(key, data1.get(key)));
            } else {
                output.append(StylishFormatter.wasAdded(key, sorted.get(key)));
            }
        }
        return output.append("}\n")
                .toString();
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }
}
