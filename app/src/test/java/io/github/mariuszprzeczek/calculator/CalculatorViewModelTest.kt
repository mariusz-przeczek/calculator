package io.github.mariuszprzeczek.calculator

import org.junit.Test

import org.junit.Assert.*

private fun CalculatorViewModel.type(n: String) =
    n.forEach { onDigit(it.digitToInt()) }

class CalculatorViewModelTest {
    @Test
    fun digits_appendToDisplay() {
        val vm = CalculatorViewModel()
        vm.type("12")
        assertEquals("12", vm.uiState.display)
    }

    @Test
    fun zeroThenSeven_showsSeven() {
        val vm = CalculatorViewModel()
        vm.type("07")
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun clear_resetDisplay() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onClear()
        assertEquals("0", vm.uiState.display)
    }

    @Test
    fun twelvePlusThree_isFifteen() {
        val vm = CalculatorViewModel()
        vm.type("12")
        vm.onOperator('+')
        vm.onDigit(3)
        vm.onEquals()
        assertEquals("15", vm.uiState.display)
    }

    @Test
    fun equalsWithoutOperator_doesNothing() {
        val vm = CalculatorViewModel()
        vm.onDigit(7)
        vm.onEquals()
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun digitAfterResult_StartsNewNumber() {
        val vm = CalculatorViewModel()
        vm.onDigit(1)
        vm.onOperator('+')
        vm.onDigit(2)
        vm.onEquals()
        vm.onDigit(9)
        assertEquals("9", vm.uiState.display)
    }

    @Test
    fun nineMinusFour_isFive() {
        val vm = CalculatorViewModel()
        vm.onDigit(9)
        vm.onOperator('-')
        vm.onDigit(4)
        vm.onEquals()
        assertEquals("5", vm.uiState.display)
    }

    @Test
    fun threeTimesFour_isTwelve() {
        val vm = CalculatorViewModel()
        vm.onDigit(3)
        vm.onOperator('*')
        vm.onDigit(4)
        vm.onEquals()
        assertEquals("12", vm.uiState.display)
    }

    @Test
    fun eightDividedByTwo_isFour() {
        val vm = CalculatorViewModel()
        vm.onDigit(8)
        vm.onOperator('/')
        vm.onDigit(2)
        vm.onEquals()
        assertEquals("4", vm.uiState.display)
    }

    @Test
    fun dividedByZero_showsError() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onOperator('/')
        vm.onDigit(0)
        vm.onEquals()
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun onePlusTwoPlusThree_isSix() {
        val vm = CalculatorViewModel()
        vm.onDigit(1)
        vm.onOperator('+')
        vm.onDigit(2)
        vm.onOperator('+')
        assertEquals("3", vm.uiState.display)
        vm.onDigit(3)
        vm.onEquals()
        assertEquals("6", vm.uiState.display)
    }

    @Test
    fun fiveMinusTwoPlusOne_isFour() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onOperator('-')
        vm.onDigit(2)
        vm.onOperator('+')
        assertEquals("3", vm.uiState.display)
        vm.onDigit(1)
        vm.onEquals()
        assertEquals("4", vm.uiState.display)
    }

    @Test
    fun dividedByZeroThenOperatorPressed_showsError() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onOperator('/')
        vm.onDigit(0)
        vm.onOperator('+')
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun errorThenDigit_showsDigit() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onOperator('/')
        vm.onDigit(0)
        vm.onEquals()
        vm.onDigit(7)
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun errorThenOperator_staysError() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onOperator('/')
        vm.onDigit(0)
        vm.onEquals()
        vm.onOperator('+')
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun errorThenNewSum_isCorrect() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onOperator('/')
        vm.onDigit(0)
        vm.onEquals()
        vm.onDigit(7)
        vm.onOperator('+')
        vm.onDigit(1)
        vm.onEquals()
        assertEquals("8", vm.uiState.display)
    }

    @Test
    fun errorThenEquals_staysError() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onOperator('/')
        vm.onDigit(0)
        vm.onEquals()
        vm.onEquals()
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun sixteenDigits_keepsFifteen() {
        val vm = CalculatorViewModel()
        repeat(16) { vm.onDigit(9)}
        assertEquals("9".repeat(15), vm.uiState.display)
    }

    @Test
    fun fifteenDigitResultThenDigit_startsNew() {
        val vm = CalculatorViewModel()
        vm.type("123456789")
        vm.onOperator('*')
        vm.type("1000000")
        vm.onEquals()
        vm.onDigit(7)
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun ninesTimesNines_isCorrect() {
        val vm = CalculatorViewModel()
        vm.type("99999")
        vm.onOperator('*')
        vm.type("99999")
        vm.onEquals()
        assertEquals("9999800001", vm.uiState.display)
    }

    @Test
    fun hugeTimesHuge_showsError() {
        val vm = CalculatorViewModel()
        repeat(15) { vm.onDigit(9) }
        vm.onOperator('*')
        repeat(15) { vm.onDigit(9) }
        vm.onEquals()
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun oneTwoThreeThenBackspace_showsTwelve() {
        val vm = CalculatorViewModel()
        vm.type("123")
        vm.onBackspace()
        assertEquals("12", vm.uiState.display)
    }

    @Test
    fun sevenThenBackspace_showsZero() {
        val vm = CalculatorViewModel()
        vm.onDigit(7)
        vm.onBackspace()
        assertEquals("0", vm.uiState.display)
    }

    @Test
    fun backspaceAfterEquals_resultStaysUnchanged() {
        val vm = CalculatorViewModel()
        vm.onDigit(1)
        vm.onOperator('+')
        vm.onDigit(2)
        vm.onEquals()
        vm.onBackspace()
        assertEquals("3", vm.uiState.display)
    }

    @Test
    fun backspaceOnError_showsError() {
        val vm = CalculatorViewModel()
        vm.onDigit(9)
        vm.onOperator('/')
        vm.onDigit(0)
        vm.onEquals()
        vm.onBackspace()
        assertEquals("Error", vm.uiState.display)
    }
}