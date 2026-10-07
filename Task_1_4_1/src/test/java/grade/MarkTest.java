package grade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Mark}, {@link GradedMark} and {@link CreditMark}.
 */
class MarkTest {

    @Test
    void gradedMarkValues() {
        assertEquals(5, GradedMark.EXCELLENT.value());
        assertEquals(4, GradedMark.GOOD.value());
        assertEquals(3, GradedMark.SATISFACTORY.value());
    }

    @Test
    void gradedMarkIsMark() {
        assertInstanceOf(Mark.class, GradedMark.EXCELLENT);
    }

    @Test
    void creditMarkIsMark() {
        assertInstanceOf(Mark.class, CreditMark.PASS);
    }

    @Test
    void permittedSubtypesAreExactlyGradedAndCredit() {
        Class<?>[] permitted = Mark.class.getPermittedSubclasses();
        assertEquals(2, permitted.length);
    }
}