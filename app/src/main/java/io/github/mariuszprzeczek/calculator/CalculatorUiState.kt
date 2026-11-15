package io.github.mariuszprzeczek.calculator

data class CalculatorUiState(
    val display: String = "0",
    val pending: Long? = null,
    val operator: Char? = null,
    val startNew: Boolean = false,
)