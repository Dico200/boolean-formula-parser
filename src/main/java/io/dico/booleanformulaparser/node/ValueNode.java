package io.dico.booleanformulaparser.node;

public class ValueNode implements Node {

    private boolean value = false;

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean evaluate() {
        return value;
    }
}
