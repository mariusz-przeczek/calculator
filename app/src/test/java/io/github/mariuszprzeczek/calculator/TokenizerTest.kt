package io.github.mariuszprzeczek.calculator

import org.junit.Test
import org.junit.Assert.*

class TokenizerTest {
    @Test
    fun emptyString_returnsEmptyList() {
        val myList: List<Token> = emptyList()
        assertEquals(myList, tokenize(""))
    }

    @Test
    fun oneNumber_returnsOneNumberToken() {
        val myList: List<Token> = listOf(Token.Number(7.0))
        assertEquals(myList, tokenize("7"))
    }

    @Test
    fun multiDigitAndOperatorString_returnsCorrectTokens() {
        val myList: List<Token> =
            listOf(Token.Number(305.0), Token.Operator('÷'), Token.Number(6.0))
        assertEquals(myList, tokenize("305÷6"))
    }

    @Test
    fun stringWithAllOperators_returnsCorrectTokens() {
        val myList: List<Token> = listOf(
            Token.Number(1.0),
            Token.Operator('+'),
            Token.Number(2.0),
            Token.Operator('-'),
            Token.Number(3.0),
            Token.Operator('×'),
            Token.Number(4.0),
            Token.Operator('÷'),
            Token.Number(5.0)
        )
        assertEquals(myList, tokenize("1+2-3×4÷5"))
    }

    @Test
    fun stringWithBrackets_returnsCorrectTokens() {
        val myList: List<Token> = listOf(
            Token.Number(7.0),
            Token.Operator('×'),
            Token.OpenBracket,
            Token.Number(25.0),
            Token.CloseBracket
        )
        assertEquals(myList, tokenize("7×(25)"))
    }

    @Test
    fun unfinishedText_stillTokenizes() {
        val myList: List<Token> = listOf(Token.OpenBracket, Token.Number(2.0), Token.Operator('+'))
        assertEquals(myList, tokenize("(2+"))
    }

    @Test
    fun unknownChar_throwsError() {
        assertThrows(IllegalStateException::class.java) { tokenize("2a") }
    }
}