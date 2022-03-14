package hexlet.code.formatters;

import java.util.Map;
import static hexlet.code.Differ.KEYLENGTH;

public class PlainFormatter {

    public static String generatePlain(Map data1, Map data2, Map<String, Object> diff) {
        StringBuilder output = new StringBuilder();
        for (String key: diff.keySet()) {
            String oKey = key.substring(KEYLENGTH);
            switch (key.substring(0, KEYLENGTH)) {
                case("DEL"):
                    output.append(wasRemoved(oKey));
                    break;
                case("ADD"):
                    output.append(wasAdded(oKey, data2.get(oKey)));
                    break;
                case ("CHV"):
                    output.append(wasUpdated(oKey, data1.get(oKey), data2.get(oKey)));
                    break;
                default:
                    output.append(unchanged());
                    break;
            }
        }
        return output.substring(0, output.length() - 1);
    }

    public static String wasRemoved(String key) {
        return String.format("Property '%s' was removed\n", key);
    }

    public static String wasAdded(String key, Object value) {
        return String.format("Property '%s' was added with value: %s\n", key, objCheck(value));
    }

    public static String wasUpdated(String key, Object oldValue, Object newValue) {
        return String.format("Property '%s' was updated. From %s to %s\n", key, objCheck(oldValue), objCheck(newValue));
    }

    public static String unchanged() {
        return "";
    }

    public static Object objCheck(Object o) {
        if (o == null) {
            return o;
        }
        String className = o.getClass().toString();
        if (className.contains("java.lang.")) {
            if (className.contains("String")) {
                return String.format("'%s'", o);
            } else {
                return o;
            }
        }
        return "[complex value]";
    }
}
