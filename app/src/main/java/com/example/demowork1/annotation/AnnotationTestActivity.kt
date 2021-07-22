package com.example.demowork1.annotation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.annotationtest.HelloAnnotation
import com.example.demowork1.R
import com.example.demowork1.annotation.shape.IShapeFactory
import com.example.demowork1.util.LogUtil
import com.example.shapetest.HelloWorld

@HelloAnnotation
class AnnotationTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation_test)
        var data = HelloWorld.hello1()+HelloWorld.hello2()+HelloWorld.hello3()
        LogUtil.instance.toast(data,this)
        val mShapeFactory = IShapeFactory()
        mShapeFactory.create("Circle").draw()
        LogUtil.instance.toast(data,this)
    }
}