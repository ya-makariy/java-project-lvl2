package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static hexlet.code.formatters.JsonFormater.generateJson;
import static hexlet.code.formatters.PlainFormatter.generatePlain;
import static hexlet.code.formatters.StylishFormatter.generateStylish;

public class Differ {
    public static final int KEYLENGTH = 3;

    public static LinkedHashMap sortedData(Map datafile1, Map datafile2) {
        Map fullData = new TreeMap<>();
        fullData.putAll(datafile1);
        fullData.putAll(datafile2);
        return new LinkedHashMap(fullData);
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        String content1 = getString(filepath1);
        String content2 = getString(filepath2);

        Map data1 = Parser.getData(content1, getContentType(filepath1));
        Map data2 = Parser.getData(content2, getContentType(filepath1));

        Map diffData = createDiff(data1, data2, sortedData(data1, data2));

        switch (format) {
            case ("plain"):
                return generatePlain(data1, data2, diffData);
            case ("stylish"):
                return generateStylish(data1, data2, diffData);
            case ("json"):
                return generateJson(data1, data2, diffData);
            default:
                throw new IllegalArgumentException(String.format("ERROR: Don't know '%s' format type", format));
        }
    }

    public static String chnDetector(String key, Map data1, Map data2) {
        if (!data1.containsKey(key)) {
            return "ADD";
        } else if (!data2.containsKey(key)) {
            return "DEL";
        } else if (Objects.equals(data2.get(key), data1.get(key))) {
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
