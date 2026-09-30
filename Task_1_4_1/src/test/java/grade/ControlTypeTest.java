package grade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ControlType}.
 */
class ControlTypeTest {

    @Test
    void examIsFinal() {
        assertTrue(ControlType.EXAM.isFinal());
    }

    @Test
    void diffCreditIsFinal() {
        assertTrue(ControlType.DIFF_CREDIT.isFinal());
    }

    @Test
    void simpleCreditIsNotFinal() {
        assertFalse(ControlType.CREDIT.isFinal());
    }

    @Test
    void vkrIsNotFinalForDiplomaSupplement() {
        assertFalse(ControlType.VKR.isFinal());
    }

    @Test
    void gradedTypes() {
        assertTrue(ControlType.EXAM.isGraded());
        assertTrue(ControlType.DIFF_CREDIT.isGraded());
        assertTrue(ControlType.COURSE_WORK.isGraded());
        assertTrue(ControlType.VKR.isGraded());
        assertTrue(ControlType.PRACTICE.isGraded());
    }

    @Test
    void nonGradedTypes() {
        assertFalse(ControlType.CREDIT.isGraded());
        assertFalse(ControlType.TEST.isGraded());
        assertFalse(ControlType.COLLOQUIUM.isGraded());
        assertFalse(ControlType.TASK.isGraded());
    }
}