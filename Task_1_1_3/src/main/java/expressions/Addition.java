package expressions;

public class Addition extends Operation {

    public Addition(Expression left, Expression right) {
        super(left, right, "+");
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