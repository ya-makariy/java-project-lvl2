package hexlet.code.formatters;

import java.util.Map;
import static hexlet.code.Differ.KEYLENGTH;

public class StylishFormatter {

    public static String generateStylish(Map data1, Map data2, Map<String, Object> diff) {
        StringBuilder output = new StringBuilder("{\n");
        for (String key: diff.keySet()) {
            String oKey = key.substring(KEYLENGTH);
            switch (key.substring(0, KEYLENGTH)) {
                case("DEL"):
                    output.append(wasRemoved(oKey, data1.get(oKey)));
                    break;
                case("ADD"):
                    output.append(wasAdded(oKey, data2.get(oKey)));
                    break;
                case ("CHV"):
                    output.append(wasUpdated(oKey, data1.get(oKey), data2.get(oKey)));
                    break;
                default:
                    output.append(unchanged(oKey, data1.get(oKey)));
                    break;
            }
        }
        return output.append("}").toString();
    }

    public static String wasRemoved(String key, Object value) {
        return String.format("  - %s: %s\n", key, value);
    }

    public static String wasAdded(String key, Object value) {
        return String.format("  + %s: %s\n", key, value);
    }

    public static String wasUpdated(String key, Object oldValue, Object newValue) {
        return String.format("  - %s: %s\n", key, oldValue)
                + String.format("  + %s: %s\n", key, newValue);
    }

    public static String unchanged(String key, Object value) {
        return String.format("    %s: %s\n", key, value);
    }
}
