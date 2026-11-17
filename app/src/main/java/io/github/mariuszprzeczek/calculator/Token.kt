package io.github.mariuszprzeczek.calculator

sealed class Token {
    data class Number(val value: Double) : Token()
    data class Operator(val symbol: Char) : Token()
    data object OpenBracket : Token()
    data object CloseBracket : Token()
}