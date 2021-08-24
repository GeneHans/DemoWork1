package com.example.demowork1.annotation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.annotationtest.HelloAnnotation
import com.example.demowork1.R
import com.example.demowork1.annotation.shape.Circle
import com.example.demowork1.annotation.shape.IShapeFactory
import com.example.demowork1.util.LogUtil
import com.example.shapetest.HelloWorld
import com.google.android.material.textfield.TextInputLayout
import java.util.logging.Logger
import java.util.regex.Matcher
import java.util.regex.Pattern

@HelloAnnotation
class AnnotationTestActivity : AppCompatActivity() {
    private lateinit var editTextTest: EditText
    private lateinit var textInputLayout: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation_test)
        editTextTest = findViewById(R.id.edit_input_test)
        textInputLayout = findViewById(R.id.input_layout_test)
        var cicle = IShapeFactory().create("Circle")
        cicle.draw()
        //开启错误提示
        textInputLayout.isErrorEnabled = true
        //开启计数
        textInputLayout.isCounterEnabled = true
        //设置输入最大长度
        textInputLayout.counterMaxLength = 10
        //设置右侧图标
        textInputLayout.setEndIconDrawable(R.drawable.arrow_right)
        editTextTest.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(FormatUtil.instance.isMatch(s.toString())){
                    LogUtil.instance.d("输入了字母或数字:"+s.toString())
                    textInputLayout.error = ""
                }
                else{
                    textInputLayout.error = "输入的字符并不是字母或者数字"
                }
            }

        })
        //设置错误文本
//        textInputLayout.error = "错误文本"
    }

    /**
     * 简单的APT测试
     */
    private fun testAnnotation() {
        var data = HelloWorld.hello1() + HelloWorld.hello2() + HelloWorld.hello3()
        LogUtil.instance.toast(data, this)
        val mShapeFactory = IShapeFactory()
        mShapeFactory.create("Circle").draw()
        LogUtil.instance.toast(data, this)
    }
}