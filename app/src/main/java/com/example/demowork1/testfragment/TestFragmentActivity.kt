package com.example.demowork1.testfragment

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.demowork1.R

class TestFragmentActivity : AppCompatActivity(), IHandleFragment {
    private var btn1: Button? = null
    private var btn2: Button? = null
    var testFragment1 = TestFragment1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N && isInMultiWindowMode){
            setContentView(R.layout.activity_test_fragment_resize)
        }
        else{
            setContentView(R.layout.activity_test_fragment)
        }
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn1?.setOnClickListener {
            openFragment1()
        }
        btn2?.setOnClickListener {
            testFragment1.updateData("update data")
        }
    }

    private fun openFragment1() {
        var bundle = Bundle()
        bundle.putString(TestFragment1.key, "data test fragment1")
        testFragment1.arguments = bundle
        supportFragmentManager.beginTransaction()
                .add(R.id.content, testFragment1)
                .addToBackStack("fragment1")
                .commit()
    }

    override fun openFragment2() {
        var testFragment2 = TestFragment2()
        var bundle = Bundle()
        bundle.putString(TestFragment2.key, "data test fragment2")
        testFragment2.arguments = bundle
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, testFragment2)
                .addToBackStack("fragment2")
                .commit()
    }
}