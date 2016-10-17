package io.dico.booleanformulaparser.node;

public class NegatedNode implements Node {

    private final Node negated;

    public NegatedNode(Node negated) {
        this.negated = negated;
    }

    @Override
    public boolean evaluate() {
        return !negated.evaluate();
    }
}
