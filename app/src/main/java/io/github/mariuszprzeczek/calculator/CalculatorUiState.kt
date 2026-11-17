package io.github.mariuszprzeczek.calculator

data class CalculatorUiState(val expression: String = "") {
    val display: String get() = expression.ifEmpty { "0" }
}