package expressions;

import java.io.PrintStream;

/**
 * Base type for all nodes of a mathematical expression tree.
 * <p>
 * Concrete subclasses represent constants, variables and binary
 * operations. Each node knows how to print itself, compute its
 * derivative with respect to a variable, evaluate itself for a
 * given assignment of variables, and simplify itself.
 */
public abstract class Expression {

    /**
     * Returns a simplified version of this expression.
     * The original expression is not modified.
     *
     * @return a new simplified expression
     */
    public abstract Expression simplify();

    /**
     * Returns the derivative of this expression with respect to
     * the given variable.
     * The original expression is not modified.
     *
     * @param var name of the variable to differentiate by
     * @return a new expression that is the derivative
     */
    public abstract Expression derivative(String var);

    /**
     * Evaluates this expression for the given assignment of variables.
     * The assignment is a string like {@code "x = 10; y = 13"}.
     *
     * @param assignments variable assignments separated by {@code ;}
     * @return integer value of the expression
     */
    public abstract int eval(String assignments);

    /**
     * Compares this expression with another for structural equality.
     * Two expressions are equal if they have the same structure
     * and the same leaf values; commutativity is not considered.
     *
     * @param other the object to compare with
     * @return {@code true} if the expressions are structurally equal
     */
    @Override
    public abstract boolean equals(Object other);

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code of this expression
     */
    @Override
    public abstract int hashCode();

    /**
     * Prints this expression to the given stream.
     * Every operation is wrapped in parentheses, no spaces.
     *
     * @param out the stream to print to
     */
    public abstract void print(PrintStream out);

    /**
     * Prints this expression to {@link System#out}.
     * Convenience shortcut for {@code print(System.out)}.
     */
    public void print() {
        print(System.out);
    }
}