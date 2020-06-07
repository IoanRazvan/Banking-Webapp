package util;

public class ValidationUtil {
    public static float validAmount(String s) throws IllegalArgumentException {
        float result = Float.parseFloat(s);
        if (result < 0)
            throw new IllegalArgumentException();
        return result;
    }

    public static boolean validPhoneNumber(String s) {
        return s.matches("07\\d{8}");
    }
}
