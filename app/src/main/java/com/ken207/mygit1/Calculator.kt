package com.ken207.mygit1

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.MathContext
import java.util.*
import kotlin.collections.ArrayList

class Calculator {

    init {
    }

    fun calc(infixFormula:String): BigDecimal? {
        val arrInfix:ArrayList<String> = splitStringToArray(infixFormula)
        val arrPostfix:ArrayList<String> = changeInfixToPostfix(arrInfix)

        val arrStackForCalculator:Stack<BigDecimal> = Stack()
        var operand:BigDecimal = BigDecimal.ZERO
        arrPostfix.forEach{
            if ( it == MULTIPLY ) {
                operand = arrStackForCalculator.pop()

                arrStackForCalculator.push(
                    arrStackForCalculator.pop().multiply(operand)
                )
            }
            else if ( it == DIVIDE ) {
                operand = arrStackForCalculator.pop()
                arrStackForCalculator.push(
                    arrStackForCalculator.pop().divide(operand, MathContext.UNLIMITED)
                )
            }
            else if ( it == PLUS ) {
                operand = arrStackForCalculator.pop()
                arrStackForCalculator.push(
                    arrStackForCalculator.pop().plus(operand)
                )
            }
            else if ( it == MINUS ) {
                operand = arrStackForCalculator.pop()
                arrStackForCalculator.push(
                    arrStackForCalculator.pop().minus(operand)
                )
            }
            else {
                arrStackForCalculator.push(BigDecimal(it))
            }
        }

        return arrStackForCalculator.pop()
    }

    private fun twoUnit(arrStackForCalculator:Stack<String>, op:Function<BigDecimal>) {

    }

    public fun splitStringToArray(reqExpression:String): ArrayList<String> {
        val arrInfix = ArrayList<String>()
        val sbOperand: StringBuilder = StringBuilder()

        reqExpression.forEachIndexed { index, it ->
            when (it) {
                in '0' .. '9' -> {
                    sbOperand.append(it)
                    if ( reqExpression.lastIndex == index || reqExpression[index + 1] !in '0'..'9' ) {
                        arrInfix.add(sbOperand.toString())
                        sbOperand.clear()
                    }
                }
                else -> {
                    arrInfix.add(it.toString())
                }
            }
        }
        return arrInfix
    }

    public fun changeInfixToPostfix(arrInfix:ArrayList<String>):ArrayList<String> {
        val arrPostfix = ArrayList<String>()
        val arrTempStack = Stack<String>()
        val tempSubExpression:ArrayList<String> = ArrayList()
        var numBracketDepth:Int = 0

        arrInfix.forEach { it->
            if ( numBracketDepth == 0 && it in setOf(PLUS,MINUS) ) {
                arrTempStack.push(it)
            }
            else if ( numBracketDepth == 0 && it in setOf(MULTIPLY,DIVIDE) ) {
                chkAndAddTempStack(arrTempStack, it)
            }
            else if ( it == L_BRACKET ) {
                numBracketDepth++
            }
            else if ( it == R_BRACKET ) {
                numBracketDepth--

                if ( numBracketDepth == 0 ) {
                    arrPostfix.addAll(changeInfixToPostfix(tempSubExpression))
                }
            }
            else if ( numBracketDepth > 0 ) {
                tempSubExpression.add(it)
            }
            else {
                arrPostfix.add(it)
            }
        }

        while ( !arrTempStack.empty() ) {
            arrPostfix.add(arrTempStack.pop())
        }

        return arrPostfix
    }

    public fun chkAndAddTempStack(arrTempStack:Stack<String>, operator:String) {
        if ( arrTempStack.empty() ) {
            arrTempStack.push(operator)
        } else {
            val preOp: String = arrTempStack.pop()
            if ((preOp == MULTIPLY || preOp == DIVIDE) && (operator == PLUS || operator == MINUS)) {
                arrTempStack.push(operator)
                arrTempStack.push(preOp)
            } else {
                arrTempStack.push(operator)
            }
        }
    }
}