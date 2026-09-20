package expressions;

public class Main {

    public static void main(String[] args) {

        Expression e = new Addition(
                new Number(3),
                new Multiplication(new Number(2), new Variable("x"))
        );

        System.out.print("print:      ");
        e.print();
        System.out.println();

        Expression de = e.derivative("x");
        System.out.print("derivative: ");
        de.print();
        System.out.println();

        System.out.print("original:   ");
        e.print();
        System.out.println();

        int result = e.eval("x = 10; y = 13");
        System.out.println("eval:       " + result);

        Expression exprBase = new Addition(new Number(5), new Number(6));
        Expression exprSame = new Addition(new Number(5), new Number(6));
        Expression exprSwpd = new Addition(new Number(6), new Number(5));

        System.out.println("equals same: " + exprBase.equals(exprSame));
        System.out.println("equals swpd: " + exprBase.equals(exprSwpd));
    }
}