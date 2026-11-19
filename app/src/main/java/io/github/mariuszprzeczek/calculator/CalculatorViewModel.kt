package io.github.mariuszprzeczek.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.abs

class CalculatorViewModel : ViewModel() {
    var uiState by mutableStateOf(CalculatorUiState())
        private set

    fun onDigit(d: Int) {
        if (uiState.justEvaluated) {
            setExpression(d.toString())
            return
        }
        val last = uiState.expression.lastOrNull()
        if (last == ')') return
        val current = uiState.expression.takeLastWhile { it.isDigit() }
        if (current.length >= 15) return
        val base = if (current == "0") uiState.expression.dropLast(1) else uiState.expression
        setExpression(base + d)
    }

    fun onOperator(op: Char) {
        val last = uiState.expression.lastOrNull() ?: return
        if (uiState.expression == "Error") return
        if (last == '(') return
        val base = if (last in "+-×÷") uiState.expression.dropLast(1) else uiState.expression
        setExpression(base + op)
    }

    fun onOpenBracket() {
        if (uiState.justEvaluated) {
            setExpression("(")
            return
        }
        val last = uiState.expression.lastOrNull()
        if (last != null && last !in "+-×÷(") return
        setExpression(uiState.expression + "(")
    }

    fun onCloseBracket() {
        val last = uiState.expression.lastOrNull() ?: return
        if (last != ')' && last !in '0'..'9') return
        val openBrackets = uiState.expression.count { it == '(' }
        val closedBrackets = uiState.expression.count { it == ')' }
        if (openBrackets - closedBrackets < 1) return
        setExpression(uiState.expression + ")")
    }

    fun onEquals() {
        val result = try {
            calculate(uiState.expression)
        } catch (e: IllegalStateException) {
            return
        }
        uiState = CalculatorUiState(expression = format(result), justEvaluated = true)
    }

    private fun format(result: Double): String = when {
        result.isInfinite() -> "Error"
        result.isNaN() -> "Error"
        abs(result) >= 1e15 -> "Error"
        result % 1.0 == 0.0 -> result.toLong().toString()
        else -> result.toString()
    }

    fun onClear() {
        uiState = CalculatorUiState()
    }

    fun onBackspace() {
        if (uiState.justEvaluated) return
        setExpression(uiState.expression.dropLast(1))
    }

    private fun preview(text: String): String {
        try {
            if (text.none { it in "+-×÷" }) return ""
            var result = text.trimEnd('+', '-', '×', '÷', '(')
            val openBrackets = result.count { it == '(' }
            val closedBrackets = result.count { it == ')' }
            result += ")".repeat(openBrackets - closedBrackets)
            result = format(calculate(result))
            return result
        } catch (e: IllegalStateException) {
            return ""
        }
    }

    private fun setExpression(text: String) {
        uiState = CalculatorUiState(expression = text, result = preview(text))
    }
}