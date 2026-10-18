package grade;

/**
 * One entry in the electronic grade book.
 *
 * @param type     type of assessment
 * @param subject  subject name
 * @param semester semester number (1..8)
 * @param mark     the received mark
 */
public record GradeEntry(
        ControlType type,
        String subject,
        int semester,
        Mark mark
) {
    /**
     * Validates that the mark matches the assessment type.
     */
    public GradeEntry {
        if (type == null || mark == null) {
            throw new IllegalArgumentException("Type and mark must not be null");
        }
        if (type == ControlType.CREDIT && !(mark instanceof CreditMark)) {
            throw new IllegalArgumentException("Simple credit must have a CreditMark");
        }
        if (type.isGraded() && !(mark instanceof GradedMark)) {
            throw new IllegalArgumentException("Graded type must have a GradedMark");
        }
    }
}