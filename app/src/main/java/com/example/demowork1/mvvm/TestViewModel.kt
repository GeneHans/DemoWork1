package com.example.demowork1.mvvm

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demowork1.DemoApplication
import com.example.demowork1.R

class TestViewModel : ViewModel() {
    var text = MutableLiveData<String>()
    var clickNum = MutableLiveData<Int>()
    var bgColor = R.color.black
    fun showToast() {
        Toast.makeText(DemoApplication.mContext, "点击了文本", Toast.LENGTH_SHORT).show()
        clickNum.value = (clickNum.value ?: 0) + 1
    }

    init {
        text.value = "默认文案"
        clickNum.value = 0
    }
}