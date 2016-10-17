package io.dico.booleanformulaparser;

public class Main {

    public static void main(String[] args) {
        printFormulaTable("A ^ (B v ¬C)");
        printFormulaTable("A ^ B v ¬C");
        printFormulaTable("C ^(¬(H v (¬B v C)) ^ ¬D)"); //always false it appears.
    }

    private static void printFormulaTable(String formula) {
        try {
            System.out.println(new TruthTablePrinter(new BooleanFormula(formula)).printTable());
        } catch (Throwable t) {
            System.out.println("Exception occurred while printing table for formula " + formula);
        }
    }

}
