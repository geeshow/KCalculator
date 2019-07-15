package com.ken207.mygit1

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal

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
        assertEquals("234*+",calc.testInfixToPostfix("2+3*4"))
        assertEquals("34*5+",calc.testInfixToPostfix("3*4+5"))
        assertEquals( "56+7*2/", calc.testInfixToPostfix("((5+6)*7)/2"))

        assertEquals( "-7", calc.calc("((-8+6)*7)/2").toString())
        assertEquals( "-49", calc.calc("(-(8+6)*7)/2").toString())
        assertEquals( "49", calc.calc("((8+6)*7)/2").toString())
        assertEquals( "33", calc.calc("(3*(11+22))/3").toString())
        assertEquals( "-33", calc.calc("(3*-(11+22))/3").toString())
        assertEquals( "-18", calc.calc("(3*(-10+20%))/2").toString())
        assertEquals( "-12", calc.calc("-10+20%").toString())
        assertEquals( "3", calc.calc("(3*(10*20%))/2").toString())
        assertEquals( "300", calc.calc("3*10*(2/20%)").toString())
        assertEquals( "0.1", calc.calc("20%/2").toString())
        assertEquals( "10", calc.calc("2/20%").toString())
        assertEquals( "1", calc.calc("2+(-1)").toString())
        assertEquals( "-30", calc.calc("3*10*(-2/2)").toString())


    }

}