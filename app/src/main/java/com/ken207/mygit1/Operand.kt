package com.ken207.mygit1

import java.math.BigDecimal
import java.math.MathContext

class Operand {
    var value:BigDecimal = BigDecimal.ZERO
    var operator:Char = '+'
    var is_percent:Boolean = false

    constructor(param: String) {
        value = BigDecimal(param)
    }

    fun setPercent(param:Boolean):Operand {
        is_percent = param
        return this
    }

    fun setOperator(op:Char):Operand {
        operator = op
        return this
    }
    fun setOperator(op:String):Operand {
        operator = op[0]
        return this
    }

    fun calc(op2:Operand):Operand {
        when (operator) {
            MULTIPLY[0] -> multiply(op2)
            DIVIDE[0] -> divide(op2)
            PLUS[0] -> plus(op2)
            MINUS[0] -> minus(op2)
        }
        return this
    }

    fun multiply(op2:Operand) {
        value = value.multiply(op2.value)
    }
    fun divide(op2:Operand) {
        value = value.divide(op2.value, MathContext.UNLIMITED)
    }
    fun plus(op2:Operand) {
        value = value.plus(op2.value)
    }
    fun minus(op2:Operand) {
        value = value.minus(op2.value)
    }

    override fun toString():String {
        if ( value.toString().length > 10 ) {
            value.
        }
        return value.toString()
    }
}