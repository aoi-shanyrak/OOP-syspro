package expressions;

import java.io.PrintStream;

/**
 * Abstract base for binary operations: addition, subtraction,
 * multiplication and division.
 *
 * <p>
 * Holds the two operands and the operator symbol. Provides shared
 * printing, equality, hashing and simplification entry point.
 */
public abstract class Operation extends Expression {

    /** Left operand. */
    protected final Expression left;

    /** Right operand. */
    protected final Expression right;

    /** Operator symbol, e.g. {@code "+"}. */
    protected final String op;

    /**
     * Creates a binary operation.
     *
     * @param left  left operand
     * @param right right operand
     * @param op    operator symbol
     */
    protected Operation(Expression left, Expression right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    /**
     * Returns the left operand.
     *
     * @return left operand
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Returns the right operand.
     *
     * @return right operand
     */
    public Expression getRight() {
        return right;
    }

    /**
     * {@inheritDoc}
     * Simplifies both operands first, then applies the operation's
     * own simplification rule via {@link #simplifyOp}.
     */
    @Override
    public Expression simplify() {
        return simplifyOp(left.simplify(), right.simplify());
    }

    /**
     * Applies the simplification rule specific to this operation.
     * The operands {@code l} and {@code r} are already simplified.
     *
     * @param l simplified left operand
     * @param r simplified right operand
     * @return simplified expression
     */
    protected abstract Expression simplifyOp(Expression l, Expression r);

    /** {@inheritDoc} */
    @Override
    public boolean hasVariables() {
        return left.hasVariables() || right.hasVariables();
    }

    /**
     * {@inheritDoc}
     * Prints operands separated by the operator, wrapped in parentheses.
     */
    @Override
    public void print(PrintStream out) {
        out.print("(");
        left.print(out);
        out.print(op);
        right.print(out);
        out.print(")");
    }

    /**
     * {@inheritDoc}
     * Two operations are equal if they are of the same class and
     * both operands are structurally equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Operation other = (Operation) o;
        return this.left.equals(other.left) && this.right.equals(other.right);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        result = 31 * result + op.hashCode();
        return result;
    }

    /**
     * Collapses the expression to a {@link Number} if it contains
     * no variables; otherwise returns it unchanged.
     *
     * @param e expression to collapse
     * @return a constant number or the original expression
     */
    protected static Expression collapseIfConstant(Expression e) {
        return e.hasVariables() ? e : new Number(e.eval(""));
    }

    /**
     * Checks whether the expression is a {@link Number} equal to zero.
     *
     * @param e expression to check
     * @return {@code true} if {@code e} is a zero constant
     */
    protected static boolean isZero(Expression e) {
        return e instanceof Number && ((Number) e).isZero();
    }

    /**
     * Checks whether the expression is a {@link Number} equal to one.
     *
     * @param e expression to check
     * @return {@code true} if {@code e} is a one constant
     */
    protected static boolean isOne(Expression e) {
        return e instanceof Number && ((Number) e).isOne();
    }

}