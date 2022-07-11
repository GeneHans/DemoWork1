package com.example.demowork1.notification

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.demowork1.R
import com.example.demowork1.notification.NotificationTest.Companion.CHANNEL_ID
import com.example.demowork1.util.LogUtil

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null || context == null)
            return
        var content = intent.extras?.getInt(Notification.EXTRA_NOTIFICATION_ID)
        LogUtil.d(content?.toString()?:"no data")
        var data = getMessageText(intent)
        LogUtil.d(data.toString())

        val repliedNotification =NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.arrow_right)
            .setContentTitle("aaa")
            .setContentText("bbb")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

// Issue the new notification.
        NotificationManagerCompat.from(context).apply {
            //接收到内容之后更新通知状态   注意ID必须要一致
            NotificationManagerCompat.from(context).notify(2, repliedNotification)
        }

    }
    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(NotificationTest.KEY_TEXT_REPLY)
    }
}