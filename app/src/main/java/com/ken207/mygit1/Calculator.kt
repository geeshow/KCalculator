package com.ken207.mygit1

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.MathContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Calculator {

    init {

    }

    fun calc(infixFormula:String): BigDecimal? {
        val arrPostfix:ArrayList<String> = changeInfixToPostfix(infixFormula)
        val arrStackForCalculator:Stack<BigDecimal> = Stack()
        var operand:BigDecimal = BigDecimal.ZERO

        arrPostfix.forEach{
            if ( it == MULTIPLY ) {
                calcTwoUnitInStack(arrStackForCalculator, {op1:BigDecimal, op2:BigDecimal ->
                    op1.multiply(op2)
                })
            }
            else if ( it == DIVIDE ) {
                calcTwoUnitInStack(arrStackForCalculator, {op1:BigDecimal, op2:BigDecimal ->
                    op1.divide(op2, MathContext.UNLIMITED)
                })
            }
            else if ( it == PLUS ) {
                calcTwoUnitInStack(arrStackForCalculator, {op1:BigDecimal, op2:BigDecimal ->
                    op1.plus(op2)
                })
            }
            else if ( it == MINUS ) {
                calcTwoUnitInStack(arrStackForCalculator, {op1:BigDecimal, op2:BigDecimal ->
                    op1.minus(op2)
                })
            }
            else {
                arrStackForCalculator.push(BigDecimal(it))
            }
        }

        return arrStackForCalculator.pop()
    }

    private fun calcTwoUnitInStack(arrStackForCalculator: Stack<BigDecimal>, function: (BigDecimal, BigDecimal) -> BigDecimal) {
        if ( arrStackForCalculator.empty() ) {
            arrStackForCalculator.push(BigDecimal.ZERO)
        }
        else if ( arrStackForCalculator.size == 1) {
            return
        }
        else {
            var operand1:BigDecimal = arrStackForCalculator.pop()
            var operand2:BigDecimal = arrStackForCalculator.pop()
            arrStackForCalculator.push(function(operand2, operand1))
        }
    }

    public fun splitStringToArray(infixFormula:String): ArrayList<String> {
        val arrInfix = ArrayList<String>()
        val sbOperand: StringBuilder = StringBuilder()

        infixFormula.forEachIndexed { index, it ->
            when (it) {
                in '0' .. '9','%' -> {
                    sbOperand.append(it)
                    if ( infixFormula.lastIndex == index || ( infixFormula[index + 1] !in '0'..'9' && infixFormula[index + 1] != '%' ) ) {
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

    public fun testInfixToPostfix(infixFormula:String):String {
        val arrInfix: ArrayList<String> = splitStringToArray(infixFormula)
        val postFix:ArrayList<String> = changeInfixToPostfix(arrInfix)
        val sbPostFix = StringBuilder()

        postFix.forEach { it ->
            sbPostFix.append(it)
        }

        return sbPostFix.toString()
    }

    private fun changeInfixToPostfix(infixFormula:String):ArrayList<String> {
        val arrInfix: ArrayList<String> = splitStringToArray(infixFormula)
        return changeInfixToPostfix(arrInfix)
    }

    private fun changeInfixToPostfix(arrInfix:ArrayList<String>):ArrayList<String> {
        val arrPostfix = ArrayList<String>()
        val arrTempStack = Stack<String>()
        val tempSubExpression:ArrayList<String> = ArrayList()
        var numBracketDepth:Int = 0

        arrInfix.forEach { it->
            if ( numBracketDepth == 0 ) {
                if ( it in setOf(PLUS,MINUS,MULTIPLY,DIVIDE) )
                    chkAndAddTempStack(it, arrTempStack, arrPostfix)
                else if ( it == L_BRACKET )
                    numBracketDepth++
                else
                    arrPostfix.add(it)
            }
            else {
                if ( it == R_BRACKET ) {
                    numBracketDepth--

                    if ( numBracketDepth == 0 )
                        arrPostfix.addAll(changeInfixToPostfix(tempSubExpression))
                    else
                        tempSubExpression.add(it)
                }
                else {
                    tempSubExpression.add(it)

                    if ( it == L_BRACKET )
                        numBracketDepth++
                }
            }
        }

        while ( !arrTempStack.empty() ) {
            arrPostfix.add(arrTempStack.pop())
        }

        return arrPostfix
    }

    public fun chkAndAddTempStack(operator:String, arrTempStack:Stack<String>, arrPostfix:ArrayList<String>) {
        if ( arrTempStack.empty() ) {
            arrTempStack.push(operator)
        } else {
            val preOp: String = arrTempStack.pop()

            if ( opPriority(preOp) == opPriority(operator) ) {
                arrPostfix.add(preOp)
                arrTempStack.push(operator)
            }
            else if ( opPriority(preOp) > opPriority(operator)) {
                arrPostfix.add(preOp)
                arrTempStack.push(operator)
            }
            else  {
                arrTempStack.push(preOp)
                arrTempStack.push(operator)
            }
        }
    }
}

fun opPriority(op:String):Int {
    if ( op in setOf("*","/") )
        return 3

    else if ( op in setOf("+","-") )
        return 2

    else
        return 0
}

