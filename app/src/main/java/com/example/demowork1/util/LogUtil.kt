package com.example.demowork1.util

import android.util.Log

class LogUtil {

    private val TAG = "message"

    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }


    companion object {
        val instance: LogUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { LogUtil() }
    }
}
