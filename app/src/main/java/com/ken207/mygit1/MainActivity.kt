package com.ken207.mygit1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var myCalc:Calculator = Calculator()

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
        if ( txtFormula.text.toString() != "0" )
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
        txtRslt.setText(
            myCalc.calc(txtFormula.text.toString()).toString()
        )
    }

    private fun doClean() {
        txtFormula.setText("0")
    }

    private fun setBracket() {

    }

    private fun setPercent() {

    }

    private fun addStringToExpression(char:String) {
        // TODO SpannableString
        txtFormula.setText(txtFormula.text.toString() + char)
    }
}


