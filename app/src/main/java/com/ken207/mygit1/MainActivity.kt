package com.ken207.mygit1

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    lateinit private var formulaMng:FormulaManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formulaMng = FormulaManager(txtFormula as MyEditText, txtRslt)
        txtFormula.showSoftInputOnFocus = false
        txtFormula.requestFocus()

        listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnMulti, btnMinus, btnPlus, btnNegative, btnDecimal, btnEqual,btnClean,btnBracket,btnPercent,btnDivide, btnDelete).forEach {
            it.setOnClickListener { clickButton(it) }
        }

        txtFormula.addTextChangedListener(object:TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                formulaMng.doCalculate()
            }

        })
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
            R.id.btnDecimal -> putDigit()
            R.id.btnEqual -> doCalculate()
            R.id.btnClean -> doClean()
            R.id.btnBracket -> setBracket()
            R.id.btnPercent -> setPercent()
            R.id.btnDivide -> putOperator(DIVIDE)
            R.id.btnDelete -> doBackspace()
            else -> Toast.makeText(this@MainActivity, R.string.notImplemented, Toast.LENGTH_LONG).show()
        }
    }

    //
    private fun putNumber(inputChar:Char) {
        var resultCode:Int = formulaMng.putNumber(inputChar)
        if ( resultCode == 1 ) {
            Toast.makeText(this, "0을 시작값으로 입력할 수 있습니다.", Toast.LENGTH_LONG).show()
        }
        else if ( resultCode == 2 ) {
            Toast.makeText(this, "15자리 이상으로 입력할 수 없습니다.", Toast.LENGTH_LONG).show()
        }
    }

    private fun putOperator(operator:Char) {
        formulaMng.putOperator(operator)
    }

    private fun putDigit() {
        formulaMng.putDigit()
    }

    private fun setNegative() {
        formulaMng.setNegative()
    }

    private fun doCalculate() {
        formulaMng.doCalculate()
    }

    private fun doClean() {
        formulaMng.setFormula("0")
    }

    private fun setBracket() {
        formulaMng.setBracket()
    }

    private fun setPercent() {
        formulaMng.setPercent()
    }

    private fun doBackspace() {
        formulaMng.doBackspace()
    }

}


