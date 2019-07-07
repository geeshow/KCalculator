package com.ken207.mygit1

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CalculatorTest {
    private var calc:Calculator = Calculator()
    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun testInfixToPostfix() {
 //       assertEquals("234*+",calc.testInfixToPostfix("2+3*4"))
//        assertEquals("34*5+",calc.testInfixToPostfix("3*4+5"))

        assertEquals( "56+7*2/", calc.testInfixToPostfix("((5+6)*7)/2"))
    }

}