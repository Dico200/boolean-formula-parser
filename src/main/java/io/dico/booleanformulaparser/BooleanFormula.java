package io.dico.booleanformulaparser;

import io.dico.booleanformulaparser.node.Node;
import io.dico.booleanformulaparser.node.VariableManager;

public class BooleanFormula {

    private final String formula;
    private final Node root;
    private final VariableManager manager;

    public BooleanFormula(String formula, Node node, VariableManager vManager) {
        this.formula = formula;
        root = node;
        manager = vManager;
    }

    public BooleanFormula(String formula) throws IllegalFormulaException {
        BooleanFormulaParser parser = new BooleanFormulaParser(formula);
        root = parser.parseFormula();
        this.formula = parser.getFormula();
        manager = parser.getManager();
    }

    public String getFormula() {
        return formula;
    }

    public String getVariables() {
        return manager.getVariables();
    }

    public boolean evaluateFor(boolean[] inputs) {
        manager.setValues(inputs);
        return root.evaluate();
    }

}
