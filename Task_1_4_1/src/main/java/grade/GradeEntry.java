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
}