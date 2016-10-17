package io.dico.booleanformulaparser.node;

public class ConjunctedNode implements Node {

    private final Node node1;
    private final Node node2;

    public ConjunctedNode(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public boolean evaluate() {
        return node1.evaluate() && node2.evaluate();
    }

}
