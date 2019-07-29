package com.ken207.mygit1

import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.MathContext
import java.util.*
import kotlin.collections.ArrayList

class Calculator {

    init {

    }

    fun calc(arrInfix:ArrayList<String>): Operand? {
        val arrPostfix:ArrayList<String> = changeInfixToPostfix(arrInfix)
        val arrStackForCalculator:Stack<Operand> = Stack()
        var operand:BigDecimal = BigDecimal.ZERO

        arrPostfix.forEach{
            if ( it in setOf(S_MULTIPLY,S_DIVIDE,S_PLUS,S_MINUS) ) {
                if ( arrStackForCalculator.empty() ) {
                    arrStackForCalculator.push(Operand("0"))
                }
                else if ( arrStackForCalculator.size > 1) {
                    var operand1:Operand = arrStackForCalculator.pop()
                    var operand2:Operand = arrStackForCalculator.pop()
                    arrStackForCalculator.push(operand2.setOperator(it).calc(operand1))
                }
            }
            else if ( it == S_PERCENT ) {
                arrStackForCalculator.push(arrStackForCalculator.pop().setPercent(true))
            }
            else {
                arrStackForCalculator.push(Operand(it))
            }
        }

        return arrStackForCalculator.pop()
    }

    public fun testInfixToPostfix(infixFormula:String):String {
        val arrInfix: ArrayList<String> = StringUtil.splitStringToArray(infixFormula)
        val postFix:ArrayList<String> = changeInfixToPostfix(arrInfix)
        val sbPostFix = StringBuilder()

        postFix.forEach { it ->
            sbPostFix.append(it)
        }

        return sbPostFix.toString()
    }

    private fun changeInfixToPostfix(arrInfix:ArrayList<String>):ArrayList<String> {
        val arrPostfix = ArrayList<String>()
        val arrTempStack = Stack<String>()
        val tempSubExpression:ArrayList<String> = ArrayList()
        var numBracketDepth:Int = 0

        arrInfix.forEach { it->
            if ( numBracketDepth == 0 ) {
                if ( it in setOf(S_PLUS,S_MINUS,S_MULTIPLY,S_DIVIDE) )
                    chkAndAddTempStack(it, arrTempStack, arrPostfix)
                else if ( it == S_L_BRACKET )
                    numBracketDepth++
                else if ( it == S_NAGATIVE ) {
                    arrPostfix.add("-1")
                    chkAndAddTempStack(S_MULTIPLY, arrTempStack, arrPostfix)
                }
                else
                    arrPostfix.add(it)
            }
            else {
                if ( it == S_R_BRACKET ) {
                    numBracketDepth--

                    if ( numBracketDepth == 0 )
                        arrPostfix.addAll(changeInfixToPostfix(tempSubExpression))
                    else
                        tempSubExpression.add(it)
                }
                else {
                    tempSubExpression.add(it)

                    if ( it == S_L_BRACKET )
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

