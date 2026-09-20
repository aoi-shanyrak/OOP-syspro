package expressions;

/**
 * Binary operation {@code (left * right)}.
 */
public class Multiplication extends Operation {

    /**
     * Creates a product of two expressions.
     *
     * @param left  left operand
     * @param right right operand
     */
    public Multiplication(Expression left, Expression right) {
        super(left, right, "*");
    }

    /**
     * {@inheritDoc}
     * Applies the rules {@code 0 * x -> 0}, {@code x * 0 -> 0},
     * {@code 1 * x -> x} and {@code x * 1 -> x}, then collapses
     * to a number if no variables remain.
     * <p>
     * Zero rules are checked before one rules, so {@code 0 * 1}
     * simplifies to {@code 0}.
     */
    @Override
    protected Expression simplifyOp(Expression l, Expression r) {
        if (isZero(l) || isZero(r)) {
            return new Number(0);
        }
        if (isOne(l)) {
            return r;
        }
        if (isOne(r)) {
            return l;
        }
        return collapseIfConstant(new Multiplication(l, r));
    }

    /**
     * {@inheritDoc}
     * Product rule: {@code (a * b)' = a'*b + a*b'}.
     */
    @Override
    public Expression derivative(String var) {
        return new Addition(
                new Multiplication(left.derivative(var), right),
                new Multiplication(left, right.derivative(var))
        );
    }

    /** {@inheritDoc} */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) * right.eval(assignments);
    }
}