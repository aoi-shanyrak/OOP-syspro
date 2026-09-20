package expressions;

public class Variable extends Expression {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expression derivative(String var) {
        if (name.equals(var)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public int eval(String assignments) {
        String[] pairs = assignments.split(";");
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            if (parts.length != 2) {
                continue;
            }
            String key = parts[0].trim();
            String value = parts[1].trim();

            if (key.equals(name)) {
                return Integer.parseInt(value);
            }
        }
        throw new IllegalArgumentException("Variable not assigned: " + name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;
        Variable other = (Variable) o;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public void print() {
        System.out.print(name);
    }
}