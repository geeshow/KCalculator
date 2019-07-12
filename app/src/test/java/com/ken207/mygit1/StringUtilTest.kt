package com.ken207.mygit1

import org.junit.Test

import org.junit.Assert.*

class StringUtilTest {

    @Test
    fun getSelectedOperand() {
        assertEquals( "(3*(123456+20%))", StringUtil.getSelectedOperand("(3*(123456+20%))/2",0))
        assertEquals( "3", StringUtil.getSelectedOperand("(3*(123456+20%))/2",1))
        assertEquals( "3", StringUtil.getSelectedOperand("(3*(123456+20%))/2",2))
        assertEquals( "(123456+20%)", StringUtil.getSelectedOperand("(3*(123456+20%))/2",3))
        assertEquals( "123456", StringUtil.getSelectedOperand("(3*(123456+20%))/2",4))
        assertEquals( "123456", StringUtil.getSelectedOperand("(3*(123456+20%))/2",5))
        assertEquals( "123456", StringUtil.getSelectedOperand("(3*(123456+20%))/2",6))
        assertEquals( "123456", StringUtil.getSelectedOperand("(3*(123456+20%))/2",7))
        assertEquals( "123456", StringUtil.getSelectedOperand("(3*(123456+20%))/2",8))
        assertEquals( "123456", StringUtil.getSelectedOperand("(3*(123456+20%))/2",9))
        assertEquals( "123456", StringUtil.getSelectedOperand("(3*(123456+20%))/2",10))
        assertEquals( "20%", StringUtil.getSelectedOperand("(3*(123456+20%))/2",11))
        assertEquals( "20%", StringUtil.getSelectedOperand("(3*(123456+20%))/2",12))
        assertEquals( "20%", StringUtil.getSelectedOperand("(3*(123456+20%))/2",13))
        assertEquals( "20%", StringUtil.getSelectedOperand("(3*(123456+20%))/2",14))
        assertEquals( "(123456+20%)", StringUtil.getSelectedOperand("(3*(123456+20%))/2",15))
        assertEquals( "(3*(123456+20%))", StringUtil.getSelectedOperand("(3*(123456+20%))/2",16))
        assertEquals( "2", StringUtil.getSelectedOperand("(3*(123456+20%))/2",17))
        assertEquals( "", StringUtil.getSelectedOperand("(3*(123456+20%))/2",18))
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
        assertArrayEquals( arrayOf(16,16), StringUtil.getSelectedUnitPosition("(3*(1234+20%))/2",16))
    }
}