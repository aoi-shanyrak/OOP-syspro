package grade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GradeEntry}.
 */
class GradeEntryTest {

    @Test
    void creditRequiresCreditMark() {
        assertThrows(IllegalArgumentException.class, () ->
                new GradeEntry(ControlType.CREDIT, "История", 1, GradedMark.EXCELLENT)
        );
        assertDoesNotThrow(() ->
                new GradeEntry(ControlType.CREDIT, "История", 1, CreditMark.PASS)
        );
    }

    @Test
    void gradedRequiresGradedMark() {
        assertThrows(IllegalArgumentException.class, () ->
                new GradeEntry(ControlType.EXAM, "Матан", 1, CreditMark.PASS)
        );
        assertDoesNotThrow(() ->
                new GradeEntry(ControlType.EXAM, "Матан", 1, GradedMark.EXCELLENT)
        );
    }

    @Test
    void nullsAreNotAllowed() {
        assertThrows(IllegalArgumentException.class, () ->
                new GradeEntry(null, "Матан", 1, GradedMark.EXCELLENT)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new GradeEntry(ControlType.EXAM, "Матан", 1, null)
        );
    }
}