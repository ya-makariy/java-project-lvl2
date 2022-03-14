package hexlet.code.formatters;

public class PlainFormatter {
    public static String wasRemoved(String key) {
        return String.format("Property '%s' was removed\n", key);
    }

    public static String wasAdded(String key, Object oldvalue) {
        return String.format("Property '%s' was added with value: %s\n", key, objCheck(oldvalue));
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
