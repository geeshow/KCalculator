package com.ken207.mygit1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var myCalc:Calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtFormula.inputType = 0
        listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnMulti, btnMinus, btnPlus, btnNegative, btnDecimal, btnEqual,btnClean,btnBracket,btnPercent,btnDivide).forEach {
            it.setOnClickListener { clickButton(it) }
        }

        doClean()
    }

    private fun clickButton(btnNum: View) {
        when (btnNum.id) {
            R.id.btn0 -> putNumber('0')
            R.id.btn1 -> putNumber('1')
            R.id.btn2 -> putNumber('2')
            R.id.btn3 -> putNumber('3')
            R.id.btn4 -> putNumber('4')
            R.id.btn5 -> putNumber('5')
            R.id.btn6 -> putNumber('6')
            R.id.btn7 -> putNumber('7')
            R.id.btn8 -> putNumber('8')
            R.id.btn9 -> putNumber('9')
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

    //
    private fun putNumber(inputChar:Char) {
        var idxSelectionStart:Int = txtFormula.getSelectionStart()
        var idxSelectionEnd = txtFormula.getSelectionEnd()

        if ( idxSelectionStart == 0 ) {
            if ( inputChar == '0' )
                return
        }
        else {
            val leftSideChar:Char = txtFormula.text.get(idxSelectionStart - 1)
            val leftSideInput:Char = inputChar

            if ( leftSideChar == '%' )
                addFormula(MULTIPLY)
        }

        addFormula(inputChar)
    }

    private fun putOperator(operator:Char) {
        var idxSelectionStart:Int = txtFormula.getSelectionStart()
        var idxSelectionEnd = txtFormula.getSelectionEnd()

        if ( idxSelectionStart == 0 ) {
            return // Deny putting operator as a first
        }
        else {
            val leftSideChar:Char = txtFormula.text.get(idxSelectionStart - 1)

            if ( isOperator(leftSideChar) ) {
                idxSelectionStart--
            }
        }

        if ( idxSelectionEnd != txtFormula.text.length ) {
            val rightSideChar:Char = txtFormula.text.get(idxSelectionEnd)

            if ( isOperator(rightSideChar) ) {
                idxSelectionEnd++
            }
        }

        addFormula(operator, idxSelectionStart, idxSelectionEnd)
    }

    private fun setNegative() {

    }

    private fun doCalculate() {
        txtRslt.setText(
            myCalc.calc(getFormulaString()).toString()
        )
    }

    private fun doClean() {
        Toast.makeText(this@MainActivity, txtFormula.getSelectionStart().toString() + ":" + txtFormula.getSelectionEnd().toString(), Toast.LENGTH_LONG).show()
        setFormula("0")
    }

    private fun setBracket() {
        var idxSelectionStart:Int = txtFormula.getSelectionStart()
        var idxSelectionEnd = txtFormula.getSelectionEnd()
        var whichBracket:Char = L_BRACKET // "("

        if ( idxSelectionStart == 0 ) {
            whichBracket = L_BRACKET // "("
        }
        else {
            val leftSideChar:Char = txtFormula.text.get(idxSelectionStart - 1)

            if ( isOperator(leftSideChar)  )
                whichBracket = L_BRACKET // "("
            else if ( leftSideChar == L_BRACKET )
                whichBracket = L_BRACKET // "("
            else
                whichBracket = R_BRACKET // ")"
        }

        addFormula(whichBracket)
    }

    private fun setPercent() {
        var idxSelectionStart:Int = txtFormula.getSelectionStart()
        var idxSelectionEnd = txtFormula.getSelectionEnd()
        var whichBracket:Char = L_BRACKET // "("

        if ( idxSelectionStart == 0 ) {
            return
        }
        else {
            val leftSideChar:Char = txtFormula.text.get(idxSelectionStart - 1)

            if ( isOperator(leftSideChar)  )
                return
            else if ( leftSideChar == L_BRACKET ) // '('
                return
            else
                addFormula('%')
        }
    }

    private fun getFormulaString():String {
        return txtFormula.text.toString()
    }
    private fun addFormula(inputChar:Char, idxSelectionStart:Int=txtFormula.getSelectionStart(), idxSelectionEnd:Int=txtFormula.getSelectionEnd()) {
        var moveToSelectionStart:Int = txtFormula.getSelectionStart()
        var moveToSelectionEnd = txtFormula.getSelectionEnd()

        txtFormula.getText().replace(moveToSelectionStart, moveToSelectionEnd, inputChar.toString())
        txtFormula.setSelection(moveToSelectionStart+1,moveToSelectionStart+1)
    }



    private fun setFormula(char:String) {
        // TODO SpannableString
        txtFormula.setText(char)
        txtFormula.setSelection(txtFormula.text.length,txtFormula.text.length)
    }

    private fun checkAllowCharAtFirst(char:Char):Boolean {
        if ( char in setOf(MULTIPLY, DIVIDE, PLUS, MINUS, R_BRACKET, PERCENT))
            return false

        return true
    }

    private fun isOperator(char:Char):Boolean {
        if ( char in setOf(MULTIPLY, DIVIDE, PLUS, MINUS))
            return true

        return false
    }
}


