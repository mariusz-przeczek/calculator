package io.github.mariuszprzeczek.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var uiState by mutableStateOf(CalculatorUiState())
        private set

    fun onDigit(d: Int) {
        val s = uiState
        val newDisplay = if (s.display == "0" || s.startNew) "$d" else s.display + d
        uiState = s.copy(display = newDisplay, startNew = false)
    }

    fun onClear() {
        uiState = CalculatorUiState()
    }

    fun onPlus() {
        uiState = uiState.copy(pending = uiState.display.toInt(), operator = '+', startNew = true)
    }

    fun onEquals() {
        val pending = uiState.pending
        if (pending != null && uiState.operator == '+') {
            val sum = uiState.display.toInt() + pending
            uiState = CalculatorUiState(display = sum.toString(), startNew = true)
        }
    }
}