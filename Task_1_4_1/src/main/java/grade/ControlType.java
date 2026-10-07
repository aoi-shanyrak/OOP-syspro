package grade;

/**
 * Type of assessment in the curriculum.
 */
public enum ControlType {

    EXAM,
    DIFF_CREDIT,
    CREDIT,
    COURSE_WORK,
    VKR,
    PRACTICE,
    TEST,
    COLLOQUIUM,
    TASK;

    /**
     * Whether this assessment is part of the final diploma supplement.
     *
     * @return {@code true} for exams and graded credits
     */
    public boolean isFinal() {
        return this == EXAM || this == DIFF_CREDIT;
    }

    /**
     * Whether this assessment contributes to the average grade.
     *
     * @return {@code true} for anything except simple credits,
     *         tests, colloquia and tasks
     */
    public boolean isGraded() {
        return this == EXAM
                || this == DIFF_CREDIT
                || this == COURSE_WORK
                || this == VKR
                || this == PRACTICE;
    }
}