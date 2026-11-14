package io.github.mariuszprzeczek.calculator

import org.junit.Test

import org.junit.Assert.*

class CalculatorViewModelTest {
    @Test
    fun digits_appendToDisplay() {
        val vm = CalculatorViewModel()
        vm.onDigit(1)
        vm.onDigit(2)
        assertEquals("12", vm.uiState.display)
    }

    @Test
    fun zeroThenSeven_showsSeven() {
        val vm = CalculatorViewModel()
        vm.onDigit(0)
        vm.onDigit(7)
        assertEquals("7", vm.uiState.display)
    }

    @Test
    fun clear_resetDisplay() {
        val  vm = CalculatorViewModel()
        vm.onDigit(5)
        vm.onClear()
        assertEquals("0", vm.uiState.display)
    }

    @Test
    fun twelvePlusThree_isFifteen() {
        val vm = CalculatorViewModel()
        vm.onDigit(1)
        vm.onDigit(2)
        vm.onPlus()
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
        vm.onPlus()
        vm.onDigit(2)
        vm.onEquals()
        vm.onDigit(9)
        assertEquals("9", vm.uiState.display)
    }
}