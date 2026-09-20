package expressions;

/**
 * Binary operation {@code (left - right)}.
 */
public class Subtraction extends Operation {

    /**
     * Creates a difference of two expressions.
     *
     * @param left  left operand
     * @param right right operand
     */
    public Subtraction(Expression left, Expression right) {
        super(left, right, "-");
    }

    /**
     * {@inheritDoc}
     * Applies the rule {@code x - x -> 0} when both operands are
     * structurally equal, then collapses to a number if possible.
     */
    @Override
    protected Expression simplifyOp(Expression l, Expression r) {
        if (l.equals(r)) return new Number(0);
        return collapseIfConstant(new Subtraction(l, r));
    }

    /**
     * {@inheritDoc}
     * {@code (a - b)' = a' - b'}.
     */
    @Override
    public Expression derivative(String var) {
        return new Subtraction(left.derivative(var), right.derivative(var));
    }

    /** {@inheritDoc} */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) - right.eval(assignments);
    }
}