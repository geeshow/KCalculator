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
        arrPostfix.forEach{
            if ( it == MULTIPLY ) {
                arrStackForCalculator.push(
                    arrStackForCalculator.pop().multiply(arrStackForCalculator.pop())
                )
            }
            else if ( it == DIVIDE ) {
                arrStackForCalculator.push(
                    arrStackForCalculator.pop().divide(arrStackForCalculator.pop(), MathContext.UNLIMITED)
                )
            }
            else if ( it == PLUS ) {
                arrStackForCalculator.push(
                    arrStackForCalculator.pop().plus(arrStackForCalculator.pop())
                )
            }
            else if ( it == MINUS ) {
                arrStackForCalculator.push(
                    arrStackForCalculator.pop().minus(arrStackForCalculator.pop())
                )
            }
            else {
                arrStackForCalculator.push(BigDecimal(it))
            }
        }

        return arrStackForCalculator.pop()
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
        val arrTempStack = ArrayList<String>()
        val tempSubExpression:ArrayList<String> = ArrayList()
        var numBracketDepth:Int = 0

        arrInfix.forEach { it->
            if ( numBracketDepth == 0 && it in setOf(PLUS,MINUS) ) {
                arrTempStack.add(it)
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

        arrPostfix.addAll(arrTempStack.reversed())
        return arrPostfix
    }

    public fun chkAndAddTempStack(arrTempStack:ArrayList<String>, operator:String) {
        val stackSize: Int = arrTempStack.size
        if (stackSize == 0) {
            arrTempStack.add(operator)
        } else {
            val preOp: String = arrTempStack.last()
            if ((preOp == MULTIPLY || preOp == DIVIDE) && (operator == PLUS || operator == MINUS)) {
                arrTempStack.set(stackSize - 1, operator)
                arrTempStack.add(preOp)
            } else {
                arrTempStack.add(operator)
            }
        }
    }
}