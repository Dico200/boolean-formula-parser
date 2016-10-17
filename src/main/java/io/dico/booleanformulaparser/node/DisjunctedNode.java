package io.dico.booleanformulaparser.node;

public class DisjunctedNode implements Node {

    private final Node node1;
    private final Node node2;

    public DisjunctedNode(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public boolean evaluate() {
        return node1.evaluate() || node2.evaluate();
    }
}