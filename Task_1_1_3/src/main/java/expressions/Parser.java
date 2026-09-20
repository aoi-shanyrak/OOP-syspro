package expressions;

import java.util.regex.Pattern;

public class Parser {

    private static final Pattern NUMBER = Pattern.compile("\\d+");
    private static final Pattern VAR = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");

    private static final String[] PRIORITY = {"+-", "*/"};

    public static Expression parse(String input) {
        String s = stripOuterParens(input.trim());

        for (String ops : PRIORITY) {
            int pos = findLastTopLevel(s, ops);
            if (pos >= 0) {
                return buildOperation(
                        s.charAt(pos),
                        s.substring(0, pos),
                        s.substring(pos + 1)
                );
            }
        }
        return buildAtom(s);
    }

    private static String stripOuterParens(String s) {
        if (s.isEmpty() || s.charAt(0) != '(') {
            return s;
        }
        int depth = 0, len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') {
                depth--;
                if (depth == 0) {
                    return i == len - 1
                            ? s.substring(1, i).trim()
                            : s;
                }
            }
        }
        return s;
    }

    private static int findLastTopLevel(String s, String ops) {
        int depth = 0, len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else if (depth == 0 && ops.indexOf(c) >= 0) {
                return i;
            }
        }
        return -1;
    }

    private static Expression buildOperation(char op, String left, String right) {
        Expression l = parse(left), r = parse(right);

        switch (op) {
            case '+':
                return new Addition(l, r);
            case '-':
                return new Subtraction(l, r);
            case '*':
                return new Multiplication(l, r);
            case '/':
                return new Division(l, r);
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    private static Expression buildAtom(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Empty atom");
        }
        if (NUMBER.matcher(s).matches()) {
            return new Number(Integer.parseInt(s));
        }
        if (VAR.matcher(s).matches()) {
            return new Variable(s);
        }
        throw new IllegalArgumentException("Invalid atom: " + s);
    }
}