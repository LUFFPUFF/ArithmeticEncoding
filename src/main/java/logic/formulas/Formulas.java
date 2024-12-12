package logic.formulas;

public class Formulas {

    public static String length(String value) {
        return "⎡log2(" + value + ")⎤ = ";
    }

    public static String X(String F, String G) {
        return "bin(" + F + " + " + G + "/2" + " = ";
    }
}
