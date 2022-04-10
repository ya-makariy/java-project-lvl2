package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import static hexlet.code.formatters.JsonFormater.generateJson;
import static hexlet.code.formatters.PlainFormatter.generatePlain;
import static hexlet.code.formatters.StylishFormatter.generateStylish;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        String content1 = getString(filepath1);
        String content2 = getString(filepath2);

        Map data1 = Parser.getData(content1, getContentType(filepath1));
        Map data2 = Parser.getData(content2, getContentType(filepath1));

        List<Map<String, Object>> diff = createDiff(data1, data2);
        return switch (format) {
            case ("plain") -> generatePlain(diff);
            case ("stylish") -> generateStylish(diff);
            case ("json") -> generateJson(diff);
            default -> throw new IllegalArgumentException(String.format("ERROR: Don't know '%s' format type", format));
        };
    }

    public static List<Map<String, Object>> createDiff(Map data1, Map data2) {
        List<Map<String, Object>> diff = new LinkedList<>();
        Map<String, Object> sorted = sortedData(data1, data2);
        for (String key: sorted.keySet()) {
            Map<String, Object> statusMap = new LinkedHashMap<>();
            statusMap.put("status", chnDetector(key, data1, data2));
            statusMap.put("fieldName", key);
            statusMap.put("value1", data1.get(key));
            statusMap.put("value2", data2.get(key));
            diff.add(statusMap);
        }
        return diff;
    }

    public static LinkedHashMap sortedData(Map data1, Map data2) {
        Map fullData = new TreeMap<>();
        fullData.putAll(data1);
        fullData.putAll(data2);
        return new LinkedHashMap(fullData);
    }

    public static String chnDetector(String key, Map data1, Map data2) {
        if (!data1.containsKey(key)) {
            return "ADD";
        } else if (!data2.containsKey(key)) {
            return "DEL";
        } else if (Objects.equals(data2.get(key), data1.get(key))) {
            return "DNC";
        } else {
            return "CHV";
        }
    }

    public static String getString(String filepath) throws IOException {
        return Files.readString(Path.of(filepath));
    }

    public static String getContentType(String filepath) {
        if (filepath.endsWith(".json")) {
            return "JSON";
        } else if (filepath.endsWith(".yml") || filepath.endsWith(".yaml")) {
            return "YAML";
        } else {
            throw new IllegalArgumentException("Don't know this extension!");
        }
    }
}
