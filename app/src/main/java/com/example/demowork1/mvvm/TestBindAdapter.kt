package com.example.demowork1.mvvm

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:bgcolor")
fun setBgColor(view: TextView, color: Int) {
    view.setBackgroundColor(color)
}

@BindingAdapter(value = ["android:text","android:bgcolor"],requireAll = true)
fun setData(view: TextView,text:String,color: Int){
    view.setBackgroundColor(color)
    view.text = text
    view.textSize = 15f
}