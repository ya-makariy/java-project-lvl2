package hexlet.code.formatters;

public class StylishFormatter {
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
