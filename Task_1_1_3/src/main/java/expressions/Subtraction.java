package expressions;

public class Subtraction extends Operation {

    public Subtraction(Expression left, Expression right) {
        super(left, right, "-");
    }

    @Override
    protected Expression simplifyOp(Expression l, Expression r) {
        if (l.equals(r)) return new Number(0);
        if (isZero(r)) return l;
        return collapseIfConstant(new Subtraction(l, r));
    }

    @Override
    public Expression derivative(String var) {
        return new Subtraction(left.derivative(var), right.derivative(var));
    }

    @Override
    public int eval(String assignments) {
        return left.eval(assignments) - right.eval(assignments);
    }
}