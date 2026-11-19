package io.github.mariuszprzeczek.calculator

data class CalculatorUiState(
    val expression: String = "",
    val result: String = "",
    val justEvaluated: Boolean = false
) {
    val display: String get() = expression.ifEmpty { "0" }
}