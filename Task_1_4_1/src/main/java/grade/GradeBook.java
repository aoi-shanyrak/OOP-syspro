package grade;

import java.util.ArrayList;
import java.util.List;

/**
 * Electronic grade book of an MSU Faculty of Mechanics
 * and Mathematics student.
 */
public class GradeBook {

    private final List<GradeEntry> entries = new ArrayList<>();
    private final int currentSemester;

    /**
     * Creates a grade book for the given current semester.
     *
     * @param currentSemester semester number (1..8)
     */
    public GradeBook(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    /**
     * Adds a grade entry to the book.
     *
     * @param entry the entry
     */
    public void add(GradeEntry entry) {
        entries.add(entry);
    }

    /**
     * Convenience shortcut: builds a {@link GradeEntry} and adds it.
     *
     * @param type     assessment type
     * @param subject  subject name
     * @param semester semester number
     * @param mark     received mark
     */
    public void add(ControlType type, String subject, int semester, Mark mark) {
        entries.add(new GradeEntry(type, subject, semester, mark));
    }

    /**
     * Returns the average grade across all graded assessments.
     * Simple credits, tests and colloquia are ignored.
     *
     * @return average grade, or {@code 0} if there are no graded entries
     */
    public double average() {
        return entries.stream()
                .filter(e -> e.type().isGraded())
                .map(GradeEntry::mark)
                .filter(GradedMark.class::isInstance)
                .map(GradedMark.class::cast)
                .mapToInt(GradedMark::value)
                .average()
                .orElse(0);
    }

    /**
     * Whether the student can transfer from a paid place to a state-funded one.
     * The condition is: no satisfactory grade at any exam taken during the
     * last two examination sessions. Satisfactory grades at graded credits
     * are allowed.
     *
     * @return {@code true} if the transfer is possible
     */
    public boolean canTransferToBudget() {
        if (!allCreditsPassed()) {
            return false;
        }
        int last = currentSemester - 1;
        return entries.stream()
                .filter(e -> e.semester() >= last)
                .filter(e -> e.type() == ControlType.EXAM)
                .map(GradeEntry::mark)
                .filter(GradedMark.class::isInstance)
                .map(GradedMark.class::cast)
                .mapToInt(GradedMark::value)
                .noneMatch(i -> i <= GradedMark.SATISFACTORY.value());
    }

    /**
     * Whether all simple credits have been passed.
     * A failed credit means an outstanding debt: it blocks
     * any transfer, honours diploma or scholarship.
     *
     * @return {@code true} if no {@link CreditMark#FAIL} is present
     */
    private boolean allCreditsPassed() {
        return entries.stream()
                .map(GradeEntry::mark)
                .filter(CreditMark.class::isInstance)
                .noneMatch(m -> m == CreditMark.FAIL);
    }

    /**
     * Whether the student can graduate with honours.
     * The conditions are:
     * <ul>
     *   <li>75% of final marks are excellent;</li>
     *   <li>no satisfactory final marks;</li>
     *   <li>the qualification thesis is excellent (if it has been defended).</li>
     * </ul>
     *
     * <p>The result can be predicted before the graduation: if the thesis has
     * not been defended yet, that condition is considered satisfied.
     *
     * @return {@code true} if the honours diploma is still possible
     */
    public boolean canGetRedDiploma() {
        if (!allCreditsPassed() || !hasNoBadOrSatisfactoryMarksForFinal()) {
            return false;
        }
        List<GradeEntry> finalEntries = entries.stream()
                .filter(e -> e.type().isFinal())
                .filter(e -> e.mark() instanceof GradedMark)
                .toList();
        long total = finalEntries.size();
        long excellent = finalEntries.stream()
                .filter(e -> e.mark() == GradedMark.EXCELLENT)
                .count();
        boolean seventyFivePercent = total == 0 || excellent * 100 >= total * 75;

        boolean thesisExcellent = entries.stream()
                .filter(e -> e.type() == ControlType.VKR)
                .allMatch(e -> e.mark() == GradedMark.EXCELLENT);

        return seventyFivePercent && thesisExcellent;
    }

    /**
     * No bad or satisfactory marks for exams or diffzachet.
     *
     * @return {@code true} if all has 4 or 5 marks
     */
    private boolean hasNoBadOrSatisfactoryMarksForFinal() {
        return entries.stream()
                .filter(e -> e.type().isFinal())
                .noneMatch(e -> e.mark() == GradedMark.SATISFACTORY);
    }

    /**
     * Whether the student can receive an increased scholarship in the
     * current semester.
     *
     * @return {@code true} if the increased scholarship is possible
     */
    public boolean canGetIncreasedScholarship() {
        if (!allCreditsPassed()) {
            return false;
        }
        return entries.stream()
                .filter(e -> e.semester() == currentSemester)
                .filter(e -> e.type().isGraded())
                .noneMatch(e -> e.mark() == GradedMark.SATISFACTORY);
    }
}