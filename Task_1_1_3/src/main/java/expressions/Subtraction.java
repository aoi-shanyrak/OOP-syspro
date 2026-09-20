package expressions;

public class Subtraction extends Operation {

    public Subtraction(Expression left, Expression right) {
        super(left, right, "-");
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