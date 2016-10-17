package io.dico.booleanformulaparser.node;

public class VariableManager {

    private final String variables;
    private final ValueNode[] nodes;

    public VariableManager(String variables) {
        this.variables = variables;
        nodes = new ValueNode[variables.length()];
        int index = 0;
        for (char c : variables.toCharArray()) {
            nodes[index++] = new ValueNode();
        }
    }

    public String getVariables() {
        return variables;
    }

    public void setValues(boolean[] values) {
        if (values.length != nodes.length) {
            throw new IllegalArgumentException("invalid values length: " + values.length);
        }

        int index = 0;
        for (boolean b : values) {
            nodes[index++].setValue(b);
        }
    }

    public Node getNodeFor(char variable) {
        int index = variables.indexOf(variable);
        if (index == -1) {
            throw new IllegalArgumentException("Variable not registed: " + variable);
        }
        return nodes[index];
    }

    public boolean isVariable(char c) {
        return variables.indexOf(c) >= 0;
    }

}
