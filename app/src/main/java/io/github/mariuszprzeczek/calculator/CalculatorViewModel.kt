package io.github.mariuszprzeczek.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var uiState by mutableStateOf(CalculatorUiState())
        private set

    fun onDigit(d: Int) {
        val last = uiState.expression.lastOrNull()
        if ( last == ')' ) return
        val current = uiState.expression.takeLastWhile { it.isDigit() }
        if ( current.length >= 15 ) return
        val base = if (current == "0" ) uiState.expression.dropLast(1) else uiState.expression
        uiState = uiState.copy(expression = base + d)
    }

    fun onOperator(op: Char) {
        val last = uiState.expression.lastOrNull() ?: return
        if (last == '(') return
        val base = if (last in "+-×÷") uiState.expression.dropLast(1) else uiState.expression
        uiState = uiState.copy(expression = base + op)
    }

    fun onOpenBracket() {
        val last = uiState.expression.lastOrNull()
        if (last != null && last !in "+-×÷(") return
        uiState = uiState.copy(expression = uiState.expression + "(")
    }

    fun onCloseBracket() {
        val last = uiState.expression.lastOrNull() ?: return
        if (last != ')' && last !in '0'..'9') return
        val openBrackets = uiState.expression.count { it == '(' }
        val closedBrackets = uiState.expression.count { it == ')' }
        if (openBrackets - closedBrackets < 1) return
        uiState = uiState.copy(expression = uiState.expression + ")")
    }

    fun onEquals() {
    }

    fun onClear() {
        uiState = CalculatorUiState()
    }

    fun onBackspace() {
        uiState = uiState.copy(expression = uiState.expression.dropLast(1))
    }
}