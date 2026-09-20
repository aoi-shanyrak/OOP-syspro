package expressions;

/**
 * Binary operation {@code (left + right)}.
 */
public class Addition extends Operation {

    /**
     * Creates a sum of two expressions.
     *
     * @param left  left operand
     * @param right right operand
     */
    public Addition(Expression left, Expression right) {
        super(left, right, "+");
    }

    /**
     * {@inheritDoc}
     * Applies the rules {@code 0 + x -> x} and {@code x + 0 -> x},
     * then collapses to a number if no variables remain.
     */
    @Override
    protected Expression simplifyOp(Expression l, Expression r) {
        if (isZero(l)) return r;
        if (isZero(r)) return l;
        return collapseIfConstant(new Addition(l, r));
    }

    /**
     * {@inheritDoc}
     * {@code (a + b)' = a' + b'}.
     */
    @Override
    public Expression derivative(String var) {
        return new Addition(left.derivative(var), right.derivative(var));
    }

    /** {@inheritDoc} */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) + right.eval(assignments);
    }
}