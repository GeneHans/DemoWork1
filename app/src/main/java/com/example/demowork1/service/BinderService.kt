package com.example.demowork1.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.common.util.LogUtil
import kotlin.random.Random

class BinderService : Service() {
    private var binder: MyBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("BinderService onCreate")
    }

    override fun onDestroy() {
        LogUtil.d("BinderService onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        LogUtil.d("BinderService onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        LogUtil.d("BinderService onUnbind")
        return super.onUnbind(intent)
    }

    fun getRandomNumber():Int{
        return Random(10).nextInt()
    }
}

class MyBinder : Binder() {
    fun getService(): BinderService {
        return BinderService()
    }
}