package com.ken207.mygit1

import java.lang.StringBuilder

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

        fun getNumberUnit(strFormula: String, idxCursor: Int): String {
            if ( idxCursor == 0 )
                return ""

            var startIdx: Int = idxCursor
            var endIdx: Int = idxCursor
            val leftSideChar = strFormula[idxCursor-1]
            val rightSideChar = if (strFormula.length <= idxCursor) ' ' else strFormula[idxCursor]

            if (rightSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9') ) {
                if (leftSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9') )
                    startIdx = strFormula.lastIndexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET, PERCENT), idxCursor - 1) + 1

                endIdx = strFormula.indexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET, PERCENT), idxCursor).coerceAtLeast(idxCursor+1)
            }
            else if (leftSideChar in arrayOf('0','1','2','3','4','5','6','7','8','9') ) {
                startIdx = strFormula.lastIndexOfAny(charArrayOf(MULTIPLY, DIVIDE, PLUS, MINUS, L_BRACKET, R_BRACKET), idxCursor - 1) + 1
            }

            return strFormula.substring(startIdx, endIdx)
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
    }
}