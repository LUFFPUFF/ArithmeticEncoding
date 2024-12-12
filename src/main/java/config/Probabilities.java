package config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Probabilities {

    public static Double P_S1 = 0.2;
    public static Double P_S2 = 0.4;
    public static Double P_S3 = 0.1;
    public static Double P_S4 = 0.3;

    public static String sequence = "s4 s3 s2 s4 s2 s2 s1";

    public static Map<String, Double> getProbabilities() {
        Map<String, Double> probs = new HashMap<>();
        probs.put("s1", P_S1);
        probs.put("s2", P_S2);
        probs.put("s3", P_S3);
        probs.put("s4", P_S4);
        return probs;
    }


}
