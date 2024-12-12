package logic.coder;

import logic.config.ArithmeticConfig;
import logic.formulas.Formulas;
import logic.manager.ProbabilityManager;
import logic.printer.TablePrinter;
import logic.utils.ArithmeticUtils;
import logic.writer.Writer;

import java.util.*;

public class ArithmeticCoder {

    private final Map<String, Double> pEnsemble;
    private final Map<String, Double> qEnsemble = new LinkedHashMap<>();
    private static final String FILE_PATH = "src/main/java/output/output.txt";

    public ArithmeticCoder(ProbabilityManager manager) {
        this.pEnsemble = manager.getProbabilities();
        buildQEnsemble();
    }

    private void buildQEnsemble() {
        double q = 0.0;
        for (Map.Entry<String, Double> entry : pEnsemble.entrySet()) {
            qEnsemble.put(entry.getKey(), q);
            q = ArithmeticUtils.round(q + entry.getValue(), ArithmeticConfig.ACCURATENESS);
        }
    }


    public String encode(List<String> sequence) {
        double F = 0.0;
        double G = 1.0;
        int step = 0;

        String[] headers = {"Step", "s_i", "p(s_i)", "q(s_i)", "F(s_ik)", "G(s_ik)"};
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{
                String.valueOf(step), "-", "-", "-", String.format("%.8f", F), String.format("%.8f", G)
        });

        rows.add(new String[]{"", "", "", "", "----------", "----------"});

        for (String alpha : sequence) {
            step++;
            F = ArithmeticUtils.round(F + G * qEnsemble.get(alpha), ArithmeticConfig.ACCURATENESS);
            G = ArithmeticUtils.round(G * pEnsemble.get(alpha), ArithmeticConfig.ACCURATENESS);

            rows.add(new String[]{
                    String.valueOf(step), alpha,
                    String.format("%.8f", pEnsemble.get(alpha)),
                    String.format("%.8f", qEnsemble.get(alpha)),
                    String.format("%.8f", F), String.format("%.8f", G)
            });
            rows.add(new String[]{"", "", "", "", "----------", "----------"});
        }

        int L = (int) Math.ceil(-Math.log(G) / Math.log(2)) + 1;
        double code = ArithmeticUtils.round(F + G / 2, ArithmeticConfig.ACCURATENESS);
        String codeWord = ArithmeticUtils.floatToBinary(code, L);

        String table = TablePrinter.printTable(headers, rows.toArray(new String[0][0]));

        Writer.write(table + "\n" +
                "Длинна L = " + Formulas.length(String.format("%.8f", G)) + L + "\n" +
                "Кодовое слово: " + Formulas.X(String.format("%.8f", F), String.format("%.8f", G)) + codeWord + "\n" +
                "X = " + ArithmeticUtils.calculateX(codeWord, codeWord.length()) +
                "\n" + decode(codeWord, sequence), FILE_PATH);


        return codeWord;
    }

    public String decode(String codeWord, List<String> sequence) {
        double X = ArithmeticUtils.calculateX(codeWord, codeWord.length());
        double F_k = 0.0;
        double G_k = 1.0;

        String[] headers = new String[]{"Шаг k", "F_k", "G_k", "Гипотеза s_i", "q(s_i)", "Сравнение", "Решение", "Решение s_i", "p(s_i)"};
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{"0", "", "", "C = " + codeWord, "", "X = " + String.format("%.8f", X), "", "", ""});
        rows.add(new String[]{"", "", "", "----------", "", "----------", "", "", ""});

        String lastSuccessfulHypothesis = "";
        double lastSuccessfulProbability = 0.0;

        for (int k = 1; k <= sequence.size(); k++) {
            String alpha = sequence.get(k - 1);

            for (Map.Entry<String, Double> entry : qEnsemble.entrySet()) {
                String s_i = entry.getKey();
                double q = entry.getValue();

                double compareValue = ArithmeticUtils.round(F_k + G_k * q);
                boolean decision = compareValue < X;

                if (decision) {
                    lastSuccessfulHypothesis = s_i;
                    lastSuccessfulProbability = pEnsemble.get(s_i);
                }

                rows.add(new String[]{
                        String.valueOf(k),
                        String.format("%.8f", F_k),
                        String.format("%.8f", G_k),
                        s_i,
                        String.format("%.8f", q),
                        String.format("%.8f < X ?", compareValue),
                        decision ? "1" : "0",
                        "",
                        ""
                });
            }

            rows.add(new String[]{"", "", "", "----------", "", "----------", "", lastSuccessfulHypothesis, lastSuccessfulHypothesis.isEmpty() ? "" : String.format("%.8f", lastSuccessfulProbability)});

            if (k == 1) {
                F_k = qEnsemble.get(alpha);
                G_k = pEnsemble.get(alpha);
            } else {
                F_k = ArithmeticUtils.round(F_k + ArithmeticUtils.round(G_k * qEnsemble.get(alpha)));
                G_k = ArithmeticUtils.round(G_k * pEnsemble.get(alpha));
            }
        }

        return TablePrinter.printTable(headers, rows.toArray(new String[0][]));
    }
}
