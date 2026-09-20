package expressions;

public class Division extends Operation {

    public Division(Expression left, Expression right) {
        super(left, right, "/");
    }

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

    @Override
    public int eval(String assignments) {
        return left.eval(assignments) / right.eval(assignments);
    }
}