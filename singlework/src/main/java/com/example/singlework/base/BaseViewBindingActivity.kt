package com.example.singlework.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var mActivity: BaseViewBindingActivity<VB>
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
    }

}