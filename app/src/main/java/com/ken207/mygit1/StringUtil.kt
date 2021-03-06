package com.ken207.mygit1

import android.icu.lang.UCharacter
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.NumberFormat

class  StringUtil {

    companion object {
        fun toggleNegative(operand: String):String {
            var changeUnit:StringBuilder = StringBuilder()
            if ( operand.startsWith("-") ) {
                changeUnit.append(operand.toString().substring(1, operand.length))
            }
            else if ( operand.startsWith("(-") ) {
                if ( operand.endsWith(")") )
                    changeUnit.append(operand.toString().substring(2, operand.length-1))
                else
                    changeUnit.append(operand.toString().substring(2, operand.length))
            }
            else {
                changeUnit.append("(-")
                changeUnit.append(operand)
                changeUnit.append(")")
            }

            return changeUnit.toString()
        }

        fun getNumberUnitPosition(strFormula: String, idxCursor: Int): Array<Int> {
            if ( idxCursor == 0 )
                return arrayOf(0, 0)

            var startIdx: Int = idxCursor
            var endIdx: Int = idxCursor
            val leftSideChar = strFormula[idxCursor-1]
            val rightSideChar = if (strFormula.length <= idxCursor) ' ' else strFormula[idxCursor]

            if (rightSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9','.',',') ) {
                if (leftSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9','.',',') )
                    startIdx = strFormula.lastIndexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET, PERCENT), idxCursor - 1) + 1

                endIdx = strFormula.indexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET, PERCENT), idxCursor)
                if ( endIdx == -1 )
                    endIdx = strFormula.length
                else
                    endIdx--
            }
            else if (leftSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9','.',',') ) {
                startIdx = strFormula.lastIndexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET), idxCursor - 1) + 1
            }

            return arrayOf(startIdx, endIdx)
        }

        fun getNumberUnit(strFormula: String, idxCursor: Int): String {
            val position:Array<Int> = getNumberUnitPosition(strFormula, idxCursor)
            return strFormula.substring(position[0], position[1])
        }

        fun getSelectedUnitPosition(strFormula: String, idxCursor: Int): Array<Int> {

            var startIdx: Int = idxCursor
            var endIdx: Int = idxCursor
            val leftSideChar = if ( idxCursor == 0 ) { ' ' } else { strFormula[idxCursor-1]}
            val rightSideChar = if (strFormula.length <= idxCursor) ' ' else strFormula[idxCursor]

            if (rightSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9', PERCENT) ) {
                if (leftSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9', PERCENT) )
                    startIdx = strFormula.lastIndexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET), idxCursor - 1) + 1
                else if (leftSideChar in setOf(MULTIPLY, DIVIDE, PLUS, MINUS) )
                    startIdx = idxCursor
                else if (leftSideChar == R_BRACKET )
                    startIdx = idxCursor
                else if (leftSideChar == L_BRACKET )
                    startIdx = idxCursor

                endIdx = strFormula.indexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET), idxCursor).coerceAtLeast(idxCursor+1)

            }
            else if (rightSideChar in setOf(MULTIPLY, DIVIDE, PLUS, MINUS, R_BRACKET,' ') ) {
                if (leftSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9', PERCENT) ) {
                    startIdx = strFormula.lastIndexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET), idxCursor - 1) + 1
                    endIdx = idxCursor
                }
                else if (leftSideChar in setOf(MULTIPLY, DIVIDE, PLUS, MINUS) ) {
                    startIdx = idxCursor
                    endIdx = idxCursor
                }
                else if (leftSideChar == R_BRACKET ) {
                    var bracketLevel:Int = 0
                    for (index: Int in idxCursor - 1 downTo 0) {
                        if (strFormula[index] == L_BRACKET)
                            bracketLevel++
                        else if (strFormula[index] == R_BRACKET)
                            bracketLevel--

                        if ( bracketLevel == 0 ) {
                            startIdx = index
                            break
                        }
                    }
                }
                else if (leftSideChar == L_BRACKET && rightSideChar == MINUS) {
                    startIdx = idxCursor
                    endIdx = strFormula.indexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET), idxCursor+1).coerceAtLeast(idxCursor+1)
                }
                else if (leftSideChar == L_BRACKET ) {
                    startIdx = idxCursor
                    endIdx = idxCursor
                }
            }
            else if (rightSideChar == L_BRACKET ) {
                var bracketLevel:Int = 0
                for (index: Int in idxCursor..strFormula.length - 1) {
                    if (strFormula[index] == L_BRACKET)
                        bracketLevel++
                    else if (strFormula[index] == R_BRACKET)
                        bracketLevel--

                    if ( bracketLevel == 0 ) {
                        endIdx = index + 1
                        break
                    }
                }
            }

            if ( startIdx == 0 ) {
                return arrayOf(startIdx, endIdx)
            }
            else if ( endIdx == strFormula.length ) {
                return arrayOf(startIdx, endIdx)
            }
            else if ( startIdx == 1 ) {
                if ( strFormula[0] == MINUS )
                    return arrayOf(0, endIdx)
            }
            else if ( strFormula[startIdx - 1] == L_BRACKET && strFormula[endIdx] == R_BRACKET ) {
                return arrayOf(startIdx-1, endIdx+1)
            }
            else if ( strFormula[startIdx - 1] == MINUS && strFormula[startIdx - 2] == L_BRACKET && strFormula[endIdx] == R_BRACKET ) {
                return arrayOf(startIdx-2, endIdx+1)
            }
            else if ( strFormula[startIdx - 1] == MINUS && strFormula[startIdx - 2] == L_BRACKET ) {
                return arrayOf(startIdx-1, endIdx)
            }

            return arrayOf(startIdx, endIdx)
        }

        fun splitStringToArray(infixFormula:String): ArrayList<String> {
            val arrInfix = ArrayList<String>()
            val sbOperand: StringBuilder = StringBuilder()
            var previousOne:Char = ' '

            infixFormula.forEachIndexed { index, it ->
                when (it) {
                    in '0' .. '9' -> {
                        sbOperand.append(it)
                        if ( infixFormula.lastIndex == index || infixFormula[index + 1] !in '0'..'9' ) {
                            arrInfix.add(sbOperand.toString())
                            sbOperand.clear()
                        }
                    }
                    ',' -> {}
                    '-' -> {
                        var isMinusOperator:Boolean = false
                        when ( previousOne ) {
                            PERCENT, R_BRACKET, DIGIT -> isMinusOperator = true
                            in '0' .. '9' -> isMinusOperator = true
                            else -> isMinusOperator = false
                        }

                        if ( isMinusOperator )
                            arrInfix.add(it.toString())
                        else
                            arrInfix.add(S_NAGATIVE)
                    }
                    else -> {
                        arrInfix.add(it.toString())
                    }
                }
                previousOne = it
            }
            return arrInfix
        }

        fun setCurrencyPattern(infixFormula:String): String {
            val sbPatternFormula: StringBuilder = StringBuilder()
            val sbOperand: StringBuilder = StringBuilder()
            var isPoint:Boolean = false

            infixFormula.forEachIndexed { index, it ->
                when (it) {
                    in '0' .. '9' -> {
                        if ( isPoint ) {
                            sbPatternFormula.append(it.toString())
                        }
                        else {
                            sbOperand.append(it)
                            if (infixFormula.lastIndex == index || infixFormula[index + 1] !in '0'..'9' || infixFormula[index + 1] == '.') {
                                sbPatternFormula.append(setFormat(sbOperand.toString()))
                                sbOperand.clear()
                            }
                        }
                    }
                    ',' -> {}
                    '.' -> {
                        isPoint = true
                        sbPatternFormula.append(it.toString())
                    }
                    else -> {
                        isPoint = false
                        sbPatternFormula.append(it.toString())
                    }
                }
            }
            return sbPatternFormula.toString()
        }

        fun setFormat(unit:String):String {
            val digitIndex:Int = unit.indexOf('.') - 1
            var sbUnit:StringBuilder = StringBuilder()

            unit.forEachIndexed { index, c ->
                if ( index % 3 == digitIndex ) {

                }
            }
            val nf = NumberFormat.getCurrencyInstance()
            val decimalFormatSymbols = (nf as DecimalFormat).getDecimalFormatSymbols()
            decimalFormatSymbols.setCurrencySymbol("")
            decimalFormatSymbols.digit = '.'
            (nf as DecimalFormat).setDecimalFormatSymbols(decimalFormatSymbols)
            return nf.format(unit.toDouble())
        }
    }
}