package grade;

/**
 * A graded mark: excellent, good, satisfactory.
 */
public enum GradedMark implements Mark {

    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3);

    private final int value;

    GradedMark(int value) {
        this.value = value;
    }

    /**
     * Returns the numeric value of this mark.
     *
     * @return 5, 4, 3
     */
    public int value() {
        return value;
    }
}