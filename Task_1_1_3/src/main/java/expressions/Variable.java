package expressions;

import java.io.PrintStream;

/**
 * Leaf node representing a named variable.
 */
public class Variable extends Expression {

    private final String name;

    /**
     * Creates a variable with the given name.
     *
     * @param name the variable name (may contain multiple letters)
     */
    public Variable(String name) {
        this.name = name;
    }

    /** {@inheritDoc} A variable is already simplified. */
    @Override
    public Expression simplify() {
        return this;
    }

    /** {@inheritDoc}
     * The derivative of a variable with respect to itself is 1,
     * and 0 with respect to any other variable.
     */
    @Override
    public Expression derivative(String var) {
        return name.equals(var) ? new Number(1) : new Number(0);
    }

    /** {@inheritDoc}
     * Looks up this variable in the assignment string.
     * @throws IllegalArgumentException if the variable is not assigned
     */
    @Override
    public int eval(String assignments) {
        String[] pairs = assignments.split(";");
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            if (parts.length != 2) {
                continue;
            }
            String key = parts[0].trim();
            String value = parts[1].trim();
            if (key.equals(name)) {
                return Integer.parseInt(value);
            }
        }
        throw new IllegalArgumentException("Variable not assigned: " + name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;
        Variable other = (Variable) o;
        return this.name.equals(other.name);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public void print(PrintStream out) {
        out.print(name);
    }
}