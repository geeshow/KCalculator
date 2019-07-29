package com.ken207.mygit1

import org.junit.Test

import org.junit.Assert.*

class StringUtilTest {

    @Test
    fun getSelectedOperand2() {
        assertEquals("1", StringUtil.setCurrencyPattern("1"))
        assertEquals("12", StringUtil.setCurrencyPattern("12"))
        assertEquals("123", StringUtil.setCurrencyPattern("123"))
        assertEquals("1,234", StringUtil.setCurrencyPattern("1234"))
        assertEquals("12,345", StringUtil.setCurrencyPattern("12345"))
        assertEquals("123,456", StringUtil.setCurrencyPattern("123456"))
        assertEquals("1,234,567", StringUtil.setCurrencyPattern("1234567"))
        assertEquals("-12,345", StringUtil.setCurrencyPattern("-12345"))
        assertEquals("12,345", StringUtil.setCurrencyPattern("0012345"))
        assertEquals("1,234.5", StringUtil.setFormat("1234.5"))
    }

    @Test
    fun getSelectedOperand() {
        assertEquals( "(-3)",StringUtil.toggleNegative("3"))
        assertEquals( "3",StringUtil.toggleNegative("(-3)"))
        assertEquals( "(-(3*(123456+20%)))",StringUtil.toggleNegative("(3*(123456+20%))"))
        assertEquals( "(3*(123456+20%))",StringUtil.toggleNegative("(-(3*(123456+20%)))"))
        assertEquals( "(3*(123456+20%))",StringUtil.toggleNegative(StringUtil.toggleNegative(StringUtil.toggleNegative("(-(3*(123456+20%)))"))))
        assertEquals( "(3*(123456+20%))",StringUtil.toggleNegative("-(3*(123456+20%))"))
    }

    @Test
    fun getSelectedUnitPosition() {
        assertArrayEquals( arrayOf(0,14), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",0))
        assertArrayEquals( arrayOf(1,2), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",1))
        assertArrayEquals( arrayOf(1,2), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",2))
        assertArrayEquals( arrayOf(3,13), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",3))
        assertArrayEquals( arrayOf(4,8), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",4))
        assertArrayEquals( arrayOf(4,8), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",5))
        assertArrayEquals( arrayOf(4,8), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",6))
        assertArrayEquals( arrayOf(4,8), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",7))
        assertArrayEquals( arrayOf(4,8), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",8))
        assertArrayEquals( arrayOf(9,12), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",9))
        assertArrayEquals( arrayOf(9,12), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",10))
        assertArrayEquals( arrayOf(9,12), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",11))
        assertArrayEquals( arrayOf(9,12), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",12))
        assertArrayEquals( arrayOf(3,13), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",13))
        assertArrayEquals( arrayOf(0,14), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",14))
        assertArrayEquals( arrayOf(15,16), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",15))
        assertArrayEquals( arrayOf(15,16), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",16))

        assertArrayEquals( arrayOf(0,0), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",0))
        assertArrayEquals( arrayOf(0,2), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",1))
        assertArrayEquals( arrayOf(0,2), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",2))
        assertArrayEquals( arrayOf(3,14), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",3))
        assertArrayEquals( arrayOf(4,9), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",4))
        assertArrayEquals( arrayOf(4,9), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",5))
        assertArrayEquals( arrayOf(4,9), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",6))
        assertArrayEquals( arrayOf(4,9), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",7))
        assertArrayEquals( arrayOf(4,9), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",8))
        assertArrayEquals( arrayOf(4,9), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",9))

        assertArrayEquals( arrayOf(10,13), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",10))
        assertArrayEquals( arrayOf(10,13), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",11))
        assertArrayEquals( arrayOf(10,13), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",12))
        assertArrayEquals( arrayOf(10,13), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",13))
        assertArrayEquals( arrayOf(3,14), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",14))
        assertArrayEquals( arrayOf(15,19), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",15))
        assertArrayEquals( arrayOf(15,19), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",16))
        assertArrayEquals( arrayOf(15,19), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",17))
        assertArrayEquals( arrayOf(15,19), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",18))
        assertArrayEquals( arrayOf(15,19), StringUtil.getSelectedUnitPosition("-3*(-1234+20%)/(-2)",19))
    }
}