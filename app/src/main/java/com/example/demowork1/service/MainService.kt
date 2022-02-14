package com.example.demowork1.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.common.util.LogUtil

class MainService : Service() {

    private val serviceID = 101

    override fun onCreate() {
        LogUtil.d("MainService onCreate")
        super.onCreate()
        setForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.d("MainService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder? {
        LogUtil.d("onBind")
        return null
    }

    override fun onDestroy() {
        LogUtil.d("onDestroy")
        super.onDestroy()
    }


    /**
     * 将service设置为前台服务
     */
    private fun setForegroundService() {
        val channelId = "DemoWork1MainService"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var notificationChannel =
                manager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                notificationChannel =
                    NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.vibrationPattern = LongArray(0)
                notificationChannel.setSound(null, null)
                manager.createNotificationChannel(notificationChannel)
            }
        }
        val builder =
            NotificationCompat.Builder(this, channelId)
        builder.setSound(null)
        builder.setVibrate(longArrayOf())
        startForeground(serviceID, builder.build())
    }

}