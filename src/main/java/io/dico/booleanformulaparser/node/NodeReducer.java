package io.dico.booleanformulaparser.node;

@FunctionalInterface
public interface NodeReducer {

    Node reduce(Node node1, Node node2);

}
