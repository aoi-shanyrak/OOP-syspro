package expressions;

public abstract class Expression {

    public abstract Expression derivative(String var);

    public abstract int eval(String assigments);

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

    public abstract void print();
}
