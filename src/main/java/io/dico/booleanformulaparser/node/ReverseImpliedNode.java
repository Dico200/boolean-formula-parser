package io.dico.booleanformulaparser.node;

public class ReverseImpliedNode extends DisjunctedNode {

    public ReverseImpliedNode(Node node1, Node node2) {
        super(node1, new NegatedNode(node2));
    }

}
