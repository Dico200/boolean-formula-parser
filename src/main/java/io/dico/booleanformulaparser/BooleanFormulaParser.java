package io.dico.booleanformulaparser;

import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.hash.TCharObjectHashMap;
import io.dico.booleanformulaparser.node.*;

import java.util.*;

public class BooleanFormulaParser {

    private static final char symbol_AND = '^';
    private static final char symbol_OR = 'v';
    private static final char symbol_IMPLIES = '→';
    private static final char symbol_REVERSE_IMPLIES = '←';
    private static final char symbol_NOT = '¬';

    private static final TCharObjectMap<NodeReducer> reducers = new TCharObjectHashMap<NodeReducer>() {
        {
            put(symbol_AND, ConjunctedNode::new);
            put(symbol_OR, DisjunctedNode::new);
            put(symbol_IMPLIES, ImpliedNode::new);
            put(symbol_REVERSE_IMPLIES, ReverseImpliedNode::new);
            put('\0', (n1, n2) -> {
                throw new IllegalFormulaException();
            });
        }
    };

    private final char[] formula;
    private final VariableManager vManager;

    public BooleanFormulaParser(String formula) {
        List<Character> chars = new LinkedList<>();
        Set<Character> variables = new LinkedHashSet<>();

        chars.add('(');
        for (char c : formula.toCharArray()) {
            if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c) >= 0) {
                chars.add(c);
                variables.add(c);
            } else if (c == '(' || c == ')' || c == symbol_NOT || (c != '\0' && reducers.containsKey(c))) {
                chars.add(c);
            }
        }
        chars.add(')');

        this.formula = toArray(chars);
        this.vManager = new VariableManager(new String(toArray(variables)));
    }

    public VariableManager getManager() {
        return vManager;
    }

    public String getFormula() {
        return new String(formula);
    }

    public Node parseFormula() {
        index++; //consume first (
        return readSection();
    }

    private char nextChar() {
        if (index == formula.length) {
            throw new IllegalFormulaException();
        }
        return formula[index++];
    }

    private int index = 0;
    private boolean negating = false;
    private char operation = '\0';

    private Node readSection() {
        Node node = null;

        while (true) {
            final char c = nextChar();

            switch (c) {
                case '(': {
                    boolean negatingSnapshot = negating;
                    char operationSnapshot = operation;
                    negating = false;
                    operation = '\0';
                    Node toAdd = readSection();

                    negating = negatingSnapshot;
                    operation = operationSnapshot;
                    node = addNode(node, toAdd);
                    break;
                }
                case ')':
                    return node;
                case symbol_AND:
                case symbol_OR:
                case symbol_IMPLIES:
                case symbol_REVERSE_IMPLIES:
                    checkNewOperationValid(node);
                    operation = c;
                    break;
                case symbol_NOT:
                    negating = !negating;
                    break;
                default:
                    // Getting here implies c is a variable
                    node = addNode(node, vManager.getNodeFor(c));
                    break;
            }
        }
    }

    private Node addNode(Node current, Node toAdd) {
        Node result = null;
        if (negating) {
            toAdd = new NegatedNode(toAdd);
            negating = false;
        }

        if (current == null) {
            result = toAdd;
        } else {
            result = reducers.get(operation).reduce(current, toAdd);
            operation = '\0';
        }

        return result;
    }

    private char[] toArray(Collection<Character> iterable) {
        char[] ret = new char[iterable.size()];
        int index = 0;
        for (char c : iterable) {
            ret[index++] = c;
        }
        return ret;
    }

    private void checkNewOperationValid(Node node) {
        if (node == null || operation != '\0') {
            throw new IllegalFormulaException();
        }
    }

}
