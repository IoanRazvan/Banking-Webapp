package util;

public class TransactionUtil {
    private static final float EURO_IN_RON = 4.84f;
    private static final float DOLLAR_IN_RON = 4.43f;
    private static final float EURO_IN_DOLLAR = 1.09f;

    public static float getFactor(String sourceCurrency, String targetCurrency) {
        if (targetCurrency.equalsIgnoreCase(sourceCurrency))
            return 1.0f;
        else if (targetCurrency.equalsIgnoreCase("RON")) {
            if (sourceCurrency.equalsIgnoreCase("DOLLAR"))
                return DOLLAR_IN_RON;
            else
                return EURO_IN_RON;
        }

        else if (targetCurrency.equalsIgnoreCase("EURO")) {
            if (sourceCurrency.equalsIgnoreCase("DOLLAR"))
                return 1/EURO_IN_DOLLAR;
            else
                return 1/EURO_IN_RON;
        }

        else {
            if (sourceCurrency.equalsIgnoreCase("EURO"))
                return EURO_IN_DOLLAR;
            else
                return 1/DOLLAR_IN_RON;
        }
    }
}
