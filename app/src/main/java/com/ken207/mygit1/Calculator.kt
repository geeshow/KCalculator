package com.ken207.mygit1

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class Calculator {

    init {
    }

    fun calc(infixFormula:String) {
        val arrInfix:ArrayList<String> = splitStringToArray(infixFormula)
        val arrPostfix:ArrayList<String> = changeInfixToPostfix(arrInfix)
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
            if ( numBracketDepth == 0 && it in setOf("+","-") ) {
                arrTempStack.add(it)
            }
            else if ( numBracketDepth == 0 && it in setOf("*","/") ) {
                chkAndAddTempStack(arrTempStack, it)
            }
            else if ( it == "(" ) {
                numBracketDepth++
            }
            else if ( it == ")" ) {
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
            if ((preOp == "*" || preOp == "/") && (operator == "+" || operator == "-")) {
                arrTempStack.set(stackSize - 1, operator)
                arrTempStack.add(preOp)
            } else {
                arrTempStack.add(operator)
            }
        }
    }
}