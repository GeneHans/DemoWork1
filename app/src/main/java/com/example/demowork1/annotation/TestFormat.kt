package com.example.demowork1.annotation

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

class TestFormat @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FormatInputLayout(context, attrs, defStyleAttr) {

    override fun addListener() {
        super.addListener()
        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}