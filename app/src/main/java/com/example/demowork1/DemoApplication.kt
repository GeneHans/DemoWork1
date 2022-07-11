package com.example.demowork1

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.example.demowork1.database.room.DemoWorkDataBase
import com.example.demowork1.database.sqlite.SQLiteDBManager
import com.example.demowork1.util.CrashHandler


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
        SQLiteDBManager.getInstance(this).createDb(1)
        Room.databaseBuilder(this,
            DemoWorkDataBase::class.java,
            DemoWorkDataBase.RoomDataBaseName)
    }

    companion object {
        lateinit var mContext: Context
    }
}