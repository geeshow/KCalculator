package com.ken207.mygit1

class StringUtil {

    fun getSelectedOperand(strFormula:String, idxCursor:Int):String {
        if ( strFormula.length == idxCursor )
            return ""

        val unitPosition:Array<Int> = getSelectedUnitPosition(strFormula,idxCursor)
        return strFormula.substring(unitPosition[0],unitPosition[1])
    }

    fun getSelectedUnitPosition(strFormula:String, idxCursor:Int):Array<Int> {
        var startIdx:Int = idxCursor
        var endIdx:Int = idxCursor

        if ( strFormula.length == idxCursor )
            return arrayOf(startIdx,endIdx)

        val rightSideChar = strFormula[idxCursor]

        if (rightSideChar in ('0' .. '9') ) {
            var leftSideIdx:Int = idxCursor
            for ( index:Int in idxCursor - 1 downTo 0) {
                if ( strFormula[index] !in ('0' .. '9')) {
                    startIdx = index + 1
                    break
                }
            }

            for ( index:Int in idxCursor .. strFormula.length - 1) {
                if ( strFormula[index] !in ('0' .. '9')) {
                    endIdx = index
                    break
                }
            }
        }

        return arrayOf(startIdx,endIdx)
    }
}