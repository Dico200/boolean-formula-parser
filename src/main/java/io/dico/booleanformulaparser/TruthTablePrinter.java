package io.dico.booleanformulaparser;

public class TruthTablePrinter {

    private final BooleanFormula formula;

    public TruthTablePrinter(BooleanFormula formula) {
        this.formula = formula;
    }

    private boolean[][] generateOptions(int variableCount) {
        int optionCount = 1;
        for (int i = 0; i < variableCount; i++) optionCount *= 2;
        boolean[][] result = new boolean[optionCount][];

        for (int i = 0; i < optionCount; i++) {
            boolean[] option = result[i] = new boolean[variableCount];
            for (int var = 0; var < variableCount; var++) {
                option[var] = ((i >> var) & 1) == 1;
            }
        }

        return result;
    }

    public String printTable() {
        char[] variables = formula.getVariables().toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : variables) {
            result.append(c).append('|');
        }
        result.append(formula.getFormula());
        result.append('\n');

        boolean[][] inputOptions = generateOptions(formula.getVariables().length());
        for (boolean[] option : inputOptions) {
            for (boolean b : option) {
                result.append(b ? '1' : '0').append('|');
            }
            result.append(formula.evaluateFor(option) ? '1' : '0');
            result.append('\n');
        }

        return result.toString();
    }
}
