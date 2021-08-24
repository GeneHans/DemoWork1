package com.example.demowork1.annotation

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.example.demowork1.R
import com.google.android.material.textfield.TextInputLayout

open class FormatInputLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    open var textInputLayout: TextInputLayout? = null
    open var editText: EditText? = null

    init {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_format_input, this, false)
        addView(view)
        textInputLayout = view.findViewById(R.id.text_input_layout)
        editText = view.findViewById(R.id.edit_text)
        addListener()
    }

    open fun addListener(){
    }


}