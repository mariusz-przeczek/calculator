package io.github.mariuszprzeczek.calculator

import org.junit.Test
import org.junit.Assert.*

class EvaluatorTest {
    // --- Order of operations ---

    @Test
    fun twoPlusThreeTimesFour_isFourteen() {
        assertEquals(14.0, calculate("2+3×4"), 0.0)
    }

    @Test
    fun equationOrder_isLeftToRight() {
        assertEquals(5.0, calculate("10-2-3"), 0.0)
    }

    @Test
    fun eightDividedByTwoDividedByTwo_isTwo() {
        assertEquals(2.0, calculate("8÷2÷2"), 0.0)
    }

    // --- Brackets ---

    @Test
    fun equationWithBrackets_isCalculatedCorrectly() {
        assertEquals(14.0, calculate("2×(3+4)"), 0.0)
    }

    @Test
    fun equationWithMultipleBrackets_isCalculatedCorrectly() {
        assertEquals(15.0, calculate("(2+3)×(4-1)"), 0.0)
    }

    @Test
    fun twoInTwoPairsOfBrackets_isTwo() {
        assertEquals(2.0, calculate("((2))"), 0.0)
    }

    // --- Division by zero (no throw, Double gives Infinity) ---

    @Test
    fun divisionByZero_isInfinite() {
        assertTrue(calculate("5÷0").isInfinite())
    }

    // --- Broken input throws ---

    @Test
    fun operatorAtStart_throws() {
        assertThrows(IllegalStateException::class.java) { calculate("×2") }
    }

    @Test
    fun operatorAtEnd_throws() {
        assertThrows(IllegalStateException::class.java) { calculate("2+") }
    }

    @Test
    fun missingCloseBracket_throws() {
        assertThrows(IllegalStateException::class.java) { calculate("(2+3") }
    }

    @Test
    fun extraCloseBracket_throws() {
        assertThrows(IllegalStateException::class.java) { calculate("2)") }
    }

    @Test
    fun bracketsWithoutOperator_throws() {
        assertThrows(IllegalStateException::class.java) { calculate("2(3)") }
    }
}
