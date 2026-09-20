package expressions;

public class Multiplication extends Operation {

    public Multiplication(Expression left, Expression right) {
        super(left, right, "*");
    }

    @Override
    protected Expression simplifyOp(Expression l, Expression r) {
        if (isZero(l) || isZero(r)) return new Number(0);
        if (isOne(l)) return r;
        if (isOne(r)) return l;
        return collapseIfConstant(new Multiplication(l, r));
    }

    @Override
    public Expression derivative(String var) {
        return new Addition(
                new Multiplication(left.derivative(var), right),
                new Multiplication(left, right.derivative(var))
        );
    }

    @Override
    public int eval(String assignments) {
        return left.eval(assignments) * right.eval(assignments);
    }
}