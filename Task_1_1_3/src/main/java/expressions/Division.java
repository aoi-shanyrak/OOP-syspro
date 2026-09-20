package expressions;

/**
 * Binary operation {@code (left / right)}.
 */
public class Division extends Operation {

    /**
     * Creates a quotient of two expressions.
     *
     * @param left  numerator
     * @param right denominator
     */
    public Division(Expression left, Expression right) {
        super(left, right, "/");
    }

    /**
     * {@inheritDoc}
     * Applies the rules {@code 0 / x -> 0} and {@code x / 1 -> x},
     * then collapses to a number if no variables remain.
     */
    @Override
    protected Expression simplifyOp(Expression l, Expression r) {
        if (isZero(l)) {
            return new Number(0);
        }
        if (isOne(r)) {
            return l;
        }
        return collapseIfConstant(new Division(l, r));
    }

    /**
     * {@inheritDoc}
     * Quotient rule: {@code (a / b)' = (a'*b - a*b') / (b*b)}.
     */
    @Override
    public Expression derivative(String var) {
        return new Division(
                new Subtraction(
                        new Multiplication(left.derivative(var), right),
                        new Multiplication(left, right.derivative(var))
                ),
                new Multiplication(right, right)
        );
    }

    /** {@inheritDoc} Uses integer division. */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) / right.eval(assignments);
    }
}