package com.ken207.mygit1

import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class FormulaManager {
    var txtFormula: EditText
    private val START_CURSOR_POSITION:Int = 0
    private val END_CURSOR_POSITION:Int = 1

    constructor(txtFormula: EditText) {
        this.txtFormula = txtFormula
        setFormula("0")
    }

    private fun isCursorAtTheHead():Boolean {
        return txtFormula.getSelectionStart() == 0
    }

    private fun isCursorAtTheClose():Boolean {
        return txtFormula.getSelectionEnd() == getFormula().length
    }

    private fun getLeftSideCharFromCursor():Char {
        if ( isCursorAtTheHead() )
            return ' '
        return getFormula().get(txtFormula.getSelectionStart() - 1)
    }

    private fun getRightSideCharFromCursor():Char {
        if ( isCursorAtTheClose() )
            return ' '
        return getFormula().get(txtFormula.getSelectionStart() + 1)
    }

    private fun getCursorPosition():Array<Int> {
        return arrayOf(txtFormula.getSelectionStart(),txtFormula.getSelectionEnd())
    }

    fun addFormula(inputStr:String, cursorStartIdx:Int=txtFormula.getSelectionStart(), cursorEndIdx:Int=txtFormula.getSelectionEnd()) {
        txtFormula.getText().replace(cursorStartIdx, cursorEndIdx, inputStr)
        txtFormula.setSelection(cursorStartIdx+inputStr.length,cursorStartIdx+inputStr.length)
    }

    fun addFormula(inputChar:Char, cursorStartIdx:Int=txtFormula.getSelectionStart(), cursorEndIdx:Int=txtFormula.getSelectionEnd()) {
        addFormula(inputChar.toString(), cursorStartIdx, cursorEndIdx);
    }

    fun setFormula(char:String) {
        // TODO SpannableString
        txtFormula.setText(char)
        txtFormula.setSelection(getFormula().length,getFormula().length)
    }

    fun setFormula(char:Char) {
        setFormula(char.toString())
    }

    fun putNumber(inputChar:Char):Int {
        var resultCode:Int = 0

        if ( isCursorAtTheHead() && inputChar == '0' ) {
            resultCode = 1
        }
        else if ( getStringFormula() == "0" ) {
            setFormula(inputChar)
        }
        else if ( getOperandNumber().length >= 18 ) {
            resultCode = 2
        }
        else {
            // when the left side charactor is a percent(%) symbol if put a number, add multiply(*) operator symbol
            val leftSideChar:Char = getLeftSideCharFromCursor()
            if ( leftSideChar == PERCENT || leftSideChar == R_BRACKET )
                addFormula(MULTIPLY)

            addFormula(inputChar)
        }

        return resultCode
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

    fun putDigit():Int {
        var resultCode:Int = 0

        var operand:String = getOperandNumber()
        if ( operand.length >= 18 ) {
            resultCode = 2
        }
        else {
            // when the left side charactor is a percent(%) symbol if put a number, add multiply(*) operator symbol
            var unitPosition:Array<Int> = StringUtil.getNumberUnitPosition(txtFormula.getText().toString(), getCursorPosition()[0])
            doDelete('.', unitPosition[0], unitPosition[1])

            if ( isCursorAtTheHead() ) {
                addFormula("0.")
            }
            else if ( !isOperand(getLeftSideCharFromCursor()) )
                addFormula("0.")
            else
                addFormula(DIGIT)
        }

        return resultCode
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
            else if ( isOperand(getRightSideCharFromCursor()) )
                return
            else
                addFormula(PERCENT)
        }
    }

    fun setNegative() {
        var unitPosition:Array<Int> = StringUtil.getSelectedUnitPosition(txtFormula.getText().toString(), getCursorPosition()[0])
        var operand:String = StringUtil.toggleNegative(
                        txtFormula.getText().toString().substring(unitPosition[0], unitPosition[1])
            )
        addFormula(operand, unitPosition[0], unitPosition[1])
    }

    fun calc():String {
        return Calculator().calc(toString()).toString()
    }

    private fun isOperator(char:Char):Boolean {
        if ( char in setOf(MULTIPLY, DIVIDE, PLUS, MINUS))
            return true

        return false
    }

    private fun isOperand(char:Char):Boolean {
        if ( char in '0' .. '9')
            return true
        else if ( char in setOf(',','.','%'))
            return true

        return false
    }

    fun doBackspace() {
        doDelete(txtFormula.getSelectionStart() - 1, txtFormula.getSelectionEnd());
    }

    fun doDelete(idxSelectionStart:Int=txtFormula.getSelectionStart(), idxSelectionEnd:Int=txtFormula.getSelectionEnd()) {
        if ( idxSelectionStart >= 0 ) {
            txtFormula.getText().replace(idxSelectionStart, idxSelectionEnd, "")
        }
    }

    fun doDelete(chrTarget:Char, idxStart:Int=txtFormula.getSelectionStart(), idxEnd:Int=txtFormula.getSelectionEnd()) {
        var targetIdx:Int = txtFormula.getText().indexOf(chrTarget.toString(), idxStart)

        if ( targetIdx > -1 && targetIdx <= idxEnd )
            doDelete(targetIdx, targetIdx+1)
    }

    fun getOperandNumber():String {
        val cursorPosition:Array<Int> = getCursorPosition()
        return StringUtil.getNumberUnit(getStringFormula(), cursorPosition[0])
    }

    fun getStringFormula():String {
        return toString()
    }

    fun getFormula(): Editable {
        return txtFormula.text
    }
    override fun toString():String {
        return getFormula().toString()
    }

}