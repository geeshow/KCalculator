package com.ken207.mygit1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnMulti, btnMinus, btnPlus, btnNegative, btnDecimal, btnEqual).forEach {
            it.setOnClickListener { clickButton(it) }
        }
    }

    private fun clickButton(btnNum: View) {
        when (btnNum.id) {
            R.id.btn0 -> putZero()
            R.id.btn1 -> putNumber("1")
            R.id.btn2 -> putNumber("2")
            R.id.btn3 -> putNumber("3")
            R.id.btn4 -> putNumber("4")
            R.id.btn5 -> putNumber("5")
            R.id.btn6 -> putNumber("6")
            R.id.btn7 -> putNumber("7")
            R.id.btn8 -> putNumber("8")
            R.id.btn9 -> putNumber("9")
            R.id.btnMulti -> putOperator(MULTIPLY)
            R.id.btnMinus -> putOperator(MINUS)
            R.id.btnPlus -> putOperator(PLUS)
            R.id.btnNegative -> setNegative()
            R.id.btnDecimal -> putOperator(DIGIT)
            R.id.btnEqual -> doCalculate()
            R.id.btnClean -> doClean()
            R.id.btnBracket -> setBracket()
            R.id.btnPercent -> setPercent()
            R.id.btnDivide -> putOperator(DIVIDE)
            else -> Toast.makeText(this@MainActivity, R.string.notImplemented, Toast.LENGTH_LONG).show()
        }
    }

    private fun putZero() {
        if ( txtRslt.text.toString() != "0" )
            putNumber("0")
    }

    private fun putNumber(number:String) {
        addStringToExpression(number)
    }

    private fun putOperator(operator:String) {
        addStringToExpression(operator)
    }

    private fun setNegative() {

    }

    private fun doCalculate() {
        calculator(txtRslt.text.toString())
    }

    private fun doClean() {
        txtRslt.setText("")
    }

    private fun setBracket() {

    }

    private fun setPercent() {

    }

    private fun addStringToExpression(char:String) {
        // TODO SpannableString
        txtRslt.setText(txtRslt.text.toString() + char)
    }

    private fun addOperatorToStack(arrStack:ArrayList<Int>, operator:Char) {

    }
    private fun calculator(reqExpression:String) {

        var arrPostFix = ArrayList<String>()
        var arrStack = ArrayList<Int>()
        var sbOperand:StringBuffer = StringBuffer()
        val expLength : Int = reqExpression.length

        reqExpression.forEachIndexed { index, it ->
            when (it) {
                in '0' .. '9' -> sbOperand.append(it)
                else -> arrPostFix.add(sbOperand.toString())
            }

            when (it) {
                '+' -> arrStack.add(1)
                '-' -> arrStack.add(2)

            }

        }

        var expression:String = reqExpression
        if ( expression.indexOf(L_BRACKET) >= 0 ) {
            val startIdx = expression.indexOf(L_BRACKET)
            val endIdx = expression.lastIndexOf(R_BRACKET)
            val subExpression = expression.substring(startIdx+1, endIdx)

            var sbExpression:StringBuffer = StringBuffer()
            sbExpression.append(expression.substring(0, startIdx))
            sbExpression.append(calculator(subExpression))
            sbExpression.append(expression.substring(endIdx, expression.length))

            expression = sbExpression.toString()
        }

        if ( expression.indexOf(MULTIPLY) >= 0 ) {
            val startIdx = expression.indexOf('(')
            val endIdx = expression.lastIndexOf(')')
            val subExpression = expression.substring(startIdx+1, endIdx)

            calculator(subExpression)
        }


    }
}


