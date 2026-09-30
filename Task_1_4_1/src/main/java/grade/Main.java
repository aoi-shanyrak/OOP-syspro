package grade;

/**
 * Demo of the grade book functionality.
 */
public class Main {

    public static void main(String[] args) {
        GradeBook book = new GradeBook(4);

        book.add(ControlType.EXAM, "calculus", 1, GradedMark.EXCELLENT);
        book.add(ControlType.EXAM, "linear algebra", 3, GradedMark.GOOD);
        book.add(ControlType.DIFF_CREDIT, "C++", 4, GradedMark.SATISFACTORY);
        book.add(ControlType.CREDIT, "67", 1, CreditMark.PASS);

        System.out.printf("average:  %.2f%n", book.average());
        System.out.println("budget:     " + book.canTransferToBudget());
        System.out.println("red:" + book.canGetRedDiploma());
        System.out.println("scholarship:     " + book.canGetIncreasedScholarship());
    }
}