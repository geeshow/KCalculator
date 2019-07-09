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

    fun calc(oper2:Operand):Operand {
        when (operator) {
            MULTIPLY[0] -> multiply(oper2)
            DIVIDE[0] -> divide(oper2)
            PLUS[0] -> plus(oper2)
            MINUS[0] -> minus(oper2)
        }

        this.is_percent = false
        oper2.is_percent = false

        return this
    }

    fun multiply(oper2:Operand) {
        if ( this.is_percent  )
            this.value = this.value.divide(BigDecimal(100), MathContext.DECIMAL32)
        if ( oper2.is_percent )
            oper2.value = oper2.value.divide(BigDecimal(100), MathContext.DECIMAL32)

        value = value.multiply(oper2.value)
    }
    fun divide(oper2:Operand) {
        if ( this.is_percent  )
            this.value = this.value.divide(BigDecimal(100), MathContext.DECIMAL32)
        if ( oper2.is_percent )
            oper2.value = oper2.value.divide(BigDecimal(100), MathContext.DECIMAL32)

        value = value.divide(oper2.value, MathContext.DECIMAL128)
    }
    fun plus(oper2:Operand) {
        if ( this.is_percent && oper2.is_percent ) {
            this.value = this.value.divide(BigDecimal(100), MathContext.DECIMAL32)
            oper2.value = oper2.value.divide(BigDecimal(100), MathContext.DECIMAL32)
        }
        else if ( this.is_percent ) {
            this.value = this.value.divide(BigDecimal(100), MathContext.DECIMAL32).multiply(oper2.value)
        }
        else if ( oper2.is_percent ) {
            oper2.value = oper2.value.divide(BigDecimal(100), MathContext.DECIMAL32).multiply(this.value)
        }

        value = value.plus(oper2.value)
    }
    fun minus(oper2:Operand) {
        if ( this.is_percent && oper2.is_percent ) {
            this.value = this.value.divide(BigDecimal(100), MathContext.DECIMAL32)
            oper2.value = oper2.value.divide(BigDecimal(100), MathContext.DECIMAL32)
        }
        else if ( this.is_percent ) {
            this.value = this.value.divide(BigDecimal(100), MathContext.DECIMAL32).multiply(oper2.value)
        }
        else if ( oper2.is_percent ) {
            oper2.value = oper2.value.divide(BigDecimal(100), MathContext.DECIMAL32).multiply(this.value)
        }

        value = value.minus(oper2.value)
    }

    override fun toString():String {
        return value.setScale(18,BigDecimal.ROUND_HALF_UP).toString().trimEnd('0').trimEnd('.')
    }

}