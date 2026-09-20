package expressions;

import java.io.PrintStream;

/**
 * Leaf node representing an integer constant.
 */
public class Number extends Expression {

    private final int value;

    /**
     * Creates a constant with the given value.
     *
     * @param value the integer value of the constant
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Returns the value of this constant.
     *
     * @return the integer value
     */
    public int getValue() {
        return value;
    }

    /** {@inheritDoc} A constant is already simplified. */
    @Override
    public Expression simplify() {
        return this;
    }

    /** {@inheritDoc} The derivative of a constant is zero. */
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    /** {@inheritDoc} A constant evaluates to its value regardless of assignments. */
    @Override
    public int eval(String assignments) {
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Number)) return false;
        Number other = (Number) o;
        return this.value == other.value;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    /** {@inheritDoc} */
    @Override
    public void print(PrintStream out) {
        out.print(value);
    }
}