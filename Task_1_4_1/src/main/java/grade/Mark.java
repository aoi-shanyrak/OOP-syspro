package grade;

/**
 * A mark that can appear in a grade book.
 *
 * <p>There are two kinds of marks:
 * <ul>
 *   <li>{@link GradedMark} — an ordinary grade (5, 4 or 3);</li>
 *   <li>{@link CreditMark} — a pass/fail result of a simple credit.</li>
 * </ul>
 *
 * <p>The interface is sealed: no other implementations are allowed,
 * so any {@code switch} over a {@code Mark} can be exhaustive.
 */
public sealed interface Mark permits GradedMark, CreditMark {
}