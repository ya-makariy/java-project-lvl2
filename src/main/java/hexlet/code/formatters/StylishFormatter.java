package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class StylishFormatter {
    public static String generateStylish(List<Map<String, Object>> diff) {
        StringBuilder output = new StringBuilder("{\n");
        for (Map<String, Object> statusMap: diff) {
            switch ((String) statusMap.get("status")) {
                case ("DEL") -> output.append(wasRemoved((String) statusMap.get("fieldName"),
                        statusMap.get("value1")));
                case ("ADD") -> output.append(wasAdded((String) statusMap.get("fieldName"),
                        statusMap.get("value2")));
                case ("CHV") -> output.append(wasUpdated((String) statusMap.get("fieldName"),
                        statusMap.get("value1"), statusMap.get("value2")));
                default -> output.append(unchanged((String) statusMap.get("fieldName"),
                        statusMap.get("value1")));
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
