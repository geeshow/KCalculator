package com.ken207.mygit1

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class MyEditText : EditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr:Int) : super(context, attrs, defStyleAttr)

    override fun onCheckIsTextEditor(): Boolean {
         return super.onCheckIsTextEditor()
    }

}