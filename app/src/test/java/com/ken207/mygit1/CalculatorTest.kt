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
    fun splitStringToArray() {
        assertArrayEquals(ArrayList(listOf("1","+","2")).toArray(), calc.splitStringToArray("1+2").toArray())
        assertArrayEquals(ArrayList(listOf("11","+","22","*","44")).toArray(), calc.splitStringToArray("11+22*44").toArray())
        assertArrayEquals(ArrayList(listOf("(","123","+","456",")","*","789","*","100")).toArray(), calc.splitStringToArray("(123+456)*789*100").toArray())
    }

    @Test
    fun changeInfixToPostfix() {
        assertArrayEquals(ArrayList(listOf("1","2","+")).toArray(), calc.changeInfixToPostfix(calc.splitStringToArray("1+2")).toArray())
        assertArrayEquals(ArrayList(listOf("111","223","455","*","+")).toArray(), calc.changeInfixToPostfix(calc.splitStringToArray("111+223*455")).toArray())
        assertArrayEquals(ArrayList(listOf("111","223","455","222","111","+","*","*","+")).toArray(), calc.changeInfixToPostfix(calc.splitStringToArray("111+223*455*(222+111)")).toArray())
    }

    @Test
    fun chkAndAddTempStack() {
        val sampleData:ArrayList<String> = ArrayList()
        sampleData.add("+")
        calc.chkAndAddTempStack(sampleData, "*")
        assertArrayEquals(ArrayList(listOf("+","*")).toArray(), sampleData.toArray())

        calc.chkAndAddTempStack(sampleData, "+")
        assertArrayEquals(ArrayList(listOf("+","+","*")).toArray(), sampleData.toArray())
    }
}