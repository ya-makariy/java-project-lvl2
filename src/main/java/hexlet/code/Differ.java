package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
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

        Map<String, Object> data1 = Parser.getData(content1, getContentType(filepath1));
        Map<String, Object> data2 = Parser.getData(content2, getContentType(filepath2));

        List<Map<String, Object>> diff = createDiff(data1, data2);
        return switch (format) {
            case ("plain") -> generatePlain(diff);
            case ("stylish") -> generateStylish(diff);
            case ("json") -> generateJson(diff);
            default -> throw new IllegalArgumentException(String.format("ERROR: Don't know '%s' format type", format));
        };
    }

    public static List<Map<String, Object>> createDiff(Map<String, Object> data1, Map<String, Object> data2) {
        List<Map<String, Object>> diff = new LinkedList<>();
        for (String key: sortKeys(data1, data2)) {
            Map<String, Object> node = new HashMap<>();
            node.put("status", determineStatus(key, data1, data2));
            node.put("fieldName", key);
            node.put("value1", data1.get(key));
            node.put("value2", data2.get(key));
            diff.add(node);
        }
        return diff;
    }

    public static Set<String> sortKeys(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> sortedKeys = new TreeSet<>(data1.keySet());
        sortedKeys.addAll(data2.keySet());
        return sortedKeys;
    }

    private static String determineStatus(String key, Map<String, Object> data1, Map<String, Object> data2) {
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
