package com.example.common.util

import android.content.Context
import android.util.Log
import android.widget.Toast

object LogUtil {

    private val TAG = "message"

    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun toast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
