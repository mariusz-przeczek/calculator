package io.github.mariuszprzeczek.calculator

fun tokenize(text: String): List<Token> {
    val result = mutableListOf<Token>()

    var i = 0
    while ( i < text.length ) {
        val c = text[i]
        when {
            c.isDigit() -> {
                val start = i
                while (i < text.length && (text[i].isDigit() || text[i] == '.')) i++
                result.add(Token.Number(text.substring(start, i).toDouble()))
            }
            c in "+-×÷" -> {
                result.add(Token.Operator(c))
                i++
            }
            c == '(' -> {
                result.add(Token.OpenBracket)
                i++
            }
            c == ')' -> {
                result.add(Token.CloseBracket)
                i++
            }
            else -> error("Unknown char '$c'")
        }
    }

    return result
}