package com.ken207.mygit1

import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class FormulaManager {
    var txtFormula: EditText
    private val START_CURSOR_POSITION:Int = 0
    private val END_CURSOR_POSITION:Int = 1

    constructor(txtFormula: EditText) {
        this.txtFormula = txtFormula
        this.txtFormula.inputType = 0
        setFormula("0")
    }

    private fun isCursorAtTheHead():Boolean {
        return txtFormula.getSelectionStart() == 0
    }

    private fun isCursorAtTheClose():Boolean {
        return txtFormula.getSelectionEnd() == txtFormula.text.length
    }

    private fun getLeftSideCharFromCursor():Char {
        if ( isCursorAtTheHead() )
            return ' '
        return txtFormula.text.get(txtFormula.getSelectionStart() - 1)
    }

    private fun getRightSideCharFromCursor():Char {
        if ( isCursorAtTheClose() )
            return ' '
        return txtFormula.text.get(txtFormula.getSelectionStart() - 1)
    }

    private fun getCursorPosition():Array<Int> {
        return arrayOf(txtFormula.getSelectionStart(),txtFormula.getSelectionEnd())
    }

    fun addFormula(inputStr:String, cursorStartIdx:Int=txtFormula.getSelectionStart(), cursorEndIdx:Int=txtFormula.getSelectionEnd()) {
        txtFormula.getText().replace(cursorStartIdx, cursorEndIdx, inputStr)
        txtFormula.setSelection(cursorStartIdx+inputStr.length,cursorEndIdx+inputStr.length)
    }

    fun addFormula(inputChar:Char, cursorStartIdx:Int=txtFormula.getSelectionStart(), cursorEndIdx:Int=txtFormula.getSelectionEnd()) {
        txtFormula.getText().replace(cursorStartIdx, cursorEndIdx, inputChar.toString())
        txtFormula.setSelection(cursorStartIdx+1,cursorEndIdx+1)
    }

    fun setFormula(char:String) {
        // TODO SpannableString
        txtFormula.setText(char)
        txtFormula.setSelection(txtFormula.text.length,txtFormula.text.length)
    }

    fun putNumber(inputChar:Char) {
        if ( isCursorAtTheHead() ) {
            if ( inputChar == '0' )
                return
        }
        else {
            // when the left side charactor is a percent(%) symbol if put a number, add multiply(*) operator symbol
            if ( getLeftSideCharFromCursor() == PERCENT )
                addFormula(MULTIPLY)
        }

        addFormula(inputChar)
    }

    fun putOperator(operator:Char) {
        val cursorPosition:Array<Int> = getCursorPosition()

        if ( isCursorAtTheHead() ) {
            return // Deny putting operator as a first
        }
        else {
            if ( isOperator(getLeftSideCharFromCursor()) ) {
                cursorPosition[START_CURSOR_POSITION]--
            }
        }

        if ( !isCursorAtTheClose() ) {
            if ( isOperator(getRightSideCharFromCursor()) ) {
                cursorPosition[END_CURSOR_POSITION]++
            }
        }

        addFormula(operator, cursorPosition[START_CURSOR_POSITION], cursorPosition[END_CURSOR_POSITION])
    }

    fun setBracket() {
        var whichBracket:Char = L_BRACKET // "("

        if ( isCursorAtTheHead() ) {
            whichBracket = L_BRACKET // "("
        }
        else {
            val leftSideChar:Char = getLeftSideCharFromCursor()

            if ( isOperator(leftSideChar)  )
                whichBracket = L_BRACKET // "("
            else if ( leftSideChar == L_BRACKET )
                whichBracket = L_BRACKET // "("
            else
                whichBracket = R_BRACKET // ")"
        }

        addFormula(whichBracket)
    }

    fun setPercent() {
        var whichBracket:Char = L_BRACKET // "("

        if ( isCursorAtTheHead() ) {
            return
        }
        else {
            val leftSideChar:Char = getLeftSideCharFromCursor()

            if ( isOperator(leftSideChar)  )
                return
            else if ( leftSideChar == L_BRACKET ) // '('
                return
            else
                addFormula(PERCENT)
        }
    }

    fun setNegative() {
        val cursorPosition:Array<Int> = getCursorPosition()

        if ( isCursorAtTheHead() ) {
            return // Deny putting operator as a first
        }
        else {

        }

        if ( !isCursorAtTheClose() ) {
            if ( isOperator(getRightSideCharFromCursor()) ) {
                cursorPosition[END_CURSOR_POSITION]++
            }
        }
    }

    fun calc():String {
        return Calculator().calc(toString()).toString()
    }

    private fun isOperator(char:Char):Boolean {
        if ( char in setOf(MULTIPLY, DIVIDE, PLUS, MINUS))
            return true

        return false
    }

    override fun toString():String {
        return txtFormula.text.toString()
    }

}