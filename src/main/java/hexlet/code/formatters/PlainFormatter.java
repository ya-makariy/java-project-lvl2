package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class PlainFormatter {
    public static String generatePlain(List<Map<String, Object>> diff) {
        StringBuilder output = new StringBuilder();
        for (Map<String, Object> statusMap: diff) {
            switch ((String) statusMap.get("status")) {
                case ("DEL") -> output.append(wasRemoved((String) statusMap.get("fieldName")));
                case ("ADD") -> output.append(wasAdded((String) statusMap.get("fieldName"),
                        statusMap.get("value2")));
                case ("CHV") -> output.append(wasUpdated((String) statusMap.get("fieldName"),
                        statusMap.get("value1"), statusMap.get("value2")));
                default -> output.append(unchanged());
            }
        }
        return output.substring(0, output.length() - 1);
    }

    public static String wasRemoved(String key) {
        return String.format("Property '%s' was removed\n", key);
    }

    public static String wasAdded(String key, Object value) {
        return String.format("Property '%s' was added with value: %s\n", key, stringify(value));
    }

    public static String wasUpdated(String key, Object oldValue, Object newValue) {
        return String.format("Property '%s' was updated. From %s to %s\n", key,
                stringify(oldValue), stringify(newValue));
    }

    public static String unchanged() {
        return "";
    }

    public static Object stringify(Object o) {
        if (o == null) {
            return o;
        }
        if (o instanceof String) {
            return String.format("'%s'", o);
        } else if ((o instanceof Number) || (o instanceof Boolean)) {
            return o;
        }
        return "[complex value]";
    }
}
