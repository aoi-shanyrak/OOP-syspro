package expressions;

import java.util.regex.Pattern;

/**
 * Recursive-descent parser for mathematical expressions.
 *
 * <p>
 * Accepts both fully parenthesized input like {@code "(3+(2*x))"}
 * and bare input like {@code "3+2*x"}. Operator precedence is
 * respected, and binary operators are left-associative.
 */
public class Parser {

    private static final Pattern NUMBER = Pattern.compile("\\d+");
    private static final Pattern VARIABLE = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");

    /** Precedence levels, lowest first. */
    private static final String[] PRIORITY = { "+-", "*/" };

    /**
     * Parses the given string into an expression tree.
     *
     * @param input textual expression
     * @return the parsed expression
     * @throws IllegalArgumentException if the input is empty or malformed
     */
    public static Expression parse(String input) {
        String s = stripOuterParens(input.trim());

        for (String ops : PRIORITY) {
            int pos = findLastTopLevel(s, ops);
            if (pos >= 0) {
                Expression left = parse(s.substring(0, pos));
                Expression right = parse(s.substring(pos + 1));
                return buildOperation(s.charAt(pos), left, right);
            }
        }
        return buildAtom(s);
    }

    /**
     * Removes a single outer pair of parentheses if the whole string
     * is wrapped in one common pair.
     *
     * @param s input string
     * @return string without redundant outer parentheses
     */
    private static String stripOuterParens(String s) {
        if (s.isEmpty() || s.charAt(0) != '(') {
            return s;
        }
        int depth = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
                if (depth == 0) {
                    return i == s.length() - 1 ? s.substring(1, i).trim() : s;
                }
            }
        }
        return s;
    }

    /**
     * Finds the last top-level operator from the given set.
     * Going from the end gives left-associative parsing.
     *
     * @param s   input string
     * @param ops set of operator characters to look for
     * @return index of the operator, or {@code -1} if none found
     */
    private static int findLastTopLevel(String s, String ops) {
        int depth = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                depth++;
            } else if (c == '(') {
                depth--;
            } else if (depth == 0 && ops.indexOf(c) >= 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Builds the correct binary operation for the given symbol.
     *
     * @param op    operator character
     * @param left  left operand
     * @param right right operand
     * @return a new operation node
     */
    private static Expression buildOperation(char op, Expression left, Expression right) {
        switch (op) {
            case '+': return new Addition(left, right);
            case '-': return new Subtraction(left, right);
            case '*': return new Multiplication(left, right);
            case '/': return new Division(left, right);
            default:  throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    /**
     * Builds a leaf node from a token: a number or a variable.
     *
     * @param s the token
     * @return a new leaf expression
     * @throws IllegalArgumentException if the token is neither a number nor a valid variable name
     */
    private static Expression buildAtom(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Empty atom");
        }
        if (NUMBER.matcher(s).matches()) {
            return new Number(Integer.parseInt(s));
        }
        if (VARIABLE.matcher(s).matches()) {
            return new Variable(s);
        }
        throw new IllegalArgumentException("Invalid atom: " + s);
    }
}