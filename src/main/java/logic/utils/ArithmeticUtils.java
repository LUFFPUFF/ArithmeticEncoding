package logic.utils;

import logic.config.ArithmeticConfig;

public class ArithmeticUtils {

    public static double round(double value, int accuracy) {
        double factor = Math.pow(10, accuracy);
        return Math.round(value * factor) / factor;
    }

    public static double round(double value) {
        return Math.round(value * 1e8) / 1e8;
    }

    public static String floatToBinary(double value, int length) {
        StringBuilder binary = new StringBuilder();
        double fractional = value;

        for (int i = 0; i < length; i++) {
            fractional *= 2;
            if (fractional >= 1) {
                binary.append("1");
                fractional -= 1;
            } else {
                binary.append("0");
            }
        }

        return binary.toString();
    }

    public static double calculateX(String codeWord, int length) {
        double X = 0;
        for (int i = 0; i < length; i++) {
            X += Integer.parseInt(String.valueOf(codeWord.charAt(i))) * Math.pow(2, -(i + 1));
        }
        return round(X, ArithmeticConfig.ACCURATENESS);
    }
}
