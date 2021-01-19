package com.example.demowork1

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.demowork1.litepal.LitePalDBManager
import com.example.demowork1.sqlite.SQLiteDBManager
import com.tencent.mmkv.MMKV
import org.litepal.LitePal

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this

        //MMKV初始化
        MMKV.initialize(this)

        //数据库初始化
        LitePal.initialize(this)
        LitePalDBManager.instance.initDB()
        SQLiteDBManager.getInstance(this).createDb(1)
    }

    companion object {
        var mContext: Context? = null
    }
}