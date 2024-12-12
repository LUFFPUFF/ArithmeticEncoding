package logic.manager;


import java.util.LinkedHashMap;
import java.util.Map;

public class ProbabilityManager {

    private final Map<String, Double> probabilities = new LinkedHashMap<>();

    public void addProbability(String symbol, double probability) {
        probabilities.put(symbol, probability);
    }

    public double getProbability(String symbol) {
        return probabilities.get(symbol);
    }

    public Map<String, Double> getProbabilities() {
        return probabilities;
    }

    public boolean isValid() {
        double sum = probabilities.values().stream().mapToDouble(Double::doubleValue).sum();
        return Math.abs(1.0 - sum) < 1e-10;
    }
}
