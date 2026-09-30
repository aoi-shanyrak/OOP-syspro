package grade;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GradeBook}.
 */
class GradeBookTest {

    private static GradeBook book(int currentSemester) {
        return new GradeBook(currentSemester);
    }

    private static void exam(GradeBook b, int semester, GradedMark mark) {
        b.add(ControlType.EXAM, "subject", semester, mark);
    }

    private static void diffCredit(GradeBook b, int semester, GradedMark mark) {
        b.add(ControlType.DIFF_CREDIT, "subject", semester, mark);
    }

    private static void credit(GradeBook b, int semester, CreditMark mark) {
        b.add(ControlType.CREDIT, "subject", semester, mark);
    }

    private static void courseWork(GradeBook b, int semester, GradedMark mark) {
        b.add(ControlType.COURSE_WORK, "subject", semester, mark);
    }

    private static void vkr(GradeBook b, GradedMark mark) {
        b.add(ControlType.VKR, "thesis", 8, mark);
    }

    @Nested
    class Average {

        @Test
        void emptyBookGivesZero() {
            assertEquals(0, book(1).average());
        }

        @Test
        void onlyCreditsGiveZero() {
            GradeBook b = book(1);
            credit(b, 1, CreditMark.PASS);
            credit(b, 1, CreditMark.PASS);
            assertEquals(0, b.average());
        }

        @Test
        void failedCreditDoesNotAffectAverage() {
            GradeBook b = book(1);
            credit(b, 1, CreditMark.FAIL);
            exam(b, 1, GradedMark.EXCELLENT);
            assertEquals(5, b.average());
        }

        @Test
        void singleGrade() {
            GradeBook b = book(1);
            exam(b, 1, GradedMark.GOOD);
            assertEquals(4, b.average());
        }

        @Test
        void mixedGrades() {
            GradeBook b = book(2);
            exam(b, 1, GradedMark.EXCELLENT);
            exam(b, 1, GradedMark.GOOD);
            diffCredit(b, 1, GradedMark.SATISFACTORY);
            credit(b, 1, CreditMark.PASS);
            // (5 + 4 + 3) / 3 = 4.0
            assertEquals(4.0, b.average());
        }

        @Test
        void testAndColloquiumIgnored() {
            GradeBook b = book(1);
            exam(b, 1, GradedMark.EXCELLENT);
            b.add(ControlType.TEST, "test", 1, GradedMark.SATISFACTORY);
            b.add(ControlType.COLLOQUIUM, "coll", 1, GradedMark.SATISFACTORY);
            b.add(ControlType.TASK, "task", 1, GradedMark.SATISFACTORY);
            // только экзамен учитывается
            assertEquals(5, b.average());
        }

        @Test
        void courseWorkAndVkrCounted() {
            GradeBook b = book(8);
            courseWork(b, 3, GradedMark.EXCELLENT);
            vkr(b, GradedMark.GOOD);
            assertEquals(4.5, b.average());
        }
    }

    @Nested
    class BudgetTransfer {

        @Test
        void emptyBookAllowsTransfer() {
            assertTrue(book(4).canTransferToBudget());
        }

        @Test
        void recentExamsAllGoodOrExcellent() {
            GradeBook b = book(4);
            exam(b, 3, GradedMark.EXCELLENT);
            exam(b, 4, GradedMark.GOOD);
            assertTrue(b.canTransferToBudget());
        }

        @Test
        void recentExamSatisfactoryBlocks() {
            GradeBook b = book(4);
            exam(b, 4, GradedMark.SATISFACTORY);
            assertFalse(b.canTransferToBudget());
        }

        @Test
        void oldExamSatisfactoryDoesNotBlock() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.SATISFACTORY);
            exam(b, 4, GradedMark.EXCELLENT);
            // семестр 1 не входит в последние две сессии (3 и 4)
            assertTrue(b.canTransferToBudget());
        }

        @Test
        void examTwoSemestersBackCounts() {
            GradeBook b = book(4);
            exam(b, 3, GradedMark.SATISFACTORY);
            exam(b, 4, GradedMark.EXCELLENT);
            // semester >= currentSemester - 1 = 3
            assertFalse(b.canTransferToBudget());
        }

        @Test
        void satisfactoryDiffCreditAllowed() {
            GradeBook b = book(4);
            diffCredit(b, 4, GradedMark.SATISFACTORY);
            exam(b, 4, GradedMark.EXCELLENT);
            assertTrue(b.canTransferToBudget());
        }

        @Test
        void failedCreditBlocks() {
            GradeBook b = book(4);
            credit(b, 4, CreditMark.FAIL);
            exam(b, 4, GradedMark.EXCELLENT);
            assertFalse(b.canTransferToBudget());
        }
    }

    @Nested
    class RedDiploma {

        @Test
        void allExcellentNoThesisYet() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            diffCredit(b, 2, GradedMark.EXCELLENT);
            // ВКР ещё не защищена — условие автоматически ok
            assertTrue(b.canGetRedDiploma());
        }

        @Test
        void exactlySeventyFivePercent() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            exam(b, 2, GradedMark.EXCELLENT);
            exam(b, 3, GradedMark.EXCELLENT);
            exam(b, 4, GradedMark.GOOD);
            // 3 из 4 = 75% → ok
            assertTrue(b.canGetRedDiploma());
        }

        @Test
        void belowSeventyFivePercent() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            exam(b, 2, GradedMark.EXCELLENT);
            exam(b, 3, GradedMark.GOOD);
            // 2 из 3 = 66.7% → fail
            assertFalse(b.canGetRedDiploma());
        }

        @Test
        void satisfactoryInFinalBlocks() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            exam(b, 2, GradedMark.SATISFACTORY);
            assertFalse(b.canGetRedDiploma());
        }

        @Test
        void satisfactoryInCourseWorkDoesNotBlockDiploma() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            courseWork(b, 3, GradedMark.SATISFACTORY);
            assertTrue(b.canGetRedDiploma());
        }

        @Test
        void satisfactoryOnlyInDiffCreditBlocks() {
            GradeBook b = book(4);
            diffCredit(b, 1, GradedMark.SATISFACTORY);
            assertFalse(b.canGetRedDiploma());
        }

        @Test
        void goodGradesStillAllowDiploma() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            exam(b, 2, GradedMark.EXCELLENT);
            exam(b, 3, GradedMark.EXCELLENT);
            exam(b, 4, GradedMark.GOOD);
            assertTrue(b.canGetRedDiploma());
        }

        @Test
        void thesisExcellentIsFine() {
            GradeBook b = book(8);
            exam(b, 1, GradedMark.EXCELLENT);
            vkr(b, GradedMark.EXCELLENT);
            assertTrue(b.canGetRedDiploma());
        }

        @Test
        void thesisGoodBlocks() {
            GradeBook b = book(8);
            exam(b, 1, GradedMark.EXCELLENT);
            vkr(b, GradedMark.GOOD);
            assertFalse(b.canGetRedDiploma());
        }

        @Test
        void thesisSatisfactoryBlocks() {
            GradeBook b = book(8);
            exam(b, 1, GradedMark.EXCELLENT);
            vkr(b, GradedMark.SATISFACTORY);
            assertFalse(b.canGetRedDiploma());
        }

        @Test
        void failedCreditBlocksDiploma() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            credit(b, 1, CreditMark.FAIL);
            assertFalse(b.canGetRedDiploma());
        }

        @Test
        void simpleCreditsDoNotAffectPercentage() {
            GradeBook b = book(4);
            exam(b, 1, GradedMark.EXCELLENT);
            credit(b, 1, CreditMark.PASS);
            credit(b, 1, CreditMark.PASS);
            credit(b, 1, CreditMark.PASS);
            // зачёты не идут в подсчёт 75%
            assertTrue(b.canGetRedDiploma());
        }
    }

    @Nested
    class IncreasedScholarship {

        @Test
        void noFailAndNoSatisfactoryCurrentSemester() {
            GradeBook b = book(4);
            exam(b, 4, GradedMark.EXCELLENT);
            exam(b, 4, GradedMark.GOOD);
            assertTrue(b.canGetIncreasedScholarship());
        }

        @Test
        void allExcellentCurrentSemester() {
            GradeBook b = book(4);
            exam(b, 4, GradedMark.EXCELLENT);
            exam(b, 4, GradedMark.EXCELLENT);
            assertTrue(b.canGetIncreasedScholarship());
        }

        @Test
        void goodGradesStillQualify() {
            GradeBook b = book(4);
            exam(b, 4, GradedMark.GOOD);
            exam(b, 4, GradedMark.GOOD);
            exam(b, 4, GradedMark.GOOD);
            assertTrue(b.canGetIncreasedScholarship());
        }
    }

    @Nested
    class Integration {

        @Test
        void perfectStudentQualifiesForEverything() {
            GradeBook b = book(8);
            exam(b, 1, GradedMark.EXCELLENT);
            exam(b, 2, GradedMark.EXCELLENT);
            exam(b, 3, GradedMark.EXCELLENT);
            exam(b, 4, GradedMark.EXCELLENT);
            exam(b, 5, GradedMark.EXCELLENT);
            exam(b, 6, GradedMark.EXCELLENT);
            diffCredit(b, 7, GradedMark.EXCELLENT);
            diffCredit(b, 8, GradedMark.EXCELLENT);
            vkr(b, GradedMark.EXCELLENT);
            credit(b, 8, CreditMark.PASS);

            assertTrue(b.canTransferToBudget());
            assertTrue(b.canGetRedDiploma());
            assertTrue(b.canGetIncreasedScholarship());
        }

        @Test
        void failedStudentQualifiesForNothing() {
            GradeBook b = book(4);
            exam(b, 4, GradedMark.SATISFACTORY);
            credit(b, 4, CreditMark.FAIL);

            assertFalse(b.canTransferToBudget());
            assertFalse(b.canGetRedDiploma());
            assertFalse(b.canGetIncreasedScholarship());
        }

        @Test
        void averageStillComputedForFailedStudent() {
            GradeBook b = book(4);
            exam(b, 4, GradedMark.SATISFACTORY);
            exam(b, 4, GradedMark.GOOD);
            credit(b, 4, CreditMark.FAIL);

            // средний считается, несмотря на долги
            assertEquals(3.5, b.average());
        }
    }
}