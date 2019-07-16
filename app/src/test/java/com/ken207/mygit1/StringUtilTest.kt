package com.ken207.mygit1

import org.junit.Test

import org.junit.Assert.*

class StringUtilTest {

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