package com.example.singlework.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseActivity:AppCompatActivity(){
    lateinit var mActivity: BaseActivity

    inline fun <reified VB : ViewBinding> Activity.inflate() = lazy {
        inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
    }

    inline fun <reified VB : ViewBinding> Dialog.inflate() = lazy {
        inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
        VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
    }

}