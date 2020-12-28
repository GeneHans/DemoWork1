package com.example.demowork1

import android.app.Application
import android.content.Context
import com.example.demowork1.manager.LitePalDBManager
import org.litepal.LitePal

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        LitePal.initialize(this)
        LitePalDBManager.instance.initDB()
    }

    companion object {
        var mContext: Context? = null
    }
}