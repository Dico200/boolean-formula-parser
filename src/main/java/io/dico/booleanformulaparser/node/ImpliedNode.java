package io.dico.booleanformulaparser.node;

public class ImpliedNode extends DisjunctedNode {

    public ImpliedNode(Node node1, Node node2) {
        super(new NegatedNode(node1), node2);
    }

}
