package com.ken207.mygit1

class  StringUtil {

    companion object {
        fun getSelectedOperand(strFormula: String, idxCursor: Int): String {
            if (strFormula.length == idxCursor)
                return ""

            val unitPosition: Array<Int> = getSelectedUnitPosition(strFormula, idxCursor)
            return strFormula.substring(unitPosition[0], unitPosition[1])
        }


        fun getSelectedUnitPosition(strFormula: String, idxCursor: Int): Array<Int> {

            if (strFormula.length == idxCursor)
                return arrayOf(idxCursor, idxCursor)

            var startIdx: Int = idxCursor
            var endIdx: Int = idxCursor
            val leftSideChar = if ( idxCursor == 0 ) { ' ' } else { strFormula[idxCursor-1]}
            val rightSideChar = strFormula[idxCursor]

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
            else if (rightSideChar in setOf(MULTIPLY, DIVIDE, PLUS, MINUS, R_BRACKET) ) {
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

            return arrayOf(startIdx, endIdx)
        }
    }
}