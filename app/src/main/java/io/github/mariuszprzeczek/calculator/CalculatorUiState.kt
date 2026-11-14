package io.github.mariuszprzeczek.calculator

data class CalculatorUiState(
    val display: String = "0",
    val pending: Int? = null,
    val operator: Char? = null,
    val startNew: Boolean = false,
)