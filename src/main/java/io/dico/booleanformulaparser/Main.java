package io.dico.booleanformulaparser;

public class Main {

    public static void main(String[] args) {
        printFormulaTable("¬X");
        printFormulaTable("Y ← X");
        printFormulaTable("X → Y");
        printFormulaTable("Z → ¬X");
        printFormulaTable("(X → Y) v (Z → ¬X)");
    }

    private static void printFormulaTable(String formula) {
        try {
            System.out.println(new TruthTablePrinter(new BooleanFormula(formula)).printTable());
        } catch (Throwable t) {
            System.out.println("Exception occurred while printing table for formula " + formula);
            t.printStackTrace();
        }
    }

}
