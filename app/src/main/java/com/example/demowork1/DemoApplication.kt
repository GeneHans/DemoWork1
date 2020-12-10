package com.example.demowork1

import android.app.Application
import android.content.Context

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
        var mContext: Context? = null
    }
}