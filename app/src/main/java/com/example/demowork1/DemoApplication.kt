package com.example.demowork1

import android.app.Application
import android.content.Context
import com.example.demowork1.database.litepal.LitePalDBManager
import com.example.demowork1.database.sqlite.SQLiteDBManager
import com.example.demowork1.util.CrashHandler
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowLog
import com.raizlabs.android.dbflow.config.FlowManager
import com.tencent.mmkv.MMKV
import org.litepal.LitePal


class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        var crashHandler = CrashHandler.newInstance(this)
        //DbFlow初始化
        FlowManager.init(this)

        FlowManager.init(
            FlowConfig.Builder(this).openDatabasesOnInit(true).build()
        )

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