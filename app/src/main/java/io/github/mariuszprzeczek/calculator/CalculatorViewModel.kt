package io.github.mariuszprzeczek.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var uiState by mutableStateOf(CalculatorUiState())
        private set

    private fun calculate(a: Long, b: Long, op: Char): Long? = try {
        when (op) {
            '+' -> Math.addExact(a, b)
            '-' -> Math.subtractExact(a, b)
            '*' -> Math.multiplyExact(a, b)
            '/' -> if (b == 0L) null else a / b
            else -> null
        }
    } catch (e: ArithmeticException) {
        null
    }

    fun onDigit(d: Int) {
        val s = uiState
        if (s.display.length >= 15 && !s.startNew) return
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
            val result = calculate(a = pending, b = uiState.display.toLong(), op = prevOp)

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
            uiState.copy(pending = uiState.display.toLong(), operator = op, startNew = true)
        }
    }

    fun onEquals() {
        val pending = uiState.pending
        val operator = uiState.operator

        if (pending != null && operator != null) {
            val result = calculate(a = pending, b = uiState.display.toLong(), op = operator)

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