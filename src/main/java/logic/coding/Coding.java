package logic.coding;

import config.Probabilities;
import logic.coder.ArithmeticCoder;
import logic.manager.ProbabilityManager;

import java.util.ArrayList;
import java.util.List;

public class Coding {

    public static void print() {
        ProbabilityManager manager = new ProbabilityManager();

        manager.addProbability("s1", Probabilities.getProbabilities().get("s1"));
        manager.addProbability("s2", Probabilities.getProbabilities().get("s2"));
        manager.addProbability("s3", Probabilities.getProbabilities().get("s3"));
        manager.addProbability("s4", Probabilities.getProbabilities().get("s4"));

        String[] split = Probabilities.sequence.split(" ");
        List<String> sequence = new ArrayList<>(List.of(split));

        if (!manager.isValid()) {
            throw new IllegalArgumentException("Invalid probabilities! The sum must be 1.0.");
        }

        ArithmeticCoder coder = new ArithmeticCoder(manager);
        String codeWord = coder.encode(sequence);
        coder.decode(codeWord, sequence);
    }
}
