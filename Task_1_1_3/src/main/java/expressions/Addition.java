package expressions;

public class Addition extends Operation {

    public Addition(Expression left, Expression right) {
        super(left, right, "+");
    }

    @Override
    protected Expression simplifyOp(Expression l, Expression r) {
        if (isZero(l)) return r;
        if (isZero(r)) return l;
        return collapseIfConstant(new Addition(l, r));
    }

    @Override
    public Expression derivative(String var) {
        return new Addition(left.derivative(var), right.derivative(var));
    }

    @Override
    public int eval(String assignments) {
        return left.eval(assignments) + right.eval(assignments);
    }
}