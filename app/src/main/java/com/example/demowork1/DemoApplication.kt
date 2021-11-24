package com.example.demowork1

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.example.demowork1.database.litepal.LitePalDBManager
import com.example.demowork1.database.sqlite.SQLiteDBManager
import com.example.demowork1.database.room.DemoWorkDataBase
import com.example.demowork1.util.CrashHandler
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import org.litepal.LitePal


class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        if(BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        var crashHandler = CrashHandler.newInstance(this)
        //DbFlow初始化
        FlowManager.init(this)

        FlowManager.init(
            FlowConfig.Builder(this).openDatabasesOnInit(true).build()
        )

        //数据库初始化
        LitePal.initialize(this)
        LitePalDBManager.instance.initDB()
        SQLiteDBManager.getInstance(this).createDb(1)
        Room.databaseBuilder(this,
            DemoWorkDataBase::class.java,
            DemoWorkDataBase.RoomDataBaseName)
    }

    companion object {
        lateinit var mContext: Context
    }
}