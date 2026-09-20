package expressions;

public class Number extends Expression {

    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public int eval(String assignments) {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Number)) return false;
        Number other = (Number) o;
        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}