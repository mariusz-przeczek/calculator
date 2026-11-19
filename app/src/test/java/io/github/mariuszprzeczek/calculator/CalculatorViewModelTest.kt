package io.github.mariuszprzeczek.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

private fun CalculatorViewModel.type(n: String) =
    n.forEach {
        when(it) {
            in '0'..'9' -> onDigit(it.digitToInt())
            in "+-×÷" -> onOperator(it)
            '(' -> onOpenBracket()
            ')' -> onCloseBracket()
            else -> error("type() can't press '$it'")
        }
    }

class CalculatorViewModelTest {
    // --- Digits ---

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
    fun fivePlusZeroSeven_showsFivePlusSeven() {
        val vm = CalculatorViewModel()
        vm.type("5+07")
        assertEquals("5+7", vm.uiState.display)
    }

    @Test
    fun sixteenDigits_keepsFifteen() {
        val vm = CalculatorViewModel()
        repeat(16) { vm.onDigit(9)}
        assertEquals("9".repeat(15), vm.uiState.display)
    }

    @Test
    fun limitsPerNumber_workCorrectly() {
        val vm = CalculatorViewModel()
        repeat(16) { vm.onDigit(9)}
        vm.type("+1")
        assertEquals("9".repeat(15)+"+1", vm.uiState.display)
    }

    // --- Operators ---

    @Test
    fun whenEmpty_showsZero() {
        val vm = CalculatorViewModel()
        vm.type("+")
        assertEquals("0", vm.uiState.display)
    }

    @Test
    fun fivePlusTimes_showsFiveTimes() {
        val vm = CalculatorViewModel()
        vm.type("5+×")
        assertEquals("5×", vm.uiState.display)
    }

    // --- Brackets ---

    @Test
    fun openBracketsPlus_showsOpenBrackets() {
        val vm = CalculatorViewModel()
        vm.type("(+")
        assertEquals("(", vm.uiState.display)
    }

    @Test
    fun expressionInBrackets_showsInBrackets() {
        val vm = CalculatorViewModel()
        vm.type("(2+3)")
        assertEquals("(2+3)", vm.uiState.display)
    }

    @Test
    fun closedBracketsWithoutOpenBrackets_doNotShow() {
        val vm = CalculatorViewModel()
        vm.type("3)")
        assertEquals("3", vm.uiState.display)
    }

    @Test
    fun digitWithoutOperatorAfterBrackets_doesNotShow() {
        val vm = CalculatorViewModel()
        vm.type("(2)4")
        assertEquals("(2)", vm.uiState.display)
    }

    // --- Clear and backspace ---

    @Test
    fun clear_resetDisplay() {
        val vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onClear()
        assertEquals("0", vm.uiState.display)
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
    fun numberAndOperatorThenBackspace_showsNumberWithoutOperator() {
        val vm = CalculatorViewModel()
        vm.type("12+")
        vm.onBackspace()
        assertEquals("12", vm.uiState.display)
    }

    // --- Equals: results ---

    @Test
    fun twelvePlusThree_isFifteen() {
        val vm = CalculatorViewModel()
        vm.type("12+3")
        vm.onEquals()
        assertEquals("15", vm.uiState.display)
    }

    @Test
    fun nineMinusFour_isFive() {
        val vm = CalculatorViewModel()
        vm.type("9-4")
        vm.onEquals()
        assertEquals("5", vm.uiState.display)
    }

    @Test
    fun threeTimesFour_isTwelve() {
        val vm = CalculatorViewModel()
        vm.type("3×4")
        vm.onEquals()
        assertEquals("12", vm.uiState.display)
    }

    @Test
    fun eightDividedByTwo_isFour() {
        val vm = CalculatorViewModel()
        vm.type("8÷2")
        vm.onEquals()
        assertEquals("4", vm.uiState.display)
    }

    @Test
    fun twoPlusThreeTimesFour_isFourteen(){
        val vm = CalculatorViewModel()
        vm.type("2+3×4")
        vm.onEquals()
        assertEquals("14", vm.uiState.display)
    }

    @Test
    fun ninesTimesNines_isCorrect() {
        val vm = CalculatorViewModel()
        vm.type("99999×99999")
        vm.onEquals()
        assertEquals("9999800001", vm.uiState.display)
    }

    // --- Equals: nothing to calculate ---

    @Test
    fun equalsWithoutOperator_doesNothing() {
        val vm = CalculatorViewModel()
        vm.onDigit(7)
        vm.onEquals()
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun twoPlusThenEquals_staysTwoPlus() {
        val vm = CalculatorViewModel()
        vm.type("2+")
        vm.onEquals()
        assertEquals("2+", vm.uiState.display)
    }

    // --- Equals: Error ---

    @Test
    fun dividedByZero_showsError() {
        val vm = CalculatorViewModel()
        vm.type("5÷0")
        vm.onEquals()
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun hugeTimesHuge_showsError() {
        val vm = CalculatorViewModel()
        repeat(15) { vm.onDigit(9) }
        vm.onOperator('×')
        repeat(15) { vm.onDigit(9) }
        vm.onEquals()
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun errorThenEquals_staysError() {
        val vm = CalculatorViewModel()
        vm.type("5÷0")
        vm.onEquals()
        vm.onEquals()
        assertEquals("Error", vm.uiState.display)
    }

    // --- Pressing buttons after a result ---

    @Test
    fun digitAfterResult_StartsNewNumber() {
        val vm = CalculatorViewModel()
        vm.type("1+2")
        vm.onEquals()
        vm.onDigit(9)
        assertEquals("9", vm.uiState.display)
    }

    @Test
    fun fifteenDigitResultThenDigit_startsNew() {
        val vm = CalculatorViewModel()
        vm.type("123456789×1000000")
        vm.onEquals()
        vm.onDigit(7)
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun backspaceAfterEquals_resultStaysUnchanged() {
        val vm = CalculatorViewModel()
        vm.type("1+2")
        vm.onEquals()
        vm.onBackspace()
        assertEquals("3", vm.uiState.display)
    }

    // --- Pressing buttons after Error ---

    @Test
    fun errorThenDigit_showsDigit() {
        val vm = CalculatorViewModel()
        vm.type("5÷0")
        vm.onEquals()
        vm.onDigit(7)
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun errorThenOperator_staysError() {
        val vm = CalculatorViewModel()
        vm.type("5÷0")
        vm.onEquals()
        vm.onOperator('+')
        assertEquals("Error", vm.uiState.display)
    }

    @Test
    fun errorThenNewSum_isCorrect() {
        val vm = CalculatorViewModel()
        vm.type("5÷0")
        vm.onEquals()
        vm.type("7+1")
        vm.onEquals()
        assertEquals("8", vm.uiState.display)
    }

    @Test
    fun backspaceOnError_showsError() {
        val vm = CalculatorViewModel()
        vm.type("9÷0")
        vm.onEquals()
        vm.onBackspace()
        assertEquals("Error", vm.uiState.display)
    }

    // --- Live result while typing ---

    @Test
    fun onePlusTwoPlusThree_isSix() {
        val vm = CalculatorViewModel()
        vm.type("1+2+")
        assertEquals("3", vm.uiState.result)
        vm.onDigit(3)
        vm.onEquals()
        assertEquals("6", vm.uiState.display)
    }

    @Test
    fun fiveMinusTwoPlusOne_isFour() {
        val vm = CalculatorViewModel()
        vm.type("5-2+")
        assertEquals("3", vm.uiState.result)
        vm.onDigit(1)
        vm.onEquals()
        assertEquals("4", vm.uiState.display)
    }

    @Test
    fun dividedByZeroThenOperatorPressed_showsError() {
        val vm = CalculatorViewModel()
        vm.type("5÷0+")
        assertEquals("Error", vm.uiState.result)
    }

    @Test
    fun bracketTwoPlusThree_resultShowsFive() {
        val vm = CalculatorViewModel()
        vm.type("(2+3")
        assertEquals("5", vm.uiState.result)
    }

    @Test
    fun sumAfterEquals_worksCorrectly() {
        val vm = CalculatorViewModel()
        vm.type("1+2")
        vm.onEquals()
        vm.type("+4")
        assertEquals("3+4", vm.uiState.display)
    }

    @Test
    fun withJustANumber_resultStaysEmpty() {
        val vm = CalculatorViewModel()
        vm.type("12")
        assertEquals("", vm.uiState.result)
    }
}
