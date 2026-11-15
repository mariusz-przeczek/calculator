package io.github.mariuszprzeczek.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var uiState by mutableStateOf(CalculatorUiState())
        private set

    private fun calculate(a: Int, b: Int, op: Char): Int? = when (op) {
        '+' -> a + b
        '-' -> a - b
        '*' -> a * b
        '/' -> if (b == 0) null else a / b
        else -> null
    }

    fun onDigit(d: Int) {
        val s = uiState
        val newDisplay = if (s.display == "0" || s.startNew) "$d" else s.display + d
        uiState = s.copy(display = newDisplay, startNew = false)
    }

    fun onClear() {
        uiState = CalculatorUiState()
    }

    fun onOperator(op: Char) {
        if (uiState.display == "Error") return

        val pending = uiState.pending
        val prevOp = uiState.operator
        uiState = if (pending != null && prevOp != null && !uiState.startNew) {
            val result = calculate(a = pending, b = uiState.display.toInt(), op = prevOp)

            if (result != null) {
                uiState.copy(
                    display = result.toString(),
                    pending = result,
                    operator = op,
                    startNew = true
                )
            } else {
                uiState.copy(
                    display = "Error",
                    pending = null,
                    operator = null,
                    startNew = true
                )
            }
        } else {
            uiState.copy(pending = uiState.display.toInt(), operator = op, startNew = true)
        }
    }

    fun onEquals() {
        val pending = uiState.pending
        val operator = uiState.operator

        if (pending != null && operator != null) {
            val result = calculate(a = pending, b = uiState.display.toInt(), op = operator)

            uiState = if (result == null) {
                uiState.copy(display = "Error", pending = null, operator = null, startNew = true)
            } else {
                uiState.copy(
                    display = result.toString(),
                    pending = null,
                    operator = null,
                    startNew = true
                )
            }
        }
    }
}