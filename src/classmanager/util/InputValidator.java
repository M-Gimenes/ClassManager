package classmanager.util;

public class InputValidator {

    public static boolean isValidFloat(String text) {
        if (text == null || text.trim().isEmpty()) return false;
        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidInteger(String text) {
        if (text == null || text.trim().isEmpty()) return false;
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
