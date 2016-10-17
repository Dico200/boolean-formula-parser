package io.dico.booleanformulaparser;

import io.dico.booleanformulaparser.node.ConjunctedNode;
import io.dico.booleanformulaparser.node.DisjunctedNode;
import io.dico.booleanformulaparser.node.NegatedNode;
import io.dico.booleanformulaparser.node.Node;
import io.dico.booleanformulaparser.node.VariableManager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BooleanFormulaParser {

    private static final char symbol_AND = '^';
    private static final char symbol_OR = 'v';
    private static final char symbol_NOT = '¬';

    private final char[] formula;
    private final VariableManager vManager;

    public BooleanFormulaParser(String formula) {
        List<Character> chars = new LinkedList<>();
        List<Character> variables = new LinkedList<>();

        chars.add('(');
        for (char c : formula.toCharArray()) {
            if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c) >= 0) {
                chars.add(c);
                variables.add(c);
            } else if (c == '(' || c == ')' || c == symbol_AND || c == symbol_NOT || c == symbol_OR) {
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
    private boolean conjuncting = false;
    private boolean disjuncting = false;

    private Node readSection() {
        Node node = null;

        while (true) {
            char c = nextChar();

            if (c == '(') {

                boolean negating = this.negating;
                boolean conjuncting = this.conjuncting;
                boolean disjuncting = this.disjuncting;
                this.negating = false;
                this.conjuncting = false;
                this.disjuncting = false;
                Node toAdd = readSection();

                this.negating = negating;
                this.conjuncting = conjuncting;
                this.disjuncting = disjuncting;
                node = addNode(node, toAdd);

            } else if (c == ')') {
                return node;
            } else if (c == symbol_AND) {
                if (node == null || conjuncting || disjuncting) {
                    throw new IllegalFormulaException();
                }
                conjuncting = true;
            } else if (c == symbol_OR) {
                if (node == null || disjuncting || conjuncting) {
                    throw new IllegalFormulaException();
                }
                disjuncting = true;
            } else if (c == symbol_NOT) {
                negating = !negating;
            } else if (vManager.isVariable(c)) {
                node = addNode(node, vManager.getNodeFor(c));
            } else {
                //skip
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
        } else if (conjuncting) {
            result = new ConjunctedNode(current, toAdd);
            conjuncting = false;
        } else if (disjuncting) {
            result = new DisjunctedNode(current, toAdd);
            disjuncting = false;
        } else {
            throw new IllegalFormulaException();
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

}
