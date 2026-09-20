package expressions;

public class Multiplication extends Operation {

    public Multiplication(Expression left, Expression right) {
        super(left, right, "*");
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