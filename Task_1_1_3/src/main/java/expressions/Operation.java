package expressions;

public abstract class Operation extends Expression {

    protected final Expression left, right;
    protected final String op;

    protected Operation(Expression left, Expression right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();
        return simplifyOp(l, r);
    }

    protected abstract Expression simplifyOp(Expression l, Expression r);

    protected static boolean isZero(Expression e) {
        return e instanceof Number && ((Number) e).getValue() == 0;
    }

    protected static boolean isOne(Expression e) {
        return e instanceof Number && ((Number) e).getValue() == 1;
    }

    protected static boolean hasVariables(Expression e) {
        if (e instanceof Variable) return true;
        if (e instanceof Operation) {
            Operation op = (Operation) e;
            return hasVariables(op.left) || hasVariables(op.right);
        }
        return false;
    }

    protected static Expression collapseIfConstant(Expression e) {
        if (!hasVariables(e)) {
            return new Number(e.eval(""));
        }
        return e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Operation other = (Operation) o;
        return this.left.equals(other.left) && this.right.equals(other.right);
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        result = 31 * result + op.hashCode();
        return result;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print(op);
        right.print();
        System.out.print(")");
    }
}