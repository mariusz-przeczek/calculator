package io.github.mariuszprzeczek.calculator

fun calculate(text: String): Double = Evaluator(tokenize(text)).evaluate()

class Evaluator(private val tokens: List<Token>) {
    private var pos = 0

    private fun expression(): Double {
        var value = term()
        while (pos < tokens.size) {
            val t = tokens[pos]
            if (t !is Token.Operator || t.symbol !in "+-") break
            pos++
            val right = term()
            value = if (t.symbol == '+') value + right else value - right
        }
        return value
    }

    private fun term(): Double {
        var value = factor()
        while (pos < tokens.size) {
            val t = tokens[pos]
            if (t !is Token.Operator || t.symbol !in "×÷") break
            pos++
            val right = factor()
            value = if (t.symbol == '×') value * right else value / right
        }
        return value
    }

    private fun factor(): Double {
        val t = tokens.getOrNull(pos++) ?: error("Unexpected end")
        return when (t) {
            is Token.Number -> t.value
            Token.OpenBracket -> {
                val inside = expression()
                if (tokens.getOrNull(pos++) != Token.CloseBracket) error("Missing )")
                inside
            }

            else -> error("Unexpected $t")
        }
    }

    fun evaluate(): Double {
        val result = expression()
        return if (pos < tokens.size) error("Unexpected ${tokens[pos]}")
        else result
    }
}